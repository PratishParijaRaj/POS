package com.example.pratishparija.pos.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferense {
    private static final String LOGIN_STATUS = "is_login";
    public static final String DEFAULT_VALUE = "null";
    private static final String PREFERENCE_NAME = "MY_PREFERENCE";
    private static final String USER_ID = "user_id";
    private static SharedPreferences mPreferences;

    public static void init(Context context) {
        if (mPreferences == null) {
            mPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
    }

    public static String getLoginStatus() {
        return mPreferences.getString(LOGIN_STATUS, DEFAULT_VALUE);
    }


    public static void setLoginStatus(String value) {
        SharedPreferences.Editor _editor = mPreferences.edit();
        _editor.putString(LOGIN_STATUS, value);
        _editor.commit();
        _editor = null;
    }

    public static String getUserId() {
        return mPreferences.getString(USER_ID, DEFAULT_VALUE);
    }

    public static void setUserId(String value) {
        SharedPreferences.Editor _editor = mPreferences.edit();
        _editor.putString(USER_ID, value);
        _editor.commit();
        _editor = null;
    }

    public static void clearAll() {
        mPreferences.edit().clear().apply();
    }
}
