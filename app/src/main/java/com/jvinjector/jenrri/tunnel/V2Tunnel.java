/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.tunnel;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.net.Network;
import android.util.Log;
import android.annotation.SuppressLint;
import android.widget.TextView;
import com.speedlite.vpn.config.Settings;
import com.speedlite.vpn.config.SettingsConstants;
import com.speedlite.vpn.logger.SkStatus;
import com.speedlite.vpn.tunnel.TunnelManagerHelper;
import com.speedlite.vpn.tunnel.TunnelManagerThread;
import com.speedlite.vpn.tunnel.vpn.V2Listener;
import com.speedlite.vpn.V2Configs;
import com.speedlite.vpn.util.V2Utilities;
import com.speedlite.vpn.V2Service;
import com.speedlite.vpn.V2Core;


import libv2ray.Libv2ray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class V2Tunnel {
    private static V2Listener v2Listener;
    private static SharedPreferences sp = null;
    public V2Tunnel(Context context) {
        v2Listener = TunnelManagerThread.getV2rayServicesListener();
        sp = new Settings(context).getPrefsPrivate();
    }

    public static void init(final Context context, final int app_icon, final String app_name) {
        V2Utilities.copyAssets(context);
        V2Configs.APPLICATION_ICON = app_icon;
        V2Configs.APPLICATION_NAME = app_name;
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                V2Configs.V2RAY_STATE = (V2Configs.V2RAY_STATES) arg1.getExtras().getSerializable("STATE");
            }
        }, new IntentFilter("V2RAY_CONNECTION_INFO"));
    }/*BY LATAMSRC*/public static void changeConnectionMode(final V2Configs.V2RAY_CONNECTION_MODES connection_mode) {
        if (getConnectionState() == V2Configs.V2RAY_STATES.V2RAY_DISCONNECTED) {
            V2Configs.V2RAY_CONNECTION_MODE = connection_mode;
        }/*BY LATAMSRC*/ }/*BY LATAMSRC*/ public static void StartV2ray(final Context context, final String remark, final String config, final ArrayList<String> blocked_apps) {
        V2Configs.V2RAY_CONFIG = V2Utilities.parseV2rayJsonFile(remark, config, blocked_apps);
        if (V2Configs.V2RAY_CONFIG == null) {
            v2Listener.onError();
            SkStatus.logInfo("V2Ray Error");/*BY LATAMSRC*/ }
        Intent start_intent;
        if (V2Configs.V2RAY_CONNECTION_MODE == V2Configs.V2RAY_CONNECTION_MODES.PROXY_ONLY) {
            start_intent = new Intent(context, V2Proxy.class);
        } else if (V2Configs.V2RAY_CONNECTION_MODE == V2Configs.V2RAY_CONNECTION_MODES.VPN_TUN) {
            start_intent = new Intent(context, V2Service.class);
        } else {
            v2Listener.onError();
            SkStatus.logInfo("V2Ray Error");/*BY LATAMSRC*/ return;/*BY LATAMSRC*/
            /*BY LATAMSRC*//*BY LATAMSRC*/ }/*BY LATAMSRC*/ /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/
        /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/  start_intent.putExtra("COMMAND", V2Configs.V2RAY_SERVICE_COMMANDS.START_SERVICE); /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/
        /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/  start_intent.putExtra("V2RAY_CONFIG", V2Configs.V2RAY_CONFIG); /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/
        /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/  SkStatus.logInfo(V2Tunnel.getCoreVersion()); /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/
        /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/  v2Listener.startService(); /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/
        /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/ /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/ SkStatus.logInfo("Iniciando V2ray");
        SkStatus.logInfo("Verificando conexión a internet");
        siNoInternet();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {/*BY LATAMSRC*/context.startForegroundService(start_intent);/*BY LATAMSRC*/ } else {/*BY LATAMSRC*/  context.startService(start_intent);/*BY LATAMSRC*/}/*BY LATAMSRC*/ }
    /*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*/  /*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*//*BY LATAMSRC*/                                                                                                                                                                                                                                                  public static void checkConnectionAfterDelay(final Context context) { sp = new Settings(context).getPrefsPrivate(); new Timer().schedule(new TimerTask() {/*BY LATAMSRC*/ @Override/*BY LATAMSRC*/public void run() {/*BY LATAMSRC*/ String ELmandarinSniff = sp.getString(SettingsConstants.APILATAMIP, "");
        /*ConfigUtil.ELMANDARINSNIFFPUTOELQUELOLEA*/ /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/ /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**//*+ConfigUtil.IPLINKGATESCCN*//*+*//*ConfigUtil.LEEMESIERESPUTOBYLATAMSRC*/String GATESCCNLATAMSRC1 ="mandarin pro"; /*CHANGE*/String GATESCCNLATAMSRC = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);/*BY LATAMSRC*/
        /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/ /**//**//**//**//*EL MANDARIN SNIFF MODED*//**//**//**//**//**/  String result = makeGetRequest(/*ConfigUtil.ELMANDARINSNIFFPUTOELQUELOLEA+*/ELmandarinSniff/*+ConfigUtil.LEEMESIERESPUTOBYLATAMSRC*/+GATESCCNLATAMSRC, 10000); /* 10 segundos de espera/BY LATAMSRC*/ if (result.equals("Fail")) {/*BY LATAMSRC*/  StopV2ray(context);/*BY LATAMSRC*/  TunnelManagerHelper.stopSocksHttp(context);/*BY LATAMSRC*/  SkStatus.logInfo(new Object() {
            int LatamSRC;
            public String toString() {
                byte[] ElMandarinSniff = new byte[111];
                LatamSRC = -1962629217;
                ElMandarinSniff[0] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1884540102;
                ElMandarinSniff[1] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1127232025;
                ElMandarinSniff[2] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -246526519;
                ElMandarinSniff[3] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1166766439;
                ElMandarinSniff[4] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1499618289;
                ElMandarinSniff[5] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1600377731;
                ElMandarinSniff[6] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 2020986339;
                ElMandarinSniff[7] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 812584336;
                ElMandarinSniff[8] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1427582459;
                ElMandarinSniff[9] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 427650649;
                ElMandarinSniff[10] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 641968036;
                ElMandarinSniff[11] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -2030476045;
                ElMandarinSniff[12] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -429988753;
                ElMandarinSniff[13] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1101361969;
                ElMandarinSniff[14] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 117394629;
                ElMandarinSniff[15] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 298100510;
                ElMandarinSniff[16] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -710310122;
                ElMandarinSniff[17] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 530793997;
                ElMandarinSniff[18] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1808865775;
                ElMandarinSniff[19] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 35341835;
                ElMandarinSniff[20] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1794000108;
                ElMandarinSniff[21] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1751549911;
                ElMandarinSniff[22] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -719476853;
                ElMandarinSniff[23] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 468708968;
                ElMandarinSniff[24] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1081536159;
                ElMandarinSniff[25] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 200904274;
                ElMandarinSniff[26] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -864416267;
                ElMandarinSniff[27] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1090104778;
                ElMandarinSniff[28] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -250458893;
                ElMandarinSniff[29] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1914275807;
                ElMandarinSniff[30] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1476069999;
                ElMandarinSniff[31] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -2046751070;
                ElMandarinSniff[32] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -437607418;
                ElMandarinSniff[33] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -2093544148;
                ElMandarinSniff[34] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 685632990;
                ElMandarinSniff[35] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -432607602;
                ElMandarinSniff[36] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1813253300;
                ElMandarinSniff[37] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 235023942;
                ElMandarinSniff[38] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -805696050;
                ElMandarinSniff[39] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 197815473;
                ElMandarinSniff[40] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1229664612;
                ElMandarinSniff[41] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1551809518;
                ElMandarinSniff[42] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1706128232;
                ElMandarinSniff[43] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 324045990;
                ElMandarinSniff[44] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -783823549;
                ElMandarinSniff[45] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 826534973;
                ElMandarinSniff[46] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1269108296;
                ElMandarinSniff[47] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -468210639;
                ElMandarinSniff[48] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -2142854120;
                ElMandarinSniff[49] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 518261905;
                ElMandarinSniff[50] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -2007896903;
                ElMandarinSniff[51] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1635804183;
                ElMandarinSniff[52] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1011413864;
                ElMandarinSniff[53] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1714768937;
                ElMandarinSniff[54] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -577212862;
                ElMandarinSniff[55] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1137974256;
                ElMandarinSniff[56] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -521581421;
                ElMandarinSniff[57] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 965430311;
                ElMandarinSniff[58] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -34451818;
                ElMandarinSniff[59] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -596554246;
                ElMandarinSniff[60] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 762015343;
                ElMandarinSniff[61] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1701526524;
                ElMandarinSniff[62] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1548087801;
                ElMandarinSniff[63] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -848392720;
                ElMandarinSniff[64] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 386887244;
                ElMandarinSniff[65] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1673145897;
                ElMandarinSniff[66] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 364850860;
                ElMandarinSniff[67] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 944737467;
                ElMandarinSniff[68] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1109402633;
                ElMandarinSniff[69] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -420548996;
                ElMandarinSniff[70] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -162574685;
                ElMandarinSniff[71] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 359842824;
                ElMandarinSniff[72] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -507171194;
                ElMandarinSniff[73] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1823927805;
                ElMandarinSniff[74] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1836200584;
                ElMandarinSniff[75] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1677823164;
                ElMandarinSniff[76] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1566068169;
                ElMandarinSniff[77] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1508891629;
                ElMandarinSniff[78] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1782258681;
                ElMandarinSniff[79] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -742191676;
                ElMandarinSniff[80] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1033990683;
                ElMandarinSniff[81] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -114793471;
                ElMandarinSniff[82] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -689189665;
                ElMandarinSniff[83] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 663719600;
                ElMandarinSniff[84] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1407267656;
                ElMandarinSniff[85] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1956365341;
                ElMandarinSniff[86] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -163992536;
                ElMandarinSniff[87] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1273459615;
                ElMandarinSniff[88] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 298372757;
                ElMandarinSniff[89] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -56799966;
                ElMandarinSniff[90] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -109678905;
                ElMandarinSniff[91] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1240994784;
                ElMandarinSniff[92] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1729074046;
                ElMandarinSniff[93] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -635302427;
                ElMandarinSniff[94] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -669161600;
                ElMandarinSniff[95] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1995269618;
                ElMandarinSniff[96] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -2044850589;
                ElMandarinSniff[97] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1933045382;
                ElMandarinSniff[98] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -2004431268;
                ElMandarinSniff[99] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -110678551;
                ElMandarinSniff[100] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 2123541975;
                ElMandarinSniff[101] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1850715368;
                ElMandarinSniff[102] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 897075148;
                ElMandarinSniff[103] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 2106468254;
                ElMandarinSniff[104] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -144751135;
                ElMandarinSniff[105] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 2109205699;
                ElMandarinSniff[106] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -1410888212;
                ElMandarinSniff[107] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = -431043108;
                ElMandarinSniff[108] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1020841611;
                ElMandarinSniff[109] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                LatamSRC = 1175504856;
                ElMandarinSniff[110] = (byte) (LatamSRC >>>
                        Integer.parseInt((new Object() {
                            int Smandchat;
                            public String toString() {
                                byte[] iLoveLatamSRC = new byte[2];
                                Smandchat = -846088739;
                                iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                                Smandchat = 613358785;
                                iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                                return new String(iLoveLatamSRC);}}.toString())));
                return new String(ElMandarinSniff);
            }}.toString());
            /*BY LATAMSRC*/ } } }, 5000); /* 3 segundos de retraso*/ }
    public static String makeGetRequest(String urlString, int timeout) {
        try {/*BY LATAMSRC*/URL url = new URL(urlString);/*BY LATAMSRC*/ HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();/*BY LATAMSRC*/  urlConnection.setConnectTimeout(timeout); /*BY LATAMSRC*//*BY LATAMSRC*/ urlConnection.setReadTimeout(timeout); /*BY LATAMSRC*//*BY LATAMSRC*/  urlConnection.setRequestMethod("GET");/*BY LATAMSRC*//*BY LATAMSRC*/int responseCode = urlConnection.getResponseCode();/*BY LATAMSRC*/ if (responseCode == HttpURLConnection.HTTP_OK) {/*BY LATAMSRC*//*BY LATAMSRC*/ BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();/*BY LATAMSRC*//*BY LATAMSRC*/ String inputLine;/*BY LATAMSRC*//*BY LATAMSRC*/while ((inputLine = in.readLine()) != null) {  response.append(inputLine);  }/*BY LATAMSRC*/ in.close();  return response.toString(); } else { return "Fail"; } } catch (Exception e) {  e.printStackTrace();  return "OK";   }/*BY LATAMSRC*/ }
    public static void StopV2ray(final Context context) {
        Intent stop_intent;
        if (V2Configs.V2RAY_CONNECTION_MODE == V2Configs.V2RAY_CONNECTION_MODES.PROXY_ONLY) {
            stop_intent = new Intent(context, V2Proxy.class);
        } else if (V2Configs.V2RAY_CONNECTION_MODE != V2Configs.V2RAY_CONNECTION_MODES.VPN_TUN) {
            return;
        } else {
            stop_intent = new Intent(context, V2Service.class);
        }
        stop_intent.putExtra("COMMAND", V2Configs.V2RAY_SERVICE_COMMANDS.STOP_SERVICE);
        context.startService(stop_intent);
        V2Configs.V2RAY_CONFIG = null;
    }
    public static V2Configs.V2RAY_CONNECTION_MODES getConnectionMode() {
        return V2Configs.V2RAY_CONNECTION_MODE;
    }

    public static V2Configs.V2RAY_STATES getConnectionState() {
        return V2Configs.V2RAY_STATE;
    }

    public static String getCoreVersion(){
        return Libv2ray.checkVersionX();
    }

    public static void siNoInternet(){/*BY LATAMSRC*/  new Timer().schedule(new TimerTask()/*BY LATAMSRC*/ {/*BY LATAMSRC*/ @Override/*BY LATAMSRC*/ public void run() {/*BY LATAMSRC*/  if (verificarInternet()){/*BY LATAMSRC*/Log.d("Tienes internet", "Tienes acceso a internet");/*BY LATAMSRC*/ SkStatus.logInfo("Conectando servidor");/*BY LATAMSRC*/ v2Listener.onConnected();/*BY LATAMSRC*/  return;/*BY LATAMSRC*/ }/*BY LATAMSRC*/  Log.d("No tienes internet", "No tienes acceso a internet");/*BY LATAMSRC*/ SkStatus.logInfo("No hay acceso a internet");/*BY LATAMSRC*/ v2Listener.onError();/*BY LATAMSRC*/  }/*BY LATAMSRC*/}, 2000);/*BY LATAMSRC*/}
    public static boolean verificarInternet() {/*BY LATAMSRC*/  try {/*BY LATAMSRC*/  String command = "ping -c 1 google.com";/*BY LATAMSRC*/ return (Runtime.getRuntime().exec(command).waitFor() == 0);/*BY LATAMSRC*/} catch (Exception e) {/*BY LATAMSRC*/  return false;/*BY LATAMSRC*/ }/*BY LATAMSRC*/ }/*BY LATAMSRC*/}
