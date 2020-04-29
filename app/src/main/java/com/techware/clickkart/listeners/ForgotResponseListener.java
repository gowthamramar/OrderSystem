package com.techware.clickkart.listeners;


import com.techware.clickkart.model.forgotpassword.ForgotPasswordBean;
import com.techware.clickkart.model.login.LoginResponseBean;

public interface ForgotResponseListener {

    void onLoadCompleted(ForgotPasswordBean forgotPasswordBean);

    void onLoadFailed(String error);
}
