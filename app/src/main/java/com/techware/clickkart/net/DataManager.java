package com.techware.clickkart.net;

import android.util.Log;

import com.google.gson.JsonObject;
import com.techware.clickkart.listeners.AdResponseListener;
import com.techware.clickkart.listeners.AddAddressResponseListener;
import com.techware.clickkart.listeners.BasicListener;
import com.techware.clickkart.listeners.CartListResponseListener;
import com.techware.clickkart.listeners.CartResponseListener;
import com.techware.clickkart.listeners.CategoryWisePrdoductsResponseListener;
import com.techware.clickkart.listeners.CategoryWiseStoreResponseListener;
import com.techware.clickkart.listeners.ChangepasswordResponseListener;
import com.techware.clickkart.listeners.EditAddressResponseListener;
import com.techware.clickkart.listeners.EditProfileResponseListener;
import com.techware.clickkart.listeners.ForgotResponseListener;
import com.techware.clickkart.listeners.GetAddressListResponseListener;
import com.techware.clickkart.listeners.HelpResponseListener;
import com.techware.clickkart.listeners.LocationResponseListener;
import com.techware.clickkart.listeners.LoginListener;
import com.techware.clickkart.listeners.LoginResponseListener;
import com.techware.clickkart.listeners.OrderDetailsResponseListener;
import com.techware.clickkart.listeners.OrderHistoryListener;
import com.techware.clickkart.listeners.OrderPlacedResponseListener;
import com.techware.clickkart.listeners.PastOrderResponseListener;
import com.techware.clickkart.listeners.PeopleFavouriteResponseListener;
import com.techware.clickkart.listeners.RegisterResponseListener;
import com.techware.clickkart.listeners.RemoveAddressResponseListener;
import com.techware.clickkart.listeners.RemoveCartResponseListener;
import com.techware.clickkart.listeners.ReportResponseListener;
import com.techware.clickkart.listeners.SearchStoreResponseListener;
import com.techware.clickkart.listeners.SearchedCategoriesResponseListener;
import com.techware.clickkart.listeners.SearchedProductsResponseListener;
import com.techware.clickkart.listeners.ShopByCategoriesResponseListener;
import com.techware.clickkart.listeners.ShopByStoreResponseListener;
import com.techware.clickkart.listeners.StoreWiseSearchResponseListener;
import com.techware.clickkart.listeners.StoreWiseStoreResponseListener;
import com.techware.clickkart.listeners.UpdateCartResponseListener;
import com.techware.clickkart.listeners.UploadProfileResponseListener;
import com.techware.clickkart.listeners.YourFavouriteResponseListener;
import com.techware.clickkart.listeners.ZipCodeListener;
import com.techware.clickkart.model.AuthBean;
import com.techware.clickkart.model.BasicBean;
import com.techware.clickkart.model.CategoryWiseStore.CategoryWiseStoreResponseBean;
import com.techware.clickkart.model.PeopleFavouriteStores.PeopleFavouriteBean;
import com.techware.clickkart.model.YourFavouriteBean.YourFavouriteBean;
import com.techware.clickkart.model.addaddress.AddAddressBean;
import com.techware.clickkart.model.cart.CartBean;
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
import com.techware.clickkart.net.WSAsyncTasks.LocationNameTask;
import com.techware.clickkart.net.WSAsyncTasks.LoginTask;
import com.techware.clickkart.net.WSAsyncTasks.MobileAvailabilityCheckTask;
import com.techware.clickkart.net.WSAsyncTasks.UpdateFCMTokenTask;
import com.techware.clickkart.retrofit.RetrofitClient;
import com.techware.clickkart.util.AppConstants;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataManager {

    private static final String TAG = DataManager.class.getSimpleName();

    public static void performMobileAvailabilityCheck(JSONObject postData, final BasicListener listener) {

        MobileAvailabilityCheckTask mobileAvailabilityCheckTask = new MobileAvailabilityCheckTask(postData);
        mobileAvailabilityCheckTask.setMobileAvailabilityCheckTaskListener(
                new MobileAvailabilityCheckTask.MobileAvailabilityCheckTaskListener() {
                    @Override
                    public void dataDownloadedSuccessfully(BasicBean basicBean) {
                        if (basicBean == null)
                            listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                        else {
                            if (basicBean.getStatus().equalsIgnoreCase("Success")) {
                                listener.onLoadCompleted(basicBean);
                            } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                                listener.onLoadFailed(basicBean.getErrorMsg());
                            } else {
                                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                            }
                        }
                    }

                    @Override
                    public void dataDownloadFailed() {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                });
        mobileAvailabilityCheckTask.execute();
    }


    public static void performUpdateFCMToken(JSONObject postData, final BasicListener listener) {

        UpdateFCMTokenTask updateFCMTokenTask = new UpdateFCMTokenTask(postData);
        updateFCMTokenTask.setUpdateFCMTokenTaskListener(new UpdateFCMTokenTask.UpdateFCMTokenTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(BasicBean basicBean) {
                if (basicBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (basicBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(basicBean);
                    } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(basicBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        updateFCMTokenTask.execute();
    }

    public static void performLogin(JSONObject postData, final LoginListener listener) {

        LoginTask loginTask = new LoginTask(postData);
        loginTask.setLoginTaskListener(new LoginTask.LoginTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(AuthBean authBean) {
                if (authBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (authBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(authBean);
                    } else if (authBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(authBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        loginTask.execute();
    }

    public static void fetchLocationName(HashMap<String, String> urlParams, final BasicListener listener) {

        LocationNameTask locationNameTask = new LocationNameTask(urlParams);
        locationNameTask.setLocationNameTaskListener(new LocationNameTask.LocationNameTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(BasicBean basicBean) {
                if (basicBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (basicBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(basicBean);
                    } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(basicBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        locationNameTask.execute();
    }

    public static void registerUser(JsonObject postData, final RegisterResponseListener listener) {
        Call<RegisterResponseBean> call = RetrofitClient.getInstance().getApi().registerUser(postData);
        call.enqueue(new Callback<RegisterResponseBean>() {
            @Override
            public void onResponse(Call<RegisterResponseBean> call, Response<RegisterResponseBean> response) {
                RegisterResponseBean bean = response.body();
                try {
                    Log.i("Register Response", response.body().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bean == null) {
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        listener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        listener.onLoadFailed(bean.getMessage());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseBean> call, Throwable t) {
                listener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void loginUser(JsonObject postData, final LoginResponseListener loginResponseListener) {
        Call<LoginResponseBean> call = RetrofitClient.getInstance().getApi().userLogin(postData);
        call.enqueue(new Callback<LoginResponseBean>() {
            @Override
            public void onResponse(Call<LoginResponseBean> call, Response<LoginResponseBean> response) {
                LoginResponseBean bean = response.body();
                if (bean == null) {
                    loginResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        loginResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        loginResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        loginResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseBean> call, Throwable t) {
                loginResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void findZip(JsonObject postData, final ZipCodeListener zipCodeListener) {
        Call<ZipCodeResponseBean> call = RetrofitClient.getInstance().getApi().findZipCode(postData);
        call.enqueue(new Callback<ZipCodeResponseBean>() {
            @Override
            public void onResponse(Call<ZipCodeResponseBean> call, Response<ZipCodeResponseBean> response) {
                ZipCodeResponseBean bean = response.body();
                if (bean == null) {
                    zipCodeListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        zipCodeListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        zipCodeListener.onLoadFailed(bean.getMessage());
                    } else {
                        zipCodeListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<ZipCodeResponseBean> call, Throwable t) {
                zipCodeListener.onLoadFailed(t.getMessage());
            }
        });

    }

    public static void performForgotPasssword(JsonObject postData, final ForgotResponseListener forgotResponseListener) {
        Call<ForgotPasswordBean> call = RetrofitClient.getInstance().getApi().forgotPassword(postData);
        call.enqueue(new Callback<ForgotPasswordBean>() {
            @Override
            public void onResponse(Call<ForgotPasswordBean> call, Response<ForgotPasswordBean> response) {
                ForgotPasswordBean bean = response.body();
                if (bean == null) {
                    forgotResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        forgotResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        forgotResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        forgotResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordBean> call, Throwable t) {
                forgotResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchShopByStoreList(JsonObject postData, final ShopByStoreResponseListener shopByStoreResponseListener) {
        Call<StoreListBean> call = RetrofitClient.getInstance().getApi().storeList(postData);
        call.enqueue(new Callback<StoreListBean>() {
            @Override
            public void onResponse(Call<StoreListBean> call, Response<StoreListBean> response) {
                StoreListBean bean = response.body();
                if (bean == null) {
                    shopByStoreResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        shopByStoreResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        shopByStoreResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        shopByStoreResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }


            @Override
            public void onFailure(Call<StoreListBean> call, Throwable t) {
                shopByStoreResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchCategoryWiseStoreList(JsonObject postData, final StoreWiseStoreResponseListener categoryWiseStoreResponseListener) {
        Call<StoreListWiseCategory> call = RetrofitClient.getInstance().getApi().shopCategoryWiseStoreList(postData);
        call.enqueue(new Callback<StoreListWiseCategory>() {
            @Override
            public void onResponse(Call<StoreListWiseCategory> call, Response<StoreListWiseCategory> response) {
                StoreListWiseCategory bean = response.body();
                if (bean == null) {
                    categoryWiseStoreResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        categoryWiseStoreResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        categoryWiseStoreResponseListener.onLoadFailed("No Store List Avialable");
                    } else {
                        categoryWiseStoreResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }


            @Override
            public void onFailure(Call<StoreListWiseCategory> call, Throwable t) {
                categoryWiseStoreResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchCategories(JsonObject postData, final ShopByCategoriesResponseListener shopByCategoriesResponseListener) {
        Call<CategoryListBean> call = RetrofitClient.getInstance().getApi().categoryList(postData);
        call.enqueue(new Callback<CategoryListBean>() {
            @Override
            public void onResponse(Call<CategoryListBean> call, Response<CategoryListBean> response) {
                CategoryListBean bean = response.body();
                if (bean == null) {
                    shopByCategoriesResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        shopByCategoriesResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        shopByCategoriesResponseListener.onLoadFailed("No Categories Avialable");
                    } else {
                        shopByCategoriesResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryListBean> call, Throwable t) {
                shopByCategoriesResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchPastOrders(final PastOrderResponseListener pastOrderResponseListener) {
        Call<PastOrberBean> call = RetrofitClient.getInstance().getApi().pastOrderList();
        call.enqueue(new Callback<PastOrberBean>() {
            @Override
            public void onResponse(Call<PastOrberBean> call, Response<PastOrberBean> response) {
                PastOrberBean pastOrberBean = response.body();
                if (pastOrberBean == null) {
                    pastOrderResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (pastOrberBean.getStatus().equalsIgnoreCase("success")) {
                        pastOrderResponseListener.onLoadCompleted(pastOrberBean);
                    } else if (pastOrberBean.getStatus().equalsIgnoreCase("error")) {
                        pastOrderResponseListener.onLoadFailed("No Past Order List Avialable");
                    } else {
                        pastOrderResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<PastOrberBean> call, Throwable t) {
                pastOrderResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchAd(final AdResponseListener adResponseListener) {
        Call<AdResponseBean> call = RetrofitClient.getInstance().getApi().adList();
        call.enqueue(new Callback<AdResponseBean>() {
            @Override
            public void onResponse(Call<AdResponseBean> call, Response<AdResponseBean> response) {
                AdResponseBean bean = response.body();
                if (bean == null) {
                    adResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        adResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        adResponseListener.onLoadFailed("No Ad Avialable");
                    } else {
                        adResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<AdResponseBean> call, Throwable t) {
                adResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchFavouriteStore(JsonObject postData, final PeopleFavouriteResponseListener peopleFavouriteResponseListener) {
        Call<PeopleFavouriteBean> call = RetrofitClient.getInstance().getApi().peopleFavourite(postData);
        call.enqueue(new Callback<PeopleFavouriteBean>() {
            @Override
            public void onResponse(Call<PeopleFavouriteBean> call, Response<PeopleFavouriteBean> response) {
                PeopleFavouriteBean bean = response.body();
                if (bean == null) {
                    peopleFavouriteResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        peopleFavouriteResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        peopleFavouriteResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        peopleFavouriteResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<PeopleFavouriteBean> call, Throwable t) {
                peopleFavouriteResponseListener.onLoadFailed(t.getMessage());

            }
        });
    }

    public static void fetchYourStoreList(JsonObject postData, final YourFavouriteResponseListener yourFavouriteResponseListener) {
        Call<YourFavouriteBean> call = RetrofitClient.getInstance().getApi().yourFavourite(postData);
        call.enqueue(new Callback<YourFavouriteBean>() {
            @Override
            public void onResponse(Call<YourFavouriteBean> call, Response<YourFavouriteBean> response) {
                YourFavouriteBean bean = response.body();
                if (bean == null) {
                    yourFavouriteResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        yourFavouriteResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        yourFavouriteResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        yourFavouriteResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<YourFavouriteBean> call, Throwable t) {
                yourFavouriteResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchCategoryPeopleFavouriteStore(JsonObject postData, PeopleFavouriteResponseListener peopleFavouriteResponseListener) {
        Call<PeopleFavouriteBean> call = RetrofitClient.getInstance().getApi().categoryPeopleFavourite(postData);
        call.enqueue(new Callback<PeopleFavouriteBean>() {
            @Override
            public void onResponse(Call<PeopleFavouriteBean> call, Response<PeopleFavouriteBean> response) {
                PeopleFavouriteBean bean = response.body();
                if (bean == null) {
                    peopleFavouriteResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        peopleFavouriteResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        peopleFavouriteResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        peopleFavouriteResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<PeopleFavouriteBean> call, Throwable t) {
                peopleFavouriteResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchCategoryYourFavourite(JsonObject postData, YourFavouriteResponseListener yourFavouriteResponseListener) {
        Call<YourFavouriteBean> call = RetrofitClient.getInstance().getApi().yourCategoryFavourite(postData);
        call.enqueue(new Callback<YourFavouriteBean>() {
            @Override
            public void onResponse(Call<YourFavouriteBean> call, Response<YourFavouriteBean> response) {
                YourFavouriteBean bean = response.body();
                if (bean == null) {
                    yourFavouriteResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        yourFavouriteResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        yourFavouriteResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        yourFavouriteResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }

            }

            @Override
            public void onFailure(Call<YourFavouriteBean> call, Throwable t) {
                yourFavouriteResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchHelp(HelpResponseListener helpResponseListener) {
        Call<HelpBean> call = RetrofitClient.getInstance().getApi().help();
        call.enqueue(new Callback<HelpBean>() {
            @Override
            public void onResponse(Call<HelpBean> call, Response<HelpBean> response) {
                HelpBean bean = response.body();
                if (bean == null) {
                    helpResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        helpResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        helpResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        helpResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }

            }

            @Override
            public void onFailure(Call<HelpBean> call, Throwable t) {

            }
        });
    }

    public static void performReportProblem(JsonObject postData, ReportResponseListener reportResponseListener) {
        Call<ReportBean> call = RetrofitClient.getInstance().getApi().reportProblem(postData);
        call.enqueue(new Callback<ReportBean>() {
            @Override
            public void onResponse(Call<ReportBean> call, Response<ReportBean> response) {
                ReportBean bean = response.body();
                if (bean == null) {
                    reportResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        reportResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        reportResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        reportResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }

            }

            @Override
            public void onFailure(Call<ReportBean> call, Throwable t) {

            }
        });
    }

    public static void fetOrderHistory(int pageNumberToLoad, OrderHistoryListener orderHistoryListener) {
        Call<OrderHistoryBean> call = RetrofitClient.getInstance().getApi().getOrderHistory(pageNumberToLoad);
        call.enqueue(new Callback<OrderHistoryBean>() {
            @Override
            public void onResponse(Call<OrderHistoryBean> call, Response<OrderHistoryBean> response) {
                OrderHistoryBean bean = response.body();
                if (bean == null) {
                    orderHistoryListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        orderHistoryListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        orderHistoryListener.onLoadFailed(bean.getMessage());
                    } else {
                        orderHistoryListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryBean> call, Throwable t) {
                orderHistoryListener.onLoadFailed(t.getMessage());
            }
        });
    }


    public static void performLocationSearch(JsonObject postData, LocationResponseListener locationResponseListener) {
        Call<LocationResponseBean> call = RetrofitClient.getInstance().getApi().getLocation(postData);
        call.enqueue(new Callback<LocationResponseBean>() {
            @Override
            public void onResponse(Call<LocationResponseBean> call, Response<LocationResponseBean> response) {
                LocationResponseBean bean = response.body();
                if (bean == null) {
                    locationResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        locationResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        locationResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        locationResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<LocationResponseBean> call, Throwable t) {
                locationResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
    }

    public static void performProductSearch(JsonObject postData, SearchedProductsResponseListener searchedProductsResponseListener) {
        Call<SearchProductBean> call = RetrofitClient.getInstance().getApi().getSearchedProduct(postData);
        call.enqueue(new Callback<SearchProductBean>() {
            @Override
            public void onResponse(Call<SearchProductBean> call, Response<SearchProductBean> response) {
                SearchProductBean bean = response.body();
                if (bean == null) {
                    searchedProductsResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        searchedProductsResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        searchedProductsResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        searchedProductsResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchProductBean> call, Throwable t) {
                searchedProductsResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
    }

    public static void performStoreSearch(JsonObject postData, SearchStoreResponseListener searchStoreResponseListener) {
        Call<SearchStoreBean> call = RetrofitClient.getInstance().getApi().getSearchedStore(postData);
        call.enqueue(new Callback<SearchStoreBean>() {
            @Override
            public void onResponse(Call<SearchStoreBean> call, Response<SearchStoreBean> response) {
                SearchStoreBean bean = response.body();
                if (bean == null) {
                    searchStoreResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        searchStoreResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        searchStoreResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        searchStoreResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }

            }

            @Override
            public void onFailure(Call<SearchStoreBean> call, Throwable t) {
                searchStoreResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
    }

    public static void performStoreProductSearch(JsonObject postData, StoreWiseSearchResponseListener storeWiseSearchResponseListener) {
        Call<StorewiseSearchProductBean> call = RetrofitClient.getInstance().getApi().storewiseSearchProduct(postData);
        call.enqueue(new Callback<StorewiseSearchProductBean>() {
            @Override
            public void onResponse(Call<StorewiseSearchProductBean> call, Response<StorewiseSearchProductBean> response) {
                StorewiseSearchProductBean bean = response.body();
                if (bean == null) {
                    storeWiseSearchResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        storeWiseSearchResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        storeWiseSearchResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        storeWiseSearchResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }

            }

            @Override
            public void onFailure(Call<StorewiseSearchProductBean> call, Throwable t) {
                storeWiseSearchResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
    }

    public static void performSearchCategories(JsonObject postData, SearchedCategoriesResponseListener searchedCategoriesResponseListener) {
        Call<SearchedCategoryList> call = RetrofitClient.getInstance().getApi().getSearchedCategories(postData);
        call.enqueue(new Callback<SearchedCategoryList>() {
            @Override
            public void onResponse(Call<SearchedCategoryList> call, Response<SearchedCategoryList> response) {
                SearchedCategoryList bean = response.body();
                if (bean == null) {
                    searchedCategoriesResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        searchedCategoriesResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        searchedCategoriesResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        searchedCategoriesResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchedCategoryList> call, Throwable t) {
                searchedCategoriesResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void getProductList(JsonObject postData, CategoryWisePrdoductsResponseListener categoryWisePrdoductsResponseListener) {
        Call<CategoryWiseProductBean> call = RetrofitClient.getInstance().getApi().getProductsList(postData);
        call.enqueue(new Callback<CategoryWiseProductBean>() {
            @Override
            public void onResponse(Call<CategoryWiseProductBean> call, Response<CategoryWiseProductBean> response) {
                CategoryWiseProductBean bean = response.body();
                if (bean == null) {
                    categoryWisePrdoductsResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        categoryWisePrdoductsResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        categoryWisePrdoductsResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        categoryWisePrdoductsResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryWiseProductBean> call, Throwable t) {
                categoryWisePrdoductsResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void changePassword(JsonObject postData, ChangepasswordResponseListener changepasswordResponseListener) {
        Call<ChangePasswordBean> call = RetrofitClient.getInstance().getApi().changePassword(postData);
        call.enqueue(new Callback<ChangePasswordBean>() {
            @Override
            public void onResponse(Call<ChangePasswordBean> call, Response<ChangePasswordBean> response) {
                ChangePasswordBean bean = response.body();
                if (bean == null) {
                    changepasswordResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        changepasswordResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        changepasswordResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        changepasswordResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }

            }

            @Override
            public void onFailure(Call<ChangePasswordBean> call, Throwable t) {
                changepasswordResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void performAddAddress(JsonObject postData, AddAddressResponseListener addAddressResponseListener) {
        Call<AddAddressBean> call = RetrofitClient.getInstance().getApi().addAddress(postData);
        call.enqueue(new Callback<AddAddressBean>() {
            @Override
            public void onResponse(Call<AddAddressBean> call, Response<AddAddressBean> response) {
                AddAddressBean bean = response.body();
                if (bean == null) {
                    addAddressResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        addAddressResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        addAddressResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        addAddressResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<AddAddressBean> call, Throwable t) {
                addAddressResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void performEditProfile(JsonObject postData, EditProfileResponseListener editProfileResponseListener) {
        Call<EditProfileBean> call = RetrofitClient.getInstance().getApi().editProfile(postData);
        call.enqueue(new Callback<EditProfileBean>() {
            @Override
            public void onResponse(Call<EditProfileBean> call, Response<EditProfileBean> response) {
                EditProfileBean bean = response.body();
                if (bean == null) {
                    editProfileResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        editProfileResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        editProfileResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        editProfileResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<EditProfileBean> call, Throwable t) {
                editProfileResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void getAddressList(GetAddressListResponseListener getAddressListResponseListener) {
        Call<GetAddressBean> call = RetrofitClient.getInstance().getApi().getAddressList();
        call.enqueue(new Callback<GetAddressBean>() {
            @Override
            public void onResponse(Call<GetAddressBean> call, Response<GetAddressBean> response) {
                GetAddressBean bean = response.body();
                if (bean == null) {
                    getAddressListResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        getAddressListResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        getAddressListResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        getAddressListResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }

            }

            @Override
            public void onFailure(Call<GetAddressBean> call, Throwable t) {
                getAddressListResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void performEditAddress(JsonObject postData, EditAddressResponseListener editAddressResponseListener) {
        Call<EditBean> call = RetrofitClient.getInstance().getApi().editAddress(postData);
        call.enqueue(new Callback<EditBean>() {
            @Override
            public void onResponse(Call<EditBean> call, Response<EditBean> response) {
                EditBean bean = response.body();
                if (bean == null) {
                    editAddressResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        editAddressResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        editAddressResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        editAddressResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }

            }

            @Override
            public void onFailure(Call<EditBean> call, Throwable t) {
                editAddressResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void deleteAddress(JsonObject postData, RemoveAddressResponseListener removeAddressResponseListener) {
        Call<RemoveAddressBean> call = RetrofitClient.getInstance().getApi().addressDelete(postData);
        call.enqueue(new Callback<RemoveAddressBean>() {
            @Override
            public void onResponse(Call<RemoveAddressBean> call, Response<RemoveAddressBean> response) {
                RemoveAddressBean bean = response.body();
                if (bean == null) {
                    removeAddressResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        removeAddressResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        removeAddressResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        removeAddressResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<RemoveAddressBean> call, Throwable t) {
                removeAddressResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void uploadProfilePic(String imageFilePath, UploadProfileResponseListener uploadProfileResponseListener) {
        File file = new File(imageFilePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        Call<ProfileImagBean> call = RetrofitClient.getInstance().getApi().uploadProfile(filePart);
        call.enqueue(new Callback<ProfileImagBean>() {
            @Override
            public void onResponse(Call<ProfileImagBean> call, Response<ProfileImagBean> response) {
                ProfileImagBean bean = response.body();
                if (bean == null) {
                    uploadProfileResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        uploadProfileResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("failed to upload")) {
                        uploadProfileResponseListener.onLoadFailed(bean.getStatus());
                    } else {
                        uploadProfileResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileImagBean> call, Throwable t) {
                uploadProfileResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void addToCart(JsonObject postData, CartResponseListener cartResponseListener) {
        Call<com.techware.clickkart.model.cart.CartBean> call=RetrofitClient.getInstance().getApi().addToCart(postData);
      call.enqueue(new Callback<CartBean>() {
          @Override
          public void onResponse(Call<CartBean> call, Response<CartBean> response) {
              com.techware.clickkart.model.cart.CartBean bean=response.body();
              if (bean == null) {
                  cartResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
              } else {
                  if (bean.getStatus().equalsIgnoreCase("success")) {
                      cartResponseListener.onLoadCompleted(bean);
                  } else if (bean.getStatus().equalsIgnoreCase("error")) {
                      cartResponseListener.onLoadFailed(bean.getMessage());
                  } else {
                      cartResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                  }
              }
          }

          @Override
          public void onFailure(Call<CartBean> call, Throwable t) {
              cartResponseListener.onLoadFailed(t.getMessage());
          }
      });
    }

    public static void fetchCartList(CartListResponseListener cartListResponseListener) {
        Call<GetCartBean> call=RetrofitClient.getInstance().getApi().getCartList();
        call.enqueue(new Callback<GetCartBean>() {
            @Override
            public void onResponse(Call<GetCartBean> call, Response<GetCartBean> response) {
                GetCartBean bean=response.body();
                if (bean == null) {
                    cartListResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        cartListResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        cartListResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        cartListResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCartBean> call, Throwable t) {
                cartListResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void performUpdateCart(JsonObject postData, UpdateCartResponseListener updateCartResponseListener) {
        Call<UpdateCart>call=RetrofitClient.getInstance().getApi().updateCart(postData);
        call.enqueue(new Callback<UpdateCart>() {
            @Override
            public void onResponse(Call<UpdateCart> call, Response<UpdateCart> response) {
                UpdateCart bean=response.body();
                if (bean == null) {
                    updateCartResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        updateCartResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        updateCartResponseListener.onLoadFailed(bean.getStatus());
                    } else {
                        updateCartResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateCart> call, Throwable t) {
                updateCartResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void performRemoveCart(JsonObject postData, RemoveCartResponseListener removeCartResponseListener) {
        Call<RemoveItemCart> call=RetrofitClient.getInstance().getApi().removeCart(postData);
        call.enqueue(new Callback<RemoveItemCart>() {
            @Override
            public void onResponse(Call<RemoveItemCart> call, Response<RemoveItemCart> response) {
                RemoveItemCart bean=response.body();
                if (bean == null) {
                    removeCartResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        removeCartResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        removeCartResponseListener.onLoadFailed(bean.getStatus());
                    } else {
                        removeCartResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<RemoveItemCart> call, Throwable t) {

            }
        });
    }

    public static void orderPlaced(JsonObject postData, OrderPlacedResponseListener orderPlacedResponseListener) {
        Call<ResponseOrderPlaced> call=RetrofitClient.getInstance().getApi().orderPlaced(postData);
        call.enqueue(new Callback<ResponseOrderPlaced>() {
            @Override
            public void onResponse(Call<ResponseOrderPlaced> call, Response<ResponseOrderPlaced> response) {
                ResponseOrderPlaced bean=response.body();
                if (bean == null) {
                    orderPlacedResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                } else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        orderPlacedResponseListener.onLoadCompleted(bean);
                    } else if (bean.getStatus().equalsIgnoreCase("error")) {
                        orderPlacedResponseListener.onLoadFailed(bean.getStatus());
                    } else {
                        orderPlacedResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOrderPlaced> call, Throwable t) {
                orderPlacedResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void fetchOrderDetails(JsonObject postData, OrderDetailsResponseListener orderDetailsResponseListener) {
        Call<OrderDetailsBean> call=RetrofitClient.getInstance().getApi().orderDetails(postData);
        call.enqueue(new Callback<OrderDetailsBean>() {
            @Override
            public void onResponse(Call<OrderDetailsBean> call, Response<OrderDetailsBean> response) {
                OrderDetailsBean bean=response.body();
                if (bean == null) {
                    orderDetailsResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                }
                else {
                    if (bean.getStatus().equalsIgnoreCase("success")) {
                        orderDetailsResponseListener.onLoadCompleted(bean);
                    }
                    else if (bean.getStatus().equalsIgnoreCase("error")) {
                        orderDetailsResponseListener.onLoadFailed(bean.getMessage());
                    } else {
                        orderDetailsResponseListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }

            }

            @Override
            public void onFailure(Call<OrderDetailsBean> call, Throwable t) {
                orderDetailsResponseListener.onLoadFailed(t.getMessage());
            }
        });
    }
}
