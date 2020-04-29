package com.techware.clickkart.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techware.clickkart.R;
import com.techware.clickkart.fragments.StoresFragment;
import com.techware.clickkart.model.HomeStoreCategoryBean;
import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class HomeStoreCategoryRecyclerViewAdapter extends RecyclerView.Adapter<HomeStoreCategoryRecyclerViewAdapter.ViewHolder> {
   CategoryListBean categoryListBean;
    Context context;
    StoresFragment storesFragment;
    StoreCategoryAdapterListener storeCategoryAdapterListener;
    int index;
    public HomeStoreCategoryRecyclerViewAdapter(CategoryListBean categoryListBean, Context context ,StoresFragment storesFragment) {
        this.categoryListBean=categoryListBean;
        this.context=context;
        this.storesFragment=storesFragment;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_select, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.txt_item_select_name.setText(categoryListBean.getData().get(i).getCategoryName());
        if (i==0){
           holder.txt_item_select_name.setTextColor(Color.WHITE);
        }
        holder.txt_item_select_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=i;
                notifyDataSetChanged();
             storeCategoryAdapterListener.itemClicked(i,categoryListBean.getData().get(i).getCategoryId());

            }
        });
        if (index==i){
            holder.txt_item_select_name.setTextColor(Color.WHITE);

        }
        else {
            holder.txt_item_select_name.setTextColor(Color.parseColor("#82ffffff"));
        }

    }

    @Override
    public int getItemCount() {
        return categoryListBean.getData().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       CustomTextView txt_item_select_name;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_item_select_name=itemView.findViewById(R.id.txt_item_select_name);
        }
    }
    public interface StoreCategoryAdapterListener{
        void itemClicked(int index, String categoryId);
    }

    public StoreCategoryAdapterListener getStoreCategoryAdapterListener() {
        return storeCategoryAdapterListener;
    }

    public void setStoreCategoryAdapterListener(StoreCategoryAdapterListener storeCategoryAdapterListener) {
        this.storeCategoryAdapterListener = storeCategoryAdapterListener;
    }
}
