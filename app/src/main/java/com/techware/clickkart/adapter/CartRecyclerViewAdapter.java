package com.techware.clickkart.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.CartActivity;
import com.techware.clickkart.model.cart.CartAmountBean;
import com.techware.clickkart.model.getcart.GetCartBean;
import com.techware.clickkart.model.orderplaced.Data;
import com.techware.clickkart.model.orderplaced.OrderPlacedBean;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.widgets.CustomTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {
    Dialog dialog;
    GetCartBean getCartBean;
    CartActivity cartActivity;
    CustomTextView productQuantity, productName;
    int quantity;
    OrderPlacedBean orderPlacedBean;
    CartAmountBean cartAmountBean;
    CartAdapterListener cartAdapterListener;

    public CartRecyclerViewAdapter(GetCartBean getCartBean, CartActivity cartActivity) {
        this.getCartBean = getCartBean;
        this.cartActivity = cartActivity;
        cartAmountBean = new CartAmountBean();
        int totalAmount = 0;
        orderPlacedBean = new OrderPlacedBean();

        for (int i = 0; i < getCartBean.getData().size(); i++) {
            totalAmount += Integer.valueOf(Integer.valueOf(getCartBean.getData().get(i).getProductPrice().toString()) * Integer.valueOf(getCartBean.getData().get(i).getQuantity().toString()));
            Data data = new Data();
            data.setStoreId(getCartBean.getData().get(i).getStoreId());
            data.setProductId(getCartBean.getData().get(i).getProductId());
            data.setProductPrice(getCartBean.getData().get(i).getProductPrice());
            data.setQuantity(getCartBean.getData().get(i).getQuantity());
            data.setAmount(String.valueOf(Integer.valueOf(getCartBean.getData().get(i).getProductPrice()) * Integer.valueOf(getCartBean.getData().get(i).getQuantity())));
            orderPlacedBean.getData().add(data);
        }
        cartAmountBean.setTotalAmount(totalAmount);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cart, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        Glide.with(cartActivity).load(RetrofitClient.IMAGE_PATH + getCartBean.getData().get(i).getProductImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.cartImageview);
        cartAdapterListener.setBulkAmount(cartAmountBean);
        /* holder.pastOrderImageview.setImageResource(fruitsRecyclerItems.get(i).getImage());*/
        /* holder.offerPrice.setText(getCartBean.getData().get(i).getProductPrice());*/
        holder.productName.setText(getCartBean.getData().get(i).getProductName());
        quantity = Integer.parseInt(getCartBean.getData().get(i).getQuantity());
        holder.txt_cart_quantity.setText(getCartBean.getData().get(i).getQuantity());
        int totalAmount = Integer.valueOf(getCartBean.getData().get(i).getProductPrice()) * quantity;
        holder.price.setText(String.valueOf(totalAmount));
        cartAdapterListener.itemsCount(getCartBean.getData().size());
        holder.txt_cartRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = holder.price.getText().toString();
                int amt = Integer.valueOf(amount);
                cartAmountBean.setTotalAmount(cartAmountBean.getTotalAmount() - amt);
                cartAdapterListener.calculateBulkAmount(cartAmountBean);
                cartActivity.removeFromCart(getCartBean.getData().get(i).getProductId(), getCartBean.getData().get(i).getStoreId(), i, getCartBean.getData().size(), getCartBean);
                orderPlacedBean.getData().remove(i);
            }
        });
        holder.cart_quantitiy_increment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int cartQuantity = Integer.parseInt(holder.txt_cart_quantity.getText().toString());
                if (cartQuantity < 10) {
                    cartQuantity++;
                    holder.txt_cart_quantity.setText(String.valueOf(cartQuantity));

                    int totalAmount = Integer.valueOf(getCartBean.getData().get(i).getProductPrice()) * cartQuantity;
                    holder.price.setText(String.valueOf(totalAmount));
                    cartAmountBean.setTotalAmount(cartAmountBean.getTotalAmount() + Integer.valueOf(getCartBean.getData().get(i).getProductPrice()));
                    cartAdapterListener.calculateBulkAmount(cartAmountBean);
                    cartAdapterListener.updateCart(getCartBean.getData().get(i).getProductId(), cartQuantity, getCartBean.getData().get(i).getStoreId());
                    Data data = new Data();
                    data.setStoreId(getCartBean.getData().get(i).getStoreId());
                    data.setProductId(getCartBean.getData().get(i).getProductId());
                    data.setProductPrice(getCartBean.getData().get(i).getProductPrice());
                    data.setQuantity(String.valueOf(cartQuantity));
                    data.setAmount(String.valueOf(totalAmount));

                    orderPlacedBean.getData().set(i, data);
                } else {
                    Toast.makeText(cartActivity,R.string.greater_ten, Toast.LENGTH_SHORT).show();
                }


            }
        });
        holder.cart_quantity_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartQuantity = Integer.parseInt(holder.txt_cart_quantity.getText().toString());
                int amount = Integer.valueOf(holder.price.getText().toString());
                if (cartQuantity > 0) {
                    if (cartQuantity != 1) {
                        cartQuantity--;
                        holder.txt_cart_quantity.setText(String.valueOf(cartQuantity));
                        int totalAmount = Integer.valueOf(amount - Integer.valueOf(getCartBean.getData().get(i).getProductPrice().toString()));
                        holder.price.setText(String.valueOf(totalAmount));
                        cartAmountBean.setTotalAmount(cartAmountBean.getTotalAmount() - Integer.valueOf(getCartBean.getData().get(i).getProductPrice()));
                        cartAdapterListener.updateCart(getCartBean.getData().get(i).getProductId(), cartQuantity, getCartBean.getData().get(i).getStoreId());
                        cartAdapterListener.calculateBulkAmount(cartAmountBean);
                        Data data = new Data();
                        data.setStoreId(getCartBean.getData().get(i).getStoreId());
                        data.setProductId(getCartBean.getData().get(i).getProductId());
                        data.setProductPrice(getCartBean.getData().get(i).getProductPrice());
                        data.setQuantity(String.valueOf(cartQuantity));
                        data.setAmount(String.valueOf(totalAmount));

                        orderPlacedBean.getData().set(i, data);
                    }

                } else {
                    Toast.makeText(cartActivity,R.string.greater_one, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return getCartBean.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImageview;
        CustomTextView price, offerPrice, productName, cart_quantity_decrement, txt_cart_quantity, cart_quantitiy_increment, txt_cartRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_cartRemove = itemView.findViewById(R.id.txt_cartRemove);
            cartImageview = itemView.findViewById(R.id.img_view_cart_product);
            price = itemView.findViewById(R.id.txt_cart_price);
            offerPrice = itemView.findViewById(R.id.txt_cart_offer_price);
            productName = itemView.findViewById(R.id.cart_productName);
            cart_quantity_decrement = itemView.findViewById(R.id.cart_quantity_decrement);
            txt_cart_quantity = itemView.findViewById(R.id.txt_cart_quantity);
            cart_quantitiy_increment = itemView.findViewById(R.id.cart_quantitiy_increment);
        }
    }

    public OrderPlacedBean getOrderPlacedBean() {
        return orderPlacedBean;
    }

    public interface CartAdapterListener {
        void setBulkAmount(CartAmountBean cartAmountBean);

        void calculateBulkAmount(CartAmountBean cartAmountBean);

        void itemsCount(int size);

        void updateCart(String productId, int cartQuantity, String storeId);


    }

    public CartAdapterListener getCartAdapterListener() {
        return cartAdapterListener;
    }

    public void setCartAdapterListener(CartAdapterListener cartAdapterListener) {
        this.cartAdapterListener = cartAdapterListener;
    }
}
