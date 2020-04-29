package com.techware.clickkart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.techware.clickkart.R;
import com.techware.clickkart.app.App;
import com.techware.clickkart.util.AppConstants;

import static com.techware.clickkart.app.App.getInstance;

public class SplashActivity extends BaseAppCompatNoDrawerActivity {
    private final int SPLASH_TIME_OUT = 3000;
    ImageView splash_icon;
    private String token;
    private Boolean isFirstTime=true;
    private boolean isSelectedCity=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showToolbar(false, "");
        initViews();
        setStatusColor();

    }

    private void setStatusColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white)); //status bar or the time bar at the top
        }

    }
    private void initViews() {
        splash_icon=findViewById(R.id.splash_icon);
    }
    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(splashTask, 2000);

    }
    Runnable splashTask = new Runnable() {
        @Override
        public void run() {
            if (App.isNetworkAvailable()){
                getSharedPreference();
                navigate();
            }
            else{
                Toast.makeText(SplashActivity.this,"No Network available",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void getSharedPreference() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        isFirstTime = prfs.getBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_FIRST_TIME, true);
        isSelectedCity = prfs.getBoolean(AppConstants.PREFERENCE_IS_SELECTED_CITY, false);
        System.out.println("huhi"+isFirstTime);
        System.out.println("token is "+token);
    }

    private void navigate() {
        if (App.checkForToken()&&isSelectedCity==true){
            startActivity(new Intent(SplashActivity.this,HomePageActivity.class));
        }
        else if (App.checkForToken()&&isSelectedCity==false){
            startActivity(new Intent(SplashActivity.this,FindLoginSignupActivity.class)
            .putExtra("isSelectedCity",false));
        }
        else{
           if (isFirstTime){
               startActivity(new Intent(SplashActivity.this,WalkThroughActivity.class));
           }
           else{
               startActivity(new Intent(SplashActivity.this,FindLoginSignupActivity.class)
                       .putExtra("isSelectedCity",true));
           }
        }
    }
}
