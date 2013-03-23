package org.openliveview.device;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openliveview.device.messages.AbstractEvent;
import org.openliveview.device.messages.MessageConstants;
import org.openliveview.device.messages.calls.DeviceStatusAck;
import org.openliveview.device.messages.calls.GetAlertResponse;
import org.openliveview.device.messages.calls.GetTimeResponse;
import org.openliveview.device.messages.calls.MenuItem;
import org.openliveview.device.messages.calls.NavigationResponse;
import org.openliveview.device.messages.calls.SetMenuSettings;
import org.openliveview.device.messages.calls.SetMenuSize;
import org.openliveview.device.messages.calls.SetVibrate;
import org.openliveview.device.messages.events.CapsResponse;
import org.openliveview.device.messages.events.DeviceStatusChange;
import org.openliveview.device.messages.events.DeviceStatusChange.DeviceStatus;
import org.openliveview.device.messages.events.GetAlert;
import org.openliveview.util.Log;
import org.openliveview.util.UShort;

import android.content.Context;

public class EventDispatcher {

    private final byte[] IMG_MENU_NOTIFICATION;
    private final byte[] IMG_ANNOUNCE_GENERIC;

    private final LiveViewConnection connection;

    private State state;
    private DeviceStatus deviceStatus;

    public EventDispatcher(final LiveViewConnection connection) {
        this.connection = connection;
        this.state = State.MENU; // after connection we're in the menu
        this.deviceStatus = DeviceStatus.UNKNOWN; // the device should be in ON-state, but we're not sure

        IMG_MENU_NOTIFICATION = loadImageByteArray(connection.service, "menu_notification.png");
        IMG_ANNOUNCE_GENERIC = loadImageByteArray(connection.service, "announce_generic.png");
    }

    public void dispatchEvent(final AbstractEvent event) throws IOException {
        if (event == null) {
            return;
        }

        /*
         * TODO:
         * 
         * Implement messages. Warning, not all messages can be received.
         * 
         * MSG_GETCAPS
         * MSG_DISPLAYTEXT
         * MSG_DISPLAYTEXT_ACK
         * MSG_DISPLAYPANEL
         * MSG_DEVICESTATUS_ACK
         * MSG_DISPLAYBITMAP
         * MSG_CLEARDISPLAY
         * MSG_CLEARDISPLAY_ACK
         * MSG_SETMENUSIZE
         * MSG_SETMENUSIZE_ACK
         * MSG_GETMENUITEM
         * MSG_GETMENUITEM_RESP
         * MSG_GETALERT_RESP
         * MSG_NAVIGATION_RESP
         * MSG_SETSTATUSBAR
         * MSG_SETSTATUSBAR_ACK
         * MSG_SETMENUSETTINGS
         * MSG_SETMENUSETTINGS_ACK
         * MSG_GETTIME_RESP
         * MSG_SETLED
         * MSG_SETVIBRATE
         * MSG_ACK
         * MSG_SETSCREENMODE
         * MSG_SETSCREENMODE_ACK
         * MSG_GETSCREENMODE
         * MSG_GETSCREENMODE_RESP
         */
        switch (event.getId()) {
            case MessageConstants.MSG_GETCAPS_RESP:
                final CapsResponse caps = (CapsResponse) event;
                // TODO: set menu
                connection.sendCall(new SetMenuSettings(0, 0));
                connection.sendCall(new SetMenuSize((byte) 1));
                connection.sendCall(new SetVibrate(0, SetVibrate.NORMAL));
                break;

            case MessageConstants.MSG_GETTIME:
                connection.sendCall(new GetTimeResponse(true));
                break;

            case MessageConstants.MSG_DEVICESTATUS:
                final DeviceStatusChange statusChange = (DeviceStatusChange) event;
                connection.sendCall(new DeviceStatusAck());
                deviceStatus = statusChange.getStatus();
                break;

            case MessageConstants.MSG_SETVIBRATE_ACK:
                // nothing
                break;

            case MessageConstants.MSG_SETLED_ACK:
                // nothing
                break;

            case MessageConstants.MSG_DISPLAYBITMAP_ACK:
                // nothing
                break;

            case MessageConstants.MSG_DISPLAYPANEL_ACK:
                // nothing
                break;

            case MessageConstants.MSG_GETMENUITEMS:
                // TODO send menu items
                connection.sendCall(new MenuItem((byte) 0, true, new UShort((short) 0), "All notifications", IMG_MENU_NOTIFICATION));
                break;

            case MessageConstants.MSG_GETALERT:
                final GetAlert alert = (GetAlert) event;
                // TODO implement alerts
                connection.sendCall(new SetVibrate(0, SetVibrate.NORMAL));
                connection.sendCall(new GetAlertResponse(0, 0, 0, "", "No notifications", "", IMG_ANNOUNCE_GENERIC));
                break;

            case MessageConstants.MSG_NAVIGATION:
                connection.sendCall(new NavigationResponse(MessageConstants.RESULT_CANCEL));
                break;

            default:
                Log.e("Unknown event (" + event.getId() + ")");
                connection.sendCall(new NavigationResponse(MessageConstants.RESULT_CANCEL)); // TODO
                break;
        }
    }

    private enum State {
        MENU, MEDIA, PANEL;
    }

    /**
     * Return byte array for the supplied image file
     * 
     * @param context
     * @param imageFileName
     *            the filename of the image in /assets
     * @return byte[] for the image file or empty if image not found
     */
    private byte[] loadImageByteArray(final Context context, final String imageFileName) {
        try {
            final InputStream stream = context.getAssets().open(imageFileName);
            final ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
            final byte[] buffer = new byte[1024];
            while (stream.available() > 0) {
                final int read = stream.read(buffer);
                arrayStream.write(buffer, 0, read);
            }
            stream.close();
            return arrayStream.toByteArray();
        } catch (IOException e) {
            Log.e("Icon not found: " + imageFileName);
        }

        return new byte[] {};
    }

}
