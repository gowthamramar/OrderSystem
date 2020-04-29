package com.techware.clickkart.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.FindLoginSignupActivity;
import com.techware.clickkart.activity.HomePageActivity;
import com.techware.clickkart.activity.StoreActivity;
import com.techware.clickkart.listeners.CartResponseListener;
import com.techware.clickkart.model.cart.CartBean;
import com.techware.clickkart.model.searchproduct.SearchProductBean;
import com.techware.clickkart.model.storewisesearchedproduct.Data;
import com.techware.clickkart.model.storewisesearchedproduct.StorewiseSearchProductBean;
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
public class SearchProductStoreWiseListRecyclerViewAdapter extends RecyclerView.Adapter<SearchProductStoreWiseListRecyclerViewAdapter.ViewHolder> {
    private SearchProductBean searchProductBean;
    private HomePageActivity homePageActivity;
    private StoreActivity storeActivity;
    private boolean isLoadMore;
    private int currentPage;
    private int totalPages;
    private int quantity;
    SearchListRecyclerAdapterListener searchListRecyclerAdapterListener;
    private StorewiseSearchProductBean storewiseSearchProductBean;

    public SearchProductStoreWiseListRecyclerViewAdapter(HomePageActivity homePageActivity, SearchProductBean searchProductBean) {
        this.searchProductBean = searchProductBean;
        this.homePageActivity = homePageActivity;
    }

    public SearchProductStoreWiseListRecyclerViewAdapter(StoreActivity storeActivity, StorewiseSearchProductBean storewiseSearchProductBean) {
        this.storewiseSearchProductBean = storewiseSearchProductBean;
        this.storeActivity = storeActivity;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public void setFilterProductsListBean(StorewiseSearchProductBean storewiseSearchProductBean) {
        this.storewiseSearchProductBean = storewiseSearchProductBean;
        try {
            currentPage = Integer.parseInt(storewiseSearchProductBean.getMeta().getCurrentPage());
            totalPages = Integer.parseInt(String.valueOf(storewiseSearchProductBean.getMeta().getTotalPages()));
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_storewise_product__search, viewGroup, false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
       holder.txtGrocery.setText(storewiseSearchProductBean.getData().get(i).getProductName());
        holder.txtPrice.setText(storewiseSearchProductBean.getData().get(i).getProductPrice()+"/kg");
        Glide.with(storeActivity).load(RetrofitClient.IMAGE_PATH+storewiseSearchProductBean.getData().get(i).getProductImage())
                .apply(RequestOptions.circleCropTransform())
                .into( holder.storeImage);
        if (i == storewiseSearchProductBean.getData().size() - 1
                && currentPage < totalPages) {
            searchListRecyclerAdapterListener.onRequestNextPage(true, currentPage);
        }
     setProductsQuantity(holder);
       addToCart(storewiseSearchProductBean.getData().get(i),holder);
    }

    private void addToCart(Data data, ViewHolder holder) {
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity>0){
                    JsonObject postData = getJsonObject(data);
                    DataManager.addToCart(postData, new CartResponseListener() {
                        @Override
                        public void onLoadCompleted(CartBean cartBean) {
                            Toast.makeText(storeActivity, "Product SuccessFully Added to Cart", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onLoadFailed(String error) {
                            Toast.makeText(storeActivity,error,Toast.LENGTH_SHORT).show();
                        }
                    });}
                else{
                    Toast.makeText(storeActivity, "Please select quantity greater than 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private JsonObject getJsonObject(Data data) {
        JsonObject postData=new JsonObject();
        postData.addProperty("product_id",data.getProductId());
        postData.addProperty("quantity",quantity);
        postData.addProperty("store_id",data.getStoreId());
        return  postData;
    }

    private void setProductsQuantity(ViewHolder holder) {
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity=Integer.valueOf(holder.productQuantity.getText().toString());
                if (quantity<10){
                    quantity++;
                    holder.productQuantity.setText(String.valueOf(quantity));
                }
                else {
                    Toast.makeText(storeActivity,"You can select maximum quantity 10",Toast.LENGTH_SHORT).show();
                }

            }
        });
       holder.decerement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity=Integer.valueOf(holder.productQuantity.getText().toString());
                if (quantity>1){
                    quantity--;
                    holder.productQuantity.setText(String.valueOf(quantity));
                }
                else {
                    Toast.makeText(storeActivity,"Please select quantity greater than 1",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return storewiseSearchProductBean.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txtGrocery,txtStore,txtPrice,decerement,increment,productQuantity,addToCart;
        ImageView storeImage;
        LinearLayout llGrocery;

        public ViewHolder(View itemView) {
            super(itemView);
            addToCart=itemView.findViewById(R.id.addtoCart);
            productQuantity=itemView.findViewById(R.id.txt_dialog_quantity);
            decerement=itemView.findViewById(R.id.dialog_quantity_decrement);
            increment=itemView.findViewById(R.id.dialog_quantitiy_increment);
            storeImage=itemView.findViewById(R.id.iv_storeWise__grocery);
            txtGrocery = itemView.findViewById(R.id.txt_storeWise_itemGrocery);
            txtPrice = itemView.findViewById(R.id.txt_storeWise__grocery_price);
            llGrocery=itemView.findViewById(R.id.ll_item_grocery_search);

        }
    }

    public SearchListRecyclerAdapterListener getLocationListRecyclerAdapterListener() {
        return searchListRecyclerAdapterListener;
    }

    public void setSearchListRecyclerAdapterListener(SearchListRecyclerAdapterListener searchListRecyclerAdapterListener) {
        this.searchListRecyclerAdapterListener = searchListRecyclerAdapterListener;
    }

    public static interface SearchListRecyclerAdapterListener {
        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeRefreshing);

        void onSnackBarShow(String message);


    }
}
