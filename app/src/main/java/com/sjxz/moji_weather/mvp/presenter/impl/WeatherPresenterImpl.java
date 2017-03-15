package com.sjxz.moji_weather.mvp.presenter.impl;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.utils.LogUtilss;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sjxz.moji_weather.BaseApplication;
import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.bean.weather.NotifyBean;
import com.sjxz.moji_weather.bean.weather.ShowApiWeather;
import com.sjxz.moji_weather.bean.weather.ShowApiWeatherNormalInner;
import com.sjxz.moji_weather.mvp.interactor.WeatherInteractor;
import com.sjxz.moji_weather.mvp.interactor.impl.WeatherInteractorImpl;
import com.sjxz.moji_weather.mvp.presenter.BasePresenter;
import com.sjxz.moji_weather.mvp.view.OnRxWeatherLisenter;
import com.sjxz.moji_weather.mvp.view.RxWeatherView;
import com.sjxz.moji_weather.view.MyBitmapImageViewTarget;

import java.util.Calendar;
import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/9.
 * Role:将逻辑与界面相结合
 */
public class WeatherPresenterImpl extends BasePresenter<RxWeatherView> {

    private WeatherInteractor weatherInteractor;

    public WeatherPresenterImpl() {
        weatherInteractor = new WeatherInteractorImpl();
    }

    /**
     * View界面的初始化，将立即能获取到的数据进行初始化
     */
    public void initialWeatherData() {
        mView.initialTime(weatherInteractor.getTodayTime());
    }


    /**
     * 将获取的数据传递给View进行界面赋值
     */
    public void getWeatherListData(String area, String needMoreDay, String needIndex,
                                   String needAlarm, String need3HourForcast) {
        weatherInteractor.getWeatherData(area, needMoreDay, needIndex, needAlarm, need3HourForcast, new OnRxWeatherLisenter() {
            @Override
            public void onSuccess(ShowApiWeather data) {
                if (data == null) {
                    LogUtilss.i("data是空的");
                } else {
                    LogUtilss.i("当前城市=" + data.getF1().getCityName());
                }
                mView.initialLifeData(weatherInteractor.getRecyclerData(weatherInteractor.getListDayWeather(data)));
                mView.initialSevenDay(weatherInteractor.setDailyForcastWeather(weatherInteractor.getListDayWeather(data)));
                mView.initialTodayStatus(weatherInteractor.getListDayWeather(data).get(0));
                mView.initialViewData(data, weatherInteractor.getListDayWeather(data));
                mView.onSuccess(data);

                //成功获取后进行数据初始化
            }

            @Override
            public void onError() {
                mView.onError();
            }
        });
    }


