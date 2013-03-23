package org.openliveview.device.messages.calls;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;
import org.openliveview.util.UShort;

import android.graphics.Color;

public class SetLed extends AbstractCall {

    private final short color;
    private final UShort delayTime;
    private final UShort onTime;

    public SetLed(int color_in, int delayTime, int onTime) {
        super(MessageConstants.MSG_SETLED);
        this.color = colorToRGB565(color_in);
        this.delayTime = new UShort((short) delayTime);
        this.onTime = new UShort((short) onTime);
    }

    @Override
    protected byte[] getPayload() {
        ByteBuffer buffer = ByteBuffer.allocate(6); //3*2
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(color);
        buffer.putShort(delayTime.getValue());
        buffer.putShort(onTime.getValue());
        return buffer.array();
    }

    public static short colorToRGB565(int color) {
        int r = Color.red(color) >> 3;
        int g = Color.green(color) >> 2;
        int b = Color.blue(color) >> 3;
        return (short) ((r << (5 + 6)) + (g << 5) + (b));
    }

}
