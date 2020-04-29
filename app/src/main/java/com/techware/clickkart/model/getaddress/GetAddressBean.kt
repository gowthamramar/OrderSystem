package com.techware.clickkart.model.getaddress


import com.google.gson.annotations.SerializedName

data class GetAddressBean(
    @SerializedName("Address")
    var address: List<Addres> = listOf(),
    @SerializedName("status")
    var status: String = "",
    var message: String = "",
    @SerializedName("user data")
    var userData: UserData = UserData()
)