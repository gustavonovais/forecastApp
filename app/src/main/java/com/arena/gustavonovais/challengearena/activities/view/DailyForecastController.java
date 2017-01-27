package com.arena.gustavonovais.challengearena.activities.view;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arena.gustavonovais.challengearena.DailyForecastBinding;
import com.arena.gustavonovais.challengearena.R;
import com.arena.gustavonovais.challengearena.enums.IconEnum;
import com.arena.gustavonovais.challengearena.model.City;
import com.arena.gustavonovais.challengearena.model.Daily;
import com.arena.gustavonovais.challengearena.model.Forecast;
import com.arena.gustavonovais.challengearena.retrofit.ManagerRequest;
import com.arena.gustavonovais.challengearena.utils.ParamKey;
import com.bluelinelabs.conductor.Controller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GustavoNovais on 27/01/17.
 */

public class DailyForecastController extends Controller implements SwipeRefreshLayout.OnRefreshListener{

    private City city;
    private Forecast forecast;
    private View view;
    private DailyForecastBinding binding;

    public DailyForecastController(Bundle bundle) {
        super(bundle);
        city = getCity();
    }

    public City getCity() {
        return (City) getArgs().get(ParamKey.CITY);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.daily_forecast_controller, container, false);
        view = binding.getRoot();

        getForecast(city.lat1, city.lng1);

        binding.swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }


    public void configureInfoDaily(){
        binding.txtCityName.setText(city.name);
        binding.txtSummary.setText(forecast.daily.data.get(0).summary);
        binding.txtTemperature.setText(String.valueOf(forecast.daily.data.get(0).temperatureMax) + " ℉");
        binding.imgIcon.setImageDrawable(IconEnum.fromDesc(forecast.daily.data.get(0).icon));
    }


    private void getForecast(double lat, double lng){
        ManagerRequest<Forecast> mApi = new ManagerRequest<>();
        binding.swipeRefreshLayout.setRefreshing(true);

        mApi.getForecast(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {

                binding.swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()){
                    forecast = response.body();
                    configureInfoDaily();
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.i("DEU RUIM", "DEU RUIM");
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        },lat, lng);
    }


    @Override
    public void onRefresh() {
        getForecast(city.lat1, city.lng1);
        binding.swipeRefreshLayout.setRefreshing(false);
    }
}
