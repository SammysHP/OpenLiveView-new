package org.openliveview.device.messages.calls;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;
import org.openliveview.util.UShort;

public class MenuItem extends AbstractCall {

    private byte itemId;
    private boolean alertItem;
    private UShort unreadCount;
    private String text;
    private byte[] image;

    public MenuItem(byte itemId, boolean alertItem, UShort unreadCount, String text, byte[] image) {
        super(MessageConstants.MSG_GETMENUITEM_RESP);
        this.itemId = itemId;
        this.alertItem = alertItem;
        this.unreadCount = unreadCount;
        this.text = text;
        this.image = image;
    }

    @Override
    protected byte[] getPayload() {
        try {
            byte[] textArray = text.getBytes("iso-8859-1");
            int size = 15 + textArray.length + image.length;
            ByteBuffer buffer = ByteBuffer.allocate(size);
            buffer.put((byte) (alertItem ? 0 : 1));
            buffer.putShort((short) 0);
            buffer.putShort(unreadCount.getValue());
            buffer.putShort((short) 0);
            buffer.put((byte) (itemId + 3));
            buffer.put((byte) 0);
            buffer.putShort((short) 0);
            buffer.putShort((short) 0);
            buffer.putShort((short) textArray.length);
            buffer.put(textArray);
            buffer.put(image);
            return buffer.array();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not found: " + e.getMessage(), e);
        }
    }
}
