package com.raiyansoft.mawed.services.api;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String TAG = RetrofitClient.class.getSimpleName();

    private static Retrofit retrofit = null;
    private static String BASE_URL = "http://mawed.work/api/";

    public static Retrofit getClient() {
        //TODO: Understanding HttpLoggingInterceptor and networkInterceptor and implemnt it here
        HttpLoggingInterceptor httpLogging = new HttpLoggingInterceptor(message -> Log.d(TAG, "getClient: " + message));
        httpLogging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(httpLogging)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}
