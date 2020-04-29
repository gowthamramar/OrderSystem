package com.techware.clickkart.model.addaddress


import com.google.gson.annotations.SerializedName

data class AddAddressBean(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("status")
    var status: String = "",
    var message: String = "",
    @SerializedName("type")
    var type: String = ""
)