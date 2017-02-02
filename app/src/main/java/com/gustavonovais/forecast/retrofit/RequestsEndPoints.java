package com.gustavonovais.forecast.retrofit;


import com.arena.gustavonovais.forecast.BuildConfig;
import com.gustavonovais.forecast.model.Forecast;
import com.gustavonovais.forecast.utils.ParamKey;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class RequestsEndPoints {

    public interface EndPoints {
        @GET(ParamKey.URI + BuildConfig.KEY + ParamKey.QUERY + ParamKey.EXCLUDE)
        Call<Forecast> getForecast(@Path(ParamKey.LAT) double lat, @Path(ParamKey.LNG) double lng);
    }


}