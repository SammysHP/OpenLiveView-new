package org.openliveview.device.messages.events;

import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractEvent;

public class ResultEvent extends AbstractEvent {

    private byte result;

    public byte getResult() {
        return result;
    }

    public ResultEvent(byte id) {
        super(id);
    }

    @Override
    public void readData(ByteBuffer buffer) {
        result = buffer.get();
    }

}
