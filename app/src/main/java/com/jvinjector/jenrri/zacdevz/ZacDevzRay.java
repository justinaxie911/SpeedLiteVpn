/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.jvinjector.jenrri.zacdevz;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import es.dmoral.toasty.Toasty;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.speedlite.vpn.MainActivity;
import com.speedlite.vpn.config.Settings;
import com.speedlite.vpn.R;

import com.speedlite.vpn.logger.SkStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import android.text.TextWatcher;
import android.text.Editable;

public class ZacDevzRay extends AppCompatActivity {

    private TextInputEditText accountInput;
    private TextInputEditText jsoninput;
    private Toolbar toolbar_main;
    private Settings mConfig;
    private SharedPreferences prefs;
    private String generatedJson = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zac);

        // Inisialisasi toolbar
        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("V2Ray Settings");
        }

        mConfig = new Settings(this);
        prefs = mConfig.getPrefsPrivate();

        setupViews();
    }

    private void setupViews() {
        accountInput = (TextInputEditText) findViewById(R.id.accountInput);
        jsoninput = (TextInputEditText) findViewById(R.id.jsontInput);

        jsoninput.setText(mConfig.getPrivString(Settings.V2RAY_JSON));

        // Add a TextWatcher to the jsoninput field to automatically save the JSON
        jsoninput.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
					// Nothing to do here
				}

				@Override
				public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
					// Auto-save whenever the text changes
					doSave();
				}

				@Override
				public void afterTextChanged(Editable editable) {
					// Nothing to do here
				}
			});
    }

    private void convertToJson() {
        try {
            String accountUri = accountInput.getText().toString().trim();
            if (accountUri.isEmpty()) return;

            String templateJson = loadJSONFromAsset(ZacDevzRay.this, "v2ray_config.json");
            if (templateJson == null) return;

            JSONObject config = new JSONObject(templateJson);
            modifyV2RayConfig(accountUri, config);

            generatedJson = config.toString(4);
            jsoninput.setText(generatedJson);

        } catch (JSONException | URISyntaxException e) {
            Log.e("ZacDevzRay", "Error: " + e.getMessage());
        }
    }

    private String loadJSONFromAsset(Context context, String fileName) {
		StringBuilder jsonString = new StringBuilder();
		InputStream is = null;
		BufferedReader reader = null;

		try {
			// Buka file dari folder assets
			is = context.getAssets().open(fileName);

			// Bungkus InputStream dengan BufferedReader untuk membaca baris demi baris
			reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
		} catch (IOException e) {
			// Log error dengan informasi tambahan
			Log.e("V2Ray", "Error reading JSON file '" + fileName + "': " + e.getMessage(), e);
			return null; // Mengembalikan null jika terjadi kesalahan
		} finally {
			try {
				// Tutup InputStream dan BufferedReader jika tidak null
				if (reader != null) reader.close();
				if (is != null) is.close();
			} catch (IOException e) {
				Log.e("V2Ray", "Error closing file resources: " + e.getMessage(), e);
			}
		}

		// Mengembalikan string JSON yang dibaca
		return jsonString.toString();
	}

    private void modifyV2RayConfig(String accountUri, JSONObject config) throws JSONException, URISyntaxException {
        String[] parts = accountUri.split("://");
        if (parts.length != 2) {
            throw new JSONException("Invalid V2Ray URI format.");
        }
		String sanitizedUri = sanitizeUri(accountUri);
		URI uri = new URI(sanitizedUri);
		String protocol = uri.getScheme();
		String host = uri.getHost();
		int port = uri.getPort();

		String query = uri.getQuery(); // Ambil query string untuk parsing parameter tambahan
		JSONObject queryParams = parseQueryString(query);
        String data = parts[1];
        JSONObject outbound = config.getJSONArray("outbounds").getJSONObject(0);

        outbound.put("protocol", protocol);

        switch (protocol) {
            case "vmess":
                parseVMess(data, outbound);
                break;
            case "vless":
				parseVLess(uri, queryParams, outbound);
				break;
			case "trojan":
				parseTrojan(uri, queryParams, outbound);
				break;
            default:
                throw new JSONException("Unsupported protocol: " + protocol);
        }
    }

	private String sanitizeUri(String uri) {
		int fragmentIndex = uri.indexOf("#");
		if (fragmentIndex != -1) {
			// Hapus fragmen (bagian setelah #) karena tidak diperlukan untuk parsing
			uri = uri.substring(0, fragmentIndex);
		}
		return uri;
	}

	private JSONObject parseQueryString(String query) throws JSONException {
        JSONObject params = new JSONObject();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }

    // Parsers untuk berbagai protokol
    private void parseVMess(String data, JSONObject outbound) throws JSONException {
        String decodedData = new String(android.util.Base64.decode(data, android.util.Base64.DEFAULT));
        JSONObject parsedData = new JSONObject(decodedData);

        JSONObject settings = outbound.getJSONObject("settings");
        JSONArray vnextArray = new JSONArray();
        JSONObject vnextObject = new JSONObject();

        vnextObject.put("address", parsedData.optString("add", "unknown"));
        vnextObject.put("port", parsedData.optInt("port", 0));

        JSONArray usersArray = new JSONArray();
        JSONObject userObject = new JSONObject();
        userObject.put("id", parsedData.optString("id", ""));
        userObject.put("alterId", parsedData.optInt("aid", 0));
        usersArray.put(userObject);

        vnextObject.put("users", usersArray);
        vnextArray.put(vnextObject);

        settings.put("vnext", vnextArray);

        addStreamSettingsv(parsedData, outbound);
    }

    private void parseVLess(URI uri, JSONObject queryParams, JSONObject outbound) throws JSONException {
        String id = uri.getUserInfo();
        String address = uri.getHost();
        int port = uri.getPort();

        JSONObject settings = outbound.getJSONObject("settings");
        JSONArray vnextArray = new JSONArray();
        JSONObject vnextObject = new JSONObject();

        vnextObject.put("address", address);
        vnextObject.put("port", port);

        JSONArray usersArray = new JSONArray();
        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("encryption", queryParams.optString("encryption", "none"));
        usersArray.put(userObject);

        vnextObject.put("users", usersArray);
        vnextArray.put(vnextObject);

        settings.put("vnext", vnextArray);

        addStreamSettings(queryParams, outbound);
    }

    private void parseTrojan(URI uri, JSONObject queryParams, JSONObject outbound) throws JSONException {
        String password = uri.getUserInfo();
        String address = uri.getHost();
        int port = uri.getPort();

        JSONObject settings = outbound.getJSONObject("settings");
        JSONArray serversArray = new JSONArray();
        JSONObject serverObject = new JSONObject();

        serverObject.put("address", address);
        serverObject.put("port", port);
        serverObject.put("password", password);
        serversArray.put(serverObject);

        settings.put("servers", serversArray);

        addStreamSettings(queryParams, outbound);
    }
	
	private void addStreamSettings(JSONObject queryParams, JSONObject outbound) throws JSONException {
        JSONObject streamSettings = outbound.getJSONObject("streamSettings");
        String network = queryParams.optString("type", "tcp");
        streamSettings.put("network", network);

        String security = queryParams.optString("security", "none");
        streamSettings.put("security", security);

		// Jika menggunakan TLS, tambahkan konfigurasi tlsSettings
		if ("tls".equals(security)) {
            JSONObject tlsSettings = new JSONObject();
            tlsSettings.put("allowInsecure", true);
            tlsSettings.put("serverName", queryParams.optString("sni", ""));
            streamSettings.put("tlsSettings", tlsSettings);
        }

		if ("ws".equals(network)) {
            JSONObject wsSettings = new JSONObject();
            wsSettings.put("path", queryParams.optString("path", "/"));
            wsSettings.put("headers", new JSONObject().put("Host", queryParams.optString("host", "")));
            streamSettings.put("wsSettings", wsSettings);
        }

		if ("grpc".equals(network)) {
			JSONObject grpcSettings = new JSONObject();
			// Ambil serviceName dari akun, jika tidak ada gunakan default "grpc-service"
			grpcSettings.put("serviceName", queryParams.optString("serviceName", ""));
			streamSettings.put("grpcSettings", grpcSettings);

			// Tambahkan pengaturan TLS jika gRPC menggunakan TLS
			if ("tls".equals(security)) {
				JSONObject tlsSettings = new JSONObject();
				tlsSettings.put("allowInsecure", true);
				tlsSettings.put("serverName", queryParams.optString("add", "unknown"));
				streamSettings.put("tlsSettings", tlsSettings);
			}
		}
	}
	
	private void addStreamSettingsv(JSONObject parsedData, JSONObject outbound) throws JSONException {
		JSONObject streamSettings = outbound.getJSONObject("streamSettings");
		String network = parsedData.optString("net", "tcp");
		streamSettings.put("network", network);

		String security = parsedData.optString("tls", "none");
		streamSettings.put("security", security);

		// Jika menggunakan TLS, tambahkan konfigurasi tlsSettings
		if ("tls".equals(security)) {
			JSONObject tlsSettings = new JSONObject();
			tlsSettings.put("allowInsecure", true);  // Atur sesuai kebutuhan Anda
			tlsSettings.put("serverName", parsedData.optString("host", "unknown"));
			streamSettings.put("tlsSettings", tlsSettings);
		}

		if ("ws".equals(network)) {
			JSONObject wsSettings = new JSONObject();
			wsSettings.put("path", parsedData.optString("path", "/"));
			wsSettings.put("headers", new JSONObject().put("Host", parsedData.optString("host", "")));
			streamSettings.put("wsSettings", wsSettings);
		}

		if ("grpc".equals(network)) {
			JSONObject grpcSettings = new JSONObject();
			// Ambil serviceName dari akun, jika tidak ada gunakan default "grpc-service"
			grpcSettings.put("serviceName", parsedData.optString("serviceName", "vmess-grpc"));

			streamSettings.put("grpcSettings", grpcSettings);

			// Tambahkan pengaturan TLS jika gRPC menggunakan TLS
			if ("tls".equals(security)) {
				JSONObject tlsSettings = new JSONObject();
				tlsSettings.put("allowInsecure", true);
				tlsSettings.put("serverName", parsedData.optString("add", "unknown"));
				streamSettings.put("tlsSettings", tlsSettings);
			}
		}
	}

    private void doSave() {
        SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();
        String mJson = jsoninput.getEditableText().toString();
        edit.putString(Settings.V2RAY_JSON, mJson);
        edit.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.zacray_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_paste_convert) {
            pasteAndConvert();
            return true;
         

        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
           if (item.getItemId() == R.id.borrar) {
        SharedPreferences prefs2 = mConfig.getPrefsPrivate();
        boolean protect6 = prefs2.getBoolean(Settings.CONFIG_PROTEGER_KEY, false);
        if (protect6) {
          error();

        } else {

          if (!SkStatus.isTunnelActive()) {
            jsoninput.setText("");

          } else {
            Toasty.error(this, (R.string.error_tunnel_service_execution), Toast.LENGTH_SHORT, true)
                .show();
          }
        }
 } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.guardar) {
        SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();
        String mJson = jsoninput.getEditableText().toString();
        edit.putString(Settings.V2RAY_JSON, mJson);
        edit.apply();
        finish();
 } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void error() {
    Toasty.error(this, "Configuracion bloqueada", Toast.LENGTH_SHORT, true).show();
  }

    private void pasteAndConvert() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null && clipboard.getPrimaryClip() != null) {
            ClipData clipData = clipboard.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                String pastedText = clipData.getItemAt(0).getText().toString();
                accountInput.setText(pastedText);
                convertToJson();
            }
        }
    }
}
