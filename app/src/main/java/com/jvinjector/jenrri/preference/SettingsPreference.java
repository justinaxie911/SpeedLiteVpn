/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.preference;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import android.os.Handler;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;
import com.speedlite.vpn.MainActivity;
import com.speedlite.vpn.R;
import com.speedlite.vpn.MyApplication;
import com.speedlite.vpn.config.Settings;
import com.speedlite.vpn.config.SettingsConstants;
import com.speedlite.vpn.util.Utils;
import com.speedlite.vpn.logger.*;
import com.speedlite.vpn.activities.SplashActivity;


public class SettingsPreference extends PreferenceFragmentCompat
    implements Preference.OnPreferenceChangeListener, SettingsConstants, SkStatus.StateListener {
  private Handler mHandler;
  private SharedPreferences mPref;

  public static final String SSHSERVER_PREFERENCE_KEY = "screenSSHSettings",
      ADVANCED_SCREEN_PREFERENCE_KEY = "screenAdvancedSettings";

  private String[] settings_disabled_keys = {
    UDPFORWARD_KEY,
    TETHERING_SUBNET,
    AUTO_PINGER,
          TUNNEL_TYPE_LATAMSRC,
          USER_HWID,

          WAKELOCK_KEY,
        DNSRESOLVER_KEY1,
        DNSRESOLVER_KEY2,
        DNSFORWARD_KEY,
    VIBRATE,
    DISABLE_DELAY_KEY,
    UDPRESOLVER_KEY,
    PINGER_KEY,
    SSH_COMPRESSION
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mHandler = new Handler();
  }

  @Override
  public void onResume() {
    super.onResume();

    SkStatus.addStateListener(this);
  }

  @Override
  public void onPause() {
    super.onPause();

    SkStatus.removeStateListener(this);
  }

  @Override
  public void onCreatePreferences(Bundle bundle, String root_key) {
    // Load the Preferences from the XML file
    setPreferencesFromResource(R.xml.app_preferences, root_key);

    mPref = getPreferenceManager().getDefaultSharedPreferences(getContext());

    Preference udpForwardPreference = (SwitchPreferenceCompat) findPreference(UDPFORWARD_KEY);
    udpForwardPreference.setOnPreferenceChangeListener(this);

        Preference dnsForwardPreference = (SwitchPreferenceCompat)
			findPreference(DNSFORWARD_KEY);
		dnsForwardPreference.setOnPreferenceChangeListener(this);	
        

    // update view
    setRunningTunnel(SkStatus.isTunnelActive());
  }

  private void onChangeUseVpn(boolean use_vpn) {
    Preference udpResolverPreference = (EditTextPreference) findPreference(UDPRESOLVER_KEY);

        Preference dnsResolverPreference = (EditTextPreference)
			findPreference(DNSRESOLVER_KEY1);
        Preference dnsResolverPreference2 = (EditTextPreference)
            findPreference(DNSRESOLVER_KEY2);    
        
        
    for (String key : settings_disabled_keys) {
      getPreferenceManager().findPreference(key).setEnabled(use_vpn);
    }

    use_vpn = true;
    if (use_vpn) {
      boolean isUdpForward = mPref.getBoolean(UDPFORWARD_KEY, false);

      boolean isDnsForward = mPref.getBoolean(DNSFORWARD_KEY, false);
			
			udpResolverPreference.setEnabled(isUdpForward);
			dnsResolverPreference.setEnabled(isDnsForward);
            dnsResolverPreference2.setEnabled(isDnsForward);
    } else {
      String[] list = {
        UDPFORWARD_KEY, AUTO_PINGER,TUNNEL_TYPE_LATAMSRC,USER_HWID, UDPRESOLVER_KEY,
                DNSFORWARD_KEY,
				DNSRESOLVER_KEY1,
                DNSRESOLVER_KEY2
      };
      for (String key : list) {
        getPreferenceManager().findPreference(key).setEnabled(false);
      }
    }
  }

  private void setRunningTunnel(boolean isRunning) {
    if (isRunning) {
      for (String key : settings_disabled_keys) {

        getPreferenceManager().findPreference(key).setEnabled(false);
      }
    } else {
      onChangeUseVpn(true);
    }
  }

  /** Preference.OnPreferenceChangeListener Implementação */
  @Override
  public boolean onPreferenceChange(Preference pref, Object newValue) {
    switch (pref.getKey()) {
      
            
            case DNSFORWARD_KEY:
				boolean isDnsForward = (boolean) newValue;

				Preference dnsResolverPreference = (EditTextPreference)
					findPreference(DNSRESOLVER_KEY1);
				dnsResolverPreference.setEnabled(isDnsForward);
                
                Preference dnsResolverPreference2 = (EditTextPreference)
                    findPreference(DNSRESOLVER_KEY2);
                dnsResolverPreference2.setEnabled(isDnsForward);
			break;
            
            

      case UDPFORWARD_KEY:
        boolean isUdpForward = (boolean) newValue;

        Preference udpResolverPreference = (EditTextPreference) findPreference(UDPRESOLVER_KEY);
        udpResolverPreference.setEnabled(isUdpForward);
        break;
    }
    return true;
  }


  @Override
  public void updateState(
      String state, String logMessage, int localizedResId, ConnectionStatus level, Intent intent) {
    mHandler.post(
        new Runnable() {
          @Override
          public void run() {
            setRunningTunnel(SkStatus.isTunnelActive());
          }
        });
  }
}
