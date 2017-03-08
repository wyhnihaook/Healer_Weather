package com.sjxz.moji_weather.helper.moji;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AnimationUtils;

import com.andview.refreshview.utils.LogUtilss;
import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.helper.moji.cloudweather.CloudyLeft;
import com.sjxz.moji_weather.helper.moji.cloudweather.CloudyRight;
import com.sjxz.moji_weather.helper.moji.cloudweather.CloudyTop;
import com.sjxz.moji_weather.helper.moji.cloudweather.StarRandomCloudNight;
import com.sjxz.moji_weather.helper.moji.fogweather.FogTop;
import com.sjxz.moji_weather.helper.moji.rainweather.RainCloudLeft;
import com.sjxz.moji_weather.helper.moji.rainweather.RainCloudRight;
import com.sjxz.moji_weather.helper.moji.rainweather.RainStatus;
import com.sjxz.moji_weather.helper.moji.snowweather.SnowOne;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/12/2.
 * Role:
 */
public class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    //    public enum Type {
//        DEFAULT, CLEAR_D, CLEAR_N, RAIN_D, RAIN_N, SNOW_D, SNOW_N, CLOUDY_D, CLOUDY_N,
//        OVERCAST_D, OVERCAST_N, FOG_D, FOG_N, HAZE_D, HAZE_N, SAND_D, SAND_N,
//        WIND_D, WIND_N, RAIN_SNOW_D, RAIN_SNOW_N, UNKNOWN_D, UNKNOWN_N
//    }
    public enum Type {
        CLEAR_D, RAIN_D, SNOW_D, FOG_D, CLOUDY_D,OVERCAST,CLEAR_N,SNOW_N,CLOUDY_N,OVERCAST_N
    }

    private SurfaceHolder surfaceHolder;
    int width;
    int height;
    private RenderHandler renderHandler;
    public Scene scene;
    public boolean isHomeCheck = false;
    private DrawThread mDrawThread;
    private Type position = Type.CLEAR_D;//当前的界面数据

    public void checkScence(Context context, Type type) {
        scene.clear();
        position = type;
        switch (type) {
            case CLEAR_D:
                //晴天

                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg_sunny_day));
                scene.add(new CloudLeft(context));
                scene.add(new CloudRight(context));
                scene.add(new SunShine(context));
                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }

                return;
            case RAIN_D:
                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_moderate_rain_day));
                scene.add(new RainCloudLeft(context));
                scene.add(new RainCloudRight(context));
                for (int i = 0; i < 80; i++) {
                    scene.add(new RainStatus(context, i));
                }
                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }
                return;
            case FOG_D:
                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_fog_and_haze));

                for (int i = 0; i < 7; i++) {
                    scene.add(new FogTop(context, i));
                }
                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }
                return;
            case SNOW_D:
                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_snow_day));

                for (int i = 0; i < 80; i++) {
                    scene.add(new SnowOne(context, i));
                }
                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }
                return;
            case CLOUDY_D:
                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_cloud));

                scene.add(new CloudyTop(context));
                scene.add(new CloudyLeft(context));
                scene.add(new CloudyRight(context));

                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }
                return;
            case OVERCAST:
                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg_overcast_day));
                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }
                return;
            case CLEAR_N:
                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sunny_night));
                scene.add(new PopularSunnyNight(context));
                scene.add(new StarSunnyNight(context));
                scene.add(new StarSunnyNightTwo(context));
                scene.add(new SeaWaterNight(context));
                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }
                return;
            case SNOW_N:
                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_snow_night));

                for (int i = 0; i < 80; i++) {
                    scene.add(new SnowOne(context, i));
                }
                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }
                return ;
            case CLOUDY_N:
                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_cloud_night));

                scene.add(new CloudRight(context));
                scene.add(new CloudLeft(context));

                for(int i=1;i<8;i++){
                    if(i<3){
                        scene.add(new StarRandomCloudNight(context,i,i));
                    }else if(i<6){
                        scene.add(new StarRandomCloudNight(context,9-i,(i+1)/2));
                    }else {
                        scene.add(new StarRandomCloudNight(context,i+1,i-5));
                    }

                }

                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }
                return ;
            case OVERCAST_N:
                scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_overcast_night));
                if (getRenderHandler() != null) {
                    getRenderHandler().sendEmptyMessage(2);
                }
                return;
        }
        scene.setBg(BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg_sunny_day));
        scene.add(new CloudLeft(context));
        scene.add(new CloudRight(context));
        scene.add(new SunShine(context));
        if (getRenderHandler() != null) {
            getRenderHandler().sendEmptyMessage(2);
        }
        return;
    }


    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        scene = new Scene(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);

