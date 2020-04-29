package com.techware.clickkart.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.OrderDetailsActivity;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.model.GroceryBean;
import com.techware.clickkart.model.VegetablesBean;
import com.techware.clickkart.model.orderdetails.OrderDetailsBean;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class GroceryRecyclerViewAdapter extends RecyclerView.Adapter<GroceryRecyclerViewAdapter.ViewHolder> {
    Dialog dialog;
   OrderDetailsBean orderDetailsBean;
    OrderDetailsActivity orderDetailsActivity;
    CustomTextView productQuantity,productName;
    int quantity;
    public GroceryRecyclerViewAdapter(OrderDetailsBean orderDetailsBean, OrderDetailsActivity orderDetailsActivity) {
        this.orderDetailsBean=orderDetailsBean;
        this.orderDetailsActivity=orderDetailsActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_grocery, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        Glide.with(orderDetailsActivity).load(RetrofitClient.IMAGE_PATH+orderDetailsBean.getGroceries().get(i).getProductImage())
                .apply(RequestOptions.circleCropTransform())
                .into( holder.productImageview);
       /* holder.pastOrderImageview.setImageResource(vegetablesRecyclerItems.get(i).getImage());*/
        holder.quantity.setText("Qua:"+orderDetailsBean.getGroceries().get(i).getQuantity());
        holder.grocery_productName.setText(orderDetailsBean.getGroceries().get(i).getProductName());
        holder.amount.setText("Rs:"+String.valueOf(Integer.valueOf(orderDetailsBean.getGroceries().get(i).getQuantity())*Integer.valueOf(orderDetailsBean.getGroceries().get(i).getProductPrice())));
    }




    @Override
    public int getItemCount() {
        return orderDetailsBean.getGroceries().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageview;
        CustomTextView grocery_productName,quantity,amount;
        public ViewHolder(View itemView) {
            super(itemView);
            productImageview=itemView.findViewById(R.id.img_view_grocery_product);
            grocery_productName=itemView.findViewById(R.id.grocery_productName);
            quantity=itemView.findViewById(R.id.txt_grocery_quantity);
            amount=itemView.findViewById(R.id.txt_grocery_amount);

        }
    }

}
