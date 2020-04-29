package com.techware.clickkart.model.editaddress


import com.google.gson.annotations.SerializedName

data class EditBean(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: String = ""
)