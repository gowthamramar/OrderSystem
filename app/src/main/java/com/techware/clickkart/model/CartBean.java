package com.techware.clickkart.model;

/**
 * Created by Developer on 25 September, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
public class CartBean {
    int image;
    String productName,price,offerPrice;

    public CartBean(int image, String productName, String price, String offerPrice) {
        this.image=image;
        this.productName=productName;
        this.price=price;
        this.offerPrice=offerPrice;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }
}
