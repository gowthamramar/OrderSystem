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
import com.google.gson.Gson;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.model.YourFavouriteBean.Data;
import com.techware.clickkart.model.YourFavouriteBean.YourFavouriteBean;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.widgets.CustomTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class YourFavouriteStoresRecyclerViewAdapter extends RecyclerView.Adapter<YourFavouriteStoresRecyclerViewAdapter.ViewHolder> {
    YourFavouriteBean yourFavouriteBean;
    Context context;

    public YourFavouriteStoresRecyclerViewAdapter(YourFavouriteBean yourFavouriteBean, Context context) {
        this.yourFavouriteBean = yourFavouriteBean;
        this.context = context;
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
        final Data data = yourFavouriteBean.getData().get(i);
        System.out.println("bean is" + new Gson().toJson(data));

        Glide.with(context).load(RetrofitClient.IMAGE_PATH + yourFavouriteBean.getData().get(i).getStoreImage())
                .into(holder.storeImageview);
        holder.storeName.setText(yourFavouriteBean.getData().get(i).getStoreName());
        holder.storeCategory.setText(Html.fromHtml((String) yourFavouriteBean.getData().get(i).getDescription()));
        holder.ll_peoples_favourite_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StoreActivity.class)
                        .putExtra("list", data)
                        .putExtra("valueFromAdapter", 2));
            }
        });
    }


    @Override
    public int getItemCount() {
        return yourFavouriteBean.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_peoples_favourite_store;
        ImageView storeImageview;
        CustomTextView storeName, storeCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_peoples_favourite_store = itemView.findViewById(R.id.ll_peoples_favourite_store);
            storeImageview = itemView.findViewById(R.id.favourite_store_imgview);
            storeName = itemView.findViewById(R.id.txt_favourite_store_name);
            storeCategory = itemView.findViewById(R.id.txt_favourite_store_category);
        }
    }
}
