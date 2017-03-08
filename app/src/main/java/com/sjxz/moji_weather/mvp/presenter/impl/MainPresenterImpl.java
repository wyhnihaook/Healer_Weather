package com.sjxz.moji_weather.mvp.presenter.impl;

import android.content.Context;

import com.sjxz.moji_weather.mvp.interactor.MainInteractor;
import com.sjxz.moji_weather.mvp.interactor.impl.MainInteractorImpl;
import com.sjxz.moji_weather.mvp.presenter.MainPresenter;
import com.sjxz.moji_weather.mvp.view.MainView;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/8.
 * Role:
 */
public class MainPresenterImpl implements MainPresenter{

    private Context context;

    private MainInteractor mainInteractor;

    private MainView mainView;

    public MainPresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;//在Activity中实例化数据
        this.context = context;

        mainInteractor= new MainInteractorImpl();
    }

    @Override
    public void initialMain() {
        mainView.intialCityName(mainInteractor.initialCity(context));
        mainView.animAlpha(mainInteractor.initialAnim(context));
        mainView.initialTime(mainInteractor.getTodayTime());
    }
}
