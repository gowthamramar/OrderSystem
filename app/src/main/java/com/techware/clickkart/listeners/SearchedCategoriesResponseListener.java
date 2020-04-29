package com.techware.clickkart.listeners;


import com.techware.clickkart.model.searchcategory.SearchedCategoryList;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

public interface SearchedCategoriesResponseListener {

    void onLoadCompleted(SearchedCategoryList searchedCategoryList);

    void onLoadFailed(String error);
}
