package com.ashcorps.githubapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragList = new ArrayList<>();
    private final List<String> mFragTitleList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragList.get(i);
    }

    @Override
    public int getCount() {
        return mFragList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragList.add(fragment);
        mFragTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragTitleList.get(position);
    }
}
