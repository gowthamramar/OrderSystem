package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

public interface AddAddressResponseListener {

    void onLoadCompleted(AddAddressBean addAddressBean);

    void onLoadFailed(String error);
}
