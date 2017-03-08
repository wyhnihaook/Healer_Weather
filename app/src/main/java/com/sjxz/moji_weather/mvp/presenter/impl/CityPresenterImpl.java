package com.sjxz.moji_weather.mvp.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.sjxz.moji_weather.mvp.interactor.impl.CityInteractorImpl;
import com.sjxz.moji_weather.mvp.presenter.MainPresenter;
import com.sjxz.moji_weather.mvp.view.CityView;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/15.
 * Role:
 */
public class CityPresenterImpl implements MainPresenter{

    private Context context;
    private CityView cityView;

    private CityInteractorImpl cityInteractor;


    public CityPresenterImpl(Context context, CityView cityView) {
        this.context = context;
        this.cityView = cityView;
        cityInteractor=new CityInteractorImpl();
    }

    @Override
    public void initialMain() {
        cityView.addCity(cityInteractor.getCityList(context));
    }


    public void deleteCity(String cityName){
        if(cityInteractor.getCityList(context).size()==1){
            Toast.makeText(context,"至少保留一个地区",Toast.LENGTH_SHORT).show();
            return ;
        }
        for(String city:cityInteractor.getCityList(context)){
            if(city.equals(cityName)){
                cityInteractor.deleteCity(context,cityName);
                break;
            }
        }
    }

    public void addCity(String cityName){
        cityInteractor.addCity(context,cityName);
    }
}
