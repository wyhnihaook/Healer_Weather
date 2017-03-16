package com.sjxz.moji_weather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjxz.moji_weather.adapter.MxxFragmentsAdapter;
import com.sjxz.moji_weather.base.BaseActivity;
import com.sjxz.moji_weather.base.BaseLFragment;
import com.sjxz.moji_weather.base.SimpleBackPage;
import com.sjxz.moji_weather.bean.weather.NotifyBean;
import com.sjxz.moji_weather.fragment.WeatherFragment;
import com.sjxz.moji_weather.helper.EventCenter;
import com.sjxz.moji_weather.helper.MxxViewPager;
import com.sjxz.moji_weather.helper.UIHelper;
import com.sjxz.moji_weather.helper.WeatherService;
import com.sjxz.moji_weather.mvp.presenter.impl.MainPresenterImpl;
import com.sjxz.moji_weather.mvp.view.MainView;
import com.sjxz.moji_weather.util.Constants;
import com.sjxz.moji_weather.weather.BaseDrawer;
import com.sjxz.moji_weather.weather.DynamicWeatherView;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {


    public static MainActivity instance=null;

    @Bind(R.id.main_viewpager)
    MxxViewPager viewPager;

    @Bind(R.id.main_dynamicweatherview)
    DynamicWeatherView weatherView;

    @Bind(R.id.manual_location)
    ImageView manual_location;

    @Bind(R.id.tv_date)
    TextView tv_date;

    @Bind(R.id.view_bg)
    public View view_bg;

    private MainPresenterImpl mainPresenter;

    private String nowWeather="晴";

    private Long mExitTime=0L;

    private NotifyBean notifyBean;

    private WeatherService weatherService;
    /**
     * 默认的天气情况
     * */
    private BaseDrawer.Type mDrawerType = BaseDrawer.Type.CLEAR_D;
    private WeatherFragment fragment;//当前界面fragment
    private BaseLFragment[] fragments;
    private int tempPosition=0;

    //标识前台展示
    private boolean isForeground=true;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:

                    break;
                case 2:
                    break;

                case 3:
                    //天气定位
                    Calendar calendar=Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    switch (nowWeather) {

                        case "晴":

                            if(hour>=6&&hour<=18){
                                mDrawerType=BaseDrawer.Type.CLEAR_D;
                                switchWeatherView(mDrawerType);
                            }else {
                                mDrawerType=BaseDrawer.Type.CLEAR_N;
                                switchWeatherView(mDrawerType);
                            }

                            break;
                        case "阴":
                            if(hour>=6&&hour<=18){
                                mDrawerType=BaseDrawer.Type.OVERCAST;
                                switchWeatherView(mDrawerType);
                            }else {
                                mDrawerType=BaseDrawer.Type.OVERCAST_N;
                                switchWeatherView(mDrawerType);
                            }

                            break;
                        case "小雨":
                        case "中雨":
                        case "大雨":
                        case "阵雨":
                        case "雷阵雨":
                        case "暴雨":
                        case "大暴雨":
                        case "特大暴雨":
                        case "雷阵雨伴有冰雹":
                        case "雨夹雪":
                        case "冻雨":
                            mDrawerType=BaseDrawer.Type.RAIN_D;
                            switchWeatherView(mDrawerType);
                            break;
                        case "雪":
                        case "霜冻":
                        case "阵雪":
                        case "大雪":
                        case "中雪":
                        case "小雪":
                        case "暴风雪":
                        case "暴雪":
                        case "冰雹":
                            if(hour>=6&&hour<=18){
                                mDrawerType=BaseDrawer.Type.SNOW_D;
                                switchWeatherView(mDrawerType);
                            }else {
                                mDrawerType=BaseDrawer.Type.SNOW_N;
                                switchWeatherView(mDrawerType);
                            }

                            break;
                        case "少云":
                        case "多云":
                            if(hour>=6&&hour<=18){
                                mDrawerType=BaseDrawer.Type.CLOUDY_D;
                                switchWeatherView(mDrawerType);
                            }else {
                                mDrawerType=BaseDrawer.Type.CLOUDY_N;
                                switchWeatherView(mDrawerType);
                            }

                            break;

                        case "霾":
                        case "雾":
                        case "浮尘":
                        case "扬沙":
                        case "强沙尘暴":
                        case "沙尘暴":
                            mDrawerType=BaseDrawer.Type.FOG_D;
                            switchWeatherView(mDrawerType);
                            break;
                    }
                    break;

            }
        }
    };

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        int eventCode = eventCenter.getEventCode();
        switch (eventCode) {
            case Constants.EVENTBUS_CHANGE_WEATHER:
                if(eventCenter.getData()!=null){
                    notifyBean=(NotifyBean)eventCenter.getData();
                    nowWeather=notifyBean.getNow_state();
                    initNotifyData(notifyBean);
                }
                handler.sendEmptyMessage(3);
                break;
            case Constants.EVENTBUS_INIT:

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragment = (WeatherFragment) fragments[tempPosition];
                        fragment.postRefresh(mDrawerType);
                    }
                },200);
                break;
            case Constants.EVENTBUS_SECOND:
                //通知surfaceView层更新动画
                if(fragment.nowWeather!=null){
                    nowWeather=fragment.nowWeather;
                }
                handler.sendEmptyMessage(3);
                break;
            case Constants.EVENTBUS_CHECK:
                weatherView.setDrawerType((BaseDrawer.Type)eventCenter.getData());
                break;
            case Constants.EVENTBUS_DELETE_CITY_MAIN:
                mainPresenter.initialMain();
                break;
            case Constants.EVENTBUS_CHANGE_NOTIFY:
                if(fragment.notifyBeanBundle!=null){
                    initNotifyData(fragment.notifyBeanBundle);
                }
                break;
        }
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
        return true;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
