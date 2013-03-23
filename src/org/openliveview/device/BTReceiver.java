package org.openliveview.device;

import org.openliveview.R;
import org.openliveview.util.Log;
import org.openliveview.util.Preferences;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * This receiver listens for system broadcasts related to the bluetooth device
 * and controls the LiveView service.
 */
public class BTReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        final Preferences prefs = new Preferences(context);

        final String deviceAddress = prefs.getDeviceAddress();
        if (deviceAddress == null) {
            Log.w("No device configured");
            Toast.makeText(context, context.getString(R.string.device_not_configured), Toast.LENGTH_LONG).show();
            return;
        }
        Log.d("Device address: " + deviceAddress);

        final String action = intent.getAction();

        if (intent.getExtras() == null) {
            return;
        }

        Log.d("Received broadcast: " + action);

        final BluetoothDevice device = (BluetoothDevice) intent.getExtras().get(BluetoothDevice.EXTRA_DEVICE);
        if (device == null || !deviceAddress.equals(device.getAddress())) {
            return;
        }

        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            Log.d("Device connected");
            context.startService(new Intent(context, LiveViewService.class));
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)
                || BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            Log.d("Device disconnected");
            context.stopService(new Intent(context, LiveViewService.class));
        }
    }
}
