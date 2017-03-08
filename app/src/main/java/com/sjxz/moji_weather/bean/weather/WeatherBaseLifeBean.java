package com.sjxz.moji_weather.bean.weather;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/12/1.
 * Role:
 */
public class WeatherBaseLifeBean {
    private String desc;

    private String title;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    private int check;

    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
}
