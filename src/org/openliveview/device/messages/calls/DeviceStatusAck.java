package org.openliveview.device.messages.calls;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class DeviceStatusAck extends AbstractCall {

    public DeviceStatusAck() {
        super(MessageConstants.MSG_DEVICESTATUS_ACK);
    }

    protected byte[] getPayload() {
        return new byte[] {MessageConstants.RESULT_OK};
    }

}
