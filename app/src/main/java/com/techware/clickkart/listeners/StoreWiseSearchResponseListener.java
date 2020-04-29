package com.techware.clickkart.listeners;


import com.techware.clickkart.model.shopbyad.AdResponseBean;
import com.techware.clickkart.model.storewisesearchedproduct.StorewiseSearchProductBean;

public interface StoreWiseSearchResponseListener {

    void onLoadCompleted(StorewiseSearchProductBean storewiseSearchProductBean);

    void onLoadFailed(String error);
}
