package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.editaddress.EditBean;

public interface EditAddressResponseListener {

    void onLoadCompleted(EditBean editBean);

    void onLoadFailed(String error);
}
