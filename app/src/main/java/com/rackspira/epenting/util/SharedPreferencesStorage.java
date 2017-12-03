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

    public void setAutoBackup(boolean mode){
        editor.putBoolean(Constants.AUTO_BACKUP, mode);
        editor.commit();
    }

    public void setAutoSend(boolean mode){
        editor.putBoolean(Constants.AUTO_SEND,mode);
        editor.commit();
    }

    public boolean getAutoSend(){
        return sharedPreferences.getBoolean(Constants.AUTO_SEND, false);
    }

    public boolean getAutoBackup(){
        return sharedPreferences.getBoolean(Constants.AUTO_BACKUP, false);
    }
    public String getEmail (){
        return sharedPreferences.getString(Constants.EMAIL_ORTU,null);
    }
    public void setEmail(String email){
        editor.putString(Constants.EMAIL_ORTU,email);
        editor.commit();
    }

}
