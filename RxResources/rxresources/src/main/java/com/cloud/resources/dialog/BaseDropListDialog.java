package com.cloud.resources.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.core.ObjectJudge;
import com.cloud.core.ObjectManager;
import com.cloud.core.beans.DropListItem;
import com.cloud.core.loadings.SmallLoading;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;
import com.cloud.core.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import com.cloud.resources.R;
import com.cloud.resources.enums.DropListShowType;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016-1-18 下午10:17:46
 * @Description: 每排两个列表；每排一个ListView列表
 * @Modifier:
 * @ModifyContent:
 */
public class BaseDropListDialog extends PopupWindow {

    private SmallLoading msloading = new SmallLoading();
    private int ROOT_VIEW_ID = 2042989620;
    private int CONTENT_VIEW_ID = 543839303;
    private int TEXT_VIEW_ID = 1615069812;
    private int ITEM_CHECK_STATE_TAG = 282951366;
    private DropListShowType mdlstype = DropListShowType.None;
    private List<DropListItem> datalist = new ArrayList<DropListItem>();
    private DropListAdapter curradapter = null;
    private DropListItem selectedItem = new DropListItem();

    protected void onDismissListener() {

    }

    protected void onItemSelectedListener(DropListItem dlitem) {

    }

    protected void show(Activity activity, View parent, View contentView,
                        int xoff, int yoff) {
        this.setContentView(contentView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(Color.WHITE);
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                onDismissListener();
            }
        });
        super.showAsDropDown(parent, xoff, yoff);
    }

    protected RelativeLayout buildContentView(Context context,
                                              DropListShowType dlstype) {
        this.mdlstype = dlstype;
        RelativeLayout.LayoutParams llparam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout mrl = new RelativeLayout(context);
        mrl.setLayoutParams(llparam);
        mrl.setId(ROOT_VIEW_ID);
        mrl.setBackgroundColor(Color.WHITE);
        mrl.setGravity(Gravity.CENTER);
        mrl.setPadding(0, 0, 0, 0);
        RelativeLayout.LayoutParams cviewparam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout mcviewll = new LinearLayout(context);
        mcviewll.setLayoutParams(cviewparam);
        mcviewll.setOrientation(LinearLayout.VERTICAL);
        mcviewll.setId(CONTENT_VIEW_ID);
        if (mdlstype == DropListShowType.EachRowOne) {
            mcviewll.addView(createListView(mcviewll.getContext()));
        }
        mrl.addView(mcviewll);
        msloading.show(context, mrl);
        return mrl;
    }

    /**
     * @return 获取datalist
     */
    public List<DropListItem> getDatalist() {
        return datalist;
    }

    private View createSplitLine(Context context, boolean isHorizontal) {
        LinearLayout.LayoutParams vparam = new LinearLayout.LayoutParams(
                isHorizontal ? LinearLayout.LayoutParams.MATCH_PARENT : 1,
                isHorizontal ? 1 : LinearLayout.LayoutParams.MATCH_PARENT);
        View v = new View(context);
        v.setLayoutParams(vparam);
        v.setBackgroundColor(Color.rgb(215, 212, 207));
        return v;
    }

    private int getTileItemWidth(Context context) {
        int diswidth = ObjectManager.getDisplayMetrics(context).widthPixels;
        return (diswidth - 1) / 2;
    }

    private LinearLayout createItem(Context context, DropListItem item,
                                    boolean isTile) {
        LinearLayout.LayoutParams llparam = new LinearLayout.LayoutParams(
                isTile ? getTileItemWidth(context)
                        : LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout mll = new LinearLayout(context);
        mll.setLayoutParams(llparam);
        mll.setGravity(Gravity.CENTER);
        mll.setOrientation(LinearLayout.HORIZONTAL);
        mll.setTag(item);
        mll.setBackgroundResource(R.drawable.drop_list_item_bg);
        mll.setTag(ITEM_CHECK_STATE_TAG, 0);
        mll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener(v);
            }
        });
        LinearLayout.LayoutParams tvparam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(context);
        tv.setLayoutParams(tvparam);
        tv.setId(TEXT_VIEW_ID);
        tv.setTextColor(Color.rgb(50, 50, 50));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tv.setText(item.getDisplayName());
        tv.setCompoundDrawablePadding(PixelUtils.dip2px(context, 8));
        tv.setPadding(PixelUtils.dip2px(context, 6),
                PixelUtils.dip2px(context, 10), PixelUtils.dip2px(context, 6),
                PixelUtils.dip2px(context, 10));
        tv.setSingleLine(true);
        mll.addView(tv);
        return mll;
    }

    protected void notifyDataSetChanged() {
        try {
            LinearLayout mcviewll = (LinearLayout) getContentView()
                    .findViewById(CONTENT_VIEW_ID);
            if (mdlstype == DropListShowType.EachRowTwo) {
                LinearLayout.LayoutParams rowparam = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout row = new LinearLayout(mcviewll.getContext());
                row.setLayoutParams(rowparam);
                for (int i = 0; i < datalist.size(); i++) {
                    DropListItem item = datalist.get(i);
                    if (item.isChk()) {
                        selectedItem = item;
                    }
                    LinearLayout mitemview = createItem(mcviewll.getContext(),
                            item, true);
                    TextView tv = (TextView) mitemview
                            .findViewById(TEXT_VIEW_ID);
                    setSelectedMark(mcviewll.getContext(), item, tv);
                    row.addView(mitemview);
                    if (i % 2 == 0) {
                        row.addView(createSplitLine(mcviewll.getContext(),
                                false));
                    }
                    if ((i + 1) % 2 == 0) {
                        mcviewll.addView(createSplitLine(mcviewll.getContext(),
                                true));
                        mcviewll.addView(row);
                        row = new LinearLayout(mcviewll.getContext());
                        row.setLayoutParams(rowparam);
                    } else {
                        if ((i + 1) == datalist.size()) {
                            mcviewll.addView(createSplitLine(
                                    mcviewll.getContext(), true));
                            mcviewll.addView(row);
                        }
                    }
                }
                mcviewll.addView(createSplitLine(mcviewll.getContext(), true));
                RelativeLayout container = (RelativeLayout) getContentView()
                        .findViewById(ROOT_VIEW_ID);
                msloading.dismiss(container);
            } else if (mdlstype == DropListShowType.EachRowOne) {
                curradapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            Logger.L.error("notify dataset changed deal with error:", e);
        }
    }

    private class DropListItemViewHolder {
        public TextView displayNameTv = null;
    }

    private class DropListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Object getItem(int position) {
            return datalist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                DropListItem mdlitem = datalist.get(position);
                DropListItemViewHolder holder = null;
                if (convertView == null) {
                    holder = new DropListItemViewHolder();
                    convertView = createItem(getContentView().getContext(),
                            mdlitem, false);
                    holder.displayNameTv = (TextView) convertView
                            .findViewById(TEXT_VIEW_ID);
                    convertView.setTag(holder);
                } else {
                    holder = (DropListItemViewHolder) convertView.getTag();
                }
                holder.displayNameTv.setTag(mdlitem.getDisplayName());
                setSelectedMark(getContentView().getContext(), mdlitem,
                        holder.displayNameTv);
            } catch (Exception e) {
                Logger.L.error("build order state item error:", e);
            }
            return convertView;
        }
    }

    private void setSelectedMark(Context context, DropListItem item, TextView tv) {
        if (item.isChk()) {
            Drawable mrightdraw = context.getResources().getDrawable(
                    R.drawable.tick_icon);
            mrightdraw.setBounds(0, 0, mrightdraw.getMinimumWidth(),
                    mrightdraw.getMinimumHeight());
            tv.setCompoundDrawables(null, null, mrightdraw, null);
            tv.setCompoundDrawablePadding(PixelUtils.dip2px(context, 8));
        } else {
            tv.setCompoundDrawables(null, null, null, null);
            tv.setCompoundDrawablePadding(0);
        }
    }

    private ListView createListView(Context context) {
        LinearLayout.LayoutParams lvparam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView lv = new ListView(context);
        lv.setLayoutParams(lvparam);
        lv.setDivider(new ColorDrawable(Color.parseColor("#E8E8E8")));
        lv.setDividerHeight(1);
        lv.setBackgroundColor(Color.WHITE);
        curradapter = new DropListAdapter();
        lv.setAdapter(curradapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectedItem = datalist.get(position);
                onItemSelectedListener(selectedItem);
            }
        });
        return lv;
    }

    private void clearAllSelectedItems() {
        LinearLayout mcviewll = (LinearLayout) getContentView().findViewById(
                CONTENT_VIEW_ID);
        List<View> lst = ViewUtils.getViewsByTag(mcviewll,
                ITEM_CHECK_STATE_TAG, 1);
        if (!ObjectJudge.isNullOrEmpty(lst)) {
            for (View view : lst) {
                view.setTag(ITEM_CHECK_STATE_TAG, 0);
                TextView tv = (TextView) view.findViewById(TEXT_VIEW_ID);
                tv.setCompoundDrawables(null, null, null, null);
                tv.setCompoundDrawablePadding(0);
            }
        }
    }

    private void onItemClickListener(View v) {
        try {
            DropListItem dlitem = (DropListItem) v.getTag();
            if (dlitem == null) {
                return;
            }
            if (mdlstype == DropListShowType.EachRowTwo) {
                clearAllSelectedItems();
                v.setTag(ITEM_CHECK_STATE_TAG, 1);
                TextView tv = (TextView) v.findViewById(TEXT_VIEW_ID);
                Drawable mrightdraw = getContentView().getResources()
                        .getDrawable(R.drawable.tick_icon);
                mrightdraw.setBounds(0, 0, mrightdraw.getMinimumWidth(),
                        mrightdraw.getMinimumHeight());
                tv.setCompoundDrawables(null, null, mrightdraw, null);
                tv.setCompoundDrawablePadding(PixelUtils.dip2px(v.getContext(),
                        8));
                for (DropListItem element : datalist) {
                    if (TextUtils.equals(element.getId(), dlitem.getId())) {
                        element.setChk(true);
                    } else {
                        element.setChk(false);
                    }
                }
            } else if (mdlstype == DropListShowType.EachRowOne) {
                if (curradapter != null) {
                    curradapter.notifyDataSetChanged();
                }
            }
            selectedItem = dlitem;
            onItemSelectedListener(dlitem);
            dismiss();
        } catch (Exception e) {
            Logger.L.error("on item click deal with error:", e);
        }
    }

    public DropListItem getSelectedItem() {
        DropListItem item = new DropListItem();
        for (DropListItem element : datalist) {
            if (element.isChk()) {
                item = element;
                break;
            }
        }
        return item;
    }
}
