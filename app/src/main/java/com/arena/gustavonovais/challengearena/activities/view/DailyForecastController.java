package com.arena.gustavonovais.challengearena.activities.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arena.gustavonovais.challengearena.DailyForecastBinding;
import com.arena.gustavonovais.challengearena.R;
import com.arena.gustavonovais.challengearena.adapter.AdapterNextDays;
import com.arena.gustavonovais.challengearena.enums.Day;
import com.arena.gustavonovais.challengearena.enums.IconEnum;
import com.arena.gustavonovais.challengearena.model.City;
import com.arena.gustavonovais.challengearena.model.Data;
import com.arena.gustavonovais.challengearena.model.Forecast;
import com.arena.gustavonovais.challengearena.retrofit.ManagerRequest;
import com.arena.gustavonovais.challengearena.utils.ActivityUtils;
import com.arena.gustavonovais.challengearena.utils.ParamKey;
import com.bluelinelabs.conductor.Controller;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GustavoNovais on 27/01/17.
 */

public class DailyForecastController extends Controller implements SwipeRefreshLayout.OnRefreshListener{

    private final City city;
    private Forecast forecast;
    private View view;
    private DailyForecastBinding binding;
    private AdapterNextDays adapter;

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
        binding.txtSummary.setText(forecast.daily.data.get(Day.DAY00.getId()).summary);
        binding.txtTemperature.setText(String.valueOf(forecast.daily.data.get(0).temperatureMax) + ActivityUtils.FAHRENHEIT);
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
                    configureNextDays();
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        },lat, lng);
    }


    @Override
    public void onRefresh() {
        getForecast(city.lat1, city.lng1);
        binding.swipeRefreshLayout.setRefreshing(false);
    }


    private void configureNextDays() {
        binding.txtNextDays.setVisibility(View.VISIBLE);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        binding.recyclerView.setHasFixedSize(true);

        adapter = new AdapterNextDays(getDataDailyList());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }

    private ArrayList getDataDailyList() {
        ArrayList<Data> dataList = new ArrayList<>();
        for (Day day : Day.values()) {
            if (day != Day.DAY00){
                dataList.add(forecast.daily.data.get(day.getId()));
            }
        }
        return dataList;
    }
}
