package com.techware.clickkart.listeners;


import com.techware.clickkart.model.LocationBean;
import com.techware.clickkart.model.locationbean.LocationResponseBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

public interface LocationResponseListener {

    void onLoadCompleted(LocationResponseBean locationResponseBean);

    void onLoadFailed(String error);
}
