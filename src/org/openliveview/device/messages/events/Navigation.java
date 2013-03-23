package org.openliveview.device.messages.events;

import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractEvent;
import org.openliveview.device.messages.MessageConstants;
import org.openliveview.util.Log;

public class Navigation extends AbstractEvent {

    private byte menuItemId;
    private byte navAction;
    private byte navType;
    private boolean inAlert;

    public byte getMenuItemId() {
        return menuItemId;
    }

    public byte getNavAction() {
        return navAction;
    }

    public byte getNavType() {
        return navType;
    }

    public boolean isInAlert() {
        return inAlert;
    }

    public Navigation() {
        super(MessageConstants.MSG_NAVIGATION);
    }

    @Override
    public void readData(ByteBuffer buffer) {
        if (buffer.get() != 0) {
            Log.w("First byte not 0");
        }
        if (buffer.get() != 3) {
            Log.w("Second byte not 3");
        }
        byte navigation = buffer.get();
        this.menuItemId = buffer.get();
        byte menuId = buffer.get();
        if (menuId != 10 && menuId != 20) {
            Log.w("Unexpected menuId: " + menuId);
        }
        if (navigation != 32 && ((navigation < 1) || (navigation > 15))) {
            Log.w("Navigation out of range: " + navigation);
        }
        this.inAlert = menuId == 20;

        if (navigation == 32) {
            this.navAction = MessageConstants.NAVACTION_PRESS;
            this.navType = MessageConstants.NAVTYPE_MENUSELECT;
        } else {
            this.navAction = (byte) ((navigation - 1) % 3);
            this.navType = (byte) ((navigation - 1) / 3);
        }
    }

    @Override
    public String toString() {
        return String.format("Navigation: Action=%d Type=%d MenuItem=%d %s",
                navAction, navType, menuItemId, inAlert ? "ALERT" : "");
    }
}
