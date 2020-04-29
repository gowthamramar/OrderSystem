package com.techware.clickkart.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.CategoryWiseStoreListActivity;
import com.techware.clickkart.model.help.HelpBean;
import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.widgets.CustomTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class HelpRecyclerViewAdapter extends RecyclerView.Adapter<HelpRecyclerViewAdapter.ViewHolder> {
   HelpBean helpBean;
    Context context;
    public HelpRecyclerViewAdapter(HelpBean helpBean, Context context) {
        this.helpBean=helpBean;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_help, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
            holder.helpTitle.setText(helpBean.getData().get(i).getIdentifier());
             holder.helpDetails.setText(Html.fromHtml((String)helpBean.getData().get(i).getData()));

    }

    @Override
    public int getItemCount() {
        return helpBean.getData().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextView helpTitle,helpDetails;
        public ViewHolder(View itemView) {
            super(itemView);
            helpTitle=itemView.findViewById(R.id.txt_help_title);
            helpDetails=itemView.findViewById(R.id.tx_help_details);
        }
    }
}
