package com.techware.clickkart.listeners;


import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.model.shopbystore.StoreListBean;

public interface ShopByCategoriesResponseListener {

    void onLoadCompleted(CategoryListBean categoryListBean);

    void onLoadFailed(String error);
}
