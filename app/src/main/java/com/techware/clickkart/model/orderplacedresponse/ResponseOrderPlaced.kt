package com.techware.clickkart.model.orderplacedresponse


import com.google.gson.annotations.SerializedName

data class ResponseOrderPlaced(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: String = ""
)