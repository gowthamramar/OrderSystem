package com.techware.clickkart.model.CategoryWiseStore;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.techware.clickkart.model.categorywisestore.Data;
import com.techware.clickkart.model.categorywisestore.Meta;


@SuppressWarnings("unused")
public class CategoryWiseStoreResponseBean implements Serializable {

    @SerializedName("data")
    private List<Data> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("meta")
    private Meta mMeta;
    @SerializedName("status")
    private String mStatus;

    public List<Data> getData() {
        return mData;
    }

    public void setData(List<Data> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        mMeta = meta;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
