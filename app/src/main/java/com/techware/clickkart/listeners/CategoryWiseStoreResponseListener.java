package com.techware.clickkart.listeners;


import com.techware.clickkart.model.CategoryWiseStore.CategoryWiseStoreResponseBean;

public interface CategoryWiseStoreResponseListener {

    void onLoadCompleted(CategoryWiseStoreResponseBean storeListBean);

    void onLoadFailed(String error);
}
