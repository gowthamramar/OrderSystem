package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.uploadimage.ProfileImagBean;

public interface UploadProfileResponseListener {

    void onLoadCompleted(ProfileImagBean profileImagBean);

    void onLoadFailed(String error);
}
