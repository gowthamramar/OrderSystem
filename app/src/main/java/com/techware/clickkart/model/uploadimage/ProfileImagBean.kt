package com.techware.clickkart.model.uploadimage


import com.google.gson.annotations.SerializedName

data class ProfileImagBean(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("status")
    var status: String = ""
)