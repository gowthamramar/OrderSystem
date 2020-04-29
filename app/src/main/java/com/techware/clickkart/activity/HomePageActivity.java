package com.techware.clickkart.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.HomeFragmentPagerAdapter;
import com.techware.clickkart.adapter.SearchCategoryListRecyclerViewAdapter;
import com.techware.clickkart.adapter.SearchProductListRecyclerViewAdapter;
import com.techware.clickkart.adapter.SearchStoreListRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.config.Config;
import com.techware.clickkart.listeners.SearchStoreResponseListener;
import com.techware.clickkart.listeners.SearchedCategoriesResponseListener;
import com.techware.clickkart.listeners.SearchedProductsResponseListener;
import com.techware.clickkart.listeners.ZipCodeListener;
import com.techware.clickkart.model.locationbean.LocationResponseBean;
import com.techware.clickkart.model.login.LoginResponseBean;
import com.techware.clickkart.model.searchcategory.SearchedCategoryList;
import com.techware.clickkart.model.searchproduct.SearchProductBean;
import com.techware.clickkart.model.searchstore.SearchStoreBean;
import com.techware.clickkart.model.zipcode.ZipCodeResponseBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.util.AppConstants;
import com.techware.clickkart.util.CustomViewPager;
import com.techware.clickkart.widgets.CustomEditText;
import com.techware.clickkart.widgets.CustomTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomePageActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    private final String TAG = HomePageActivity.class.getSimpleName();
    private DrawerLayout drawer;
    private ActionBarDrawerToggle t;
    private String name,image;
    private NavigationView navleft;
    private LinearLayout llSearchIcon, llStoreSearch, llSearchGroceryIcon;
    private  Dialog dialog, groceryDialog, storeDialog, categoryDialog;
    private FragmentPagerAdapter adapterViewPager;
    private CustomTextView txtLogout, txtCancel, txt_home_location, txtSeeAllStore, navName;
    private CustomViewPager vpPager;
    private SearchStoreBean searchStoreBean = new SearchStoreBean();
    private LoginResponseBean loginResponseBean;

    private CustomEditText txtHomeSearch, dialogSearch, dialogStoreSearch, dialogCategorySearch;
    private LinearLayout ll_search, ll_category, ll_home, ll_stores, ll_cart, ll_home_location, iv_three_dotsHome, llUserProfile, llGrocerySerch, ll_dialogBackGroceries, ll_dialogBackStores, ll_dialogBackCategories;
    private ImageView imgSearch, imgCategory, imgCkHome, imgStores, imgCart, iv_logout_cancel, ivGroceryBack,navImageView;
    private FragmentPagerAdapter adapter;
    private LocationManager locationManager;
    private String zipCode;
    private String searchText, storeSearchText, categorySearchedText;
    private SearchCategoryListRecyclerViewAdapter searchCategoryListRecyclerViewAdapter;
    private SearchStoreListRecyclerViewAdapter searchStoreListRecyclerViewAdapter;
    private RecyclerView searchedRecyclerView, searchedStoreRecyclerView, searchedCategoryRecyclerView;
    private LinearLayoutManager searchedLayoutManager, searchedStoreManager, searchedCategoryManager;
    private SearchProductBean searchProductBean = new SearchProductBean();
    private SearchedCategoryList searchedCategoryList = new SearchedCategoryList();
    private SearchProductListRecyclerViewAdapter searchProductListRecyclerViewAdapter;
    private ImageView progressImageView;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("zipCode")) {
                Log.i(TAG, "onReceive: " + intent.getStringExtra("zipCode"));
                locationClicked(intent.getStringExtra("zipCode"));
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        setHomeLocation();
        viewPagerSetup();

        setCkFragmentFirst();
//        showToolbar(false, "");
        initClick();
        drawyerSetUp();

    }

    private void drawyerSetUp() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        name = preferences.getString(AppConstants.PREFERENCE_KEY_FULL_NAME, "");
        image= preferences.getString(AppConstants.PREFERENCE_KEY_IMAGE, "");
        System.out.println("name is " + name);
        /* navName.setText(name);*/
