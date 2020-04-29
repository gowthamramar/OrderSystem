package com.techware.clickkart.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.techware.clickkart.R;
import com.techware.clickkart.adapter.DeliverAddressRecyclerViewAdapter;
import com.techware.clickkart.adapter.ProfileDeliverAddressRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.GetAddressListResponseListener;
import com.techware.clickkart.model.DeliveredAddressBean;
import com.techware.clickkart.model.getaddress.GetAddressBean;
import com.techware.clickkart.model.orderplaced.OrderPlacedBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;

public class DeliverAddressActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    CustomTextView txtAddNewAddress,txtContinueBtn;
///deliver recyclerview setup//////

    RecyclerView deliverRecyclerview;
    RecyclerView.LayoutManager deliverLayoutManager;
    DeliverAddressRecyclerViewAdapter deliverAddressRecyclerViewAdapter;
    ImageView llBackArrowDeliverAddress;
    private OrderPlacedBean orderPlacedBean;
    private String adrsId="";
    private String total_amount;

    //////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_address);
        showToolbar(false,"");
        initViews();
        initClick();
        DeliverAddressRecyclervIewSetup();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }
    private void getAddressList() {
        if (App.isNetworkAvailable()){
            fechAddressDetails();
        }
        else{
            Toast.makeText(this,R.string.no, Toast.LENGTH_SHORT).show();
        }
    }
    private void fechAddressDetails() {
        DataManager.getAddressList(new GetAddressListResponseListener() {
            @Override
            public void onLoadCompleted(GetAddressBean getAddressBean) {
                deliverAddressRecyclerViewAdapter = new DeliverAddressRecyclerViewAdapter(getAddressBean, DeliverAddressActivity.this);
                deliverAddressRecyclerViewAdapter.notifyDataSetChanged();
                deliverRecyclerview.setAdapter(deliverAddressRecyclerViewAdapter);
                populateAddressList(getAddressBean);
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(DeliverAddressActivity.this,error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateAddressList(GetAddressBean getAddressBean) {
        deliverAddressRecyclerViewAdapter = new DeliverAddressRecyclerViewAdapter(getAddressBean, DeliverAddressActivity.this);
        deliverAddressRecyclerViewAdapter.notifyDataSetChanged();
        deliverRecyclerview.setAdapter(deliverAddressRecyclerViewAdapter);
    }

    private void initClick() {
        txtAddNewAddress.setOnClickListener(this);
        llBackArrowDeliverAddress.setOnClickListener(this);
        txtContinueBtn.setOnClickListener(this);
    }

    private void initViews() {
        txtAddNewAddress=findViewById(R.id.txt_plus_new_address);
        llBackArrowDeliverAddress=findViewById(R.id.ll_BackArrowDeliverAddress);
        txtContinueBtn=findViewById(R.id.txt_continue_new_address);
        if (getIntent().hasExtra("bean")){
            orderPlacedBean= (OrderPlacedBean) getIntent().getSerializableExtra("bean");
        }
        if (getIntent().hasExtra("total_amount")){
            total_amount= getIntent().getStringExtra("total_amount");
        }
    }

    private void DeliverAddressRecyclervIewSetup() {
        deliverRecyclerview=findViewById(R.id.address_recyclerview);
        deliverLayoutManager =  new LinearLayoutManager(DeliverAddressActivity.this, LinearLayoutManager.VERTICAL, false);
        deliverRecyclerview.setLayoutManager(deliverLayoutManager);
        deliverRecyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==txtAddNewAddress.getId()){
            startActivity(new Intent(DeliverAddressActivity.this,AddNewAddressActivity.class)
            .putExtra("flag","0"));
        }
       else if (v.getId()==llBackArrowDeliverAddress.getId()){
            finish();
        }
       else if (txtContinueBtn.getId()==v.getId()){
           if (adrsId.equals("")){
               Toast.makeText(this, "Please choose an address", Toast.LENGTH_SHORT).show();
           }
           else{
               for (int i=0;i<orderPlacedBean.getData().size();i++){
                   orderPlacedBean.getData().get(i).setAddressId(adrsId);
                   orderPlacedBean.getData().get(i).setTotalAmount(Integer.valueOf(total_amount));
               }
               System.out.println("beanissssssssssss"+orderPlacedBean);
               startActivity(new Intent(DeliverAddressActivity.this,SheduledDateTimeActivity.class)
                       .putExtra("bean",orderPlacedBean));
           }

        }
    }

    public void getAddressId(String addressId) {
        adrsId=addressId;
    }

    public void finsh(View view) {
        finish();
    }
}
