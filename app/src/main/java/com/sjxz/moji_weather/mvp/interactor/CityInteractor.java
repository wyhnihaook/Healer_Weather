package com.sjxz.moji_weather.mvp.interactor;

import android.content.Context;

import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/15.
 * Role:1.获取需要的数据 2.删除数据操作
 */
public interface CityInteractor {
    List<String> getCityList(Context context);

    void deleteCity(Context context,String cityName);

    void addCity(Context context,String cityName);
}

