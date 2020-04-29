package com.techware.clickkart.model;

/**
 * Created by Developer on 03 October, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
public class CardModel {
    String accountNo;

    public CardModel(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
