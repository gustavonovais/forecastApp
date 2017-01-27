package com.arena.gustavonovais.challengearena.model;

import com.arena.gustavonovais.challengearena.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GustavoNovais on 27/01/17.
 */

public class NavObject {

    private int icon;
    private String title;

    public NavObject(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public NavObject() {
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public List<NavObject> getList() {
        List<NavObject> list = new ArrayList<>();
        list.add(new NavObject(R.drawable.ic_ab_drawer, "Teste 1"));
        list.add(new NavObject(R.drawable.ic_ab_drawer, "Teste 2"));
        list.add(new NavObject(R.drawable.ic_ab_drawer, "Teste 3"));
        return list;
    }
}