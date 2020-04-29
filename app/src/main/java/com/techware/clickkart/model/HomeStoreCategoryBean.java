package com.techware.clickkart.model;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
public class HomeStoreCategoryBean extends BaseBean {
    String category;

    public HomeStoreCategoryBean(String category) {
        this.category=category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
