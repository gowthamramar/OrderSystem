package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.updatecart.UpdateCart;

public interface UpdateCartResponseListener {

    void onLoadCompleted(UpdateCart updateCart);

    void onLoadFailed(String error);
}
