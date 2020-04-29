package com.techware.clickkart.model.searchstore


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchStoreBean(
        @SerializedName("data")
    var `data`: ArrayList<Data> = arrayListOf(),
        @SerializedName("message")
    var message: String = "",
        @SerializedName("meta")
    var meta: Meta = Meta(),
        @SerializedName("status")
    var status: String = ""
):Serializable