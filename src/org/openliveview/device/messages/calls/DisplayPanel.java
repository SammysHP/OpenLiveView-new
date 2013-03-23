package org.openliveview.device.messages.calls;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractCall;
import org.openliveview.device.messages.MessageConstants;

public class DisplayPanel extends AbstractCall {

    private byte[] image;
    private String headerText;
    private String footerText;
    private boolean alertUser;

    public DisplayPanel(String headerText, String footerText, byte[] image, boolean alertUser) {
        super(MessageConstants.MSG_DISPLAYPANEL);
        this.image = image;
        this.headerText = headerText;
        this.footerText = footerText;
        this.alertUser = alertUser;
    }

    @Override
    protected byte[] getPayload() {
        try {
            byte[] headerTextArray = headerText == null ? new byte[0] : headerText.getBytes("iso-8859-1"); //UTF-8 is not working
            byte[] footerTextArray = footerText == null ? new byte[0] : footerText.getBytes("iso-8859-1");
            int size = 15 + headerTextArray.length + footerTextArray.length + image.length;
            ByteBuffer buffer = ByteBuffer.allocate(size);
            buffer.put((byte) 0);
            buffer.putShort((short) 0);
            buffer.putShort((short) 0);
            buffer.putShort((short) 0);
            if (alertUser)
            {
                buffer.put((byte) 80); //id (alert)
            }
            else
            {
                buffer.put((byte) 81); //id (no alert)
            }
            buffer.put((byte) 0); //0 is for plaintext vs bitmapimage (1) strings
            buffer.putShort((short) headerTextArray.length);
            buffer.put(headerTextArray);
            buffer.putShort((short) 0); //Needed for the protocol...
            buffer.putShort((short) footerTextArray.length);
            buffer.put(footerTextArray);
            //buffer.putShort((short)image.length);
            buffer.put(image);
            return buffer.array();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not found: " + e.getMessage(),
                    e);
        }
    }
}
