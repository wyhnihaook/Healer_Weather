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
 * Created by xz on 2016/12/6.
 * Role:
 */
public class SeaWaterNight extends Actor {

    float initPositionX;
    float initPositionY;
    boolean isInit;
    Bitmap frame;
    RectF box;
    RectF targetBox;
    Paint paint = new Paint();
    public int paintAlpha=50;//透明度

    public SeaWaterNight(Context context) {
        super(context);
        box = new RectF();
        targetBox = new RectF();
        paint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas, int width, int height) {

        if (!isInit) {
            Log.d("weather", "cloud init");
            initPositionX = width * 0.01F;
//            initPositionY = height * 0.69F;
            initPositionY = height * 0.7F;
            frame = BitmapFactory.decodeResource(context.getResources(), R.drawable.sunny_night_seawater);
            box.set(0, 0, frame.getWidth(), frame.getHeight());
            matrix.reset();
            matrix.setScale(2f, 2f);
            matrix.mapRect(targetBox, box);
            matrix.postTranslate(initPositionX - targetBox.width() / 2, initPositionY - targetBox.height() / 2);
            isInit = true;
            return;
        }
        matrix.postTranslate(1.5F, 0);
        //边界处理--不断移动中，将box（当前）的坐标赋值给targetBox
        matrix.mapRect(targetBox, box);
        //当距离过半的时候就是停止重新来过
        if (targetBox.right > width*2/3) {
            matrix.postTranslate(targetBox.left*3/2,0 );
        }

        //渐变透明
        //获取移动的距离---宽度---width*2/3
        //前1/3从模糊到清晰，后1/3从清晰到消失
        if(targetBox.right>0&&targetBox.right < (width*2/3-120)){

            if(targetBox.right-frame.getWidth()>0&&targetBox.right-frame.getWidth()<255){
                //移动的距离
                paintAlpha=(int)(targetBox.right-frame.getWidth())%255;
            }else if(targetBox.right-frame.getWidth()>255){
                paintAlpha=255;
            }else{
                //未负数的时候就是重置
                paintAlpha=0;
            }



        }else if(targetBox.right>0&&targetBox.right < width*2/3){
            if((int)(255-(targetBox.right-frame.getWidth())%255)>200){

            }else{
                paintAlpha=(int)(255-(targetBox.right-frame.getWidth())%255);
            }

        }

        paint.setAlpha(paintAlpha);


        //绘制
        canvas.drawBitmap(frame, matrix, paint);

    }


}
