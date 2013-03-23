package org.openliveview.device.messages.events;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.openliveview.device.messages.AbstractEvent;
import org.openliveview.device.messages.MessageConstants;
import org.openliveview.util.UByte;

public class CapsResponse extends AbstractEvent {

    private UByte width;
    private UByte height;
    private byte statusBarWidth;
    private byte statusBarHeight;
    private byte viewWidth;
    private byte viewHeight;
    private byte announceWidth;
    private byte announceHeight;
    private byte textChunkSize;
    private byte idleTimer;
    private String softwareVersion;

    public UByte getWidth() {
        return width;
    }

    public void setWidth(UByte width) {
        this.width = width;
    }

    public UByte getHeight() {
        return height;
    }

    public void setHeight(UByte height) {
        this.height = height;
    }

    public byte getStatusBarWidth() {
        return statusBarWidth;
    }

    public void setStatusBarWidth(byte statusBarWidth) {
        this.statusBarWidth = statusBarWidth;
    }

    public byte getStatusBarHeight() {
        return statusBarHeight;
    }

    public void setStatusBarHeight(byte statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
    }

    public byte getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(byte viewWidth) {
        this.viewWidth = viewWidth;
    }

    public byte getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(byte viewHeight) {
        this.viewHeight = viewHeight;
    }

    public byte getAnnounceWidth() {
        return announceWidth;
    }

    public void setAnnounceWidth(byte announceWidth) {
        this.announceWidth = announceWidth;
    }

    public byte getAnnounceHeight() {
        return announceHeight;
    }

    public void setAnnounceHeight(byte announceHeight) {
        this.announceHeight = announceHeight;
    }

    public byte getTextChunkSize() {
        return textChunkSize;
    }

    public void setTextChunkSize(byte textChunkSize) {
        this.textChunkSize = textChunkSize;
    }

    public byte getIdleTimer() {
        return idleTimer;
    }

    public void setIdleTimer(byte idleTimer) {
        this.idleTimer = idleTimer;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public CapsResponse() {
        super(MessageConstants.MSG_GETCAPS_RESP);
    }

    @Override
    public void readData(ByteBuffer buffer) {
        this.width = new UByte(buffer.get());
        this.height = new UByte(buffer.get());
        this.statusBarWidth = buffer.get();
        this.statusBarHeight = buffer.get();
        this.viewWidth = buffer.get();
        this.viewHeight = buffer.get();
        this.announceWidth = buffer.get();
        this.announceHeight = buffer.get();
        this.textChunkSize = buffer.get();
        this.idleTimer = buffer.get();
        buffer.get(); // TODO "|" before version string
        int versionLen = buffer.remaining();
        byte[] versionArray = new byte[versionLen];
        buffer.get(versionArray);
        try {
            this.softwareVersion = new String(versionArray, "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Capabilities: version = " + softwareVersion;
    }

}
