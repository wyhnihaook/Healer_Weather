package com.sjxz.moji_weather.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.utils.LogUtils;
import com.sjxz.moji_weather.MainActivity;
import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.adapter.WeatherLifeAdapter;
import com.sjxz.moji_weather.base.BaseLFragment;
import com.sjxz.moji_weather.bean.weather.DailyForecastBean;
import com.sjxz.moji_weather.bean.weather.NotifyBean;
import com.sjxz.moji_weather.bean.weather.ShowApiWeather;
import com.sjxz.moji_weather.bean.weather.ShowApiWeatherNormalInner;
import com.sjxz.moji_weather.bean.weather.WeatherBaseLifeBean;
import com.sjxz.moji_weather.helper.EventCenter;
import com.sjxz.moji_weather.helper.moji.AqiView;
import com.sjxz.moji_weather.helper.moji.AstroView;
import com.sjxz.moji_weather.helper.moji.DailyForecastView;
import com.sjxz.moji_weather.mvp.presenter.impl.WeatherPresenterImpl;
import com.sjxz.moji_weather.mvp.view.RxWeatherView;
import com.sjxz.moji_weather.util.Constants;
import com.sjxz.moji_weather.util.Utils;
import com.sjxz.moji_weather.view.MyScrollView;
import com.sjxz.moji_weather.view.PullRefreshLayout;
import com.sjxz.moji_weather.weather.BaseDrawer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/7.
 * Role:结合Viewpager的懒加载，避免数据错乱
 */
@TargetApi(Build.VERSION_CODES.M)
public class WeatherFragment extends BaseLFragment implements RxWeatherView, View.OnClickListener, View.OnScrollChangeListener {

    public static WeatherFragment weatherFragment;

