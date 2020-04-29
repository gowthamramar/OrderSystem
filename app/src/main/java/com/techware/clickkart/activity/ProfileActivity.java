package com.techware.clickkart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.DeliverAddressRecyclerViewAdapter;
import com.techware.clickkart.adapter.ProfileDeliverAddressRecyclerViewAdapter;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.GetAddressListResponseListener;
import com.techware.clickkart.listeners.RemoveAddressResponseListener;
import com.techware.clickkart.model.DeliveredAddressBean;
import com.techware.clickkart.model.getaddress.GetAddressBean;
import com.techware.clickkart.model.removeaddress.RemoveAddressBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.widgets.CustomTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class ProfileActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener{
LinearLayout ll_profile_edit,llBackProfile;
    CustomTextView txtAddMoney,addAddress,txtName,txtEmail;
    RecyclerView deliverRecyclerview;
    RecyclerView.LayoutManager deliverLayoutManager;
    private ArrayList<DeliveredAddressBean> deliverRecyclerItems;
    ProfileDeliverAddressRecyclerViewAdapter deliverAddressRecyclerViewAdapter;
    ImageView llBackArrowDeliverAddress;
    Dialog dialog;
    private String phone,image;
    private Dialog dialog1;
    private CustomTextView txtOk;
    private int pos;
    private int size;
    private GetAddressBean getAddressBean;
    private com.mikhaellopez.circularimageview.CircularImageView iv_circle_profile;
    private int camera = 1,gallery = 2;
    com.facebook.shimmer.ShimmerFrameLayout shimmerFrameLayout;
    public static final int PERMISSION_CODE = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        initClick();
        showToolbar(false,"");
        deliverRecyclerview=findViewById(R.id.profile_recyclerview_address);
        deliverLayoutManager =  new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.VERTICAL, false);
        deliverRecyclerview.setLayoutManager(deliverLayoutManager);
        deliverRecyclerview.setItemAnimator(new DefaultItemAnimator());
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
                txtName.setText(getAddressBean.getUserData().getFullname());
                txtEmail.setText(getAddressBean.getUserData().getEmail());
                image=getAddressBean.getUserData().getImage();
                if (image!=null){
                    Glide.with(getApplicationContext()).load(RetrofitClient.IMAGE_PATH+getAddressBean.getUserData().getImage())
                            .into(iv_circle_profile);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.hideShimmer();
                }
                else{
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.hideShimmer();
                }

                phone=getAddressBean.getUserData().getPhoneNo();
                deliverAddressRecyclerViewAdapter = new ProfileDeliverAddressRecyclerViewAdapter(getAddressBean, ProfileActivity.this);
                deliverAddressRecyclerViewAdapter.notifyDataSetChanged();
                deliverRecyclerview.setAdapter(deliverAddressRecyclerViewAdapter);
                populateAddressList(getAddressBean);

            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(ProfileActivity.this,error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateAddressList(GetAddressBean getAddressBean) {
        deliverAddressRecyclerViewAdapter = new ProfileDeliverAddressRecyclerViewAdapter(getAddressBean, ProfileActivity.this);
        deliverRecyclerview.setAdapter(deliverAddressRecyclerViewAdapter);

    }

    private void initClick() {
        iv_circle_profile.setOnClickListener(this);
        txtOk.setOnClickListener(this);
        ll_profile_edit.setOnClickListener(this);
        llBackProfile.setOnClickListener(this);
       // txtAddMoney.setOnClickListener(this);
        addAddress.setOnClickListener(this);
    }

    private void initViews() {
        iv_circle_profile=(com.mikhaellopez.circularimageview.CircularImageView)findViewById(R.id.iv_circle_profile);
        shimmerFrameLayout=findViewById(R.id.profileShimmer);
        shimmerFrameLayout.startShimmer();
        dialog = new Dialog(ProfileActivity.this, R.style.ThemeDialogCustom_NoBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_money);
       // txtAddMoney=findViewById(R.id.txtAddmoney);
        ll_profile_edit=findViewById(R.id.ll_profile_edit);
        llBackProfile=findViewById(R.id.ll_back_profile);
        addAddress=findViewById(R.id.txt_profile_addaddress);
        txtName=findViewById(R.id.txt_profile_name);
        txtEmail=findViewById(R.id.txt_profile_email);
        dialog1 = new Dialog(this, R.style.ThemeDialogCustom_NoBackground);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.remove_address_dialog);
        txtOk=dialog1.findViewById(R.id.txt_ok);
        Window window = dialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onClick(View view) {
        if (ll_profile_edit.getId()== view.getId()){
            String name,email;
            name=txtName.getText().toString();
            email=txtEmail.getText().toString();
            startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class)
                    .putExtra("image",image)
            .putExtra("name",name)
            .putExtra("email",email)
            .putExtra("phone",phone));
        }
        else if (llBackProfile.getId()==view.getId()){
            finish();
        }
       /* else if (txtAddMoney.getId()==view.getId()){
           dialog.show();
        }*/
        else if (txtOk.getId()==view.getId()){
            dialog1.dismiss();
            getAddressBean.getAddress().remove(pos);
            deliverAddressRecyclerViewAdapter.notifyItemRemoved(pos);
            deliverAddressRecyclerViewAdapter.notifyItemRangeChanged(pos,size);
            deliverAddressRecyclerViewAdapter.notifyDataSetChanged();
        }
        else if (addAddress.getId()==view.getId()){
            startActivity(new Intent(ProfileActivity.this,AddNewAddressActivity.class)
                    .putExtra("flag","1"));
        }
    }

    public void removeClicked(GetAddressBean getAddressBeanWs, int posW, int sizeW) {
        pos = posW;
        size = sizeW;
        getAddressBean = getAddressBeanWs;
        if (App.isNetworkAvailable()){
            removeAddressDetails(getAddressBean.getAddress().get(pos).getAddressId());
        }
        else{
            Toast.makeText(this,R.string.no, Toast.LENGTH_SHORT).show();
        }

    }

    private void removeAddressDetails(String addressId) {
        JsonObject postData=new JsonObject();
        postData.addProperty("address_id",addressId);
        DataManager.deleteAddress(postData, new RemoveAddressResponseListener() {
            @Override
            public void onLoadCompleted(RemoveAddressBean removeAddressBean) {
                dialog1.show();

            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(ProfileActivity.this,error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
