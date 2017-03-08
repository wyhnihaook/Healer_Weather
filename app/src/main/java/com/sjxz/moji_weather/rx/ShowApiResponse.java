package com.sjxz.moji_weather.rx;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/2.
 * Role:
 */
public class ShowApiResponse<T> {
    public String showapi_res_code;
    public String showapi_res_error;

    public String getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(String showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public T getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(T showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public T showapi_res_body;
}
