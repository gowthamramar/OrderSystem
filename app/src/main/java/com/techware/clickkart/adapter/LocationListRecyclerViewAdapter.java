package com.techware.clickkart.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.techware.clickkart.R;
import com.techware.clickkart.activity.LocationActivity;
import com.techware.clickkart.model.locationbean.LocationListItemData;
import com.techware.clickkart.model.locationbean.LocationResponseBean;
import com.techware.clickkart.widgets.CustomTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class LocationListRecyclerViewAdapter extends RecyclerView.Adapter<LocationListRecyclerViewAdapter.ViewHolder> {
    private LocationResponseBean locationResponseBean;
    LocationActivity context;
    private boolean isLoadMore;
    private int currentPage;
    private int totalPages;
    LocationListRecyclerAdapterListener locationListRecyclerAdapterListener;

    public LocationListRecyclerViewAdapter(LocationActivity context, LocationResponseBean locationResponseBean) {
        this.locationResponseBean = locationResponseBean;
        this.context = context;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void setFilterProductsListBean(LocationResponseBean locationResponseBean) {
        this.locationResponseBean = locationResponseBean;
        try {
            currentPage = Integer.parseInt(locationResponseBean.getMeta().getCurrentPage());
            totalPages = Integer.parseInt(String.valueOf(locationResponseBean.getMeta().getTotalPages()));
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_location_layout, viewGroup, false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        LocationListItemData locationBean = locationResponseBean.getLocationListDataBean().get(i);
        holder.locationName.setText(locationBean.getLocation());
        holder.locationZip.setText("Zip Code- " + locationBean.getZipCode());
        if (i == locationResponseBean.getLocationListDataBean().size() - 1
                && currentPage < totalPages) {
            locationListRecyclerAdapterListener.onRequestNextPage(true, currentPage);
        }
        holder.ll_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationListRecyclerAdapterListener.onItemClicked(locationBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationResponseBean.getLocationListDataBean().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView locationName, locationZip;
        ImageView storeImage;
        LinearLayout ll_Location;

        public ViewHolder(View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.txt_itemLocation);
            locationZip = itemView.findViewById(R.id.txt_itemZip);
            ll_Location = itemView.findViewById(R.id.ll_location);

        }
    }

    public LocationListRecyclerAdapterListener getLocationListRecyclerAdapterListener() {
        return locationListRecyclerAdapterListener;
    }

    public void setLocationListRecyclerAdapterListener(LocationListRecyclerAdapterListener locationListRecyclerAdapterListener) {
        this.locationListRecyclerAdapterListener = locationListRecyclerAdapterListener;
    }

    public static interface LocationListRecyclerAdapterListener {
        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeRefreshing);

        void onSnackBarShow(String message);

        void onItemClicked(LocationListItemData locationDataBean);
    }
}
