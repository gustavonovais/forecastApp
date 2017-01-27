package com.arena.gustavonovais.challengearena.enums;

import android.graphics.drawable.Drawable;
import android.support.v7.content.res.AppCompatResources;

import com.arena.gustavonovais.challengearena.R;
import com.arena.gustavonovais.challengearena.utils.ChallengeArenaApplication;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gustavo on 17/09/2016.
 */
public enum IconEnum {
    CLEARDAY("clear-day", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.clear_day)),
    CLEAR_NIGHT("clear-night", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.clear_night)),
    RAIN("rain", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.rain)),
    SNOW("snow", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.snow)),
    SLEET("sleet", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.sleet)),
    WIND("wind", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.wind)),
    FOG("fog", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.fog)),
    CLOUDY("cloudy", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.cloud)),
    PARTLY_CLOUDY_DAY("partly-cloudy-day", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.partly_cloudy_day)),
    PARTLY_CLOUDY_NIGHT("partly-cloudy-night", AppCompatResources.getDrawable(ChallengeArenaApplication.getAppContext(), R.drawable.partly_cloudy_night));

    IconEnum(String descricao, Drawable icon) {
        this.descricao = descricao;
        this.icon = icon;
    }

    private String descricao;
    private Drawable icon;

    public Drawable getIcon() {
        return icon;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Drawable fromDesc(String desc){
        if(CLEARDAY.descricao.equalsIgnoreCase(desc)){
            return CLEARDAY.icon;
        }
        if(CLEAR_NIGHT.descricao.equalsIgnoreCase(desc)){
            return CLEAR_NIGHT.icon;
        }
        if(RAIN.descricao.equalsIgnoreCase(desc)){
            return RAIN.icon;
        }
        if(SNOW.descricao.equalsIgnoreCase(desc)){
            return SNOW.icon;
        }
        if(SLEET.descricao.equalsIgnoreCase(desc)){
            return SLEET.icon;
        }
        if(WIND.descricao.equalsIgnoreCase(desc)){
            return WIND.icon;
        }
        if(FOG.descricao.equalsIgnoreCase(desc)){
            return FOG.icon;
        }
        if(CLOUDY.descricao.equalsIgnoreCase(desc)){
            return CLOUDY.icon;
        }
        if(PARTLY_CLOUDY_DAY.descricao.equalsIgnoreCase(desc)){
            return PARTLY_CLOUDY_DAY.icon;
        }
        if(PARTLY_CLOUDY_NIGHT.descricao.equalsIgnoreCase(desc)){
            return PARTLY_CLOUDY_NIGHT.icon;
        }

        return null;
    }



}
