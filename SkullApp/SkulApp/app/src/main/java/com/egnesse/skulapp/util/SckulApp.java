package com.egnesse.skulapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.egnesse.skulapp.dto.SessionDTO;
import com.google.gson.Gson;

/**
 * Created by adityaagrawal on 09/02/16.
 */
public class SckulApp {

    public static String SESSION = "session";

    public static SharedPreferences getSharedPreferences(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences;
    }


    public static SharedPreferences.Editor getSharedEditor(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor;
    }

    public static SessionDTO getSession(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String sessionJSON = sharedPreferences.getString(SESSION, null);
        SessionDTO sessionDTO;
        if(null != sessionJSON)
            sessionDTO = new Gson().fromJson(sessionJSON, SessionDTO.class);
        else {
            sessionDTO = new SessionDTO();
        }
        return sessionDTO;
    }


}
