/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.activities;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import com.speedlite.vpn.R;
import com.speedlite.vpn.MainActivity;

public class SplashActivity extends BaseActivity  {
	
	
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
                @Override
                public void run() {
					// inicia atividade principal
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(intent);

					// encerra o launcher
					finish();
                }
            }, 600);
    }
}