    public synchronized static WeatherFragment getInstance(Bundle bundle) {
        weatherFragment = new WeatherFragment();
        weatherFragment.setArguments(bundle);
        return weatherFragment;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (ll_one != null && ll_second != null) {
                                if (ll_one.getAlpha() == 1.0) {
                                    isZeroOne = true;
                                    ll_one.startAnimation(hideAnim);
                                } else if (ll_second.getAlpha() == 1.0) {
                                    isZeroOne = false;
                                    ll_second.startAnimation(hideAnim);
                                }
                            }

                        }
                    }, 12000);

                    break;

            }
        }
    };

    @Bind(R.id.w_PullRefreshLayout)
    PullRefreshLayout pullRefreshLayout;

    @Bind(R.id.now_weather)
    TextView now_weather;

    @Bind(R.id.now_temperature)
    TextView now_temperature;

    @Bind(R.id.now_direction)
    TextView now_direction;

    @Bind(R.id.now_humidity)
    TextView now_humidity;

    @Bind(R.id.today_temperature_range)
    TextView today_temperature_range;

    @Bind(R.id.today_weather)
    TextView today_weather;

    @Bind(R.id.today_weather_pic)
    ImageView today_weather_pic;

    @Bind(R.id.tomorrow_temperature_range)
    TextView tomorrow_temperature_range;

    @Bind(R.id.tomorrow_weather)
    TextView tomorrow_weather;

    @Bind(R.id.tomorrow_weather_pic)
    ImageView tomorrow_weather_pic;

    @Bind(R.id.air_quality)
    TextView air_quality;

    @Bind(R.id.now_small_temperature)
    TextView now_small_temperature;

    @Bind(R.id.now_pressure)
    TextView now_pressure;

    @Bind(R.id.ll_second)
    LinearLayout ll_second;

    @Bind(R.id.ll_one)
    LinearLayout ll_one;

    @Bind(R.id.ll_middle)
    LinearLayout ll_middle;

    @Bind(R.id.ll_bottom)
    LinearLayout ll_bottom;

    @Bind(R.id.rl_transport)
    RelativeLayout rl_transport;

    @Bind(R.id.w_dailyForecastView)
    DailyForecastView w_dailyForecastView;

    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    @Bind(R.id.aqi_view)
    AqiView aqi_view;

    @Bind(R.id.pm2_5)
    TextView pm2_5;

    @Bind(R.id.pm10)
    TextView pm10;

    @Bind(R.id.so2)
    TextView so2;

    @Bind(R.id.no2)
    TextView no2;

    @Bind(R.id.astroView)
    AstroView astroView;

    @Bind(R.id.switch_weather)
    TextView switch_weather;

    @Bind(R.id.myscrollview)
    MyScrollView myscrollview;

    public NotifyBean notifyBeanBundle;

    private WeatherPresenterImpl weatherPresenter;

    private WeatherLifeAdapter weatherLifeAdapter;

    private int middleHeight;

    private int bottomHeight;

    private int rl_transportHeight;

    private boolean isInitData = false;


    public String nowWeather;

    private Animation showAnim = null;
    private Animation hideAnim = null;
    private boolean isZeroOne = false;

    private BaseDrawer.Type mDrawerType = BaseDrawer.Type.DEFAULT;

    private int heightWindow;

    private int alpha;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_weather;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        weatherPresenter = new WeatherPresenterImpl();
        weatherPresenter.attach(this);
        weatherPresenter.initialWeatherData();

        heightWindow = Utils.getScreenHeight(getActivity());
        alpha = (heightWindow / 255);
        MainActivity.instance.view_bg.getBackground().setAlpha(0);
        initView();
        initAnim();
        initData();
    }


    private void initView() {

        initialRecyclerViewGridHorizontal(recycler_view);
        switch_weather.setOnClickListener(this);
        manualMeasureHeight();

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新，网络请求
                weatherPresenter.getWeatherListData(getTitle(), "1", "1", "1", "0");
            }
        });
    }

    private void initAnim() {
        ll_second.setAlpha(0f);
        showAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        hideAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        showAnim.setFillAfter(true);
        hideAnim.setFillAfter(true);
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isZeroOne) {
                    ll_one.setAlpha(0f);
                    ll_second.startAnimation(showAnim);
                    ll_second.setAlpha(1f);
                } else {
                    ll_second.setAlpha(0f);
                    ll_one.startAnimation(showAnim);
                    ll_one.setAlpha(1f);
                }
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initData() {

        weatherLifeAdapter = new WeatherLifeAdapter(getActivity());
        recycler_view.setAdapter(weatherLifeAdapter);
        //使用懒加载
        //        postRefresh();

        myscrollview.setOnScrollChangeListener(this);
    }


    /**
     * 网络请求刷新数据
     */
    public void postRefresh(BaseDrawer.Type type) {
        changeBgAlpha();
        if (!isInitData) {
            if (pullRefreshLayout != null) {
                Activity activity = getActivity();
                if (activity != null) {
                    if (Utils.isNet(activity))
                        pullRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //滚动到顶部
                                myscrollview.smoothScrollTo(0, 0);
                                pullRefreshLayout.setRefreshing(true, true);
                            }
                        }, 100);
                }

            } else {
                EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_INIT));
            }
        } else {
            //加载完数据页面选中的时候更新界面
            //surfaceView更新
            if (type != mDrawerType) {
                EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_SECOND));
            }
        }
    }

    /**
     * 保存当前的天气情况
     */
    public void setWeatherSave(BaseDrawer.Type mDrawerType) {
        this.mDrawerType = mDrawerType;
    }

    /**
     * 计算控件高度,用于将七天天气置于屏幕外
     */
    private void manualMeasureHeight() {

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);


        ll_middle.measure(w, h);

        ll_bottom.measure(w, h);


        middleHeight = ll_middle.getMeasuredHeight();

        bottomHeight = ll_bottom.getMeasuredHeight();

        int fullScreenHeight = Utils.getScreenHeight(getActivity());

        rl_transportHeight = fullScreenHeight - (middleHeight + bottomHeight * 2 + Utils.getStatusBarHeight(getActivity()));//不理解计算的时候要减去两倍的高度

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rl_transportHeight);

        rl_transport.setLayoutParams(params);
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    public String getTitle() {
        return getArguments().getString(Constants.CITY) == null ? "杭州市" : getArguments().getString(Constants.CITY);
    }


    @Override
    public void initialTime(String time) {
    }

    @Override
    public void initialLifeData(List<WeatherBaseLifeBean> lifeBeanList) {
        weatherLifeAdapter.setDatas(lifeBeanList);
        weatherLifeAdapter.notifyDataSetChanged();
    }

    @Override
    public void initialSevenDay(List<DailyForecastBean> dailyForecastBeanList) {
        w_dailyForecastView.setData(dailyForecastBeanList);
    }

    @Override
    public void initialTodayStatus(ShowApiWeatherNormalInner weather) {
        astroView.setData(weather);
    }

    @Override
    public void initialViewData(ShowApiWeather data, List<ShowApiWeatherNormalInner> showApiWeatherNormalInnerList) {
        NotifyBean notifyBean= weatherPresenter.setTopViewData(data, showApiWeatherNormalInnerList, now_weather, now_temperature, now_direction, now_humidity,
                today_temperature_range, today_weather, today_weather_pic, tomorrow_temperature_range,
                tomorrow_weather, tomorrow_weather_pic, air_quality, now_small_temperature, now_pressure
                , pm2_5, pm10, so2, no2);
        notifyBean.setCity(getTitle()+"");
        notifyBeanBundle=notifyBean;
        //传递广播提醒主界面surfaceview更改数据
        EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_CHANGE_WEATHER, notifyBean));
    }

    @Override
    public void onSuccess(ShowApiWeather data) {
        //暂停刷新
        isInitData = true;
        if(pullRefreshLayout!=null){
            pullRefreshLayout.setRefreshing(false);
        }
        nowWeather = data.getNow().getWeather();
        LogUtils.i(nowWeather + "当前城市：=" + getTitle());
        aqi_view.setData(data.getNow().getAqiDetail());

        handler.sendEmptyMessage(0);
    }


    @Override
    public void onError() {
        if(pullRefreshLayout!=null){
            pullRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_weather:
                //弹出dialog显示天气样式选择
                final BaseDrawer.Type tempDrawer = mDrawerType;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                ArrayList<String> strs = new ArrayList<String>();
                for (BaseDrawer.Type t : BaseDrawer.Type.values()) {
                    strs.add(t.toString());
                }
                int index = 0;
                for (int i = 0; i < BaseDrawer.Type.values().length; i++) {
                    if (BaseDrawer.Type.values()[i] == mDrawerType) {
                        index = i;
                        break;
                    }
                }
                builder.setSingleChoiceItems(strs.toArray(new String[]{}), index,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDrawerType = BaseDrawer.Type.values()[which];
                                if (tempDrawer != mDrawerType) {
                                    EventBus.getDefault().post(new EventCenter(Constants.EVENTBUS_CHECK, mDrawerType));
                                }
                                dialog.dismiss();
                                // Toast.makeText(getActivity(), "onClick->"
                                // + which, Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();

                break;
        }
    }

    int scrollYTemp;

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //随着滚动距离将屏幕渐变
        if (scrollY < heightWindow && oldScrollY - scrollY < 0) {
            //背景渐变,向下滑动
            MainActivity.instance.view_bg.getBackground().setAlpha(scrollY / alpha < 0 ? 0 : scrollY / alpha);

        } else if (oldScrollY - scrollY > 0 && scrollY < heightWindow) {
            //向上滑动,背景渐变
            MainActivity.instance.view_bg.getBackground().setAlpha(scrollY / alpha < 0 ? 0 : scrollY / alpha);

        }

        scrollYTemp = scrollY;
    }


    private void changeBgAlpha(){
        //重绘的时候定义背景颜色
        //背景渐变,向下滑动
        if(MainActivity.instance!=null&&alpha!=0){
            MainActivity.instance.view_bg.getBackground().setAlpha(scrollYTemp / alpha < 0 ? 0 : scrollYTemp / alpha);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        //解决handler的内存泄漏
        handler.removeCallbacksAndMessages(null);
        handler=null;

        weatherFragment=null;
    }
}
