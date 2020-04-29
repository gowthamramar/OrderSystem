package com.techware.clickkart.adapter;

import android.app.Dialog;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.model.FruitsBean;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class StoreFruitsRecyclerViewAdapter extends RecyclerView.Adapter<StoreFruitsRecyclerViewAdapter.ViewHolder> {
    Dialog dialog;
    List<FruitsBean> fruitsRecyclerItems=new ArrayList<>();
    StoreActivity storeActivity;
    CustomTextView productQuantity,productName;
    int quantity;
    HomePastOrderAdapterListener homePastOrderAdapterListener;
    public StoreFruitsRecyclerViewAdapter(ArrayList<FruitsBean> fruitsRecyclerItems, StoreActivity storeActivity) {
        this.fruitsRecyclerItems=fruitsRecyclerItems;
        this.storeActivity=storeActivity;
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
        Glide.with(storeActivity).load(fruitsRecyclerItems.get(i).getImage())
                .apply(RequestOptions.circleCropTransform())
                .into( holder.pastOrderImageview);
       /* holder.pastOrderImageview.setImageResource(fruitsRecyclerItems.get(i).getImage());*/
        holder.price.setText(fruitsRecyclerItems.get(i).getPrice());
        holder.offerPrice.setText(fruitsRecyclerItems.get(i).getOfferPrice());
        holder.productName.setText(fruitsRecyclerItems.get(i).getProductName());
        holder.product_plus_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(storeActivity, R.style.ThemeDialogCustom_NoBackground);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.product_dialog);
                productQuantity=dialog.findViewById(R.id.txt_dialog_quantity);
                productName= dialog.findViewById(R.id.dialog_productName);
                quantity=Integer.valueOf(productQuantity.getText().toString());
              productName.setText(fruitsRecyclerItems.get(i).getProductName());
                Glide.with(storeActivity)
                        .load(fruitsRecyclerItems.get(i).getImage())
                        .apply(RequestOptions.circleCropTransform())
                        .into((ImageView) dialog.findViewById(R.id.img_view_dialog_product));
                dialog.show();
                setQuantity();
                closeDialog();

            }
        });

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
                    Toast.makeText(storeActivity,"You can select maximum quantity 10",Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.findViewById(R.id.dialog_quantity_decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>0){
                    quantity--;
                    productQuantity.setText(String.valueOf(quantity));
                }
                else {
                    Toast.makeText(storeActivity,"Please select quantity greater than 0",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return fruitsRecyclerItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pastOrderImageview,product_plus_icon;
        CustomTextView price,offerPrice,productName;
        public ViewHolder(View itemView) {
            super(itemView);
            pastOrderImageview=itemView.findViewById(R.id.past_order_image);
            price=itemView.findViewById(R.id.txt_past_order_price);
            offerPrice=itemView.findViewById(R.id.txt_past_order_offer_price);
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
