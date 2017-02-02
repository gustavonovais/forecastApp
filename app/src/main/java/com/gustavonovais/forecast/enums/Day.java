package com.gustavonovais.forecast.enums;

/**
 * Created by leandro.vella on 05/11/16.
 */

public enum Day {

    DAY00(0),
    DAY01(1),
    DAY02(2),
    DAY03(3),
    DAY04(4),
    DAY05(5);

    public int getId() {
        return id;
    }

    private final int id;

    Day(int id) {
        this.id = id;
    }


}
