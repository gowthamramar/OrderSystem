package com.techware.clickkart.model;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
public class HomeStoreBean extends BaseBean {
    int image;
    String storeName,storeCategory;

    public HomeStoreBean(int image, String storeName, String storeCategory) {
        this.image=image;
        this.storeCategory=storeCategory;
        this.storeName=storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
