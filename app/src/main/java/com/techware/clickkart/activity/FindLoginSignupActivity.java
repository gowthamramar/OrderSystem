package com.techware.clickkart.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.app.App;
import com.techware.clickkart.config.Config;
import com.techware.clickkart.listeners.ForgotResponseListener;
import com.techware.clickkart.listeners.LoginResponseListener;
import com.techware.clickkart.listeners.RegisterResponseListener;
import com.techware.clickkart.listeners.ZipCodeListener;
import com.techware.clickkart.model.forgotpassword.ForgotPasswordBean;
import com.techware.clickkart.model.login.LoginResponseBean;
import com.techware.clickkart.model.register.RegisterResponseBean;
import com.techware.clickkart.model.zipcode.ZipCodeResponseBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.widgets.CustomEditText;
import com.techware.clickkart.widgets.CustomTextView;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.content.ContextCompat;

public class FindLoginSignupActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    ViewFlipper viewFlipper;
    LinearLayout ll_find, ll_login, ll_signup, llShowPassword;
    ImageView iv_find, iv_login, iv_signup,iv_showPassword;
    View find_View, login_View, signUp_View;
    CustomTextView login_btn, btnSignUp, btnFind, txtResetBtn;
    CustomEditText edtFullName, edtEmail, edtDistrict, edtPhoneNumber, edtCreatePassword, edtConfirmPassword, logEmail, logPassword, txtZipCode, txtForgotPhone, txtForgotPassword;
    String strFullName, strEmail, strDistrict, strPhoneNumber, strCreatePassword, strConfirmPassword;
    String strLogEmail, strLogPassword;
    private boolean showClicked;
    private String zipCode;
    Dialog dialog;
    String forgotStrPhone, forgotStrPassword;
    CustomTextView forgotPassword;
    private Boolean isSelectedCity;
    private int value=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_login_signup);
        getIntentData();
        initViews();
        setForgotPasswordDialog();
        initClick();
        setStatusColor();
        showToolbar(false, "");
    }

    private void getIntentData() {
        if (getIntent().hasExtra("isSelectedCity")) {
            isSelectedCity = getIntent().getBooleanExtra("isSelectedCity", true);
        }
    }

    private void setForgotPasswordDialog() {
        forgotPassword = findViewById(R.id.txtForgot);
        dialog = new Dialog(FindLoginSignupActivity.this, R.style.ThemeDialogCustom_NoBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forgot_password);
        txtResetBtn = dialog.findViewById(R.id.txt_resetbtn);
        txtForgotPhone = dialog.findViewById(R.id.txt_phone_forgot);
        txtForgotPassword = dialog.findViewById(R.id.txt_forgot_password);
    }

    private void setStatusColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white)); //status bar or the time bar at the top
        }
    }

    private void initClick() {
        txtResetBtn.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        ll_find.setOnClickListener(this);
        ll_login.setOnClickListener(this);
        ll_signup.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        llShowPassword.setOnClickListener(this);
        btnFind.setOnClickListener(this);
    }

    private void initViews() {
        btnFind = findViewById(R.id.txtBtnFind);
        txtZipCode = findViewById(R.id.txt_zipCode);
        iv_showPassword=findViewById(R.id.iv_showPassword);
        llShowPassword = findViewById(R.id.ll_show_password);
        logEmail = findViewById(R.id.txtLogEmail);
        logPassword = findViewById(R.id.txtLogPassword);
        edtFullName = findViewById(R.id.txt_fullName);
        edtEmail = findViewById(R.id.txt_EmailAddress);
        edtDistrict = findViewById(R.id.txt_district);
        edtPhoneNumber = findViewById(R.id.txt_phoneNumber);
        edtCreatePassword = findViewById(R.id.txtCreatePassword);
        edtConfirmPassword = findViewById(R.id.txt_confirmPassword);
        btnSignUp = findViewById(R.id.txtSignUpBtn);
        iv_find = findViewById(R.id.iv_find);
        iv_login = findViewById(R.id.iv_login);
        iv_signup = findViewById(R.id.iv_signup);
        find_View = findViewById(R.id.find_view);
        login_View = findViewById(R.id.login_view);
        signUp_View = findViewById(R.id.signup_view);
        viewFlipper = findViewById(R.id.viewFlipper_login);
        ll_find = findViewById(R.id.ll_find);
        ll_login = findViewById(R.id.ll_login);
        ll_signup = findViewById(R.id.ll_signup);
        login_btn = findViewById(R.id.login_btn);
        if (isSelectedCity) {
            viewFlipper.setDisplayedChild(1);
            login_View.setVisibility(View.VISIBLE);
            ll_signup.setVisibility(View.VISIBLE);
            ll_login.setVisibility(View.VISIBLE);
            iv_login.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.login_color_with_icon));
        } else {
            viewFlipper.setDisplayedChild(0);
            ll_find.setVisibility(View.VISIBLE);
            find_View.setVisibility(View.VISIBLE);
            signUp_View.setVisibility(View.GONE);
            ll_signup.setVisibility(View.GONE);
            login_View.setVisibility(View.GONE);
            ll_login.setVisibility(View.GONE);

        }

    }

    @Override
    public void onClick(View view) {
        if (txtResetBtn.getId() == view.getId()) {
            forgotStrPhone = txtForgotPhone.getText().toString();
            forgotStrPassword = txtForgotPassword.getText().toString();
            if (collectForgotData()) {
                if (App.isNetworkAvailable()) {
                    performForgotPassword();
                } else {
                    Toast.makeText(FindLoginSignupActivity.this, "No Network Avialable", Toast.LENGTH_SHORT).show();
                }

            }
        }
        if (forgotPassword.getId() == view.getId()) {
            dialog.show();
            txtForgotPassword.setText("");
            txtForgotPhone.setText("");
        }
        if (view.getId() == ll_find.getId()) {

            if (viewFlipper.getDisplayedChild() == 1) {
                viewFlipper.setDisplayedChild(0);
                find_View.setVisibility(View.VISIBLE);
                login_View.setVisibility(View.GONE);
                signUp_View.setVisibility(View.GONE);
                iv_find.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.find_icon));
                iv_login.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.login_icon));
                iv_signup.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.signup_icon));

            }

            viewFlipper.setDisplayedChild(0);
            find_View.setVisibility(View.VISIBLE);
            login_View.setVisibility(View.GONE);
            signUp_View.setVisibility(View.GONE);
            iv_find.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.find_icon_with_color));
            iv_login.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.login_icon));
            iv_signup.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.signup_icon));
        }
        if (view.getId() == ll_login.getId()) {
            if (viewFlipper.getDisplayedChild() == 0) {
                viewFlipper.setDisplayedChild(1);
                login_View.setVisibility(View.VISIBLE);
                find_View.setVisibility(View.GONE);
                signUp_View.setVisibility(View.GONE);
                iv_find.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.find_icon));
                iv_login.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.login_color_with_icon));
                iv_signup.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.signup_icon));
            }
            if (viewFlipper.getDisplayedChild() == 2) {
                viewFlipper.setDisplayedChild(1);
                login_View.setVisibility(View.VISIBLE);
                find_View.setVisibility(View.GONE);
                signUp_View.setVisibility(View.GONE);
                iv_find.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.find_icon));
                iv_login.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.login_color_with_icon));
                iv_signup.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.signup_icon));
            }

            viewFlipper.setDisplayedChild(1);
            login_View.setVisibility(View.VISIBLE);
            find_View.setVisibility(View.GONE);
            signUp_View.setVisibility(View.GONE);
            iv_login.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.login_color_with_icon));
            iv_find.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.find_icon));
            iv_signup.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.signup_icon));
        }
        if (view.getId() == ll_signup.getId()) {
            if (viewFlipper.getDisplayedChild() == 0) {
                viewFlipper.setDisplayedChild(2);
                signUp_View.setVisibility(View.VISIBLE);
                find_View.setVisibility(View.GONE);
                login_View.setVisibility(View.GONE);
                iv_find.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.find_icon));
                iv_login.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.login_icon));
                iv_signup.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.signup_color_with_icon));

            }
            if (viewFlipper.getDisplayedChild() == 1) {
                viewFlipper.setDisplayedChild(2);
                signUp_View.setVisibility(View.VISIBLE);
                find_View.setVisibility(View.GONE);
                login_View.setVisibility(View.GONE);
                iv_find.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.find_icon));
                iv_login.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.login_icon));
                iv_signup.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.signup_color_with_icon));
            }

            viewFlipper.setDisplayedChild(2);
            signUp_View.setVisibility(View.VISIBLE);
            find_View.setVisibility(View.GONE);
            login_View.setVisibility(View.GONE);
            iv_find.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.find_icon));
            iv_login.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.login_icon));
            iv_signup.setImageDrawable(ContextCompat.getDrawable(FindLoginSignupActivity.this, R.drawable.signup_color_with_icon));
        }
        if (view.getId() == login_btn.getId()) {
            if (collectLoginData()) {
                if (App.isNetworkAvailable()) {
                    loginUser();
                } else {
                    Toast.makeText(FindLoginSignupActivity.this, "No Network Avialable", Toast.LENGTH_SHORT).show();
                }

            }

        }
        if (view.getId() == btnSignUp.getId()) {
            if (collectData()) {
                if (App.isNetworkAvailable()) {
                    registerUser();
                } else {
                    Toast.makeText(FindLoginSignupActivity.this, R.string.no_net, Toast.LENGTH_SHORT).show();
                }
            }

        }
        if (view.getId() == btnFind.getId()) {
            if (collectZipCode()) {
                if (App.isNetworkAvailable()) {
                    findZipCode();
                } else {
                    Toast.makeText(FindLoginSignupActivity.this, R.string.no_net, Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (view.getId() == llShowPassword.getId()) {
            if (!logPassword.getText().toString().equalsIgnoreCase("")) {
                if (showClicked) {
                    logPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    logPassword.setSelection(logPassword.getText().length());
                    showClicked = false;
                    iv_showPassword.setImageResource(R.drawable.colored_eye);
                } else {
                    if (value == 0) {
                        logPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        value++;
                        logPassword.setSelection(logPassword.getText().length());
                        iv_showPassword.setImageResource(R.drawable.colored_eye);
                    } else {
                        logPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        showClicked = true;
                        logPassword.setSelection(logPassword.getText().length());
                        iv_showPassword.setImageResource(R.drawable.show_password_icon);

                    }

                }

            }
        }
    }

    private void performForgotPassword() {
        JsonObject postData = new JsonObject();
        postData.addProperty("phone_no", forgotStrPhone);
        postData.addProperty("password", forgotStrPassword);
        DataManager.performForgotPasssword(postData, new ForgotResponseListener() {
            @Override
            public void onLoadCompleted(ForgotPasswordBean forgotPasswordBean) {
                Toast.makeText(FindLoginSignupActivity.this, "Password Change Successfully,You Can Login Now", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(FindLoginSignupActivity.this, "User Not Exist", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean collectForgotData() {
        if (forgotStrPhone.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a phone", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((forgotStrPhone.length() < 10) || (forgotStrPhone.length() > 12)) {
            Toast.makeText(FindLoginSignupActivity.this, "Phone Number is not valid", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!forgotStrPassword.equalsIgnoreCase("") && !(forgotStrPassword.length() >= 8)) {
            Toast.makeText(this, "Password must contains minimum 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (forgotStrPassword.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void findZipCode() {
        JsonObject postData = new JsonObject();
        postData.addProperty("zip_code", zipCode);
        DataManager.findZip(postData, new ZipCodeListener() {
            @Override
            public void onLoadCompleted(ZipCodeResponseBean zipCodeResponseBean) {
                Toast.makeText(FindLoginSignupActivity.this, "Registered Successfully...", Toast.LENGTH_SHORT).show();
                Config.getInstance().setLocation(zipCodeResponseBean.getData().getLocation());
                Config.getInstance().setCity_id(zipCodeResponseBean.getData().getCityId());
                Config.getInstance().setZipCode(zipCodeResponseBean.getData().getZipCode());
                Config.getInstance().setCitySelected(true);
                App.saveToken();
                startActivity(new Intent(FindLoginSignupActivity.this, HomePageActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(FindLoginSignupActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean collectZipCode() {
        zipCode = txtZipCode.getText().toString().trim();
        if (zipCode.equalsIgnoreCase("")) {
            Toast.makeText(FindLoginSignupActivity.this, "Zip Code is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loginUser() {
        JsonObject postData = new JsonObject();
        postData.addProperty("email", strLogEmail);
        postData.addProperty("password", strLogPassword);
        DataManager.loginUser(postData, new LoginResponseListener() {
            @Override
            public void onLoadCompleted(LoginResponseBean loginResponseBean) {
                System.out.println("login data" + loginResponseBean.getData().getFullname());
                if (!loginResponseBean.getData().getCityId().equalsIgnoreCase("")) {
                    Config.getInstance().setCitySelected(true);
                }
                App.saveToken(loginResponseBean);
                App.saveToken();
                startActivity(new Intent(FindLoginSignupActivity.this, HomePageActivity.class)
                        .putExtra("bean",  loginResponseBean.getData())
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(FindLoginSignupActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean collectLoginData() {
        strLogEmail = logEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        strLogPassword = logPassword.getText().toString().trim();
        if (strLogEmail.equalsIgnoreCase("")) {
            Toast.makeText(FindLoginSignupActivity.this, "Email is Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!strLogEmail.matches(emailPattern) && !strLogEmail.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strLogPassword.equalsIgnoreCase("")) {
            Toast.makeText(FindLoginSignupActivity.this, "Password is Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!strLogPassword.equalsIgnoreCase("") && !(strLogPassword.length() >= 8)) {
            Toast.makeText(this, "Password must contains minimum 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!strLogPassword.equalsIgnoreCase("") && !(strLogPassword.length() <= 25)) {
            Toast.makeText(this, "Password must contains maximum 25 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean collectData() {
        strFullName = edtFullName.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strDistrict = edtDistrict.getText().toString().trim();
        strPhoneNumber = edtPhoneNumber.getText().toString().trim();
        strCreatePassword = edtCreatePassword.getText().toString().trim();
        strConfirmPassword = edtConfirmPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile("[\\W\\d\\s]+");
        Matcher matcher = pattern.matcher(strFullName.trim());
        if (strFullName.isEmpty()) {
            Toast.makeText(FindLoginSignupActivity.this, "Name is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
         else if (!strFullName.matches("[a-zA-Z ]+")) {
            Toast.makeText(getApplicationContext(), "Name contains only  characters", Toast.LENGTH_SHORT).show();
            return false;}
         else if ( (strFullName.length() > 25)) {
            Toast.makeText(getApplicationContext(), "Name must contains maximum 25 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strEmail.equalsIgnoreCase("")) {
            Toast.makeText(FindLoginSignupActivity.this, "Email is Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!strEmail.matches(emailPattern) && !strEmail.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strPhoneNumber.equalsIgnoreCase("")) {

            Toast.makeText(FindLoginSignupActivity.this, "Phone Number is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((strPhoneNumber.length() < 10) || (strPhoneNumber.length() > 12)) {
            Toast.makeText(FindLoginSignupActivity.this, "Phone Number is not valid", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strDistrict.equalsIgnoreCase("")) {
            Toast.makeText(FindLoginSignupActivity.this, "District is Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strCreatePassword.equalsIgnoreCase("")) {
            Toast.makeText(FindLoginSignupActivity.this, "Create a Password ", Toast.LENGTH_SHORT).show();

            return false;
        } else if (!strCreatePassword.equalsIgnoreCase("") && !(strCreatePassword.length() >= 8)) {
            Toast.makeText(this, "Password must contains minimum 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!strCreatePassword.equalsIgnoreCase("") && !(strCreatePassword.length() <= 25)) {
            Toast.makeText(this, "Password must contains maximum 25 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (strConfirmPassword.equalsIgnoreCase("")) {
            Toast.makeText(FindLoginSignupActivity.this, "Confirm a Password ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!strConfirmPassword.equals(strCreatePassword)) {
            Toast.makeText(FindLoginSignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void registerUser() {
        JsonObject postData = new JsonObject();
        postData.addProperty("fullname", strFullName);
        postData.addProperty("email", strEmail);
        postData.addProperty("phone_no", strPhoneNumber);
        postData.addProperty("district", strDistrict);
        postData.addProperty("password", strCreatePassword);
        postData.addProperty("otp_flag", "");


        DataManager.registerUser(postData, new RegisterResponseListener() {
            @Override
            public void onLoadCompleted(RegisterResponseBean registerResponseBean) {
                App.saveToken(registerResponseBean);
                find_View.setVisibility(View.VISIBLE);
                viewFlipper.getChildAt(2).setVisibility(View.GONE);
                ll_signup.setVisibility(View.GONE);
                signUp_View.setVisibility(View.GONE);
                ll_find.setVisibility(View.VISIBLE);
                viewFlipper.setDisplayedChild(0);
                viewFlipper.getChildAt(1).setVisibility(View.GONE);
                ll_login.setVisibility(View.GONE);
                login_View.setVisibility(View.GONE);

            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(FindLoginSignupActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
