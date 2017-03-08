package com.sjxz.moji_weather.helper.moji.snowweather;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.andview.refreshview.utils.LogUtils;
import com.sjxz.moji_weather.BaseApplication;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/28.
 * Role:
 */
public class SnowSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SnowThread snowThread;
    private SurfaceHolder surfaceHolder;

    public SnowSurfaceView(Context context) {
        super(context);
    }

    public SnowSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    public SnowSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("weather", "surfaceCreated");
        if (snowThread == null) {
            LogUtils.i("=surfaceCreated=");
            snowThread = new SnowThread(surfaceHolder, BaseApplication.getApplication());
            snowThread.setWidth(width);
            snowThread.setHeight(height);
            snowThread.start();
        }else{
            snowThread.getRenderHandler().sendEmptyMessage(2);
        }
    }

    int width;
    int height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.i("=renderThread!=null="+getMeasuredWidth());
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        if (snowThread != null) {
            LogUtils.i("=renderThread!=null");
            snowThread.setWidth(width);
            snowThread.setHeight(height);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("weather", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("weather", "surfaceDestroyed");
        snowThread.getRenderHandler().sendEmptyMessage(1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("weather", "onFinishInflate");
    }
}
