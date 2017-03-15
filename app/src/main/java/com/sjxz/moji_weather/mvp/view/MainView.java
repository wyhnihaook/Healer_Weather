package com.sjxz.moji_weather.mvp.view;

import android.view.animation.AlphaAnimation;

import com.sjxz.moji_weather.helper.WeatherService;

import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/8.
 * Role:主界面需要的View的操作
 */
public interface MainView {
    //1.动画效果 2.界面赋值（城市初始化）
    void animAlpha(AlphaAnimation alphaAnimation);

    void intialCityName(List<String> cityNames);

    void initialTime(String time);

    void initNotify(WeatherService weatherService);
}
