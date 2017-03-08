package com.sjxz.moji_weather.helper.moji.fogweather;

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
 * Created by xz on 2016/11/28.
 * Role:
 */
public class FogTop extends Actor {

    float initPositionX;
    float initPositionY;
    boolean isInit;
    Bitmap frame;
    RectF box;
    RectF targetBox;
    Paint paint = new Paint();
    float initX;
    float initY;

    int position=0;

    public FogTop(Context context, int i) {
        super(context);
        box = new RectF();
        targetBox = new RectF();
        paint.setAntiAlias(true);
        position=i;
    }

    public int[] bitmaps = {
            R.drawable.sand_1,
            R.drawable.sand_2,
            R.drawable.sand_3,
            R.drawable.sand_4,
            R.drawable.sand_5,
            R.drawable.sand_6,
            R.drawable.sand_7,
    };


    @Override
    public void draw(Canvas canvas, int width, int height) {
        //逻辑处理
        //初始化
        if (!isInit) {
            initPositionX = width * 0.15F*position;
//            initPositionY = height * 0.69F;
            initPositionY = height * 1.0F*0.166f*position;
            frame = BitmapFactory.decodeResource(context.getResources(), bitmaps[position]);
            box.set(0, 0, frame.getWidth(), frame.getHeight());
            matrix.reset();
            matrix.setScale(3f, 3f);
            matrix.mapRect(targetBox, box);
            matrix.postTranslate(initPositionX - targetBox.width() / 2, initPositionY - targetBox.height() / 2);
            initX = initPositionX - targetBox.width() / 2;
            initY = initPositionY - targetBox.height() / 2;
            isInit = true;
            return;
        }
        //移动
//        matrix.postTranslate(0.5F, 0);
        matrix.postTranslate(0, -1.5F);
        //边界处理
        matrix.mapRect(targetBox, box);
        if (-targetBox.bottom > frame.getHeight()) {
            matrix.postTranslate(0, -targetBox.top * 30f);
        }
        //绘制
        canvas.drawBitmap(frame, matrix, paint);
    }
}
