package com.techware.clickkart.adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.HomePageActivity;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.model.searchcategory.Data;
import com.techware.clickkart.model.searchcategory.SearchedCategoryList;
import com.techware.clickkart.widgets.CustomTextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class SearchCategoryListRecyclerViewAdapter extends RecyclerView.Adapter<SearchCategoryListRecyclerViewAdapter.ViewHolder> {
    private SearchedCategoryList searchedCategoryList;
    HomePageActivity homePageActivity;
    private boolean isLoadMore;
    private int currentPage;
    private int totalPages;
    private SearchListRecyclerAdapterListener searchListRecyclerAdapterListener;

    public SearchCategoryListRecyclerViewAdapter(HomePageActivity homePageActivity, SearchedCategoryList searchedCategoryList){
        this.searchedCategoryList = searchedCategoryList;
        this.homePageActivity = homePageActivity;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void setFilterProductsListBean(SearchedCategoryList searchedCategoryList) {
        this.searchedCategoryList = searchedCategoryList;
        try {
            currentPage = Integer.parseInt(searchedCategoryList.getMeta().getCurrentPage());
            totalPages = Integer.parseInt(String.valueOf(searchedCategoryList.getMeta().getTotalPages()));
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
        Data data=searchedCategoryList.getData().get(i);
        System.out.println("store bean"+searchedCategoryList.getData());
        holder.txtStore.setText(searchedCategoryList.getData().get(i).getCategoryName());
        holder.txtDescription.setText("Shop :"+searchedCategoryList.getData().get(i).getStoreName());
        holder.llStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             homePageActivity.startActivity(new Intent(homePageActivity, StoreActivity.class)
                     .putExtra("list",data)
                     .putExtra("valueFromAdapter", 6));
            }
        });
        if (i == searchedCategoryList.getData().size() - 1
                && currentPage < totalPages) {
            searchListRecyclerAdapterListener.onRequestNextPage(true, currentPage);
        }

    }

    @Override
    public int getItemCount() {
        return searchedCategoryList.getData().size();
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
