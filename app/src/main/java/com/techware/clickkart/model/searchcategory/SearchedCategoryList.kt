package com.techware.clickkart.model.searchcategory


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchedCategoryList(
        @SerializedName("data")
    var `data`: ArrayList<Data> = arrayListOf(),
        @SerializedName("message")
    var message: String = "",
        @SerializedName("meta")
    var meta: Meta = Meta(),
        @SerializedName("status")
    var status: String = ""
):Serializable