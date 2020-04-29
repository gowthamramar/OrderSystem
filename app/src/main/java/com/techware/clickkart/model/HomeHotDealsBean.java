package com.techware.clickkart.model;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
public class HomeHotDealsBean extends BaseBean {
    int image;

    public HomeHotDealsBean(int image) {
        this.image=image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
