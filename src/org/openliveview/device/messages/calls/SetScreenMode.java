package org.openliveview.device.messages.calls;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class SetScreenMode extends AbstractCall {
	
	private final byte brightness;
	
    public SetScreenMode(byte brightness) {
        super(MessageConstants.MSG_SETSCREENMODE);
        this.brightness = brightness;
    }

    @Override
    protected byte[] getPayload() {
        return new byte[] { brightness };
    }

}
