package com.arena.gustavonovais.challengearena.activities;

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

import com.arena.gustavonovais.challengearena.HomeBinding;
import com.arena.gustavonovais.challengearena.R;
import com.arena.gustavonovais.challengearena.activities.view.DailyForecastController;
import com.arena.gustavonovais.challengearena.adapter.AdapterNavigation;
import com.arena.gustavonovais.challengearena.enums.CityPreDefined;
import com.arena.gustavonovais.challengearena.model.City;
import com.arena.gustavonovais.challengearena.model.Forecast;
import com.arena.gustavonovais.challengearena.retrofit.ManagerRequest;
import com.arena.gustavonovais.challengearena.utils.ActivityUtils;
import com.arena.gustavonovais.challengearena.utils.ParamKey;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements AdapterNavigation.OnItemSelectedListener, View.OnClickListener {

    private AdapterNavigation adapter;
    private HomeBinding homeBinding;
    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        setCityPreDefined();

        setSupportActionBar(homeBinding.homeController.toolbar);
        configureDrawerAndToggle();
        configureNavigationView();
        configTxtAddCyty();
        setController(savedInstanceState);
    }


    public void configTxtAddCyty() {
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
            adapter = new AdapterNavigation(this);
            homeBinding.recyclerViewMenu.setAdapter(adapter);
            RelativeLayout.LayoutParams relative = (RelativeLayout.LayoutParams) homeBinding.recyclerViewMenu.getLayoutParams();
            relative.setMargins(0, ActivityUtils.getStatusBarHeight(this), 0, 0);
        }
    }

    @Override
    public void onItemSelected(int position, City city) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ParamKey.CITY, city);

        changeController(new DailyForecastController(bundle));
        homeBinding.drawerLayout.closeDrawers();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtAddCity:
                //inserir nova cidade e chamar o adapter novamente
                adapter = new AdapterNavigation(this);
                homeBinding.recyclerViewMenu.setAdapter(adapter);
                homeBinding.recyclerViewMenu.getAdapter().notifyDataSetChanged();
                break;
            default:
                break;
        }

    }

    public void setCityPreDefined() {
        if (City.getAll().isEmpty()){
            for (CityPreDefined cityPreDefined : CityPreDefined.values()) {
                City city = new City();
                city.editable = "N";
                city.name = cityPreDefined.getDescricao();
                city.lat1 = cityPreDefined.getLatLng().latitude;
                city.lng1 = cityPreDefined.getLatLng().longitude;
                city.save();
            }
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
        ViewGroup container = (ViewGroup)findViewById(R.id.controller_container);
        router = Conductor.attachRouter(this, container, savedInstanceState);

        if (!router.hasRootController()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ParamKey.CITY, City.getByName(CityPreDefined.DUBLIN.getDescricao()));

            Controller controller =  new DailyForecastController(bundle);
            router.setRoot(RouterTransaction.with(controller));
        }
    }

    public void changeController(Controller controller) {
        if (controller != null) {
            RouterTransaction transaction = RouterTransaction.with(controller);
            router.pushController(transaction);
        }
    }
}
