package com.techware.clickkart.model.orderhistory


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("current_page")
    var currentPage: Int = 0,
    @SerializedName("per_page")
    var perPage: Int = 0,
    @SerializedName("total")
    var total: Int = 0,
    @SerializedName("total_pages")
    var totalPages: Int = 0
)