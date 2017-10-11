package com.cloud.resources.flows;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloud.core.ObjectJudge;
import com.cloud.core.beans.MapEntry;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;
import com.cloud.resources.beans.FlowEditBean;
import com.cloud.resources.beans.FlowLayoutInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-19 上午11:07:41
 * @Description: 流式可编辑项组件(类似多选联系人编辑控件)
 * @Modifier:
 * @ModifyContent:
 */
public class FlowEditItemsLayout extends LinearLayout {

    private List<MapEntry<String, FlowEditBean>> data = new ArrayList<MapEntry<String, FlowEditBean>>();
    private List<FlowEditBean> tags = new ArrayList<FlowEditBean>();
    private FlowLayout mflv = null;
    private EditText mflet = null;
    private FlowLayoutInstance mfli = new FlowLayoutInstance();
    private int ENABLE_CHECK_TAG_KEY = 915283048;


    private OnFlowLayoutListener mflowLayoutListener = null;

    /**
     * @param listener
     */
    public void setOnFlowLayoutListener(OnFlowLayoutListener listener) {
        this.mflowLayoutListener = listener;
        changeInstanceSetting();
    }

    public FlowEditItemsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(false);
    }

    public FlowEditItemsLayout(Context context) {
        super(context);
        init(true);
    }

    private void init(boolean flagparams) {
        if (flagparams) {
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(params);
        }
        int mpadding = PixelUtils.dip2px(getContext(), 3);
        this.setPadding(mpadding, mpadding, mpadding, mpadding);
        mflv = (FlowLayout) View.inflate(getContext(), R.layout.flow_item_layout_view, null);
        mflet = (EditText) mflv.findViewById(R.id.flow_edit_et);
        LayoutParams flvparams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        this.addView(mflv, flvparams);
    }

    private void changeInstanceSetting() {
        if (mflowLayoutListener != null) {
            mfli = mflowLayoutListener.getFlowLayoutInstance(mfli);
            mflet.setVisibility(mfli.isEnableEdit() ? View.VISIBLE : View.GONE);
            mflv.setBackgroundResource(mfli.getFlowBackground());
        }
    }

    public void addItem(String title, Object tagobj) {
        addItem(title, tagobj, false);
    }

    public void addItem(String title, Object tagobj, boolean check) {
        try {
            if (mflowLayoutListener != null) {
                if (isContainsItem(title)) {
                    return;
                }
                final FlowEditable ea = new FlowEditable(getContext(), mfli);
                View itemview = ea.getItemView();
                View contentview = itemview.findViewById(R.id.flow_edit_content_rl);
                ea.getTitleView().setText(title);
                contentview.setTag(tagobj);
                ea.getDeleteView().setTag(itemview);
                ea.getDeleteView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View itemv = (View) v.getTag();
                        deleteItem(itemv);
                        if (mflowLayoutListener != null) {
                            mflowLayoutListener.deleteItem(itemv.getTag());
                        }
                    }
                });
                if (mfli.isEnableDelete()) {
                    ea.getDeleteView().setVisibility(View.VISIBLE);
                } else {
                    ea.getDeleteView().setVisibility(View.GONE);
                }
                if (mfli.isEnableCheck()) {
                    contentview.setTag(ENABLE_CHECK_TAG_KEY, 0);
                }
                MapEntry<String, FlowEditBean> mentry = new MapEntry<String, FlowEditBean>();
                mentry.setKey(title);
                mentry.setValue(new FlowEditBean(title, tagobj, itemview));
                if (check) {
                    View chkcontentv = itemview.findViewById(R.id.flow_edit_content_rl);
                    TextView chktextview = (TextView) chkcontentv.findViewById(R.id.flow_title);
                    chkcontentv.setTag(ENABLE_CHECK_TAG_KEY, 1);
                    if (mfli.getSelectedItemBackground() != 0) {
                        chkcontentv.setBackgroundResource(mfli.getSelectedItemBackground());
                        chktextview.setTextColor(mfli.getSelectedTitleTextColor());
                    }
                }
                data.add(mentry);
                tags.add(new FlowEditBean(title, tagobj, contentview, ea.getTitleView()));
                contentview.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag() == null) {
                            return;
                        }
                        if (mfli.isEnableCheck()) {
                            int checktag = ConvertUtils.toInt(v.getTag(ENABLE_CHECK_TAG_KEY));
                            if (mfli.isSingleChecked()) {
                                List<FlowEditBean> selitems = getFlowEditBeanSelectedItems();
                                if (!ObjectJudge.isNullOrEmpty(selitems)) {
                                    clearSelectedItems(selitems);
                                }
                            }
                            setItemCheckStatus(v, mfli.isLeastSelected() ? 0 : checktag, ea.getTitleView());
                        }
                        if (mflowLayoutListener != null) {
                            mflowLayoutListener.onItemClickListener(v.getTag());
                        }
                    }
                });
                mflv.addView(itemview);
                if (mflv.getChildCount() > 1) {
                    mflv.setBackgroundResource(mfli.getFlowBackground());
                }
            }
        } catch (Exception e) {
            Logger.L.error("add flow layout item error:", e);
        }
    }

    private void setItemCheckStatus(View v, int checktag, TextView tv) {
        if (checktag == 1) {
            v.setTag(ENABLE_CHECK_TAG_KEY, 0);
            v.setBackgroundResource(mfli.getFlowItemBackground());
            tv.setTextColor(mfli.getTitleTextColor());
        } else {
            v.setTag(ENABLE_CHECK_TAG_KEY, 1);
            if (mfli.getSelectedItemBackground() != 0) {
                v.setBackgroundResource(mfli.getSelectedItemBackground());
                tv.setTextColor(mfli.getSelectedTitleTextColor());
            }
        }
    }

    private boolean isContainsItem(String key) {
        boolean flag = false;
        for (MapEntry<String, FlowEditBean> mentry : data) {
            if (TextUtils.equals(mentry.getKey().trim(), key.trim())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public View findItem(int index) {
        try {
            if (index < 0) {
                index = 0;
            }
            int n = mflv.getChildCount();
            if (index > (n - 2)) {
                return null;
            }
            return mflv.getChildAt(index);
        } catch (Exception e) {
            Logger.L.error("find item by index error:", e);
        }
        return null;
    }

    public void deleteItem(View v) {
        try {
            FlowEditBean removeItem = null;
            for (MapEntry<String, FlowEditBean> mentry : data) {
                if (v.equals(mentry.getValue().getView())) {
                    removeItem = mentry.getValue();
                }
            }
            if (removeItem != null) {
                data.remove(removeItem);
            }
            mflv.removeView(v);
            if (mflv.getChildCount() <= 1) {
                mflv.setBackgroundResource(0);
            }
        } catch (Exception e) {
            Logger.L.error("delete flow layout item error:", e);
        }
    }

    public List<FlowEditBean> getAllItems() {
        return tags;
    }

    public void deleteItems() {
        int flSize = mflv.getChildCount();
        if (flSize - 1 > 0) {
            mflv.removeViews(0, flSize - 1);
        }
    }

    public void deleteItem(int index) {
        View v = findItem(index);
        if (v != null) {
            deleteItem(v);
        }
    }

    public void deleteItem() {
        try {
            String content = mflet.getText().toString();
            int flSize = mflv.getChildCount();
            if (TextUtils.isEmpty(content)) {
                if (flSize > 1) {
                    mflv.removeViewAt(flSize - 2);
                    if (data.size() > 0) {
                        data.remove(data.size() - 1);
                    }
                    if (mflv.getChildCount() <= 1) {
                        mflv.setBackgroundResource(0);
                    }
                }
            } else {
                mflet.setText("");
            }
        } catch (Exception e) {
            Logger.L.error("delete flow layout last item error:", e);
        }
    }

    public List<FlowEditBean> getFlowEditBeanSelectedItems() {
        List<FlowEditBean> lst = new ArrayList<FlowEditBean>();
        try {
            for (MapEntry<String, FlowEditBean> mentry : data) {
                View v = mentry.getValue().getView().findViewById(R.id.flow_edit_content_rl);
                int checktag = ConvertUtils.toInt(v.getTag(ENABLE_CHECK_TAG_KEY));
                if (checktag == 1) {
                    lst.add(mentry.getValue());
                }
            }
        } catch (Exception e) {
            Logger.L.error("get flow edit bean selected items error:", e);
        }
        return lst;
    }

    public void setSelectedItem(List<String> selected) {
        try {
            List<FlowEditBean> selitems = getFlowEditBeanSelectedItems();
            for (FlowEditBean flowEditBean : selitems) {
                View v = flowEditBean.getView().findViewById(R.id.flow_edit_content_rl);
                TextView textview = (TextView) v.findViewById(R.id.flow_title);
                v.setTag(ENABLE_CHECK_TAG_KEY, 0);
                v.setBackgroundResource(mfli.getFlowItemBackground());
                textview.setTextColor(mfli.getTitleTextColor());
            }
            for (String item : selected) {
                for (MapEntry<String, FlowEditBean> mentry : data) {
                    if (TextUtils.equals(item, mentry.getKey().trim())) {
                        View v = mentry.getValue().getView().findViewById(R.id.flow_edit_content_rl);
                        TextView textview = (TextView) v.findViewById(R.id.flow_title);
                        v.setTag(ENABLE_CHECK_TAG_KEY, 1);
                        if (mfli.getSelectedItemBackground() != 0) {
                            v.setBackgroundResource(mfli.getSelectedItemBackground());
                            textview.setTextColor(mfli.getSelectedTitleTextColor());
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("set already selected items error:", e);
        }
    }

    private void clearSelectedItems(List<FlowEditBean> lst) {
        try {
            if (ObjectJudge.isNullOrEmpty(lst)) {
                return;
            }
            for (FlowEditBean flowEditBean : lst) {
                View v = flowEditBean.getView().findViewById(R.id.flow_edit_content_rl);
                TextView textview = (TextView) v.findViewById(R.id.flow_title);
                v.setTag(ENABLE_CHECK_TAG_KEY, 0);
                v.setBackgroundResource(mfli.getFlowItemBackground());
                textview.setTextColor(mfli.getTitleTextColor());
            }
        } catch (Exception e) {
            Logger.L.error("clear selected items error:", e);
        }
    }

    public void clearSelectItems() {
        try {
            List<FlowEditBean> selitems = getFlowEditBeanSelectedItems();
            if (ObjectJudge.isNullOrEmpty(selitems)) {
                return;
            }
            for (FlowEditBean flowEditBean : selitems) {
                View v = flowEditBean.getView().findViewById(R.id.flow_edit_content_rl);
                TextView textview = (TextView) v.findViewById(R.id.flow_title);
                v.setTag(ENABLE_CHECK_TAG_KEY, 0);
                v.setBackgroundResource(mfli.getFlowItemBackground());
                textview.setTextColor(mfli.getTitleTextColor());
            }
        } catch (Exception e) {
            Logger.L.error("clear all selected items error:", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getSelectedItems() {
        List<T> lst = new ArrayList<T>();
        try {
            List<FlowEditBean> selitems = getFlowEditBeanSelectedItems();
            for (FlowEditBean flowEditBean : selitems) {
                Object obj = flowEditBean.getView().findViewById(R.id.flow_edit_content_rl).getTag();
                if (obj != null) {
                    lst.add((T) obj);
                }
            }
        } catch (Exception e) {
            Logger.L.error("get selected items error:", e);
        }
        return lst;
    }


}
