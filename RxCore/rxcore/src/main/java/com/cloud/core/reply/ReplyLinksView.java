package com.cloud.core.reply;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloud.core.enums.ReplyLinkTarget;
import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/13
 * @Description:回复链接列表
 * @Modifier:
 * @ModifyContent:
 */
public class ReplyLinksView extends TextView {

    /**
     * 链接文本颜色(默认#576b95)
     */
    private int linkColor = 0;

    private ReplyLinkClickListener replyLinkClickListener = null;

    public ReplyLinksView(Context context) {
        super(context);
        init(true, null);
    }

    public ReplyLinksView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(false, attrs);
    }

    public ReplyLinksView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(false, attrs);
    }

    /**
     * 设置链接文本颜色(默认#576b95)
     *
     * @param linkColor
     */
    public void setLinkColor(String linkColor) {
        if (!TextUtils.isEmpty(linkColor)) {
            this.linkColor = Color.parseColor(linkColor);
        }
    }

    public void setOnReplyLinkClickListener(ReplyLinkClickListener listener) {
        this.replyLinkClickListener = listener;
    }

    private void init(boolean flag, AttributeSet attrs) {
        if (flag) {
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(vgparam);
        } else {
            int[] attrarray = new int[]{android.R.attr.textColor};
            TypedArray alst = getContext().obtainStyledAttributes(attrs, attrarray);
            linkColor = alst.getColor(0, Color.parseColor("#576b95"));
        }
        this.setMovementMethod(LinkMovementMethod.getInstance());
        this.setHighlightColor(getResources().getColor(android.R.color.transparent));
        this.setSingleLine(true);
        this.setEllipsize(TextUtils.TruncateAt.END);
    }

    public <T> void appendLink(String content, T obj) {
        try {
            if (TextUtils.isEmpty(content)) {
                return;
            }
            SpannableStringBuilder ssb = new SpannableStringBuilder(getText());
            int start = ssb.length();
            if (start > 0) {
                ssb.append(",");
            }
            ssb.append(content);
            int end = ssb.length();
            ReplyLinkClickableSpan replyLinkClickableSpan = new ReplyLinkClickableSpan(obj, ReplyLinkTarget.None);
            ssb.setSpan(replyLinkClickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            this.setText(ssb);
        } catch (Exception e) {
            Logger.L.error("set reply content error:", e);
        }
    }

    private class ReplyLinkClickableSpan<T> extends ClickableSpan {

        private T obj;
        private ReplyLinkTarget replyLinkTarget = ReplyLinkTarget.None;

        public ReplyLinkClickableSpan(T obj, ReplyLinkTarget replyLinkTarget) {
            this.obj = obj;
            this.replyLinkTarget = replyLinkTarget;
        }

        @Override
        public void onClick(View widget) {
            if (replyLinkClickListener != null) {
                replyLinkClickListener.onReplyLinkClick(obj, replyLinkTarget);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(linkColor);
            ds.setUnderlineText(false);
        }
    }
}
