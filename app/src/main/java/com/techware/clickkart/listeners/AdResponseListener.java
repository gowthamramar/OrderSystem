package com.techware.clickkart.listeners;


import com.techware.clickkart.model.pastorders.PastOrberBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

public interface AdResponseListener {

    void onLoadCompleted(AdResponseBean adResponseBean);

    void onLoadFailed(String error);
}
