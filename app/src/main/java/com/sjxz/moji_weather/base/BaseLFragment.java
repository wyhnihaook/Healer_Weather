package com.sjxz.moji_weather.base;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.andview.refreshview.XRefreshView;
import com.sjxz.moji_weather.helper.MyLayoutManager;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/10/18.
 * Role:
 */
public abstract class BaseLFragment extends BaseLazyFragment implements BaseView {

    public abstract String getTitle();

    public Handler handler=new Handler();

    public boolean isLoadMore=false;

    public int page=1;

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
     * 抽取RecyclerView的初始化方法
     */
    /**
     * 抽取RecyclerView的初始化方法
     */
    protected void initialRecyclerViewLinearLayout(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected void initialRecyclerViewMyLinearLayout(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new MyLayoutManager(getActivity()));
    }



    protected void initialRecyclerViewGrid(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }
    public StaggeredGridLayoutManager layoutManager;
    protected void initialRecyclerViewStagger(RecyclerView recyclerView){
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

    protected void initialRecyclerViewGridHorizontal(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2,GridLayoutManager.HORIZONTAL,false));
    }
    /**
     * 网络请求公共
     * */
    protected  void noDataCommon(XRefreshView xRefreshView,int page){
        xRefreshView.setPullLoadEnable(false);//设置不允许上拉加载
        xRefreshView.setPullRefreshEnable(page==1?false:true);//设置默认允许下拉刷新
    }

    protected void finishHttpCommon(XRefreshView xRefreshView){
        hideLoading();
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore();
    }

}
