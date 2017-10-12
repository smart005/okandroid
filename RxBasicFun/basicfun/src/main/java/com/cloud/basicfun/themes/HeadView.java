package com.cloud.basicfun.themes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.basicfun.R;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/4/5
 * @Description:默认白色,可自定义颜色
 * @Modifier:
 * @ModifyContent:
 */
public class HeadView extends FrameLayout {

    public enum HeadTypeMode {lmrTrans, lmr, generate}

    public enum HeadBackTypeMode {None, cancel, returnIcon, iconText}

    public enum HeadConfirmTypeMode {None, Confirm, Confirm1}

    private HeadTypeMode typeMode = HeadTypeMode.generate;
    private HeadBackTypeMode backTypeMode = HeadBackTypeMode.None;
    private HeadConfirmTypeMode confirmTypeMode = HeadConfirmTypeMode.None;
    private OnHeadBackClickListener onHeadBackClickListener = null;
    private OnHeadConfirmClickListener onHeadConfirmClickListener = null;

    public void setOnHeadBackClickListener(OnHeadBackClickListener listener) {
        this.onHeadBackClickListener = listener;
    }

    public void setOnHeadConfirmClickListener(OnHeadConfirmClickListener listener) {
        this.onHeadConfirmClickListener = listener;
    }

    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.HeadView, 0, 0);
        typeMode = HeadTypeMode.values()[array.getInt(R.styleable.HeadView_headViewType, HeadTypeMode.generate.ordinal())];
        if (typeMode == HeadTypeMode.lmr) {
            View view = View.inflate(context, R.layout.head_lmr_view, null);
            bindLmrView(view, array);
            this.addView(view);
        } else if (typeMode == HeadTypeMode.lmrTrans) {
            View view = View.inflate(context, R.layout.head_lmr_trans_view, null);
            bindLmrView(view, array);
            this.addView(view);
        } else {
            View view = View.inflate(context, R.layout.head_generate_view, null);
            bindGenerateView(view, array);
            this.addView(view);
        }
        array.recycle();
    }

    private void bindGenerateView(View view, TypedArray array) {
        String text = array.getString(R.styleable.HeadView_headViewText);
        TextView tv = (TextView) view.findViewById(R.id.subject_tv);
        tv.setText(text);
        int subjectTextColor = array.getColor(R.styleable.HeadView_headViewTextColor, 0);
        if (subjectTextColor != 0) {
            tv.setTextColor(subjectTextColor);
        }
        //返回
        backTypeMode = HeadBackTypeMode.values()[array.getInt(R.styleable.HeadView_headViewBackType, HeadBackTypeMode.None.ordinal())];
        View returnIb = view.findViewById(R.id.return_ib);
        if (backTypeMode == HeadBackTypeMode.cancel || backTypeMode == HeadBackTypeMode.returnIcon) {
            returnIb.setVisibility(VISIBLE);
        } else {
            returnIb.setVisibility(GONE);
        }
    }

    private void bindLmrView(View view, TypedArray array) {
        try {
            //文本
            String text = array.getString(R.styleable.HeadView_headViewText);
            TextView tv = (TextView) view.findViewById(R.id.subject_tv);
            tv.setText(text);
            int subjectTextColor = array.getColor(R.styleable.HeadView_headViewTextColor, 0);
            if (subjectTextColor != 0) {
                tv.setTextColor(subjectTextColor);
            }
            //返回
            backTypeMode = HeadBackTypeMode.values()[array.getInt(R.styleable.HeadView_headViewBackType, HeadBackTypeMode.None.ordinal())];
            View returnIb = view.findViewById(R.id.return_ib);
            returnIb.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHeadBackClickListener != null) {
                        onHeadBackClickListener.onBackClick(HeadBackTypeMode.returnIcon, v);
                    }
                }
            });
            TextView cancelTv = (TextView) view.findViewById(R.id.cancel_tv);
            cancelTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHeadBackClickListener != null) {
                        onHeadBackClickListener.onBackClick(HeadBackTypeMode.cancel, v);
                    }
                }
            });
            if (backTypeMode == HeadBackTypeMode.cancel) {
                returnIb.setVisibility(GONE);
                cancelTv.setVisibility(VISIBLE);
                cancelTv.setText(array.getString(R.styleable.HeadView_headViewCancelText));
                int cancelTextColor = array.getColor(R.styleable.HeadView_headViewCancelTextColor, 0);
                if (cancelTextColor != 0) {
                    cancelTv.setTextColor(cancelTextColor);
                }
            } else if (backTypeMode == HeadBackTypeMode.returnIcon) {
                returnIb.setVisibility(VISIBLE);
                cancelTv.setVisibility(GONE);
                int returnIcon = array.getResourceId(R.styleable.HeadView_headViewReturnIcon, 0);
                if (returnIcon != 0) {
                    ImageButton imageButton = (ImageButton) returnIb;
                    imageButton.setImageResource(returnIcon);
                }
            } else if (backTypeMode == HeadBackTypeMode.iconText) {
                returnIb.setVisibility(GONE);
                cancelTv.setVisibility(VISIBLE);
                cancelTv.setText(array.getString(R.styleable.HeadView_headViewCancelText));
                int cancelTextColor = array.getColor(R.styleable.HeadView_headViewCancelTextColor, 0);
                if (cancelTextColor != 0) {
                    cancelTv.setTextColor(cancelTextColor);
                }
                int returnIcon = array.getResourceId(R.styleable.HeadView_headViewReturnIcon, 0);
                if (returnIcon != 0) {
                    Drawable drawable = getContext().getResources().getDrawable(returnIcon);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    cancelTv.setCompoundDrawables(drawable, null, null, null);
                }
            } else {
                returnIb.setVisibility(GONE);
                cancelTv.setVisibility(GONE);
            }
            //背景
            View headContentRl = view.findViewById(R.id.head_content_rl);
            int bcolor = array.getColor(R.styleable.HeadView_headViewBackground, Color.WHITE);
            headContentRl.setBackgroundColor(bcolor);
            //确认文本1
            TextView confirmTextTv = (TextView) view.findViewById(R.id.submit_tv);
            confirmTextTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHeadConfirmClickListener != null) {
                        onHeadConfirmClickListener.onConfirmClick(HeadConfirmTypeMode.Confirm, v);
                    }
                }
            });
            String confirmText1 = array.getString(R.styleable.HeadView_headViewConfirmText);
            int confirmIcon1 = array.getResourceId(R.styleable.HeadView_headViewConfirmIcon, 0);
            if (TextUtils.isEmpty(confirmText1) && confirmIcon1 == 0) {
                confirmTextTv.setVisibility(GONE);
            } else {
                confirmTextTv.setVisibility(VISIBLE);
                if (confirmIcon1 != 0) {
                    Drawable leftDrawable = getContext().getResources().getDrawable(confirmIcon1);
                    leftDrawable.setBounds(0, 0, leftDrawable.getIntrinsicWidth(), leftDrawable.getIntrinsicHeight());
                    confirmTextTv.setCompoundDrawables(leftDrawable, null, null, null);
                }
                if (!TextUtils.isEmpty(confirmText1)) {
                    confirmTextTv.setText(confirmText1);
                    confirmTextTv.setCompoundDrawablePadding(PixelUtils.dip2px(getContext(), 6));
                    //文本1颜色
                    int Text1Color = array.getColor(R.styleable.HeadView_headViewConfirmTextColor, 0);
                    if (Text1Color != 0) {
                        confirmTextTv.setTextColor(Text1Color);
                    }
                    //文本背景
                    int text1Background = array.getResourceId(R.styleable.HeadView_headViewConfirmTextBackground, 0);
                    if (text1Background != 0) {
                        confirmTextTv.setBackgroundResource(text1Background);
                    }
                }
            }
            //确认文本2
            TextView confirmTextTv2 = (TextView) view.findViewById(R.id.submit2_tv);
            confirmTextTv2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHeadConfirmClickListener != null) {
                        onHeadConfirmClickListener.onConfirmClick(HeadConfirmTypeMode.Confirm1, v);
                    }
                }
            });
            int rightPadding = (int) array.getDimension(R.styleable.HeadView_headViewRightViewPadding, 0);
            if (rightPadding != 0) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) confirmTextTv2.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.setMargins(rightPadding, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
                }
            }
            String confirmText2 = array.getString(R.styleable.HeadView_headViewConfirmText1);
            int confirmIcon2 = array.getResourceId(R.styleable.HeadView_headViewConfirmIcon1, 0);
            if (TextUtils.isEmpty(confirmText2) && confirmIcon2 == 0) {
                confirmTextTv2.setVisibility(GONE);
            } else {
                confirmTextTv2.setVisibility(VISIBLE);
                if (confirmIcon2 != 0) {
                    Drawable leftDrawable = getContext().getResources().getDrawable(confirmIcon2);
                    leftDrawable.setBounds(0, 0, leftDrawable.getIntrinsicWidth(), leftDrawable.getIntrinsicHeight());
                    confirmTextTv2.setCompoundDrawables(leftDrawable, null, null, null);
                }
                if (!TextUtils.isEmpty(confirmText2)) {
                    confirmTextTv2.setText(confirmText2);
                    confirmTextTv2.setCompoundDrawablePadding(PixelUtils.dip2px(getContext(), 6));
                    //文本2颜色
                    int Text2Color = array.getColor(R.styleable.HeadView_headViewConfirmText1Color, 0);
                    if (Text2Color != 0) {
                        confirmTextTv2.setTextColor(Text2Color);
                    }
                    //文本背景
                    int text2Background = array.getResourceId(R.styleable.HeadView_headViewConfirmText1Background, 0);
                    if (text2Background != 0) {
                        confirmTextTv2.setBackgroundResource(text2Background);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("bind lrm view error:", e);
        }
    }

    public void setBackIcon(HeadBackTypeMode backTypeMode, int icon) {
        if (backTypeMode == HeadBackTypeMode.returnIcon) {
            ImageButton returnIb = (ImageButton) findViewById(R.id.return_ib);
            if (returnIb != null) {
                returnIb.setImageResource(icon);
            }
        } else if (backTypeMode == HeadBackTypeMode.iconText) {
            if (icon != 0) {
                TextView cancelTv = (TextView) findViewById(R.id.cancel_tv);
                if (cancelTv != null) {
                    Drawable drawable = getContext().getResources().getDrawable(icon);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    cancelTv.setCompoundDrawables(drawable, null, null, null);
                }
            }
        }
    }

    public void setCancelTextColor(int cancelTextColor) {
        if (cancelTextColor != 0) {
            TextView cancelTv = (TextView) findViewById(R.id.cancel_tv);
            cancelTv.setTextColor(cancelTextColor);
        }
    }

    public void setBackText(HeadBackTypeMode backTypeMode, String text) {
        if (backTypeMode == HeadBackTypeMode.cancel ||
                backTypeMode == HeadBackTypeMode.iconText) {
            TextView cancelTv = (TextView) findViewById(R.id.cancel_tv);
            if (cancelTv != null) {
                cancelTv.setText(text);
            }
        }
    }

    public void setSubjectText(String text) {
        TextView tv = (TextView) findViewById(R.id.subject_tv);
        if (tv != null) {
            tv.setText(text);
        }
    }

    public void setSubjectTextColor(int textColor) {
        if (textColor == 0) {
            return;
        }
        TextView tv = (TextView) findViewById(R.id.subject_tv);
        if (tv != null) {
            tv.setTextColor(textColor);
        }
    }

    public void setConfirmIcon(HeadConfirmTypeMode confirmTypeMode, int icon) {
        if (confirmTypeMode == HeadConfirmTypeMode.Confirm) {
            TextView tv = (TextView) findViewById(R.id.submit_tv);
            Drawable leftDrawable = getContext().getResources().getDrawable(icon);
            leftDrawable.setBounds(0, 0, leftDrawable.getIntrinsicWidth(), leftDrawable.getIntrinsicHeight());
            if (tv != null) {
                tv.setCompoundDrawables(leftDrawable, null, null, null);
            }
        } else if (confirmTypeMode == HeadConfirmTypeMode.Confirm1) {
            TextView tv = (TextView) findViewById(R.id.submit2_tv);
            Drawable leftDrawable = getContext().getResources().getDrawable(icon);
            leftDrawable.setBounds(0, 0, leftDrawable.getIntrinsicWidth(), leftDrawable.getIntrinsicHeight());
            if (tv != null) {
                tv.setCompoundDrawables(leftDrawable, null, null, null);
            }
        }
    }

    public void setConfirmText(HeadConfirmTypeMode confirmTypeMode, String text) {
        if (confirmTypeMode == HeadConfirmTypeMode.Confirm) {
            TextView tv = (TextView) findViewById(R.id.submit_tv);
            if (tv != null) {
                tv.setText(text);
            }
        } else if (confirmTypeMode == HeadConfirmTypeMode.Confirm1) {
            TextView tv = (TextView) findViewById(R.id.submit2_tv);
            if (tv != null) {
                tv.setText(text);
            }
        }
    }

    public void setConfirmVisibility(HeadConfirmTypeMode confirmTypeMode, int visibility) {
        if (confirmTypeMode == HeadConfirmTypeMode.Confirm) {
            TextView tv = (TextView) findViewById(R.id.submit_tv);
            if (tv != null) {
                tv.setVisibility(visibility);
            }
        } else if (confirmTypeMode == HeadConfirmTypeMode.Confirm1) {
            TextView tv = (TextView) findViewById(R.id.submit2_tv);
            if (tv != null) {
                tv.setVisibility(visibility);
            }
        }
    }

    public void setBackVisibility(HeadBackTypeMode headBackTypeMode, int visibility) {
        View returnIb = findViewById(R.id.return_ib);
        View cancelTv = findViewById(R.id.cancel_tv);
        if (backTypeMode == HeadBackTypeMode.cancel ||
                backTypeMode == HeadBackTypeMode.iconText) {
            if (returnIb != null) {
                returnIb.setVisibility(GONE);
            }
            if (cancelTv != null) {
                cancelTv.setVisibility(visibility);
            }
        } else if (backTypeMode == HeadBackTypeMode.returnIcon) {
            if (returnIb != null) {
                returnIb.setVisibility(visibility);
            }
            if (cancelTv != null) {
                cancelTv.setVisibility(GONE);
            }
        }
    }

    public void setConfirmPadding(HeadConfirmTypeMode confirmTypeMode, int left, int top, int right, int bottom) {
        if (confirmTypeMode == HeadConfirmTypeMode.Confirm) {
            TextView tv = (TextView) findViewById(R.id.submit_tv);
            if (tv != null) {
                tv.setPadding(left, top, right, bottom);
            }
        } else if (confirmTypeMode == HeadConfirmTypeMode.Confirm1) {
            TextView tv = (TextView) findViewById(R.id.submit2_tv);
            if (tv != null) {
                tv.setPadding(left, top, right, bottom);
            }
        }
    }

    public void setBackPadding(HeadBackTypeMode headBackTypeMode, int left, int top, int right, int bottom) {
        View returnIb = findViewById(R.id.return_ib);
        View cancelTv = findViewById(R.id.cancel_tv);
        if (headBackTypeMode == HeadBackTypeMode.cancel ||
                headBackTypeMode == HeadBackTypeMode.iconText) {
            if (returnIb != null) {
                returnIb.setVisibility(GONE);
            }
            if (cancelTv != null) {
                cancelTv.setPadding(left, top, right, bottom);
            }
        } else if (headBackTypeMode == HeadBackTypeMode.returnIcon) {
            if (returnIb != null) {
                returnIb.setPadding(left, top, right, bottom);
            }
            if (cancelTv != null) {
                cancelTv.setVisibility(GONE);
            }
        }
    }

    public View getConfirmView(HeadConfirmTypeMode confirmTypeMode) {
        if (confirmTypeMode == HeadConfirmTypeMode.Confirm1) {
            TextView tv = (TextView) findViewById(R.id.submit2_tv);
            return tv;
        } else {
            TextView tv = (TextView) findViewById(R.id.submit_tv);
            return tv;
        }
    }

    public View getBackView(HeadBackTypeMode headBackTypeMode) {
        if (headBackTypeMode == HeadBackTypeMode.cancel ||
                headBackTypeMode == HeadBackTypeMode.iconText) {
            View cancelTv = findViewById(R.id.cancel_tv);
            return cancelTv;
        } else {
            View returnIb = findViewById(R.id.return_ib);
            return returnIb;
        }
    }
}
