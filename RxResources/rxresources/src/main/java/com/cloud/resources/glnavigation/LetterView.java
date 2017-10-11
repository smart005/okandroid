/**
 * @Title: LetterView.java
 * @Description: 字母导航视图
 * @author: lijinghuan
 * @data: 2014-12-27 下午13:23:14
 */
package com.cloud.resources.glnavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LetterView extends View {
    /**
     * 触摸到view的时候的背景颜色
     **/
    public static final int COLOR_BG = 0xaae9e9e9;
    /**
     * 没触摸view的时候的背景颜色
     **/
    public static final int COLOR_NO_BG = 0x00000000;
    /**
     * 选中的字母的字体颜色
     **/
    public static final int COLOR_TEXT_SELECTED = 0xff386AB7;
    /**
     * 没选中的字母的字体颜色
     **/
    public static final int COLOR_TEXT_NORMAL = 0xffcdcdcd;
    /**
     * 字母的字体大小
     **/
    public static final int SIZE_TEXT = 32;
    /**
     * 字母表
     **/
    private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Paint paint;
    /**
     * 该View的宽
     **/
    private int width;
    /**
     * 该View的高
     **/
    private int height;
    /**
     * 单个字母的高
     **/
    private int singleHight;

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true); // 抗锯齿
        paint.setTextSize(SIZE_TEXT); // 设置字体大小
        paint.setFakeBoldText(true); // 设置文字为粗体
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (width == 0 || height == 0) {
            width = getWidth();
            height = getHeight();
            singleHight = height / letters.length();
        }

        for (int i = 0; i < letters.length(); i++) {
            if (currentSelectedIndex == i) {
                paint.setColor(COLOR_TEXT_SELECTED);
            } else {
                paint.setColor(COLOR_TEXT_NORMAL);
            }
            // paint.measureText(str) 测量str的宽
            float xPos = (width - paint.measureText(letters.charAt(i) + "")) / 2;
            float yPos = singleHight * i + singleHight;
            canvas.drawText(letters.charAt(i) + "", xPos, yPos, paint);
        }
    }

    private int currentSelectedIndex = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentSelectedIndex = (int) (event.getY() / singleHight);
        if (currentSelectedIndex < 0) {
            currentSelectedIndex = 0;
        }
        if (currentSelectedIndex > letters.length() - 1) {
            currentSelectedIndex = letters.length() - 1;
        }
        if (letterChangeListener != null) {
            // 回调
            letterChangeListener.onLetterChange(currentSelectedIndex);
        }
        invalidate();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(COLOR_BG);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(COLOR_NO_BG);
                if (letterChangeListener != null) {
                    letterChangeListener.onLetterChanged();
                }
                break;
        }
        return true;
    }

    private OnLetterChangeListener letterChangeListener;

    public void setOnLetterChangeListener(
            OnLetterChangeListener letterChangeListener) {
        this.letterChangeListener = letterChangeListener;
    }

    public interface OnLetterChangeListener {
        void onLetterChange(int selectedIndex);

        void onLetterChanged();
    }

    public void setSelectedIndex(int selectedIndex) {
        currentSelectedIndex = selectedIndex;
        invalidate();
    }
}
