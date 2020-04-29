package com.techware.clickkart.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.ShopByCategoryListRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.CategoryWiseStoreResponseListener;
import com.techware.clickkart.listeners.StoreWiseStoreResponseListener;
import com.techware.clickkart.model.CategoryWiseStore.CategoryWiseStoreResponseBean;
import com.techware.clickkart.model.categoryliststore.StoreListWiseCategory;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.util.AppConstants;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.techware.clickkart.app.App.getInstance;

public class CategoryWiseStoreListActivity extends BaseAppCompatNoDrawerActivity {
    RecyclerView allStoresListRecyclerView;
    RecyclerView.LayoutManager allStoresListLayoutManager;
    ShopByCategoryListRecyclerViewAdapter allStoresListAdapter;
    LinearLayout llListBack;
    private String city_id;
    private String categoryName;
    private StoreListWiseCategory storeListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        showToolbar(false, "");
        llListBack = findViewById(R.id.ll_list_back);
        getIntenetData();
        getSharedPreferencedData();
        allStoreListSetUp();
        fetchStoreList();
        llListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIntenetData() {
        if (getIntent().hasExtra("category")){
            categoryName=getIntent().getStringExtra("category");
        }
    }

    private void getSharedPreferencedData() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        city_id = prfs.getString(AppConstants.PREFERENCE_CITY_ID, "");
    }

    private void fetchStoreList() {
        if (App.isNetworkAvailable()) {
            fetchStoreList(false, 1);
        }
    }

    private void fetchStoreList(final boolean isLoadMore, int pageNo) {
        JsonObject postData = new JsonObject();
        postData.addProperty("category_id", categoryName);
        DataManager.fetchCategoryWiseStoreList(postData, new StoreWiseStoreResponseListener() {
            @Override
            public void onLoadCompleted(StoreListWiseCategory storeListBean) {
                updateStoreList(isLoadMore, storeListBean);
                populateStoreList(storeListBean);
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(CategoryWiseStoreListActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStoreList(boolean isLoadMore, StoreListWiseCategory storeListBean) {
        if (isLoadMore) {
            this.storeListBean.getCategoryStores().addAll(storeListBean.getCategoryStores());
            this.storeListBean.setMeta(storeListBean.getMeta());
        } else {
            this.storeListBean = storeListBean;
        }
    }

    private void populateStoreList(StoreListWiseCategory storeListBean) {
        if (allStoresListAdapter == null) {
            allStoresListAdapter = new ShopByCategoryListRecyclerViewAdapter(this, storeListBean);
            allStoresListAdapter.setStoreListRecyclerAdapterListener(new ShopByCategoryListRecyclerViewAdapter.StoreListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    setProgressScreenVisibility(true, true);
                    fetchStoreList(isLoadMore, currentPageNumber + 1);
                }

                @Override
                public void onRefresh() {
                }

                @Override
                public void onSwipeRefreshingChange(boolean isSwipeRefreshing) {
                }

                @Override
                public void onSnackBarShow(String message) {
                }
            });
            allStoresListRecyclerView.setAdapter(allStoresListAdapter);
        } else {
            allStoresListAdapter.setLoadMore(false);
            //allStoresListAdapter.setFilterProductsListBean(storeListBean);
            allStoresListAdapter.notifyDataSetChanged();
        }
// swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }

    private void allStoreListSetUp() {
        allStoresListRecyclerView = findViewById(R.id.rcVw_storelist_for_category);
        allStoresListLayoutManager = new LinearLayoutManager(CategoryWiseStoreListActivity.this, LinearLayoutManager.VERTICAL, false);
        allStoresListRecyclerView.setLayoutManager(allStoresListLayoutManager);
        allStoresListRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
