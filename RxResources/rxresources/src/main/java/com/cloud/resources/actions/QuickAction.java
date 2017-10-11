package com.cloud.resources.actions;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.core.logger.Logger;
import com.cloud.core.shapes.JustTriangleDown;
import com.cloud.core.shapes.JustTriangleLeft;
import com.cloud.core.shapes.JustTriangleRight;
import com.cloud.core.shapes.JustTriangleUp;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;

import java.util.ArrayList;
import java.util.List;

public class QuickAction extends PopupWindow {

    private BitmapDrawable mBackground;

    private QuickactionItemVertical mRootView;

    private Display mDefaultDisplay;

    private View mArrowTop;

    private View mArrowBottom;

    private View mArrowLeft;

    private View mArrowRight;

    private LinearLayout mQuickActionLayout;

    private int[] mAnchorLocations;

    private int mScreenWidth;

    private int mScreenHeight;

    private List<ActionItem> mActionItems = new ArrayList<ActionItem>();

    private OnClickQuickActionListener mClickListener;
    /**
     * 背景
     */
    private int quickActionBackgroundResource = 0;
    /**
     * item 背景
     */
    private int quickActionItemBackgroundResource = 0;
    /**
     * 宽(默认100dp)
     */
    private int quickActionWidth = 100;
    /**
     * 箭头背景颜色
     */
    private int quickActionJustTriangleBackgroundColor = 0;
    /**
     * 文本颜色
     */
    private int quickActionTextColor = 0;

    private ThemeColorType themeColorType = ThemeColorType.WHITE;

    public void setQuickActionBackgroundResource(int resId) {
        this.quickActionBackgroundResource = resId;
    }

    public void setQuickActionItemBackgroundResource(int resId) {
        this.quickActionItemBackgroundResource = resId;
    }

    public void setQuickActionWidth(int width) {
        if (width <= 0) {
            return;
        }
        this.quickActionWidth = width;
    }

    public void setQuickActionJustTriangleBackgroundColor(int backgroundColor) {
        this.quickActionJustTriangleBackgroundColor = backgroundColor;
    }

    public void setQuickActionTextColor(int textColor) {
        this.quickActionTextColor = textColor;
    }

    public QuickAction(Context context) {
        super(context);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mDefaultDisplay = wm.getDefaultDisplay();
        initParams();
    }

    /**
     * @param anchor
     * @param appendx x方向增加偏量值
     * @param appendy y方向增加偏量值
     */
    public void show(View anchor, int appendx, int appendy) {
        try {
            if (!isShowing()) {
                if (mRootView == null) {
                    initQuickAction(anchor.getContext(), themeColorType);
                }
                Direction showDirection = computeDisplayPosition(anchor);
                int[] locations = preShow(anchor, showDirection);
                if (locations != null) {
                    showAtLocation(anchor, Gravity.NO_GRAVITY, locations[0]
                            + appendx, locations[1] + appendy);
                }
            } else {
                dismiss();
            }
        } catch (Exception e) {
            Logger.L.error("show quick action error:", e);
        }
    }

    /**
     * @param quickAction
     */
    public void addQuickAction(View quickAction) {
        mQuickActionLayout.addView(quickAction);
    }

    private void removeActionItem(ActionItem actionItem) {
        int position = -1;
        for (int i = 0; i < mActionItems.size(); i++) {
            ActionItem item = mActionItems.get(i);
            if (TextUtils.equals(item.getActionId(), actionItem.getActionId())) {
                position = i;
                break;
            }
        }
        if (position >= 0) {
            mActionItems.remove(position);
        }
    }

