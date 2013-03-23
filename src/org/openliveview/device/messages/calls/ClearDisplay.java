package org.openliveview.device.messages.calls;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class ClearDisplay extends AbstractCall {

    public ClearDisplay() {
        super(MessageConstants.MSG_CLEARDISPLAY);
    }

    @Override
    protected byte[] getPayload() {
        return new byte[] {0};
    }

}
