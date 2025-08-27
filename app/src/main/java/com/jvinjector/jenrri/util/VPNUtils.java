/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.ClipData;
import android.os.Build.VERSION;
import android.os.Build;
import android.widget.Toast;
import java.util.List;
import java.util.Collections;
import java.util.Locale;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.InetAddress;
import android.text.Html;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import org.apache.http.conn.util.InetAddressUtils;
import com.speedlite.vpn.util.VPNUtil;

public class VPNUtils
{
	public static final String MESSAGE = "SHlkcmEgVlBOIEZyZWUgU2VydmVycyBpcyBQcm92aWRlZCBieTogIlRDUFZQTiIgVGVybXMgb2YgU2VydmljZS4gTm8gRERPUywgTm8gQ2FyZGluZywgRG9uJ3QgdXNlIHRoaXMgZm9yIHRvcnJlbnQuIFBsZWFzZSBzdXBwb3J0IERhcmtNb29uIFZQTi4=";
	public static final String SERVER_MESSAGE = new String(new byte[]{(byte)35,  (byte)52,  (byte)99,  (byte)97,  (byte)102,  (byte)53,  (byte)48});
	public static final String[] MAX_CHARACTERS = new String[]{".", "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

	public static String getDefaultValue(char chart){

		return MAX_CHARACTERS["e74dToRqU8iAhGD5sCm10FvNXzVS".indexOf(chart)];
	}

	public static void copyToClipboard(Context context, String str) {
        if (VERSION.SDK_INT >= 11) {
            ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("CDCEVPN-log", str));
        } else {
            ((android.text.ClipboardManager) context.getSystemService("clipboard")).setText(str);
        }
        Toast.makeText(context, "Copy to Clipboard", 0).show();
    }

	public static String getMessage(){
		String message = "<font color=" + SERVER_MESSAGE + ">" + VPNUtil.decrypt(MESSAGE) + "</font>";

		return message;
	}



	public static String getIPv4Address()
    {
        List<NetworkInterface> netInterfaces;
        try
        {
            netInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        }
        catch (SocketException e)
        {
            return "127.0.0.1";
        }

        for (NetworkInterface netInterface : netInterfaces)
        {
            for (InetAddress inetAddress : Collections.list(netInterface.getInetAddresses()))
            {
                if (!inetAddress.isLoopbackAddress())
                {
                    String ipAddress = inetAddress.getHostAddress();
                    if (isIPv4Address(ipAddress))
                    {
                        return ipAddress;
                    }
                }
            }
        }
        return "127.0.0.1";
    }

	private static boolean isIPv4Address(String ipAddress) {
		return false;
	}
	public static final String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & 255);
                while (toHexString.length() < 2) {
                    toHexString = new StringBuffer().append("0").append(toHexString).toString();
                }
                stringBuilder.append(toHexString);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            return (String) null;
        }
    }

    public static String getHWID() {
        return md5(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(Build.SERIAL).append(Build.BOARD.length() % 5).toString()).append(Build.BRAND.length() % 5).toString()).append(Build.DEVICE.length() % 5).toString()).append(Build.MANUFACTURER.length() % 5).toString()).append(Build.MODEL.length() % 5).toString()).append(Build.PRODUCT.length() % 5).toString()).append(Build.HARDWARE).toString()).toUpperCase(Locale.getDefault());
    }
}
