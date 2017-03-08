package com.sjxz.moji_weather.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.sjxz.moji_weather.R;

import butterknife.ButterKnife;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/9/18.
 * Role:activity的基类
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements BaseView {

    public int page = 0;

    public int pageSize = 20;

    public boolean isLoadMore = false;

    protected Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isApplyKitKatTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        toolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (null != toolbar) {
            //如果当前的view存在toolbar就设置样式
            setSupportActionBar(toolbar);
            //决定左上角的图标是否可以点击
            getSupportActionBar().setHomeButtonEnabled(true);
            // 给左上角图标的左边加上一个返回的图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }

    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }

    /**
     * 是否将背景颜色设置为统一颜色
     */
    protected abstract boolean isApplyKitKatTranslucency();

    /**
     * 抽取RecyclerView的初始化方法
     */
    protected void initialRecyclerViewLinearLayout(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void initialRecyclerViewGrid(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public StaggeredGridLayoutManager layoutManager;

    protected void initialRecyclerViewStagger(RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);
        layoutManager=  new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
    }

    protected void initialXRecyclerView(XRefreshView xRefreshView) {
        xRefreshView.setPullLoadEnable(false);//设置不允许上拉加载
        xRefreshView.setPullRefreshEnable(true);//设置默认允许下拉刷新
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);//允许可以水平滑动
    }


    protected ProgressDialog pDialog;

    protected Dialog showProgressDialog(String message) {
        if (pDialog == null) {
            pDialog = new ProgressDialog(this, R.style.ProgressDialogStyle);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            View view = LayoutInflater.from(this).inflate(R.layout.progres_dialog, null);
            pDialog.setContentView(view);
            ((TextView) view.findViewById(R.id.textView1)).setText(message);
        }
        return pDialog;
    }

    protected void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
            pDialog.dismiss();
        }
        pDialog = null;
    }

    protected Dialog showProgressDialog(int message) {
        return showProgressDialog(this.getResources().getString(message));
    }

    /**
     * Fragment切换方法
     *
     * @param hideFragment 要隐藏的Fragment 可以为null
     * @param startFragment 要启动的Fragment
     * @return void
     */
    //避免页面未加载完毕进行二次加载
    public Fragment contentFragment;

    public void addFragmentContainer(Fragment hideFragment, Fragment startFragment, int container) {

        if (contentFragment != null) {
            //当前Fragment与启动发Fragment一致不执行任何操作
            if (contentFragment.getClass().getName().equals(startFragment.getClass().getName())) {
                return;
            }
        }
        //开启一个事物
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String tagKey = startFragment.getClass().getName();
//        if (tagKey.contains("Home")) {
//
//        } else {
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
//        }

        //不为空的话隐藏
        if (hideFragment != null) {
            transaction.hide(hideFragment);
        }
        //已经添加的话显示
        if (startFragment.isAdded()) {
            transaction.show(startFragment);
            //第一次添加，并保存tagKey（Fragment类名）,可以用getSupportFragmentManager()直接根据tagKey名取出该Fragment进行操作
        } else {
            transaction.add(container, startFragment, tagKey);
        }

        //记录当前Fragment
        contentFragment = startFragment;
        transaction.commitAllowingStateLoss();
    }
    /**
     * 网络请求公共
     * */
    protected  void noDataCommon(XRefreshView xRefreshView,int page){
        xRefreshView.setPullLoadEnable(false);//设置不允许上拉加载
        xRefreshView.setPullRefreshEnable(page==1?false:true);//设置默认允许下拉刷新
    }

    protected void finishHttpCommon(XRefreshView xRefreshView){
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
    }
}
