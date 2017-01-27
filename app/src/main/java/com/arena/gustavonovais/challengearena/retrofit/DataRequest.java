package com.arena.gustavonovais.challengearena.retrofit;


import com.arena.gustavonovais.challengearena.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class DataRequest {

    private final Retrofit retrofit;
    private final RequestsEndPoints.EndPoints api;

    public DataRequest() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .readTimeout(40, TimeUnit.SECONDS).connectTimeout(40, TimeUnit.SECONDS)
                .build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = this.retrofit.create(RequestsEndPoints.EndPoints.class);
    }

    private static OkHttpClient getOkHttpClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.readTimeout(60, TimeUnit.SECONDS);
            builder.connectTimeout(60, TimeUnit.SECONDS);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public RequestsEndPoints.EndPoints getApi() {
        return api;
    }
}
