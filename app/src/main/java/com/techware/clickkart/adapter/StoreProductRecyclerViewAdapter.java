package com.techware.clickkart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.listeners.CartResponseListener;
import com.techware.clickkart.model.cart.CartBean;
import com.techware.clickkart.model.categorywiseproduct.CategoryWiseProductBean;
import com.techware.clickkart.model.pastorders.PastOrberBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.widgets.CustomTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class StoreProductRecyclerViewAdapter extends RecyclerView.Adapter<StoreProductRecyclerViewAdapter.ViewHolder> {
    Dialog dialog;
    CategoryWiseProductBean categoryWiseProductBean;
    Context context;
    CustomTextView productQuantity,productName,productPrice;
    int quantity;
    HomePastOrderAdapterListener homePastOrderAdapterListener;
    public StoreProductRecyclerViewAdapter(StoreActivity storeActivity, CategoryWiseProductBean categoryWiseProductBean) {
        this.categoryWiseProductBean=categoryWiseProductBean;
        this.context=storeActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_past_orders_layout, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
       /* holder.pastOrderImageview.setImageResource(pastOrdersRecyclerItems.get(i).getImage());*/

            Glide.with(context).load(RetrofitClient.IMAGE_PATH+categoryWiseProductBean.getData().get(i).getProductImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into( holder.pastOrderImageview);
        System.out.println("product price is"+categoryWiseProductBean.getData().get(i).getProductPrice());
            holder.price.setText("Rs:"+categoryWiseProductBean.getData().get(i).getProductPrice()+"/kg");
            /*  holder.offerPrice.setText(pastOrberBean.getData().get(i).getOfferPrice());*/
            holder.productName.setText(categoryWiseProductBean.getData().get(i).getProductName());
            holder.product_plus_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog = new Dialog(context, R.style.ThemeDialogCustom_NoBackground);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.product_dialog);
                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.BOTTOM);
                    productQuantity=dialog.findViewById(R.id.txt_dialog_quantity);
                    productName= dialog.findViewById(R.id.dialog_productName);
                    productPrice=dialog.findViewById(R.id.txt_dialog__price);
                    quantity=Integer.valueOf(productQuantity.getText().toString());
                    productName.setText(categoryWiseProductBean.getData().get(i).getProductName());
                    productPrice.setText("Rs:"+categoryWiseProductBean.getData().get(i).getProductPrice()+"/kg");
                    Glide.with(context)
                            .load(RetrofitClient.IMAGE_PATH+categoryWiseProductBean.getData().get(i).getProductImage())
                            .apply(RequestOptions.circleCropTransform())
                            .into((ImageView) dialog.findViewById(R.id.img_view_dialog_product));
                    dialog.show();
                    setQuantity();
                    closeDialog();
                    addToCart(i);

                }
            });


    }

    private void addToCart(int i) {
        dialog.findViewById(R.id.addToCart_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity>0){
                JsonObject postData= getJsonObject(i);
                DataManager.addToCart(postData, new CartResponseListener() {
                    @Override
                    public void onLoadCompleted(CartBean cartBean) {
                        Toast.makeText(context, "Product SuccessFully Added to Cart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoadFailed(String error) {
                        Toast.makeText(context,error, Toast.LENGTH_SHORT).show();
                    }
                });} else{
                    Toast.makeText(context, "Please select quantity greater than 0", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private JsonObject getJsonObject(int i) {
        JsonObject postData=new JsonObject();
        postData.addProperty("product_id",categoryWiseProductBean.getData().get(i).getProductId());
        postData.addProperty("quantity",quantity);
        postData.addProperty("store_id",categoryWiseProductBean.getData().get(i).getStoreId());
        return  postData;
    }
    private void closeDialog() {
        dialog.findViewById(R.id.dialog_product_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setQuantity() {
        dialog.findViewById(R.id.dialog_quantitiy_increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity<10){
                    quantity++;
                    productQuantity.setText(String.valueOf(quantity));
                }
                else {
                    Toast.makeText(context,"You can select maximum quantity 10",Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.findViewById(R.id.dialog_quantity_decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>1){
                    quantity--;
                    productQuantity.setText(String.valueOf(quantity));
                }
                else {
                    Toast.makeText(context,"Please select quantity greater than 1",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryWiseProductBean.getData().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pastOrderImageview,product_plus_icon;
        CustomTextView price,offerPrice,productName;
        public ViewHolder(View itemView) {
            super(itemView);
            pastOrderImageview=itemView.findViewById(R.id.past_order_image);
            price=itemView.findViewById(R.id.txt_past_order_price);
          /*  offerPrice=itemView.findViewById(R.id.txt_past_order_offer_price);*/
            productName=itemView.findViewById(R.id.txt_past_orderName);
            product_plus_icon=itemView.findViewById(R.id.product_plus_icon);
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
