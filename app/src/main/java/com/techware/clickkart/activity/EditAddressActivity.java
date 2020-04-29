package com.techware.clickkart.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.AddAddressResponseListener;
import com.techware.clickkart.listeners.EditAddressResponseListener;
import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.editaddress.EditBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.widgets.CustomEditText;
import com.techware.clickkart.widgets.CustomTextView;

public class EditAddressActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    private LinearLayout llResedential,llBusiness,ll_NewAddressBackArrow;
    private ViewFlipper addressViewFlipper;
    private CustomTextView txtResdential,txtBusiness,txtContinueBtn,txtDeliverHere,txtOk;
    private CustomEditText resedentialStreet,resedentialLandmark,resedentialZipcode,resedentialInstruction;
    private CustomEditText bussinessStreet,bussinessLandmark,bussinessZipcode,businessInstruction;
    private Dialog dialog,dialog1;
    private ImageView addressDialogCancel;
    private String type,street,landmark,zipCode,instruction,address_id;
    private String flag;
    private String streetAddress,adLandMark,adZipcode,adType,adInstruction;
    private String typeFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_address);
        showToolbar(false,"");
        initViews();
        initClick();
    }

    private void initClick() {
        txtOk.setOnClickListener(this);
        llBusiness.setOnClickListener(this);
        llResedential.setOnClickListener(this);
        txtContinueBtn.setOnClickListener(this);
        ll_NewAddressBackArrow.setOnClickListener(this);
        addressDialogCancel.setOnClickListener(this);
        txtDeliverHere.setOnClickListener(this);
    }

    private void initViews() {
        if (getIntent().hasExtra("streetAddress")){
            streetAddress=getIntent().getStringExtra("streetAddress");
        }
        if (getIntent().hasExtra("landMark")){
            adLandMark=getIntent().getStringExtra("landMark");
        }
        if (getIntent().hasExtra("zipCode")){
            adZipcode=getIntent().getStringExtra("zipCode");
        }
        if (getIntent().hasExtra("zipCode")){
            adZipcode=getIntent().getStringExtra("zipCode");
        }
        if (getIntent().hasExtra("type")){
            adType=getIntent().getStringExtra("type");
        }
        if (getIntent().hasExtra("instruction")){
            adInstruction=getIntent().getStringExtra("instruction");
        }
        if (getIntent().hasExtra("address_id")){
            address_id=getIntent().getStringExtra("address_id");
        }

        dialog1 = new Dialog(this, R.style.ThemeDialogCustom_NoBackground);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.edit_address_dialog);
        dialog = new Dialog(this, R.style.ThemeDialogCustom_NoBackground);
        txtOk=dialog1.findViewById(R.id.edit_txt_ok);
        Window window = dialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_address_successfully);
        txtDeliverHere=dialog.findViewById(R.id.txt_deliver_here);
        addressDialogCancel=dialog.findViewById(R.id.address_dialog_cancel);
        ll_NewAddressBackArrow=findViewById(R.id.ll_NewAddressBackArrow);
        txtContinueBtn=findViewById(R.id.txt_new_address_continue_btn);
        txtBusiness=findViewById(R.id.txt_business_address);
        txtResdential=findViewById(R.id.txt_resedntial_address);
        addressViewFlipper=findViewById(R.id.addressViewFlipper);
        llResedential=findViewById(R.id.ll_resedential);
        llBusiness=findViewById(R.id.ll_business);
//        *****************************************************
        resedentialStreet=findViewById(R.id.txt_resedential_street);
        resedentialLandmark=findViewById(R.id.txt_resedential_landmark);
        resedentialZipcode=findViewById(R.id.txt_resedential_zipcode);
        bussinessStreet=findViewById(R.id.txt_bussiness_street_address);
        bussinessLandmark=findViewById(R.id.txt_bussiness_landmark);
        bussinessZipcode=findViewById(R.id.txt_bussiness_zipcode);
        resedentialInstruction=findViewById(R.id.txt_resedential_instruction);
        businessInstruction=findViewById(R.id.txt_bussiness_instruction);
