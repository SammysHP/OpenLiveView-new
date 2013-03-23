package org.openliveview.device.messages.events;

import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractEvent;
import org.openliveview.device.messages.MessageConstants;

public class GetMenuItems extends AbstractEvent {

    public GetMenuItems() {
        super(MessageConstants.MSG_GETMENUITEMS);
    }

    @Override
    public void readData(ByteBuffer buffer) {
    }

}
