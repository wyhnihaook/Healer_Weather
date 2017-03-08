package com.sjxz.moji_weather.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/9/18.
 * Role:统计启动的activity页面
 */
public class BaseAppManager {

    private static BaseAppManager instance;

    private List<Activity> mActivities=new LinkedList<>();

    public BaseAppManager() {
    }

    public static BaseAppManager getInstance(){
        if(null==instance){
            synchronized (BaseAppManager.class){
                instance=new BaseAppManager();
            }
        }
        return instance;
    }

    public int size (){
        return mActivities.size();
    }

    public synchronized  void addActivity(Activity activity){
        mActivities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    public synchronized void clear() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    public synchronized void clearToTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }
}
