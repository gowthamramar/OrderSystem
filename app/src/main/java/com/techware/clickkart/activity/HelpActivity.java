package com.techware.clickkart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.techware.clickkart.R;
import com.techware.clickkart.adapter.HelpRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.HelpResponseListener;
import com.techware.clickkart.model.help.HelpBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.util.AppConstants;
import com.techware.clickkart.widgets.CustomTextView;

import static com.techware.clickkart.app.App.getInstance;

public class HelpActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
LinearLayout llBack;
CustomTextView txtReportProblem,txtReport,txtEmail,txtPhone;
    private String email;
    private String phone;
    private RecyclerView recyclerViewHelp;
    RecyclerView helpRecyclerView;
    RecyclerView.LayoutManager helpLayoutManager;
    HelpRecyclerViewAdapter helpRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        showToolbar(false,"");
        initViews();
        getSharedPreferenceData();
        setData();
        initClick();
        setHelpRecyclerView();
        fetchHelpDetails();
    }

    private void setHelpRecyclerView() {
        helpRecyclerView = findViewById(R.id.rcvw_help);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewHelp.setLayoutManager(layoutManager);
        recyclerViewHelp.setItemAnimator(new DefaultItemAnimator());
    }

    private void fetchHelpDetails() {
        if (App.isNetworkAvailable()) {
            helpDetails();
        } else {
            Toast.makeText(HelpActivity.this,R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void helpDetails() {
        DataManager.fetchHelp(new HelpResponseListener() {
            @Override
            public void onLoadCompleted(HelpBean helpBean) {
                populateHelpDetails(helpBean);
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });

    }

    private void populateHelpDetails(HelpBean helpBean) {
        helpRecyclerViewAdapter = new HelpRecyclerViewAdapter(helpBean, HelpActivity.this);
        helpRecyclerView.setAdapter(helpRecyclerViewAdapter);
    }

    private void setData() {
        txtEmail=findViewById(R.id.txt_help_email);
        txtPhone=findViewById(R.id.txt_help_phone);
        txtEmail.setText(email);
        txtPhone.setText(phone);

    }

    private void getSharedPreferenceData() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        email = prfs.getString(AppConstants.PREFERENCE_KEY_EMAIL, "");
        phone=prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_PHONE, "");
        System.out.println("email is"+email+""+phone);
    }

    private void initClick() {
        llBack.setOnClickListener(this);
        txtReportProblem.setOnClickListener(this);
        txtReport.setOnClickListener(this);
    }

    private void initViews() {
        llBack=findViewById(R.id.ll_help_back);
        recyclerViewHelp=findViewById(R.id.rcvw_help);
        txtReportProblem=findViewById(R.id.txt_report_a_problem);
        txtReport=findViewById(R.id.txt_report_a_problem);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==llBack.getId()){
            finish();
        }
        else if (view.getId()==txtReportProblem.getId()){
            txtReport.setTextColor(Color.parseColor("#43aee5"));
           txtReport.setBackground(this.getResources().getDrawable(R.drawable.report_color_rounded));
            startActivity(new Intent(HelpActivity.this,ReportProblemActivity.class));
        }

    }

    public void finsh(View view) {
        finish();
    }
}
