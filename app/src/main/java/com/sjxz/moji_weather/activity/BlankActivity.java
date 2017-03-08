package com.sjxz.moji_weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.sjxz.moji_weather.R;
import com.sjxz.moji_weather.base.BaseActivity;
import com.sjxz.moji_weather.base.SimpleBackPage;
import com.sjxz.moji_weather.helper.EventCenter;
import com.sjxz.moji_weather.helper.UIHelper;

import java.lang.ref.WeakReference;

public class BlankActivity extends BaseActivity {

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    private static final String TAG = "FLAG_TAG";
    protected WeakReference<Fragment> mFragment;
    protected int mPageValue = -1;


    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionType() {
        return TransitionMode.RIGHT;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_blank;
    }

    @Override
    protected void initViewsAndEvents() {

        UIHelper.setLeftBack(this);

        if (mPageValue == -1) {
            mPageValue = getIntent().getIntExtra(BUNDLE_KEY_PAGE, 0);
        }
        initFromIntent(mPageValue, getIntent());
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    protected void initFromIntent(int pageValue, Intent data) {
        if (data == null) {
            throw new RuntimeException(
                    "you must provide a page info to display");
        }
        SimpleBackPage page = SimpleBackPage.getPageByValue(pageValue);
        if (page == null) {
            throw new IllegalArgumentException("can not find page by value:"
                    + pageValue);
        }


        try {
            Fragment fragment = (Fragment) page.getClz().newInstance();

            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
            if (args != null) {
                fragment.setArguments(args);
            }

            FragmentTransaction trans = getSupportFragmentManager()
                    .beginTransaction();
            trans.replace(R.id.container, fragment, TAG);
            trans.commitAllowingStateLoss();

            mFragment = new WeakReference<Fragment>(fragment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "generate fragment error. by value:" + pageValue);
        }


    }



}

