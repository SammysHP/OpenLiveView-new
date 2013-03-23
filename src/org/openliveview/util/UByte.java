package org.openliveview.util;

public class UByte {

    private short value;

    public byte getValue() {
        return (byte) (value & 0xFF);
    }

    public void setValue(byte value) {
        this.value = (short) (value & 0xFF);
    }

    public UByte(byte value) {
        setValue(value);
    }

    @Override
    public String toString() {
        return Short.toString(value);
    }

}
