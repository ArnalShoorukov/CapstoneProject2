package com.mosquefinder.arnal.prayertimesapp.sync;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class TimesSyncIntentService extends IntentService {


    public TimesSyncIntentService() {
        super("TimesSyncIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        TimesSyncTask.syncWeather(this);
    }


}
