package com.techware.clickkart.model.editaddress


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address_id")
    var addressId: String = "",
    @SerializedName("landmark")
    var landmark: String = "",
    @SerializedName("street_address")
    var streetAddress: String = "",
    @SerializedName("type")
    var type: String = "",
    @SerializedName("user_id")
    var userId: String = "",
    @SerializedName("zip_code")
    var zipCode: String = ""
)