package com.sjxz.moji_weather.bean.weather;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/23.
 * Role:
 */
public class Now {

    private AqiDetail aqiDetail;

    private String weather_code;

    private String wind_direction;

    private String temperature_time;

    private String wind_power;

    private int aqi;

    private String sd;

    private String weather_pic;

    private String weather;

    private String temperature;

    public void setAqiDetail(AqiDetail aqiDetail){
        this.aqiDetail = aqiDetail;
    }
    public AqiDetail getAqiDetail(){
        return this.aqiDetail;
    }
    public void setWeather_code(String weather_code){
        this.weather_code = weather_code;
    }
    public String getWeather_code(){
        return this.weather_code;
    }
    public void setWind_direction(String wind_direction){
        this.wind_direction = wind_direction;
    }
    public String getWind_direction(){
        return this.wind_direction;
    }
    public void setTemperature_time(String temperature_time){
        this.temperature_time = temperature_time;
    }
    public String getTemperature_time(){
        return this.temperature_time;
    }
    public void setWind_power(String wind_power){
        this.wind_power = wind_power;
    }
    public String getWind_power(){
        return this.wind_power;
    }
    public void setAqi(int aqi){
        this.aqi = aqi;
    }
    public int getAqi(){
        return this.aqi;
    }
    public void setSd(String sd){
        this.sd = sd;
    }
    public String getSd(){
        return this.sd;
    }
    public void setWeather_pic(String weather_pic){
        this.weather_pic = weather_pic;
    }
    public String getWeather_pic(){
        return this.weather_pic;
    }
    public void setWeather(String weather){
        this.weather = weather;
    }
    public String getWeather(){
        return this.weather;
    }
    public void setTemperature(String temperature){
        this.temperature = temperature;
    }
    public String getTemperature(){
        return this.temperature;
    }
}
