
package com.techware.clickkart.model.YourFavouriteBean;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Meta {

    @SerializedName("current_page")
    private String mCurrentPage;
    @SerializedName("per_page")
    private Long mPerPage;
    @SerializedName("total")
    private Long mTotal;
    @SerializedName("total_pages")
    private Long mTotalPages;

    public String getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(String currentPage) {
        mCurrentPage = currentPage;
    }

    public Long getPerPage() {
        return mPerPage;
    }

    public void setPerPage(Long perPage) {
        mPerPage = perPage;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

    public Long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Long totalPages) {
        mTotalPages = totalPages;
    }

}
