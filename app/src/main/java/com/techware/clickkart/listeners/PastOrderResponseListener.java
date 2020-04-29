package com.techware.clickkart.listeners;


import com.techware.clickkart.model.pastorders.PastOrberBean;
import com.techware.clickkart.model.shopbystore.StoreListBean;

public interface PastOrderResponseListener {

    void onLoadCompleted(PastOrberBean pastOrberBean);

    void onLoadFailed(String error);
}
