package com.sjxz.moji_weather.weather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/13.
 * Role:
 */
public abstract class BaseDrawer {


    public enum Type {
        CLEAR_D, RAIN_D, FOG_D, SNOW_D, CLOUDY_D,OVERCAST,CLEAR_N,SNOW_N,CLOUDY_N,OVERCAST_N,DEFAULT
    }


    public static BaseDrawer makeDrawerByType(Context context, Type type) {
        switch (type) {
            case CLEAR_D:
                return new SunnyDrawer(context);
            case RAIN_D:
                return new RainDayDrawer(context,false);
            case FOG_D:
                return new FogDayDrawer(context,false);
            case SNOW_D:
                return new SnowDayDrawer(context,false);
            case CLOUDY_D:
                return new CloudyDayDrawer(context,false);
            case OVERCAST:
                return new OvercastDayDrawer(context,false);
            case CLEAR_N:
                return new SunnyNightDrawer(context,true);
            case SNOW_N:
                return new SnowNightDrawer(context,true);
            case CLOUDY_N:
                return new CloudyNightDrawer(context,true);
            case OVERCAST_N:
                return new OvercastNightDrawer(context,true);
            case DEFAULT:
            default:
                return new SunnyDrawer(context);
        }
    }

    protected Context context;
    //	private float curPercent = 0f;
    private final float desity;
    protected int width, height;
    private GradientDrawable skyDrawable;
    protected final boolean isNight;

    public BaseDrawer(Context context, boolean isNight) {
        super();
        this.context = context;
        this.desity = context.getResources().getDisplayMetrics().density;
        this.isNight = isNight;
    }



    /**
     * @param canvas
     * @return needDrawNextFrame
     */
    public boolean draw(Canvas canvas, float alpha) {
        //long start = AnimationUtils.currentAnimationTimeMillis();
        boolean needDrawNextFrame = drawWeather(canvas, alpha);
//		if (needDrawNextFrame) {
//			curPercent += getFrameOffsetPercent();
//			if (curPercent > 1) {
//				curPercent = 0f;
//			}
//		}
        // Log.i(TAG, getClass().getSimpleName() + " drawWeather: "
        // + (AnimationUtils.currentAnimationTimeMillis() - start) + "ms");
        return needDrawNextFrame;
    }

    public abstract boolean drawWeather(Canvas canvas, float alpha);// return
    // needDrawNextFrame

    protected void setSize(int width, int height) {
        if(this.width != width && this.height != height){
            this.width = width;
            this.height = height;
            if (this.skyDrawable != null) {
                skyDrawable.setBounds(0, 0, width, height);
            }
        }

    }


    public static int convertAlphaColor(float percent,final int originalColor) {
        int newAlpha = (int) (percent * 255) & 0xFF;
        return (newAlpha << 24) | (originalColor & 0xFFFFFF);
    }
}
