package com.cloud.resources.xlistview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.PixelUtils;

import java.util.List;

/**
 * @Author LiJingHuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-5 上午6:08:10
 * @Description:XListView数据适配器
 * @Modifier:
 * @ModifyContent:
 */
public abstract class BaseXListAdapter<THolder> extends BaseAdapter {

    private SlidingSlideView mLastSlideViewWithStatusOn = null;

    public abstract XListInstanceEntity<THolder> getInstanceView();

    public abstract THolder buildViewHolder(View convertView);

    public abstract List<OperatorItemEntity> getItemView(int position,
                                                         THolder holder, SlidingSlideView slideView);

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void onItemClickListener(int position) {

    }

    public void OnSlideViewCliskListener(OperatorItemEntity item) {

    }

    /**
     * 便于后面扩展
     *
     * @author lijinghuan
     */
    private class InnerViewHolder {
        public THolder thb = null;
        public SlideGroupView sgv = null;
        public boolean IsAddSGV = false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        XListInstanceEntity<THolder> xlie = getInstanceView();
        if (xlie == null) {
            return convertView;
        }
        SlidingSlideView slideView = (SlidingSlideView) convertView;
        try {
            InnerViewHolder ivh = null;
            if (slideView == null) {
                ivh = new InnerViewHolder();
                View itemView = View.inflate(xlie.getContext(),
                        xlie.getLayoutItemId(), null);
                if (xlie.isEnableItemViewClick()) {
                    itemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.getTag() == null) {
                                return;
                            }
                            int position = ConvertUtils.toInt(v.getTag());
                            onItemClickListener(position);
                        }
                    });
                }
                ViewGroup.LayoutParams vparam = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                itemView.setLayoutParams(vparam);
                int mhwidth = PixelUtils.dip2px(xlie.getContext(),
                        xlie.getHolderWidth());
                slideView = new SlidingSlideView(xlie.getContext(), mhwidth);
                slideView.setContentView(itemView);
                slideView.setOnSlideListener(new XSlideListener());
                xlie.setHolder(buildViewHolder(itemView));
                ivh.thb = xlie.getHolder();
                if (xlie.isEnableSliding()) {
                    ivh.sgv = new SlideGroupView(xlie.getContext(), mhwidth);
                    ivh.sgv.setBackgroundColor(xlie.getHolderBackgroudColor());
                }
                ivh.IsAddSGV = true;
                slideView.setTag(ivh);
            } else {
                ivh = (InnerViewHolder) slideView.getTag();
                ivh.IsAddSGV = false;
                xlie.setHolder(ivh.thb);
            }
            slideView.getContentView().setTag(position);
            List<OperatorItemEntity> oies = getItemView(position,
                    xlie.getHolder(), slideView);
            if (xlie.isEnableSliding() && ivh.IsAddSGV
                    && !ObjectJudge.isNullOrEmpty(oies)) {
                for (OperatorItemEntity item : oies) {
                    ivh.sgv.addView(createItem(xlie.getContext(), item));
                }
                slideView.addView(ivh.sgv);
            }
            slideView.shrinkByFast();
        } catch (Exception e) {
            Logger.L.error("build list item view error:", e);
        }
        return slideView;
    }

    private TextView createItem(Context context, OperatorItemEntity item) {
        TextView tv = new TextView(context);
        try {
            LinearLayout.LayoutParams tvparam = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT);
            tvparam.weight = 1;
            tv.setLayoutParams(tvparam);
            tv.setText(item.getText());
            tv.setGravity(Gravity.CENTER);
            tv.setClickable(true);
            tv.setPadding(PixelUtils.dip2px(context, 5), 0,
                    PixelUtils.dip2px(context, 5), 0);
            if (item.getTextColor() != 0) {
                tv.setTextColor(item.getTextColor());
            }
            if (item.getBackgroudResId() != 0) {
                tv.setBackgroundResource(item.getBackgroudResId());
            } else {
                tv.setBackgroundResource(0);
            }
            if (item.getIcon() != 0) {
                Drawable drawable = context.getResources().getDrawable(
                        item.getIcon());
                tv.setCompoundDrawablesWithIntrinsicBounds(drawable, null,
                        null, null);
            }
            tv.setTag(item);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnSlideViewCliskListener((OperatorItemEntity) v.getTag());
                }
            });
        } catch (Exception e) {
            Logger.L.error("build slide TextView error:", e);
        }
        return tv;
    }

    private class XSlideListener implements SlidingSlideView.OnSlideListener {
        @Override
        public void onSlide(View view, int status) {
            if (mLastSlideViewWithStatusOn != null
                    && mLastSlideViewWithStatusOn != view) {
                mLastSlideViewWithStatusOn.shrink();
            }
            if (status == SLIDE_STATUS_ON) {
                mLastSlideViewWithStatusOn = (SlidingSlideView) view;
            }
        }
    }
}
