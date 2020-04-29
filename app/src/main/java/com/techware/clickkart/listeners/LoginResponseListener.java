package com.techware.clickkart.listeners;


import com.techware.clickkart.model.login.LoginResponseBean;

public interface LoginResponseListener {

    void onLoadCompleted(LoginResponseBean loginResponseBean);

    void onLoadFailed(String error);
}
