package com.sjxz.moji_weather.mvp.interactor.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.sjxz.moji_weather.mvp.interactor.MainInteractor;
import com.sjxz.moji_weather.util.Constants;
import com.sjxz.moji_weather.util.SpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/8.
 * Role:
 */
public class MainInteractorImpl implements MainInteractor {

    @Override
    public List<String> initialCity(Context context) {

        List<String> list=SpUtils.getListObj(context, Constants.CITY_NAME);

        if(list!=null&&list.size()>0){
            return list;
        }
        list=new ArrayList<>();
        list.add("杭州市");
        SpUtils.setListObj(context,list,Constants.CITY_NAME);
        return list;
    }

    @Override
    public AlphaAnimation initialAnim(final Context context) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(260);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ((Activity)context).getWindow().setBackgroundDrawable(
				 new ColorDrawable(Color.BLACK));
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {}
        });

        return alphaAnimation;
    }

    @Override
    public String getTodayTime() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 EEEE");
        return format.format(date);
    }
}
