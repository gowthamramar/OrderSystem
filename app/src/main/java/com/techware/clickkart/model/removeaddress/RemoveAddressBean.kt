package com.techware.clickkart.model.removeaddress


import com.google.gson.annotations.SerializedName

data class RemoveAddressBean(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: String = ""
)