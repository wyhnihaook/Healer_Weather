package com.sjxz.moji_weather.bean.weather;

import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/22.
 * Role:
 */
public class ShowApiWeather {


    public ShowApiWeatherNormalInner f6;

    public ShowApiWeatherNormalInner f7;
    public ShowApiWeatherNormalInner f1;

    public ShowApiWeatherNormalInner getF6() {
        return f6;
    }

    public void setF6(ShowApiWeatherNormalInner f6) {
        this.f6 = f6;
    }

    public ShowApiWeatherNormalInner getF7() {
        return f7;
    }

    public void setF7(ShowApiWeatherNormalInner f7) {
        this.f7 = f7;
    }

    public ShowApiWeatherNormalInner getF1() {
        return f1;
    }

    public void setF1(ShowApiWeatherNormalInner f1) {
        this.f1 = f1;
    }

    public ShowApiWeatherNormalInner getF3() {
        return f3;
    }

    public void setF3(ShowApiWeatherNormalInner f3) {
        this.f3 = f3;
    }

    public ShowApiWeatherNormalInner getF2() {
        return f2;
    }

    public void setF2(ShowApiWeatherNormalInner f2) {
        this.f2 = f2;
    }

    public ShowApiWeatherNormalInner getF4() {
        return f4;
    }

    public void setF4(ShowApiWeatherNormalInner f4) {
        this.f4 = f4;
    }

    public ShowApiWeatherNormalInner getF5() {
        return f5;
    }

    public void setF5(ShowApiWeatherNormalInner f5) {
        this.f5 = f5;
    }

    public ShowApiWeatherNormalInner f3;
    public ShowApiWeatherNormalInner f2;
    public ShowApiWeatherNormalInner f4;
    public ShowApiWeatherNormalInner f5;

    public CityInfo cityInfo;

    public List<AlarmList> alarmList ;

    public Now now;

    private String time;

    private int ret_code;

    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setRet_code(int ret_code){
        this.ret_code = ret_code;
    }
    public int getRet_code(){
        return this.ret_code;
    }

    public void setNow(Now now){
        this.now = now;
    }
    public Now getNow(){
        return this.now;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public void setAlarmList(List<AlarmList> alarmList){
        this.alarmList = alarmList;
    }
    public List<AlarmList> getAlarmList(){
        return this.alarmList;
    }




//    public ShowApiWeatherNormalInner f1;//后一天的天气预报
//    public ShowApiWeatherNowInner now;//现在的天气预报
//
//    public class ShowApiWeatherNormalInner {
//        public String day;//日期
//        public String air_press;//气压
//        public String sun_begin_end;//白天持续时间
//        public String weekday;//星期几
//        public String ziwaixian;//紫外线
//        //白天
//        public String day_air_temperature;//气温
//        public String day_weather;//天气“晴雨”
//        public String day_weather_code;//天气代码
//        public String day_weather_pic;//天气图片
//        public String day_wind_direction;//风向
//        public String day_wind_power;//风力
//        //晚上
//        public String night_air_temperature;
//        public String night_weather;
//        public String night_weather_code;
//        public String night_weather_pic;
//        public String night_wind_direction;
//        public String night_wind_power;
//    }
//
//    public class ShowApiWeatherNowInner {
//        public String aqi;//污染指数
//        public String sd;//湿度
//        public String temperature;//气温
//        public String temperature_time;//气温时间
//        public String weather;//天气“晴雨”
//        public String weather_code;//天气代码
//        public String weather_pic;//天气图片
//        public String wind_direction;//风向
//        public String wind_power;//风力
//    }
}
