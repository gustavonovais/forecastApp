package com.gustavonovais.forecast.retrofit;

import com.gustavonovais.forecast.model.Forecast;

import retrofit2.Call;
import retrofit2.Callback;

public class ManagerRequest<T> {

    private final RequestsEndPoints.EndPoints mApi;

    public ManagerRequest() {
        DataRequest dataRequest = new DataRequest();
        mApi = dataRequest.getApi();
    }

    public void getForecast(Callback<Forecast> forecast, double lat, double lng) {
        Call<Forecast> call = mApi.getForecast(lat, lng);
        call.enqueue(forecast);
    }

}



