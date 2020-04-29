package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.cart.CartBean;

public interface CartResponseListener {

    void onLoadCompleted(CartBean cartBean);

    void onLoadFailed(String error);
}
