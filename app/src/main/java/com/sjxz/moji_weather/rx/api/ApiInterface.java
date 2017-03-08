package com.sjxz.moji_weather.rx.api;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/8.
 * Role:百度接口
 */
public interface ApiInterface {

    /**
     * 百度API接口
     */
    String API = "http://apis.baidu.com";

    /**
     * 开发者API密钥
     */
    String API_KEY = "4720bdbcfb3aa457eefd38d2f8fa580f";

    /**
     * 天气预报 (根据地名)
     服务商： 易源接口
     */
    String WEATHER_URL = "/showapi_open_bus/weather_showapi/address";
}
