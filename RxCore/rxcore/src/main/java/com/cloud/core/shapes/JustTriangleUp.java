package com.cloud.core.shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-6 上午12:00:32
 * @Description: 剪头向上正三角形
 * @Modifier:
 * @ModifyContent:
 */
public class JustTriangleUp extends View {

    private Paint mpaint = null;
    private int width = 0;
    private int height = 0;
    private int justTriangleUpBackgroundColor = 0;

    public JustTriangleUp(Context context, int justTriangleUpBackgroundColor) {
        super(context);
        this.justTriangleUpBackgroundColor = justTriangleUpBackgroundColor;
        init();
    }

    public JustTriangleUp(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mpaint = new Paint();
        mpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mpaint.setAntiAlias(true);
        if (justTriangleUpBackgroundColor == 0) {
            Drawable mdrawable = getBackground();
            if (mdrawable instanceof ColorDrawable) {
                ColorDrawable mcd = (ColorDrawable) mdrawable;
                mpaint.setColor(mcd.getColor());
            }
            setBackgroundColor(0);
        } else {
            mpaint.setColor(justTriangleUpBackgroundColor);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        width = getWidth();
        height = getHeight();
        Path path = new Path();
        path.moveTo(width / 2, 0);
        path.lineTo(0, height);
        path.lineTo(width, height);
        path.close();
        canvas.drawPath(path, mpaint);
    }
}
