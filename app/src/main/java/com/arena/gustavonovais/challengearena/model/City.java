package com.arena.gustavonovais.challengearena.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.arena.gustavonovais.challengearena.enums.CityPreDefined;
import com.google.android.gms.location.places.Place;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GustavoNovais on 27/01/17.
 */

@Table(name = "CITY")
public class City extends Model implements Serializable{

    @Column(name = "name")
    public String name;

    @Column(name = "lat1")
    public double lat1;

    @Column(name = "lng1")
    public double lng1;

    @Column(name = "editable")
    public String editable;

    public static List<City> getAll() {
        return new Select()
                .from(City.class)
                .execute();
    }

    public static City getByName(String name) {
        return new Select()
                .from(City.class)
                .where("name = ?", name)
                .executeSingle();
    }

    public static void createNewCity(Place place){
        City city = new City();
        city.name = place.getName().toString();
        city.lat1 = place.getLatLng().latitude;
        city.lng1 = place.getLatLng().longitude;
        city.editable = "S";
        city.save();
    }

    public static void setCityPreDefined() {

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
}
