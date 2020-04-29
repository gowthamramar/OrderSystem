package com.techware.clickkart.listeners;


import com.techware.clickkart.model.BasicBean;

public interface BasicListener {

    void onLoadCompleted(BasicBean basicBean);

    void onLoadFailed(String error);
}
