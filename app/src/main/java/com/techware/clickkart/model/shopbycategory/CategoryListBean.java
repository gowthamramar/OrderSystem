
package com.techware.clickkart.model.shopbycategory;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryListBean {

    @SerializedName("data")
    private ArrayList<Data> mData;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("message")
    private String mMessage;

    public List<Data> getData() {
        return mData;
    }

    public void setData(ArrayList<Data> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
