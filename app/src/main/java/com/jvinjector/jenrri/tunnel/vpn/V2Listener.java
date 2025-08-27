/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */

/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.tunnel.vpn;

import android.app.Service;

public interface V2Listener {
    boolean onProtect(final int socket);
    Service getService();
    void startService();
    void stopService();
    void onConnected();
    void onError();

}
