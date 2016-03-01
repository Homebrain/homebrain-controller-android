package com.johan.homebraincontroller;

import android.app.Application;

/**
 * Created by johan on 9/15/15.
 */
public class ApplicationController extends Application {
    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static ApplicationController sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // initialize the singleton
        sInstance = this;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance() {
        if (sInstance == null){
            sInstance = new ApplicationController();
        }
        return sInstance;
    }
}
