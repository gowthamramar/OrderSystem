package com.techware.clickkart.model.locationbean


import com.google.gson.annotations.SerializedName

data class LocationListItemData(
    @SerializedName("location")
    var location: String = "",
    @SerializedName("zip_code")
    var zipCode: String = ""
)