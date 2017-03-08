package com.sjxz.moji_weather.rx;


import com.sjxz.moji_weather.rx.net.ApiException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author: Othershe
 * Time:  2016/8/11 17:53
 */
public class RxManager {
    private RxManager() {

    }

    public static RxManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RxManager INSTANCE = new RxManager();
    }

    public <T> Subscription doSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public <T> Subscription doSubscribe1(Observable<HttpResult<T>> observable, Subscriber<T> subscriber) {
        return observable
                .map(new Func1<HttpResult<T>, T>() {
                    @Override
                    public T call(HttpResult<T> httpResult) {
                        if (httpResult.isError()) {
                            throw new ApiException();
                        }
                        return httpResult.getResults();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public <T> Subscription doSubscribe2(Observable<ShowApiResponse<T>> observable, Subscriber<T> subscriber) {
        return observable
                .map(new Func1<ShowApiResponse<T>, T>() {
                    @Override
                    public T call(ShowApiResponse<T> httpResult) {
                        if (httpResult.getShowapi_res_error()=="200"?true:false) {
                            throw new ApiException();
                        }
                        return httpResult.getShowapi_res_body();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
