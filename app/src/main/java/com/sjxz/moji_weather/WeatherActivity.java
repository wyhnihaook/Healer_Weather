package com.sjxz.moji_weather;

import android.view.View;

import com.sjxz.moji_weather.base.BaseActivity;
import com.sjxz.moji_weather.helper.EventCenter;

public class WeatherActivity extends BaseActivity {

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionType() {
        return null;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_weather;
    }


    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}
