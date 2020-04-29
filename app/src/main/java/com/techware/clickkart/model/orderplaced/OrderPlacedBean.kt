package com.techware.clickkart.model.orderplaced


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderPlacedBean(
    @SerializedName("data")
    var `data`: ArrayList<Data> = arrayListOf()
):Serializable