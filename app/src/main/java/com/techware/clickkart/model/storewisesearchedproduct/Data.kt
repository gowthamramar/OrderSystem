package com.techware.clickkart.model.storewisesearchedproduct


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("category_id")
    var categoryId: String = "",
    @SerializedName("product_id")
    var productId: String = "",
    @SerializedName("product_image")
    var productImage: String = "",
    @SerializedName("product_name")
    var productName: String = "",
    @SerializedName("product_price")
    var productPrice: String = "",
    @SerializedName("store_id")
    var storeId: String = ""
):Serializable