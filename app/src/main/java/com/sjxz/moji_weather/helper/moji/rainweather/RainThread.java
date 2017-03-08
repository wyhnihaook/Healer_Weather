package com.sjxz.moji_weather.helper.moji.rainweather;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.helper.moji.Scene;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/25.
 * Role:
 */
public class RainThread extends Thread {

    private Context context;
    private SurfaceHolder surfaceHolder;
    private RenderHandler renderHandler;
    private Scene scene;
    public boolean isHomeCheck = false;

    public RainThread(SurfaceHolder surfaceHolder, Context context) {
        this.context = context;
        this.surfaceHolder = surfaceHolder;
        scene = new Scene(context);
        //add scene/actor
        scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_moderate_rain_day));
        scene.add(new RainCloudLeft(context));
        scene.add(new RainCloudRight(context));
        for(int i=0;i<80;i++){
            scene.add(new RainStatus(context,i));
        }

    }

    @Override
    public void run() {
        Log.d("weather", "run");
        //在非主线程使用消息队列
        Looper.prepare();
        renderHandler = new RenderHandler();
        renderHandler.sendEmptyMessage(0);
        Looper.loop();
    }

    public RenderHandler getRenderHandler() {
        return renderHandler;
    }

    public class RenderHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (!isHomeCheck) {
                        if (scene.getWidth() != 0 && scene.getHeight() != 0) {
                            draw();
                        }
                        renderHandler.sendEmptyMessage(0);
                    }
                    break;
                case 1:
                    isHomeCheck = true;
//                    Looper.myLooper().quit();
                    break;
                case 2:
                    isHomeCheck = false;
                    renderHandler.sendEmptyMessage(0);
//                    Looper.myLooper().quit();
                    break;
            }
        }
    }


    private void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            scene.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    public void setWidth(int width) {
        scene.setWidth(width);
    }

    public void setHeight(int height) {
        scene.setHeight(height);
    }
}
