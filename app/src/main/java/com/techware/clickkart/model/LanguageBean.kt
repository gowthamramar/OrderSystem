package com.techware.clickkart.model

/**
 * Created by Jemsheer K D on 07 August, 2017.
 * Package `in`.techware.dearest.model
 * Project Dearest
 */
class LanguageBean : BaseBean(), Comparable<LanguageBean> {
    var id: String = ""
    var shortCode: String = ""
    var name: String = ""
    var languageStatus: Int = 0

    override fun compareTo(other: LanguageBean): Int {
        val bean = other
        return id.compareTo(bean.id)
    }

}
