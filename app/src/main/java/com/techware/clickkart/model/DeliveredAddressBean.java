package com.techware.clickkart.model;

/**
 * Created by Developer on 25 September, 2019.
 * Package com.techware.clickkart.model
 * Project ClickKart
 */
public class DeliveredAddressBean {

    String userName, addressType, houseName, address, place, district, pinCode,mobNo;

    public DeliveredAddressBean(String userName, String addressType, String houseName, String address,String mobNo) {
        this.userName = userName;
        this.addressType = addressType;
        this.houseName = houseName;
        this.address = address;
        this.place = place;
        this.district = district;
        this.mobNo = mobNo;

    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
