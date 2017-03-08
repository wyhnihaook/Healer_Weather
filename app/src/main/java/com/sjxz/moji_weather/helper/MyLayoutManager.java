package com.sjxz.moji_weather.helper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/17.
 * Role:
 */
public class MyLayoutManager extends LinearLayoutManager {

    public MyLayoutManager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        View view = recycler.getViewForPosition(0);
        if(view != null){
            measureChild(view, widthSpec, heightSpec);
            int measuredWidth = View.MeasureSpec.getSize(widthSpec);
            int measuredHeight = view.getMeasuredHeight();
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }
}