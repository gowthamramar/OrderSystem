package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.orderdetails.OrderDetailsBean;

public interface OrderDetailsResponseListener {

    void onLoadCompleted(OrderDetailsBean orderDetailsBean);

    void onLoadFailed(String error);
}
