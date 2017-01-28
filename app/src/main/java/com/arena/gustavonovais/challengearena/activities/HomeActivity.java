package com.arena.gustavonovais.challengearena.activities;

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

import com.arena.gustavonovais.challengearena.HomeBinding;
import com.arena.gustavonovais.challengearena.R;
import com.arena.gustavonovais.challengearena.activities.view.DailyForecastController;
import com.arena.gustavonovais.challengearena.adapter.AdapterNavigation;
import com.arena.gustavonovais.challengearena.enums.CityPreDefined;
import com.arena.gustavonovais.challengearena.model.City;
import com.arena.gustavonovais.challengearena.utils.ActivityUtils;
import com.arena.gustavonovais.challengearena.utils.ParamKey;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

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
                openAutocompleteActivity();
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
        ViewGroup container = (ViewGroup)findViewById(R.id.controller_container);
        router = Conductor.attachRouter(this, container, savedInstanceState);

        if (!router.hasRootController()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ParamKey.CITY, City.getByName(CityPreDefined.DUBLIN.getDescricao()));

            Controller controller =  new DailyForecastController(bundle);
            router.setRoot(RouterTransaction.with(controller));
        }
    }

    private void changeController(Controller controller) {
        if (controller != null) {
            RouterTransaction transaction = RouterTransaction.with(controller);
            router.pushController(transaction);
        }
    }


    private void openAutocompleteActivity() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
            startActivityForResult(intent, ActivityUtils.REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),0 ).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = getString(R.string.google_message_error) + GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Log.e(ParamKey.PLACE, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivityUtils.REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                City.createNewCity(place);
                refreshMenuCities();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e(ParamKey.PLACE, getString(R.string.error_status) + status.toString());
            }
        }
    }

    private void refreshMenuCities(){
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
