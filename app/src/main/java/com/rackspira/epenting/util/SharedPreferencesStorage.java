package com.rackspira.epenting.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.rackspira.epenting.common.Constants;

/**
 * Created by Yudis on 11/2/2017.
 */

public class SharedPreferencesStorage {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public SharedPreferencesStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getPin(){
        return sharedPreferences.getString(Constants.PIN,null);
    }
    public String getPinActive(){
        return sharedPreferences.getString(Constants.PIN_ACTIVE,null);
    }
}
