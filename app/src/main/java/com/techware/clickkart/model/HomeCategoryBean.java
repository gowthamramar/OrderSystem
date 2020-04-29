package com.techware.clickkart.model;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
public class HomeCategoryBean extends BaseBean {
    int image;
    String category;

    public HomeCategoryBean(int image, String category) {
        this.image=image;
        this.category=category;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
