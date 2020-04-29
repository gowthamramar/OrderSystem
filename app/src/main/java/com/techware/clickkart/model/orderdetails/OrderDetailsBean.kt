package com.techware.clickkart.model.orderdetails


import com.google.gson.annotations.SerializedName

data class OrderDetailsBean(
    @SerializedName("Booking Date")
    var bookingDate: String = "",
    var message: String = "",
    @SerializedName("Booking ID")
    var bookingID: String = "",
    @SerializedName("Delivery Address")
    var deliveryAddress: DeliveryAddress = DeliveryAddress(),
    @SerializedName("Groceries")
    var groceries: List<Grocery> = listOf(),
    @SerializedName("Scheduled Date")
    var scheduledDate: String = "",
    @SerializedName("Time")
    var time: String = "",
    @SerializedName("Order Status")
    var order_status: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("Store")
    var store: String = ""
)