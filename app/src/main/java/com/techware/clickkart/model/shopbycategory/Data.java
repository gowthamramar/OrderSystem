
package com.techware.clickkart.model.shopbycategory;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("category_id")
    private String mCategoryId;
    @SerializedName("category_image")
    private String mCategoryImage;
    @SerializedName("category_name")
    private String mCategoryName;

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getCategoryImage() {
        return mCategoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        mCategoryImage = categoryImage;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

}