//        *********************************************************************
        if (adType.equalsIgnoreCase("1")){
            typeFlag="1";
            resedentialStreet.setText(streetAddress);
            resedentialLandmark.setText(adLandMark);
            resedentialZipcode.setText(adZipcode);
            resedentialInstruction.setText(adInstruction);
            addressViewFlipper.setDisplayedChild(0);
            llResedential.setBackground(getResources().getDrawable(R.drawable.address_color_resdential_rounded));
            txtResdential.setTextColor(getResources().getColor(R.color.white));
            llBusiness.setBackground(getResources().getDrawable(R.drawable.address_business_color_rounded));
            txtBusiness.setTextColor(getResources().getColor(R.color.Resedential));
            type="1";

        }
        else {
            typeFlag="2";
            bussinessStreet.setText(streetAddress);
            bussinessLandmark.setText(adLandMark);
            bussinessZipcode.setText(adZipcode);
            businessInstruction.setText(adInstruction);
            addressViewFlipper.setDisplayedChild(1);
            llBusiness.setBackground(getResources().getDrawable(R.drawable.address_color_resdential_rounded));
            txtBusiness.setTextColor(getResources().getColor(R.color.white));
            llResedential.setBackground(getResources().getDrawable(R.drawable.address_business_color_rounded));
            txtResdential.setTextColor(getResources().getColor(R.color.Resedential));
            type="2";
        }

    }

    @Override
    public void onClick(View v) {
if (llBusiness.getId()==v.getId()){
    type="2";
    llBusiness.setBackground(getResources().getDrawable(R.drawable.address_color_resdential_rounded));
    txtBusiness.setTextColor(getResources().getColor(R.color.white));
    llResedential.setBackground(getResources().getDrawable(R.drawable.address_business_color_rounded));
    txtResdential.setTextColor(getResources().getColor(R.color.Resedential));
}
 else if(llResedential.getId()==v.getId()){
    type="1";
    llResedential.setBackground(getResources().getDrawable(R.drawable.address_color_resdential_rounded));
    txtResdential.setTextColor(getResources().getColor(R.color.white));
    llBusiness.setBackground(getResources().getDrawable(R.drawable.address_business_color_rounded));
    txtBusiness.setTextColor(getResources().getColor(R.color.Resedential));
        }
 else if (v.getId()==txtContinueBtn.getId()){
      if (typeFlag.equalsIgnoreCase("1")){
          if (collectResedentialData()){
              instruction=resedentialInstruction.getText().toString();
              if (App.isNetworkAvailable()){
                  performEditAddress();
              }
              else{
                  Toast.makeText(this,R.string.no, Toast.LENGTH_SHORT).show();
              }
          }
      }
      else{
          if (collectBusinessData()){
              instruction=businessInstruction.getText().toString();
              if (App.isNetworkAvailable()){
                  performEditAddress();
              }
              else{
                  Toast.makeText(this,R.string.no, Toast.LENGTH_SHORT).show();
              }
          }

      }

     //startActivity(new Intent(AddNewAddressActivity.this,DeliverAddressActivity.class));

 }
else if (v.getId()==ll_NewAddressBackArrow.getId()){
  finish();

}
else if (v.getId()==ll_NewAddressBackArrow.getId()){
    finish();

}
else if (v.getId()==addressDialogCancel.getId()){
    dialog.dismiss();
    startActivity(new Intent(EditAddressActivity.this,DeliverAddressActivity.class));

}
else if (v.getId()==txtDeliverHere.getId()){
    dialog.dismiss();
    startActivity(new Intent(EditAddressActivity.this,SheduledDateTimeActivity.class));

}
else if (v.getId()==txtOk.getId()) {
    dialog1.dismiss();
    finish();
}

    }

    private void performEditAddress() {
        JsonObject postData=getJsonObj();
        DataManager.performEditAddress(postData, new EditAddressResponseListener() {
            @Override
            public void onLoadCompleted(EditBean editBean) {
                if (typeFlag.equalsIgnoreCase("0")){
                    dialog1.show();
                    bussinessStreet.setText("");
                    bussinessLandmark.setText("");
                    bussinessZipcode.setText("");
                    resedentialStreet.setText("");
                    resedentialLandmark.setText("");
                    resedentialZipcode.setText("");
                    businessInstruction.setText("");
                    resedentialInstruction.setText("");
                }
                else{
                   dialog1.show();
                    bussinessStreet.setText("");
                    bussinessLandmark.setText("");
                    bussinessZipcode.setText("");
                    resedentialStreet.setText("");
                    resedentialLandmark.setText("");
                    resedentialZipcode.setText("");
                    businessInstruction.setText("");
                    resedentialInstruction.setText("");
                }


            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(EditAddressActivity.this,error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private JsonObject getJsonObj() {
        JsonObject postData=new JsonObject();
        postData.addProperty("street_address",street);
        postData.addProperty("landmark",landmark);
        postData.addProperty("zip_code",zipCode);
        postData.addProperty("type",type);
        postData.addProperty("instruction",instruction);
        postData.addProperty("address_id",address_id);
        return postData;

    }

    private boolean collectBusinessData() {
        street=bussinessStreet.getText().toString();
        landmark=bussinessLandmark.getText().toString();
        zipCode=bussinessZipcode.getText().toString();
        if (street.equals("")){
            Toast.makeText(this,R.string.street_address_required, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (landmark.equals("")){
            Toast.makeText(this,R.string.landmark_address_required, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (zipCode.equals("")){
            Toast.makeText(this,R.string.message_zipcode_is_required, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (zipCode.length()!=6){
            Toast.makeText(this,R.string.zipcode_digits, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private boolean collectResedentialData() {
        street=resedentialStreet.getText().toString();
        landmark=resedentialLandmark.getText().toString();
        zipCode=resedentialZipcode.getText().toString();
        if (street.equals("")){
        Toast.makeText(this,R.string.street_address_required, Toast.LENGTH_SHORT).show();
        return false;
    }
        else if (landmark.equals("")){
            Toast.makeText(this,R.string.landmark_address_required, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (zipCode.equals("")){
            Toast.makeText(this,R.string.message_zipcode_is_required, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void finish(View view) {
        finish();
    }
}
