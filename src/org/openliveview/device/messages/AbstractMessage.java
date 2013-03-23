package org.openliveview.device.messages;

public abstract class AbstractMessage {
    private byte id;

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public AbstractMessage(byte id) {
        this.id = id;
    }
}
