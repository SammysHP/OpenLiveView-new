package org.openliveview.device.messages.calls;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class CapsRequest extends AbstractCall {

    public CapsRequest() {
        super(MessageConstants.MSG_GETCAPS);
    }

    @Override
    protected byte[] getPayload() {
        try {
            byte[] version = MessageConstants.CLIENT_SOFTWARE_VERSION.getBytes("iso-8859-1");
            byte msgLength = (byte) version.length;
            ByteBuffer buffer = ByteBuffer.allocate(msgLength + 1);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.put(msgLength);
            buffer.put(version);
            return buffer.array();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not found: " + e.getMessage(), e);
        }
    }
}
