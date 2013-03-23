package org.openliveview.device.messages.events;

import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractEvent;
import org.openliveview.device.messages.MessageConstants;

public class DeviceStatusChange extends AbstractEvent {

    private DeviceStatus status;

    public DeviceStatus getStatus() {
        return status;
    }

    public DeviceStatusChange() {
        super(MessageConstants.MSG_DEVICESTATUS);
    }

    @Override
    public void readData(ByteBuffer buffer) {
        status = DeviceStatus.getByByte(buffer.get());
    }

    @Override
    public String toString() {
        return "DeviceStatus = " + status;
    }

    public enum DeviceStatus {
        OFF(MessageConstants.DEVICESTATUS_OFF),
        ON(MessageConstants.DEVICESTATUS_ON),
        MENU(MessageConstants.DEVICESTATUS_MENU),
        UNKNOWN((byte) -1);

        private final byte status;

        private DeviceStatus(final byte status) {
            this.status = status;
        }
        
        public static DeviceStatus getByByte(final byte status) {
            switch (status) {
                case MessageConstants.DEVICESTATUS_OFF:
                    return OFF;
                case MessageConstants.DEVICESTATUS_ON:
                    return ON;
                case MessageConstants.DEVICESTATUS_MENU:
                    return MENU;
                default:
                    return UNKNOWN;
            }
        }
    }
}
