
package com.techware.clickkart.model.YourFavouriteBean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@SuppressWarnings("unused")
public class Data implements Serializable {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("store_id")
    private String mStoreId;
    @SerializedName("store_image")
    private String mStoreImage;
    @SerializedName("store_name")
    private String mStoreName;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("start_time")
    private String mStartTime;

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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
