package com.techware.clickkart.model.cart


import com.google.gson.annotations.SerializedName

data class CartBean(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("status")
    var status: String = "",
    var message: String = "",
    var error: String = ""

)