package com.gustavonovais.forecast.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.gustavonovais.forecast.R;

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

    public static void openAutocompleteActivity(Activity activity) {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(activity);
            activity.startActivityForResult(intent, ActivityUtils.REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(activity, e.getConnectionStatusCode(),0 ).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message =  activity.getString(R.string.google_message_error) + GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Log.e(ParamKey.PLACE, message);
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        }
    }

}
