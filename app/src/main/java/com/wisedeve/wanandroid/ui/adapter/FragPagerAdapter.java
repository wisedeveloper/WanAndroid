package com.wisedeve.wanandroid.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Description：
 * Created time：18-6-4 上午10:27
 * author：wisedeve
 * email：wisedeve@163.com
 */
public class FragPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    public FragPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
