package com.techware.clickkart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.techware.clickkart.R;
import com.techware.clickkart.model.bookidaddresid.BookAddressId;

public class PaymentSuccessFullActivity extends AppCompatActivity {
    private final int SPLASH_TIME_OUT = 3000;
    BookAddressId bookAddressId=new BookAddressId();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success_full);
        setActivity();

    }

    private void setActivity() {
        if (getIntent().hasExtra("bean")){
            bookAddressId=(BookAddressId)getIntent().getSerializableExtra("bean");

        }
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    startActivity(new Intent(PaymentSuccessFullActivity.this, OrderDetailsActivity.class)
                            .putExtra("bean",bookAddressId)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));

                }
            }, SPLASH_TIME_OUT);
        }
    }

