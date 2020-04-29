package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.removeaddress.RemoveAddressBean;

public interface RemoveAddressResponseListener {

    void onLoadCompleted(RemoveAddressBean removeAddressBean);

    void onLoadFailed(String error);
}
