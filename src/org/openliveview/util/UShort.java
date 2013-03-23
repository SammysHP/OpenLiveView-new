package org.openliveview.util;

public class UShort {

    private int value;

    public short getValue() {
        return (short) (value & 0xFFFF);
    }

    public void setValue(short value) {
        this.value = (int) (value & 0xFFFF);
    }

    public UShort(short value) {
        setValue(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
