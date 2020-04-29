package com.techware.clickkart.model

/**
 * Created by Jemsheer K D on 24 April, 2017.
 * Package in.techware.dearest.model
 * Project Dearest
 */

class StateBean : BaseBean(), Comparable<StateBean> {

    var id: String = ""
    var countryID: String = ""
    var name: String = ""
    var countryCode: String = ""
    var countryName: String = ""
    var stateStatus: Int = 0
    override fun compareTo(other: StateBean): Int {
        var bean = other;
        return id.compareTo(bean.id)
    }
}
