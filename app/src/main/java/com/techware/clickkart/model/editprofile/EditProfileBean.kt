package com.techware.clickkart.model.editprofile


import com.google.gson.annotations.SerializedName

data class EditProfileBean(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: String = ""
)