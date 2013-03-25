package org.openliveview.plugin;

import org.openliveview.device.messages.calls.MenuItem;
import org.openliveview.util.UShort;


public class MenuEntry {
    
    private final String title;
    private final byte[] image;
    private final boolean isAlert;

    public MenuEntry(final String title, final byte[] image, final boolean isAlert) {
        this.title = title;
        this.image = image;
        this.isAlert = isAlert;
    }
    
    public MenuItem getMenuItem(final byte id) {
        return new MenuItem(id, isAlert, new UShort((short) getUnreadCount()), title, image);
    }
    
    public int getUnreadCount() {
        return 0; // TODO
    }
    
}
