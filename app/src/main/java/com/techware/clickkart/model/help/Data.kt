package com.techware.clickkart.model.help


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    var `data`: String = "",
    @SerializedName("identifier")
    var identifier: String = ""
)