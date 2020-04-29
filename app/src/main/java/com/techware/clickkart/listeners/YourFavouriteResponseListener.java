package com.techware.clickkart.listeners;


import com.techware.clickkart.model.PeopleFavouriteStores.PeopleFavouriteBean;
import com.techware.clickkart.model.YourFavouriteBean.YourFavouriteBean;

public interface YourFavouriteResponseListener {

    void onLoadCompleted(YourFavouriteBean yourFavouriteBean);

    void onLoadFailed(String error);
}
