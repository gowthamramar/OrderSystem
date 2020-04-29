package com.techware.clickkart.listeners;


import com.techware.clickkart.model.PeopleFavouriteStores.PeopleFavouriteBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

public interface PeopleFavouriteResponseListener {

    void onLoadCompleted(PeopleFavouriteBean peopleFavouriteBean);

    void onLoadFailed(String error);
}
