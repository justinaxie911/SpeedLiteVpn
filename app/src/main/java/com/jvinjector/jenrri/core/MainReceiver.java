/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.core;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.speedlite.vpn.service.SocksDNSService;
import com.speedlite.vpn.tunnel.TunnelManagerHelper;

public class MainReceiver extends BroadcastReceiver
{
    public static final String ACTION_SERVICE_RESTART = "sshTunnelServiceRestsrt",
    ACTION_SERVICE_STOP = "sshtunnelservicestop";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String acao = intent.getAction();

        if (acao == null) {
            return;
        }

        switch (acao) {

            case ACTION_SERVICE_STOP:
                TunnelManagerHelper.stopSocksHttp(context);
                break;

            case ACTION_SERVICE_RESTART:
                Intent restartTunnel = new Intent(SocksDNSService.TUNNEL_SSH_RESTART_SERVICE);
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(restartTunnel);
                break;
        }
    }
}
