package com.sjxz.moji_weather.mvp.interactor.impl;

import android.content.Context;

import com.andview.refreshview.utils.LogUtilss;
import com.sjxz.moji_weather.helper.EventCenter;
import com.sjxz.moji_weather.mvp.interactor.CityInteractor;
import com.sjxz.moji_weather.util.Constants;
import com.sjxz.moji_weather.util.SpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/15.
 * Role:
 */
public class CityInteractorImpl implements CityInteractor {
    @Override
    public List<String> getCityList(Context context) {
        List<String> list= SpUtils.getListObj(context, Constants.CITY_NAME);
        if(list!=null&&list.size()>0){
            return list;
        }
        list.add("杭州市");
        SpUtils.setListObj(context,list,Constants.CITY_NAME);
        return list;
    }

    @Override
    public void deleteCity(Context context,String cityName) {
        List<String> list= SpUtils.getListObj(context, Constants.CITY_NAME);
        list.remove(cityName);
        LogUtilss.i("删除后的长度="+list.size());
        SpUtils.setListObj(context,list,Constants.CITY_NAME);
        EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_DELETE_CITY_MAIN));
    }

    @Override
    public void addCity(Context context,String cityName) {
        List<String> list=  getCityList(context);
        list.add(cityName);
        SpUtils.setListObj(context,list, Constants.CITY_NAME);
        EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_DELETE_CITY_MAIN));
    }
}
