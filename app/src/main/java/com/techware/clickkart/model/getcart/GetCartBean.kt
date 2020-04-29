package com.techware.clickkart.model.getcart


import com.google.gson.annotations.SerializedName

data class GetCartBean(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: String = ""
)