/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.util;

import android.content.Context;
import android.os.AsyncTask;

public class Worker2 extends AsyncTask<Void, Integer, String> {
    private WorkerAction workerAction;


    protected void onPreExecute() {
    }

    protected void onProgressUpdate(Integer... numArr) {
    }

    public Worker2(WorkerAction workerAction, Context context) {
        this.workerAction = workerAction;
    }

    protected String doInBackground(Void... voidArr) {
        this.workerAction.runFirst();
        return (String) null;
    }

    protected void onPostExecute(String str) {
        this.workerAction.runLast();
    }
}
