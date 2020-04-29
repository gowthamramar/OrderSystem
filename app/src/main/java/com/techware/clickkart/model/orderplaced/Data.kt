package com.techware.clickkart.model.orderplaced


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("address_id")
    var addressId: String = "",
    @SerializedName("amount")
    var amount: String = "",
    @SerializedName("CategoryId")
    var categoryId: String = "",
    @SerializedName("delivery_charge")
    var deliveryCharge: String = "",
    @SerializedName("payment_method")
    var paymentMethod: String = "",
    @SerializedName("payment_status")
    var paymentStatus: String = "",
    @SerializedName("ProductId")
    var productId: String = "",
    @SerializedName("product_price")
    var productPrice: String = "",
    @SerializedName("quantity")
    var quantity: String = "",
    @SerializedName("scheduled_date")
    var scheduledDate: String = "",
    @SerializedName("scheduled_time")
    var scheduledTime: String = "",
    @SerializedName("store_id")
    var storeId: String = "",
    @SerializedName("tax")
    var tax: String = "",
    @SerializedName("total_amount")
    var totalAmount: Int = 0
):Serializable