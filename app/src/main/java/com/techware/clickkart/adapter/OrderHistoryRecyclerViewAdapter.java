package com.techware.clickkart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techware.clickkart.R;
import com.techware.clickkart.model.orderhistory.OrderHistoryDataBean;
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
public class OrderHistoryRecyclerViewAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerViewAdapter.ViewHolder> {
    Dialog dialog;
    List<OrderHistoryDataBean> historyRecyclerItems;
    Context context;
    int quantity;

    public boolean showLoader;

    public OrderHistoryRecyclerViewAdapter(ArrayList<OrderHistoryDataBean> historyRecyclerItems, Context context) {
        this.historyRecyclerItems = historyRecyclerItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        if (viewType == 0)
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_history, viewGroup, false);
        else
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_loader, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public int getItemViewType(int position) {
        if (showLoader && position == historyRecyclerItems.size())
            return 1;
        else
            return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        if (i < historyRecyclerItems.size()) {
            // holder.bookDate.setText(historyRecyclerItems.get(i).getBookedDate());
            holder.bookingId.setText(historyRecyclerItems.get(i).getBookingId());
            holder.amount.setText(historyRecyclerItems.get(i).getTotalAmount());
            holder.scheduledDate.setText(historyRecyclerItems.get(i).getScheduledDate());
            //status-1-completed
            if (historyRecyclerItems.get(i).getStatus().equalsIgnoreCase("1")) {
                holder.status.setText("Completed");
            } else {
                holder.status.setText("pending");
            }
        }
    }


    @Override
    public int getItemCount() {
        if (historyRecyclerItems.isEmpty()) {
            if (showLoader)
                return 1;
            else
                return 0;
        } else if (showLoader)
            return historyRecyclerItems.size() + 1;
        else
            return historyRecyclerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView bookingId, bookDate, amount, scheduledDate, status, scheduledTime;

        public ViewHolder(View itemView) {
            super(itemView);
            bookDate = itemView.findViewById(R.id.txt_bookingDate);
            bookingId = itemView.findViewById(R.id.txt_bookingIdHistory);
            amount = itemView.findViewById(R.id.txt_amount_history);
            scheduledTime = itemView.findViewById(R.id.txt_sheduled_time);
            scheduledDate = itemView.findViewById(R.id.txt_sheduled_date);
            status = itemView.findViewById(R.id.txt_status_history);
        }
    }

}
