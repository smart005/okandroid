package com.cloud.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/11/2
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class ImageUtils {

    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static Bitmap getBitmapForBg(Context context, Bitmap rawBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(rawBitmap.getWidth(), rawBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.setBitmap(bitmap);
        Matrix matrix = new Matrix();
        matrix.setScale(1, 1);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(rawBitmap, matrix, paint);
        return bitmap;
    }
}
