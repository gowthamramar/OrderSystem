package com.techware.clickkart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.GroceryRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.OrderDetailsResponseListener;
import com.techware.clickkart.model.GroceryBean;
import com.techware.clickkart.model.bookidaddresid.BookAddressId;
import com.techware.clickkart.model.orderdetails.OrderDetailsBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;

public class OrderDetailsActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    LinearLayout llBack,llGrocerries,llOrderDetailsAddress,llAddress;
    RecyclerView groceryRecyclerview;
    ImageView ivGroceryArrow,ivDeliveryAddresArrow;
    boolean imgArrowRotate = true;
    boolean imgDeliveryArrowRotate = true;
    int angle,antiAngle;
    CustomTextView goToAccount,home;
    RecyclerView.LayoutManager groceryLayoutManager;
    private ArrayList<GroceryBean> groceryRecyclerItems;
    GroceryRecyclerViewAdapter groceryRecyclerViewAdapter;
    String booking_id,address_id;
    private CustomTextView name,type,location,zip,mobNo,sheduledDate,time,bookId,bookDate,store,status;
    private BookAddressId bookAddressId ;
    private CustomTextView totalAmount;
    private LinearLayout  llStoreDet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        showToolbar(false,"");
        initViews();
        if (getIntent().hasExtra("bean")){
            bookAddressId=(BookAddressId)getIntent().getSerializableExtra("bean");

        }
        System.out.println("booking_id+address_id"+booking_id+address_id);
        initClick();
        setOrderDetailsRecyclerViewSetUp();
        getOrderDetails();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OrderDetailsActivity.this,HomePageActivity.class));
    }

    private void getOrderDetails() {
        if (App.isNetworkAvailable()){
            JsonObject postData=getJsonObject();
            System.out.println("gashgu"+postData);
            DataManager.fetchOrderDetails(postData, new OrderDetailsResponseListener() {
                @Override
                public void onLoadCompleted(OrderDetailsBean orderDetailsBean) {
                    sheduledDate.setText(orderDetailsBean.getScheduledDate());
                    llStoreDet.setVisibility(View.GONE);
                    //store.setText(orderDetailsBean.getStore());
                    bookId.setText(orderDetailsBean.getBookingID());
                    bookDate.setText(orderDetailsBean.getBookingDate());
                    time.setText(orderDetailsBean.getTime());
                    totalAmount.setText(orderDetailsBean.getGroceries().get(0).getTotalAmount()+".00");
                    if (orderDetailsBean.getOrder_status().equals("1")){
                        status.setText("Ordered");
                    }
                    name.setText(orderDetailsBean.getDeliveryAddress().getName());
                    if (orderDetailsBean.getDeliveryAddress().getType().equals("1")){
                        type.setText("(Home)");
                    }
                    else if(orderDetailsBean.getDeliveryAddress().getType().equals("2")){
                        type.setText("(Work)");
                    }

                    location.setText(orderDetailsBean.getDeliveryAddress().getLandmark()+orderDetailsBean.getDeliveryAddress().getStreetAddress());
                    zip.setText(orderDetailsBean.getDeliveryAddress().getZipCode());
                    mobNo.setText(orderDetailsBean.getDeliveryAddress().getPhoneNumber());
                    groceryRecyclerViewAdapter = new GroceryRecyclerViewAdapter(orderDetailsBean, OrderDetailsActivity.this);
                    groceryRecyclerview.setAdapter(groceryRecyclerViewAdapter);
                }

                @Override
                public void onLoadFailed(String error) {
                    Toast.makeText(OrderDetailsActivity.this,error, Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
            Toast.makeText(this,R.string.no_net, Toast.LENGTH_SHORT).show();
        }
    }

    private JsonObject getJsonObject() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("address_id",bookAddressId.getAdsId());
        jsonObject.addProperty("booking_id",bookAddressId.getBookId());
        return  jsonObject;
    }

    private void setOrderDetailsRecyclerViewSetUp() {
        groceryRecyclerview = findViewById(R.id.groceries_recyclerView);
        groceryLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        groceryRecyclerview.setLayoutManager(groceryLayoutManager);
        groceryRecyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void initViews() {
        angle = 90;
        antiAngle = 0;
        llStoreDet=findViewById(R.id.ll_store_det);
        totalAmount=findViewById(R.id.txtOrderTotalAmount);
        status=findViewById(R.id.txt_order_status);
        //store=findViewById(R.id.txtorderStore);
        bookDate=findViewById(R.id.txtorderBookDate);
        time=findViewById(R.id.txt_orderDetails_sheduledTime);
        sheduledDate=findViewById(R.id.txt_orderDetailsSheduledDate);
        bookId=findViewById(R.id.txtorderBookId);
        llAddress=findViewById(R.id.ll_order_details_address);
        llOrderDetailsAddress=findViewById(R.id.ll_deliveryAddress);
        llBack=findViewById(R.id.ll_orderDetails_back);
        llGrocerries=findViewById(R.id.ll_grocerries);
        ivGroceryArrow=findViewById(R.id.iv_groceryArrow);
        ivDeliveryAddresArrow=findViewById(R.id.iv_arrow_delivery_address);
        goToAccount=findViewById(R.id.txt_gotoAccount);
        home=findViewById(R.id.txt_home);
        name=findViewById(R.id.txt_orderName);
        type=findViewById(R.id.txt_orderAdrsType);
        location=findViewById(R.id.txt_orderdetails_location);
        zip=findViewById(R.id.txt_orderdetails_zipcode);
        mobNo=findViewById(R.id.txt_orderdetails_phone);
    }

    private void initClick() {
        llBack.setOnClickListener(this);
        llGrocerries.setOnClickListener(this);
        ivGroceryArrow.setOnClickListener(this);
        llOrderDetailsAddress.setOnClickListener(this);
        ivDeliveryAddresArrow.setOnClickListener(this);
        goToAccount.setOnClickListener(this);
        home.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==llBack.getId()){
           startActivity(new Intent(OrderDetailsActivity.this,HomePageActivity.class));
        }
       else if (ivGroceryArrow.getId() == view.getId()||llGrocerries.getId() == view.getId()) {
            if (imgArrowRotate) {
                ivGroceryArrow.setRotation(angle);
                imgArrowRotate = false;
                groceryRecyclerview.setVisibility(View.VISIBLE);
            } else {
                ivGroceryArrow.setRotation(antiAngle);
                imgArrowRotate = true;
                groceryRecyclerview.setVisibility(View.GONE);
            }
        }
       else if (llOrderDetailsAddress.getId()==view.getId()||ivDeliveryAddresArrow.getId()== view.getId()){
            if (imgDeliveryArrowRotate) {
                ivDeliveryAddresArrow.setRotation(angle);
                imgDeliveryArrowRotate = false;
                llAddress.setVisibility(View.VISIBLE);
            } else {
                ivDeliveryAddresArrow.setRotation(antiAngle);
                imgDeliveryArrowRotate = true;
                llAddress.setVisibility(View.GONE);
            }
        }
      else  if (goToAccount.getId()==view.getId()){
            goToAccount.setBackground(getResources().getDrawable( R.drawable.button_gradient));
            goToAccount.setTextColor(getResources().getColor(R.color.white));
            home.setBackground(getResources().getDrawable( R.drawable.clickkart_color_rounded));
            home.setTextColor(getResources().getColor(R.color.keepshoping));
            startActivity(new Intent(OrderDetailsActivity.this, ProfileActivity.class));
        }
        else if (home.getId()==view.getId()){
            home.setBackground(getResources().getDrawable( R.drawable.button_gradient));
            goToAccount.setBackground(getResources().getDrawable( R.drawable.clickkart_color_rounded));
            home.setTextColor(Color.WHITE);
            goToAccount.setTextColor(getResources().getColor(R.color.keepshoping));
            startActivity(new Intent(OrderDetailsActivity.this, HomePageActivity.class));
        }
        }
    }

