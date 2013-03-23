package org.openliveview.device.messages.calls;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class SetMenuSettings extends AbstractCall {

    /**
     * approximately 100 ms/unit
     */
    private final byte vibrationTime;
    private final byte initialMenuItemId;

    public SetMenuSettings(int vibrationTime, int initialMenuItemId) {
        super(MessageConstants.MSG_SETMENUSETTINGS);
        this.vibrationTime = (byte) vibrationTime;
        this.initialMenuItemId = (byte) initialMenuItemId;
    }

    @Override
    protected byte[] getPayload() {
        ByteBuffer buffer = ByteBuffer.allocate(3);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put(vibrationTime);
        buffer.put((byte) 12);
        buffer.put(initialMenuItemId);
        return buffer.array();
    }

}
