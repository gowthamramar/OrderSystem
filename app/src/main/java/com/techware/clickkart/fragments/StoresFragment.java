package com.techware.clickkart.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.AllStoresListRecyclerViewAdapter;
import com.techware.clickkart.adapter.FavouriteStoresRecyclerViewAdapter;
import com.techware.clickkart.adapter.HomeStoreCategoryRecyclerViewAdapter;
import com.techware.clickkart.adapter.YourFavouriteStoresRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.PeopleFavouriteResponseListener;
import com.techware.clickkart.listeners.ShopByCategoriesResponseListener;
import com.techware.clickkart.listeners.ShopByStoreResponseListener;
import com.techware.clickkart.listeners.YourFavouriteResponseListener;
import com.techware.clickkart.model.AllStoreListBean;
import com.techware.clickkart.model.HomeStoreCategoryBean;
import com.techware.clickkart.model.PeopleFavouriteStores.PeopleFavouriteBean;
import com.techware.clickkart.model.StoreFavouriteBean;
import com.techware.clickkart.model.YourFavouriteBean.YourFavouriteBean;
import com.techware.clickkart.model.YourFavouriteStoreBean;
import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.model.shopbycategory.Data;
import com.techware.clickkart.model.shopbystore.StoreListBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.util.AppConstants;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.techware.clickkart.app.App.getInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoresFragment extends Fragment {

    ///favourite stores recyclerview setup//////
    HomeStoreCategoryRecyclerViewAdapter homeStoreCategoryRecyclerViewAdapter;
    RecyclerView favouriteStoreRecyclerView,favourite_recyclerView;
    RecyclerView.LayoutManager favouriteStoreLayoutManager;
    RecyclerView sweetsFavouriteStoreRecyclerView;
    RecyclerView.LayoutManager sweetsFavouriteStoreLayoutManager;
    RecyclerView oilFavouriteStoreRecyclerView;
    RecyclerView.LayoutManager oilFavouriteStoreLayoutManager;
    RecyclerView fruitsFavouriteStoreRecyclerView;
    RecyclerView.LayoutManager fruitsFavouriteStoreLayoutManager;
    RecyclerView vegFavouriteStoreRecyclerView;
    RecyclerView.LayoutManager vegFavouriteStoreLayoutManager;
    private ArrayList<StoreFavouriteBean> favouriteStoreRecyclerItems;
    FavouriteStoresRecyclerViewAdapter favouriteStore_adapter;
    View v;
    CustomTextView txtFirstPeopleFavouriteStore,txtSecondPeopleFavouriteStore,txtYourFavStore;
    ProgressBar progressBar;
    ///All Store list//
    RecyclerView oilAllStoresListRecyclerView;
    RecyclerView.LayoutManager oilFruitsAllStoresListLayoutManager;
    RecyclerView sweetsAllStoresListRecyclerView;
    RecyclerView.LayoutManager sweetsFruitsAllStoresListLayoutManager;
    RecyclerView.LayoutManager fruitsAllStoresListLayoutManager;
    RecyclerView fruitsAllStoresListRecyclerView;
    RecyclerView allStoresListRecyclerView;
    RecyclerView.LayoutManager allStoresListLayoutManager;
    RecyclerView veg_allStoresListRecyclerView;
    RecyclerView.LayoutManager veg_allStoresListLayoutManager;
    private ArrayList<AllStoreListBean> allStoresListRecyclerItems;
    AllStoresListRecyclerViewAdapter allStoresList_adapter;
    ///All your favourite store list//
    RecyclerView oilYour_favouriteRecyclerView;
    RecyclerView.LayoutManager oilYour_favouriteLayoutManager;
    RecyclerView sweetsYour_favouriteRecyclerView;
    RecyclerView.LayoutManager sweetsYour_favouriteLayoutManager;
    RecyclerView fruitsYour_favouriteRecyclerView;
    RecyclerView.LayoutManager fruitsYour_favouriteLayoutManager;
    RecyclerView your_favouriteRecyclerView;
    RecyclerView.LayoutManager your_favouriteLayoutManager;
    RecyclerView veg_your_favouriteRecyclerView;
    private RecyclerView.LayoutManager veg_your_favouriteLayoutManager;
    private ArrayList<YourFavouriteStoreBean> your_favouriteRecyclerItems;
    private YourFavouriteStoresRecyclerViewAdapter your_favourite_adapter;
    //store list recyclerview setup//
    private RecyclerView items_recyclerview;
    private RecyclerView storeCategoryRecyclerView;
    private RecyclerView.LayoutManager storeCategoryLayoutManager;
    private ArrayList<HomeStoreCategoryBean> storeCategoryRecyclerItems;
    private HomeStoreCategoryRecyclerViewAdapter storeCategory_adapter;
    private StoresFragment storesFragment;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private ViewFlipper viewFlipper;
    private String city_id;
    private StoreListBean storeListBean;
    String category;

    public StoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_stores2, container, false);
        initViews();
        getSharedPreferenceData();
        storeCategoryRecyclerViewSetup();
        shopByCategory();
        favouriteStoresRecyclerViewSetup();
        FavouriteStoreList(0);
        allStorelistRecyclerViewSetup();
        allStoreList(0);
        yourFavouriteStoreRecyclerViewSetUp();
        yourFavouriteList(0);
        viewFlipper.setDisplayedChild(0);
        return v;
    }



    private void yourFavouriteList(int value) {
        if (App.isNetworkAvailable()) {
            fetchYourStoreList(false, 1, value);
        } else {
            Toast.makeText(getContext(), R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchYourStoreList(boolean isLoadMore, int pageNo, int value) {
        JsonObject postData = null;
        if (value == 0) {
            postData = new JsonObject();
        } else if (value == 1) {
            postData = new JsonObject();
            postData.addProperty("category_id", category);
        }
        DataManager.fetchYourStoreList(postData, new YourFavouriteResponseListener() {
            @Override
            public void onLoadCompleted(YourFavouriteBean yourFavouriteBean) {
                populateYourStoreList(yourFavouriteBean);
            }

            @Override
            public void onLoadFailed(String error) {
                your_favouriteRecyclerView.setVisibility(View.GONE);
                txtYourFavStore.setVisibility(View.GONE);
             /*   Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    private void populateYourStoreList(YourFavouriteBean yourFavouriteBean) {
        if (yourFavouriteBean.getData().isEmpty()) {
            your_favouriteRecyclerView.setVisibility(View.GONE);
            txtYourFavStore.setVisibility(View.GONE);
        } else {
            your_favouriteRecyclerView.setVisibility(View.VISIBLE);
            txtYourFavStore.setVisibility(View.VISIBLE);
            your_favourite_adapter = new YourFavouriteStoresRecyclerViewAdapter(yourFavouriteBean, getContext());
            your_favouriteRecyclerView.setAdapter(your_favourite_adapter);
        }


    }


    private void allStoreList(int value) {
        if (App.isNetworkAvailable()) {
            fetchAllStoreList(false, 1, value);
        } else {
            Toast.makeText(getContext(), R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAllStoreList(final boolean isLoadMore, int pageNo, final int value) {
        JsonObject postData = new JsonObject();
        if (value == 0) {
            postData.addProperty("city_id", city_id);
            postData.addProperty("page", pageNo);
        } else if (value == 1) {
            postData.addProperty("city_id", city_id);
            postData.addProperty("page", pageNo);
            postData.addProperty("category_id", category);
        }

        DataManager.fetchShopByStoreList(postData, new ShopByStoreResponseListener() {
            @Override
            public void onLoadCompleted(StoreListBean storeListBean) {
                updateStoreList(isLoadMore, storeListBean);
                populateStoreList(storeListBean, value);
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStoreList(boolean isLoadMore, StoreListBean storeListBean) {
        if (isLoadMore) {
            this.storeListBean.getData().addAll(storeListBean.getData());
            this.storeListBean.setMeta(storeListBean.getMeta());
        } else {
            this.storeListBean = storeListBean;
        }
    }

    private void populateStoreList(StoreListBean storeListBean, final int value) {
        allStoresList_adapter = new AllStoresListRecyclerViewAdapter(storeListBean, getContext());
        allStoresListRecyclerView.setAdapter(allStoresList_adapter);
        if (allStoresList_adapter == null) {
            allStoresList_adapter = new AllStoresListRecyclerViewAdapter(storeListBean, getContext());
            allStoresList_adapter.setStoreListRecyclerAdapterListener(new AllStoresListRecyclerViewAdapter.StoreListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    /* baseAppCompatNoDrawerActivity.setProgressScreenVisibility(true, true);*/
                    fetchAllStoreList(isLoadMore, currentPageNumber + 1, value);
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
            allStoresListRecyclerView.setAdapter(allStoresList_adapter);
        } else {
            allStoresList_adapter.setLoadMore(false);
            allStoresList_adapter.setFilterProductsListBean(storeListBean);
            allStoresList_adapter.notifyDataSetChanged();
        }
// swipeView.setRefreshing(false);
        /*  baseAppCompatNoDrawerActivity.setProgressScreenVisibility(false, false);*/
    }


    private void shopByCategory() {
        if (App.isNetworkAvailable()) {
            fetchShopByCategory();
        } else {
            Toast.makeText(getContext(), R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchShopByCategory() {
        JsonObject postData = new JsonObject();
        postData.addProperty("store_id", "");
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

    private void getSharedPreferenceData() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        city_id = prfs.getString(AppConstants.PREFERENCE_CITY_ID, "");
    }

    private void populateCategories(CategoryListBean categoryListBean) {
        Data data = new Data();
        data.setCategoryName("All");
        data.setCategoryId("");
        categoryListBean.getData().add(0, data);
        storeCategory_adapter = new HomeStoreCategoryRecyclerViewAdapter(categoryListBean, getActivity(), storesFragment);
        storeCategory_adapter.setStoreCategoryAdapterListener(new HomeStoreCategoryRecyclerViewAdapter.StoreCategoryAdapterListener() {
            @Override
            public void itemClicked(int index, String category_id) {
                category = category_id;

                if (index == 0) {
                    viewFlipper.setDisplayedChild(0);
                    favouriteStoresRecyclerViewSetup();
                    FavouriteStoreList(0);
                    allStorelistRecyclerViewSetup();
                    allStoreList(0);
                    yourFavouriteStoreRecyclerViewSetUp();
                    yourFavouriteList(0);
                } else {
                    categoryPeopleFavourite();
                    /*  FavouriteStoreList(1);*/
                    allStoreList(1);
                    yourFavourite();
                    /* yourFavouriteList(1);*/
                }
            }
        });
        storeCategoryRecyclerView.setAdapter(storeCategory_adapter);
    }

    private void yourFavourite() {
        if (App.isNetworkAvailable()) {
            fetchCategoryYourFavourite();
        } else {
            Toast.makeText(getContext(), R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCategoryYourFavourite() {
        JsonObject postData = new JsonObject();
        postData.addProperty("category_id", category);
        DataManager.fetchCategoryYourFavourite(postData, new YourFavouriteResponseListener() {
            @Override
            public void onLoadCompleted(YourFavouriteBean yourFavouriteBean) {
                populateYourStoreList(yourFavouriteBean);
            }

            @Override
            public void onLoadFailed(String error) {
                your_favouriteRecyclerView.setVisibility(View.GONE);
                txtYourFavStore.setVisibility(View.GONE);
            }
        });
    }

    private void categoryPeopleFavourite() {
        if (App.isNetworkAvailable()) {
            fetchCategoryPeopleFavourite();
        } else {
            Toast.makeText(getContext(), R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCategoryPeopleFavourite() {
        JsonObject postData = new JsonObject();
        postData.addProperty("city_id", city_id);
        postData.addProperty("category_id", category);
        DataManager.fetchCategoryPeopleFavouriteStore(postData, new PeopleFavouriteResponseListener() {
            @Override
            public void onLoadCompleted(PeopleFavouriteBean peopleFavouriteBean) {
                txtFirstPeopleFavouriteStore.setVisibility(View.VISIBLE);
                favouriteStoreRecyclerView.setVisibility(View.VISIBLE);
                populatePeopleFavouriteList(peopleFavouriteBean);
            }

            @Override
            public void onLoadFailed(String error) {
                txtFirstPeopleFavouriteStore.setVisibility(View.GONE);
                favouriteStoreRecyclerView.setVisibility(View.GONE);

            }
        });

    }

    private void FavouriteStoreList(int value) {
        if (App.isNetworkAvailable()) {
            fetchFavouriteStore(value);
        } else {
            Toast.makeText(getContext(), R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchFavouriteStore(int value) {
        JsonObject postData = new JsonObject();
        if (value == 0) {
            postData.addProperty("city_id", city_id);
        }
        else if (value == 1) {
            postData.addProperty("city_id", city_id);
            postData.addProperty("category_id", category);
        }
        DataManager.fetchFavouriteStore(postData, new PeopleFavouriteResponseListener() {
            @Override
            public void onLoadCompleted(PeopleFavouriteBean peopleFavouriteBean) {
                txtFirstPeopleFavouriteStore.setVisibility(View.VISIBLE);
                favouriteStoreRecyclerView.setVisibility(View.VISIBLE);
                populatePeopleFavouriteList(peopleFavouriteBean);
            }

            @Override
            public void onLoadFailed(String error) {
                txtFirstPeopleFavouriteStore.setVisibility(View.GONE);
                favouriteStoreRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void populatePeopleFavouriteList(PeopleFavouriteBean peopleFavouriteBean) {
        if (peopleFavouriteBean.getData().isEmpty()) {
            txtFirstPeopleFavouriteStore.setVisibility(View.GONE);
            txtSecondPeopleFavouriteStore.setVisibility(View.GONE);
            txtYourFavStore.setVisibility(View.GONE);
        } else {
            txtYourFavStore.setVisibility(View.VISIBLE);
            txtSecondPeopleFavouriteStore.setVisibility(View.VISIBLE);
            txtFirstPeopleFavouriteStore.setVisibility(View.VISIBLE);
            favouriteStoreRecyclerView.setVisibility(View.VISIBLE);
            favouriteStore_adapter = new FavouriteStoresRecyclerViewAdapter(peopleFavouriteBean, getContext());
            favouriteStoreRecyclerView.setAdapter(favouriteStore_adapter);
        }

    }


    private void storeCategoryRecyclerViewSetup() {
        storeCategoryRecyclerView = (RecyclerView) v.findViewById(R.id.items_recyclerview);
        storeCategoryLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        storeCategoryRecyclerView.setLayoutManager(storeCategoryLayoutManager);
        storeCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    private void initViews() {
        txtSecondPeopleFavouriteStore=v.findViewById(R.id.txtSecondPeopleFavouriteStore);
        txtFirstPeopleFavouriteStore=v.findViewById(R.id.txtFirstPeopleFavouriteStore);
        items_recyclerview = v.findViewById(R.id.items_recyclerview);
        txtYourFavStore=v.findViewById(R.id.txtyourfirst_fav);
        viewFlipper = v.findViewById(R.id.store_view_flipper);
    }

    private void yourFavouriteStoreRecyclerViewSetUp() {
        your_favouriteRecyclerView = v.findViewById(R.id.your_favourite_store_recyclerview);
        your_favouriteLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        your_favouriteRecyclerView.setLayoutManager(your_favouriteLayoutManager);
        your_favouriteRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void allStorelistRecyclerViewSetup() {
        allStoresListRecyclerView = v.findViewById(R.id.all_stores_recyclerview);
        allStoresListLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        allStoresListRecyclerView.setLayoutManager(allStoresListLayoutManager);
        allStoresListRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void favouriteStoresRecyclerViewSetup() {
        favouriteStoreRecyclerView = v.findViewById(R.id.favourite_recyclerView);
        favouriteStoreLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        favouriteStoreRecyclerView.setLayoutManager(favouriteStoreLayoutManager);
        favouriteStoreRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }


}
