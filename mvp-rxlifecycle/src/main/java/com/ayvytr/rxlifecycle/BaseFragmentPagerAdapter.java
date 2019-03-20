package com.ayvytr.rxlifecycle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Do
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;
    @Nullable
    private List<String> mTitles;

    public BaseFragmentPagerAdapter(FragmentManager fm, @NonNull List<Fragment> list) {
        this(fm, list, null);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, @NonNull List<Fragment> list, @Nullable List<String> titles) {
        super(fm);
        this.mList = list;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mList.set(position, fragment);
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitles == null || mTitles.size() <= position) {
            return super.getPageTitle(position);
        }

        return mTitles.get(position);
    }

    @Nullable
    public Fragment getFragment(int position) {
        if(position > 0 && position < mList.size()) {
            return mList.get(position);
        }

        return null;
    }

    public void clear() {
        if(!mList.isEmpty()) {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
