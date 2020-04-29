package com.techware.clickkart.model.orderhistory


import com.google.gson.annotations.SerializedName

data class OrderHistoryDataBean(
    @SerializedName("booking_id")
    var bookingId: String = "",
    @SerializedName("scheduled_date")
    var scheduledDate: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("total_amount")
    var totalAmount: String = ""
)