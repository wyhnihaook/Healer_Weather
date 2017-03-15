package com.sjxz.moji_weather.bean.weather;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/15.
 * Role:
 */
public class NotifyBean {

    String now_tmp;
    String now_range;
    String now_state;
    String city;
    int drawableId;

    public String getNow_tmp() {
        return now_tmp;
    }

    public void setNow_tmp(String now_tmp) {
        this.now_tmp = now_tmp;
    }

    public String getNow_range() {
        return now_range;
    }

    public void setNow_range(String now_range) {
        this.now_range = now_range;
    }

    public String getNow_state() {
        return now_state;
    }

    public void setNow_state(String now_state) {
        this.now_state = now_state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
