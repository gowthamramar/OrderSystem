package com.techware.clickkart.listeners;


import com.techware.clickkart.model.help.HelpBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

public interface HelpResponseListener {

    void onLoadCompleted(HelpBean helpBean);

    void onLoadFailed(String error);
}
