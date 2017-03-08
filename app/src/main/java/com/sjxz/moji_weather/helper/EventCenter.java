package com.sjxz.moji_weather.helper;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/9/19.
 * Role:
 */
public class EventCenter <T> {

    /**
     * reserved data
     */
    public T data;

    /**
     * this code distinguish between different events
     */
    public int eventCode = -1;

    public EventCenter(int eventCode) {
        this(eventCode, null);
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    /**
     * get event code
     *
     * @return
     */
    public int getEventCode() {
        return this.eventCode;
    }

    /**
     * get event reserved data
     *
     * @return
     */
    public T getData() {
        return this.data;
    }
}
