package com.techware.clickkart.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.LocationListRecyclerViewAdapter;
import com.techware.clickkart.listeners.LocationResponseListener;
import com.techware.clickkart.model.locationbean.LocationListItemData;
import com.techware.clickkart.model.locationbean.LocationResponseBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Locale;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LocationActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 200;
    private EditText location;
    private LinearLayout llVoice,llSearch;
    private Activity mContainerActivity;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    RecyclerView locationRecyclerView;
    private String searchText;
    private boolean isDataLoading;
    private LocationResponseBean locationResponseBean = new LocationResponseBean();
    private LocationListRecyclerViewAdapter locationListRecyclerViewAdapter;
    private LinearLayoutManager locationLayoutManager;
    private ImageView searchBack,ivLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        showToolbar(false, "");
        initViews();
        initClick();
        perfomLocationSearch();
        llVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognition();
            }
        });

    }

    private void initClick() {
        searchBack.setOnClickListener(this);
    }

    private void perfomLocationSearch() {
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = location.getText().toString().trim();
                if (searchText.length() >= 1) {
                    locationRecyclerView.setVisibility(View.VISIBLE);
                    searchLocation(false, 1);
                } else {
                    locationRecyclerView.setVisibility(View.GONE);
                }

            }
        });
    }

    private void searchLocation(final boolean isLoadMore, int pageNo) {
        JsonObject postData = new JsonObject();
        postData.addProperty("location", searchText);
        postData.addProperty("page", pageNo);
        DataManager.performLocationSearch(postData, new LocationResponseListener() {
            @Override
            public void onLoadCompleted(LocationResponseBean locationResponseBean) {
                locationRecyclerView.setVisibility(View.VISIBLE);
                updateStoreList(isLoadMore, locationResponseBean);
                populateStoreList(locationResponseBean);
            }

            @Override
            public void onLoadFailed(String error) {
                locationRecyclerView.setVisibility(View.GONE);
                Toast.makeText(LocationActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateStoreList(LocationResponseBean locationResponseBean) {
        if (locationListRecyclerViewAdapter == null) {
            this.locationResponseBean = locationResponseBean;
            locationListRecyclerViewAdapter = new LocationListRecyclerViewAdapter(LocationActivity.this, locationResponseBean);
            locationListRecyclerViewAdapter.setLocationListRecyclerAdapterListener(new LocationListRecyclerViewAdapter.LocationListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    setProgressScreenVisibility(true, true);
                    searchLocation(isLoadMore, currentPageNumber + 1);
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

                @Override
                public void onItemClicked(LocationListItemData locationDataBean) {
                    Intent intent = new Intent("com.techware.clickkart.activity.HomePageActivity.UpdateLocation");
                    intent.putExtra("zipCode", locationDataBean.getZipCode());
                    sendBroadcast(intent);
                    finish();
                    /*startActivity(new Intent(LocationActivity.this,HomePageActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));*/
                }
            });
            locationRecyclerView.setAdapter(locationListRecyclerViewAdapter);
        } else {
            locationListRecyclerViewAdapter.setLoadMore(false);
            locationListRecyclerViewAdapter.setFilterProductsListBean(locationResponseBean);
            locationListRecyclerViewAdapter.notifyDataSetChanged();
        }
// swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);

    }

    private void updateStoreList(boolean isLoadMore, LocationResponseBean locationResponseBean) {
        if (isLoadMore) {
            this.locationResponseBean.getLocationListDataBean().addAll(locationResponseBean.getLocationListDataBean());
            this.locationResponseBean.setMeta(locationResponseBean.getMeta());
        } else {
            this.locationResponseBean.getLocationListDataBean().clear();
            this.locationResponseBean = locationResponseBean;
        }
    }

    private void initViews() {
        llSearch=findViewById(R.id.ll_search);
        location = findViewById(R.id.txt_location);
        llVoice = findViewById(R.id.ll_voice);
        searchBack = findViewById(R.id.iv_searchBack);
        locationRecyclerView = findViewById(R.id.locationRecyclerView);
        locationLayoutManager = new LinearLayoutManager(LocationActivity.this, LinearLayoutManager.VERTICAL, false);
        locationRecyclerView.setLayoutManager(locationLayoutManager);
        locationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        );


    }

    public void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    location.setText(result.get(0));

                }
                break;
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LocationActivity.this,HomePageActivity.class));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == searchBack.getId()) {
            finish();
            startActivity(new Intent(LocationActivity.this,HomePageActivity.class));
        }

    }

}
