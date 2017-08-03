package com.mosquefinder.arnal.prayertimesapp.sync;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by arnal on 7/16/17.
 */

public class TimesSyncUtils {


    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, TimesSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}
