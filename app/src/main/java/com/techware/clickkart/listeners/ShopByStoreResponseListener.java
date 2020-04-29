package com.techware.clickkart.listeners;


import com.techware.clickkart.model.login.LoginResponseBean;
import com.techware.clickkart.model.shopbystore.StoreListBean;

public interface ShopByStoreResponseListener {

    void onLoadCompleted(StoreListBean storeListBean);

    void onLoadFailed(String error);
}
