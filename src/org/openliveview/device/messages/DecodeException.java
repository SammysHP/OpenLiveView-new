package org.openliveview.device.messages;

public class DecodeException extends Exception {
    private static final long serialVersionUID = -7158573269460369608L;

    public DecodeException() {
        super();
    }

    public DecodeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DecodeException(String detailMessage) {
        super(detailMessage);
    }

    public DecodeException(Throwable throwable) {
        super(throwable);
    }
}
