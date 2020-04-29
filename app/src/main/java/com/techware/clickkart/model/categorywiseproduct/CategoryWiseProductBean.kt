package com.techware.clickkart.model.categorywiseproduct


import com.google.gson.annotations.SerializedName

data class CategoryWiseProductBean(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("meta")
    var meta: Meta = Meta(),
    @SerializedName("status")
    var status: String = ""
)