package com.gustavonovais.forecast.retrofit;




import com.gustavonovais.forecast.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class DataRequest {

    private final Retrofit retrofit;
    private final RequestsEndPoints.EndPoints api;

    public DataRequest() {


        this.retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = this.retrofit.create(RequestsEndPoints.EndPoints.class);
    }

    private static OkHttpClient getOkHttpClient() {
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                    .readTimeout(40, TimeUnit.SECONDS).connectTimeout(40, TimeUnit.SECONDS)
                    .build();

            return client;
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
