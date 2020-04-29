package com.techware.clickkart.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techware.clickkart.R;
import com.techware.clickkart.model.SearchGroceryCategoryBean;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class SearchGroceryCategoryRecyclerViewAdapter extends RecyclerView.Adapter<SearchGroceryCategoryRecyclerViewAdapter.ViewHolder> {
    List<SearchGroceryCategoryBean> searchGroceryCategoryRecyclerItems=new ArrayList<>();
    Context context;
    public SearchGroceryCategoryRecyclerViewAdapter(ArrayList<SearchGroceryCategoryBean> searchGroceryCategoryRecyclerItems, Context context) {
        this.searchGroceryCategoryRecyclerItems=searchGroceryCategoryRecyclerItems;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_select, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.txt_item_select_name.setText(searchGroceryCategoryRecyclerItems.get(i).getCategory());

    }

    @Override
    public int getItemCount() {
        return searchGroceryCategoryRecyclerItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       CustomTextView txt_item_select_name;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_item_select_name=itemView.findViewById(R.id.txt_item_select_name);
        }
    }
}
