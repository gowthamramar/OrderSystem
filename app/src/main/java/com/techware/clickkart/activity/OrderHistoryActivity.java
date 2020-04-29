package com.techware.clickkart.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.techware.clickkart.R;
import com.techware.clickkart.adapter.OrderHistoryRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.OrderHistoryListener;
import com.techware.clickkart.model.orderhistory.OrderHistoryBean;
import com.techware.clickkart.net.DataManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    /**
     * Set scrolling threshold here (for now assuming 10 item in one page)
     */
    private final int PAGE_SIZE = 10;

    RecyclerView historyRecyclerView;
    LinearLayoutManager historyLayoutManager;
    OrderHistoryRecyclerViewAdapter orderHistoryRecyclerViewAdapter;
    LinearLayout llHistoryBack,ll_NoOrderHistory;

    private OrderHistoryBean orderHistoryBean;
    private boolean isDataLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        showToolbar(false, "");
        recyclerViewSetup();
        initViews();
        initClick();
        fetchOrderHistory();
    }

    private void fetchOrderHistory() {
        if (App.isNetworkAvailable()) {
            getHistory();
        } else {
            Toast.makeText(this, R.string.no, Toast.LENGTH_SHORT).show();
        }
    }

    private void getHistory() {
        isDataLoading = true;

        orderHistoryRecyclerViewAdapter.showLoader = true;
        orderHistoryRecyclerViewAdapter.notifyItemInserted(orderHistoryBean.getOrderHistoryList().size());

        DataManager.fetOrderHistory(orderHistoryBean.getMeta().getCurrentPage() + 1, new OrderHistoryListener() {

            @Override
            public void onLoadCompleted(OrderHistoryBean orderHistoryBean) {
                ll_NoOrderHistory.setVisibility(View.GONE);
                if (/*loadFirstPage || */orderHistoryBean.getMeta().getCurrentPage() <= 1) {
                    OrderHistoryActivity.this.orderHistoryBean.getOrderHistoryList().clear();
                }
//                if (loadFirstPage) {
//                    setProgressScreenVisibility(false, false)
//                } else {
                orderHistoryRecyclerViewAdapter.showLoader = false;
                orderHistoryRecyclerViewAdapter.notifyItemRemoved(orderHistoryBean.getOrderHistoryList().size());
//                }

                OrderHistoryActivity.this.orderHistoryBean.getOrderHistoryList().addAll(orderHistoryBean.getOrderHistoryList());
                OrderHistoryActivity.this.orderHistoryBean.setMeta(orderHistoryBean.getMeta());
                orderHistoryRecyclerViewAdapter.notifyDataSetChanged();
                isDataLoading = false;
            }

            @Override
            public void onLoadFailed(String error) {
                ll_NoOrderHistory.setVisibility(View.VISIBLE);
                isDataLoading = false;
            }
        });
    }

    private void initClick() {
        ll_NoOrderHistory.setVisibility(View.VISIBLE);
        llHistoryBack.setOnClickListener(this);
    }

    private void initViews() {
        llHistoryBack = findViewById(R.id.ll_history_back);
        ll_NoOrderHistory=findViewById(R.id.ll_no_orderHistory);
    }

    private void recyclerViewSetup() {
        orderHistoryBean = new OrderHistoryBean();

        historyRecyclerView = findViewById(R.id.recView_orderHistory);
        historyLayoutManager = new LinearLayoutManager(OrderHistoryActivity.this, RecyclerView.VERTICAL, false);
        historyRecyclerView.setLayoutManager(historyLayoutManager);
        historyRecyclerView.setItemAnimator(new DefaultItemAnimator());

        orderHistoryRecyclerViewAdapter = new OrderHistoryRecyclerViewAdapter(orderHistoryBean.getOrderHistoryList(), OrderHistoryActivity.this);
        historyRecyclerView.setAdapter(orderHistoryRecyclerViewAdapter);
        historyRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int visibleItemCount = historyLayoutManager.getChildCount();
                int totalItemCount = historyLayoutManager.getItemCount();
                int firstVisibleItemPosition = historyLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (!isDataLoading
                        && orderHistoryBean.getMeta().getCurrentPage() < orderHistoryBean.getMeta().getTotalPages()) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                        getHistory();
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == llHistoryBack.getId()) {
            finish();
        }
    }

    public void finsh(View view) {
        finish();
    }
}
