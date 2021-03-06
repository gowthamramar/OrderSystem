
package com.techware.clickkart.model.shopbystore;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Data implements Serializable {

    @SerializedName("city_id")
    private String mCityId;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("start_time")
    private String mStartTime;
    @SerializedName("store_id")
    private String mStoreId;
    @SerializedName("store_image")
    private String mStoreImage;
    @SerializedName("store_name")
    private String mStoreName;

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getStoreId() {
        return mStoreId;
    }

    public void setStoreId(String storeId) {
        mStoreId = storeId;
    }

    public String getStoreImage() {
        return mStoreImage;
    }

    public void setStoreImage(String storeImage) {
        mStoreImage = storeImage;
    }

    public String getStoreName() {
        return mStoreName;
    }

    public void setStoreName(String storeName) {
        mStoreName = storeName;
    }

}
