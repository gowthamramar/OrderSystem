package com.techware.clickkart.model.orderdetails


import com.google.gson.annotations.SerializedName

data class DeliveryAddress(
    @SerializedName("Landmark")
    var landmark: String = "",
    @SerializedName("Name")
    var name: String = "",
    @SerializedName("Phone Number")
    var phoneNumber: String = "",
    @SerializedName("Street Address")
    var streetAddress: String = "",
    @SerializedName("user_id")
    var userId: String = "",
    @SerializedName("Zip Code")
    var zipCode: String = "",
    var type: String = ""
)