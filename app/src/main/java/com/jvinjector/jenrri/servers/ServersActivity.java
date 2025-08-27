/**
 * VERSIRCULO DEL DIA:
 * <p>
 * MATEO 55:56
 * <p>
 * ¿No es este el hijo del carpintero?
 * ¿No se llama su madre María, y sus hermanos,
 * Jacobo, José, Simón y Judas?
 * ¿No están todas sus hermanas con nosotros?
 * ¿De dónde, pues, tiene este todas estas cosas?
 * <p>
 * <p>
 * Amen.
 **/


/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.servers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.speedlite.vpn.MainActivity;
import com.speedlite.vpn.R;
import com.speedlite.vpn.config.Settings;
import com.speedlite.vpn.logger.SkStatus;
import com.speedlite.vpn.util.AESCrypt;
import com.speedlite.vpn.util.ConfigUtil;


public class ServersActivity extends AppCompatActivity {
    private List<ServersModel> servidores;
    private RecyclerView recycler;
    private ServersAdapter adaptador;
    private ConfigUtil config;
    private Settings mConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers);
        Toolbar mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        servidores = new ArrayList<>();
        cargarservers();
        config = new ConfigUtil(this);
        mConfig = new Settings(this);
        recycler = findViewById(R.id.recycler_servers);
        adaptador = new ServersAdapter(servidores);
        adaptador.setOnItemClick(new ServersAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int posicion) {
                loadServerData(posicion);
            }
        });

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adaptador);
    }

    private void cargarservers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < config.getServersArray().length(); i++) {
                        JSONObject servers = config.getServersArray().getJSONObject(i);
                        String name = servers.getString("Name");
                        String info = servers.getString("sInfo");
                        String flag = servers.getString("FLAG");
                        ServersModel modelo = new ServersModel();
                        modelo.setServerName(name);
                        modelo.setServerFlag(flag);
                        modelo.setServerPosicion(i);
                        if (info.isEmpty() || info == null) {
                            modelo.setServerInfo(getString(R.string.app_name));
                        } else {
                            modelo.setServerInfo(info);
                        }
                        servidores.add(modelo);
                        ServersActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adaptador.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    ServersActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ServersActivity.this, "JSON Error Severs Activity: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }).start();
    }

    private void loadServerData(int pos1) {
        try {
            SharedPreferences prefs = mConfig.getPrefsPrivate();
            SharedPreferences.Editor edit = prefs.edit();

            String ssh_server = config.getServersArray().getJSONObject(pos1).getString("ServerIP");
            String remote_proxy = config.getServersArray().getJSONObject(pos1).getString("ProxyIP");
            String proxy_port = config.getServersArray().getJSONObject(pos1).getString("ProxyPort");
            String ssh_user = config.getServersArray().getJSONObject(pos1).getString("ServerUser");
            String ssh_password = config.getServersArray().getJSONObject(pos1).getString("ServerPass");
            String ssh_port = config.getServersArray().getJSONObject(pos1).getString("ServerPort");
            String ssl_port = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
            String payload = config.getServersArray().getJSONObject(pos1).getString("Payload");
            String sni = config.getServersArray().getJSONObject(pos1).getString("SNI");
            String chaveKey = config.getServersArray().getJSONObject(pos1).getString("Slowchave");
            String serverNameKey = config.getServersArray().getJSONObject(pos1).getString("Nameserver");
            String dnsKey = config.getServersArray().getJSONObject(pos1).getString("Slowdns");
            String udpserver = config.getServersArray().getJSONObject(pos1).getString("udpserver");
            String udpauth = config.getServersArray().getJSONObject(pos1).getString("udpauth");
            String udpobfs = config.getServersArray().getJSONObject(pos1).getString("udpobfs");
            String udpbuffer = config.getServersArray().getJSONObject(pos1).getString("udpbuffer");
            String udpdown = config.getServersArray().getJSONObject(pos1).getString("udpdown");
            String udpup = config.getServersArray().getJSONObject(pos1).getString("udpup");
            String authlatamsrc = config.getServersArray().getJSONObject(pos1).getString("apilatamsrcv2ray");
            String v2rayJson = config.getServersArray().getJSONObject(pos1).getString("v2rayJson");
            String putita = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            edit.putString(Settings.USUARIO_KEY, ssh_user);
            edit.putString(Settings.SENHA_KEY, ssh_password);
            edit.putString(Settings.SERVIDOR_KEY, ssh_server);
            edit.putString(Settings.PROXY_IP_KEY, remote_proxy);
            edit.putString(Settings.PROXY_PORTA_KEY, proxy_port);

            boolean sslType = config.getServersArray().getJSONObject(pos1).getBoolean("isSSL");

            boolean sslpayload = config.getServersArray().getJSONObject(pos1).getBoolean("isPayloadSSL");

            boolean inject = config.getServersArray().getJSONObject(pos1).getBoolean("isInject");

            boolean direct = config.getServersArray().getJSONObject(pos1).getBoolean("isDirect");

            boolean slow = config.getServersArray().getJSONObject(pos1).getBoolean("isSlow");
            boolean isudp = config.getServersArray().getJSONObject(pos1).getBoolean("isudp");
            boolean iv2ray = config.getServersArray().getJSONObject(pos1).getBoolean("iv2ray");

            //SSH DIRECT
            if (direct) {
                prefs.edit().putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, false).apply();
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT).apply();

                prefs.edit().putString(Settings.SERVIDOR_KEY, ssh_server).apply();
                prefs.edit().putString(Settings.SERVIDOR_PORTA_KEY, ssh_port).apply();

                prefs.edit().putString(Settings.PROXY_IP_KEY, remote_proxy).apply();
                prefs.edit().putString(Settings.PROXY_PORTA_KEY, proxy_port).apply();
                prefs.edit().putString(Settings.CUSTOM_PAYLOAD_KEY, payload).apply();
            }

            //SSH PROXY
            if (inject) {
                prefs.edit().putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, false).apply();
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY).apply();

                prefs.edit().putString(Settings.SERVIDOR_KEY, ssh_server).apply();
                prefs.edit().putString(Settings.SERVIDOR_PORTA_KEY, ssh_port).apply();

                prefs.edit().putString(Settings.PROXY_IP_KEY, remote_proxy).apply();
                prefs.edit().putString(Settings.PROXY_PORTA_KEY, proxy_port).apply();
                prefs.edit().putString(Settings.CUSTOM_PAYLOAD_KEY, payload).apply();
            }


            //SSH SSL
            if (sslType) {
                prefs.edit().putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true).apply();
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_SSLTUNNEL).apply();

                prefs.edit().putString(Settings.SERVIDOR_KEY, ssh_server).apply();
                prefs.edit().putString(Settings.SERVIDOR_PORTA_KEY, ssl_port).apply();

                prefs.edit().putString(Settings.PROXY_IP_KEY, remote_proxy).apply();
                prefs.edit().putString(Settings.PROXY_PORTA_KEY, proxy_port).apply();

                prefs.edit().putString(Settings.CUSTOM_PAYLOAD_KEY, payload).apply();
                prefs.edit().putString(Settings.CUSTOM_SNI, sni).apply();

            }
            //SSL PAYLOAD
            if (sslpayload) {
                prefs.edit().putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, false).apply();
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();

                prefs.edit().putString(Settings.SERVIDOR_KEY, ssh_server).apply();
                prefs.edit().putString(Settings.SERVIDOR_PORTA_KEY, ssl_port).apply();

                prefs.edit().putString(Settings.CUSTOM_PAYLOAD_KEY, payload).apply();
                prefs.edit().putString(Settings.CUSTOM_SNI, sni).apply();

            }
