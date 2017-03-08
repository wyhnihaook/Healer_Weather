package com.sjxz.moji_weather;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/9/19.
 * Role:启动时首先运行的注册类
 */
public class BaseApplication extends Application{

    private static BaseApplication context;

    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();

        this.context=this;
//        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration("/SimplifyReader/Images/"));
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }

    public static BaseApplication getApplication(){
        return context;
    }
}

