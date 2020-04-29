package com.techware.clickkart.listeners;


import com.techware.clickkart.model.changepassword.ChangePasswordBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

public interface ChangepasswordResponseListener {

    void onLoadCompleted(ChangePasswordBean changePasswordBean);

    void onLoadFailed(String error);
}
