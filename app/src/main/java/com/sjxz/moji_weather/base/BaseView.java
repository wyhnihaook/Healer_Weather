package com.sjxz.moji_weather.base;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/9/19.
 * Role:对应当前数据填充的界面的状态操控
 */
public interface BaseView {
    /**
     * show loading message
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * show error message
     */
    void showError(String msg);

    /**
     * show exception message
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNetError();
}
