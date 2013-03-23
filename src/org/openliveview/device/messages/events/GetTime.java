package org.openliveview.device.messages.events;

import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractEvent;
import org.openliveview.device.messages.MessageConstants;

public class GetTime extends AbstractEvent {

    public GetTime() {
        super(MessageConstants.MSG_GETTIME);
    }

    @Override
    public void readData(ByteBuffer buffer) {
    }

}
