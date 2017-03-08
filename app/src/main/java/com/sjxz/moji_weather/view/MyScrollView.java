package com.sjxz.moji_weather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.sjxz.moji_weather.fragment.WeatherFragment;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/9.
 * Role:
 */
public class MyScrollView extends ScrollView {


    private ScrollViewLisenter scrollViewLisenter;


    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void setScrollViewLisenter(ScrollViewLisenter scrollViewLisenter) {
        this.scrollViewLisenter = scrollViewLisenter;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewLisenter != null) {
            scrollViewLisenter.onScrollChanged(this, x, y, oldx, oldy);
        }

    }



}
