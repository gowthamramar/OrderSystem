package com.techware.clickkart.listeners;


import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.editprofile.EditProfileBean;

public interface EditProfileResponseListener {

    void onLoadCompleted(EditProfileBean editProfileBean);

    void onLoadFailed(String error);
}
