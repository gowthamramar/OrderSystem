package com.techware.clickkart.adapter;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.techware.clickkart.R;
import com.techware.clickkart.activity.HomePageActivity;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.model.searchproduct.SearchProductBean;
import com.techware.clickkart.model.searchstore.SearchStoreBean;
import com.techware.clickkart.widgets.CustomTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class SearchStoreListRecyclerViewAdapter extends RecyclerView.Adapter<SearchStoreListRecyclerViewAdapter.ViewHolder> {
    private SearchStoreBean searchStoreBean;
    HomePageActivity homePageActivity;
    private boolean isLoadMore;
    private int currentPage;
    private int totalPages;
    private SearchListRecyclerAdapterListener searchListRecyclerAdapterListener;

    public SearchStoreListRecyclerViewAdapter(HomePageActivity homePageActivity, SearchStoreBean searchStoreBean) {
        this.searchStoreBean = searchStoreBean;
        this.homePageActivity = homePageActivity;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void setFilterProductsListBean(SearchStoreBean searchStoreBean) {
        this.searchStoreBean = searchStoreBean;
        try {
            currentPage = Integer.parseInt(searchStoreBean.getMeta().getCurrentPage());
            totalPages = Integer.parseInt(String.valueOf(searchStoreBean.getMeta().getTotalPages()));
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_store_search, viewGroup, false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        System.out.println("store bean"+searchStoreBean.getData());
       holder.txtDescription.setText(Html.fromHtml((String)searchStoreBean.getData().get(i).getDescription()));
        holder.txtStore.setText(searchStoreBean.getData().get(i).getStoreName());
        holder.llStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePageActivity.startActivity(new Intent(homePageActivity,StoreActivity.class)
                        .putExtra("list",searchStoreBean.getData().get(i))
                        .putExtra("valueFromAdapter", 5));
            }
        });
        if (i == searchStoreBean.getData().size() - 1
                && currentPage < totalPages) {
            searchListRecyclerAdapterListener.onRequestNextPage(true, currentPage);
        }

    }

    @Override
    public int getItemCount() {
        return searchStoreBean.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txtStore,txtDescription;
        ImageView storeImage;
        LinearLayout llStore;

        public ViewHolder(View itemView) {
            super(itemView);
            txtStore = itemView.findViewById(R.id.txt_itemStore);
            txtDescription=itemView.findViewById(R.id.txt_store_item_decription);
            llStore=itemView.findViewById(R.id.ll_item_store_search);

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
