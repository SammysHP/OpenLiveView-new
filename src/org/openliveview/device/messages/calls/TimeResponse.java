package org.openliveview.device.messages.calls;

import java.nio.ByteBuffer;
import java.util.Calendar;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class TimeResponse extends AbstractCall {

    private int time;
    private byte mode;

    public TimeResponse(boolean mode) {
        super(MessageConstants.MSG_GETTIME_RESP);

        this.time = (int) (Calendar.getInstance().getTimeInMillis() / 1000);
        this.time += Calendar.getInstance().get(Calendar.ZONE_OFFSET) / 1000;
        this.time += Calendar.getInstance().get(Calendar.DST_OFFSET) / 1000;
        if (mode) {
            this.mode = 0;
        } else {
            this.mode = 1;
        }
    }

    @Override
    protected byte[] getPayload() {
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.putInt(time);
        buffer.put((byte) mode);
        return buffer.array();
    }

}
