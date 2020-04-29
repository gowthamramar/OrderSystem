package com.techware.clickkart.model;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
public class HomeAdBean extends BaseBean {
    int image;

    public HomeAdBean(int first_ad) {
        this.image=first_ad;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
