package com.sjxz.moji_weather.helper.moji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.andview.refreshview.utils.LogUtilss;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/30.
 * Role:
 */
public class SurfacePlaceHolder extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;

    public SurfacePlaceHolder(Context context) {
        this(context, null);
    }

    public SurfacePlaceHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtilss.i("SurfacePlaceHolder----surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        LogUtilss.i("SurfacePlaceHolder----surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtilss.i("SurfacePlaceHolder----surfaceDestroyed");
    }
}
