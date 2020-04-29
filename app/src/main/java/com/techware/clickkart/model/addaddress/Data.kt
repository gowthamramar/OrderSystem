package com.techware.clickkart.model.addaddress


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    var address: List<String> = listOf(),
    @SerializedName("address_id")
    var addressId: String = "",
    @SerializedName("user_id")
    var userId: String = ""
)