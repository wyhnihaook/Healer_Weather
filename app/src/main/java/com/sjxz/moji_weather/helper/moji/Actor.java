package com.sjxz.moji_weather.helper.moji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/24.
 * Role:使用Matrix进行平滑图像处理
 */
public abstract class Actor {

    protected Context context;
    protected Matrix matrix = new Matrix();

    protected Actor(Context context) {
        this.context = context;
    }

    public abstract void draw(Canvas canvas, int width, int height);
}
