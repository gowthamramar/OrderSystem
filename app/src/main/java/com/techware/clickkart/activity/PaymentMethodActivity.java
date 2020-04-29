package com.techware.clickkart.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techware.clickkart.listeners.OrderPlacedResponseListener;
import com.techware.clickkart.model.bookidaddresid.BookAddressId;
import com.techware.clickkart.model.orderplaced.OrderPlacedBean;
import com.techware.clickkart.model.orderplacedresponse.ResponseOrderPlaced;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.util.StackCardLayoutManager;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.CardLIstAdapter;
import com.techware.clickkart.model.CardModel;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;


public class PaymentMethodActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    public static CardLIstAdapter cardLIstAdapter;
    private ArrayList<CardModel> array;
    RecyclerView cardRecyclerView;
    StackCardLayoutManager layoutManager;
    CustomTextView txtAddNewCard,selectCard,txt_pay_btn,txtPaymentTotal;
    LinearLayout ll_cardLayout,ll_paymentBackArrow,card,paypal,cash;
    ImageView ivCardArrow;
    Dialog dialog;
    boolean imgCardRotate = true;
    RadioButton rbCashOnDelivery,rbPayPal,rbCreditCard;
    private String payment_status,payment_method="0";
    private OrderPlacedBean orderPlacedBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        initViews();
        showToolbar(false,"");
        initClick();
        array = new ArrayList<>();
        cardRecyclerView = findViewById(R.id.cardRecyclerview);
        array.add(new CardModel("2222 **** **** 4444"));
        array.add(new CardModel("2222 **** **** 4445"));
        array.add(new CardModel("2222 **** **** 4434"));
        array.add(new CardModel("2222 **** **** 4244"));
        cardLIstAdapter = new CardLIstAdapter(array, PaymentMethodActivity.this);
        int maxItemCount = 5;
        cardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new StackCardLayoutManager(maxItemCount);
        cardRecyclerView.setLayoutManager(layoutManager);
        cardRecyclerView.setAdapter(cardLIstAdapter);

        ItemTouchHelper itemDecor = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            int fromPos = viewHolder.getAdapterPosition();
                            int toPos = target.getAdapterPosition();
                            cardLIstAdapter.notifyItemMoved(fromPos, toPos);
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    }
            });
        itemDecor.attachToRecyclerView(cardRecyclerView);
    }

    private void initClick() {
        ll_paymentBackArrow.setOnClickListener(this);
        txtAddNewCard.setOnClickListener(this);
        ivCardArrow.setOnClickListener(this);
        ivCardArrow.setOnClickListener(this);
        txt_pay_btn.setOnClickListener(this);
        cash.setOnClickListener(this);
        card.setOnClickListener(this);
        paypal.setOnClickListener(this);
        rbPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbCashOnDelivery.setChecked(false);
                rbCreditCard.setChecked(false);
                rbPayPal.setChecked(true);
                payment_status="2";
                payment_method="2";
            }

        });
        rbCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbCashOnDelivery.setChecked(false);
                rbCreditCard.setChecked(true);
                rbPayPal.setChecked(false);
                payment_status="3";
                payment_method="3";
            }
        });
        rbCashOnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbCashOnDelivery.setChecked(true);
                rbCreditCard.setChecked(false);
                rbPayPal.setChecked(false);
                payment_status="1";
                payment_method="1";
            }
        });
       /* rbCashOnDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rbCashOnDelivery.setChecked(true);
                rbCreditCard.setChecked(false);
                rbPayPal.setChecked(false);
                payment_status="1";
                payment_method="1";
            }
        });*/
       /* rbCreditCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rbCashOnDelivery.setChecked(false);
                rbCreditCard.setChecked(true);
                rbPayPal.setChecked(false);
                payment_status="3";
                payment_method="3";

            }
        });
        rbPayPal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rbCashOnDelivery.setChecked(false);
                rbCreditCard.setChecked(false);
                rbPayPal.setChecked(true);
                payment_status="2";
                payment_method="2";

            }
        });*/
    }

    private void initViews() {
        cash=findViewById(R.id.ll_cash);
        card=findViewById(R.id.ll_credit);
        paypal=findViewById(R.id.ll_paypal);
        txtPaymentTotal=findViewById(R.id.txt_paymentTotal);
        ll_paymentBackArrow=findViewById(R.id.ll_paymentBackArrow);
        txt_pay_btn=findViewById(R.id.txt_pay_btn);
        selectCard=findViewById(R.id.txt_select_your_card);
        ivCardArrow=findViewById(R.id.iv_arrow_card);
        txtAddNewCard=findViewById(R.id.txt_Add_new_Card);
        rbCashOnDelivery=findViewById(R.id.rb_cash_on_delivery);
        rbPayPal=findViewById(R.id.rb_paypal);
        rbCreditCard=findViewById(R.id.rb_credit_debit);
        if (getIntent().hasExtra("bean")){
            orderPlacedBean= (OrderPlacedBean) getIntent().getSerializableExtra("bean");
        }
        txtPaymentTotal.setText(orderPlacedBean.getData().get(0).getTotalAmount()+".00");
      /*  ll_cardLayout=findViewById(R.id.ll_cardLayout);*/
    }

    @Override
    public void onClick(View view) {
        if (view.getId()== txtAddNewCard.getId()){
            dialog = new Dialog(this, R.style.ThemeDialogCustom_NoBackground);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_add_new_card);
            dialog.show();

        }
        else if (cash.getId()==view.getId()){
            rbCashOnDelivery.setChecked(true);
            rbCreditCard.setChecked(false);
            rbPayPal.setChecked(false);
            payment_status="1";
            payment_method="1";

        }
        else if (card.getId()==view.getId()){
            rbCashOnDelivery.setChecked(false);
            rbCreditCard.setChecked(true);
            rbPayPal.setChecked(false);
            payment_status="3";
            payment_method="3";
        }
        else if (paypal.getId()==view.getId()){
            rbCashOnDelivery.setChecked(false);
            rbCreditCard.setChecked(false);
            rbPayPal.setChecked(true);
            payment_status="2";
            payment_method="2";

        }
        else if (ivCardArrow.getId()==view.getId()){
           /* if (imgCardRotate) {
                int angle=90;
                ivCardArrow.setRotation(angle);
                imgCardRotate = false;
                cardRecyclerView.setVisibility(View.VISIBLE);
                txtAddNewCard.setVisibility(View.VISIBLE);
                selectCard.setVisibility(View.VISIBLE);
            } else {
                int antiAngle=0;
                ivCardArrow.setRotation(antiAngle);
                imgCardRotate = true;
                cardRecyclerView.setVisibility(View.GONE);
                txtAddNewCard.setVisibility(View.GONE);
                selectCard.setVisibility(View.GONE);
            }*/


        }
        else  if (view.getId()==txt_pay_btn.getId()){
            if (checkPayment()) {

                    for (int i=0;i<orderPlacedBean.getData().size();i++){
                        orderPlacedBean.getData().get(i).setPaymentStatus(payment_status);
                        orderPlacedBean.getData().get(i).setPaymentMethod(payment_method);
                    }
                    JsonObject jsonObject;
                    JsonArray jsonArray=new JsonArray();
                    for (int i=0;i<orderPlacedBean.getData().size();i++){
                        jsonObject=new JsonObject();
                        jsonObject.addProperty("ProductId",orderPlacedBean.getData().get(i).getProductId());
                        jsonObject.addProperty("address_id",orderPlacedBean.getData().get(i).getAddressId());
                        jsonObject.addProperty("scheduled_date",orderPlacedBean.getData().get(i).getScheduledDate());
                        jsonObject.addProperty("scheduled_time",orderPlacedBean.getData().get(i).getScheduledTime());
                        jsonObject.addProperty("product_price",orderPlacedBean.getData().get(i).getProductPrice());
                        jsonObject.addProperty("quantity",orderPlacedBean.getData().get(i).getQuantity());
                        jsonObject.addProperty("amount",orderPlacedBean.getData().get(i).getAmount());
                        jsonObject.addProperty("total_amount",orderPlacedBean.getData().get(i).getTotalAmount());
                        jsonObject.addProperty("store_id",orderPlacedBean.getData().get(i).getStoreId());
                        jsonObject.addProperty("payment_status",orderPlacedBean.getData().get(i).getPaymentStatus());
                        jsonObject.addProperty("payment_method",orderPlacedBean.getData().get(i).getPaymentMethod());
                        jsonArray.add(jsonObject);
                    }
                    JsonObject postData=new JsonObject();
                    postData.add("data",jsonArray);
                    DataManager.orderPlaced(postData, new OrderPlacedResponseListener() {
                        @Override
                        public void onLoadCompleted(ResponseOrderPlaced responseOrderPlaced) {
                            String bookId=responseOrderPlaced.getData().get(0).getBookingId().toString();
                            String adsId=orderPlacedBean.getData().get(0).getAddressId().toString();
                            BookAddressId bookAddressId=new BookAddressId();
                            bookAddressId.setBookId(bookId);
                            bookAddressId.setAdsId(adsId);
                            startActivity(new Intent(PaymentMethodActivity.this,PaymentSuccessFullActivity.class)
                                    .putExtra("bean",bookAddressId)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK)
                            );


                        }

                        @Override
                        public void onLoadFailed(String error) {
                            Toast.makeText(PaymentMethodActivity.this,error, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }


            }


    private boolean checkPayment() {
        if (payment_method.equals("0")){
            Toast.makeText(this,R.string.choose_payment, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(payment_method.equals("3")){
            Toast.makeText(this," Available only cash on delivery ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(payment_method.equals("2")){
            Toast.makeText(this," Available only cash on delivery ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void hideRecyclerView() {
        cardRecyclerView.setVisibility(View.GONE);
    }

    public void finsh(View view) {
        finish();
    }
}
