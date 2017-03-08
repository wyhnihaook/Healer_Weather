package com.sjxz.moji_weather.base;


import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.fragment.AllCityFragment;
import com.sjxz.moji_weather.fragment.SelectCityFragment;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/15.
 * Role:
 */
public enum SimpleBackPage {


    CITY(1, R.string.city, AllCityFragment.class),
    SELECTCITY(2, R.string.select_city, SelectCityFragment.class);//选择城市


    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }
}
