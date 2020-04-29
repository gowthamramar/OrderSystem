package com.techware.clickkart.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.techware.clickkart.R;
import com.techware.clickkart.activity.DeliverAddressActivity;
import com.techware.clickkart.activity.EditAddressActivity;
import com.techware.clickkart.activity.EditProfileActivity;
import com.techware.clickkart.activity.ProfileActivity;
import com.techware.clickkart.model.DeliveredAddressBean;
import com.techware.clickkart.model.getaddress.GetAddressBean;
import com.techware.clickkart.widgets.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Praveen Prasad on 04 September, 2019.
 * Package com.techware.clickkart.adapter
 * Project ClickKart
 */
public class ProfileDeliverAddressRecyclerViewAdapter extends RecyclerView.Adapter<ProfileDeliverAddressRecyclerViewAdapter.ViewHolder> {
    Dialog dialog;
    ProfileActivity profileActivity;
    RadioButton lastBtnSelected;
    GetAddressBean getAddressBean;

        public ProfileDeliverAddressRecyclerViewAdapter(GetAddressBean getAddressBean, ProfileActivity profileActivity) {
        this.getAddressBean = getAddressBean;
        this.profileActivity = profileActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_profile_address, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        holder.userName.setText(getAddressBean.getUserData().getFullname());
        if (getAddressBean.getAddress().get(i).getType().equalsIgnoreCase("1")){
            holder.addressType.setText("Home");
        }
        else{
            holder.addressType.setText("Work");
        }
        holder.address.setText(getAddressBean.getAddress().get(i).getStreetAddress());
        holder.location.setText(getAddressBean.getAddress().get(i).getLandmark()+"-"+getAddressBean.getAddress().get(i).getZipCode());
        holder.phoneNo.setText(getAddressBean.getUserData().getPhoneNo());
        holder.txt_remove_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getAddressBean.getAddress().remove(i);
                profileActivity.removeClicked(getAddressBean,i,getAddressBean.getAddress().size());

            }
        });
        holder.llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileActivity.startActivity(new Intent(profileActivity, EditAddressActivity.class)
                .putExtra("streetAddress",getAddressBean.getAddress().get(i).getStreetAddress())
                        .putExtra("landMark",getAddressBean.getAddress().get(i).getLandmark())
                        .putExtra("zipCode",getAddressBean.getAddress().get(i).getZipCode())
                        .putExtra("type",getAddressBean.getAddress().get(i).getType())
                        .putExtra("instruction",getAddressBean.getAddress().get(i).getInstruction())
                        .putExtra("address_id",getAddressBean.getAddress().get(i).getAddressId()));
            }
        });
        holder.ll_profile_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.radioButtonAddress.setClickable(false);
                holder.radioButtonAddress.setChecked(true);
                if (lastBtnSelected !=null && !lastBtnSelected.equals(holder.radioButtonAddress)){
                    lastBtnSelected.setChecked(false);
                }
                lastBtnSelected=holder.radioButtonAddress;
            }
        });


        holder.ll_profile_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.radioButtonAddress.setClickable(false);
                holder.radioButtonAddress.setChecked(true);
                if (lastBtnSelected !=null && !lastBtnSelected.equals(holder.radioButtonAddress)){
                    lastBtnSelected.setChecked(false);
                }
                lastBtnSelected=holder.radioButtonAddress;
            }
        });

    }

    @Override
    public int getItemCount() {
        return getAddressBean.getAddress().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomTextView userName, addressType, address, location, phoneNo,txt_remove_address;
        LinearLayout llAddressDetails;
        RadioButton radioButtonAddress;
        private LinearLayout llEdit,ll_profile_address;

        public ViewHolder(View itemView) {
            super(itemView);
            llEdit=itemView.findViewById(R.id.ll_editAddress);
            ll_profile_address=itemView.findViewById(R.id.ll_address_profile);
            txt_remove_address=itemView.findViewById(R.id.txt_remove_address);
            userName = itemView.findViewById(R.id.txt_adress_userName);
            addressType = itemView.findViewById(R.id.txt_adress_addressType);
            address = itemView.findViewById(R.id.txt_userAddress);
            location = itemView.findViewById(R.id.txt_user_location);
            phoneNo = itemView.findViewById(R.id.txt_userMobNo);
            radioButtonAddress=itemView.findViewById(R.id.radio_btn_address);
        }
    }

}
