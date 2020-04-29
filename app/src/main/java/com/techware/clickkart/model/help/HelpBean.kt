package com.techware.clickkart.model.help


import com.google.gson.annotations.SerializedName

data class HelpBean(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("status")
    var status: String = "",
    var message: String = ""
)