package com.techware.clickkart.model.changepassword


import com.google.gson.annotations.SerializedName

data class ChangePasswordBean(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: String = ""
)