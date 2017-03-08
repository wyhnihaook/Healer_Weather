package com.sjxz.moji_weather.helper.moji.rainweather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.helper.moji.Actor;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/25.
 * Role:
 */
public class RainCloudRight extends Actor {

    float initPositionX;
    float initPositionY;
    boolean isInit;
    Bitmap frame;
    RectF box;
    RectF targetBox;
    Paint paint = new Paint();

    public RainCloudRight(Context context) {
        super(context);
        box = new RectF();
        targetBox = new RectF();
        paint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas, int width, int height) {
        //逻辑处理
        //初始化
        if (!isInit) {
            Log.d("weather", "cloud init");
            initPositionX = width * 0.758F;
//            initPositionY = height * 0.69F;
            initPositionY = height * 0.11F;
            frame = BitmapFactory.decodeResource(context.getResources(), R.mipmap.moderate_rain_cloud1);
            box.set(0, 0, frame.getWidth(), frame.getHeight());
            matrix.reset();
            matrix.setScale(2f, 2f);
            matrix.mapRect(targetBox, box);
            matrix.postTranslate(initPositionX - targetBox.width() / 2, initPositionY - targetBox.height() / 2);
            isInit = true;
            return;
        }
        //移动
//        matrix.postTranslate(0.5F, 0);
        matrix.postTranslate(1.5F, 0);
        //边界处理
        matrix.mapRect(targetBox, box);
        if (targetBox.left > width) {
            matrix.postTranslate(-targetBox.right, 0);
        }
        //绘制
        canvas.drawBitmap(frame, matrix, paint);
    }
}