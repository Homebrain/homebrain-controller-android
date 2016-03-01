package com.johan.homebraincontroller;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsController {
    private static SettingsController sInstance = null;

    SharedPreferences preferences;
    final String defaultIpPort = "192.168.1.2:5600";

    private SettingsController(){
        preferences = ApplicationController.getInstance().getSharedPreferences("general", Context.MODE_PRIVATE);
        preferences.getString("ipport", defaultIpPort);
    }

    static public SettingsController getInstance(){
        if (sInstance == null)
            sInstance = new SettingsController();
        return sInstance;
    }

    public String getIpPort(){
        return preferences.getString("ipport", defaultIpPort);
    }

    public void setIpPort(String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ipport", value);
        editor.apply();
    }
}
