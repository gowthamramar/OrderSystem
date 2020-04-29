package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.getcart.GetCartBean;

public interface CartListResponseListener {

    void onLoadCompleted(GetCartBean bean);

    void onLoadFailed(String error);
}
