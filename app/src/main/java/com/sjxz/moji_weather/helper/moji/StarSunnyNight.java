package com.sjxz.moji_weather.helper.moji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.sjxz.moji_weather.R;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/12/5.
 * Role:
 */
public class StarSunnyNight extends Actor {

    float initPositionX;
    float initPositionY;
    boolean isInit;
    Bitmap frame;
    RectF box;
    RectF targetBox;
    Paint paint = new Paint();
    public int paintAlpha=50;//透明度
    public boolean isAdd=true;//标识在加

    protected StarSunnyNight(Context context) {
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
            initPositionX = width * 0.6F;
//            initPositionY = height * 0.69F;
            initPositionY = height * 0.1F;
            frame = BitmapFactory.decodeResource(context.getResources(), R.drawable.sunny_night_star_l);
            box.set(0, 0, frame.getWidth(), frame.getHeight());
            matrix.reset();
            matrix.setScale(2f, 2f);
            matrix.mapRect(targetBox, box);
            matrix.postTranslate(initPositionX - targetBox.width() / 2, initPositionY - targetBox.height() / 2);
            isInit = true;
            return;
        }

//        matrix.postScale(2f, 2f);
//        LogUtils.i("targetBox.top="+targetBox.top+"frame.getWidth()="+frame.getWidth());
//        if (targetBox.right > frame.getWidth()) {
//            matrix.postTranslate(0, -targetBox.bottom);
//        }
        if(isAdd){
            if(paintAlpha<=254){
                paintAlpha+=10;
                if(paintAlpha>255){
                    paintAlpha=255;
                }
            }else {
                isAdd=false;
            }
        }else{
            if(paintAlpha>=50){
                paintAlpha-=10;
            }else{
                isAdd=true;
            }

        }

        paint.setAlpha(paintAlpha);

        //绘制
        canvas.drawBitmap(frame, matrix, paint);
    }
}