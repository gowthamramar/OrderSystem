package com.techware.clickkart.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.HomeShopByCategoriesRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.ShopByCategoriesResponseListener;
import com.techware.clickkart.model.HomeCategoryBean;
import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.net.DataManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    RecyclerView categoriesRecyclerView;
    RecyclerView.LayoutManager categoriesLayoutManager;
    private ArrayList<HomeCategoryBean> categoryRecyclerItems;
    HomeShopByCategoriesRecyclerViewAdapter categories_adapter;
    View rootView;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView= inflater.inflate(R.layout.fragment_category, container, false);
        shopByCategoriesAdapterSetup();
        shopByCategory();

        return rootView;
    }

    private void shopByCategory() {
        if (App.isNetworkAvailable()){
            fetchShopByCategory();
        }
        else{
            Toast.makeText(getContext(), R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }
    private void fetchShopByCategory() {
        JsonObject postData=new JsonObject();
        postData.addProperty("store_id","");
        DataManager.fetchCategories(postData, new ShopByCategoriesResponseListener() {
            @Override
            public void onLoadCompleted(CategoryListBean categoryListBean) {
                populateCategories(categoryListBean);
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateCategories(CategoryListBean categoryListBean) {
        categories_adapter = new HomeShopByCategoriesRecyclerViewAdapter(categoryListBean, getContext());
        categoriesRecyclerView.setAdapter(categories_adapter);
    }

    private void shopByCategoriesAdapterSetup() {
        categoriesRecyclerView =rootView.findViewById(R.id.CategoriesFragmentRecyclerview);
        categoriesLayoutManager = new GridLayoutManager(getContext(), 3);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
        categoriesRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
