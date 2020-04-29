package com.techware.clickkart.model.orderplacedresponse


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("booking_date")
    var bookingDate: String = "",
    @SerializedName("booking_id")
    var bookingId: String = "",
    @SerializedName("order_id")
    var orderId: String = "",
    @SerializedName("order_time")
    var orderTime: String = "",
    @SerializedName("product_id")
    var productId: String = "",
    @SerializedName("scheduled_date")
    var scheduledDate: String = "",
    @SerializedName("scheduled_time")
    var scheduledTime: String = "",
    @SerializedName("store_id")
    var storeId: String = ""
)