package org.openliveview.util;

import org.openliveview.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Preferences {
    private final SharedPreferences preferences;
    private final Context context;

    public Preferences(final Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getDeviceAddress() {
        return preferences.getString(context.getString(R.string.prefkeys_device_address), null);
    }

    public void setDeviceAddress(String address) {
        Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.prefkeys_device_address), address);
        editor.commit();
    }
}
