package com.techware.clickkart.retrofit;

import com.google.gson.JsonObject;
import com.techware.clickkart.model.CartBean;
import com.techware.clickkart.model.CategoryWiseStore.CategoryWiseStoreResponseBean;
import com.techware.clickkart.model.PeopleFavouriteStores.PeopleFavouriteBean;
import com.techware.clickkart.model.UsersResponse;
import com.techware.clickkart.model.YourFavouriteBean.YourFavouriteBean;
import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.categoryliststore.StoreListWiseCategory;
import com.techware.clickkart.model.categorywiseproduct.CategoryWiseProductBean;
import com.techware.clickkart.model.changepassword.ChangePasswordBean;
import com.techware.clickkart.model.editaddress.EditBean;
import com.techware.clickkart.model.editprofile.EditProfileBean;
import com.techware.clickkart.model.forgotpassword.ForgotPasswordBean;
import com.techware.clickkart.model.getaddress.GetAddressBean;
import com.techware.clickkart.model.getcart.GetCartBean;
import com.techware.clickkart.model.help.HelpBean;
import com.techware.clickkart.model.locationbean.LocationResponseBean;
import com.techware.clickkart.model.login.LoginResponseBean;
import com.techware.clickkart.model.orderdetails.OrderDetailsBean;
import com.techware.clickkart.model.orderhistory.OrderHistoryBean;
import com.techware.clickkart.model.orderplacedresponse.ResponseOrderPlaced;
import com.techware.clickkart.model.pastorders.PastOrberBean;
import com.techware.clickkart.model.register.RegisterResponseBean;
import com.techware.clickkart.model.removeaddress.RemoveAddressBean;
import com.techware.clickkart.model.removecart.RemoveItemCart;
import com.techware.clickkart.model.reportproblem.ReportBean;
import com.techware.clickkart.model.searchcategory.SearchedCategoryList;
import com.techware.clickkart.model.searchproduct.SearchProductBean;
import com.techware.clickkart.model.searchstore.SearchStoreBean;
import com.techware.clickkart.model.shopbyad.AdResponseBean;
import com.techware.clickkart.model.shopbycategory.CategoryListBean;
import com.techware.clickkart.model.shopbystore.StoreListBean;
import com.techware.clickkart.model.storewisesearchedproduct.StorewiseSearchProductBean;
import com.techware.clickkart.model.updatecart.UpdateCart;
import com.techware.clickkart.model.uploadimage.ProfileImagBean;
import com.techware.clickkart.model.zipcode.ZipCodeResponseBean;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @POST("register")
    Call<RegisterResponseBean> registerUser(@Body JsonObject postData);

    @POST("do_login")
    Call<LoginResponseBean> userLogin(@Body JsonObject postData);

    @POST("forgot_password")
    Call<ForgotPasswordBean> forgotPassword(@Body JsonObject postData);

    @POST("find")
    Call<ZipCodeResponseBean> findZipCode(@Body JsonObject postData);

    @POST("store_details")
    Call<StoreListBean> storeList(@Body JsonObject postData);

    @POST("search_all_categories")
    Call<CategoryListBean> categoryList(@Body JsonObject postData);

    @POST("advertisement")
    Call<AdResponseBean> adList();

    @POST("searchCategory")
    Call<CategoryWiseStoreResponseBean> CategoryWiseStoreList(@Body JsonObject postData);
    @POST("shopByCategory")
    Call<StoreListWiseCategory> shopCategoryWiseStoreList(@Body JsonObject postData);



    @POST("pastorder_details")
    Call<PastOrberBean> pastOrderList();

    @POST("peopleFavoriteList")
    Call<PeopleFavouriteBean> peopleFavourite(@Body JsonObject postData);

    @POST("peopleFavoriteStoresByCategory")
    Call<PeopleFavouriteBean> categoryPeopleFavourite(@Body JsonObject postData);

    @POST("myFavoriteStores")
    Call<YourFavouriteBean> yourFavourite(@Body JsonObject postData);

    @POST("myFavoriteStoresByCategory")
    Call<YourFavouriteBean> yourCategoryFavourite(@Body JsonObject postData);

    @POST("reportAProblem")
    Call<ReportBean> reportProblem(@Body JsonObject postData);

    @POST("help")
    Call<HelpBean> help();

    @GET("order_history")
    Call<OrderHistoryBean> getOrderHistory(@Query("page") int page);

    @POST("searchPlace")
    Call<LocationResponseBean> getLocation(@Body JsonObject postData);

    @POST("addAddress")
    Call<AddAddressBean> addAddress(@Body JsonObject postData);

    @POST("searchCategory")
    Call<SearchedCategoryList> getSearchedCategories(@Body JsonObject postData);

    @POST("searchProduct")
    Call<SearchProductBean> getSearchedProduct(@Body JsonObject postData);

    @POST("storeProductSearch")
    Call<StorewiseSearchProductBean> storewiseSearchProduct(@Body JsonObject postData);

    @POST("productsByCategory")
    Call<CategoryWiseProductBean> getProductsList(@Body JsonObject postData);

    @POST("changePassword")
    Call<ChangePasswordBean> changePassword(@Body JsonObject postData);

    @POST("editProfile")
    Call<EditProfileBean> editProfile(@Body JsonObject postData);

    @POST("searchStore")
    Call<SearchStoreBean> getSearchedStore(@Body JsonObject postData);

    @POST("showAllAddress")
    Call<GetAddressBean> getAddressList();

    @POST("editAddress")
    Call<EditBean> editAddress(@Body JsonObject postData);

    @POST("addressDelete")
    Call<RemoveAddressBean> addressDelete(@Body JsonObject postData);
    @POST("update_cart")
    Call<UpdateCart> updateCart(@Body JsonObject postData);
    @POST("deleteitem_cart")
    Call<RemoveItemCart> removeCart(@Body JsonObject postData);
    @POST("add_cart")
    Call<com.techware.clickkart.model.cart.CartBean> addToCart(@Body JsonObject postData);
    @GET("get_cart")
    Call<GetCartBean> getCartList();
    @Multipart
    @POST("userImage")
    Call<ProfileImagBean> uploadProfile(@Part MultipartBody.Part image);
    @POST("placeOrder")
    Call<ResponseOrderPlaced>orderPlaced(@Body JsonObject postData);
    @POST("orderDetails")
    Call<OrderDetailsBean>orderDetails(@Body JsonObject postData);

    @GET("allusers")
    Call<UsersResponse> getUsers();

}
