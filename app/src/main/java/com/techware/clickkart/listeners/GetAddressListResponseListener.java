package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.getaddress.GetAddressBean;

public interface GetAddressListResponseListener {

    void onLoadCompleted(GetAddressBean getAddressBean);

    void onLoadFailed(String error);
}
