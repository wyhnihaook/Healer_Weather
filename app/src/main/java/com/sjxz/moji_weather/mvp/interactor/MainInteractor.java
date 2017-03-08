package com.sjxz.moji_weather.mvp.interactor;

import android.content.Context;
import android.view.animation.AlphaAnimation;

import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/8.
 * Role:主界面初始化需要的数据,需要return 数据回去
 */
public interface MainInteractor {

    List<String> initialCity(Context context);

    AlphaAnimation initialAnim(Context context);

    /**
     * 获取当天的日期
     * */
    String getTodayTime();

}
