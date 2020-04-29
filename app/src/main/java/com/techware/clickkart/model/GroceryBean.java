package com.techware.clickkart.model;

/**
 * Created by Developer on 25 September, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
public class GroceryBean {
    int image;
    String productName,price,quantity;

    public GroceryBean(int image, String productName, String price, String quantity) {
        this.image=image;
        this.productName=productName;
        this.price=price;
        this.quantity=quantity;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
