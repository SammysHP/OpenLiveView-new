package org.openliveview.device.messages.events;

import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractEvent;
import org.openliveview.device.messages.MessageConstants;

public class GetAlert extends AbstractEvent {

    private byte menuItemId;
    private byte alertAction;
    private short maxBodySize;

    public GetAlert() {
        super(MessageConstants.MSG_GETALERT);
    }

    public byte getMenuItemId() {
        return menuItemId;
    }

    public byte getAlertAction() {
        return alertAction;
    }

    public short getMaxBodySize() {
        return maxBodySize;
    }

    @Override
    public void readData(ByteBuffer buffer) {
        this.menuItemId = buffer.get();
        this.alertAction = buffer.get();
        this.maxBodySize = buffer.getShort();
    }

}
