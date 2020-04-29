
package com.techware.clickkart.model.shopbyad;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("add_id")
    private String mAddId;
    @SerializedName("add_name")
    private String mAddName;
    @SerializedName("ending_time")
    private String mEndingTime;
    @SerializedName("image")
    private String mImage;
    @SerializedName("starting_time")
    private String mStartingTime;
    @SerializedName("status")
    private String mStatus;

    public String getAddId() {
        return mAddId;
    }

    public void setAddId(String addId) {
        mAddId = addId;
    }

    public String getAddName() {
        return mAddName;
    }

    public void setAddName(String addName) {
        mAddName = addName;
    }

    public String getEndingTime() {
        return mEndingTime;
    }

    public void setEndingTime(String endingTime) {
        mEndingTime = endingTime;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getStartingTime() {
        return mStartingTime;
    }

    public void setStartingTime(String startingTime) {
        mStartingTime = startingTime;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
