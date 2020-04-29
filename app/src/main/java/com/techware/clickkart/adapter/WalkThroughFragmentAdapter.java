package com.techware.clickkart.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.techware.clickkart.activity.WalkThroughActivity;
import com.techware.clickkart.fragments.WalkThroughFragment;

/**
 * Created by Praveen Prasad on 01 August, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class WalkThroughFragmentAdapter extends FragmentPagerAdapter {
    private int slideCount;
    private Context context;
    private String[] title;
    private String[] description;
    private int[] images;

    public WalkThroughFragmentAdapter(FragmentManager fm, int slideCount, String[] title, String[] description, int[] images, WalkThroughActivity walkThroughActivity) {
        super(fm);
        this.slideCount=slideCount;
        this.context=walkThroughActivity;
        this.title=title;
        this.description=description;
        this.images=images;
    }

    @Override
    public Fragment getItem(int position) {
        return new WalkThroughFragment(title[position],description[position],images[position],context);
    }

    @Override
    public int getCount() {
        return slideCount;
    }
}
