/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.config;

import java.io.Serializable;
import java.util.ArrayList;

public class V2Config implements Serializable {

    public String CONNECTED_V2RAY_SERVER_ADDRESS = "";
    public String CONNECTED_V2RAY_SERVER_PORT = "";
    public int LOCAL_SOCKS5_PORT = 10808;
    public int LOCAL_HTTP_PORT = 10809;
    public ArrayList<String> BLOCKED_APPS = null;
    public String V2RAY_FULL_JSON_CONFIG = null;
    public boolean ENABLE_TRAFFIC_STATICS = false;
    public String REMARK = "";
    public String APPLICATION_NAME;
    public int APPLICATION_ICON;
}
