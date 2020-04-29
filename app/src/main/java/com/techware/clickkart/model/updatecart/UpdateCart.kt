package com.techware.clickkart.model.updatecart


import com.google.gson.annotations.SerializedName

data class UpdateCart(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("status")
    var status: String = ""
)