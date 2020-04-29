package com.techware.clickkart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.CartRecyclerViewAdapter;
import com.techware.clickkart.listeners.CartListResponseListener;
import com.techware.clickkart.listeners.RemoveCartResponseListener;
import com.techware.clickkart.listeners.UpdateCartResponseListener;
import com.techware.clickkart.model.CartBean;
import com.techware.clickkart.model.cart.CartAmountBean;
import com.techware.clickkart.model.getcart.GetCartBean;
import com.techware.clickkart.model.orderplaced.OrderPlacedBean;
import com.techware.clickkart.model.removecart.RemoveItemCart;
import com.techware.clickkart.model.updatecart.UpdateCart;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    ///vegetables recyclerview setup//////

    RecyclerView cxartRecyclerview;
    RecyclerView.LayoutManager cartLayoutManager;
    private ArrayList<CartBean> cartRecyclerItems;
    CartRecyclerViewAdapter cartRecyclerViewAdapter;
    private CustomTextView txtCartItemQuantity, txtCartBulkAmount;
    //////////////////////
    CustomTextView btn_cart_keepShopping, btn_cart_checkout;
    LinearLayout ll_cartBackArrow;
    private int pos;
    private int size;
    private GetCartBean getCartBean;
    private GetCartBean orderPlaceCartBean;
    String total;
    GetCartBean cartBean;
    LinearLayout llNoCart,ll_bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        showToolbar(false, "");
        initViews();
        initClick();
        setProgressScreenVisibility(false, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartRecyclerViewSetup();
        fetchCartList();

    }


    private void setListener() {
        cartRecyclerViewAdapter.setCartAdapterListener(new CartRecyclerViewAdapter.CartAdapterListener() {
            @Override
            public void setBulkAmount(CartAmountBean cartAmountBean) {
                String getTotal=String.valueOf(cartAmountBean.getTotalAmount());
                txtCartBulkAmount.setText(getTotal+".00");
                total=getTotal;
            }

            @Override
            public void calculateBulkAmount( CartAmountBean cartAmountBean) {
                String getTotal=String.valueOf(cartAmountBean.getTotalAmount());
                txtCartBulkAmount.setText(getTotal+".00");
                total=getTotal;

            }

            @Override
            public void itemsCount(int size) {

                txtCartItemQuantity.setText(String.valueOf(size)+" "+"Items");
            }

            @Override
            public void updateCart(String productId, int cartQuantity, String storeId) {
                performUpdateCart(productId,cartQuantity,storeId);
            }
        });
    }

    private void performUpdateCart(String productId, int cartQuantity, String storeId) {
        JsonObject postData=getJsonObject(productId,cartQuantity,storeId);
        DataManager.performUpdateCart(postData, new UpdateCartResponseListener() {
            @Override
            public void onLoadCompleted(UpdateCart updateCart) {
                Toast.makeText(CartActivity.this,R.string.updated_success, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(CartActivity.this,R.string.updated_success, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private JsonObject getJsonObject(String productId, int cartQuantity, String storeId) {
        JsonObject postData=new JsonObject();
        postData.addProperty("product_id",productId);
        postData.addProperty("quantity",cartQuantity);
        postData.addProperty("store_id",storeId);
        return postData;
    }


    private void fetchCartList() {
        DataManager.fetchCartList(new CartListResponseListener() {
            @Override
            public void onLoadCompleted(GetCartBean bean) {
                cartBean=bean;
                if (!bean.getData().isEmpty()){
                    llNoCart.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.VISIBLE);
                    cartRecyclerViewAdapter = new CartRecyclerViewAdapter(bean, CartActivity.this);
                    setListener();
                    cxartRecyclerview.setAdapter(cartRecyclerViewAdapter);
                }

            }

            @Override
            public void onLoadFailed(String error) {
                llNoCart.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.GONE);
                txtCartItemQuantity.setText("0"+" Items");
                txtCartBulkAmount.setText("00.00");
            }
        });
    }

    private void initClick() {
        ll_cartBackArrow.setOnClickListener(this);
        btn_cart_keepShopping.setOnClickListener(this);
        btn_cart_checkout.setOnClickListener(this);
    }

    private void initViews() {
        ll_bottom=findViewById(R.id.ll_cart_bottom);
        llNoCart=findViewById(R.id.ll_Nocart);
        ll_cartBackArrow = findViewById(R.id.ll_cartBackArrow);
        cxartRecyclerview = findViewById(R.id.cartRecyclerView);
        btn_cart_keepShopping = findViewById(R.id.btn_cart_keepShopping);
        btn_cart_checkout = findViewById(R.id.btn_cart_checkout);
        txtCartItemQuantity = findViewById(R.id.txt_cart_item_quantity);
        txtCartBulkAmount = findViewById(R.id.txtCartBulkAmount);


    }

    private void cartRecyclerViewSetup() {
        cartLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        cxartRecyclerview.setLayoutManager(cartLayoutManager);
        cxartRecyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        if (btn_cart_keepShopping.getId() == v.getId()) {
            btn_cart_keepShopping.setBackground(getResources().getDrawable(R.drawable.button_gradient));
            btn_cart_keepShopping.setTextColor(getResources().getColor(R.color.white));
            btn_cart_checkout.setBackground(getResources().getDrawable(R.drawable.clickkart_color_rounded));
            btn_cart_checkout.setTextColor(getResources().getColor(R.color.keepshoping));
            startActivity(new Intent(CartActivity.this, HomePageActivity.class));

        } else if (btn_cart_checkout.getId() == v.getId()) {
                OrderPlacedBean orderPlacedBean= cartRecyclerViewAdapter.getOrderPlacedBean();
                btn_cart_checkout.setBackground(getResources().getDrawable(R.drawable.button_gradient));
                btn_cart_keepShopping.setBackground(getResources().getDrawable(R.drawable.clickkart_color_rounded));
                btn_cart_checkout.setTextColor(Color.WHITE);
                btn_cart_keepShopping.setTextColor(getResources().getColor(R.color.keepshoping));
                startActivity(new Intent(CartActivity.this, DeliverAddressActivity.class)
                        .putExtra("bean",orderPlacedBean)
                        .putExtra("total_amount",total));

        } else if (ll_cartBackArrow.getId() == v.getId()) {
            finish();

        }
    }

    public void removeFromCart(String productId, String storeId, int i, int sizes, GetCartBean getCartBeans) {
        pos = i;
        size = sizes;
        getCartBean = getCartBeans;
        JsonObject postData=getRemoveJsonObject(productId,storeId);
        DataManager.performRemoveCart(postData, new RemoveCartResponseListener() {
            @Override
            public void onLoadCompleted(RemoveItemCart removeItemCart) {
                llNoCart.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.GONE);
                getCartBeans.getData().remove(pos);
                cartRecyclerViewAdapter.notifyItemRemoved(pos);
                cartRecyclerViewAdapter.notifyItemRangeChanged(pos,size);
                cartRecyclerViewAdapter.notifyDataSetChanged();
                if (cartRecyclerViewAdapter.getItemCount()==0){
                    llNoCart.setVisibility(View.VISIBLE);
                    ll_bottom.setVisibility(View.GONE);
                }
                else {
                    llNoCart.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.VISIBLE);
                }
                Toast.makeText(CartActivity.this,R.string.removed_Success, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(CartActivity.this,error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private JsonObject getRemoveJsonObject(String productId, String storeId) {
        JsonObject postData=new JsonObject();
        postData.addProperty("product_id",productId);
        postData.addProperty("store_id",storeId);
        return postData;
    }

    public void finsh(View view) {
        finish();
    }
}
