/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.speedlite.vpn.config.Settings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Painghein
 * Date Crated: 08/10/2022
 * Project: SocksHttp-master (ENGLISH)
 **/
public class ConfigUpdate extends AsyncTask<String, String, String> {
    private Settings mConfig;
    private final Context context;
    private final OnUpdateListener listener;
    private ProgressDialog progressDialog;
    private boolean isOnCreate;
    //TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
    public ConfigUpdate(Context context, OnUpdateListener listener) {
        this.context = context;
        this.listener = listener;
    }
    //TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
    public void start(boolean isOnCreate) {
        this.isOnCreate = isOnCreate;
        execute();
    }//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC

    public interface OnUpdateListener {
        void onUpdateListener(String result);
    }
    //TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
    @Override
    protected String doInBackground(String... strings) {
        mConfig = new Settings(context);//TODO BY LATAMSRC//TODO BY LATAMSRC
        try {//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
            StringBuilder sb = new StringBuilder();
            ///*inicio modo vip*///TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
            if (mConfig.getUserOrHwid()){/////ON POBRE
                URL url = new URL(ConfigUtil.LINKUPDATEMODOPOBRE);//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
                HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                conn1.setRequestMethod("GET");//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
                conn1.connect();//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
                BufferedReader br = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                String response1;//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
                while ((response1 = br.readLine()) != null) {
                    sb.append(response1);//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
                }
            } else {///MODOPOBRE. //TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
                URL url1 = new URL(ConfigUtil.LINKUPDATEMODOVIP);
                HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response;
                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
                /*llave del cierre para  MOPOBRE*/   }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error on getting data: " + e.getMessage();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        if (!isOnCreate) {//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Please wait while loading");
//            progressDialog.setTitle("Checking Update");
//            progressDialog.setCancelable(false);//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
//            progressDialog.show();
//        }
    }
    //TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC//TODO BY LATAMSRC
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!isOnCreate && progressDialog != null) {
            progressDialog.dismiss();
        }
        if (listener != null) {
            listener.onUpdateListener(s);
        }
    }
}
