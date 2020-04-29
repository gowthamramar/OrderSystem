package com.techware.clickkart.model.orderhistory


import com.google.gson.annotations.SerializedName

data class OrderHistoryBean(
        @SerializedName("data")
        var orderHistoryList: ArrayList<OrderHistoryDataBean> = arrayListOf(),
        @SerializedName("message")
        var message: String = "",
        @SerializedName("meta")
        var meta: Meta = Meta(),
        @SerializedName("status")
        var status: String = ""
)