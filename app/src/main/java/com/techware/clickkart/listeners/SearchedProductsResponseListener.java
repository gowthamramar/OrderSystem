package com.techware.clickkart.listeners;


import com.techware.clickkart.model.searchproduct.SearchProductBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

public interface SearchedProductsResponseListener {

    void onLoadCompleted(SearchProductBean searchProductBean);

    void onLoadFailed(String error);
}
