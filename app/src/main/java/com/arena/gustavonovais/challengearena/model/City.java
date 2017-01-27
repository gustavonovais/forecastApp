package com.arena.gustavonovais.challengearena.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

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
}
