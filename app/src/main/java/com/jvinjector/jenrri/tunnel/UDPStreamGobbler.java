/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */

/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.tunnel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UDPStreamGobbler extends Thread {
    public interface OnResultListener {
        void onResult(String str);
    }
    private boolean isInterrupted;
    private final OnResultListener listener;
    private final BufferedReader reader;

    public void setInterrupted(boolean z) {
        this.isInterrupted = z;
    }

    public UDPStreamGobbler(InputStream inputStream, OnResultListener onResultListener) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.listener = onResultListener;
    }

    @Override
    public void run() {
        String readLine;
        try {
            while (!this.isInterrupted && (readLine = this.reader.readLine()) != null) {
                OnResultListener onResultListener = this.listener;
                if (onResultListener != null) {
                    onResultListener.onResult(readLine);
                }

            }
        } catch (Exception unused) {
        }
        try {
            this.reader.close();
        } catch (IOException unused2) {
        }
    }

}
