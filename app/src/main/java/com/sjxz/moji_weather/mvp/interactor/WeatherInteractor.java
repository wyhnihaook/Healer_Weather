package com.sjxz.moji_weather.mvp.interactor;


import com.sjxz.moji_weather.bean.weather.DailyForecastBean;
import com.sjxz.moji_weather.bean.weather.ShowApiWeather;
import com.sjxz.moji_weather.bean.weather.ShowApiWeatherNormalInner;
import com.sjxz.moji_weather.bean.weather.WeatherBaseLifeBean;
import com.sjxz.moji_weather.mvp.view.OnRxWeatherLisenter;
import com.sjxz.moji_weather.rx.ShowApiResponse;

import java.util.List;

import rx.Observable;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/22.
 * Role:
 */
public interface WeatherInteractor {
    /**
     * 获取天气预报
     * @param area 地区名称，比如北京
     * @param needMoreDay 是否需要返回7天数据中的后4天。1为返回，0为不返回。
     * @param needIndex 是否需要返回指数数据，比如穿衣指数、紫外线指数等。1为返回，0为不返回。
     * @param needAlarm 是否需要天气预警。1为需要，0为不需要。
     * @param need3HourForcast 是否需要当天每3小时1次的天气预报列表。1为需要，0为不需要。
     */
    Observable<ShowApiResponse<ShowApiWeather>> getWeatherListData(String area, String needMoreDay, String needIndex,
                                                                   String needAlarm, String need3HourForcast);
    /**
     * 获取当天的日期
     * */
    String getTodayTime();

    /**
     * 进行数据请求
     * */
    void getWeatherData(String area, String needMoreDay, String needIndex,
                        String needAlarm, String need3HourForcast, OnRxWeatherLisenter onRxWeatherLisenter);

    /**
     * 生活相关数据整合
     * */
     List<WeatherBaseLifeBean> getRecyclerData(List<ShowApiWeatherNormalInner> dataList);

    /**
     * 近七天天气数据整合
     * */
    List<DailyForecastBean> setDailyForcastWeather(List<ShowApiWeatherNormalInner> dataList);

    /**
     * 对获取的天气时间进行排序
     * */
    List<ShowApiWeatherNormalInner> getListDayWeather(ShowApiWeather data);
}