/**
 * Created by: ElMandarinSNiff
 * Date Crated: 10/8/2023
 * Project: LatamSRC (Español)
 **/

            //SLOW DIRECT
            if (slow) {

                prefs.edit().putString(Settings.CHAVE_KEY, chaveKey).apply();

                prefs.edit().putString(Settings.NAMESERVER_KEY, serverNameKey).apply();
                prefs.edit().putString(Settings.DNS_KEY, dnsKey).apply();

                prefs.edit().putString(Settings.SERVIDOR_KEY, ssh_server).apply();
                prefs.edit().putString(Settings.SERVIDOR_PORTA_KEY, ssh_port).apply();

                prefs.edit().putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true).apply();
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SLOWDNS).apply();
            }
            if (isudp) {

                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_UDP).apply();
                //prefs.edit().putString(Settings.UDP_BUFFER, udpbuffer).apply();
                prefs.edit().putString(Settings.UDP_SERVER, udpserver).apply();
                prefs.edit().putString(Settings.UDP_AUTH, udpauth).apply();
                prefs.edit().putString(Settings.UDP_OBFS, udpobfs).apply();
                prefs.edit().putString(Settings.UDP_DOWN, udpdown).apply();
                prefs.edit().putString(Settings.UDP_UP, udpup).apply();
            }
            if (iv2ray) {
                edit.putString(Settings.APILATAMIP, authlatamsrc);
                edit.putString(Settings.V2RAY_JSON, v2rayJson);
                edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_V2RAY);
                edit.apply();
            }
            edit.apply();
            saveSpinner(pos1);
            MainActivity.updateMainViews(getApplicationContext());
            finish();

        } catch (Exception e) {
            SkStatus.logInfo(e.getMessage());
        }
    }

    private void saveSpinner(int position) {
        SharedPreferences prefs = mConfig.getPrefsPrivate();
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("LastSelectedServer", position);
        edit.apply();
    }
}
