package com.techware.clickkart.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.techware.clickkart.fragments.CategoryFragment;
import com.techware.clickkart.fragments.FirstFragment;
import com.techware.clickkart.fragments.HomeFragment;
import com.techware.clickkart.fragments.LastFragment;
import com.techware.clickkart.fragments.StoresFragment;

/**
 * Created by Praveen Prasad on 30 August, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new FirstFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new HomeFragment();
            case 3:
                return new StoresFragment();
            case 4:
                return new LastFragment();
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
