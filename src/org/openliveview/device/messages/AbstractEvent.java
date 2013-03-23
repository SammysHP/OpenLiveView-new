package org.openliveview.device.messages;

import java.nio.ByteBuffer;

public abstract class AbstractEvent extends AbstractMessage {
    public AbstractEvent(byte id) {
        super(id);
    }

    public abstract void readData(ByteBuffer buffer);
}
