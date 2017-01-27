package com.arena.gustavonovais.challengearena.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.arena.gustavonovais.challengearena.HomeBinding;
import com.arena.gustavonovais.challengearena.R;
import com.arena.gustavonovais.challengearena.adapter.AdapterNavigation;
import com.arena.gustavonovais.challengearena.model.NavObject;
import com.arena.gustavonovais.challengearena.utils.ActivityUtils;

public class HomeActivity extends AppCompatActivity implements AdapterNavigation.OnItemSelectedListener, View.OnClickListener {

    private AdapterNavigation adapter;
    private HomeBinding homeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        setSupportActionBar(homeBinding.homeController.toolbar);
        configureDrawerAndToggle();
        configureNavigationView();
        configTxtAddCyty();
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
    public void onItemSelected(int position) {
        switch (position) {
            case 0:
                homeBinding.drawerLayout.closeDrawers();
                break;
            case 1:
                homeBinding.drawerLayout.closeDrawers();
                break;
            case 2:
                homeBinding.drawerLayout.closeDrawers();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtAddCity:
                NavObject navObject = new NavObject(R.drawable.ic_ab_drawer, "Teste 3");
                adapter = new AdapterNavigation(this, navObject);
                homeBinding.recyclerViewMenu.setAdapter(adapter);
                homeBinding.recyclerViewMenu.getAdapter().notifyDataSetChanged();
                break;
            default:
                break;
        }
    }


}
