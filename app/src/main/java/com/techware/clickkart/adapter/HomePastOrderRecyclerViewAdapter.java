package com.techware.clickkart.adapter;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import com.techware.clickkart.listeners.CartResponseListener;
import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.cart.CartBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.model.pastorders.PastOrberBean;
import com.techware.clickkart.widgets.CustomTextView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class HomePastOrderRecyclerViewAdapter extends RecyclerView.Adapter<HomePastOrderRecyclerViewAdapter.ViewHolder> {
    Dialog dialog;
    PastOrberBean pastOrberBean;
    Context context;
    CustomTextView productQuantity,productName;
    int quantity;
    HomePastOrderAdapterListener homePastOrderAdapterListener;
    public HomePastOrderRecyclerViewAdapter(PastOrberBean pastOrberBean, Context context) {
        this.pastOrberBean=pastOrberBean;
        this.context=context;
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

            Glide.with(context).load(RetrofitClient.IMAGE_PATH+pastOrberBean.getData().get(i).getProductImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into( holder.pastOrderImageview);
            holder.price.setText("Rs:"+pastOrberBean.getData().get(i).getProductPrice()+"/kg");
            holder.productName.setText(pastOrberBean.getData().get(i).getProductName());
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
                    quantity=Integer.valueOf(productQuantity.getText().toString());
                    productName.setText(pastOrberBean.getData().get(i).getProductName());
                    Glide.with(context)
                            .load(RetrofitClient.IMAGE_PATH+pastOrberBean.getData().get(i).getProductImage())
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
                    JsonObject postData = getJsonObject(i);
                        DataManager.addToCart(postData, new CartResponseListener() {
                            @Override
                            public void onLoadCompleted(CartBean cartBean) {
                                Toast.makeText(context, "Product SuccessFully Added to Cart", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onLoadFailed(String error) {
                                Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                            }
                        });}
                    else{
                        Toast.makeText(context, "Please select quantity greater than 0", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }

    private JsonObject getJsonObject(int i) {
        JsonObject postData=new JsonObject();
        postData.addProperty("product_id",pastOrberBean.getData().get(i).getProductId());
        postData.addProperty("quantity",quantity);
        postData.addProperty("store_id",pastOrberBean.getData().get(i).getStore_id());
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
                    Toast.makeText(context,R.string.greater_ten,Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context,R.string.greater_one,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return pastOrberBean.getData().size();
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
