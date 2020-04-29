package com.techware.clickkart.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.app.App;
import com.techware.clickkart.dialogs.SelectPhotoDialog;
import com.techware.clickkart.listeners.ChangepasswordResponseListener;
import com.techware.clickkart.listeners.EditProfileResponseListener;
import com.techware.clickkart.listeners.UploadProfileResponseListener;
import com.techware.clickkart.model.changepassword.ChangePasswordBean;
import com.techware.clickkart.model.editprofile.EditProfileBean;
import com.techware.clickkart.model.uploadimage.ProfileImagBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.util.AppConstants;
import com.techware.clickkart.util.ImageFilePath;
import com.techware.clickkart.widgets.CustomEditText;
import com.techware.clickkart.widgets.CustomTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

public class EditProfileActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    Dialog dialog;
    private CustomTextView txtChangePassword, btnChangePassword, save;
    private CustomEditText txtCurrentPassword, txtNewPassword, txtCnfPassword, txtName, txtPhoneNumber, txtEmail;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private String name, email;
    private String profileName;
    private ImageView currentEye, newEye, confrmEye, ivEditProfile;
    private boolean currentShowClicked, newShowClicked, confmShowClicked;
    private int currentValue = 0;
    private int newValue = 0;
    private int confmValue = 0;
    private LinearLayout llBack;
    private String phone;
    private String proxfileEmail;
    private String profilePhoneNumber;
    private SelectPhotoDialog selectPhotoDialog;
    private File photoFile;
    private String imageFilePath;
    private Uri photoURI;
    private boolean isVideoPath;
    private Bitmap photo;
    private String image, pic;

    private String currentPhotoPath;
    boolean isCameraPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
       /* TextView tv = (TextView)findViewById(R.id.textview1);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font-name.ttf");
        tx.setTypeface(custom_font);*/
        if (getIntent().hasExtra("name")) {
            name = getIntent().getStringExtra("name");
        }
        if (getIntent().hasExtra("image")) {
            pic = getIntent().getStringExtra("image");
        }
        if (getIntent().hasExtra("email")) {
            email = getIntent().getStringExtra("email");

        }
        if (getIntent().hasExtra("phone")) {
            phone = getIntent().getStringExtra("phone");

        }
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        image = preferences.getString(AppConstants.PREFERENCE_KEY_SESSION_PROFILE_PHOTO, "");
        initViews();
        initClick();
        showToolbar(false, "");


    }

    private void initClick() {
        llBack.setOnClickListener(this);
        confrmEye.setOnClickListener(this);
        newEye.setOnClickListener(this);
        currentEye.setOnClickListener(this);
        txtChangePassword.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        save.setOnClickListener(this);
        ivEditProfile.setOnClickListener(this);
    }

    private void initViews() {
        save = findViewById(R.id.txtSaveBtn);
        ivEditProfile = findViewById(R.id.ivEditProfile);
        Glide.with(getApplicationContext()).load(RetrofitClient.IMAGE_PATH + pic)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(ivEditProfile);
        llBack = findViewById(R.id.editProfileBack);
        dialog = new Dialog(this, R.style.ThemeDialogCustom_NoBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_password);
        currentEye = dialog.findViewById(R.id.iv_currentEye);
        newEye = dialog.findViewById(R.id.iv_newPasswordEye);
        confrmEye = dialog.findViewById(R.id.iv_cofirmPasswordEye);
        txtCurrentPassword = dialog.findViewById(R.id.txt_dialog_curntPassword);
        txtNewPassword = dialog.findViewById(R.id.txt_dialog_newpassword);
        txtCnfPassword = dialog.findViewById(R.id.txt_dialog_cnfPassword);
        txtChangePassword = findViewById(R.id.txt_change_password);
        btnChangePassword = dialog.findViewById(R.id.txt_dialog_change_password);
        txtName = findViewById(R.id.text_editName);
        txtEmail = findViewById(R.id.text_editEmail);
        txtPhoneNumber = findViewById(R.id.text_editPhoneNumber);
        txtName.setText(name);
        System.out.println("email is " + name);
        System.out.println("email is " + email);
        txtEmail.setText(email);
        txtPhoneNumber.setText(phone);
        selectPhotoDialog = new SelectPhotoDialog(this);

        selectPhotoDialog.setSelectPhotoDialogActionListener(new SelectPhotoDialog.SelectPhotoDialogActionListener() {
            @Override
            public void onSelectGalleryClick() {
                onAddProfilePhotoFromGallery();
            }

            @Override
            public void onSelectCameraClick() {
                onAddProfilePhotoFromCamera();
            }

            @Override
            public void onSnackBarShow(String message) {

            }

            @Override
            public void onSwipeRefreshChange(boolean isSwipeRefreshing) {

            }
        });

    }

    public void onAddProfilePhotoFromGallery() {
        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();

        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, AppConstants.REQUEST_PICK_IMAGE);
        }
    }

    public void onAddProfilePhotoFromCamera() {
        if (!checkCameraPermission()) {
            getCameraPermissions();
        } else {
            dispatchTakePictureIntent();
        }
    }

    private boolean checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    protected void getCameraPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(this, permissions, AppConstants.REQUEST_ACCESS_CAMERA);
                isCameraPermissionGranted = true;
            } else {
                isCameraPermissionGranted = false;
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        takePictureIntent.resolveActivity(getPackageManager());
        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(
                    this,
                    "com.techware.clickkart.provider",
                    photoFile
            );
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, AppConstants.REQUEST_ACCESS_CAMERA);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = file.getAbsolutePath();
        photoURI = Uri.fromFile(file);
        return file;
    }

    private void openGalleryForVideo() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("video/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//        pickIntent.setType("video/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Video");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, AppConstants.REQUEST_PICK_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.REQUEST_ACCESS_CAMERA) {
                if (!currentPhotoPath.isEmpty()) {
//                    imageFilePath = compressFile(imageFilePath, true).getAbsolutePath();
                    showImageFromCamera();
                } else {
                    currentPhotoPath = "";
                    Toast.makeText(getApplicationContext(), "Error capturing image", Toast.LENGTH_SHORT).show();
                }
//            }
//            if (requestCode == AppConstants.REQUEST_ACCESS_CAMERA) {
//                if (imageFilePath.length() > 0) {
////                        imageFilePath = compressFile(imageFilePath, true).getAbsolutePath();
//                    saveMediaPathToUploadList();
//                }
            } else if (requestCode == AppConstants.REQUEST_PICK_IMAGE) {
                imageFilePath = ImageFilePath.getPath(getApplicationContext(), data.getData());
                photoURI = data.getData();
                isVideoPath = false;
                saveMediaPathToUploadList();
            } else if (requestCode == AppConstants.REQUEST_PICK_VIDEO) {
                photoURI = data.getData();
                imageFilePath = ImageFilePath.getPath(EditProfileActivity.this, photoURI);
                isVideoPath = true;
                saveMediaPathToUploadList();
            }
        }
    }

    public File compressFile(String filePath, boolean flagCamera) {
        File file = new File(filePath);
        File compressedFile = null;

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            if (flagCamera) {
                flagCamera = false;
                compressedFile = new File(Environment.getExternalStorageDirectory().toString() + "/compressed" + file.getName());
                FileOutputStream out = new FileOutputStream(compressedFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
                out.flush();
                out.close();
            } else {
                return file;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return compressedFile;

    }

    private void showImageFromCamera() {
        Glide.with(EditProfileActivity.this)
                .load(photoURI)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(ivEditProfile);
        DataManager.uploadProfilePic(photoURI.getPath(), new UploadProfileResponseListener() {
            @Override
            public void onLoadCompleted(ProfileImagBean profileImagBean) {
                App.saveToken(profileImagBean);
                Toast.makeText(EditProfileActivity.this, "Image selected successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(EditProfileActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveMediaPathToUploadList() {
        Glide.with(EditProfileActivity.this)
                .load(imageFilePath)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(ivEditProfile);
//        File file = savebitmap(photo);
        DataManager.uploadProfilePic(imageFilePath, new UploadProfileResponseListener() {
            @Override
            public void onLoadCompleted(ProfileImagBean profileImagBean) {
                App.saveToken(profileImagBean);
                Toast.makeText(EditProfileActivity.this, "Image selected successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(EditProfileActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File savebitmap(Bitmap photo) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, "temp.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp.png");

        }

        try {
            outStream = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_CAMERA:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    getCameraPermissions();
                } else {
                    openCamera();
                }
                break;
        }
    }

    private File createImageFile(int op) throws IOException {
        File image = null;

        if (op == 0) {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());
            String imageFileName = getString(R.string.app_name) + timeStamp + "_";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File storageDir = new File(
                        Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/Photo/");
                if (!storageDir.exists()) {
                    storageDir.mkdirs();
                }
                image = new File(storageDir + imageFileName + ".jpg");
            } else {
                image = new File(getFilesDir() + "/" + imageFileName + ".jpg");
            }

            image.createNewFile();
            // Save a file: path for use with ACTION_VIEW intents
            imageFilePath = image.getAbsolutePath();
        }
        return image;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, AppConstants.REQUEST_ACCESS_CAMERA);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == txtChangePassword.getId()) {
            dialog.show();
        } else if (currentEye.getId() == view.getId()) {
            if (!txtCurrentPassword.getText().toString().equalsIgnoreCase("")) {
                if (currentShowClicked) {
                    txtCurrentPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txtCurrentPassword.setSelection(txtCurrentPassword.getText().length());
                    currentShowClicked = false;
                    currentEye.setImageResource(R.drawable.colored_eye);
                } else {
                    if (currentValue == 0) {
                        txtCurrentPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        currentValue++;
                        txtCurrentPassword.setSelection(txtCurrentPassword.getText().length());
                        currentEye.setImageResource(R.drawable.colored_eye);
                    } else {
                        txtCurrentPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        currentShowClicked = true;
                        txtCurrentPassword.setSelection(txtCurrentPassword.getText().length());
                        currentEye.setImageResource(R.drawable.show_password_icon);

                    }

                }

            }

        } else if (ivEditProfile.getId() == view.getId()) {
            selectPhotoDialog.show();
        } else if (newEye.getId() == view.getId()) {
            if (!txtNewPassword.getText().toString().equalsIgnoreCase("")) {
                if (newShowClicked) {
                    txtNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txtNewPassword.setSelection(txtNewPassword.getText().length());
                    newShowClicked = false;
                    newEye.setImageResource(R.drawable.colored_eye);
                } else {
                    if (newValue == 0) {
                        txtNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        newValue++;
                        txtNewPassword.setSelection(txtNewPassword.getText().length());
                        newEye.setImageResource(R.drawable.colored_eye);
                    } else {
                        txtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        newShowClicked = true;
                        txtNewPassword.setSelection(txtNewPassword.getText().length());
                        newEye.setImageResource(R.drawable.show_password_icon);

                    }

                }

            }

        } else if (confrmEye.getId() == view.getId()) {
            if (!txtCnfPassword.getText().toString().equalsIgnoreCase("")) {
                if (confmShowClicked) {
                    txtCnfPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txtCnfPassword.setSelection(txtCnfPassword.getText().length());
                    confmShowClicked = false;
                    confrmEye.setImageResource(R.drawable.colored_eye);
                } else {
                    if (confmValue == 0) {
                        txtCnfPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        confmValue++;
                        txtCnfPassword.setSelection(txtCnfPassword.getText().length());
                        confrmEye.setImageResource(R.drawable.colored_eye);
                    } else {
                        txtCnfPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        confmShowClicked = true;
                        txtCnfPassword.setSelection(txtCnfPassword.getText().length());
                        confrmEye.setImageResource(R.drawable.show_password_icon);

                    }

                }

            }

        } else if (llBack.getId() == view.getId()) {
            finish();
        } else if (view.getId() == save.getId()) {
            if (App.isNetworkAvailable()) {
                if (collectEditData()) {
                    JsonObject postData = getEditPostData();
                    DataManager.performEditProfile(postData, new EditProfileResponseListener() {
                        @Override
                        public void onLoadCompleted(EditProfileBean editProfileBean) {
                            Toast.makeText(EditProfileActivity.this, R.string.edit_success, Toast.LENGTH_SHORT).show();
                            App.saveToken(editProfileBean);
                            startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }

                        @Override
                        public void onLoadFailed(String error) {
                            Toast.makeText(EditProfileActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        } else if (view.getId() == btnChangePassword.getId()) {
            if (App.isNetworkAvailable()) {
                if (collectData()) {
                    JsonObject postData = getPostData();
                    DataManager.changePassword(postData, new ChangepasswordResponseListener() {
                        @Override
                        public void onLoadCompleted(ChangePasswordBean changePasswordBean) {
                            Toast.makeText(EditProfileActivity.this, R.string.change_password, Toast.LENGTH_SHORT).show();
                            txtCurrentPassword.setText("");
                            txtNewPassword.setText("");
                            txtCnfPassword.setText("");
                            dialog.dismiss();
                        }

                        @Override
                        public void onLoadFailed(String error) {
                            Toast.makeText(EditProfileActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            } else {
                Toast.makeText(this, R.string.no, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private JsonObject getEditPostData() {
        JsonObject postData = new JsonObject();
        postData.addProperty("fullname", profileName);
        postData.addProperty("email", proxfileEmail);
        postData.addProperty("phone_no", profilePhoneNumber);
        return postData;
    }

    private boolean collectEditData() {
        profileName = txtName.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        proxfileEmail = txtEmail.getText().toString();
        profilePhoneNumber = txtPhoneNumber.getText().toString();
        if (profileName.isEmpty()) {
            Toast.makeText(this, R.string.message_name_is_required, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (profilePhoneNumber.equalsIgnoreCase("")) {

            Toast.makeText(this, "Phone Number is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((profilePhoneNumber.length() < 10) || (profilePhoneNumber.length() > 12)) {
            Toast.makeText(this, "Phone Number is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (proxfileEmail.equalsIgnoreCase("")) {
            Toast.makeText(this, "Email is Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!proxfileEmail.matches(emailPattern) && !proxfileEmail.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
       /* else if (proxfileEmail.isEmpty()) {
            Toast.makeText(this, R.string.message_name_is_required, Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;
    }

    private JsonObject getPostData() {
        JsonObject postData = new JsonObject();
        postData.addProperty("password", currentPassword);
        postData.addProperty("npassword", newPassword);
        postData.addProperty("cpassword", confirmPassword);
        return postData;
    }


    private boolean collectData() {
        currentPassword = txtCurrentPassword.getText().toString();
        newPassword = txtNewPassword.getText().toString();
        confirmPassword = txtCnfPassword.getText().toString();
        if (currentPassword.equalsIgnoreCase("")) {
            Toast.makeText(this, R.string.enter_current_pass, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!currentPassword.equalsIgnoreCase("") && !(currentPassword.length() >= 8)) {
            Toast.makeText(this, "Password must contains minimum 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!currentPassword.equalsIgnoreCase("") && !(currentPassword.length() <= 25)) {
            Toast.makeText(this, "Password must contains maximum 25 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (newPassword.equalsIgnoreCase("")) {
            Toast.makeText(this, R.string.enter_new_password, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!newPassword.equalsIgnoreCase("") && !(newPassword.length() >= 8)) {
            Toast.makeText(this, "Password must contains minimum 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!newPassword.equalsIgnoreCase("") && !(newPassword.length() <= 25)) {
            Toast.makeText(this, "Password must contains maximum 25 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (confirmPassword.equalsIgnoreCase("")) {
            Toast.makeText(this, R.string.confm_passwrd_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!confirmPassword.equalsIgnoreCase(newPassword)) {
            Toast.makeText(this, R.string.password_not_match, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void finsh(View view) {
        finish();
    }
}
