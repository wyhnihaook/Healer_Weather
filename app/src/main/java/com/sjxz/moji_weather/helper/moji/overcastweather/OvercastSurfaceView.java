package com.sjxz.moji_weather.helper.moji.overcastweather;

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
 * Created by xz on 2016/11/25.
 * Role:
 */
public class OvercastSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private OvercastThread overcastThread;
    private SurfaceHolder surfaceHolder;

    public OvercastSurfaceView(Context context) {
        super(context);
    }

    public OvercastSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    public OvercastSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        if (overcastThread == null) {
            LogUtils.i("=surfaceCreated=");
            overcastThread = new OvercastThread(surfaceHolder, BaseApplication.getApplication());
            overcastThread.setWidth(width);
            overcastThread.setHeight(height);
            overcastThread.start();
        }else{
            overcastThread.getRenderHandler().sendEmptyMessage(2);
        }
    }

    int width;
    int height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.i("=renderThread!=null="+getMeasuredWidth());
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        if (overcastThread != null) {
            LogUtils.i("=renderThread!=null");
            overcastThread.setWidth(width);
            overcastThread.setHeight(height);
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
        overcastThread.getRenderHandler().sendEmptyMessage(1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("weather", "onFinishInflate");
    }
}
