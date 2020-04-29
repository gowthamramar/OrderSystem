package com.techware.clickkart.model

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
class AllStoreListBean() : BaseBean() {
    var image: Int = 0
    var category: String = ""
    var name: String = ""
    var time: String = ""

    constructor(image: Int, category: String, name: String, time: String) : this() {
        this.image = image
        this.category = category
        this.name = name
        this.time = time
    }
}
