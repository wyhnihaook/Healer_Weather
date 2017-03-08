package com.sjxz.moji_weather.rx.api;

import com.sjxz.moji_weather.bean.weather.ShowApiWeather;
import com.sjxz.moji_weather.rx.ShowApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/8.
 * Role:
 */
public interface WeatherService {

    String BASE_URL = ApiInterface.API;

    @GET(ApiInterface.WEATHER_URL)
    @Headers("apikey: " + ApiInterface.API_KEY)
    Observable<ShowApiResponse<ShowApiWeather>> getWeatherData(
            @Query("area") String area,
            @Query("needMoreDay") String needMoreDay,
            @Query("needIndex") String needIndex,
            @Query("needAlarm") String needAlarm,
            @Query("need3HourForcast") String need3HourForcast);
}
