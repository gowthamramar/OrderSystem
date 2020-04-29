package com.techware.clickkart.model.searchproduct


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("description")
    var description: String = "",
    @SerializedName("product_name")
    var productName: String = "",
    @SerializedName("product_price")
    var productPrice: String = "",
    @SerializedName("store_name")
    var storeName: String = "",
     var store_id: String ="",
    var product_id: String ="",
    var product_image: String ="",
    var start_time: String = "",
    var end_time: String = ""
):Serializable