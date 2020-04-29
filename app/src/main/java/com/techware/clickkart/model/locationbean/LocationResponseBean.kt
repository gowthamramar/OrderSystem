package com.techware.clickkart.model.locationbean


import com.google.gson.annotations.SerializedName

data class LocationResponseBean(
        @SerializedName("data")
        var locationListDataBean: ArrayList<LocationListItemData> = arrayListOf(),
        @SerializedName("message")
        var message: String = "",
        @SerializedName("meta")
        var meta: Meta = Meta(),
        @SerializedName("status")
        var status: String = ""
)