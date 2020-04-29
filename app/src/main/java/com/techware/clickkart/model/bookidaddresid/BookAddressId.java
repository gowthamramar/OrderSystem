package com.techware.clickkart.model.bookidaddresid;

import java.io.Serializable;

/**
 * Created by Developer on 07 February, 2020.
 * Package com.techware.clickkart.model.bookidaddresid
 * Project ClickKart
 */
public class BookAddressId implements Serializable {
    String bookId;
    String adsId;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getAdsId() {
        return adsId;
    }

    public void setAdsId(String adsId) {
        this.adsId = adsId;
    }
}
