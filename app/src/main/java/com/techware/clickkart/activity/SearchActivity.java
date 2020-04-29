package com.techware.clickkart.activity;

import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.SearchGroceryCategoryRecyclerViewAdapter;
import com.techware.clickkart.model.SearchGroceryCategoryBean;
import com.techware.clickkart.widgets.CustomEditText;

import java.util.ArrayList;

public class SearchActivity extends BaseAppCompatNoDrawerActivity {
    RecyclerView searchCategoryRecyclerView;
    RecyclerView.LayoutManager groceryCategoryLayoutManager;
    private ArrayList<SearchGroceryCategoryBean> searchGroceryCategoryRecyclerItems;
    SearchGroceryCategoryRecyclerViewAdapter SearchGroceryCategory_adapter;
    ImageView iv_searchBack;
    private CustomEditText txtSearchCategory;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        showToolbar(false,"");
        initViews();
        statuscolor();
      searchRecyclerViewSetup();
        iv_searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews() {
        searchCategoryRecyclerView=findViewById(R.id.recycler_search_activity);
        iv_searchBack=findViewById(R.id.iv_searchBack);
        txtSearchCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = txtSearchCategory.getText().toString().trim();
                if (searchText.length() >= 1) {
                    searchCategoryRecyclerView.setVisibility(View.VISIBLE);
                    search(false, 1);
                } else {
                    searchCategoryRecyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void search(final boolean isLoadMore, int pageNo) {
        JsonObject postData = new JsonObject();
        postData.addProperty("location", searchText);
        postData.addProperty("page", pageNo);
    }

    private void searchRecyclerViewSetup() {
        groceryCategoryLayoutManager = new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.HORIZONTAL,false);
        searchCategoryRecyclerView.setLayoutManager(groceryCategoryLayoutManager);
        searchCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SearchGroceryCategory_adapter = new SearchGroceryCategoryRecyclerViewAdapter(searchGroceryCategoryRecyclerItems,SearchActivity.this);
        searchCategoryRecyclerView.setAdapter(SearchGroceryCategory_adapter);
    }

    private void statuscolor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.homepage_toolbar_color)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.homepage_toolbar_color)); //status bar or the time bar at the top
        }
    }
}
