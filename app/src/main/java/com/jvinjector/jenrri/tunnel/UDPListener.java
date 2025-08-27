/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */

/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.tunnel;

public interface UDPListener {
    void onConnecting();
    void onConnected();
    void onNetworkLost();
    void onAuthFailed();
    void onReconnecting();
    void onConnectionLost();
    void onError();
    void onDisconnected();
}
