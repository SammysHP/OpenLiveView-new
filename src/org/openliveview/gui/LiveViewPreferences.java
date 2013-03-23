package org.openliveview.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openliveview.R;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

public class LiveViewPreferences extends PreferenceActivity {
    private BluetoothAdapter btAdapter;
    private ListPreference devicePreference;

    // To support android 2.x the old method is still used.
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btAdapter = BluetoothAdapter.getDefaultAdapter();

        addPreferencesFromResource(R.xml.preferences);

        devicePreference = (ListPreference) findPreference(getString(R.string.prefkeys_device_address));
    }

    @Override
    protected void onResume() {
        super.onResume();

        fillDevices();
    }

    /**
     * Get the list of paired devices from the system and fill the preference list.
     */
    private void fillDevices() {
        final Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
        
        final List<String> names = new ArrayList<String>();
        final List<String> addresses = new ArrayList<String>();
        
        for (BluetoothDevice dev : devices) {
            names.add(dev.getName());
            addresses.add(dev.getAddress());
        }
        
        devicePreference.setEntries(names.toArray(new String[0]));
        devicePreference.setEntryValues(addresses.toArray(new String[0]));
    }
}
