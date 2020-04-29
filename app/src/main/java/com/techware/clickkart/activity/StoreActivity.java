package com.techware.clickkart.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.techware.clickkart.R;
import com.techware.clickkart.adapter.SearchProductListRecyclerViewAdapter;
import com.techware.clickkart.adapter.SearchProductStoreWiseListRecyclerViewAdapter;
import com.techware.clickkart.adapter.StoreDetailsCategoriesRecyclerViewAdapter;
import com.techware.clickkart.listeners.CartResponseListener;
import com.techware.clickkart.listeners.CategoryWisePrdoductsResponseListener;
import com.techware.clickkart.listeners.SearchedProductsResponseListener;
import com.techware.clickkart.listeners.StoreWiseSearchResponseListener;
import com.techware.clickkart.model.cart.CartBean;
import com.techware.clickkart.model.categorywiseproduct.CategoryWiseProductBean;
import com.techware.clickkart.model.searchproduct.SearchProductBean;
import com.techware.clickkart.model.storewisesearchedproduct.StorewiseSearchProductBean;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.app.App;
import com.techware.clickkart.listeners.ShopByCategoriesResponseListener;
import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.model.shopbystore.Data;
import com.techware.clickkart.model.shopbystore.StoreListBean;
import com.techware.clickkart.net.DataManager;
import com.techware.clickkart.widgets.CustomEditText;
import com.techware.clickkart.widgets.CustomTextView;

