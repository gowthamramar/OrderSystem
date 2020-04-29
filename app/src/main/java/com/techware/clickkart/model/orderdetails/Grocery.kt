package com.techware.clickkart.model.orderdetails


import com.google.gson.annotations.SerializedName

data class Grocery(
    @SerializedName("product_id")
    var productId: String = "",
    @SerializedName("product_image")
    var productImage: String = "",
    @SerializedName("product_name")
    var productName: String = "",
    @SerializedName("product_price")
    var productPrice: String = "",
    @SerializedName("quantity")
    var quantity: String = "",
    @SerializedName("Total Amount")
    var totalAmount: String = ""
)