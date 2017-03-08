package com.sjxz.moji_weather.helper;

import android.content.Context;
import android.view.View;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/9/19.
 * Role:
 */
public abstract interface IVaryViewHelper {

    public abstract View getCurrentLayout();

    public abstract void restoreView();

    public abstract void showLayout(View view);

    public abstract View inflate(int layoutId);

    public abstract Context getContext();

    public abstract View getView();
}
