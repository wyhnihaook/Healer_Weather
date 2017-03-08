package com.sjxz.moji_weather.mvp.interactor.impl;


import com.sjxz.moji_weather.bean.weather.DailyForecastBean;
import com.sjxz.moji_weather.bean.weather.ShowApiWeather;
import com.sjxz.moji_weather.bean.weather.ShowApiWeatherNormalInner;
import com.sjxz.moji_weather.bean.weather.WeatherBaseLifeBean;
import com.sjxz.moji_weather.mvp.interactor.WeatherInteractor;
import com.sjxz.moji_weather.mvp.view.OnRxWeatherLisenter;
import com.sjxz.moji_weather.rx.RxManager;
import com.sjxz.moji_weather.rx.RxSubscriber;
import com.sjxz.moji_weather.rx.ShowApiResponse;
import com.sjxz.moji_weather.rx.api.WeatherService;
import com.sjxz.moji_weather.rx.net.NetManager;
import com.sjxz.moji_weather.util.SortComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/22.
 * Role:网络请求
 */
public class WeatherInteractorImpl implements WeatherInteractor {

    @Override
    public Observable<ShowApiResponse<ShowApiWeather>> getWeatherListData(String area, String needMoreDay, String needIndex,
                                                                          String needAlarm, String need3HourForcast) {

        WeatherService weatherService= NetManager.getInstance().create(WeatherService.class);

        return weatherService.getWeatherData( area,  needMoreDay,  needIndex,
                 needAlarm,  need3HourForcast);
    }



