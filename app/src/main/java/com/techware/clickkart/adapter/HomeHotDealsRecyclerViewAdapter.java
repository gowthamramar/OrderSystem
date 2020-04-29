package com.techware.clickkart.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.techware.clickkart.R;
import com.techware.clickkart.model.HomeHotDealsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class HomeHotDealsRecyclerViewAdapter extends RecyclerView.Adapter<HomeHotDealsRecyclerViewAdapter.ViewHolder> {
    List<HomeHotDealsBean> hotDealsRecyclerItems=new ArrayList<>();
    Context context;
    public HomeHotDealsRecyclerViewAdapter(ArrayList<HomeHotDealsBean> hotDealsRecyclerItems, Context context) {
        this.hotDealsRecyclerItems=hotDealsRecyclerItems;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_hot_deals, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.dealsImageview.setImageResource(hotDealsRecyclerItems.get(i).getImage());

    }

    @Override
    public int getItemCount() {
        return hotDealsRecyclerItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dealsImageview;
        public ViewHolder(View itemView) {
            super(itemView);
            dealsImageview=itemView.findViewById(R.id.ad_imageView);
        }
    }
}