//        getSupportActionBar().hide();
        drawer = (DrawerLayout) findViewById(R.id.activity_main);
        navleft = (NavigationView) findViewById(R.id.nv);
        navleft.setItemIconTintList(null);
        navName = navleft.getHeaderView(0).findViewById(R.id.nav_name);
        navImageView=navleft.getHeaderView(0).findViewById(R.id.navImageView);
        navName.setText(name);
        if(!image.isEmpty()){
            Glide.with(getApplicationContext()).load(RetrofitClient.IMAGE_PATH+image)
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .into(navImageView);
        }

     /*   if (getIntent().hasExtra("bean")){
            loginResponseBean = (LoginResponseBean) getIntent().getSerializableExtra("bean");
            Glide.with(getApplicationContext()).load(RetrofitClient.IMAGE_PATH+loginResponseBean.getData().getImage())
                    .into(navImageView);
        }*/
        t = new ActionBarDrawerToggle(this, drawer, R.string.Open, R.string.Close);
        drawer.addDrawerListener(t);
        t.syncState();

        navleft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.wallet:
                        startActivity(new Intent(HomePageActivity.this, WalletActivity.class));
                        break;
                    case R.id.location:
                        startActivity(new Intent(HomePageActivity.this, LocationActivity.class));
                        break;
                    case R.id.order:
                        startActivity(new Intent(HomePageActivity.this, OrderHistoryActivity.class));
                        break;
                    case R.id.help:
                        startActivity(new Intent(HomePageActivity.this, HelpActivity.class));
                        break;
                    case R.id.report_problem:
                        startActivity(new Intent(HomePageActivity.this, ReportProblemActivity.class));
                        break;
                    case R.id.logout:
                        dialog.show();
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });
        llUserProfile = (LinearLayout) navleft.getHeaderView(0);//.findViewById(R.id.nav_profile);
        llUserProfile.setOnClickListener(this);
        IntentFilter filter = new IntentFilter("com.techware.clickkart.activity.HomePageActivity.UpdateLocation");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        showToolbar(false, "");
        statusColor();

    }

    private void setHomeLocation() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        String location = preferences.getString(AppConstants.PREFERENCE_LOCATION, "");
        txt_home_location.setText(location);
    }

    private void currentLocation() {

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void initClick() {
        ll_dialogBackStores.setOnClickListener(this);
        ll_dialogBackCategories.setOnClickListener(this);
        ll_dialogBackGroceries.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        txt_home_location.setOnClickListener(this);
        txtHomeSearch.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        ll_category.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_stores.setOnClickListener(this);
        ll_cart.setOnClickListener(this);
        iv_three_dotsHome.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        txtHomeSearch.setOnClickListener(this);
        iv_logout_cancel.setOnClickListener(this);
    }

    private void setCkFragmentFirst() {
       /* vpPager.post(new Runnable() {
            @Override
            public void run() {
                vpPager.setCurrentItem(2);
                txtHomeSearch.setHint(getResources().getString(R.string.search_your_groceries_or_stores));
                imgCkHome.setImageResource(R.drawable.ck_home_icon_bottom);
            }
        });*/
        vpPager.setCurrentItem(2);
        txtHomeSearch.setHint(getResources().getString(R.string.search_your_groceries_or_stores));
        imgCkHome.setImageResource(R.drawable.ck_home_icon_bottom);
    }

    private void viewPagerSetup() {
        vpPager = (CustomViewPager) findViewById(R.id.homeViewPager);
        adapterViewPager = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setPagingEnabled(false);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("ViewPager", "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initViews() {

        setUpCategoryDialog();
        dialog = new Dialog(HomePageActivity.this, R.style.ThemeDialogCustom_NoBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        storeDialog = new Dialog(HomePageActivity.this, R.style.ThemeDialogCustom_NoBackground);
        storeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        storeDialog.setContentView(R.layout.dialog_store_search);
        llStoreSearch = storeDialog.findViewById(R.id.llSearchStoreDialog);
        storeDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        storeDialog.getWindow().getAttributes().gravity = Gravity.FILL_VERTICAL | Gravity.TOP;
        dialogStoreSearch = storeDialog.findViewById(R.id.dialog_txtStoreSearch);
        searchedStoreRecyclerView = storeDialog.findViewById(R.id.searchedStoreRecyclerView);
        searchedStoreManager = new LinearLayoutManager(HomePageActivity.this, LinearLayoutManager.VERTICAL, false);
        searchedStoreRecyclerView.setLayoutManager(searchedStoreManager);
        searchedStoreRecyclerView.setItemAnimator(new DefaultItemAnimator());
        groceryDialog = new Dialog(HomePageActivity.this, R.style.ThemeDialogCustom_NoBackground);
        groceryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        groceryDialog.setContentView(R.layout.dialog_grocery_search);
        llSearchGroceryIcon = groceryDialog.findViewById(R.id.llSearchGroceryDialog);
        groceryDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        groceryDialog.getWindow().getAttributes().gravity = Gravity.FILL_VERTICAL | Gravity.TOP;
        dialogSearch = groceryDialog.findViewById(R.id.dialog_txtSearch);
        ll_dialogBackGroceries = groceryDialog.findViewById(R.id.ll_dialogBackGroceries);
        ll_dialogBackStores = storeDialog.findViewById(R.id.ll_dialogBackStores);
        searchedRecyclerView = groceryDialog.findViewById(R.id.groceryRecyclerView);
        progressImageView = findViewById(R.id.prgress_imageView);
        searchedLayoutManager = new LinearLayoutManager(HomePageActivity.this, LinearLayoutManager.VERTICAL, false);
        searchedRecyclerView.setLayoutManager(searchedLayoutManager);
        searchedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dialogSearch.requestFocus();
        dialogStoreSearch.requestFocus();
        storeDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialogCategorySearch.requestFocus();groceryDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialogCategorySearch
        .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                categorySearchedText = dialogCategorySearch.getText().toString().trim();
                if (categorySearchedText.length() >= 1) {
                   searchedCategoryRecyclerView.setVisibility(View.VISIBLE);
                    searchCategories(false, 1);
                } else {
                    searchedCategoryRecyclerView.setVisibility(View.GONE);
                }
            }
        });
        dialogStoreSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                storeSearchText = dialogStoreSearch.getText().toString().trim();
                if (storeSearchText.length() >= 1) {
                    searchedStoreRecyclerView.setVisibility(View.VISIBLE);
                    searchStore(false, 1);
                } else {
                    searchedStoreRecyclerView.setVisibility(View.GONE);
                }
            }
        });
        dialogSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = dialogSearch.getText().toString();
                if (searchText.length() >= 1) {
                   searchedRecyclerView.setVisibility(View.VISIBLE);
                    searchGrocery(false, 1);
                } else {
                    searchedRecyclerView.setVisibility(View.GONE);
                }
            }
        });
        txt_home_location = findViewById(R.id.txt_home_location);
        txtCancel = dialog.findViewById(R.id.txt_cancel_dialog);
        txtLogout = dialog.findViewById(R.id.txt_logout_dialog);
        iv_logout_cancel = dialog.findViewById(R.id.iv_logout_cancel);
        iv_three_dotsHome = findViewById(R.id.iv_three_dotsHome);
        txtHomeSearch = findViewById(R.id.txt_home_search);
        ll_home_location = findViewById(R.id.ll_home_location);
        ll_search = findViewById(R.id.ll_search);
        ll_category = findViewById(R.id.ll_category);
        ll_home = findViewById(R.id.ll_home);
        ll_stores = findViewById(R.id.ll_stores);
        ll_cart = findViewById(R.id.ll_cart);
        imgSearch = findViewById(R.id.home_search_icon);
        imgCategory = findViewById(R.id.home_category_icon);
        imgCkHome = findViewById(R.id.home_ck_icon);
        imgStores = findViewById(R.id.home_stores_icon);
        imgCart = findViewById(R.id.home_cart_icon);
        txtHomeSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //startActivity(new Intent(HomePageActivity.this,SearchActivity.class));
                if (txtHomeSearch.getHint().equals(getResources().getString(R.string.search_your_groceries_or_stores))) {
                    dialogSearch.setText("");
                    groceryDialog.show();
                } else if (txtHomeSearch.getHint().equals(getResources().getString(R.string.search_store))) {
                    dialogStoreSearch.setText("");
                    storeDialog.show();
                } else {
                    dialogCategorySearch.setText("");
                    categoryDialog.show();
                }

                return false;
            }
        });
    }

    private void searchCategories(final boolean isLoadMore, int pageNo) {
        JsonObject postData = new JsonObject();
        postData.addProperty("category_name", categorySearchedText);
        postData.addProperty("page", pageNo);
        DataManager.performSearchCategories(postData, new SearchedCategoriesResponseListener() {
            @Override
            public void onLoadCompleted(SearchedCategoryList searchedCategoryList) {
                llSearchIcon.setVisibility(View.GONE);
                searchedCategoryRecyclerView.setVisibility(View.VISIBLE);
                updateSearchedCategoriesList(isLoadMore, searchedCategoryList);
                populateSearchedCategoriesList(searchedCategoryList);
            }

            @Override
            public void onLoadFailed(String error) {
                llSearchIcon.setVisibility(View.VISIBLE);
                searchedCategoryRecyclerView.setVisibility(View.GONE);
            }
        });

    }

    private void populateSearchedCategoriesList(SearchedCategoryList searchedCategoryList) {
        if (searchCategoryListRecyclerViewAdapter == null) {
            this.searchedCategoryList = searchedCategoryList;
            searchCategoryListRecyclerViewAdapter = new SearchCategoryListRecyclerViewAdapter(HomePageActivity.this, searchedCategoryList);
            searchCategoryListRecyclerViewAdapter.setSearchListRecyclerAdapterListener(new SearchCategoryListRecyclerViewAdapter.SearchListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    setProgressScreenVisibility(true, true);
                    searchCategories(isLoadMore, currentPageNumber + 1);
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
            searchedCategoryRecyclerView.setAdapter(searchCategoryListRecyclerViewAdapter);
        } else {
            searchCategoryListRecyclerViewAdapter.setLoadMore(false);
            searchCategoryListRecyclerViewAdapter.setFilterProductsListBean(searchedCategoryList);
            searchCategoryListRecyclerViewAdapter.notifyDataSetChanged();
        }
// swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);

    }

    private void updateSearchedCategoriesList(boolean isLoadMore, SearchedCategoryList searchedCategoryList) {
        if (isLoadMore) {
            this.searchedCategoryList.getData().addAll(searchedCategoryList.getData());
            this.searchedCategoryList.setMeta(searchedCategoryList.getMeta());
        } else {
            this.searchedCategoryList.getData().clear();
            this.searchedCategoryList = searchedCategoryList;
        }
    }

    private void setUpCategoryDialog() {
        categoryDialog = new Dialog(HomePageActivity.this, R.style.ThemeDialogCustom_NoBackground);
        categoryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        categoryDialog.setContentView(R.layout.dialog_category_search);
        llSearchIcon = categoryDialog.findViewById(R.id.llSearchCatDialog);
        categoryDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        categoryDialog.getWindow().getAttributes().gravity = Gravity.FILL_VERTICAL | Gravity.TOP;
        dialogCategorySearch = categoryDialog.findViewById(R.id.dialog_txtCategorySearch);
        searchedCategoryRecyclerView = categoryDialog.findViewById(R.id.categoryRecyclerViewFragment);
        categoryDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        searchedCategoryManager = new LinearLayoutManager(HomePageActivity.this, LinearLayoutManager.VERTICAL, false);
        searchedCategoryRecyclerView.setLayoutManager(searchedCategoryManager);
        searchedCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ll_dialogBackCategories = categoryDialog.findViewById(R.id.ll_dialogBackCategories);
    }

    private void searchStore(final boolean isLoadMore, int pageNo) {
        JsonObject postData = new JsonObject();
        postData.addProperty("store_name", storeSearchText);
        postData.addProperty("page", pageNo);
        DataManager.performStoreSearch(postData, new SearchStoreResponseListener() {
            @Override
            public void onLoadCompleted(SearchStoreBean searchStoreBean) {
                searchedStoreRecyclerView.setVisibility(View.VISIBLE);
                llStoreSearch.setVisibility(View.GONE);
                updateSearchedStoreList(isLoadMore, searchStoreBean);
                populateSearchedStoreList(searchStoreBean);
            }

            @Override
            public void onLoadFailed(String error) {
                llStoreSearch.setVisibility(View.VISIBLE);
                searchedStoreRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void populateSearchedStoreList(SearchStoreBean searchStoreBean) {
        if (searchStoreListRecyclerViewAdapter == null) {
            this.searchStoreBean = searchStoreBean;
            searchStoreListRecyclerViewAdapter = new SearchStoreListRecyclerViewAdapter(HomePageActivity.this, searchStoreBean);
            searchStoreListRecyclerViewAdapter.setSearchListRecyclerAdapterListener(new SearchStoreListRecyclerViewAdapter.SearchListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    setProgressScreenVisibility(true, true);
                    searchStore(isLoadMore, currentPageNumber + 1);
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
            searchedStoreRecyclerView.setAdapter(searchStoreListRecyclerViewAdapter);
        } else {
            searchStoreListRecyclerViewAdapter.setLoadMore(false);
            searchStoreListRecyclerViewAdapter.setFilterProductsListBean(searchStoreBean);
            searchStoreListRecyclerViewAdapter.notifyDataSetChanged();
        }
// swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);

    }

    private void updateSearchedStoreList(boolean isLoadMore, SearchStoreBean searchStoreBean) {
        if (isLoadMore) {
            this.searchStoreBean.getData().addAll(searchStoreBean.getData());
            this.searchStoreBean.setMeta(searchStoreBean.getMeta());
        } else {
            this.searchStoreBean.getData().clear();
            this.searchStoreBean = searchStoreBean;
        }
    }

    private void searchGrocery(final boolean isLoadMore, int pageNo) {
        JsonObject postData = new JsonObject();
        postData.addProperty("product_name", searchText);
        postData.addProperty("page", pageNo);
        DataManager.performProductSearch(postData, new SearchedProductsResponseListener() {
            @Override
            public void onLoadCompleted(SearchProductBean searchProductBean) {
                searchedRecyclerView.setVisibility(View.VISIBLE);
                llSearchGroceryIcon.setVisibility(View.GONE);
                updateStoreList(isLoadMore, searchProductBean);
                populateSearchedProductsList(searchProductBean);
            }

            @Override
            public void onLoadFailed(String error) {
                llSearchGroceryIcon.setVisibility(View.VISIBLE);
                searchedRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void populateSearchedProductsList(SearchProductBean searchProductBean) {
        if (searchProductListRecyclerViewAdapter == null) {
            this.searchProductBean = searchProductBean;
            searchProductListRecyclerViewAdapter = new SearchProductListRecyclerViewAdapter(HomePageActivity.this, searchProductBean);
            searchProductListRecyclerViewAdapter.setSearchListRecyclerAdapterListener(new SearchProductListRecyclerViewAdapter.SearchListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    setProgressScreenVisibility(true, true);
                    searchGrocery(isLoadMore, currentPageNumber + 1);
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
            searchedRecyclerView.setAdapter(searchProductListRecyclerViewAdapter);
        } else {
            searchProductListRecyclerViewAdapter.setLoadMore(false);
            searchProductListRecyclerViewAdapter.setFilterProductsListBean(searchProductBean);
            searchProductListRecyclerViewAdapter.notifyDataSetChanged();
        }
// swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }


    private void updateStoreList(boolean isLoadMore, SearchProductBean searchProductBean) {
        if (isLoadMore) {
            this.searchProductBean.getData().addAll(searchProductBean.getData());
            this.searchProductBean.setMeta(searchProductBean.getMeta());
        } else {
            this.searchProductBean.getData().clear();
            this.searchProductBean = searchProductBean;
        }
    }


    private void statusColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.homepage_toolbar_color)); //status bar or the time bar at the top
        }
    }

    @Override
    public void onClick(View view) {
        /*  ll_search,ll_category,ll_home,ll_stores,ll_cart;*/
        if (view.getId() == ll_search.getId()) {
            startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
        } else if (view.getId() == txtHomeSearch.getId()) {
            txtHomeSearch.setFocusableInTouchMode(true);
        } else if (view.getId() == ll_category.getId()) {
            vpPager.setCurrentItem(1);
            txtHomeSearch.setHint(getResources().getString(R.string.search_category));
            ll_home_location.setVisibility(View.VISIBLE);
            imgCategory.setImageResource(R.drawable.cuccumber_icon);
            imgCkHome.setImageResource(R.drawable.ck_ic_normal);
            imgStores.setImageResource(R.drawable.store_bottom_icon);
        } else if (view.getId() == ll_home.getId()) {
            vpPager.setCurrentItem(2);
            txtHomeSearch.setHint(getResources().getString(R.string.search_your_groceries_or_stores));
            ll_home_location.setVisibility(View.VISIBLE);
            imgCkHome.setImageResource(R.drawable.ck_home_icon_bottom);
            imgCategory.setImageResource(R.drawable.category_bottom_icon);
            imgStores.setImageResource(R.drawable.store_bottom_icon);
        } else if (view.getId() == ll_stores.getId()) {
            vpPager.setCurrentItem(3);
            txtHomeSearch.setHint(getResources().getString(R.string.search_store));
            ll_home_location.setVisibility(View.GONE);
            imgStores.setImageResource(R.drawable.store_icon_with_colour);
            imgCkHome.setImageResource(R.drawable.ck_ic_normal);
            imgCategory.setImageResource(R.drawable.category_bottom_icon);
        } else if (view.getId() == ll_cart.getId()) {
            startActivity(new Intent(HomePageActivity.this, CartActivity.class));
        } else if (view.getId() == iv_three_dotsHome.getId()) {
            if (drawer.isDrawerOpen(navleft)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        } else if (llUserProfile.getId() == view.getId()) {
            startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
        } else if (iv_logout_cancel.getId() == view.getId()) {
            dialog.dismiss();
        } else if (txtLogout.getId() == view.getId()) {
            App.logout();
            startActivity(new Intent(HomePageActivity.this, FindLoginSignupActivity.class).putExtra("isSelectedCity", true)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (txtCancel.getId() == view.getId()) {
            dialog.dismiss();
        } else if (ll_dialogBackGroceries.getId() == view.getId()) {
            groceryDialog.dismiss();
        } else if (ll_dialogBackStores.getId() == view.getId()) {
            storeDialog.dismiss();
        } else if (ll_dialogBackCategories.getId() == view.getId()) {
            categoryDialog.dismiss();
        } else if (txt_home_location.getId() == view.getId()) {
            startActivity(new Intent(HomePageActivity.this, LocationActivity.class));
        }


    }


    public void locationClicked(String zipCode) {
        if (App.isNetworkAvailable()) {
            findZipCode(zipCode);
        } else {
            Toast.makeText(HomePageActivity.this, R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void findZipCode(String zipCode) {
        JsonObject postData = new JsonObject();
        postData.addProperty("zip_code", zipCode);
        DataManager.findZip(postData, new ZipCodeListener() {
            @Override
            public void onLoadCompleted(ZipCodeResponseBean zipCodeResponseBean) {
                Toast.makeText(HomePageActivity.this, "Location Changed Successfully", Toast.LENGTH_SHORT).show();
                Config.getInstance().setLocation(zipCodeResponseBean.getData().getLocation());
                Config.getInstance().setCity_id(zipCodeResponseBean.getData().getCityId());
                Config.getInstance().setZipCode(zipCodeResponseBean.getData().getZipCode());
                Config.getInstance().setCitySelected(true);
                App.saveToken();
                txt_home_location.setText(Config.getInstance().getLocation());
                startActivity(new Intent(HomePageActivity.this, HomePageActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(HomePageActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
