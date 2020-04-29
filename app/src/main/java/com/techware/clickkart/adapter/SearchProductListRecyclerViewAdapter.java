package com.techware.clickkart.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.techware.clickkart.R;
import com.techware.clickkart.activity.HomePageActivity;
import com.techware.clickkart.activity.LocationActivity;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.model.locationbean.LocationListItemData;
import com.techware.clickkart.model.locationbean.LocationResponseBean;
import com.techware.clickkart.model.searchproduct.Data;
import com.techware.clickkart.model.searchproduct.SearchProductBean;
import com.techware.clickkart.widgets.CustomTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class SearchProductListRecyclerViewAdapter extends RecyclerView.Adapter<SearchProductListRecyclerViewAdapter.ViewHolder> {
    private SearchProductBean searchProductBean;
    private HomePageActivity homePageActivity;
    private StoreActivity storeActivity;
    private boolean isLoadMore;
    private int currentPage;
    private int totalPages;
    SearchListRecyclerAdapterListener searchListRecyclerAdapterListener;

    public SearchProductListRecyclerViewAdapter(HomePageActivity homePageActivity, SearchProductBean searchProductBean) {
        this.searchProductBean = searchProductBean;
        this.homePageActivity = homePageActivity;
    }

    public SearchProductListRecyclerViewAdapter(StoreActivity storeActivity, SearchProductBean searchProductBean) {
        this.searchProductBean = searchProductBean;
        this.storeActivity = storeActivity;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void setFilterProductsListBean(SearchProductBean searchProductBean) {
        this.searchProductBean = searchProductBean;
        try {
            currentPage = Integer.parseInt(searchProductBean.getMeta().getCurrentPage());
            totalPages = Integer.parseInt(String.valueOf(searchProductBean.getMeta().getTotalPages()));
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_grocery_search, viewGroup, false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
       holder.txtGrocery.setText(searchProductBean.getData().get(i).getProductName());
        holder.txtStore.setText(searchProductBean.getData().get(i).getStoreName());
        holder.txtPrice.setText(searchProductBean.getData().get(i).getProductPrice()+"/kg");
        if (i == searchProductBean.getData().size() - 1
                && currentPage < totalPages) {
            searchListRecyclerAdapterListener.onRequestNextPage(true, currentPage);
        }
        holder.llGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               homePageActivity.startActivity(new Intent(homePageActivity, StoreActivity.class)
                       .putExtra("list",searchProductBean.getData().get(i))
                       .putExtra("valueFromAdapter", 4));
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchProductBean.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txtGrocery,txtStore,txtPrice;
        ImageView storeImage;
        LinearLayout llGrocery;

        public ViewHolder(View itemView) {
            super(itemView);
            txtGrocery = itemView.findViewById(R.id.txt_itemGrocery);
            txtStore = itemView.findViewById(R.id.txt_itemGroceryStore);
            txtPrice = itemView.findViewById(R.id.item_grocery_price);
            llGrocery=itemView.findViewById(R.id.ll_item_grocery_search);

        }
    }

    public SearchListRecyclerAdapterListener getLocationListRecyclerAdapterListener() {
        return searchListRecyclerAdapterListener;
    }

    public void setSearchListRecyclerAdapterListener(SearchListRecyclerAdapterListener searchListRecyclerAdapterListener) {
        this.searchListRecyclerAdapterListener = searchListRecyclerAdapterListener;
    }

    public static interface SearchListRecyclerAdapterListener {
        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeRefreshing);

        void onSnackBarShow(String message);


    }
}
