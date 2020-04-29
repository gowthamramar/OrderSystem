
package com.techware.clickkart.model.shopbyad;

import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AdResponseBean {

    @SerializedName("data")
    private List<Data> mData;
    @SerializedName("status")
    private String mStatus;

    public List<Data> getData() {
        return mData;
    }

    public void setData(List<Data> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
