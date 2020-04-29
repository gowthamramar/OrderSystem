package com.techware.clickkart.model.categoryliststore


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoreListWiseCategory(
        @SerializedName("categoryStores")
    var categoryStores: ArrayList<CategoryStore> = arrayListOf(),
        @SerializedName("message")
    var message: String = "",
        @SerializedName("meta")
    var meta: Meta = Meta(),
        @SerializedName("status")
    var status: String = ""
): Serializable