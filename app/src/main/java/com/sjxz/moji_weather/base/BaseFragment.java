package com.sjxz.moji_weather.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.sjxz.moji_weather.R;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/11/2.
 * Role:
 */
public abstract class BaseFragment extends BaseAppFragment implements BaseView {

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

    public abstract String getTitle();

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

    protected void initialRecyclerViewGrid(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    protected void initialRecyclerViewGridHorizontal(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2,GridLayoutManager.HORIZONTAL,false));
    }

    protected void initialRecyclerViewStagger(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    protected void initialXRecyclerView(XRefreshView xRefreshView) {
        xRefreshView.setPullLoadEnable(false);//设置不允许上拉加载
        xRefreshView.setPullRefreshEnable(true);//设置默认允许下拉刷新
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);//允许可以水平滑动
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

    protected ProgressDialog pDialog;

    protected Dialog showProgressDialog(String message) {
        if (pDialog == null) {
            pDialog = new ProgressDialog(getActivity(), R.style.ProgressDialogStyle);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.progres_dialog, null);
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

    private CompositeSubscription mSubscriptions;

    protected Subscription subscribeEvents() {
        return null;
    }

    protected void addSubscription(Subscription subscription) {
        if (subscription == null) return;
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeSubscription();
        }
        mSubscriptions.add(subscription);
    }
}
