package com.techware.clickkart.activity;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.widgets.CustomEditText;
import com.techware.clickkart.widgets.CustomTextView;

public class AddNewAddressActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    private LinearLayout llResedential,llBusiness,ll_NewAddressBackArrow;
    private ViewFlipper addressViewFlipper;
    private CustomTextView txtResdential,txtBusiness,txtContinueBtn,txtDeliverHere,txtOk;
    private CustomEditText resedentialStreet,resedentialLandmark,resedentialZipcode,resedentialInstruction;
    private CustomEditText bussinessStreet,bussinessLandmark,bussinessZipcode,businessInstruction;
    private Dialog dialog,dialog1;
    private ImageView addressDialogCancel,iv_resedential,iv_busines;
    private String type,street,landmark,zipCode,instruction;
    private String flag;
    private   String adId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);
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
        if (getIntent().hasExtra("flag")){
            flag=getIntent().getStringExtra("flag");
        }
        iv_resedential=findViewById(R.id.iv_resedential);
        iv_busines=findViewById(R.id.iv_business);
        type="1";
        dialog1 = new Dialog(this, R.style.ThemeDialogCustom_NoBackground);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.add_address_dialog);
        dialog = new Dialog(this, R.style.ThemeDialogCustom_NoBackground);
        txtOk=dialog1.findViewById(R.id.txt_ok);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_address_successfully);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        Window window1 = dialog1.getWindow();
        window1.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setGravity(Gravity.BOTTOM);
        txtDeliverHere=dialog.findViewById(R.id.txt_deliver_here);
        addressDialogCancel=dialog.findViewById(R.id.address_dialog_cancel);
        ll_NewAddressBackArrow=findViewById(R.id.ll_NewAddressBackArrow);
        txtContinueBtn=findViewById(R.id.txt_new_address_continue_btn);
        txtBusiness=findViewById(R.id.txt_business_address);
        txtResdential=findViewById(R.id.txt_resedntial_address);
        addressViewFlipper=findViewById(R.id.addressViewFlipper);
        llResedential=findViewById(R.id.ll_resedential);
        llBusiness=findViewById(R.id.ll_business);
        resedentialStreet=findViewById(R.id.txt_resedential_street);
        resedentialLandmark=findViewById(R.id.txt_resedential_landmark);
        resedentialZipcode=findViewById(R.id.txt_resedential_zipcode);
        bussinessStreet=findViewById(R.id.txt_bussiness_street_address);
        bussinessLandmark=findViewById(R.id.txt_bussiness_landmark);
        bussinessZipcode=findViewById(R.id.txt_bussiness_zipcode);
        resedentialInstruction=findViewById(R.id.txt_resedential_instruction);
        businessInstruction=findViewById(R.id.txt_bussiness_instruction);

    }

    @Override
    public void onClick(View v) {
if (llBusiness.getId()==v.getId()){
    addressViewFlipper.setDisplayedChild(1);
    type="2";
    iv_resedential.setImageResource(R.drawable.resdential_un_colored);
    iv_busines.setImageResource(R.drawable.white_colored_business);
    llBusiness.setBackground(getResources().getDrawable(R.drawable.address_color_resdential_rounded));
    txtBusiness.setTextColor(getResources().getColor(R.color.white));
    llResedential.setBackground(getResources().getDrawable(R.drawable.address_business_color_rounded));
    txtResdential.setTextColor(getResources().getColor(R.color.Resedential));
}
 else if(llResedential.getId()==v.getId()){
    addressViewFlipper.setDisplayedChild(0);
    type="1";
    iv_resedential.setImageResource(R.drawable.residential_icon);
    iv_busines.setImageResource(R.drawable.bussiness_icon);
    llResedential.setBackground(getResources().getDrawable(R.drawable.address_color_resdential_rounded));
    txtResdential.setTextColor(getResources().getColor(R.color.white));
    llBusiness.setBackground(getResources().getDrawable(R.drawable.address_business_color_rounded));
    txtBusiness.setTextColor(getResources().getColor(R.color.Resedential));
        }
 else if (v.getId()==txtContinueBtn.getId()){
      if (type.equalsIgnoreCase("1")){
          if (collectResedentialData()){
              instruction=resedentialInstruction.getText().toString();
              if (App.isNetworkAvailable()){
                  performAddAddress();
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
                  performAddAddress();
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
    startActivity(new Intent(AddNewAddressActivity.this,DeliverAddressActivity.class));

}
else if (v.getId()==txtDeliverHere.getId()){
    dialog.dismiss();
    startActivity(new Intent(AddNewAddressActivity.this,SheduledDateTimeActivity.class));

}
else if (v.getId()==txtOk.getId()) {
    dialog1.dismiss();
    finish();
}

    }

    private void performAddAddress() {
        JsonObject postData=getJsonObj();
        DataManager.performAddAddress(postData, new AddAddressResponseListener() {
            @Override
            public void onLoadCompleted(AddAddressBean addAddressBean) {
                 adId=addAddressBean.getData().getAddressId();
                if (flag.equalsIgnoreCase("0")){
                   /* dialog.show();*/
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
                Toast.makeText(AddNewAddressActivity.this,error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private JsonObject getJsonObj() {
        JsonObject postData=new JsonObject();
        postData.addProperty("street",street);
        postData.addProperty("landmark",landmark);
        postData.addProperty("zipcode",zipCode);
        postData.addProperty("type",type);
        postData.addProperty("instruction",instruction);
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
        else if (zipCode.length()!=6){
            Toast.makeText(this,R.string.zipcode_digits, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void finsh(View view) {
        finish();
    }


}
