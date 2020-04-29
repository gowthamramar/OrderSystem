package com.techware.clickkart.model

import kotlin.collections.ArrayList

/**
 * Created by Jemsheer K D on 24 April, 2017.
 * Package in.techware.dearest.model
 * Project Dearest
 */

class CountryListBean : BaseBean() {
    var countries: ArrayList<CountryBean> = ArrayList()

    fun search(query: String): ArrayList<CountryBean> {
        val list = ArrayList<CountryBean>()
        for (bean in countries) {
            if (bean.dialCode.toLowerCase().contains(query.toLowerCase())
                    || bean.name.toLowerCase().contains(query.toLowerCase())) {
                list.add(bean)
            }
        }
        return list
    }

}
