package com.techware.clickkart.listeners;


import com.techware.clickkart.model.CategoryWiseStore.CategoryWiseStoreResponseBean;
import com.techware.clickkart.model.categoryliststore.StoreListWiseCategory;

public interface StoreWiseStoreResponseListener {

    void onLoadCompleted(StoreListWiseCategory storeListBean);

    void onLoadFailed(String error);
}
