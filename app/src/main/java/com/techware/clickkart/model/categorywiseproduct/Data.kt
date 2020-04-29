package com.techware.clickkart.model.categorywiseproduct


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("category_id")
    var categoryId: String = "",
    @SerializedName("end_time")
    var endTime: String = "",
    @SerializedName("product_id")
    var productId: String = "",
    @SerializedName("product_image")
    var productImage: String = "",
    @SerializedName("product_name")
    var productName: String = "",
    @SerializedName("product_price")
    var productPrice: String = "",
    @SerializedName("start_time")
    var startTime: String = "",
    @SerializedName("store_id")
    var storeId: String = "",
    @SerializedName("store_name")
    var storeName: String = ""
)