package org.openliveview.plugin.api;

public interface PluginAPI {

    final static int NAV_UP = 1;
    final static int NAV_DOWN = 2;
    final static int NAV_LEFT = 3;
    final static int NAV_RIGHT = 4;
    final static int NAV_SELECT = 5;

    void onStart();

    void onStop();

    void onPause();

    void onNavigationAction(int navigationAction);

    boolean isAlertProvider();

    int getUnreadAlertCount();

    int getAlertCount();

}
