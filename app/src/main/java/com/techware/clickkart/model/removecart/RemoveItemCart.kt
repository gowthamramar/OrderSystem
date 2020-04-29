package com.techware.clickkart.model.removecart


import com.google.gson.annotations.SerializedName

data class RemoveItemCart(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: String = ""
)