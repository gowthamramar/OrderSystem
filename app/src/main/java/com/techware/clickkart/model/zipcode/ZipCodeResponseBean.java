
package com.techware.clickkart.model.zipcode;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ZipCodeResponseBean {

    @SerializedName("data")
    private Data mData;
    @SerializedName("status")
    private String mStatus;
    private String message;
    private String error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
