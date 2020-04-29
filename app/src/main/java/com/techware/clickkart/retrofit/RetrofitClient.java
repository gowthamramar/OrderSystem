package com.techware.clickkart.retrofit;

import com.techware.clickkart.config.Config;

import java.io.IOException;
import java.net.URL;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

   // private static final String BASE_URL = "https://clickkart-mobile.techware.in/Webservices/";
   // public static final String IMAGE_PATH_BASE URL = "https://techlabz.in/clickartmobile.in/";
    public static final String IMAGE_PATH = "https://techlabz.in/clickartmobile.in/";
    public static final String DEVELOPMENT_API = "https://techlabz.in/clickartmobile.in/Webservices/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()

                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();

                                Request.Builder requestBuilder = original.newBuilder()
                                        .addHeader("User-Agent", "Mozilla/5.0 ( compatible )")
                                        .addHeader("Auth", Config.getInstance().getAuthToken())
                                        .addHeader("Content-Type", "application/json")
                                        .addHeader("Accept", "application/json")
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        }
                );

        CustomHttpLoggerInterceptor customHttpLoggerInterceptor = new CustomHttpLoggerInterceptor();
        okHttpClient.addInterceptor(customHttpLoggerInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(DEVELOPMENT_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
