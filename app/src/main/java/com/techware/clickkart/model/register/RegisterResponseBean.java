
package com.techware.clickkart.model.register;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegisterResponseBean {

    @SerializedName("data")
    private Data mData;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("error")
    private String mError;
    @SerializedName("message")
    private String mMessage;

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

    public String getError() {
        return mError;
    }

    public void setError(String mError) {
        this.mError = mError;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
