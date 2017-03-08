package com.sjxz.moji_weather.mvp.view;

import android.view.animation.Animation;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/10/31.
 * Role:需要实现的动作以及数据填充抽象类
 */
public interface SplashView {

    void animateBackgroundImage(Animation animation);

    void initialzationViews(String versionName, String copyright, int backgroundResId);

    void readToMain();
}
