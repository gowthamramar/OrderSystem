
package com.techware.clickkart.model.login;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Data implements Serializable {

    @SerializedName("city_id")
    private String mCityId;
    @SerializedName("district")
    private String mDistrict;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("fullname")
    private String mFullname;
    @SerializedName("image")
    private String mImage;
    @SerializedName("phone_no")
    private String mPhoneNo;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("user_id")
    private String mUserId;
    private String auth_id;
    private String city_name;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String district) {
        mDistrict = district;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFullname() {
        return mFullname;
    }

    public void setFullname(String fullname) {
        mFullname = fullname;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getPhoneNo() {
        return mPhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        mPhoneNo = phoneNo;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
