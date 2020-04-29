package com.techware.clickkart.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.model.categoryliststore.StoreListWiseCategory;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.widgets.CustomTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class ShopByCategoryListRecyclerViewAdapter extends RecyclerView.Adapter<ShopByCategoryListRecyclerViewAdapter.ViewHolder> {
    StoreListWiseCategory wiseStoreResponseBean;
    Context context;
    private boolean isLoadMore;
    private int currentPage;
    private int totalPages;
    StoreListRecyclerAdapterListener storeListRecyclerAdapterListener;

    public ShopByCategoryListRecyclerViewAdapter(Context context, StoreListWiseCategory wiseStoreResponseBean) {
        this.wiseStoreResponseBean = wiseStoreResponseBean;
        this.context = context;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void setFilterProductsListBean(StoreListWiseCategory wiseStoreResponseBean) {
        this.wiseStoreResponseBean = wiseStoreResponseBean;
        try {
            currentPage = Integer.parseInt(wiseStoreResponseBean.getMeta().getCurrentPage());
            totalPages = Integer.parseInt(String.valueOf(wiseStoreResponseBean.getMeta().getTotalPages()));
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_stores_list, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
       StoreListWiseCategory bean=wiseStoreResponseBean;
        Glide.with(context).load(RetrofitClient.IMAGE_PATH +wiseStoreResponseBean.getCategoryStores().get(i).getStoreImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.storeImage);
        holder.txt_store_name.setText(wiseStoreResponseBean.getCategoryStores().get(i).getStoreName());
        holder.txt_store_category.setText(Html.fromHtml((String)wiseStoreResponseBean.getCategoryStores().get(i).getDescription()));
        holder.txt_store_timing.setText(wiseStoreResponseBean.getCategoryStores().get(i).getStartTime()+"AM - "+wiseStoreResponseBean.getCategoryStores().get(i).getEndTime()+" PM");
        /* holder.storeImage.setImageResource(allStoresListRecyclerItems.get(i).getImage());*/
        holder.ll_storeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StoreActivity.class)
                .putExtra("list",wiseStoreResponseBean.getCategoryStores().get(i))
                .putExtra("valueFromAdapter",1));
            }
        });

        if (i == wiseStoreResponseBean.getCategoryStores().size() - 1
                && currentPage < totalPages) {
            storeListRecyclerAdapterListener.onRequestNextPage(true, currentPage);
        }
    }

    @Override
    public int getItemCount() {
        return wiseStoreResponseBean.getCategoryStores().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txt_store_name, txt_store_category, txt_store_timing;
        ImageView storeImage;
        LinearLayout ll_storeList;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_storeList = itemView.findViewById(R.id.ll_storeList);
            txt_store_name = itemView.findViewById(R.id.txt_all_store_name);
            txt_store_category = itemView.findViewById(R.id.txt_all_store_list_category);
            txt_store_timing = itemView.findViewById(R.id.txt_all_store_list_timing);
            storeImage = itemView.findViewById(R.id.all_store_imgview);
        }
    }

    public StoreListRecyclerAdapterListener getStoreListRecyclerAdapterListener() {
        return storeListRecyclerAdapterListener;
    }

    public void setStoreListRecyclerAdapterListener(StoreListRecyclerAdapterListener storeListRecyclerAdapterListener) {
        this.storeListRecyclerAdapterListener = storeListRecyclerAdapterListener;
    }

    public static interface StoreListRecyclerAdapterListener {
        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeRefreshing);

        void onSnackBarShow(String message);
    }
}
