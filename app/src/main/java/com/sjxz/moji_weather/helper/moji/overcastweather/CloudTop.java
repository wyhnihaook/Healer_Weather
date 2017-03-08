package com.sjxz.moji_weather.helper.moji.overcastweather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.helper.moji.Actor;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/25.
 * Role:
 */
public class CloudTop  extends Actor {

    float initPositionX;
    float initPositionY;
    boolean isInit;
    Bitmap frame;
    RectF box;
    RectF targetBox;
    Paint paint = new Paint();
    float initX;
    float initY;

    protected CloudTop(Context context) {
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
            initPositionX = width * 0.5F;
//            initPositionY = height * 0.69F;
            initPositionY = height * 0.02F;
            frame = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            box.set(0, 0, frame.getWidth(), frame.getHeight());
            matrix.reset();
            matrix.setScale(2f, 2f);
            matrix.mapRect(targetBox, box);
            matrix.postTranslate(initPositionX - targetBox.width() / 2, initPositionY - targetBox.height() / 2);
            initX=initPositionX - targetBox.width() / 2;
            initY=initPositionY - targetBox.height() / 2;
            isInit = true;
            return;
        }
        //移动
//        matrix.postTranslate(0.5F, 0);
        matrix.postTranslate(0, -1.5F);
        //边界处理
        matrix.mapRect(targetBox, box);
        if (-targetBox.bottom > height/6) {
            matrix.postTranslate(0, -targetBox.top*1.2f);
        }
        //绘制
        canvas.drawBitmap(frame, matrix, paint);
    }
}
