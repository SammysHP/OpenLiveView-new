package org.openliveview.device.messages.calls;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class NavigationResponse extends AbstractCall {

    private byte response;

    public NavigationResponse(byte response) {
        super(MessageConstants.MSG_NAVIGATION_RESP);
        this.response = response;
    }

    @Override
    protected byte[] getPayload() {
        return new byte[] {response};
    }

}
