package com.techware.clickkart.model

import `in`.techware.dearest.model.ReligionBean
import java.util.*

/**
 * Created by Jemsheer K D on 24 August, 2017.
 * Package `in`.techware.dearest.model
 * Project Dearest
 */
class ReligionListBean : BaseBean() {
    var religions: List<ReligionBean> = ArrayList()

    fun search(query: String): List<ReligionBean> {
        val list = ArrayList<ReligionBean>()
        for (bean in religions) {
            if (bean.name.toLowerCase().contains(query.toLowerCase())
                    || bean.name.toLowerCase().contains(query.toLowerCase())) {
                list.add(bean)
            }
        }
        return list
    }
}