//        mDrawThread1 = new DrawThread1();
//        mDrawThread1.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        setWidth(width);
        setHeight(height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtilss.i("surfaceCreated");
//        synchronized (mDrawThread1) {
//            mDrawThread1.mSurface = holder;
//            mDrawThread1.notify();
//        }

        if (mDrawThread == null) {
            mDrawThread = new DrawThread();
            setWidth(width);
            setHeight(height);
            mDrawThread.start();
        } else {
            getRenderHandler().sendEmptyMessage(2);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        getRenderHandler().sendEmptyMessage(1);

//        synchronized (mDrawThread1) {
//            mDrawThread1.mSurface = holder;
//            mDrawThread1.notify();
//            while (mDrawThread1.mActive) {
//                try {
//                    mDrawThread1.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        holder.removeCallback(this);
    }

    private class DrawThread extends Thread {

        @Override
        public void run() {
            //在非主线程使用消息队列
            Looper.prepare();
            renderHandler = new RenderHandler();
            renderHandler.sendEmptyMessage(0);
            Looper.loop();
        }
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
                    if (scene != null && scene.getWidth() != 0 && scene.getHeight() != 0) {
                        LogUtilss.i("遍历完毕的数据显示");
                        scene.isIterator = false;
                    }
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


    /**
     * 使用线程绘制界面
     * */
//    private DrawThread1 mDrawThread1;

    private class DrawThread1 extends Thread {
        // These are protected by the Thread's lock.
        SurfaceHolder mSurface;
        boolean mRunning;
        boolean mActive;
        boolean mQuit;

        @Override
        public void run() {
            while (true) {
                // Log.i(TAG, "DrawThread run..");
                // Synchronize with activity: block until the activity is ready
                // and we have a surface; report whether we are active or
                // inactive
                // at this point; exit thread when asked to quit.
                synchronized (this) {
                    while (mSurface == null || !mRunning) {
                        if (mActive) {
                            mActive = false;
                            notify();
                        }
                        if (mQuit) {
                            return;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    }

                    if (!mActive) {
                        mActive = true;
                        notify();
                    }
                    final long startTime = AnimationUtils.currentAnimationTimeMillis();
                    //TimingLogger logger = new TimingLogger("DrawThread");
                    // Lock the canvas for drawing.
                    Canvas canvas = mSurface.lockCanvas();
                    //logger.addSplit("lockCanvas");

                    if (canvas != null) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        // Update graphics.

                        if (scene.getWidth() != 0 && scene.getHeight() != 0) {
//                            draw();
                            scene.draw(canvas);
                        }
                        //logger.addSplit("drawSurface");
                        // All done!
                        mSurface.unlockCanvasAndPost(canvas);
                        //logger.addSplit("unlockCanvasAndPost");
                        //logger.dumpToLog();
                    } else {
                    }
                    final long drawTime = AnimationUtils.currentAnimationTimeMillis() - startTime;
                    final long needSleepTime = 16 - drawTime;
                    //Log.i(TAG, "drawSurface drawTime->" + drawTime + " needSleepTime->" + Math.max(0, needSleepTime));// needSleepTime);
                    if (needSleepTime > 0) {
                        try {
                            Thread.sleep(needSleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    public void onResume() {
        // Let the drawing thread resume running.
//        synchronized (mDrawThread1) {
//            mDrawThread1.mRunning = true;
//            mDrawThread1.notify();
//        }
    }

    public void onPause() {
        // Make sure the drawing thread is not running while we are paused.
//        synchronized (mDrawThread1) {
//            mDrawThread1.mRunning = false;
//            mDrawThread1.notify();
//        }
    }

    public void onDestroy() {
        // Make sure the drawing thread goes away.
//        synchronized (mDrawThread1) {
//            mDrawThread1.mQuit = true;
//            mDrawThread1.notify();
//        }
    }
}
