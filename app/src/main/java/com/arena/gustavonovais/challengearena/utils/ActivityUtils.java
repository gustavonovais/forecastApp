package com.arena.gustavonovais.challengearena.utils;

import android.app.Activity;

/**
 * Created by GustavoNovais on 27/01/17.
 */

public class ActivityUtils {

    public static final String FAHRENHEIT = " ℉";
    public static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
