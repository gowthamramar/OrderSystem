package com.techware.clickkart.listeners;


import com.techware.clickkart.model.categorywiseproduct.CategoryWiseProductBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

public interface CategoryWisePrdoductsResponseListener {

    void onLoadCompleted(CategoryWiseProductBean categoryWiseProductBean);

    void onLoadFailed(String error);
}
