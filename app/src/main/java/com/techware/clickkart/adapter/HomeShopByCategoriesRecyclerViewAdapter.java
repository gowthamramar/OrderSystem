package com.techware.clickkart.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.CategoryWiseStoreListActivity;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.widgets.CustomTextView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class HomeShopByCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<HomeShopByCategoriesRecyclerViewAdapter.ViewHolder> {
   CategoryListBean categoryListBean;
    Context context;
    public HomeShopByCategoriesRecyclerViewAdapter(CategoryListBean categoryListBean, Context context) {
        this.categoryListBean=categoryListBean;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_shop_by_categories, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {

            Glide.with(context).load(RetrofitClient.IMAGE_PATH+categoryListBean.getData().get(i).getCategoryImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into( holder.categoryImageview);
            holder.Category.setText(categoryListBean.getData().get(i).getCategoryName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, CategoryWiseStoreListActivity.class)
                            .putExtra("category",categoryListBean.getData().get(i).getCategoryId()));
                }
            });
    }

    @Override
    public int getItemCount() {
        return categoryListBean.getData().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImageview;
        CustomTextView Category;
        LinearLayout llCategory;
        public ViewHolder(View itemView) {
            super(itemView);
            categoryImageview=itemView.findViewById(R.id.item_category_image);
            Category=itemView.findViewById(R.id.txt_item_category_name);
            llCategory=itemView.findViewById(R.id.ll_productCategory);
        }
    }
}
