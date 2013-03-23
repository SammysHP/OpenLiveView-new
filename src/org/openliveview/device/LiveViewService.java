package org.openliveview.device;

import org.openliveview.util.Log;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LiveViewService extends Service {
    public static final String ACTION_START = "start";
    public static final String ACTION_STOP = "stop";

    private LiveViewConnection connection;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        if (connection == null || !connection.isConnected()) {
            Log.d("LiveViewService started");
            
            connection = new LiveViewConnection(this);
            connection.connect();
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (connection != null && connection.isConnected()) {
            connection.disconnect();
        }
        
        Log.d("LiveViewService stopped");
    }
}
