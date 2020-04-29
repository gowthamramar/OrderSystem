package com.techware.clickkart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.techware.clickkart.R;
import com.techware.clickkart.activity.PaymentMethodActivity;
import com.techware.clickkart.model.CardModel;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Developer on 03 October, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */

public class CardLIstAdapter extends RecyclerView.Adapter<CardLIstAdapter.ViewHolder> {
    List<CardModel> cardRecyclerItems = new ArrayList<>();
    PaymentMethodActivity paymentMethodActivity;
    Dialog dialog;
    CustomTextView txtConfirmRemove;
    ImageView cancel;

    public CardLIstAdapter(ArrayList<CardModel> cardRecyclerItems,PaymentMethodActivity paymentMethodActivity) {
        this.cardRecyclerItems = cardRecyclerItems;
        this.paymentMethodActivity = paymentMethodActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_addcard, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        holder.textView.setText(cardRecyclerItems.get(i).getAccountNo());
        dialog = new Dialog(paymentMethodActivity, R.style.ThemeDialogCustom_NoBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_remove_card);
        txtConfirmRemove=dialog.findViewById(R.id.txt_confirm_remove);
        cancel=dialog.findViewById(R.id.remove_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        txtConfirmRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemRemoved(i);
                cardRecyclerItems.remove(i);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
            holder.imgRemoveCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (i>0){
                        dialog.show();

                    }
                    else{
                        paymentMethodActivity.hideRecyclerView();
                    }

                }
            });
    }

    @Override
    public int getItemCount() {
        return cardRecyclerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView textView;
        ImageView imgRemoveCard;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_account_no);
            imgRemoveCard=itemView.findViewById(R.id.iv_card_cancel_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int fromPos = getAdapterPosition();
                    int toPos = getItemCount();
                    notifyItemMoved(fromPos, toPos);
                }
            });
        }
    }
}