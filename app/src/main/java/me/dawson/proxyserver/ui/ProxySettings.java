package me.dawson.proxyserver.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.speedlite.vpn.R;
import com.speedlite.vpn.tunnel.TunnelUtils;
import android.view.LayoutInflater;
import android.app.AlertDialog;
import android.view.Gravity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.view.ViewGroup;
import android.app.Notification;

import androidx.annotation.NonNull;

import com.speedlite.vpn.MyApplication;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Vibrator;
import android.widget.ToggleButton;
import android.widget.Button;

public class ProxySettings extends AppCompatActivity implements ServiceConnection,
		OnCheckedChangeListener {
	public static final String TAG = "ProxySettings";

	protected static final String KEY_PREFS = "proxy_pref";
	protected static final String KEY_ENABALE = "proxy_enable";

	private static int NOTIFICATION_ID = 20140701;
	private static final String PortDefault = "8080";

	private IProxyControl proxyControl = null;

	private TextView tvInfo;
	private SwitchCompat cbEnable;
	private Button ZonaWifi;

	//private AdView adsBannerView;

	private InterstitialAd mInterstitialAd;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zacdevz_wifi);
		
		tvInfo = (TextView) findViewById(R.id.tv_info);
		cbEnable = (SwitchCompat) findViewById(R.id.cb_enable);
		cbEnable.setOnCheckedChangeListener(this);

	//	adsBannerView = (AdView) findViewById(R.id.adBannerMainView3);
	/*	if (!MyApplication.DEBUG) {
			adsBannerView.setAdUnitId(MyApplication.ADS_UNITID_BANNER_PROXY);
		}*/
	/*	if (TunnelUtils.isNetworkOnline(this)) {

			adsBannerView.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {
					if (adsBannerView != null) {
						adsBannerView.setVisibility(View.VISIBLE);
					}
				}
			});

			adsBannerView.loadAd(new AdRequest.Builder()
					.build());
		}*/

		

		Intent intent = new Intent(this, ProxyService.class);
		bindService(intent, this, Context.BIND_AUTO_CREATE);
	}
    
    public void btnHardwareID2(View view){
		Intent tetherSettings = new Intent();
				tetherSettings.setClassName("com.android.settings", "com.android.settings.TetherSettings");
				startActivity(tetherSettings);
	}

	@Override
	public void onServiceConnected(ComponentName cn, IBinder binder) {
		proxyControl = (IProxyControl) binder;
		if (proxyControl != null) {
			updateProxy();
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName cn) {
		proxyControl = null;
	}

	@Override
	protected void onDestroy() {
		unbindService(this);
		super.onDestroy();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		SharedPreferences sp = getSharedPreferences(KEY_PREFS, MODE_PRIVATE);
		sp.edit().putBoolean(KEY_ENABALE, isChecked).commit();
		updateProxy();
	}

	private void updateProxy() {
		if (proxyControl == null) {
			return;
		}

		boolean isRunning = false;
		try {
			isRunning = proxyControl.isRunning();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		boolean shouldRun = getSharedPreferences(KEY_PREFS, MODE_PRIVATE)
				.getBoolean(KEY_ENABALE, false);
		if (shouldRun && !isRunning) {
			startProxy();
		} else if (!shouldRun && isRunning) {
			stopProxy();
		}

		try {
			isRunning = proxyControl.isRunning();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		
	}

/*	public void alertap(View view){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View inflate = inflater.inflate(R.layout.proxy_port, (ViewGroup) null);
		AlertDialog.Builder builer = new AlertDialog.Builder(this);
		builer.setView(inflate);
		LinearLayout bubu = inflate.findViewById(R.id.afuerax2);

		TextView ippro= inflate.findViewById(R.id.ippro);
		ippro.setText(TunnelUtils.getLocalIpAddress());

		final AlertDialog alert = builer.create();
		alert.setCanceledOnTouchOutside(false);
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alert.getWindow().setGravity(Gravity.CENTER);
		alert.getWindow().getAttributes().windowAnimations = R.style.dialog;
		alert.show();

		bubu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try
				{
					alert.dismiss();

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}});

		alert.show();


	}*/

	private void startProxy() {
		boolean started = false;
		try {
			started = proxyControl.start();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (!started) {
			return;
		}

		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		Context context = getApplicationContext();

		Notification notification = new Notification();
		notification.icon = R.drawable.icon;
		notification.tickerText = getResources().getString(R.string.proxy_on);
		showInterstitial();
		notification.when = System.currentTimeMillis();

		CharSequence contentTitle = getResources().getString(R.string.app_name);
		;
		CharSequence contentText = getResources().getString(
				R.string.service_text);
		Intent intent = new Intent(this, ProxySettings.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_IMMUTABLE);

		/*notification.setLatestEventInfo(context, contentTitle, contentText,
		 pendingIntent);*/
		NotificationManager (context, contentTitle, contentText, pendingIntent);

		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		manager.notify(NOTIFICATION_ID, notification);

		Vibrator vb_service = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		vb_service.vibrate(50);

		
	}

	private void NotificationManager(Context context, CharSequence contentTitle, CharSequence contentText, PendingIntent pendingIntent) {
	}

	private void stopProxy() {
		boolean stopped = false;

		try {
			stopped = proxyControl.stop();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (!stopped) {
			return;
		}

		tvInfo.setText(R.string.proxy_off);
		showInterstitial();
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(NOTIFICATION_ID);
		Vibrator vb_service = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		vb_service.vibrate(50);

	}

	private void loadInterstitialAds() {
		AdRequest adRequest = new AdRequest.Builder().build();
		InterstitialAd.load(this, getString(R.string.admobinterst), adRequest,
				new InterstitialAdLoadCallback() {
					@Override
					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
						mInterstitialAd = interstitialAd;

					}

					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						mInterstitialAd = null;

					}
				});
	}

	private void showInterstitial() {
		if (mInterstitialAd != null) {
			mInterstitialAd.show(ProxySettings.this);
			mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){

				@Override
				public void onAdClicked() {
					// Called when a click is recorded for an ad.
					//Log.d(TAG, "Ad was clicked.");
				}

				@Override
				public void onAdDismissedFullScreenContent() {
					// Called when ad is dismissed.
					// Set the ad reference to null so you don't show the ad a second time.
					//Log.d(TAG, "Ad dismissed fullscreen content.");
					//	Toast.makeText(SocksHttpMainActivity.this, "Gracias por Apoyar la Aplicacion.", Toast.LENGTH_SHORT).show();
					mInterstitialAd = null;
				}

				@Override
				public void onAdFailedToShowFullScreenContent(AdError adError) {
					// Called when ad fails to show.
					//Log.e(TAG, "Ad failed to show fullscreen content.");
					mInterstitialAd = null;
				}

				@Override
				public void onAdImpression() {
					// Called when an impression is recorded for an ad.
					//Log.d(TAG, "Ad recorded an impression.");
				}

				@Override
				public void onAdShowedFullScreenContent() {
					// Called when ad is shown.
					//Log.d(TAG, "Ad showed fullscreen content.");
				}
			});
		} else {
			loadInterstitialAds();
		}
	}

}