public class StoreActivity extends BaseAppCompatNoDrawerActivity implements View.OnClickListener {
    ImageView imgArrow,ivStoreImage;
    boolean ivArrow = true;
    int angle, antiAngle;
    CustomTextView txtStoreName,txtStoreCategory,txtStoreTiming;
    LinearLayout ll_StoreBackArrow,ll_dialogBackGroceries, ll_store_vegetable, ll_store_fruits, ll_sweets_fruits, ll_oil_masala_fruits,llStoreList;
    String storeName,storeCategory,startTime,endTime,storeImage;
    ///sweets recyclerview setup//////
    private CustomEditText txtStoreProduct;
    RecyclerView rcvw_categoryStoreDeatils;
    RecyclerView.LayoutManager categoryLayoutManager;
    StoreDetailsCategoriesRecyclerViewAdapter storeDetailsCategoriesRecyclerViewAdapter;
    private Data dataOne;
    private com.techware.clickkart.model.categoryliststore.CategoryStore dataTwo;
    private com.techware.clickkart.model.YourFavouriteBean.Data dataThree;
    private com.techware.clickkart.model.PeopleFavouriteStores.Data dataFour;
    private com.techware.clickkart.model.searchproduct.Data dataFive;
    private com.techware.clickkart.model.searchstore.Data dataSix;
    private com.techware.clickkart.model.searchcategory.Data dataSeven;
    private int valueFromAdapter;
    private Dialog groceryDialog;
    private CustomEditText dialogSearch;
    private RecyclerView searchedRecyclerView;
    private LinearLayoutManager searchedLayoutManager;
    private String searchText;
    private SearchProductBean searchProductBean=new SearchProductBean();
    private SearchProductStoreWiseListRecyclerViewAdapter searchProductStoreWiseListRecyclerViewAdapter;
    private String storeId;
    private StorewiseSearchProductBean storewiseSearchProductBean=new StorewiseSearchProductBean();
    private String catId;
    LinearLayout llSearchIcon;
    private ProductListListener productListListener;
    private Dialog dialog;
    private CustomTextView productQuantity,productName,productPrice;
    private int quantity;
    //////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        initViews();
        getIntentData();
        setData();
        showToolbar(false, "");
        initClick();
       // fetchCategories();
       // vegetablesAdapterSetup();
       // fruitsAdapterSetup();
        storeDetailsCategoryAdapter();
        categoryList();
    }

    private void categoryList() {
        if (App.isNetworkAvailable()){
            fetchCategoriesList();
        }
        else {
            Toast.makeText(StoreActivity.this,"No Network Avialable",Toast.LENGTH_SHORT).show();
        }
    }
    private void fetchCategoriesList() {
        JsonObject postData=new JsonObject();
        if (valueFromAdapter==0){
            postData.addProperty("store_id",dataOne.getStoreId());
        }
        else if (valueFromAdapter==1){
            postData.addProperty("store_id",dataTwo.getStoreId());
        }
        else if (valueFromAdapter==2){
            postData.addProperty("store_id",dataThree.getStoreId());
        }
        else if (valueFromAdapter==3){
            postData.addProperty("store_id",dataFour.getStoreId());
        }
        else if (valueFromAdapter==4){
            postData.addProperty("store_id",dataFive.getStore_id());
        }
        else if (valueFromAdapter==5){
            postData.addProperty("store_id",dataSix.getStoreId());
        }
        else if (valueFromAdapter==6){
            postData.addProperty("store_id",dataSeven.getStoreId());
        }

        DataManager.fetchCategories(postData, new ShopByCategoriesResponseListener() {
            @Override
            public void onLoadCompleted(CategoryListBean categoryListBean) {
                populateCategories(categoryListBean);
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(StoreActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateCategories(CategoryListBean categoryListBean) {
        storeDetailsCategoriesRecyclerViewAdapter = new StoreDetailsCategoriesRecyclerViewAdapter(categoryListBean, StoreActivity.this);
        rcvw_categoryStoreDeatils.setAdapter(storeDetailsCategoriesRecyclerViewAdapter);

    }

    private void setData() {
        if (valueFromAdapter==0){
            txtStoreName.setText(dataOne.getStoreName());
            txtStoreCategory.setText(Html.fromHtml((String)dataOne.getDescription()));
            txtStoreTiming.setText(dataOne.getStartTime()+"AM - "+dataOne.getEndTime()+" PM");
            Glide.with(StoreActivity.this
            ).load(RetrofitClient.IMAGE_PATH +dataOne.getStoreImage())
                    .apply(new RequestOptions()
                            .centerInside()
                    )
                    .into(ivStoreImage);
        }
       else if (valueFromAdapter==1){
            txtStoreName.setText(dataTwo.getStoreName());
            txtStoreCategory.setText(Html.fromHtml((String)dataTwo.getDescription()));
            txtStoreTiming.setText(dataTwo.getStartTime()+"AM - "+dataTwo.getEndTime()+" PM");
            Glide.with(StoreActivity.this
            ).load(RetrofitClient.IMAGE_PATH +dataTwo.getStoreImage())
                    .apply(new RequestOptions()
                            .centerInside()
                    )
                    .into(ivStoreImage);
        }
        else if (valueFromAdapter==2){
            txtStoreName.setText(dataThree.getStoreName());
            txtStoreCategory.setText(Html.fromHtml((String)dataThree.getDescription()));
            txtStoreTiming.setText(dataThree.getmStartTime()+"AM - "+dataThree.getmEndTime()+" PM");
            Glide.with(StoreActivity.this
            ).load(RetrofitClient.IMAGE_PATH +dataThree.getStoreImage())

                    .apply(new RequestOptions()
                            .centerInside()
                    )
                    .into(ivStoreImage);
        }
        else if (valueFromAdapter==3){
            txtStoreName.setText(dataFour.getStoreName());
            txtStoreCategory.setText(Html.fromHtml((String)dataFour.getDescription()));
             txtStoreTiming.setText(dataFour.getmStartTime()+"AM - "+dataFour.getmEndTime()+" PM");
            Glide.with(StoreActivity.this
            ).load(RetrofitClient.IMAGE_PATH +dataFour.getStoreImage())

                    .apply(new RequestOptions()
                            .centerInside()
                    )
                    .into(ivStoreImage);
        }
        else if (valueFromAdapter==4){
            txtStoreName.setText(dataFive.getStoreName());
            txtStoreCategory.setText(Html.fromHtml((String)dataFive.getDescription()));
             txtStoreTiming.setText(dataFive.getStart_time()+"AM - "+dataFive.getEnd_time()+" PM");
            dialog = new Dialog(this, R.style.ThemeDialogCustom_NoBackground);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.product_dialog);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.BOTTOM);
            dialog.show();
            productQuantity=dialog.findViewById(R.id.txt_dialog_quantity);
            productName= dialog.findViewById(R.id.dialog_productName);
            productPrice=dialog.findViewById(R.id.txt_dialog__price);
            productName.setText(dataFive.getProductName());
            quantity=Integer.valueOf(productQuantity.getText().toString());
            productPrice.setText("Rs:"+dataFive.getProductPrice()+"/kg");
            Glide.with(this)
                    .load(RetrofitClient.IMAGE_PATH+dataFive.getProduct_image())
                    .apply(RequestOptions.circleCropTransform())
                    .into((ImageView) dialog.findViewById(R.id.img_view_dialog_product));
            dialog.show();
            setQuantity();
            closeDialog();
            addToCart(dataFive);

        }  else if (valueFromAdapter==5){
            txtStoreName.setText(dataSix.getStoreName());
            txtStoreCategory.setText(Html.fromHtml((String)dataSix.getDescription()));
             txtStoreTiming.setText(dataSix.getStartTime()+"AM - "+dataSix.getEndTime()+" PM");
            Glide.with(StoreActivity.this
            ).load(RetrofitClient.IMAGE_PATH +dataSix.getStoreImage())

                    .apply(new RequestOptions()
                            .centerInside()
                    )
                    .into(ivStoreImage);
        }
        else if (valueFromAdapter==6){
            txtStoreName.setText(dataSeven.getStoreName());
            txtStoreCategory.setText(Html.fromHtml((String)dataSeven.getDescription()));
            txtStoreTiming.setText(dataSeven.getStartTime()+"AM - "+dataSeven.getEndTime()+" PM");
            Glide.with(StoreActivity.this
            ).load(RetrofitClient.IMAGE_PATH +dataSeven.getStoreImage())

                    .apply(new RequestOptions()
                            .centerInside()
                    )
                    .into(ivStoreImage);
        }


    }

    private void addToCart(com.techware.clickkart.model.searchproduct.Data dataFive) {
        dialog.findViewById(R.id.addToCart_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity>0){
                    JsonObject postData = getJsonObject(dataFive);
                    DataManager.addToCart(postData, new CartResponseListener() {
                        @Override
                        public void onLoadCompleted(CartBean cartBean) {
                            Toast.makeText(StoreActivity.this, "Product SuccessFully Added to Cart", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onLoadFailed(String error) {
                            Toast.makeText(StoreActivity.this,error,Toast.LENGTH_SHORT).show();
                        }
                    });}
                else{
                    Toast.makeText(StoreActivity.this, "Please select quantity greater than 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private JsonObject getJsonObject(com.techware.clickkart.model.searchproduct.Data dataFive) {
        JsonObject postData=new JsonObject();
        postData.addProperty("product_id",dataFive.getProduct_id());
        postData.addProperty("quantity",quantity);
        postData.addProperty("store_id",dataFive.getStore_id());
        return  postData;

    }

    private void closeDialog() {
        dialog.findViewById(R.id.dialog_product_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setQuantity() {
        dialog.findViewById(R.id.dialog_quantitiy_increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity<10){
                    quantity++;
                    productQuantity.setText(String.valueOf(quantity));
                }
                else {
                    Toast.makeText(StoreActivity.this,"You can select maximum quantity 10",Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.findViewById(R.id.dialog_quantity_decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>1){
                    quantity--;
                    productQuantity.setText(String.valueOf(quantity));
                }
                else {
                    Toast.makeText(StoreActivity.this,"Please select quantity greater than 1",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getIntentData() {

        getIntent().hasExtra("valueFromAdapter");
        valueFromAdapter = getIntent().getIntExtra("valueFromAdapter",0);

        if ((getIntent().hasExtra("list"))&&(valueFromAdapter==0)){
         dataOne = (Data)getIntent().getSerializableExtra("list");
         storeId=dataOne.getStoreId();
    }
       else if ((getIntent().hasExtra("list"))&&(valueFromAdapter==1)){
          dataTwo = (com.techware.clickkart.model.categoryliststore.CategoryStore) getIntent().getSerializableExtra("list");
            storeId=dataTwo.getStoreId();
        }
        else if ((getIntent().hasExtra("list"))&&(valueFromAdapter==2)){
            dataThree = (com.techware.clickkart.model.YourFavouriteBean.Data) getIntent().getSerializableExtra("list");
            storeId=dataThree.getStoreId();
        }
        else if ((getIntent().hasExtra("list"))&&(valueFromAdapter==3)){
            dataFour = (com.techware.clickkart.model.PeopleFavouriteStores.Data) getIntent().getSerializableExtra("list");
            storeId=dataFour.getStoreId();
        }
        else if ((getIntent().hasExtra("list"))&&(valueFromAdapter==4)){
            dataFive = (com.techware.clickkart.model.searchproduct.Data) getIntent().getSerializableExtra("list");
            storeId=dataFive.getStore_id();
        }
        else if ((getIntent().hasExtra("list"))&&(valueFromAdapter==5)){
            dataSix = (com.techware.clickkart.model.searchstore.Data) getIntent().getSerializableExtra("list");
            storeId=dataSix.getStoreId();
        }
        else if ((getIntent().hasExtra("list"))&&(valueFromAdapter==6)){
            dataSeven = (com.techware.clickkart.model.searchcategory.Data) getIntent().getSerializableExtra("list");
            storeId=dataSeven.getStoreId();
        }


    }

    private void storeDetailsCategoryAdapter() {
        rcvw_categoryStoreDeatils=findViewById(R.id.rcvw_categoryStoreDeatils);
        categoryLayoutManager = new LinearLayoutManager(StoreActivity.this, LinearLayoutManager.VERTICAL, false);;
        rcvw_categoryStoreDeatils.setLayoutManager(categoryLayoutManager);
        rcvw_categoryStoreDeatils.setItemAnimator(new DefaultItemAnimator());

    }



    private void initClick() {
        ll_dialogBackGroceries.setOnClickListener(this);
        ll_StoreBackArrow.setOnClickListener(this);
        txtStoreProduct.setOnClickListener(this);
        dialogSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = dialogSearch.getText().toString();
                if (searchText.length() >= 1) {
//                    searchedRecyclerView.setVisibility(View.VISIBLE);
                    searchGrocery(false, 1);
                }
                else {
                    searchedRecyclerView.setVisibility(View.GONE);
                }
            }
        });
    }
    private void searchGrocery(final boolean isLoadMore, int pageNo) {
        JsonObject postData = new JsonObject();
        postData.addProperty("store_id",storeId);
        postData.addProperty("product_name", searchText);
        postData.addProperty("page", pageNo);
        DataManager.performStoreProductSearch(postData, new StoreWiseSearchResponseListener() {
            @Override
            public void onLoadCompleted(StorewiseSearchProductBean storewiseSearchProductBean) {
                searchedRecyclerView.setVisibility(View.VISIBLE);
                llSearchIcon.setVisibility(View.GONE);
                updateStoreList(isLoadMore, storewiseSearchProductBean);
                System.out.println("storelist are"+new Gson().toJson(storewiseSearchProductBean));
                populateSearchedProductsList(storewiseSearchProductBean);
            }

            @Override
            public void onLoadFailed(String error) {
                llSearchIcon.setVisibility(View.VISIBLE);
                searchedRecyclerView.setVisibility(View.GONE);

            }
        });
    }

    private void updateStoreList(boolean isLoadMore, StorewiseSearchProductBean storewiseSearchProductBean) {
        if (isLoadMore) {
            this.storewiseSearchProductBean.getData().addAll(storewiseSearchProductBean.getData());
            this.storewiseSearchProductBean.setMeta(storewiseSearchProductBean.getMeta());
        } else {
            this.storewiseSearchProductBean.getData().clear();
            this.storewiseSearchProductBean = storewiseSearchProductBean;
        }
    }

    private void populateSearchedProductsList(StorewiseSearchProductBean storewiseSearchProductBean) {
        if (searchProductStoreWiseListRecyclerViewAdapter == null) {
            this.storewiseSearchProductBean = storewiseSearchProductBean;
            searchProductStoreWiseListRecyclerViewAdapter = new SearchProductStoreWiseListRecyclerViewAdapter(StoreActivity.this, storewiseSearchProductBean);
            searchProductStoreWiseListRecyclerViewAdapter.setSearchListRecyclerAdapterListener(new SearchProductStoreWiseListRecyclerViewAdapter.SearchListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    setProgressScreenVisibility(true, true);
                    searchGrocery(isLoadMore, currentPageNumber + 1);
                }

                @Override
                public void onRefresh() {
                }

                @Override
                public void onSwipeRefreshingChange(boolean isSwipeRefreshing) {
                }

                @Override
                public void onSnackBarShow(String message) {
                }

            });
            searchedRecyclerView.setAdapter(searchProductStoreWiseListRecyclerViewAdapter);
        }
        else {
            searchProductStoreWiseListRecyclerViewAdapter.setLoadMore(false);
            searchProductStoreWiseListRecyclerViewAdapter.setFilterProductsListBean(storewiseSearchProductBean);
            searchProductStoreWiseListRecyclerViewAdapter.notifyDataSetChanged();
        }
// swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }
    private void initViews() {
        angle = 90;
        antiAngle = 0;
        ivStoreImage=findViewById(R.id.iv_storeDetails);
        txtStoreName=findViewById(R.id.txt_storeDetails_Name);
        txtStoreCategory=findViewById(R.id.txt_storeDetails_Category);
        txtStoreTiming=findViewById(R.id.txt_storeDetails_timing);
        txtStoreProduct=findViewById(R.id.txtStoreSerachProduct);
        groceryDialog = new Dialog(StoreActivity.this, R.style.ThemeDialogCustom_NoBackground);
        groceryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        groceryDialog.setContentView(R.layout.dialog_grocery_search);
        llSearchIcon=groceryDialog.findViewById(R.id.llSearchGroceryDialog);
        groceryDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        groceryDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        groceryDialog.getWindow().getAttributes().gravity = Gravity.FILL_VERTICAL|Gravity.TOP;
        ll_dialogBackGroceries=groceryDialog.findViewById(R.id.ll_dialogBackGroceries);
        dialogSearch=groceryDialog.findViewById(R.id.dialog_txtSearch);
        searchedRecyclerView=groceryDialog.findViewById(R.id.groceryRecyclerView);
        searchedLayoutManager = new LinearLayoutManager(StoreActivity.this, LinearLayoutManager.VERTICAL, false);
        searchedRecyclerView.setLayoutManager(searchedLayoutManager);
        searchedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        ll_StoreBackArrow = findViewById(R.id.ll_StoreBackArrow);
        txtStoreProduct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    dialogSearch.setText("");
                    groceryDialog.show();

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
       if (ll_StoreBackArrow.getId() == v.getId()) {
            finish();
        }
       else if (ll_dialogBackGroceries.getId()==v.getId()){
           groceryDialog.dismiss();
       }


    }


    public void setLayoutClicked(String categoryId) {
        catId=categoryId;
        getProducts(false, 1);
    }

    private void getProducts(final boolean isLoadMore, int pageNo) {
        JsonObject postData = new JsonObject();
        postData.addProperty("store_id",storeId);
        postData.addProperty("category_id", catId);
        postData.addProperty("page", pageNo);
        DataManager.getProductList(postData, new CategoryWisePrdoductsResponseListener() {
            @Override
            public void onLoadCompleted(CategoryWiseProductBean categoryWiseProductBean) {
                productListListener.getList(categoryWiseProductBean);
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(StoreActivity.this,error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finsh(View view) {
        finish();
    }

    public interface ProductListListener{
        void getList(CategoryWiseProductBean categoryWiseProductBean);

    }

    public ProductListListener getProductListListener() {
        return productListListener;
    }

    public void setProductListListener(ProductListListener productListListener) {
        this.productListListener = productListListener;
    }
}
