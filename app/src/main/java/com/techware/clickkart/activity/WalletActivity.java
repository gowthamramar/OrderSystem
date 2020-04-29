package com.techware.clickkart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.techware.clickkart.R;

public class WalletActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
LinearLayout llWalletBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initViews();
        initClick();
        showToolbar(false,"");
    }

    private void initClick() {
        llWalletBack.setOnClickListener(this);
    }

    private void initViews() {
        llWalletBack=findViewById(R.id.ll_wallet_back);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==llWalletBack.getId())
        {
            finish();
        }
    }
}
