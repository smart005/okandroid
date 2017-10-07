package com.cloud.core.reply;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
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
 * @CreateTime:2016/6/12
 * @Description: 回复文本视图(from 回复 to :content);若to为空则显示from内容(from :content);
 * <p/>
 * ReplyTextView commentRtv = new ReplyTextView(this);
 * commentRtv.setTextColor(this.getResources().getColor(R.color.reply_content_color));
 * commentRtv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
 * commentRtv.setFrom("from");
 * commentRtv.setTo("to");
 * commentRtv.setOnReplyLinkClickListener(new ReplyLinkClickListener<T>() {
 * @Override public void onReplyLinkClick(T obj, ReplyLinkTarget replyLinkTarget) {
 * <p/>
 * }
 * });
 * commentRtv.setText(commentRtv.getContent("content", object));
 * layoutRow.addView(commentRtv);
 */
public class ReplyTextView extends TextView {

    /**
     * 当前回复人
     */
    private String from = "";
    /**
     * @回复人
     */
    private String to = "";
    /**
     * 链接文本颜色(默认#576b95)
     */
    private String linkColor = "#576b95";

    private ReplyLinkClickListener replyLinkClickListener = null;

    public ReplyTextView(Context context) {
        super(context);
        init(true);
    }

    public ReplyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(false);
    }

    public ReplyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(false);
    }

    private void init(boolean flag) {
        if (flag) {
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(vgparam);
        }
        this.setMovementMethod(LinkMovementMethod.getInstance());
        this.setHighlightColor(getResources().getColor(android.R.color.transparent));
    }

    /**
     * 设置当前回复人
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 设置@回复人
     *
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 设置链接文本颜色(默认#576b95)
     *
     * @param linkColor
     */
    public void setLinkColor(String linkColor) {
        this.linkColor = linkColor;
    }

    public void setOnReplyLinkClickListener(ReplyLinkClickListener listener) {
        this.replyLinkClickListener = listener;
    }

    /**
     * 获取回复内容
     *
     * @param content
     */
    public <T> SpannableStringBuilder getContent(String content, T obj) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        try {
            if (TextUtils.isEmpty(this.from) || TextUtils.isEmpty(content)) {
                return ssb;
            }
            ssb.append(from);
            ReplyPersonClickableSpan replyPersonClickableSpanByFrom = new ReplyPersonClickableSpan(obj, ReplyLinkTarget.From);
            ssb.setSpan(replyPersonClickableSpanByFrom, 0, from.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (!TextUtils.isEmpty(this.to)) {
                ssb.append("回复");
                int startTo = ssb.length();
                ssb.append(this.to);
                ReplyPersonClickableSpan replyPersonClickableSpanByTo = new ReplyPersonClickableSpan(obj, ReplyLinkTarget.To);
                ssb.setSpan(replyPersonClickableSpanByTo, startTo, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            ssb.append("：");
            ssb.append(content);
        } catch (Exception e) {
            Logger.L.error("set reply content error:", e);
        }
        return ssb;
    }

    private class ReplyTextViewRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Layout layout = getLayout();
                float spacing = getLineSpacingExtra();
                float spacingmult = getLineSpacingMultiplier();
                float ms = spacing * spacingmult;
                int rowcount = layout.getLineCount();
                int height = (int) (ms * (rowcount - 1) + getTextSize() * rowcount);
                setHeight(height);
            } catch (Exception e) {
                Logger.L.error("replay ", e);
            }
        }
    }

    private class ReplyPersonClickableSpan<T> extends ClickableSpan {

        private T obj;
        private ReplyLinkTarget replyLinkTarget = ReplyLinkTarget.None;

        public ReplyPersonClickableSpan(T obj, ReplyLinkTarget replyLinkTarget) {
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
            ds.setColor(Color.parseColor(linkColor));
            ds.setUnderlineText(false);
        }
    }
}
