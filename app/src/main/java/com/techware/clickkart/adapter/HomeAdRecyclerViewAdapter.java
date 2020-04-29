package com.techware.clickkart.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.techware.clickkart.R;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.model.shopbyad.AdResponseBean;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class HomeAdRecyclerViewAdapter extends RecyclerView.Adapter<HomeAdRecyclerViewAdapter.ViewHolder> {
   AdResponseBean adResponseBean;
    Context context;
    public HomeAdRecyclerViewAdapter(AdResponseBean adResponseBean, Context context) {
        this.adResponseBean=adResponseBean;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_offer_ads, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Glide.with(context).load(RetrofitClient.IMAGE_PATH+adResponseBean.getData().get(i).getImage())
                .into( holder.adImageview);
     /*   holder.adImageview.setImageResource(adResponseBean.getData().get(i).getImage());*/

    }

    @Override
    public int getItemCount() {
        return adResponseBean.getData().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView adImageview;
        public ViewHolder(View itemView) {
            super(itemView);
            adImageview=itemView.findViewById(R.id.ad_imageView);
        }
    }
}
