package com.sjxz.moji_weather.mvp.interactor;

import android.content.Context;
import android.view.animation.Animation;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/10/31.
 * Role:获取view所需要的数据
 */
public interface SplashInteractor {

    /**
     * 获取背景图片
     * */
    int getBackgroundImageResId();

    /**
     * 获取当前的版本数据
     * */
    String getCopyRight(Context context);

    /**
     * 获取当前的版本号
     * */
    String getVersionName(Context context);

    /**
     * 获取当前的动画
     * */
    Animation getBackgroundImageAnimation(Context context);

}
