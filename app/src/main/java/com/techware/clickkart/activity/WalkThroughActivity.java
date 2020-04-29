package com.techware.clickkart.activity;

import android.content.Intent;
import android.os.Build;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.WalkThroughFragmentAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.config.Config;
import com.techware.clickkart.widgets.CustomTextView;


public class WalkThroughActivity extends FragmentActivity implements View.OnClickListener {
    ViewPager viewPager;
    WalkThroughFragmentAdapter adapter;
CustomTextView txt_getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        initViews();
        setStatusColor();
        initClick();
    }

    private void initClick() {
        txt_getStarted.setOnClickListener(this);
    }

    private void setStatusColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white)); //status bar or the time bar at the top
        }
    }

    private void initViews() {
        txt_getStarted=findViewById(R.id.txt_getStarted);
        viewPager=findViewById(R.id.walkThroughViewPager);
        String firstTitle=getResources().getString(R.string.walkthrough_first_title);
        String secondTitle=getResources().getString(R.string.walkthrough_second_title);
        String firstDescription=getResources().getString(R.string.walkthrough_first_description);
        String secondDescription=getResources().getString(R.string.walkthrough_second_description);
        String[] title={firstTitle,secondTitle};
        String[] description={firstDescription,secondDescription};
        int[] images={R.drawable.walkthrough_second_pic,R.drawable.walk_first_img};
        adapter= new WalkThroughFragmentAdapter(getSupportFragmentManager(),2,title,description,images,this);
        viewPager.setAdapter(adapter);
        ExtensiblePageIndicator extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        extensiblePageIndicator.initViewPager(viewPager);
        Config.getInstance().setFirstTime(false);
        App.saveToken();
    }

    @Override
    public void onClick(View view) {
     if (view.getId()==txt_getStarted.getId()){
         startActivity(new Intent(WalkThroughActivity.this,FindLoginSignupActivity.class)
                 .putExtra("isSelectedCity",true));
     }
    }
}
