package com.sjxz.moji_weather.helper.moji.rainweather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.helper.moji.Actor;

import java.util.Random;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/28.
 * Role:
 */
public class RainStatus extends Actor {

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
    int randomInt;
    Random random=new Random();
    public RainStatus(Context context, int i) {
        super(context);
        box = new RectF();
        targetBox = new RectF();
        paint.setAntiAlias(true);
        position=i;
        randomInt=random.nextInt(80);
    }

    public int[] bitmaps = {
            R.drawable.raindrop_l,
            R.drawable.raindrop_m,
            R.drawable.raindrop_s,
            R.drawable.raindrop_xl,
            R.drawable.raindrop_l,
            R.drawable.raindrop_m,
            R.drawable.raindrop_s,
            R.drawable.raindrop_xl,
            R.drawable.raindrop_l,
            R.drawable.raindrop_m,
            R.drawable.raindrop_s,
            R.drawable.raindrop_xl,
    };


    @Override
    public void draw(Canvas canvas, int width, int height) {
        //逻辑处理
        //初始化
        if (!isInit) {
            initPositionX = width * 0.15F*(position%12==0?1:position%12);
//            initPositionY = height * 0.69F;
            initPositionY = height * 1.0F*0.05f*(randomInt%20==0?1:randomInt%20);
            frame = BitmapFactory.decodeResource(context.getResources(),bitmaps[randomInt%12]);
            box.set(0, 0, frame.getWidth(), frame.getHeight());
            matrix.reset();
            matrix.setScale(6f, 6f);
            matrix.setRotate(-20f);
            matrix.mapRect(targetBox, box);
            matrix.postTranslate(initPositionX - targetBox.width() / 2, initPositionY - targetBox.height() / 2);
            initX = initPositionX - targetBox.width() / 2;
            initY = initPositionY - targetBox.height() / 2;
            isInit = true;
            return;
        }
        //移动
//        matrix.postTranslate(0.5F, 0);
        matrix.postTranslate(10f, 30f);
        //边界处理
        matrix.mapRect(targetBox, box);
        if (targetBox.top > height) {
            matrix.postTranslate(0, -targetBox.bottom);
        }
        if (targetBox.left > width) {
            matrix.postTranslate(-targetBox.right, 0);
        }
        //绘制
        canvas.drawBitmap(frame, matrix, paint);
    }
}
