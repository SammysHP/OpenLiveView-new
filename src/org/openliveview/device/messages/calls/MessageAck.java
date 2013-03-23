package org.openliveview.device.messages.calls;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class MessageAck extends AbstractCall {

    private final byte ackMsgId;

    public MessageAck(byte ackMsgId) {
        super(MessageConstants.MSG_ACK);
        this.ackMsgId = ackMsgId;
    }

    @Override
    protected byte[] getPayload() {
        return new byte[] {ackMsgId};
    }

}
