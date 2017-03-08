package com.sjxz.moji_weather.view;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

/**
 * Created by baoyz on 14/10/29.
 */
public abstract class RefreshDrawable extends Drawable implements Drawable.Callback, Animatable {

    private PullRefreshLayout mRefreshLayout;

    public RefreshDrawable(Context context, PullRefreshLayout layout) {
        mRefreshLayout = layout;
    }

    public Context getContext(){
        return mRefreshLayout != null ? mRefreshLayout.getContext() : null;
    }

    public PullRefreshLayout getRefreshLayout(){
        return mRefreshLayout;
    }


    public abstract void setPercent(float percent);

    public abstract void setColorSchemeColors(int[] colorSchemeColors);

    public abstract void offsetTopAndBottom(int offset);


    /**
     * callback回调是用于重绘界面，注册，移除，固定书写方式invalidateDrawable,scheduleDrawable,unscheduleDrawable
     * */
    @Override
    public void invalidateDrawable(Drawable who) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    //使窗口支持透明度
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }
}
