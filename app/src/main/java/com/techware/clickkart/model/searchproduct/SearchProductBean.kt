package com.techware.clickkart.model.searchproduct


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchProductBean(
        @SerializedName("data")
    var `data`: ArrayList<Data> = arrayListOf(),
        @SerializedName("message")
    var message: String = "",
        @SerializedName("meta")
    var meta: Meta = Meta(),
        @SerializedName("status")
    var status: String = ""
):Serializable