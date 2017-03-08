package com.sjxz.moji_weather.mvp.view;


import com.sjxz.moji_weather.bean.weather.DailyForecastBean;
import com.sjxz.moji_weather.bean.weather.ShowApiWeather;
import com.sjxz.moji_weather.bean.weather.ShowApiWeatherNormalInner;
import com.sjxz.moji_weather.bean.weather.WeatherBaseLifeBean;

import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/22.
 * Role:界面的数据操作
 */
public interface RxWeatherView extends IBaseView{

    void onSuccess(ShowApiWeather data);//网络请求成功

    void initialTime(String time);//时间赋值

    void initialLifeData(List<WeatherBaseLifeBean> lifeBeanList);//生活相关

    void initialSevenDay(List<DailyForecastBean> dailyForecastBeanList);//近七天情况

    void initialTodayStatus(ShowApiWeatherNormalInner weather);//今天的天气情况

    void initialViewData(ShowApiWeather data, List<ShowApiWeatherNormalInner> showApiWeatherNormalInnerList);//View界面数据初始化

}