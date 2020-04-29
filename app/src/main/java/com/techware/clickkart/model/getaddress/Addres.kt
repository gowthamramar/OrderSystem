package com.techware.clickkart.model.getaddress


import com.google.gson.annotations.SerializedName

data class Addres(
    @SerializedName("address_id")
    var addressId: String = "",
    @SerializedName("landmark")
    var landmark: String = "",
    @SerializedName("street_address")
    var streetAddress: String = "",
    @SerializedName("type")
    var type: String = "",
    @SerializedName("zip_code")
    var zipCode: String = "",
    var instruction: String = ""
)