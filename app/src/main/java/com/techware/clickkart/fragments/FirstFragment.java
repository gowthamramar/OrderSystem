package com.techware.clickkart.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class FirstFragment extends Fragment {


    private Context context;
    private View rootView;

    //////////////////////
    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        return rootView;
    }



}