//        List<String> list=new ArrayList<>();
//        list.add("杭州市");
//        list.add("北京市");
//        list.add("大连市");
//        list.add("上海市");
//        list.add("温州市");
//        SpUtils.setListObj(this,list,Constants.CITY_NAME);

        instance=this;
        mainPresenter=new MainPresenterImpl(this,this);
        mainPresenter.initialMain();

        manual_location.setOnClickListener(this);
        mDrawerType=BaseDrawer.Type.CLEAR_D;
        weatherView.setDrawerType(mDrawerType);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    /**
     * 切换当前界面动画效果
     * */
    public void switchWeatherView(BaseDrawer.Type mDrawerType){
        if(weatherView!=null){
            weatherView.setDrawerType(mDrawerType);
            fragment.setWeatherSave(mDrawerType);
        }
    }

    /**
     * 初始化切换动画
     * */
    @Override
    public void animAlpha(AlphaAnimation alphaAnimation) {
        viewPager.setAnimation(alphaAnimation);
    }

    /**
     * 初始化界面数据
     * */
    @Override
    public void intialCityName(List<String> cityNames) {
        fragments = new BaseLFragment[cityNames.size() ] ;
        for(int i = 0; i<cityNames.size();i++){
            final String area = cityNames.get(i);
            Bundle bundle=new Bundle();
            bundle.putString(Constants.CITY,area);
            fragments[i] = WeatherFragment.getInstance(bundle);
        }
        viewPager.setOffscreenPageLimit(cityNames.size());
        viewPager.setAdapter(new MxxFragmentsAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOnPageChangeListener(new MxxViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                tempPosition=position;
                fragment = (WeatherFragment) fragments[position];
                fragment.postRefresh(mDrawerType);
            }
        });

        if(cityNames!=null&&cityNames.size()>0){
            fragment = (WeatherFragment) fragments[0];
            fragment.postRefresh(mDrawerType);
        }
    }

    @Override
    public void initialTime(String time) {
        tv_date.setText(time);
    }

    //每次切换都进行重绘
    @Override
    public void initNotify(WeatherService weatherService) {
        //获取传递的数据
        this.weatherService=weatherService;
    }

    private void initNotifyData(NotifyBean notifyBean){
        weatherService.showNotification(this.getApplicationContext(),notifyBean.getNow_tmp(),notifyBean.getNow_range(),notifyBean.getNow_state()
                ,notifyBean.getCity(),notifyBean.getDrawableId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manual_location:
                UIHelper.jumpBlankAcitivty(this, SimpleBackPage.CITY);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        weatherView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        weatherView.onPause();
    }
    @Override
    protected void onDestroy() {
        mainPresenter.unbindPlaybackService();
        instance=null;
        weatherView.onDestroy();
        super.onDestroy();


    }

    @Override
    public void onBackPressed() {
//        moveTaskToBack(true);//后台进程
        //一定要先截断系统自发性的返回键功能
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "是否要退出Healer天气应用", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }



}
