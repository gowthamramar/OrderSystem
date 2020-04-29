package com.techware.clickkart.model.getcart


import com.google.gson.annotations.SerializedName

data class Data(
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
    @SerializedName("store_id")
    var storeId: String = ""
)