package org.openliveview.device.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class AbstractCall extends AbstractMessage {
    /**
     * Header consists of two bytes and an int value (4 bytes).
     */
    private static final int HEADER_LENGTH = 6;

    public AbstractCall(byte id) {
        super(id);
    }

    protected abstract byte[] getPayload();

    public byte[] getEncoded() {
        byte[] payload = getPayload();
        int msgLength = payload.length + HEADER_LENGTH;
        ByteBuffer msgBuffer = ByteBuffer.allocate(msgLength);
        msgBuffer.order(ByteOrder.BIG_ENDIAN);
        msgBuffer.put(getId());
        msgBuffer.put((byte) 4);
        msgBuffer.putInt(payload.length);
        msgBuffer.put(payload);
        return msgBuffer.array();
    }
}