    @Override
    public String getTodayTime() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 EEEE");
        return format.format(date);
    }


    @Override
    public void getWeatherData( String area, String needMoreDay, String needIndex, String needAlarm, String need3HourForcast, final OnRxWeatherLisenter onRxWeatherLisenter) {
         RxManager.getInstance().doSubscribe2(getWeatherListData(
                area, needMoreDay, needIndex,
                needAlarm, need3HourForcast
        ), new RxSubscriber<ShowApiWeather>(true) {
            @Override
            protected void _onNext(ShowApiWeather showApiWeather) {
                if (showApiWeather != null) {
                    onRxWeatherLisenter.onSuccess(showApiWeather);
                }

            }

            @Override
            protected void _onError() {
                onRxWeatherLisenter.onError();
            }
        });
    }



    WeatherBaseLifeBean weatherBaseLifeBean;
    @Override
    public List<WeatherBaseLifeBean> getRecyclerData(List<ShowApiWeatherNormalInner> dataList) {
        List<WeatherBaseLifeBean> lifeBeen = new ArrayList<>();

        ShowApiWeatherNormalInner showApiWeatherNormalInner = dataList.get(0);//获取当天数据即可
        if (showApiWeatherNormalInner.getIndex().getBeauty() != null) {
            weatherBaseLifeBean = new WeatherBaseLifeBean();
            weatherBaseLifeBean.setCheck(0);
            weatherBaseLifeBean.setDesc(showApiWeatherNormalInner.getIndex().getBeauty().getDesc());
            weatherBaseLifeBean.setTitle(showApiWeatherNormalInner.getIndex().getBeauty().getTitle());
            lifeBeen.add(weatherBaseLifeBean);
        }
        if (showApiWeatherNormalInner.getIndex().getClothes() != null) {
            weatherBaseLifeBean = new WeatherBaseLifeBean();
            weatherBaseLifeBean.setCheck(1);
            weatherBaseLifeBean.setDesc(showApiWeatherNormalInner.getIndex().getClothes().getDesc());
            weatherBaseLifeBean.setTitle(showApiWeatherNormalInner.getIndex().getClothes().getTitle());
            lifeBeen.add(weatherBaseLifeBean);
        }
        if (showApiWeatherNormalInner.getIndex().getCold() != null) {
            weatherBaseLifeBean = new WeatherBaseLifeBean();
            weatherBaseLifeBean.setCheck(2);
            weatherBaseLifeBean.setDesc(showApiWeatherNormalInner.getIndex().getCold().getDesc());
            weatherBaseLifeBean.setTitle(showApiWeatherNormalInner.getIndex().getCold().getTitle());
            lifeBeen.add(weatherBaseLifeBean);
        }
        if (showApiWeatherNormalInner.getIndex().getComfort() != null) {
            weatherBaseLifeBean = new WeatherBaseLifeBean();
            weatherBaseLifeBean.setCheck(3);
            weatherBaseLifeBean.setDesc(showApiWeatherNormalInner.getIndex().getComfort().getDesc());
            weatherBaseLifeBean.setTitle(showApiWeatherNormalInner.getIndex().getComfort().getTitle());
            lifeBeen.add(weatherBaseLifeBean);
        }
        if (showApiWeatherNormalInner.getIndex().getGlass() != null) {
            weatherBaseLifeBean = new WeatherBaseLifeBean();
            weatherBaseLifeBean.setCheck(4);
            weatherBaseLifeBean.setDesc(showApiWeatherNormalInner.getIndex().getGlass().getDesc());
            weatherBaseLifeBean.setTitle(showApiWeatherNormalInner.getIndex().getGlass().getTitle());
            lifeBeen.add(weatherBaseLifeBean);
        }
        if (showApiWeatherNormalInner.getIndex().getSports() != null) {
            weatherBaseLifeBean = new WeatherBaseLifeBean();
            weatherBaseLifeBean.setCheck(5);
            weatherBaseLifeBean.setDesc(showApiWeatherNormalInner.getIndex().getSports().getDesc());
            weatherBaseLifeBean.setTitle(showApiWeatherNormalInner.getIndex().getSports().getTitle());
            lifeBeen.add(weatherBaseLifeBean);
        }
        if (showApiWeatherNormalInner.getIndex().getTravel() != null) {
            weatherBaseLifeBean = new WeatherBaseLifeBean();
            weatherBaseLifeBean.setCheck(6);
            weatherBaseLifeBean.setDesc(showApiWeatherNormalInner.getIndex().getTravel().getDesc());
            weatherBaseLifeBean.setTitle(showApiWeatherNormalInner.getIndex().getTravel().getTitle());
            lifeBeen.add(weatherBaseLifeBean);
        }
        if (showApiWeatherNormalInner.getIndex().getUv() != null) {
            weatherBaseLifeBean = new WeatherBaseLifeBean();
            weatherBaseLifeBean.setCheck(7);
            weatherBaseLifeBean.setDesc(showApiWeatherNormalInner.getIndex().getUv().getDesc());
            weatherBaseLifeBean.setTitle(showApiWeatherNormalInner.getIndex().getUv().getTitle());
            lifeBeen.add(weatherBaseLifeBean);
        }
        if (showApiWeatherNormalInner.getIndex().getWash_car() != null) {
            weatherBaseLifeBean = new WeatherBaseLifeBean();
            weatherBaseLifeBean.setCheck(8);
            weatherBaseLifeBean.setDesc(showApiWeatherNormalInner.getIndex().getWash_car().getDesc());
            weatherBaseLifeBean.setTitle(showApiWeatherNormalInner.getIndex().getWash_car().getTitle());
            lifeBeen.add(weatherBaseLifeBean);
        }
        return lifeBeen;
    }


    @Override
    public List<DailyForecastBean> setDailyForcastWeather(List<ShowApiWeatherNormalInner> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return null;
        }
        List<DailyForecastBean> dailyForecastBeenList = new ArrayList<>();
        //获取七天的信息内容
        for (ShowApiWeatherNormalInner data : dataList) {
            DailyForecastBean dailyForecastBean = new DailyForecastBean();

            dailyForecastBean.setDay_weather(data.getDay_weather());
            dailyForecastBean.setNight_weather(data.getNight_weather());

            dailyForecastBean.setAir_quality(data.getZiwaixian());
            dailyForecastBean.setCloud_direction(data.getDay_wind_direction());
            dailyForecastBean.setCloud_speed(data.getJiangshui());

            dailyForecastBean.setTemperature_max(data.getDay_air_temperature());
            dailyForecastBean.setTemperature_min(data.getNight_air_temperature());
            String newAllDate = data.getDay().substring(0, 4);
            String newDate = data.getDay().substring(4);
            String newYue = newDate.substring(0, 2);
            String newRi = newDate.substring(2);
            dailyForecastBean.setWeekday(newAllDate + "-" + newYue + "-" + newRi);
            dailyForecastBean.setDate(newYue + "/" + newRi);

            dailyForecastBeenList.add(dailyForecastBean);
        }

        return dailyForecastBeenList;
    }



    List<ShowApiWeatherNormalInner> weatherList;//按时间分配好List数据
    @Override
    public List<ShowApiWeatherNormalInner> getListDayWeather(ShowApiWeather data) {
        //说明第二次还是自动
        if (weatherList != null) {
            if(weatherList.get(0).getCityName().contains(data.getCityInfo().getC5())||data.getCityInfo().getC5().contains(weatherList.get(0).getCityName())
                    ||weatherList.get(0).getCityName().equals(data.getCityInfo().getC5())){
                //如果当前的市是和存储的地址数据一致就返回已经存储的数据
                return weatherList;
            }
        }

        weatherList = new ArrayList<>();
        if (data.getF1() != null && data.getF2() != null && data.getF3() != null
                && data.getF4() != null && data.getF5() != null && data.getF6() != null
                && data.getF7() != null) {
            weatherList.add(data.getF1());
            weatherList.add(data.getF2());
            weatherList.add(data.getF3());
            weatherList.add(data.getF4());
            weatherList.add(data.getF5());
            weatherList.add(data.getF6());
            weatherList.add(data.getF7());
            for(ShowApiWeatherNormalInner showApiWeatherNormalInner : weatherList){
                showApiWeatherNormalInner.setCityName(data.getCityInfo().getC5());
            }

            Comparator comp = new SortComparator();
            Collections.sort(weatherList, comp);
            return weatherList;
        }
        return null;
    }


}