    /**
     * 添加quick action item
     *
     * @param context
     * @param item
     * @param isShowSplitLine 是否显示分隔线
     */
    public void addQuickActionItem(Context context, final ActionItem item, boolean isShowSplitLine) {
        try {
            if (mQuickActionLayout == null) {
                return;
            }
            removeActionItem(item);
            mActionItems.add(item);
            QuickactionItemHorizontal container = new QuickactionItemHorizontal(context);
            ImageView iconView = (ImageView) container.findViewById(QuickActionPkg.QUICKACTION_ICON_IV);
            TextView titleView = (TextView) container.findViewById(QuickActionPkg.QUICKACTION_TITLE_TV);
            if (item.getIcon() != null) {
                iconView.setImageDrawable(item.getIcon());
                iconView.setVisibility(View.VISIBLE);
                titleView.setGravity(Gravity.LEFT);
            } else {
                iconView.setVisibility(View.GONE);
                titleView.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            String title = item.getTitle();
            if (title != null) {
                titleView.setText(title);
            } else {
                titleView.setVisibility(View.GONE);
            }
            container.setTag(item.getActionId());
            container.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.OnClickQuickAction(view.getTag().toString(),
                                item.getPosition());
                        dismiss();
                    }
                }
            });
            int childcount = mQuickActionLayout.getChildCount();
            int idx = -1;
            if (childcount > 0) {
                for (int i = 0; i < childcount; i++) {
                    View m = mQuickActionLayout.getChildAt(i);
                    if (TextUtils.equals(String.valueOf(m.getTag()), item.getActionId())) {
                        idx = i;
                        break;
                    }
                }
            }
            if (idx >= 0) {
                mQuickActionLayout.removeViewAt(idx);
            }
            mQuickActionLayout.addView(container);
            if (isShowSplitLine) {
                mQuickActionLayout.addView(buildSplitLine(context));
            }
        } catch (Exception e) {
            Logger.L.error("add quick action item error:", e);
        }
    }

    private class QuickactionItemHorizontal extends LinearLayout {
        public QuickactionItemHorizontal(Context context) {
            super(context);
            LayoutParams llparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(llparam);
            this.setGravity(Gravity.CENTER_VERTICAL);
            this.setOrientation(VERTICAL);
            this.addView(new RowContainer(context));
        }

        private class RowContainer extends LinearLayout {
            public RowContainer(Context context) {
                super(context);
                LayoutParams llparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                this.setLayoutParams(llparam);
                this.setBackgroundResource(quickActionItemBackgroundResource != 0 ? quickActionItemBackgroundResource : R.drawable.quickaction_item_bg);
                this.setOrientation(HORIZONTAL);
                this.setGravity(Gravity.CENTER_HORIZONTAL);
                this.setMinimumWidth(PixelUtils.dip2px(context, 100));
                this.setPadding(PixelUtils.dip2px(context, 4), PixelUtils.dip2px(context, 6), PixelUtils.dip2px(context, 4), PixelUtils.dip2px(context, 6));

                ImageView iconiv = new ImageView(context);
                iconiv.setId(QuickActionPkg.QUICKACTION_ICON_IV);
                LayoutParams ivparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                ivparam.setMargins(0, 0, PixelUtils.dip2px(context, 3), 0);
                iconiv.setLayoutParams(ivparam);
                iconiv.setBackgroundColor(Color.TRANSPARENT);
                iconiv.setScaleType(ImageView.ScaleType.MATRIX);
                this.addView(iconiv);

                TextView texttv = new TextView(context);
                LayoutParams tvparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                tvparam.gravity = Gravity.CENTER;
                texttv.setLayoutParams(tvparam);
                texttv.setTextColor(quickActionTextColor != 0 ? quickActionTextColor : (themeColorType == ThemeColorType.BLACK ? Color.parseColor("#ffffff") : Color.parseColor("#323232")));
                texttv.setId(QuickActionPkg.QUICKACTION_TITLE_TV);
                this.addView(texttv);
            }
        }
    }

    private View buildSplitLine(Context context) {
        View splitLine = new View(context);
        LinearLayout.LayoutParams splitLineParam = new LinearLayout.LayoutParams(PixelUtils.dip2px(context, quickActionWidth - 4), 1);
        splitLineParam.setMargins(PixelUtils.dip2px(context, 2), 0, PixelUtils.dip2px(context, 2), 0);
        splitLine.setLayoutParams(splitLineParam);
        splitLine.setBackgroundColor(Color.parseColor("#e6e6e6"));
        return splitLine;
    }

    public void updateQuickActionIcon(String actionId, Drawable icon) {
        try {
            if (mQuickActionLayout == null) {
                return;
            }
            for (ActionItem item : mActionItems) {
                View mitemview = mQuickActionLayout.findViewWithTag(item.getActionId());
                ImageView iconView = (ImageView) mitemview.findViewById(QuickActionPkg.QUICKACTION_ICON_IV);
                TextView titleView = (TextView) mitemview.findViewById(QuickActionPkg.QUICKACTION_TITLE_TV);
                if (TextUtils.equals(item.getActionId(), actionId)) {
                    if (icon != null) {
                        iconView.setImageDrawable(icon);
                        iconView.setVisibility(View.VISIBLE);
                        titleView.setGravity(Gravity.LEFT);
                    } else {
                        iconView.setVisibility(View.GONE);
                        titleView.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                } else {
                    if (item.getIcon() != null) {
                        iconView.setImageDrawable(item.getIcon());
                        iconView.setVisibility(View.VISIBLE);
                        titleView.setGravity(Gravity.LEFT);
                    } else {
                        iconView.setVisibility(View.GONE);
                        titleView.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("update quick action icon error:", e);
        }
    }

    public void setOnClickQuickActionListener(
            OnClickQuickActionListener listener) {
        mClickListener = listener;
    }

    private void initParams() {
        setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.quickaction);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void initQuickAction(Context context, ThemeColorType themeColorType) {
        try {
            this.themeColorType = themeColorType;
            mRootView = new QuickactionItemVertical(context);
            mQuickActionLayout = (LinearLayout) mRootView.findViewById(QuickActionPkg.LAYOUT_QUICKACTION);
            mArrowTop = mRootView.findViewById(QuickActionPkg.QUICKACTION_ARROW_UP);
            mArrowBottom = mRootView.findViewById(QuickActionPkg.QUICKACTION_ARROW_BOTTOM);
            mArrowLeft = mRootView.findViewById(QuickActionPkg.QUICKACTION_ARROW_LEFT);
            mArrowRight = mRootView.findViewById(QuickActionPkg.QUICKACTION_ARROW_RIGHT);
            mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            setContentView(mRootView);
        } catch (Exception e) {
            Logger.L.error("init quick action error:", e);
        }
    }

    private class QuickactionItemVertical extends RelativeLayout {
        public QuickactionItemVertical(Context context) {
            super(context);
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(vgparam);
            this.setBackgroundColor(Color.TRANSPARENT);
            this.addView(new LayoutQuickaction(context));
            this.addView(new ArrowUp(context));
            this.addView(new ArrowBottom(context));
            this.addView(new ArrowLeft(context));
            this.addView(new ArrowRight(context));
        }

        private class LayoutQuickaction extends LinearLayout {
            public LayoutQuickaction(Context context) {
                super(context);
                RelativeLayout.LayoutParams rlparam = new RelativeLayout.LayoutParams(PixelUtils.dip2px(context, quickActionWidth), RelativeLayout.LayoutParams.WRAP_CONTENT);
                rlparam.setMargins(0, PixelUtils.dip2px(context, 15), 0, PixelUtils.dip2px(context, 15));
                this.setLayoutParams(rlparam);
                this.setBackgroundResource(quickActionBackgroundResource != 0 ? quickActionBackgroundResource : R.drawable.quickaction_bg);
                this.setOrientation(VERTICAL);
                this.setPadding(0, PixelUtils.dip2px(context, 8), 0, PixelUtils.dip2px(context, 8));
                this.setId(QuickActionPkg.LAYOUT_QUICKACTION);
            }
        }

        private class ArrowUp extends RelativeLayout {
            public ArrowUp(Context context) {
                super(context);
                LayoutParams rlparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                this.setLayoutParams(rlparam);
                this.setId(QuickActionPkg.QUICKACTION_ARROW_UP);

                if (themeColorType == ThemeColorType.WHITE) {
                    JustTriangleUp jtupone = new JustTriangleUp(context, Color.parseColor("#e6e6e6"));
                    LayoutParams jtuponeparam = new LayoutParams(PixelUtils.dip2px(context, 26), PixelUtils.dip2px(context, 13));
                    jtupone.setLayoutParams(jtuponeparam);
                    this.addView(jtupone);
                }

                JustTriangleUp jtuptwo = new JustTriangleUp(context, quickActionJustTriangleBackgroundColor != 0 ? quickActionJustTriangleBackgroundColor : Color.parseColor("#ffffff"));
                LayoutParams jtuptwoparam = new LayoutParams(PixelUtils.dip2px(context, 25), PixelUtils.dip2px(context, 12));
                jtuptwo.setLayoutParams(jtuptwoparam);
                this.addView(jtuptwo);
            }
        }

        private class ArrowBottom extends RelativeLayout {
            public ArrowBottom(Context context) {
                super(context);
                LayoutParams rlparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                rlparam.addRule(RelativeLayout.BELOW, QuickActionPkg.LAYOUT_QUICKACTION);
                this.setLayoutParams(rlparam);
                this.setId(QuickActionPkg.QUICKACTION_ARROW_BOTTOM);

                if (themeColorType == ThemeColorType.WHITE) {
                    JustTriangleDown jtdownone = new JustTriangleDown(context, Color.parseColor("#e6e6e6"));
                    LayoutParams jtdownoneparam = new LayoutParams(PixelUtils.dip2px(context, 26), PixelUtils.dip2px(context, 13));
                    jtdownone.setLayoutParams(jtdownoneparam);
                    this.addView(jtdownone);
                }

                JustTriangleDown jtdowntwo = new JustTriangleDown(context, quickActionJustTriangleBackgroundColor != 0 ? quickActionJustTriangleBackgroundColor : Color.parseColor("#ffffff"));
                LayoutParams jtdowntwoparam = new LayoutParams(PixelUtils.dip2px(context, 25), PixelUtils.dip2px(context, 12));
                jtdowntwo.setLayoutParams(jtdowntwoparam);
                this.addView(jtdowntwo);
            }
        }

        private class ArrowLeft extends RelativeLayout {
            public ArrowLeft(Context context) {
                super(context);
                LayoutParams rlparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                this.setLayoutParams(rlparam);
                this.setId(QuickActionPkg.QUICKACTION_ARROW_LEFT);

                if (themeColorType == ThemeColorType.WHITE) {
                    JustTriangleLeft jtleftone = new JustTriangleLeft(context, Color.parseColor("#e6e6e6"));
                    LayoutParams jtleftoneparam = new LayoutParams(PixelUtils.dip2px(context, 13), PixelUtils.dip2px(context, 26));
                    jtleftone.setLayoutParams(jtleftoneparam);
                    this.addView(jtleftone);
                }

                JustTriangleLeft jtlefttwo = new JustTriangleLeft(context, quickActionJustTriangleBackgroundColor != 0 ? quickActionJustTriangleBackgroundColor : Color.parseColor("#ffffff"));
                LayoutParams jtlefttwoparam = new LayoutParams(PixelUtils.dip2px(context, 12), PixelUtils.dip2px(context, 25));
                jtlefttwo.setLayoutParams(jtlefttwoparam);
                this.addView(jtlefttwo);
            }
        }

        private class ArrowRight extends RelativeLayout {
            public ArrowRight(Context context) {
                super(context);
                LayoutParams rlparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                rlparam.addRule(RelativeLayout.RIGHT_OF, QuickActionPkg.LAYOUT_QUICKACTION);
                this.setLayoutParams(rlparam);
                this.setId(QuickActionPkg.QUICKACTION_ARROW_RIGHT);

                if (themeColorType == ThemeColorType.WHITE) {
                    JustTriangleRight jtrightone = new JustTriangleRight(context, Color.parseColor("#e6e6e6"));
                    LayoutParams jtrightoneparam = new LayoutParams(PixelUtils.dip2px(context, 13), PixelUtils.dip2px(context, 26));
                    jtrightone.setLayoutParams(jtrightoneparam);
                    this.addView(jtrightone);
                }

                JustTriangleRight jtrighttwo = new JustTriangleRight(context, quickActionJustTriangleBackgroundColor != 0 ? quickActionJustTriangleBackgroundColor : Color.parseColor("#ffffff"));
                LayoutParams jtrighttwoparam = new LayoutParams(PixelUtils.dip2px(context, 12), PixelUtils.dip2px(context, 25));
                jtrighttwo.setLayoutParams(jtrighttwoparam);
                this.addView(jtrighttwo);
            }
        }
    }

    private Direction computeDisplayPosition(View anchor) {
        Direction showDirection = null;
        mAnchorLocations = new int[2];
        anchor.getLocationOnScreen(mAnchorLocations);
        mScreenWidth = mDefaultDisplay.getWidth();
        mScreenHeight = mDefaultDisplay.getHeight();
        mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int popupWidth = mRootView.getMeasuredWidth();
        int popupHeight = mRootView.getMeasuredHeight();
        boolean canShowTop = mAnchorLocations[1] - popupHeight > 0;
        boolean canShowBottom = mAnchorLocations[1] + anchor.getHeight()
                + popupHeight < mScreenHeight;
        boolean canShowRight = mAnchorLocations[0] + anchor.getWidth()
                + popupWidth < mScreenWidth;
        boolean canShowLeft = mAnchorLocations[0] - popupWidth > 0;
        if (!canShowTop && canShowBottom) {
            showDirection = Direction.BOTTOM;
        } else if (canShowTop && !canShowBottom) {
            showDirection = Direction.TOP;
        } else if (!canShowLeft && canShowRight) {
            showDirection = Direction.RIGHT;
        } else if (canShowLeft && !canShowRight) {
            showDirection = Direction.LEFT;
        }
        return showDirection;
    }

    private int[] preShow(View anchor, Direction showDirection) {
        if (mBackground == null) {
            setBackgroundDrawable(new BitmapDrawable());
        } else {
            setBackgroundDrawable(mBackground);
        }
        if (showDirection == null) {
            return null;
        }
        int[] locations = new int[2];
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mQuickActionLayout
                .getLayoutParams();
        RelativeLayout.LayoutParams arrowParams = null;
        int arrowPos = 0;
        int anchorCenterX = 0;
        int anchorCenterY = 0;
        switch (showDirection) {
            case TOP:
                mArrowTop.setVisibility(View.INVISIBLE);
                mArrowBottom.setVisibility(View.VISIBLE);
                mArrowLeft.setVisibility(View.GONE);
                mArrowRight.setVisibility(View.GONE);
                params.setMargins(0, 0, 0, -6);
                mRootView.measure(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                anchorCenterX = mAnchorLocations[0] + anchor.getWidth() / 2;
                locations[0] = anchorCenterX - mRootView.getMeasuredWidth() / 2;
                locations[1] = mAnchorLocations[1] - mRootView.getMeasuredHeight();
                if (locations[0] <= 0) {
                    locations[0] = 0;
                } else if (locations[0] + mRootView.getMeasuredWidth() >= mScreenWidth) {
                    locations[0] = mScreenWidth - mRootView.getMeasuredWidth();
                }
                arrowParams = (RelativeLayout.LayoutParams) mArrowBottom
                        .getLayoutParams();
                arrowPos = anchorCenterX - locations[0]
                        - mArrowBottom.getMeasuredWidth() / 2;
                arrowParams.setMargins(arrowPos, 0, 0, 0);
                break;
            case BOTTOM:
                mArrowTop.setVisibility(View.VISIBLE);
                mArrowBottom.setVisibility(View.INVISIBLE);
                mArrowLeft.setVisibility(View.GONE);
                mArrowRight.setVisibility(View.GONE);
                params.setMargins(0, mArrowTop.getMeasuredHeight() - 3, 0, 0);
                mRootView.measure(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                anchorCenterX = mAnchorLocations[0] + anchor.getWidth() / 2;
                locations[0] = anchorCenterX - mRootView.getMeasuredWidth() / 2;
                locations[1] = mAnchorLocations[1] + anchor.getHeight();
                if (locations[0] <= 0) {
                    locations[0] = 0;
                } else if (locations[0] + mRootView.getMeasuredWidth() >= mScreenWidth) {
                    locations[0] = mScreenWidth - mRootView.getMeasuredWidth();
                }
                arrowParams = (RelativeLayout.LayoutParams) mArrowTop
                        .getLayoutParams();
                arrowPos = anchorCenterX - locations[0]
                        - mArrowTop.getMeasuredWidth() / 2;
                arrowParams.setMargins(arrowPos, 0, 0, 0);
                break;
            case LEFT:
                mArrowTop.setVisibility(View.INVISIBLE);
                mArrowBottom.setVisibility(View.INVISIBLE);
                mArrowLeft.setVisibility(View.GONE);
                mArrowRight.setVisibility(View.VISIBLE);
                params.setMargins(0, 0, -3, 0);
                mRootView.measure(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                anchorCenterY = mAnchorLocations[1] + anchor.getHeight() / 2;
                locations[0] = mAnchorLocations[0] - mRootView.getMeasuredWidth();
                locations[1] = anchorCenterY - mRootView.getMeasuredHeight() / 2;
                if (locations[1] <= 0) {
                    locations[1] = 0;
                } else if (locations[1] + mRootView.getMeasuredHeight() >= mScreenHeight) {
                    locations[1] = mScreenHeight - mRootView.getMeasuredHeight();
                }
                arrowParams = (RelativeLayout.LayoutParams) mArrowRight
                        .getLayoutParams();
                arrowPos = anchorCenterY - locations[1]
                        - mArrowRight.getMeasuredHeight() / 2;
                arrowParams.setMargins(0, arrowPos, 0, 0);
                break;
            case RIGHT:
                mArrowTop.setVisibility(View.INVISIBLE);
                mArrowBottom.setVisibility(View.INVISIBLE);
                mArrowLeft.setVisibility(View.VISIBLE);
                mArrowRight.setVisibility(View.GONE);
                params.setMargins(mArrowLeft.getMeasuredWidth() - 3, 0, 0, 0);
                mRootView.measure(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                anchorCenterY = mAnchorLocations[1] + anchor.getHeight() / 2;
                locations[0] = mAnchorLocations[0] + anchor.getWidth();
                locations[1] = anchorCenterY - mRootView.getMeasuredHeight() / 2;
                if (locations[1] <= 0) {
                    locations[1] = 0;
                } else if (locations[1] + mRootView.getMeasuredHeight() >= mScreenHeight) {
                    locations[1] = mScreenHeight - mRootView.getMeasuredHeight();
                }
                arrowParams = (RelativeLayout.LayoutParams) mArrowLeft
                        .getLayoutParams();
                arrowPos = anchorCenterY - locations[1]
                        - mArrowLeft.getMeasuredHeight() / 2;
                arrowParams.setMargins(0, arrowPos, 0, 0);
                break;
        }
        return locations;
    }
}
