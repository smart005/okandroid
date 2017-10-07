package com.cloud.core.widgets;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.cloud.core.enums.ContextMenuIType;
import com.cloud.core.logger.Logger;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/9/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class EditTextIndentation extends EditText {

    /**
     * 内容
     */
    private CharSequence textContent = "";
    /**
     * 设置文本内容颜色
     */
    private int textContentColor = 0;
    /**
     * 是否首行缩进
     */
    private boolean firstLineIndentation = false;

    private String currTextContent = "";
    /**
     * 是否禁用复制粘贴
     */
    private boolean isDisableCopyPaste = true;

    private OnIndentationListener onIndentationListener = null;

    public void setOnIndentationListener(OnIndentationListener listener) {
        this.onIndentationListener = listener;
    }

    /**
     * 设置是否首行缩进
     *
     * @param firstLineIndentation
     */
    public void setFirstLineIndentation(boolean firstLineIndentation) {
        this.firstLineIndentation = firstLineIndentation;
    }

    public void setDisableCopyPaste(boolean isDisableCopyPaste) {
        this.isDisableCopyPaste = isDisableCopyPaste;
    }

    /**
     * 设置文本内容
     *
     * @param textContent
     */
    public void setTextContent(CharSequence textContent) {
        this.textContent = textContent;
    }

    public CharSequence getTextContent() {
        try {
            CharSequence text = this.getText();
            if (text.length() < 2) {
                return text;
            } else if (text.length() == 2) {
                if (TextUtils.equals(text.toString(), "空空")) {
                    return "";
                } else {
                    return text;
                }
            } else {
                String pretext = text.subSequence(0, 2).toString();
                if (TextUtils.equals(pretext, "空空")) {
                    return text.subSequence(2, this.length());
                } else {
                    return text;
                }
            }
        } catch (Exception e) {
            Logger.L.error("get text content error:", e);
        }
        return "";
    }

    public void setTextContentColor(int textContentColor) {
        this.textContentColor = textContentColor;
    }

    public EditTextIndentation(Context context) {
        super(context);
        this.addTextChangedListener(textWatcher);
    }

    public EditTextIndentation(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addTextChangedListener(textWatcher);
    }

    public EditTextIndentation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                String text = "";
                if (firstLineIndentation && !TextUtils.equals(s.toString(), currTextContent)) {
                    currTextContent = s.toString();
                    CharSequence temp = null;
                    if (start < 2) {
                        temp = s.subSequence(count + 2, s.length());
                    } else {
                        temp = s.subSequence(2, s.length());
                    }
                    text = temp + "#";
                    text = text.trim();
                    text = text.substring(0, text.length() - 1);
                    textContent = text;
                    setText(buildContent());
                    if (start < 2) {
                        setSelection(start);
                    } else {
                        setSelection(start + count);
                    }
                    if (onIndentationListener != null) {
                        onIndentationListener.onIndentationTextChanged(text, start, before, count);
                    }
                } else {
                    setSelection(start + count);
                    if (onIndentationListener != null) {
                        onIndentationListener.onIndentationTextChanged(s, start, before, count);
                    }
                }
            } catch (Exception e) {
                Logger.L.error("subject text changed process error:", e);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //http://blog.csdn.net/chunlongyuan/article/details/7715819
    @Override
    public boolean onTextContextMenuItem(int id) {
        try {
            if (isDisableCopyPaste) {
                if (id == ContextMenuIType.Cut.getValue() || id == ContextMenuIType.Copy.getValue()) {
                    int start = getSelectionStart();
                    int end = getSelectionEnd();
                    boolean flag = false;
                    if (start == 0) {
                        flag = true;
                    }
                    CharSequence seltext = getText().subSequence(start, end);
                    if (flag && firstLineIndentation) {
                        seltext = seltext.subSequence(2, seltext.length());
                        ClipboardManager myClipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                        ClipData myClip = ClipData.newPlainText("text", seltext);
                        myClipboard.setPrimaryClip(myClip);
                    }
                }
            } else {
                return super.onTextContextMenuItem(id);
            }
        } catch (Exception e) {
            Logger.L.error("text context menu item process error:", e);
        }
        return super.onTextContextMenuItem(id);
    }

    /**
     * 获取文本内容
     *
     * @param content
     */
    public SpannableStringBuilder buildContent() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        try {
            if (firstLineIndentation) {
                ssb.append("空空");
                ForegroundColorSpan emptyColorSpan = new ForegroundColorSpan(Color.TRANSPARENT);
                ssb.setSpan(emptyColorSpan, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (!TextUtils.isEmpty(textContent)) {
                int startat = ssb.length();
                ssb.append(textContent);
                if (textContentColor != 0) {
                    ForegroundColorSpan textColorSpan = new ForegroundColorSpan(textContentColor);
                    ssb.setSpan(textColorSpan, startat, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        } catch (Exception e) {
            Logger.L.error("set content error:", e);
        }
        return ssb;
    }
}
