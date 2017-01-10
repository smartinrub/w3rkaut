package net.dynu.w3rkaut.storage.session;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String PREF_NAME = "net.dynu.w3rkaut.PREF_NAME";
    private static final String KEY_VALUE = "net.dynu.w3rkaut.KEY_VALUE";

    private static SharedPreferencesManager instance;
    private final SharedPreferences preferences;

    private SharedPreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context
                .MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    public void setValue(long value) {
        preferences.edit()
                .putLong(KEY_VALUE, value)
                .apply();
    }

    public long getValue() {
        return preferences.getLong(KEY_VALUE, 0);
    }

    public void remove(String key) {
        preferences.edit()
                .remove(key)
                .apply();
    }

    public boolean clear() {
        return preferences.edit()
                .clear()
                .commit();
    }


}
