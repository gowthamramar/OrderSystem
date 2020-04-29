package com.techware.clickkart.listeners;

import com.techware.clickkart.model.zipcode.ZipCodeResponseBean;

/**
 * Created by Developer on 20 November, 2019.
 * Package com.techware.clickkart.listeners
 * Project ClickKart
 */
public interface ZipCodeListener {
    void onLoadCompleted(ZipCodeResponseBean zipCodeResponseBean);

    void onLoadFailed(String error);
}
