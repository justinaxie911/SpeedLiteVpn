/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.activities;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;
import android.view.Menu;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.MenuInflater;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.speedlite.vpn.R;
import com.speedlite.vpn.activities.v2ray;
import com.speedlite.vpn.logger.SkStatus;
import es.dmoral.toasty.Toasty;
import com.airbnb.lottie.LottieAnimationView;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.widget.TextView;
import androidx.preference.PreferenceManager;
import android.graphics.Color;
import android.content.ClipData;
import android.content.ClipboardManager;
import com.speedlite.vpn.config.Settings;
import com.amrdeveloper.codeview.CodeView;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.speedlite.vpn.util.securepreferences.SecurePreferences;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class v2ray extends BaseActivity {

  private Toolbar tb;
  private View changelog, license, dev;
  private AlertDialog.Builder ab;
  private TextView bloqueda;
  private FloatingActionButton fab;
  private LottieAnimationView block;
  private CodeView v2;

  private ClipboardManager clipboardManager;
  private CodeView codeView;
  public Settings mConfig;
  private AdView adsBannerView;
  private LanguageManager languageManager;
  private LanguageName currentLanguage = LanguageName.PYTHON;
  private ThemeName currentTheme = ThemeName.FIVE_COLOR;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_v2ray);

    final SharedPreferences defaultSharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(this);

    Settings settings = new Settings(this);
    mConfig = settings;
    tb = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(tb);
    block = findViewById(R.id.block);
    //	v2 = findViewById(R.id.v2);

    block.loop(true);
    block.playAnimation();
    bloqueda = findViewById(R.id.bloqueda);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    codeView = findViewById(R.id.jsontInput);
    codeView.setEnableLineNumber(true);
    codeView.setLineNumberTextColor(Color.GREEN);
    codeView.setLineNumberTextSize(38f);

    languageManager = new LanguageManager(this, codeView);
    languageManager.applyTheme(currentLanguage, currentTheme);

    clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    doUpdateLayout();

        setupViews();
    final SecurePreferences prefsPrivate = mConfig.getPrefsPrivate();
    codeView.setText(prefsPrivate.getString(Settings.V2RAY_JSON, ""));
  }

  private void doUpdateLayout() {
    SharedPreferences prefs = mConfig.getPrefsPrivate();
    boolean isRunning = SkStatus.isTunnelActive();
    boolean protect = prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false);

    if (protect) {
      codeView.setEnabled(false);

      block.setVisibility(View.VISIBLE);

      bloqueda.setVisibility(View.VISIBLE);
      //   v2.setVisibility(View.GONE);
      codeView.setVisibility(View.GONE);

    } else {

      if (SkStatus.isTunnelActive()) {

        codeView.setEnabled(false);
      }

      // v2.setVisibility(View.VISIBLE);
      codeView.setVisibility(View.VISIBLE);
      block.setVisibility(View.GONE);
      bloqueda.setVisibility(View.GONE);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.v2, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.pega:
        SharedPreferences prefs = mConfig.getPrefsPrivate();
        boolean isRunning = SkStatus.isTunnelActive();
        boolean protect = prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false);
        if (protect) {
          error();

        } else {

          if (!SkStatus.isTunnelActive()) {

            pasteAndConvert();
          } else {
            Toasty.error(this, (R.string.error_tunnel_service_execution), Toast.LENGTH_SHORT, true)
                .show();
          }
        }

        break;
            
case R.id.action_paste_convert:
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null && clipboard.getPrimaryClip() != null) {
            ClipData clipData = clipboard.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                String pastedText = clipData.getItemAt(0).getText().toString();
                accountInput.setText(pastedText);
                convertToJson();
            }
        }

        break;
      case R.id.borrar:
        SharedPreferences prefs2 = mConfig.getPrefsPrivate();
        boolean protect6 = prefs2.getBoolean(Settings.CONFIG_PROTEGER_KEY, false);
        if (protect6) {
          error();

        } else {

          if (!SkStatus.isTunnelActive()) {
            codeView.setText("");

          } else {
            Toasty.error(this, (R.string.error_tunnel_service_execution), Toast.LENGTH_SHORT, true)
                .show();
          }
        }

        break;

      case R.id.guardar:
            
            
       SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();
        String mJson = codeView.getEditableText().toString();
        edit.putString(Settings.V2RAY_JSON, mJson);
        edit.apply();
        finish();
            
        break;
    }

    return super.onOptionsItemSelected(item);
  }

    private void setupViews() {
        block = findViewById(R.id.block);
    //	v2 = findViewById(R.id.v2);

    block.loop(true);
    block.playAnimation();
    bloqueda = findViewById(R.id.bloqueda);
        accountInput = (TextInputEditText) findViewById(R.id.accountInput);
        codeView = findViewById(R.id.jsontInput);
    codeView.setEnableLineNumber(true);
    codeView.setLineNumberTextColor(Color.GREEN);
    codeView.setLineNumberTextSize(38f);

        codeView.setText(mConfig.getPrivString(Settings.V2RAY_JSON));

        // Add a TextWatcher to the jsoninput field to automatically save the JSON
        codeView.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
					// Nothing to do here
				}

				@Override
				public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
					// Auto-save whenever the text changes
					doSave();
                    doUpdateLayout();
				}

				@Override
				public void afterTextChanged(Editable editable) {
					// Nothing to do here
				}
			});
    }
     private void doSave() {
        SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();
        String mJson = codeView.getEditableText().toString();
        edit.putString(Settings.V2RAY_JSON, mJson);
        edit.apply();
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
	

    private void convertToJson() {
        try {
            String accountUri = accountInput.getText().toString().trim();
            if (accountUri.isEmpty()) return;

            String templateJson = loadJSONFromAsset(v2ray.this, "v2ray_config.json");
            if (templateJson == null) return;

            JSONObject config = new JSONObject(templateJson);
            modifyV2RayConfig(accountUri, config);

            generatedJson = config.toString(4);
            codeView.setText(generatedJson);

        } catch (JSONException | URISyntaxException e) {
            Log.e("v2ray", "Error: " + e.getMessage());
        }
        }
     
    private String generatedJson = "";
    private TextInputEditText accountInput;
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
    
  private void error() {
    Toasty.error(this, "Configuracion bloqueada", Toast.LENGTH_SHORT, true).show();
  }

  private void pasteFromClipboard() {
    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    ClipData clipData = clipboard.getPrimaryClip();
    if (clipData != null && clipData.getItemCount() > 0) {
      CharSequence text = clipData.getItemAt(0).getText();
      codeView.setText(text);
    }
  }
}
