package com.gustavonovais.forecast.utils;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

/**
 * Created by gustavo on 21/09/2016.
 */

public class ChallengeArenaApplication extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
    }


    public ChallengeArenaApplication() {
        context = this;
    }

    public static Context getAppContext(){
        return context;
    }

}
