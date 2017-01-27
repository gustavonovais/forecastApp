package com.arena.gustavonovais.challengearena.enums;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gustavo on 17/09/2016.
 */
public enum CityPreDefined {
    DUBLIN("Dublin", new LatLng(53.3244431,-6.3857867)),
    LONDON("London", new LatLng(51.5287718,-0.2416814)),
    NEWYORK("New York", new LatLng(40.7058316,-74.2581886)),
    BARCELONA("Barcelona", new LatLng(41.3948976,2.0787279));

    CityPreDefined(String descricao, LatLng latLng) {
        this.descricao = descricao;
        this.latLng = latLng;
    }

    public String getDescricao() {
        return descricao;
    }

    private String descricao;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    private LatLng latLng;
}
