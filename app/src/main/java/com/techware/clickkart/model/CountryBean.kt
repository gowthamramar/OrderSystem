package com.techware.clickkart.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Jemsheer K D on 24 April, 2017.
 * Package in.techware.dearest.model
 * Project Dearest
 */

class CountryBean : BaseBean(), Comparable<CountryBean> {

    var id: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("dial_code")
    var dialCode: String = ""
    @SerializedName("code")
    var countryCode: String = ""
    var currency: String = ""
    var history: String = ""
    var photo: String = ""
    var countryStatus: Int = 1

    var states: List<StateBean> = ArrayList()

    override fun compareTo(other: CountryBean): Int {
        val bean = other;
        return dialCode.compareTo(bean.dialCode);
    }
}
