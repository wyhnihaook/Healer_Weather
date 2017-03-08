package com.sjxz.moji_weather.mvp.interactor.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.mvp.interactor.SplashInteractor;

import java.util.Calendar;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/10/31.
 * Role:
 */
public class SplashInteractorImpl implements SplashInteractor {

    @Override
    public int getBackgroundImageResId() {
        int resId=0;
        Calendar calendar=Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour>=6&&hour<=12){
            resId= R.mipmap.morning;
        }else if(hour>12&& hour <= 18){
            resId=R.mipmap.afternoon;
        }else{
            resId=R.mipmap.night;
        }
        return resId;
    }

    @Override
    public String getCopyRight(Context context) {
        return context.getResources().getString(R.string.splash_copyright);
    }

    @Override
    public String getVersionName(Context context) {
        String versionName=null;
        try {
            versionName=context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.format(context.getResources().getString(R.string.splash_version),versionName);
    }

    @Override
    public Animation getBackgroundImageAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.splash);
    }
}
