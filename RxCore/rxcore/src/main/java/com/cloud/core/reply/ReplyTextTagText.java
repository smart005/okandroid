package com.cloud.core.reply;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/9/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ReplyTextTagText extends TextView {

    /**
     * 链接文本颜色(默认#576b95)
     */
    private String linkColor = "#576b95";
    /**
     * 回复内容
     */
    private CharSequence replyContent = "";
    /**
     * @内容
     */
    private CharSequence atContent = "";
    /**
     * @标签
     */
    private String atTag = "";

    private OnReplyTagLinkClickListener onReplyTagLinkClickListener = null;

    public ReplyTextTagText(Context context) {
        super(context);
        init(true);
    }

    public ReplyTextTagText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(false);
    }

    public ReplyTextTagText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(false);
    }

    private void init(boolean flag) {
        this.setMovementMethod(LinkMovementMethod.getInstance());
        this.setHighlightColor(getResources().getColor(android.R.color.transparent));
    }

    public void setOnReplyTagLinkClickListener(OnReplyTagLinkClickListener listener) {
        this.onReplyTagLinkClickListener = listener;
    }

    /**
     * 设置链接文本颜色(默认#576b95)
     *
     * @param linkColor
     */
    public void setLinkColor(String linkColor) {
        this.linkColor = linkColor;
    }

    /**
     * 获取回复内容
     */
    public CharSequence getReplyContent() {
        return replyContent;
    }

    /**
     * 设置回复内容
     *
     * @param replyContent
     */
    public void setReplyContent(CharSequence replyContent) {
        this.replyContent = replyContent;
    }

    /**
     * 获取@内容
     */
    public CharSequence getAtContent() {
        return atContent;
    }

    /**
     * 设置@内容
     *
     * @param atContent
     */
    public void setAtContent(CharSequence atContent) {
        this.atContent = atContent;
    }

    /**
     * 获取@标签
     */
    public String getAtTag() {
        if (atTag == null) {
            atTag = "";
        }
        return atTag;
    }

    /**
     * 设置@标签
     *
     * @param atTag
     */
    public void setAtTag(String atTag) {
        this.atTag = atTag;
    }

    /**
     * 获取回复内容
     *
     * @param content
     */
    public <T> SpannableStringBuilder getContent(T obj) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        try {
            if (TextUtils.isEmpty(replyContent)) {
                return ssb;
            }
            ssb.append(replyContent);
            if (!TextUtils.isEmpty(atTag) && !TextUtils.isEmpty(atContent)) {
                ReplyPersonClickableSpan replyPersonClickableSpanByFrom = new ReplyPersonClickableSpan(obj);
                int startat = ssb.length();
                ssb.append(atTag);
                ssb.setSpan(replyPersonClickableSpanByFrom, startat, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.append("：");
                ssb.append(atContent);
            }
        } catch (Exception e) {
            Logger.L.error("set reply content error:", e);
        }
        return ssb;
    }

    private class ReplyPersonClickableSpan<T> extends ClickableSpan {

        private T obj;

        public ReplyPersonClickableSpan(T obj) {
            this.obj = obj;
        }

        @Override
        public void onClick(View widget) {
            if (onReplyTagLinkClickListener != null) {
                onReplyTagLinkClickListener.onReplyTagLinkClick(obj);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor(linkColor));
            ds.setUnderlineText(false);
        }
    }
}
