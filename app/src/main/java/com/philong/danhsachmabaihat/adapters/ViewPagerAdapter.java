package com.philong.danhsachmabaihat.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.philong.danhsachmabaihat.fragments.DanhSachFragment;
import com.philong.danhsachmabaihat.fragments.YeuThichFragment;

/**
 * Created by Long on 6/16/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments = {new DanhSachFragment(), new YeuThichFragment()};
    private String[] mTitles = {"Danh sách", "Yêu thích"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
