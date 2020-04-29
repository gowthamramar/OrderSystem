package com.techware.clickkart.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.model.PeopleFavouriteStores.Data;
import com.techware.clickkart.model.PeopleFavouriteStores.PeopleFavouriteBean;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.model.shopbystore.StoreListBean;
import com.techware.clickkart.widgets.CustomTextView;

import java.io.Serializable;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class FavouriteStoresRecyclerViewAdapter extends RecyclerView.Adapter<FavouriteStoresRecyclerViewAdapter.ViewHolder> {
   PeopleFavouriteBean peopleFavouriteBean;
    Context context;
    private boolean isLoadMore;
    private int currentPage;
    private int totalPages;
    FavouriteStoresRecyclerViewAdapter.StoreListRecyclerAdapterListener storeListRecyclerAdapterListener;
    public FavouriteStoresRecyclerViewAdapter(PeopleFavouriteBean peopleFavouriteBean, Context context) {
        this.peopleFavouriteBean=peopleFavouriteBean;
        this.context=context;
    }
    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void setFilterProductsListBean(PeopleFavouriteBean peopleFavouriteBean) {
        this.peopleFavouriteBean = peopleFavouriteBean;
        try {
            currentPage = Integer.parseInt(peopleFavouriteBean.getMeta().getCurrentPage());
            totalPages = Integer.parseInt(String.valueOf(peopleFavouriteBean.getMeta().getTotalPages()));
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_peoples_favourites_stores, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Data data=peopleFavouriteBean.getData().get(i);
            Glide.with(context).load(RetrofitClient.IMAGE_PATH+peopleFavouriteBean.getData().get(i).getStoreImage())
                    .into( holder.storeImageview);
            holder.storeName.setText(peopleFavouriteBean.getData().get(i).getStoreName());
            holder.storeCategory.setText(Html.fromHtml((String) peopleFavouriteBean.getData().get(i).getDescription()));
            holder.ll_peoples_favourite_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, StoreActivity.class)
                            .putExtra("list",data)
                            .putExtra("valueFromAdapter", 3)
                    );
                }
            });


    }

    @Override
    public int getItemCount() {
        return peopleFavouriteBean.getData().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_peoples_favourite_store;
        ImageView storeImageview;
        CustomTextView storeName,storeCategory;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_peoples_favourite_store=itemView.findViewById(R.id.ll_peoples_favourite_store);
            storeImageview=itemView.findViewById(R.id.favourite_store_imgview);
            storeName=itemView.findViewById(R.id.txt_favourite_store_name);
            storeCategory=itemView.findViewById(R.id.txt_favourite_store_category);
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
