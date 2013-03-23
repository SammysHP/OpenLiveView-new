package org.openliveview.device;

import java.io.IOException;
import java.util.UUID;

import org.openliveview.R;
import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.AbstractEvent;
import org.openliveview.device.messages.DecodeException;
import org.openliveview.device.messages.calls.CapsRequest;
import org.openliveview.device.messages.calls.MessageAck;
import org.openliveview.gui.LiveViewPreferences;
import org.openliveview.util.Log;

import android.app.Notification;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class LiveViewConnection extends Thread {

    private static final int NOTIFICATION_ID_SERVICE = 1;
    private static final UUID SERIAL = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public final LiveViewService service;
    private final BluetoothAdapter btAdapter;
    private BluetoothSocket clientSocket;

    private boolean shouldStop = false;

    public LiveViewConnection(final LiveViewService service) {
        this.service = service;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void connect() {
        start();
    }

    public void disconnect() {
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                // nothing to do
            }
        }
        shouldStop = true;
    }

    public boolean isConnected() {
        return isAlive();
    }

    @Override
    public void run() {
        Log.d("Connection thread started");

        // Pending intent for preferences
        final Intent notificationIntent = new Intent(service, LiveViewPreferences.class);
        final PendingIntent preferencesIntent = PendingIntent.getActivity(service, 0, notificationIntent, 0);

        // Create notification and set service to foreground
        final Notification notification = new NotificationCompat.Builder(service)
                .setContentTitle(service.getString(R.string.liveview_connected))
                .setContentText(service.getString(R.string.touch_for_configuration))
                .setSmallIcon(R.drawable.olv_icon)
                .setContentIntent(preferencesIntent)
                .setOngoing(true)
                .setWhen(0)
                .build();
        service.startForeground(NOTIFICATION_ID_SERVICE, notification);

        BluetoothServerSocket serverSocket = null;
        EventReader eventReader = null;

        // Establish connection to LiveView
        try {
            serverSocket = btAdapter.listenUsingRfcommWithServiceRecord("LiveView", SERIAL);

            clientSocket = serverSocket.accept();
            eventReader = new EventReader(clientSocket.getInputStream());

            // Single connect only
            serverSocket.close();
            serverSocket = null;

            Log.d("LiveView connection established");

            final EventDispatcher dispatcher = new EventDispatcher(this);

            sendCall(new CapsRequest());

            // TODO device_status = 2; //Bugfix: after connecting device is in menu state.

            while (!shouldStop) {
                try {
                    AbstractEvent event = eventReader.readMessage();
                    sendCall(new MessageAck(event.getId()));
                    Log.d("Got message: " + event);
                    dispatcher.dispatchEvent(event);
                } catch (DecodeException e) {
                    Log.e("Error decoding message", e);
                }
            }
        } catch (IOException e) {
            Log.e("Error communicating with LV", e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    serverSocket = null;
                } catch (IOException e) {
                    // nothing to do
                }
            }
            if (eventReader != null) {
                try {
                    eventReader.close();
                    eventReader = null;
                } catch (IOException e) {
                    // nothing to do
                }
            }
        }

        // Remove notification, set service to background and stop it
        service.stopForeground(true);
        service.stopSelf();

        Log.d("Connection thread stopped");
    }

    /**
     * Send a message to the LiveView device if one is connected.
     * 
     * @param call
     *            {@link AbstractCall} to send to device.
     * @throws IOException
     *             If the message could not be sent successfully.
     */
    public void sendCall(AbstractCall call) throws IOException {
        if (clientSocket == null) {
            throw new IOException("No client connected");
        } else {
            clientSocket.getOutputStream().write(call.getEncoded());
        }
    }
}
