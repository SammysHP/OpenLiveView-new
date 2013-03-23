package org.openliveview.device.messages.calls;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;
import org.openliveview.util.UShort;

public class Vibrate extends AbstractCall {

    public static final int SHORT = 20;
    public static final int NORMAL = 50;
    public static final int LONG = 100;
    public static final int EXTRALONG = 500;

    private final UShort delay;
    private final UShort time;

    public Vibrate(int delay, int time) {
        super(MessageConstants.MSG_SETVIBRATE);
        this.delay = new UShort((short) delay);
        this.time = new UShort((short) time);
    }

    @Override
    protected byte[] getPayload() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(delay.getValue());
        buffer.putShort(time.getValue());
        return buffer.array();
    }
}
