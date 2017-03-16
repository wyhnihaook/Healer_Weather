package com.sjxz.moji_weather.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.andview.refreshview.utils.LogUtilss;
import com.sjxz.moji_weather.MainActivity;
import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.util.Utils;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/3/15.
 * Role:
 */
public class WeatherService extends Service {


    private final Binder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public WeatherService getService() {
            return WeatherService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtilss.i("onCreate");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtilss.i("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        LogUtilss.i("onDestroy");
        notifyCancle();
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    RemoteViews mRemoteViews;

    //需要当前温度，最高和最低温度，当前情况，图片信息，地址,是否在前台工作
    public void showNotification(Context context, String now_tmp, String now_range, String now_state, String city, int drawableId) {
        //展示标题栏

        PendingIntent contentIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, new Intent(context.getApplicationContext(), MainActivity.class), 0);

        if(mNotificationManager==null){
            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        if(mBuilder==null){
            mBuilder = new NotificationCompat.Builder(this);
        }

//      系统自带样式的通知栏
//        mBuilder.setContentTitle("测试标题")//设置通知栏标题
//                .setContentText("测试内容") //设置通知栏显示内容
//                .setContentIntent(contentIntent) //设置通知栏点击意图
//                //  .setNumber(number) //设置通知集合的数量
//                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
//                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//                //  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
//                .setOngoing(true)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
//                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
//                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON


        //自定义样式的标题栏


        if(mRemoteViews==null){
            mRemoteViews = new RemoteViews(getPackageName(), R.layout.notify_view);

            mRemoteViews.setInt(R.id.tv_now_tmp, "setTextColor", Utils.isDarkNotificationTheme(context) == true ? Color.WHITE : Color.BLACK);
            mRemoteViews.setInt(R.id.tv_now, "setTextColor", Utils.isDarkNotificationTheme(context) == true ? Color.WHITE : Color.BLACK);
            mRemoteViews.setInt(R.id.tv_state, "setTextColor", Utils.isDarkNotificationTheme(context) == true ? Color.WHITE : Color.BLACK);
            mRemoteViews.setInt(R.id.tv_city, "setTextColor", Utils.isDarkNotificationTheme(context) == true ? Color.WHITE : Color.BLACK);
        }

        mRemoteViews.setTextViewText(R.id.tv_now_tmp, now_tmp);
        mRemoteViews.setTextViewText(R.id.tv_now, now_range);
        mRemoteViews.setTextViewText(R.id.tv_state, now_state);
        mRemoteViews.setTextViewText(R.id.tv_city, city);

        mRemoteViews.setImageViewResource(R.id.iv_state, drawableId);

        //判断是否是当前运行的app
        mBuilder.setContent(mRemoteViews)
                .setContentIntent(contentIntent)
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)
                .setSmallIcon(R.mipmap.icon);


        int notifyId = 1;

        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(notifyId, mBuilder.build());
    }


    //清除所有通知
    public void notifyCancle() {
        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
        }
    }


}
