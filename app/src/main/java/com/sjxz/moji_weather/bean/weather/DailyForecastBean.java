package com.sjxz.moji_weather.bean.weather;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/30.
 * Role:
 */
public class DailyForecastBean {

    //当天的最高温度
    private String temperature_max;
    //当天的最低温度
    private String temperature_min;

    //时间年月日
    private String date;
    //时间周
    private String weekday;

    //早晨天气情况
    private String day_weather;
    //晚上天气情况
    private String night_weather;

    //风向
    private String cloud_direction;
    //风速
    private String cloud_speed;

    //紫外线情况
    private String air_quality;

    public String getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(String temperature_max) {
        this.temperature_max = temperature_max;
    }

    public String getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(String temperature_min) {
        this.temperature_min = temperature_min;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getDay_weather() {
        return day_weather;
    }

    public void setDay_weather(String day_weather) {
        this.day_weather = day_weather;
    }

    public String getNight_weather() {
        return night_weather;
    }

    public void setNight_weather(String night_weather) {
        this.night_weather = night_weather;
    }

    public String getCloud_direction() {
        return cloud_direction;
    }

    public void setCloud_direction(String cloud_direction) {
        this.cloud_direction = cloud_direction;
    }

    public String getCloud_speed() {
        return cloud_speed;
    }

    public void setCloud_speed(String cloud_speed) {
        this.cloud_speed = cloud_speed;
    }

    public String getAir_quality() {
        return air_quality;
    }

    public void setAir_quality(String air_quality) {
        this.air_quality = air_quality;
    }
}
