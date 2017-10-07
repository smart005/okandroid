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
 * @CreateTime:2015-11-6 上午10:51:47
 * @Description:剪头向下正三角形
 * @Modifier:
 * @ModifyContent:
 */
public class JustTriangleDown extends View {

    private Paint mpaint = null;
    private int width = 0;
    private int height = 0;
    private int justTriangleUpBackgroundColor = 0;

    public JustTriangleDown(Context context, int justTriangleUpBackgroundColor) {
        super(context);
        this.justTriangleUpBackgroundColor = justTriangleUpBackgroundColor;
        init();
    }

    public JustTriangleDown(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mpaint = new Paint();
        mpaint.setStyle(Paint.Style.FILL);
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
        path.moveTo(0, 0);
        path.lineTo(width / 2, height);
        path.lineTo(width, 0);
        path.close();
        canvas.drawPath(path, mpaint);
    }

}
