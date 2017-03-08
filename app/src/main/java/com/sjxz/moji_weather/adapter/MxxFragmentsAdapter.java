package com.sjxz.moji_weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.sjxz.moji_weather.base.BaseLFragment;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/7.
 * Role:
 */
public class MxxFragmentsAdapter extends MxxFragmentPagerAdapter {

    private BaseLFragment[] fragments;

    public MxxFragmentsAdapter(FragmentManager fragmentManager, BaseLFragment[] fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }


    @Override
    public BaseLFragment getItem(int position) {
        BaseLFragment fragment = fragments[position];
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position].getTitle();
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((Fragment) object).getView());
        super.destroyItem(container, position, object);
    }
}