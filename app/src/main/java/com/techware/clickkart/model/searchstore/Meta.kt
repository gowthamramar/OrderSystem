package com.techware.clickkart.model.searchstore


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Meta(
    @SerializedName("current_page")
    var currentPage: String = "",
    @SerializedName("per_page")
    var perPage: Int = 0,
    @SerializedName("total")
    var total: Int = 0,
    @SerializedName("total_pages")
    var totalPages: Int = 0
):Serializable