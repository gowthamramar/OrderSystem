package com.techware.clickkart.listeners;

import com.techware.clickkart.model.AuthBean;


/**
 * Created by Jemsheer K D on 28 April, 2017.
 * Package in.techware.dearest.listeners
 * Project Dearest
 */

public interface LoginListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);

}
