package com.sjxz.moji_weather.helper.moji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.andview.refreshview.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/24.
 * Role:场景部署
 */
public class Scene {

    private Context context;
    private int width;
    private int height;

    private Bitmap bg;
    private List<Actor> actors = new ArrayList<Actor>();
    private Paint paint;
    public static boolean isIterator=true;

    public Scene(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setBg(Bitmap bg) {
        this.bg = bg;
    }

    public void add(Actor actor) {
        actors.add(actor);
    }

    public void clear(){
        if(!isIterator){
            //说明是已经停止遍历了
            LogUtils.i("停止遍历");
            actors.clear();
            isIterator=true;
        }

    }

    public void draw(Canvas canvas) {
        if(bg!=null){
            canvas.drawBitmap(bg, new Rect(0, 0, bg.getWidth(), bg.getHeight()), new Rect(0, 0, width, height), paint);
        }
        if(actors!=null&&actors.size()!=0){
            for (Actor actor : actors) {
                actor.draw(canvas,width,height);
            }
        }

    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
