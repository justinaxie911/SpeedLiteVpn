/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn;

import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import com.speedlite.vpn.*;
//import com.google.android.gms.ads.*;

public class AdsManager {
	private final String TAG = "AdsManager";
	private Context mContext;
	private SharedPreferences mPrefs;
	//private InterstitialAd mInterstitialAd;

	public AdsManager(Context context) {
		mContext = context;
		mPrefs = context.getSharedPreferences(MyApplication.PREFS_GERAL, Context.MODE_PRIVATE);

	}

	/**
	 * Ads Timer
	 */
	private CountDownTimer countDownTimer;
	private long timerMilliseconds;

	private void createTimer(final long milliseconds) {
		// Create the game timer, which counts down to the end of the level
		// and shows the "retry" button.
		if (countDownTimer != null) {
			countDownTimer.cancel();
		}

		countDownTimer = new CountDownTimer(milliseconds, 50) {
			@Override
			public void onTick(long millisUnitFinished) {
				timerMilliseconds = millisUnitFinished;
			}

			@Override
			public void onFinish() {
				//	loadAdsInterstitial(); // carrega novo anúncio
			}
		};
	}

}
