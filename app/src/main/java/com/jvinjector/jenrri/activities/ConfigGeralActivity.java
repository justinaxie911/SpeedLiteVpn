/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.speedlite.vpn.MainActivity;
import com.speedlite.vpn.R;
import com.speedlite.vpn.config.Settings;
import com.speedlite.vpn.preference.SettingsPreference;

import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.Preference;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.widget.Toast;

import com.speedlite.vpn.preference.SettingsSSHPreference;
import com.speedlite.vpn.preference.SettingsDNSPreferences;
import com.speedlite.vpn.preference.SettingsHysteriaPreferences;

public class ConfigGeralActivity extends BaseActivity
        implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    public static String OPEN_SETTINGS_SSH = "openSSHScreen";
    public static String OPEN_SETTINGS_DNS = "openDNSScreen";
    public static String OPEN_SETTINGS_UDP = "openUDPScreen";
    public static String OPEN_bTUNNEL_TYPE_V2RAY = "openv2ray";
    private Settings mConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        mConfig = new Settings(this);

        if (!mConfig.cambiarestadolatamsrc()) {
            PreferenceFragmentCompat preference = new SettingsPreference();
            Intent intent = getIntent();

            String action = intent.getAction();
            if (action != null && action.equals(OPEN_SETTINGS_SSH)) {
                setTitle(R.string.settings_ssh);
                preference = new SettingsSSHPreference();
            } else if (action != null && action.equals(OPEN_SETTINGS_DNS)) {
                setTitle(R.string.slowdns_configuration);
                preference = new SettingsDNSPreferences();
            } else if (action != null && action.equals(OPEN_SETTINGS_UDP)) {
                setTitle(R.string.settings_udp);
                preference = new SettingsHysteriaPreferences();
            }

            // add preference settings
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_configLinearLayout, preference)
                    .commit();
        } else {
            //Toast.makeText(this, "Primero Activa El Custom Mode", Toast.LENGTH_SHORT).show();
            PreferenceFragmentCompat preference = new SettingsPreference();
            // add preference settings
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_configLinearLayout, preference)
                    .commit();
        }



        // toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        // Instantiate the new Fragment
        final Bundle bundle = pref.getExtras();
        final Fragment fragment = Fragment.instantiate(this, pref.getFragment(), bundle);

        fragment.setTargetFragment(caller, 0);

        // Replace the existing Fragment with the new Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_configLinearLayout, fragment)
                .addToBackStack(null)
                .commit();

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

