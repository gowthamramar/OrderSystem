package com.techware.clickkart.adapter;

import android.app.Dialog;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.model.SweetsBean;
import com.techware.clickkart.model.categorywiseproduct.CategoryWiseProductBean;
import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class StoreDetailsCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<StoreDetailsCategoriesRecyclerViewAdapter.ViewHolder> {
    Dialog dialog;
    StoreActivity storeActivity;
    CategoryListBean categoryListBean;
    CustomTextView productQuantity,productName;
    int quantity;
    private StoreActivity.ProductListListener productListListener;
    HomePastOrderAdapterListener homePastOrderAdapterListener;
    //////////////////////////////////////////////////////////////////
    boolean ivArrow = true;
    int angle = 90;
    int antiAngle = 0;
    private RecyclerView productsRecyclerView;
    private  RecyclerView.LayoutManager productsLayoutManager;
    private StoreProductRecyclerViewAdapter searchProductRecyclerViewAdapter;
    /////////////////////////////////////////////////////////////////
    public StoreDetailsCategoriesRecyclerViewAdapter(CategoryListBean categoryListBean, StoreActivity storeActivity) {
        this.categoryListBean=categoryListBean;
        this.storeActivity=storeActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_category_layout, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        productsLayoutManager = new GridLayoutManager(storeActivity, 4);
       holder.productListRecyclerView.setLayoutManager(productsLayoutManager);
        holder.productListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.categoryName.setText(categoryListBean.getData().get(i).getCategoryName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeActivity.setLayoutClicked(categoryListBean.getData().get(i).getCategoryId());
               storeActivity.setProductListListener(new StoreActivity.ProductListListener() {
                   @Override
                   public void getList(CategoryWiseProductBean categoryWiseProductBean) {
                       searchProductRecyclerViewAdapter = new StoreProductRecyclerViewAdapter(storeActivity ,categoryWiseProductBean);
                       holder.productListRecyclerView.setAdapter(searchProductRecyclerViewAdapter);
                       if (ivArrow) {
                           holder.imgArrow.setRotation(angle);
                           ivArrow = false;
                           holder.productListRecyclerView.setVisibility(View.VISIBLE);
                       } else {
                           holder.imgArrow.setRotation(antiAngle);
                           ivArrow = true;
                           holder.productListRecyclerView.setVisibility(View.GONE);
                       }
                   }
               });

            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryListBean.getData().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextView categoryName;
        RecyclerView productListRecyclerView;
        LinearLayout linearLayout;
        ImageView imgArrow;
        public ViewHolder(View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.txt_itemCategoryName);
            linearLayout=itemView.findViewById(R.id.ll_itemCategory);
            imgArrow=itemView.findViewById(R.id.iv_arrow);
            productListRecyclerView=itemView.findViewById(R.id.productListRecyclerView);
        }
    }
    public interface HomePastOrderAdapterListener{

    }

    public HomePastOrderAdapterListener getHomePastOrderAdapterListener() {
        return homePastOrderAdapterListener;
    }

    public void setHomePastOrderAdapterListener(HomePastOrderAdapterListener homePastOrderAdapterListener) {
        this.homePastOrderAdapterListener = homePastOrderAdapterListener;
    }
}
