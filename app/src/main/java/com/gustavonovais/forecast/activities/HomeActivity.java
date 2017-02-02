package com.gustavonovais.forecast.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arena.gustavonovais.forecast.HomeBinding;
import com.arena.gustavonovais.forecast.R;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.gustavonovais.forecast.activities.view.DailyForecastController;
import com.gustavonovais.forecast.adapter.AdapterNavigation;
import com.gustavonovais.forecast.enums.CityPreDefined;
import com.gustavonovais.forecast.model.City;
import com.gustavonovais.forecast.utils.ActivityUtils;
import com.gustavonovais.forecast.utils.ParamKey;

public class HomeActivity extends AppCompatActivity implements AdapterNavigation.OnItemSelectedListener, View.OnClickListener, AdapterNavigation.OnDeleteItemListener {

    private AdapterNavigation adapter;
    private HomeBinding homeBinding;
    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setSupportActionBar(homeBinding.homeController.toolbar);

        City.setCityPreDefined();
        configureDrawerAndToggle();
        configureNavigationView();
        configTxtAddCyty();
        setController(savedInstanceState);
    }


    private void configTxtAddCyty() {
        homeBinding.txtAddCity.setOnClickListener(this);
    }

    private void configureDrawerAndToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, homeBinding.drawerLayout, homeBinding.homeController.toolbar, R.string.app_name, R.string.app_name);
        toggle.syncState();
    }

    private void configureNavigationView() {
        homeBinding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        homeBinding.recyclerViewMenu.setHasFixedSize(true);

        if (homeBinding.navView != null) {
            adapter = new AdapterNavigation(this, this);
            homeBinding.recyclerViewMenu.setAdapter(adapter);
            RelativeLayout.LayoutParams relative = (RelativeLayout.LayoutParams) homeBinding.recyclerViewMenu.getLayoutParams();
            relative.setMargins(0, ActivityUtils.getStatusBarHeight(this), 0, 0);
        }
    }

    @Override
    public void onItemSelected(City city) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ParamKey.CITY, city);

        changeController(new DailyForecastController(bundle));
        homeBinding.drawerLayout.closeDrawers();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtAddCity:
                ActivityUtils.openAutocompleteActivity(this);
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (router != null && !router.handleBack()) {
            super.onBackPressed();
        }
    }

    private void setController(Bundle savedInstanceState) {
        ViewGroup container = (ViewGroup) findViewById(R.id.controller_container);
        router = Conductor.attachRouter(this, container, savedInstanceState);

        if (!router.hasRootController()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ParamKey.CITY, City.getByName(CityPreDefined.DUBLIN.getDescricao()));

            Controller controller = new DailyForecastController(bundle);
            router.setRoot(RouterTransaction.with(controller));
        }
    }

    private void changeController(Controller controller) {
        if (controller != null) {
            RouterTransaction transaction = RouterTransaction.with(controller);
            router.pushController(transaction);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivityUtils.REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (place != null) {
                    validateCity(place);
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e(ParamKey.PLACE, getString(R.string.error_status) + status.toString());
            }
        }
    }

    private void validateCity(Place place) {
        City city = City.getByName(place.getName().toString());
        if (city != null) {
            LatLng latLng = new LatLng(city.lat1, city.lng1);
            if (latLng.equals(place.getLatLng())) {
                Toast.makeText(this, R.string.city_added, Toast.LENGTH_LONG).show();
            } else {
                City.createNewCity(place);
                refreshMenuCities();
            }
        } else {
            City.createNewCity(place);
            refreshMenuCities();
        }
    }

    private void refreshMenuCities() {
        adapter = new AdapterNavigation(this, this);
        homeBinding.recyclerViewMenu.setAdapter(adapter);
        homeBinding.recyclerViewMenu.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void OnDeleteItemListener(City city) {
        city.delete();
        refreshMenuCities();
    }
}
