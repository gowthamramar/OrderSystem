package com.techware.clickkart.model.searchcategory


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("category_name")
    var categoryName: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("end_time")
    var endTime: String = "",
    @SerializedName("start_time")
    var startTime: String = "",
    @SerializedName("store_id")
    var storeId: String = "",
    @SerializedName("store_image")
    var storeImage: String = "",
    @SerializedName("store_name")
    var storeName: String = ""
):Serializable