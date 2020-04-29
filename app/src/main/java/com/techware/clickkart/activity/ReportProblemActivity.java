package com.techware.clickkart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.formatter.IFillFormatter;
import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.ReportResponseListener;
import com.techware.clickkart.model.reportproblem.ReportBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.util.AppConstants;
import com.techware.clickkart.widgets.CustomTextView;

import static com.techware.clickkart.app.App.getInstance;

public class ReportProblemActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    private LinearLayout llReportBack;
    private EditText txtProblem;
    private CustomTextView btnSend,txtReportEmail,txtReportPhone;
    private String problem;
    private String email;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);
        showToolbar(false,"");
        getPreferenceData();
        initViews();
        initClick();
        setPreferenceData();
    }

    private void setPreferenceData() {
        txtReportEmail.setText(email);
        txtReportPhone.setText(phone);
    }

    private void getPreferenceData() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        email = prfs.getString(AppConstants.PREFERENCE_KEY_EMAIL, "");
        phone=prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_PHONE, "");
        System.out.println("email is"+email+""+phone);

    }

    private void initClick() {
        llReportBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    private void initViews() {
        txtReportEmail=findViewById(R.id.txt_ReportEmail);
        txtReportPhone=findViewById(R.id.txt_ReportPhone);
        txtProblem=findViewById(R.id.txt_problem_description);
        btnSend=findViewById(R.id.txtReportSend);
        llReportBack=findViewById(R.id.ll_report_back);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==llReportBack.getId()){
            finish();
        }
        else if (view.getId()==btnSend.getId()){
            if (App.isNetworkAvailable()){
                performReportProblem();
            }
            else{
                Toast.makeText(this,R.string.no_net, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void performReportProblem() {
        problem=txtProblem.getText().toString();
        JsonObject postData=new JsonObject();
        postData.addProperty("prblm_desc",problem);
        DataManager.performReportProblem(postData, new ReportResponseListener() {
            @Override
            public void onLoadCompleted(ReportBean reportBean) {
                Toast.makeText(ReportProblemActivity.this,R.string.reported_successfully, Toast.LENGTH_SHORT).show();
                txtProblem.setText("");
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(ReportProblemActivity.this,error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finsh(View view) {
        finish();
    }
}
