package com.techware.clickkart.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.activity.StoreListActivity;
import com.techware.clickkart.adapter.HomeAdRecyclerViewAdapter;
import com.techware.clickkart.adapter.HomeHotDealsRecyclerViewAdapter;
import com.techware.clickkart.adapter.HomePastOrderRecyclerViewAdapter;
import com.techware.clickkart.adapter.HomeShopByCategoriesRecyclerViewAdapter;
import com.techware.clickkart.adapter.HomeShopByStoresRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.AdResponseListener;
import com.techware.clickkart.listeners.PastOrderResponseListener;
import com.techware.clickkart.listeners.ShopByCategoriesResponseListener;
import com.techware.clickkart.listeners.ShopByStoreResponseListener;
import com.techware.clickkart.model.HomeAdBean;
import com.techware.clickkart.model.HomeCategoryBean;
import com.techware.clickkart.model.HomeHotDealsBean;
import com.techware.clickkart.model.HomePastOrderBean;
import com.techware.clickkart.model.HomeStoreBean;
import com.techware.clickkart.model.pastorders.PastOrberBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;
import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.model.shopbystore.StoreListBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.util.AppConstants;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.techware.clickkart.app.App.getInstance;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment {
    View rootView;
    ImageView dialogProduct;
    CustomTextView txtPastOrder, txt_shopByCategories;

    ///ad recyclerview setup//////
    private LinearLayout llHomeShopByStore;
    RecyclerView adRecyclerView;
    Context context;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<HomeAdBean> adRecyclerItems;
    HomeAdRecyclerViewAdapter ad_adapter;
    com.facebook.shimmer.ShimmerFrameLayout shimmerFrameLayout;

    //////////////////////
    ///shop stores recyclerview setup//////
    RecyclerView storesRecyclerView;
    RecyclerView.LayoutManager storesLayoutManager;
    private ArrayList<HomeStoreBean> storesRecyclerItems;
    HomeShopByStoresRecyclerViewAdapter stores_adapter;

    //////////////////////
    ///shop categories recyclerview setup//////
    RecyclerView categoriesRecyclerView;
    GridLayoutManager categoriesLayoutManager;
    private ArrayList<HomeCategoryBean> categoryRecyclerItems;
    HomeShopByCategoriesRecyclerViewAdapter categories_adapter;

    //////////////////////
    ///shop past orders recycler view setup//////
    RecyclerView pastOrdersRecyclerView;
    RecyclerView.LayoutManager pastOrdersLayoutManager;
    private ArrayList<HomePastOrderBean> pastOrdersRecyclerItems;
    HomePastOrderRecyclerViewAdapter pastOrders_adapter;

    //////////////////////
    ///shop hot deals recycler view setup//////
    RecyclerView hotDealsRecyclerView;
    RecyclerView.LayoutManager hotDealsLayoutManager;
    private ArrayList<HomeHotDealsBean> hotDealsRecyclerItems;
    HomeHotDealsRecyclerViewAdapter hotDeals_adapter;
    private CustomTextView txtSeeAllStore;
    private String city_id;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        initViews();
        getSharedPreferenceData();
        adRecyclerAdapterSetup();
//        shopByAd();
        shopByStoresAdapterSetup();
//        shopByStore();
        shopByCategoriesAdapterSetup();
//        shopByCategory();
        shopByPastOrdersSetup();
//        shopByPastOrder();
        /* hotDealsSetup();*/
        txtSeeAllStoreClick();
        loadDataFromServer();
        return rootView;
    }

    private void loadDataFromServer() {
        shopByAd();
        shopByStore();
        shopByCategory();
        shopByPastOrder();
    }

    private void shopByAd() {
        if (App.isNetworkAvailable()) {
            shimmerFrameLayout.startShimmer();
            fetchAd();
        } else {
            Toast.makeText(context, R.string.no, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAd() {
        DataManager.fetchAd(new AdResponseListener() {
            @Override
            public void onLoadCompleted(AdResponseBean adResponseBean) {
                if (adResponseBean == null) {
                    adRecyclerView.setVisibility(View.GONE);
                } else {
                    adRecyclerView.setVisibility(View.VISIBLE);
                    populateAdList(adResponseBean);
                }

            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void populateAdList(AdResponseBean adResponseBean) {
        ad_adapter = new HomeAdRecyclerViewAdapter(adResponseBean, context);
        adRecyclerView.setAdapter(ad_adapter);
    }

    private void getSharedPreferenceData() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        city_id = prfs.getString(AppConstants.PREFERENCE_CITY_ID, "");
    }

    private void shopByCategory() {
        if (App.isNetworkAvailable()) {
            fetchShopByCategory();
        } else {
            Toast.makeText(context, R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void shopByPastOrder() {
        if (App.isNetworkAvailable()) {
            fetchShopByPastOrder();
        } else {
            Toast.makeText(context, R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void shopByStore() {
        if (App.isNetworkAvailable()) {
            fetchShopByStore(false, 1);
        } else {
            Toast.makeText(context, R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchShopByPastOrder() {
        DataManager.fetchPastOrders(new PastOrderResponseListener() {
            @Override
            public void onLoadCompleted(PastOrberBean pastOrberBean) {
                populatePastOrderList(pastOrberBean);

            }

            @Override
            public void onLoadFailed(String error) {

//                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populatePastOrderList(PastOrberBean pastOrberBean) {
        if (pastOrberBean.getData().isEmpty()) {
            txtPastOrder.setVisibility(View.GONE);
        } else {
            txtPastOrder.setVisibility(View.VISIBLE);
            pastOrders_adapter = new HomePastOrderRecyclerViewAdapter(pastOrberBean, context);
            pastOrdersRecyclerView.setAdapter(pastOrders_adapter);
        }

    }

    private void fetchShopByCategory() {
        JsonObject postData = new JsonObject();
        postData.addProperty("store_id", "");
        DataManager.fetchCategories(postData, new ShopByCategoriesResponseListener() {
            @Override
            public void onLoadCompleted(CategoryListBean categoryListBean) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.hideShimmer();
                populateCategories(categoryListBean);
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateCategories(CategoryListBean categoryListBean) {
        categories_adapter = new HomeShopByCategoriesRecyclerViewAdapter(categoryListBean, context);
        categoriesRecyclerView.setAdapter(categories_adapter);
    }

    private void fetchShopByStore(boolean b, int pageNo) {
        JsonObject postData = new JsonObject();
        postData.addProperty("city_id", city_id);
        postData.addProperty("page", pageNo);
        DataManager.fetchShopByStoreList(postData, new ShopByStoreResponseListener() {
            @Override
            public void onLoadCompleted(StoreListBean storeListBean) {
                populateStoreList(storeListBean);
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateStoreList(StoreListBean storeListBean) {
        if (storeListBean.getData().isEmpty()) {
            llHomeShopByStore.setVisibility(View.GONE);
        } else {
            llHomeShopByStore.setVisibility(View.VISIBLE);
            stores_adapter = new HomeShopByStoresRecyclerViewAdapter(storeListBean, context);
            storesRecyclerView.setAdapter(stores_adapter);
        }

    }

    private void txtSeeAllStoreClick() {
        txtSeeAllStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StoreListActivity.class)
                );
            }
        });

    }

    private void initViews() {
        llHomeShopByStore = rootView.findViewById(R.id.ll_home_shopByStore);
        txtPastOrder = rootView.findViewById(R.id.txt_pastOrder);
        dialogProduct = rootView.findViewById(R.id.img_view_dialog_product);
        txtSeeAllStore = rootView.findViewById(R.id.txtSeeAllStore);
        shimmerFrameLayout = rootView.findViewById(R.id.homeShimmer);
        txt_shopByCategories = rootView.findViewById(R.id.txt_shopByCategories);
    }

    private void shopByPastOrdersSetup() {
        pastOrdersRecyclerView = rootView.findViewById(R.id.pastOrders_recyclerView);
        pastOrdersLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        pastOrdersRecyclerView.setLayoutManager(pastOrdersLayoutManager);
        pastOrdersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        pastOrdersRecyclerItems = new ArrayList<>();
    }

    private void shopByCategoriesAdapterSetup() {
        categoriesRecyclerView = rootView.findViewById(R.id.shopByCategoriesRecyclerview);
        categoriesLayoutManager = new GridLayoutManager(context, 3);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
        categoriesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryRecyclerItems = new ArrayList<>();
    }

    private void shopByStoresAdapterSetup() {
        storesRecyclerView = rootView.findViewById(R.id.shop_by_stores_recyclerview);
        storesLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        storesRecyclerView.setLayoutManager(storesLayoutManager);
        storesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        storesRecyclerItems = new ArrayList<>();
    }

    private void adRecyclerAdapterSetup() {
        adRecyclerView = rootView.findViewById(R.id.ad_recyclerView);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        adRecyclerView.setLayoutManager(layoutManager);
        adRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
