package com.techware.clickkart.model.getaddress


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("email")
    var email: String = "",
    @SerializedName("fullname")
    var fullname: String = "",
    @SerializedName("phone_no")
    var phoneNo: String = "",
    @SerializedName("user_id")
    var userId: String = "",
    var image: String = ""
)