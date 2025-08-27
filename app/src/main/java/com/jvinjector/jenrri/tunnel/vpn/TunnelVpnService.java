/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */

/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.tunnel.vpn;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.VpnService;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.speedlite.vpn.logger.SkStatus;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class TunnelVpnService extends VpnService {

  private static final String LOG_TAG = "TunnelVpnService";
  public static final String TUNNEL_VPN_DISCONNECT_BROADCAST =
      "tunnelVpnDisconnectBroadcast";
  public static final String TUNNEL_VPN_START_BROADCAST =
      "tunnelVpnStartBroadcast";
  public static final String TUNNEL_VPN_START_SUCCESS_EXTRA =
      "tunnelVpnStartSuccessExtra";

  private TunnelVpnManager m_tunnelManager = new TunnelVpnManager(this);

  public class LocalBinder extends Binder {
    public TunnelVpnService getService() {
      return TunnelVpnService.this;
    }
  }

  private final IBinder m_binder = new LocalBinder();

  @Override
  public IBinder onBind(Intent intent) {
    String action = intent.getAction();
    if (action != null && action.equals(SERVICE_INTERFACE)) {
      return super.onBind(intent);
    }
    return m_binder;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(LOG_TAG, "on start");
    return m_tunnelManager.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onCreate() {
    Log.d(LOG_TAG, "on create");
    TunnelState.getTunnelState().setTunnelManager(m_tunnelManager);
  }

  @Override
  public void onDestroy() {
    Log.d(LOG_TAG, "on destroy");
    TunnelState.getTunnelState().setTunnelManager(null);
    m_tunnelManager.onDestroy();
  }

  @Override
  public void onRevoke() {
    SkStatus.logInfo("<strong>VPN service revoked</strong>");
    broadcastVpnDisconnect();
    // stopSelf will trigger onDestroy in the main thread.
    stopSelf();
  }

  public Builder newBuilder() {
    return new Builder();
  }

  // Broadcast non-user-initiated VPN disconnect.
  public void broadcastVpnDisconnect() {
    dispatchBroadcast(new Intent(TUNNEL_VPN_DISCONNECT_BROADCAST));
  }

  // Broadcast VPN start. |success| is true if the VPN and tunnel were started
  // successfully, and false otherwise.
  public void broadcastVpnStart(boolean success) {
    Intent vpnStart = new Intent(TUNNEL_VPN_START_BROADCAST);
    vpnStart.putExtra(TUNNEL_VPN_START_SUCCESS_EXTRA, success);
    dispatchBroadcast(vpnStart);
  }

  private void dispatchBroadcast(final Intent broadcast) {
    LocalBroadcastManager.getInstance(TunnelVpnService.this)
        .sendBroadcast(broadcast);
  }
}
