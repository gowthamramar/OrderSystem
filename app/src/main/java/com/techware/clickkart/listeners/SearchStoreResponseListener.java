package com.techware.clickkart.listeners;


import com.techware.clickkart.model.searchstore.SearchStoreBean;

public interface SearchStoreResponseListener {

    void onLoadCompleted(SearchStoreBean searchStoreBean);

    void onLoadFailed(String error);
}
