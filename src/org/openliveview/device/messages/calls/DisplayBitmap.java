package org.openliveview.device.messages.calls;

import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class DisplayBitmap extends AbstractCall {

    private byte x;
    private byte y;
    private byte[] image;

    public DisplayBitmap(byte x, byte y, byte[] image) {
        super(MessageConstants.MSG_DISPLAYBITMAP);
        this.x = x;
        this.y = y;
        this.image = image;
    }

    @Override
    protected byte[] getPayload() {
        int size = 3 + image.length;
        ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.put((byte) x);
        buffer.put((byte) y);
        buffer.put((byte) 1);
        buffer.put(image);
        return buffer.array();
    }
}
