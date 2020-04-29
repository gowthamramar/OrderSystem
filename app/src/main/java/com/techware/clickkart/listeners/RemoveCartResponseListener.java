package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.removecart.RemoveItemCart;

public interface RemoveCartResponseListener {

    void onLoadCompleted(RemoveItemCart removeItemCart);

    void onLoadFailed(String error);
}
