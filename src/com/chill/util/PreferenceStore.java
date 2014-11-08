package com.chill.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceStore {
    private static final String PREFERENCE_KEY_FILE = "chill_shared_prefs";
    private final SharedPreferences mSharedPreferences;

    public PreferenceStore (final Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_KEY_FILE, Context.MODE_PRIVATE);
    }

    public void put (final String key, final String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String get (final String key) {
        return mSharedPreferences.getString(key, "");
    }

    public boolean hasKey(final String key) {
        final String value = mSharedPreferences.getString(key, null);
        if(value == null) {
            return false;
        }
        return true;
    }
}
