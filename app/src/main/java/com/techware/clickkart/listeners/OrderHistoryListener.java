package com.techware.clickkart.listeners;

import com.techware.clickkart.model.orderhistory.OrderHistoryBean;

/**
 * Created by Developer on 26 December, 2019.
 * Package com.techware.clickkart.listeners
 * Project ClickKart
 */
public interface OrderHistoryListener {
    void onLoadCompleted(OrderHistoryBean orderHistoryBean);

    void onLoadFailed(String error);
}
