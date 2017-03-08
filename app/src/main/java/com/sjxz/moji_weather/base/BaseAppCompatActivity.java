package com.sjxz.moji_weather.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.helper.EventCenter;
import com.sjxz.moji_weather.helper.VaryViewHelperController;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/9/18.
 * Role:Activity抽取类
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected Context mContext;

    /**
     * 当前的view的控制界面显示---->网络异常,网络过慢,请求空数据显示
     */
    private VaryViewHelperController mVaryViewHelperController;

    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionType()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);

        if(isBindEventBus()){
            EventBus.getDefault().register(this);
        }

        setTranslucentStatus(isApplyStatusBarTranslucency());

        mContext=this;

        BaseAppManager.getInstance().addActivity(this);

        if(getContentViewLayoutID()!=0){
            setContentView(getContentViewLayoutID());
        }else{
            throw new IllegalArgumentException("You must return a  contentView layout resource Id");
        }


        initViewsAndEvents();

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (null != getLoadingTargetView()) {
            //展示不同情况下的界面结构
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }


    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionType()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (isBindEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * activity的跳转
     * */
    protected void readyGo(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

    protected void readyGoWithData(Class<?> clazz,Bundle bundle){
        Intent intent=new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoThenKill(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
        finish();
    }

    protected void readyGoWithDataThenKill(Class<?> clazz,Bundle bundle){
        Intent intent=new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    protected void readyGoWaitResult(Class<?> clazz,int requestCode){
        Intent intent=new Intent(this,clazz);
        startActivityForResult(intent,requestCode);
    }

    protected void readyGoWithDataWaitResult(Class<?> clazz,Bundle bundle,int requestCode){
        Intent intent=new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * 显示正在加载中
     *基于getLoadingTargetView()不为空的情况
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }
    /**
     * use SytemBarTintManager
     *  顶部电量行的背景颜色设置
     * @param tintDrawable
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }

    }


    /**
     * set status bar translucency
     *透明状态栏--->顶部电池栏
     * a |= b;==>a = a|b
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }


    @Subscribe
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComming(eventCenter);
        }
    }
    /**
     * 广播接受数据继承类实现相关操作
     *
     * @param eventCenter
     */
    protected abstract void onEventComming(EventCenter eventCenter);
    /**
     * activity进入退出是否需要动画设置
     * */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * activity动画选择器
     * */
    protected abstract TransitionMode getOverridePendingTransitionType();

    /**
     * 界面是否注册监听器
     * */
    protected abstract boolean isBindEventBus();

    /**
     * 是否将导航栏透明化
     * */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     *设置当前布局
     * */
    protected abstract int getContentViewLayoutID();

    /**
     * 实现数据赋值
     * */
    protected abstract void initViewsAndEvents();

    /**
     * 是否有其他不同的view
     */
    protected abstract View getLoadingTargetView();
}
