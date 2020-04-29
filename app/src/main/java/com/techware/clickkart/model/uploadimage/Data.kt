package com.techware.clickkart.model.uploadimage


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("fullname")
    var fullname: String = "",
    @SerializedName("image")
    var image: String = "",
    @SerializedName("user_id")
    var userId: String = ""
)