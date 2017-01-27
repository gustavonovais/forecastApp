package com.arena.gustavonovais.challengearena.retrofit;


import com.arena.gustavonovais.challengearena.BuildConfig;
import com.arena.gustavonovais.challengearena.model.Forecast;
import com.arena.gustavonovais.challengearena.utils.ParamKey;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class RequestsEndPoints {

    public interface EndPoints {
        @GET(ParamKey.URI + BuildConfig.KEY + ParamKey.QUERY + ParamKey.EXCLUDE)
        Call<Forecast> getForecast(@Path(ParamKey.LAT) double lat, @Path(ParamKey.LNG) double lng);
    }


}