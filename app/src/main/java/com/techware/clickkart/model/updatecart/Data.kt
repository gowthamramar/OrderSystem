package com.techware.clickkart.model.updatecart


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("cart_id")
    var cartId: String = "",
    @SerializedName("product_id")
    var productId: String = "",
    @SerializedName("quantity")
    var quantity: String = ""
)