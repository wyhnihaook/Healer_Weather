package com.sjxz.moji_weather.mvp.presenter.impl;

import android.content.Context;
import android.view.animation.Animation;

import com.sjxz.moji_weather.mvp.interactor.SplashInteractor;
import com.sjxz.moji_weather.mvp.interactor.impl.SplashInteractorImpl;
import com.sjxz.moji_weather.mvp.presenter.Presenter;
import com.sjxz.moji_weather.mvp.view.SplashView;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/10/31.
 * Role:
 */
public class SplashPresenterImpl implements Presenter {

    private Context context;

    private SplashView splashView;

    private SplashInteractor splashInteractor;

    public SplashPresenterImpl(Context context, SplashView splashView) {
        this.context = context;
        this.splashView = splashView;

        splashInteractor=new SplashInteractorImpl();
    }

    @Override
    public void initialzation() {
        splashView.initialzationViews(splashInteractor.getVersionName(context)
                ,splashInteractor.getCopyRight(context)
                ,splashInteractor.getBackgroundImageResId());

        Animation animation=splashInteractor.getBackgroundImageAnimation(context);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashView.readToMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splashView.animateBackgroundImage(animation);
    }
}
