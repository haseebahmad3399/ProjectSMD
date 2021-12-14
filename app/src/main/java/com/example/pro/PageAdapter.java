package com.example.pro;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private final int totalTabs;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {

        super(fm);
        this.totalTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1){
            System.out.println("On video Fragment");
            return new VideoFragment();
        }
        return new ImageFragment();
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
