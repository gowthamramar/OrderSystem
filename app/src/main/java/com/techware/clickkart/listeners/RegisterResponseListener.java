package com.techware.clickkart.listeners;


import com.techware.clickkart.model.register.RegisterResponseBean;

public interface RegisterResponseListener {

    void onLoadCompleted(RegisterResponseBean registerResponseBean);

    void onLoadFailed(String error);
}
