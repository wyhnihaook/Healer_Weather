package com.sjxz.moji_weather.mvp.view;

import com.sjxz.moji_weather.bean.weather.ShowApiWeather;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/9.
 * Role:专门用于接收数据返回类型
 */
public interface OnRxWeatherLisenter extends IBaseView{
    void onSuccess(ShowApiWeather data);//网络请求成功
}
