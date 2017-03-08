package com.sjxz.moji_weather.helper.moji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;

import com.sjxz.moji_weather.R;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/12/7.
 * Role:流星雨
 */
public class PopularSunnyNight extends Actor {

    float initPositionX;
    float initPositionY;
    boolean isInit;
    Bitmap frame;
    Bitmap star;
    RectF box;
    RectF targetBox;

    RectF starBox;
    RectF starTargetBox;
    Paint paint = new Paint();
    Paint paintStar = new Paint();
    float initX;
    float initY;

    public int paintAlpha=255;//透明度

    public int paintStarAlpha=0;

    private int number_of_times=6;
    private Canvas canvas;

    public boolean isStar=false;//星星的渐变标识
    public boolean isAdd=true;//渐变标识
    public boolean isOver=false;
    protected Matrix matrixStar = new Matrix();

    public PopularSunnyNight(Context context) {
        super(context);
        box = new RectF();
        targetBox = new RectF();

        //星星坐标
        starBox=new RectF();
        starTargetBox=new RectF();

        paintStar.setAntiAlias(true);
        paint.setAntiAlias(true);
    }




    @Override
    public void draw(final Canvas canvas, int width, int height) {
        //逻辑处理
        //初始化
        if (!isInit) {
            this.canvas=canvas;
            initPositionX = width *1F;
//            initPositionY = height * 0.69F;
            initPositionY = height * 0.0F;
            frame = BitmapFactory.decodeResource(context.getResources(), R.drawable.sunny_night_shooting_start);
            box.set(0, 0, frame.getWidth(), frame.getHeight());
            matrix.reset();
            matrix.setScale(2f, 2f);
            matrix.setRotate(45f);
            matrix.mapRect(targetBox, box);
            matrix.postTranslate(initPositionX , initPositionY - targetBox.height());
            initX = initPositionX + targetBox.width() /2;
            initY = initPositionY - targetBox.height() / 2;

            //星星初始化
            star = BitmapFactory.decodeResource(context.getResources(), R.drawable.sunny_night_star_l);
            starBox.set(0, 0, star.getWidth(), star.getHeight());

            matrixStar.reset();
            matrixStar.setScale(2f, 2f);
            matrixStar.mapRect(starTargetBox, starBox);
            matrixStar.setTranslate(  width- height/4-targetBox.bottom , height/4);
            isInit = true;
            paintStar.setAlpha(0);
            return;
        }
        //移动
        matrix.postTranslate(-10f, 10f);
        //边界处理
        matrix.mapRect(targetBox, box);
        if (targetBox.top > height/4) {
            //回退
            //消失.出现星闪一下,消失
            matrix.postTranslate(initX-targetBox.right, -targetBox.bottom);

            number_of_times--;
            if(number_of_times==0){
                number_of_times=6;
                isOver=false;
            }
        }
        if(number_of_times==5){
            if(targetBox.top > height/8&&targetBox.top < height/4){
                if(!isOver){
                    handler.sendEmptyMessage(0);
                }


            }else{
                paintAlpha=255;
                paint.setAlpha(255);
            }
        }else{

            paint.setAlpha(0);
        }

        //绘制
        canvas.drawBitmap(frame, matrix, paint);
        canvas.drawBitmap(star, matrixStar, paintStar);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:

                    //流行渐变

                    if(paintAlpha-20>0){
                        paintAlpha-=20;
                    }else{
                        paintAlpha=0;
                        isStar=true;
                        isAdd=true;
                        isOver=true;//代表结束一次循环操作
                    handler.sendEmptyMessage(1);
                    }
                    paint.setAlpha(paintAlpha);
                    break;

                case 1:
                    //星星渐变
                   //不用移动渐变死循环
                        if(isAdd){
                            if(paintStarAlpha+50<=254){
                                paintStarAlpha+=50;
                            }else {
                                paintStarAlpha=255;
                                isAdd=false;
                            }
                        }else{
                            if(paintStarAlpha-50>0){
                                paintStarAlpha-=50;
                            }else{
                                paintStarAlpha=0;
                                isStar=false;
                            }

                        }

                        paintStar.setAlpha(paintStarAlpha);
                    if(isStar){
                        handler.sendEmptyMessageDelayed(1,100);
                    }


                    break;
            }
        }
    };
}
