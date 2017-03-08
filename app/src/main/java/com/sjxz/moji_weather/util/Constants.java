package com.sjxz.moji_weather.util;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/8.
 * Role:
 */
public class Constants {

    public final static String CITY_NAME = "city_name";

    public final static String CITY = "city";

    public final static int EVENTBUS_CHANGE_WEATHER=100;
    public final static int EVENTBUS_INIT=EVENTBUS_CHANGE_WEATHER+10;
    public final static int EVENTBUS_SECOND=EVENTBUS_INIT+10;
    public final static int EVENTBUS_CHECK=EVENTBUS_SECOND+10;

    public final static int EVENTBUS_DELETE_CITY=EVENTBUS_CHECK+10;
    public final static int EVENTBUS_DELETE_CITY_MAIN=EVENTBUS_DELETE_CITY+10;

    public final static int EVENTBUS_ADD_CITY=EVENTBUS_DELETE_CITY_MAIN+10;

    public final static int EVENTBUS_ADD_CITY_MAIN=EVENTBUS_ADD_CITY+10;
}
