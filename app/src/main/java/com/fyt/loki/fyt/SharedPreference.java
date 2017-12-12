package com.fyt.loki.fyt;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ergas on 12/10/2017.
 */

public class SharedPreference {
    public static final String PREFS_NAME="USERINFO";
    public static final String PREFS_KEY1="NAME";
    public static final String PREFS_KEY2="PASSWORD";
    public static final String PREFS_KEY3="TOKEN";

    public SharedPreference(){
        super();
    }

    public  void save (Context context,String username,String password,String token){
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor=settings.edit();
        editor.putString(PREFS_KEY1,username);
        editor.putString(PREFS_KEY2,password);
        editor.putString(PREFS_KEY3,token);
        editor.apply();

    }

    public String getUserName(Context context){
        SharedPreferences settings;
        String username;
        settings=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        username=settings.getString(PREFS_KEY1,null);
        return username;
    }
    public String getPassword(Context context){
        SharedPreferences settings;
        String password;
        settings= context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        password=settings.getString(PREFS_KEY2,null);
        return password;
    }
    public String getToken(Context context){
        SharedPreferences settings;
        String token;
        settings= context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        token=settings.getString(PREFS_KEY3,null);
        return token;
    }
    public  void clearSharedPreferences(Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor=settings.edit();
        editor.clear();
        editor.apply();
    }
    public void removeValues(Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings=context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor=settings.edit();
        editor.remove(PREFS_KEY1);
        editor.remove(PREFS_KEY2);
        editor.remove(PREFS_KEY3);
        editor.apply();
    }


}
