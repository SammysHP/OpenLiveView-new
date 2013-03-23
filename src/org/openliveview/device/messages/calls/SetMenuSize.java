package org.openliveview.device.messages.calls;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class SetMenuSize extends AbstractCall {

    private final byte menuSize;

    public SetMenuSize(byte menuSize) {
        super(MessageConstants.MSG_SETMENUSIZE);
        this.menuSize = menuSize;
    }

    @Override
    protected byte[] getPayload() {
        return new byte[] {menuSize};
    }

}
