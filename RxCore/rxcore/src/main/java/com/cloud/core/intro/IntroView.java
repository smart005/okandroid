package com.cloud.core.intro;

import android.content.Context;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/12
 * @Description: 仿朋友圈文本描述视图
 * @Modifier:
 * @ModifyContent:
 */
public class IntroView extends RelativeLayout {

    /**
     * intro view properties
     */
    private IntroViewProperties introViewProperties = null;
    /**
     * intro文本
     */
    private CharSequence text = null;
    /**
     * 标记文本(默认-详情)
     */
    private CharSequence tagText = "详情";
    /**
     * 是否初始化tag
     */
    private boolean initTagFlag = false;

    private int atForegroundColor = 0;

    public IntroView(Context context) {
        super(context);
        init(true);
    }

    public IntroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(false);
    }

    public IntroView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(false);
    }

    /**
     * intro view properties
     *
     * @param properties
     */
    public void setIntroViewProperties(IntroViewProperties properties) {
        this.introViewProperties = properties == null ? new IntroViewProperties() : properties;
    }

    public void setAtForegroundColor(int atForegroundColor) {
        this.atForegroundColor = atForegroundColor;
    }

    /**
     * 设置intro文本
     *
     * @param text
     */
    public void setText(CharSequence text) {
        if (text == null) {
            return;
        }
        this.text = text;
        try {
            TextIntroView textIntroView = (TextIntroView) findViewById(IntroPck.TEXT_INTRO_VIEW_ID);
            textIntroView.setAtForegroundColor(atForegroundColor);
            textIntroView.build(this.introViewProperties, this.text);
            textIntroView.post(new IntroTextRunnable(textIntroView));
        } catch (Exception e) {
            Logger.L.error("set intro view text error:", e);
        }
    }

    /**
     * 标记文本(默认-详情)
     *
     * @param text
     */
    public void setTagText(CharSequence text) {
        if (text == null) {
            return;
        }
        this.tagText = text;
        try {
            this.initTagFlag = true;
            MoreTagView moreTagView = (MoreTagView) findViewById(IntroPck.MORE_TAG_VIEW_ID);
            moreTagView.build(this.introViewProperties, this.tagText);
            moreTagView.post(new Runnable() {
                @Override
                public void run() {
                    reAdjustView(null);
                }
            });
        } catch (Exception e) {
            this.initTagFlag = false;
            Logger.L.error("set tag text error:", e);
        }
    }

    private void init(boolean flag) {
        try {
            if (flag) {
                ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                this.setLayoutParams(vgparam);
            }
            this.initTagFlag = false;
            //intro文本
            TextIntroView textIntroView = new TextIntroView(getContext());
            this.addView(textIntroView);
            //more文本
            MoreTagView moreTagView = new MoreTagView(getContext());
            this.addView(moreTagView);
        } catch (Exception e) {
            Logger.L.error("intro view init error:", e);
        }
    }

    private class IntroTextRunnable implements Runnable {

        private TextIntroView textIntroView = null;

        public IntroTextRunnable(TextIntroView textIntroView) {
            this.textIntroView = textIntroView;
        }

        @Override
        public void run() {
            reAdjustView(textIntroView);
        }
    }

    /**
     * 重新调整视图
     */
    private void reAdjustView(TextIntroView textIntroView) {
        try {
            if ((this.text == null || this.tagText == null) && this.text.length() <= this.tagText.length()) {
                return;
            }
            if (textIntroView == null) {
                textIntroView = (TextIntroView) findViewById(IntroPck.TEXT_INTRO_VIEW_ID);
            }
            int currTextLineCount = textIntroView.getLineCount();
            if (currTextLineCount > introViewProperties.getLines()) {
                currTextLineCount = introViewProperties.getLines();
            }
            Layout tivl = textIntroView.getLayout();
            //more width
            int width = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            int height = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            MoreTagView moreTagView = (MoreTagView) findViewById(IntroPck.MORE_TAG_VIEW_ID);
            if (!initTagFlag) {
                moreTagView.build(introViewProperties, this.tagText);
            }
            moreTagView.measure(width, height);
            int morewidth = moreTagView.getMeasuredWidth();
            String ellipsis = "......";
            if (introViewProperties.getLastLineChars() < 5) {
                introViewProperties.setLastLineChars(5);
            }
            int ts = PixelUtils.sp2px(getContext(), introViewProperties.getTextSize());
            float leftpadd = tivl.getLineRight(currTextLineCount - 1);
            float espadd = this.getWidth() - leftpadd - introViewProperties.getTextSize() * 2 - 10;
            float lastpadd = 0;
            //每行字数
            int prenum = textIntroView.getWidth() / ts;
            float top = ts * currTextLineCount;
            if (espadd < morewidth) {
                if (introViewProperties.getLines() > currTextLineCount) {
                    moreTagView.setVisibility(View.GONE);
                } else {
                    float lastlinechars = (tivl.getLineRight(currTextLineCount - 1) - tivl.getLineLeft(currTextLineCount - 1)) / ts;
                    if (lastlinechars > introViewProperties.getLastLineChars()) {
                        CharSequence ntext = this.text.subSequence(0, prenum * (currTextLineCount - 1) + introViewProperties.getLastLineChars());
                        textIntroView.setText(ntext + ellipsis);
                        textIntroView.post(new ReTextIntroView(textIntroView, top, moreTagView, currTextLineCount));
                    } else {
                        moreTagView.setVisibility(View.GONE);
                    }
                }
            } else {
                if (introViewProperties.getLines() > currTextLineCount) {
                    moreTagView.setVisibility(View.GONE);
                } else {
                    float lastlinechars = (tivl.getLineRight(currTextLineCount - 1) - tivl.getLineLeft(currTextLineCount - 1)) / ts;
                    if (lastlinechars > introViewProperties.getLastLineChars()) {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.text);
                        CharSequence ntext = spannableStringBuilder.subSequence(0, prenum * (currTextLineCount - 1) + introViewProperties.getLastLineChars());
                        textIntroView.setText(ntext + ellipsis);
                        textIntroView.post(new ReTextIntroView(textIntroView, top, moreTagView, currTextLineCount));
                    } else {
                        moreTagView.setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("re adjust view error:", e);
        }
    }

    private class ReTextIntroView implements Runnable {

        private TextIntroView textIntroView = null;
        private float top = 0;
        private MoreTagView moreTagView = null;
        private int linenum = 0;

        public ReTextIntroView(TextIntroView textIntroView, float top, MoreTagView moreTagView, int linenum) {
            this.textIntroView = textIntroView;
            this.top = top;
            this.moreTagView = moreTagView;
            this.linenum = linenum;
        }

        @Override
        public void run() {
            locationTag(textIntroView, top, moreTagView, linenum);
        }
    }

    private void locationTag(TextIntroView textIntroView, float top, MoreTagView moreTagView, int linenum) {
        Layout tivl = textIntroView.getLayout();
        top = tivl.getLineBottom(linenum - 1) - PixelUtils.dip2px(getContext(), introViewProperties.getViewLineSpacing()) / 2 - PixelUtils.sp2px(getContext(), introViewProperties.getTagTextSize());
        float leftpadd = tivl.getLineRight(linenum - 1) + 10;
        LayoutParams mtvparam = (LayoutParams) moreTagView.getLayoutParams();
        if (mtvparam != null) {
            mtvparam.setMargins((int) leftpadd, (int) top, 0, 0);
            moreTagView.setLayoutParams(mtvparam);
        }
    }
}
