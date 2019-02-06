package it.univaq.mobileprogramming;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * MobileProgramming2018
 * Created by leonardo on 09/11/2018.
 */
public class Settings {

    public static void save(Context context, String key, long value){

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long loadLong(Context context, String key, long fallback){

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getLong(key, fallback);
    }
}