    /**
     * 获取当前数据填充
     */
    public NotifyBean setTopViewData(ShowApiWeather data, List<ShowApiWeatherNormalInner> dataList, TextView now_weather, TextView now_temperature, TextView now_direction, TextView now_humidity,
                               TextView today_temperature_range, TextView today_weather, ImageView today_weather_pic, TextView tomorrow_temperature_range,
                               TextView tomorrow_weather, ImageView tomorrow_weather_pic, TextView air_quality, TextView now_small_temperature,
                               TextView now_pressure, TextView pm2_5, TextView pm10, TextView so2, TextView no2) {
        if (null != data.getNow() && null != data.getNow().getAqiDetail()) {
            air_quality.setText(data.getNow().getAqiDetail().getAqi() + " " + data.getNow().getAqiDetail().getQuality());
        }

        pm2_5.setText(data.getNow().getAqiDetail().getPm2_5() + "μg/m³");
        pm10.setText(data.getNow().getAqiDetail().getPm10() + "μg/m³");
        so2.setText(data.getNow().getAqiDetail().getSo2() + "μg/m³");
        no2.setText(data.getNow().getAqiDetail().getNo2() + "μg/m³");

        now_weather.setText(data.getNow().getWeather());
        now_temperature.setText(data.getNow().getTemperature() + "°");
        now_small_temperature.setText(data.getNow().getTemperature() + "℃");
        now_pressure.setText(dataList.get(0).getAir_press());
        now_direction.setText(data.getNow().getWind_power());
        now_humidity.setText(data.getNow().getSd());
        today_temperature_range.setText(dataList.get(0).getDay_air_temperature() + "/" + dataList.get(0).getNight_air_temperature() + "℃");

        NotifyBean notifyBean = new NotifyBean();
        notifyBean.setNow_tmp(data.getNow().getTemperature() + "℃");
        notifyBean.setNow_range(dataList.get(0).getDay_air_temperature() + "°/" + dataList.get(0).getNight_air_temperature() + "°");
        notifyBean.setNow_state(data.getNow().getWeather() + "");
        //图片
        switch (data.getNow().getWeather()) {
            case "晴":
                notifyBean.setDrawableId(R.drawable.w0);
                break;
            case "阴":
                notifyBean.setDrawableId(R.drawable.w2);
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
                notifyBean.setDrawableId(R.drawable.w6);
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
                notifyBean.setDrawableId(R.drawable.w14);
                break;
            case "少云":
            case "多云":
                notifyBean.setDrawableId(R.drawable.w1);
                break;
            case "霾":
            case "雾":
            case "浮尘":
            case "扬沙":
            case "强沙尘暴":
            case "沙尘暴":
                notifyBean.setDrawableId(R.drawable.w45);
                break;
        }


        if (null != dataList.get(0).getDay_weather() && !TextUtils.isEmpty(dataList.get(0).getDay_weather())
                && null != dataList.get(0).getNight_weather() && !TextUtils.isEmpty(dataList.get(0).getNight_weather())) {
            if (dataList.get(0).getDay_weather().equals(dataList.get(0).getNight_weather())) {
                today_weather.setText(dataList.get(0).getDay_weather());
            } else {
                today_weather.setText(dataList.get(0).getDay_weather() + "转" + dataList.get(0).getNight_weather());
            }
        } else if (null != dataList.get(0).getDay_weather() && !TextUtils.isEmpty(dataList.get(0).getDay_weather())) {
            today_weather.setText(dataList.get(0).getDay_weather());
        } else if (null != dataList.get(0).getNight_weather() && !TextUtils.isEmpty(dataList.get(0).getNight_weather())) {
            today_weather.setText(dataList.get(0).getNight_weather());
        } else {
            today_weather.setText("暂无信息");
        }

        tomorrow_temperature_range.setText(dataList.get(1).getDay_air_temperature() + "/" + dataList.get(1).getNight_air_temperature() + "℃");
        if (null != dataList.get(1).getDay_weather() && !TextUtils.isEmpty(dataList.get(1).getDay_weather())
                && null != dataList.get(1).getNight_weather() && !TextUtils.isEmpty(dataList.get(1).getNight_weather())) {
            if (dataList.get(1).getDay_weather().equals(dataList.get(1).getNight_weather())) {
                tomorrow_weather.setText(dataList.get(1).getNight_weather());
            } else {
                tomorrow_weather.setText(dataList.get(1).getDay_weather() + "转" + dataList.get(1).getNight_weather());
            }

        } else if (null != dataList.get(1).getDay_weather() && !TextUtils.isEmpty(dataList.get(1).getDay_weather())) {
            tomorrow_weather.setText(dataList.get(1).getDay_weather());
        } else if (null != dataList.get(1).getNight_weather() && !TextUtils.isEmpty(dataList.get(1).getNight_weather())) {
            tomorrow_weather.setText(dataList.get(1).getNight_weather());
        } else {
            tomorrow_weather.setText("暂无信息");
        }


        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour <= 18) {
            Glide.with(BaseApplication.getApplication()).load(dataList.get(0).getDay_weather_pic()).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().
                    placeholder(R.mipmap.homebg).into(new MyBitmapImageViewTarget(today_weather_pic));

            Glide.with(BaseApplication.getApplication()).load(dataList.get(1).getDay_weather_pic()).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().
                    placeholder(R.mipmap.homebg).into(new MyBitmapImageViewTarget(tomorrow_weather_pic));

        } else {
            Glide.with(BaseApplication.getApplication()).load(dataList.get(0).getNight_weather_pic()).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().
                    placeholder(R.mipmap.homebg).into(new MyBitmapImageViewTarget(today_weather_pic));

            Glide.with(BaseApplication.getApplication()).load(dataList.get(1).getNight_weather_pic()).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().
                    placeholder(R.mipmap.homebg).into(new MyBitmapImageViewTarget(tomorrow_weather_pic));
        }


        return notifyBean;


    }


}
