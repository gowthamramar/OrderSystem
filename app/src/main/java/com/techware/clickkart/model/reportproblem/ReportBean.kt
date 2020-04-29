package com.techware.clickkart.model.reportproblem


import com.google.gson.annotations.SerializedName

data class ReportBean(
    @SerializedName("error")
    var error: String = "",
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status")
    var status: String = ""
)