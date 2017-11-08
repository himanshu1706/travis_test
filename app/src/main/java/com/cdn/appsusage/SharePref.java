package com.cdn.appsusage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by himanshurathore on 23/8/17.
 */

public class SharePref {

    public static String PREFS_NAME = "APP_USAGE_Share_Pref";
    private static SharePref instance = null;
    private SharedPreferences sPrefs = null;
    private SharedPreferences.Editor editor = null;

    public static String PREVIOUS_APP = "PREVIOUS_APP";
    public static String CURRENT_APP = "CURRENT_APP";
    public static String CURRENT_APP_START_TIME = "CURRENT_APP_START_TIME";
    public static String CURRENT_APP_STOP_TIME = "CURRENT_APP_STOP_TIME";

    /**
     * this constructor use to initilize an shareprefrence
     *
     * @param context activity context
     */
    public SharePref(Context context) {
        sPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Get an Object of shareprefrence class
     *
     * @param context activity context
     */
    public static SharePref getInstance(Context context) {
        if (instance == null) {
            instance = new SharePref(context);
        }
        return instance;
    }

    public static void setInstance(SharePref instance) {
        SharePref.instance = instance;
    }

    /**
     * read String value from share pref
     *
     * @param prefName name of pref which we use to save it
     * @return int
     */
    public String readStringPrefs(String prefName) {
        return sPrefs.getString(prefName, "");
    }

    /**
     * write String value in share pref66
     *
     * @param prefName  name of pref value to use this
     * @param prefValue value of pref value which store
     */
    public void writeStringPrefs(String prefName, String prefValue) {
        editor = sPrefs.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }

    /**
     * read boolean value from share pref
     *
     * @param prefName name of pref which we use to save it
     * @return int
     */
    public boolean readBooleanPrefs(String prefName) {
        return sPrefs.getBoolean(prefName, false);
    }

    /**
     * write boolean value in share pref
     *
     * @param prefName  name of pref value to use this
     * @param prefValue value of pref value which store
     */
    public void writeBooleanPrefs(String prefName, boolean prefValue) {
        editor = sPrefs.edit();
        editor.putBoolean(prefName, prefValue);
        editor.apply();
    }

    /**
     * read float value from share pref
     *
     * @param prefName name of pref which we use to save it
     * @return int
     */
    public float readFloatPrefs(String prefName) {
        return sPrefs.getFloat(prefName, 0);
    }

    /**
     * write float value in share pref
     *
     * @param prefName  name of pref value to use this
     * @param prefValue value of pref value which store
     */
    public void writeFloatPrefs(String prefName, float prefValue) {
        editor = sPrefs.edit();
        editor.putFloat(prefName, prefValue);
        editor.apply();
    }


    /**
     * read long value from share pref
     *
     * @param prefName name of pref which we use to save it
     * @return long
     */
    public long readLongPrefs(String prefName) {
        return sPrefs.getLong(prefName, 0);
    }

    /**
     * write float value in share pref
     *
     * @param prefName  name of pref value to use this
     * @param prefValue value of pref value which store
     */
    public void writeLongPrefs(String prefName, long prefValue) {
        editor = sPrefs.edit();
        editor.putLong(prefName, prefValue);
        editor.apply();
    }


    /**
     * read int value from share pref
     *
     * @param prefName name of pref which we use to save it
     * @return int
     */
    public int readIntPrefs(String prefName) {
        return sPrefs.getInt(prefName, 0);
    }

    /**
     * write int value in share pref
     *
     * @param prefName  name of pref value to use this
     * @param prefValue value of pref value which store
     */
    public void writeIntPrefs(String prefName, int prefValue) {
        editor = sPrefs.edit();
        editor.putInt(prefName, prefValue);
        editor.apply();
    }

    public void clearAllData() {
        editor = sPrefs.edit();
        editor.clear();
        editor.apply();
    }
}
