package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.orderplacedresponse.ResponseOrderPlaced;

public interface OrderPlacedResponseListener {

    void onLoadCompleted(ResponseOrderPlaced responseOrderPlaced);

    void onLoadFailed(String error);
}
