package com.sjxz.moji_weather.helper.moji.fogweather;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.andview.refreshview.utils.LogUtilss;
import com.sjxz.moji_weather.BaseApplication;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/28.
 * Role:
 */
public class FogSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private FogThread fogThread;
    private SurfaceHolder surfaceHolder;

    public FogSurfaceView(Context context) {
        super(context);
    }

    public FogSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    public FogSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.RGBA_8888);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtilss.i("surfaceCreated");
        if (fogThread == null) {
            fogThread = new FogThread(surfaceHolder, BaseApplication.getApplication());
            fogThread.setWidth(width);
            fogThread.setHeight(height);
            fogThread.start();
        }else{
            fogThread.getRenderHandler().sendEmptyMessage(2);
        }
    }

    int width;
    int height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtilss.i("onMeasure");
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        if (fogThread != null) {
            LogUtilss.i("=renderThread!=null");
            fogThread.setWidth(width);
            fogThread.setHeight(height);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtilss.i("surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtilss.i("surfaceDestroyed");
        fogThread.getRenderHandler().sendEmptyMessage(1);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtilss.i("onFinishInflate");
    }
}
