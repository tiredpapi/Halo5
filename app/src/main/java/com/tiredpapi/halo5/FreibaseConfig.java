package com.tiredpapi.halo5;

import android.app.Application;

/**
 * Created by mmacel on 10/24/2016.
 */
public class FreibaseConfig extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
