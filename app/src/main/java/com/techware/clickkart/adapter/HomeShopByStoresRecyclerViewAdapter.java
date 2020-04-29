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
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.model.shopbystore.Data;
import com.techware.clickkart.model.shopbystore.StoreListBean;
import com.techware.clickkart.widgets.CustomTextView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class HomeShopByStoresRecyclerViewAdapter extends RecyclerView.Adapter<HomeShopByStoresRecyclerViewAdapter.ViewHolder> {
    StoreListBean storeListBean;
    Context context;
    public HomeShopByStoresRecyclerViewAdapter(StoreListBean storeListBean, Context context) {
        this.storeListBean=storeListBean;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_shop_by_stores_layout, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        final Data bean = storeListBean.getData().get(i);
        Glide.with(context
        ).load(RetrofitClient.IMAGE_PATH +storeListBean.getData().get(i).getStoreImage())

                .apply(new RequestOptions()
                        .centerCrop()


                )
                .into(holder.storeImageview);
        System.out.println("image is"+storeListBean.getData().get(i).getStoreImage());

        /* holder.storeImageview.setImageResource(storeListBean.getData().get(i).getStoreImage());*/
        holder.storeName.setText(storeListBean.getData().get(i).getStoreName());
        holder.storeCategory.setText(Html.fromHtml((String) storeListBean.getData().get(i).getDescription()));
        holder.ll_shopByStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,StoreActivity.class)
                        .putExtra("list",  bean)
                        .putExtra("valueFromAdapter",0)
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeListBean.getData().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_shopByStore;
        ImageView storeImageview;
        CustomTextView storeName,storeCategory;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_shopByStore=itemView.findViewById(R.id.ll_shopByStore);
            storeImageview=itemView.findViewById(R.id.img_shopByStoresImage);
            storeName=itemView.findViewById(R.id.txt_shopByStores);
            storeCategory=itemView.findViewById(R.id.txt_shopBystores_category);
        }
    }
}
