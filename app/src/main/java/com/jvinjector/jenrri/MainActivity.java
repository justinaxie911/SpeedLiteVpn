/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.jvinjector.jenrri.logger.zacdevz;
import com.jvinjector.jenrri.zacdevz.ZacDevzRay;
import me.dawson.proxyserver.ui.ProxySettings;
// import android.preference.PreferenceManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.Html;
import android.util.Log;
import android.view.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.telephony.TelephonyManager;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.speedlite.vpn.servers.ServersActivity;
import com.speedlite.vpn.service.SocksDNSService;
import com.speedlite.vpn.util.AESCrypt;
import com.speedlite.vpn.util.ConfigUpdate;
import com.speedlite.vpn.util.ConfigUtil;
import com.thecode.aestheticdialogs.*;
import com.speedlite.vpn.activities.BaseActivity;
import com.speedlite.vpn.activities.ConfigExportFileActivity;
import com.speedlite.vpn.activities.ConfigGeralActivity;
import com.speedlite.vpn.activities.ConfigImportFileActivity;
import com.speedlite.vpn.activities.LicenseActivity;
import com.speedlite.vpn.activities.v2ray;
import com.speedlite.vpn.adapter.LogsAdapter;
import com.speedlite.vpn.config.ConfigParser;
import com.speedlite.vpn.config.Settings;
import com.speedlite.vpn.fragments.ProxyRemoteDialogFragment;
import com.speedlite.vpn.fragments.sni;
import com.speedlite.vpn.logger.ConnectionStatus;
import com.speedlite.vpn.logger.SkStatus;
import com.speedlite.vpn.model.ExceptionHandler;
import com.speedlite.vpn.tunnel.TunnelManagerHelper;
import com.speedlite.vpn.tunnel.TunnelUtils;
import com.speedlite.vpn.util.Utils;
import com.speedlite.vpn.util.VPNUtils;


import com.speedlite.vpn.util.securepreferences.SecurePreferences;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import android.net.Uri;

/*SRC BY LATAMSRC*/
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SkStatus.StateListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String UPDATE_VIEWS = "MainUpdate";
    private Settings mConfig;
    private ConfigUtil config;
    private MenuItem item1;
    private AlertDialog searchupdate;
    byte[] YourDataA;
    ProgressDialog pd;
    public static final String EncrypAESPASSWORDLatamSRC = new Object() {
        int LatamSRC;
        public String toString() {
            byte[] ElMandarinSniff = new byte[9];
            LatamSRC = 1784375619;
            ElMandarinSniff[0] = (byte) (LatamSRC >>>
                    Integer.parseInt((new Object() {
                        int Smandchat;
                        public String toString() {
                            byte[] iLoveLatamSRC = new byte[2];
                            Smandchat = -846088739;
                            iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                            Smandchat = 613358785;
                            iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                            return new String(iLoveLatamSRC);}}.toString())));
            LatamSRC = 1516383396;
            ElMandarinSniff[1] = (byte) (LatamSRC >>>
                    Integer.parseInt((new Object() {
                        int Smandchat;
                        public String toString() {
                            byte[] iLoveLatamSRC = new byte[2];
                            Smandchat = -846088739;
                            iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                            Smandchat = 613358785;
                            iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                            return new String(iLoveLatamSRC);}}.toString())));
            LatamSRC = 379252189;
            ElMandarinSniff[2] = (byte) (LatamSRC >>>
                    Integer.parseInt((new Object() {
                        int Smandchat;
                        public String toString() {
                            byte[] iLoveLatamSRC = new byte[2];
                            Smandchat = -846088739;
                            iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                            Smandchat = 613358785;
                            iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                            return new String(iLoveLatamSRC);}}.toString())));
            LatamSRC = -112202151;
            ElMandarinSniff[3] = (byte) (LatamSRC >>>
                    Integer.parseInt((new Object() {
                        int Smandchat;
                        public String toString() {
                            byte[] iLoveLatamSRC = new byte[2];
                            Smandchat = -846088739;
                            iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                            Smandchat = 613358785;
                            iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                            return new String(iLoveLatamSRC);}}.toString())));
            LatamSRC = -896364991;
            ElMandarinSniff[4] = (byte) (LatamSRC >>>
                    Integer.parseInt((new Object() {
                        int Smandchat;
                        public String toString() {
                            byte[] iLoveLatamSRC = new byte[2];
                            Smandchat = -846088739;
                            iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                            Smandchat = 613358785;
                            iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                            return new String(iLoveLatamSRC);}}.toString())));
            LatamSRC = -2031653590;
            ElMandarinSniff[5] = (byte) (LatamSRC >>>
                    Integer.parseInt((new Object() {
                        int Smandchat;
                        public String toString() {
                            byte[] iLoveLatamSRC = new byte[2];
                            Smandchat = -846088739;
                            iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                            Smandchat = 613358785;
                            iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                            return new String(iLoveLatamSRC);}}.toString())));
            LatamSRC = -1516632524;
            ElMandarinSniff[6] = (byte) (LatamSRC >>>
                    Integer.parseInt((new Object() {
                        int Smandchat;
                        public String toString() {
                            byte[] iLoveLatamSRC = new byte[2];
                            Smandchat = -846088739;
                            iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                            Smandchat = 613358785;
                            iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                            return new String(iLoveLatamSRC);}}.toString())));
            LatamSRC = -325114280;
            ElMandarinSniff[7] = (byte) (LatamSRC >>>
                    Integer.parseInt((new Object() {
                        int Smandchat;
                        public String toString() {
                            byte[] iLoveLatamSRC = new byte[2];
                            Smandchat = -846088739;
                            iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                            Smandchat = 613358785;
                            iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                            return new String(iLoveLatamSRC);}}.toString())));
            LatamSRC = 654247538;
            ElMandarinSniff[8] = (byte) (LatamSRC >>>
                    Integer.parseInt((new Object() {
                        int Smandchat;
                        public String toString() {
                            byte[] iLoveLatamSRC = new byte[2];
                            Smandchat = -846088739;
                            iLoveLatamSRC[0] = (byte) (Smandchat >>> 22);
                            Smandchat = 613358785;
                            iLoveLatamSRC[1] = (byte) (Smandchat >>> 7);
                            return new String(iLoveLatamSRC);}}.toString())));
            return new String(ElMandarinSniff);
        }}.toString();
private TextView xip,xip1;
    int status = 0;
    private InterstitialAd interstitialAd;
    private InterstitialAd mInterstitialAd;
    private long saved_ads_time;
    private boolean mTimerEnabled;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private CountDownTimer mCountDownTimer;
    private TextView mTextViewCountDown;
    private SharedPreferences sp;
    private CountDownTimer mBtnCountDown;
    private long mTimeLeftBtn;
    private long mEndTime;
    private TextView vencimentoDate;
    private TextView diasRestantes;
    private TextView avisos;
    private TextView usuario;
    private String URLServer = "ADD LINK HERE";
    private String checkuser;
    boolean isLoading;
    private AdView adsBannerView;
    private RewardedAd rewardedAd;
    private Toolbar toolbar_main;
    private LinearLayout tunnelCardViewgatesccn;
    private LinearLayout eresviplatamsrc, erespobrelatamsrc;
    private TextView servername, serverinfo;
    private LinearLayout serverlayout;
    private LinearLayout estadolatamsrc1, estadolatamsrc2;
    private ImageView serverimage;
    private Handler mHandler;
    private LinearLayout mainLayout;
    private Button starterButton;
    private AlertDialog ppd;
    private EditText textInput;
    private TextView app_info_text;
    private CardView configMsgLayout;
    private SharedPreferences spref;
    private TextView configMsgText;
    private static final String NOTIFICATION_PERMISSION = "android.permission.POST_NOTIFICATIONS";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String INTER = "ca-app-pub-3940173712";
    private static final String[] tabTitle = {"Inicio", "Registro", "Ayuda"};
    private LogsAdapter mLogAdapter;
    private RecyclerView logList;
    private String messageUpdate;
    private ViewPager vp;
    private TabLayout tabs;
    private View changelog, license, devw, dev;
    private MenuItem settings;
    private MenuItem ifolder, sharet, changelatamsrc;
    public static boolean isHomeTab = true;
    private CardView tunnelLayout;
    private CardView card_tools1;
    private CardView card_tools2;
    private CardView card_tools3;
    private LinearProgressIndicator progress_indicator;
    private CheckBox dnsCheckBox;
    private CardView connectionCardview;
    private TextView tunnelInfo;
    private TextInputLayout payloadLayout;
    private View proxyLayout, payload;
    private static final int REQUEST_CODE = 1;
    private View sslLayout;
    private String proxyStr;
    private TextView proxyText;
    private FloatingActionButton deleteLogs, infow;
    private TextInputEditText payloadEdit;
    private TextView sniText;
    private NavigationView drawerNavigationView;
    private MenuItem auth;
    private MenuItem settingsSSH;
    private MenuItem MENUSTRINGVIPLATAMSRC;
    private InterstitialAd success;
    public static SharedPreferences sShared;
    private TextView custom;
    private LinearLayout layoutForceTLS;
    private LinearLayout LayoutForceTLS;
    private ConstraintLayout save, expor;
    private Spinner Spinner;
    private BottomSheetBehavior bottomSheetBehavior;
    private Spinner spinner;
    private WindowManager windowManager;
    private View alertaFlotante;
    private BottomSheetBehavior<NestedScrollView> behavior;
    private ArrayList<String> ArrayList;private TextView TuTextView;private TextView TuTextView1;

    private TextView bytesOut,bytesIn;private TextView sZacDevz;
    
    private TextView bytesOut1,bytesIn1;private TextView sZacDevz1,version1;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        mConfig = new Settings(this);
        ReviewManager manager = ReviewManagerFactory.create(this);
        sShared = this.mConfig.getPrefsPrivate();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        SharedPreferences prefs = getSharedPreferences(MyApplication.PREFS_GERAL, Context.MODE_PRIVATE);
        boolean showFirstTime = prefs.getBoolean("connect_first_time", true);
        if (showFirstTime) {
            SharedPreferences.Editor pEdit = prefs.edit();
            pEdit.putBoolean("connect_first_time", false);
            pEdit.apply();
            Settings.setDefaultConfig(this);
        }

        doLayout();
        sp = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        if (mConfig.cambiarestadolatamsrc()) {
            setMainView();
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_VIEWS);
        LocalBroadcastManager.getInstance(this).registerReceiver(mActivityReceiver, filter);

        doUpdateLayout();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            // Aquí puedes verificar si los permisos fueron concedidos y actuar en consecuencia
        }
    }

    private void doLayout() {
        setContentView(R.layout.activity_main_drawer);
        final SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        final SecurePreferences prefsPrivate = this.mConfig.getPrefsPrivate();
        config = new ConfigUtil(this);
        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        tunnelCardViewgatesccn = (LinearLayout) findViewById(R.id.tunnelCardViewgatesccn);
        eresviplatamsrc = (LinearLayout) findViewById(R.id.eresviplatamsrc);
        erespobrelatamsrc = (LinearLayout) findViewById(R.id.erespobrelatamsrc);
        doDrawerMain(toolbar_main);
        setSupportActionBar(toolbar_main);
        doTabs();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && checkSelfPermission(NOTIFICATION_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{NOTIFICATION_PERMISSION}, PERMISSION_REQUEST_CODE);
        }
        updateConfig(true);
bytesIn1 = (TextView) findViewById(R.id.bytes_in1);
        bytesOut1 = (TextView) findViewById(R.id.bytes_out1);
        xip = (TextView) findViewById(R.id.xip);
        xip1 = (TextView) findViewById(R.id.xip1);
        bytesIn = (TextView) findViewById(R.id.bytes_in);
        bytesOut = (TextView) findViewById(R.id.bytes_out);
        progress_indicator = (LinearProgressIndicator) findViewById(R.id.progress_indicator);
        mainLayout = (LinearLayout) findViewById(R.id.activity_mainLinearLayout);
        starterButton = (Button) findViewById(R.id.activity_starterButtonMain);
        starterButton.setOnClickListener(this);
        configMsgLayout = (CardView) findViewById(R.id.activitymainCardView1);
        configMsgText = (TextView) findViewById(R.id.activity_mainMensagemConfigTextView);
        tunnelLayout = (CardView) findViewById(R.id.tunnelCardView);
        tunnelLayout.setOnClickListener(this);
        tunnelInfo = (TextView) findViewById(R.id.activitymainTextView1);
        connectionCardview = (CardView) findViewById(R.id.connection_cardView);
        proxyText = (TextView) findViewById(R.id.proxyText);
        payloadEdit = (TextInputEditText) findViewById(R.id.payloadEdit);
        payload = (View) findViewById(R.id.payload);
        proxyLayout = (View) findViewById(R.id.proxyLayout);
        proxyLayout.setOnClickListener(this);
        sslLayout = (View) findViewById(R.id.sslLayout);
        sslLayout.setOnClickListener(this);
        sniText = (TextView) findViewById(R.id.sslText);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mLogAdapter = new LogsAdapter(layoutManager, this);
        mLogAdapter.scrollToLastPosition();
        estadolatamsrc1 = (LinearLayout) findViewById(R.id.estadolatamsrc1);
        estadolatamsrc2 = (LinearLayout) findViewById(R.id.estadolatamsrc2);
        serverlayout = findViewById(R.id.layout_mainservers);
        serverlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ServersActivity.class));
            }
        });
        mTextViewCountDown = (TextView) findViewById(R.id.timerTextView);
        serverimage = findViewById(R.id.imagemainlayout);
        servername = findViewById(R.id.nombremainlayout);
        serverinfo = findViewById(R.id.infomainlayout);
        TextView version = (TextView) findViewById(R.id.config_version_info); // version update
        version.setText("" + config.getVersion()); //version update
        adsBannerView = (AdView) findViewById(R.id.bannerhome);
TextView version1 = (TextView) findViewById(R.id.config_version_info1); // version update
        version1.setText("" + config.getVersion()); //version update
        sZacDevz1 = (TextView) findViewById(R.id.monsour_stats1);
        sZacDevz = (TextView) findViewById(R.id.monsour_stats);
        TelephonyManager telephonyManager = ((TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE));
		String simOperatorName = telephonyManager.getSimOperatorName();

TuTextView = (TextView) findViewById(R.id.operador);
TuTextView.setText(simOperatorName);
        
        
TuTextView1 = (TextView) findViewById(R.id.operador1);
TuTextView1.setText(simOperatorName);

    }

    private synchronized void doSaveData() {
        SharedPreferences prefs = mConfig.getPrefsPrivate();
        SharedPreferences.Editor edit = prefs.edit();
        if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
            if (payloadEdit != null && !prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
                if (mainLayout != null) mainLayout.requestFocus();
                edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payloadEdit.getText().toString());
            }
        }
        int tunnelType = prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);
        if (tunnelType == Settings.bTUNNEL_TYPE_SLOWDNS) {
            edit.putString(Settings.SERVIDOR_KEY, "127.0.0.1");
            edit.putString(Settings.SERVIDOR_PORTA_KEY, "2222");
        }

        if (tunnelType == Settings.bTUNNEL_TYPE_UDP) {
            edit.putString(Settings.UDP_WINDOW, "8888");
        }

        edit.apply();
    }

    public void hype(View v) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
        builder.setTitle("Unete al grupo de Telegram");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final AlertDialog alertDialog;
        LayoutInflater inflater = getLayoutInflater();
        View v2 = inflater.inflate(R.layout.jointelegram_ly, null);
        builder.setView(v2);
        alertDialog = builder.create();

        alertDialog.show();

    }
    private void ZacDevzWork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi.isConnected())
        {
            xip.setText("WI-FI: "+TunnelUtils.getLocalIpAddress());

        } else if (mMobile.isConnected()) {
       
			//toolbar_main.setSubtitle("RED MOVIL: "+TunnelUtils.getLocalIpAddress());
			//toolbar_main.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitleText);
            xip.setText(""+TunnelUtils.getLocalIpAddress());


        } else {
            xip.setText("NO CONNECTION");

        }
	} 
    private void ZacDevzWork1() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi.isConnected())
        {
            xip1.setText("WI-FI: "+TunnelUtils.getLocalIpAddress());

        } else if (mMobile.isConnected()) {
       
			//toolbar_main.setSubtitle("RED MOVIL: "+TunnelUtils.getLocalIpAddress());
			//toolbar_main.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitleText);
            xip1.setText(""+TunnelUtils.getLocalIpAddress());


        } else {
            xip1.setText("NO CONNECTION");

        }
	} 
private String render_bandwidth(double bw) {
        String postfix;
        float div;
        Object[] objArr;
        float bwf = (float) bw;
        if (bwf >= 1.0E12f) {
            postfix = "TB";
            div = 1.0995116E12f;
        } else if (bwf >= 1.0E9f) {
            postfix = "GB";
            div = 1.0737418E9f;
        } else if (bwf >= 1000000.0f) {
            postfix = "MB";
            div = 1048576.0f;
        } else if (bwf >= 1000.0f) {
            postfix = "KB";
            div = 1024.0f;
        } else {
            objArr = new Object[1];
            objArr[0] = Float.valueOf(bwf);
            return String.format("%.0f", objArr);
        }
        objArr = new Object[2];
        objArr[0] = Float.valueOf(bwf / div);
        objArr[1] = postfix;
        return String.format("%.2f %s", objArr);
    }
    private void ZacDevzCall() {
        boolean isRunning = SkStatus.isTunnelActive();
        long mUpload, mDownload, saved_Send ,saved_Down/*,up, down*/;
        String saved_date, tDate;
        List<Long> allData;
        allData = zacdevz.findData();
        mDownload = allData.get(0);
        mUpload = allData.get(1);
        zacdevz.damn(mDownload, mUpload);
        //down = mDownload;
        //up = mUpload;
        SharedPreferences myData = mConfig.getPrefsPrivate();
        Calendar ca = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        tDate = sdf.format(ca.getTime());
        saved_date = myData.getString("today_date", "empty");
        SharedPreferences.Editor editor = myData.edit();
        if (saved_date.equals(tDate)) {
            saved_Send = myData.getLong("UP_DATA", 0);
            saved_Down = myData.getLong("DOWN_DATA", 0);
            editor.putLong("UP_DATA", mUpload + saved_Send);
            editor.putLong("DOWN_DATA", mDownload + saved_Down);
            editor.apply();
        } else {
            editor.clear();
            editor.putString("today_date", tDate);
            editor.apply();
        }
        if(isRunning){
            bytesOut.setText(render_bandwidth(myData.getLong("UP_DATA", 0)));
            bytesIn.setText(render_bandwidth(myData.getLong("DOWN_DATA", 0)));
            if(isRunning){
            bytesOut1.setText(render_bandwidth(myData.getLong("UP_DATA", 0)));
            bytesIn1.setText(render_bandwidth(myData.getLong("DOWN_DATA", 0)));
        }else{
            myData.edit().putLong("UP_DATA", 0).apply();
            myData.edit().putLong("DOWN_DATA", 0).apply();
        }
    }
	
}
    private void doUpdateLayout() {
        SharedPreferences prefs = mConfig.getPrefsPrivate();
        final SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRunning = SkStatus.isTunnelActive();
        setStarterButton(starterButton, this);
        boolean protect = prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false);
        String proxy = mConfig.getPrivString(Settings.PROXY_IP_KEY);

        int msgVisibility = View.GONE;
        String msgText = "";

        if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
            String msg = mConfig.getPrivString(Settings.CONFIG_MENSAGEM_KEY);
            if (!msg.isEmpty()) {
                msgText = msg.replace("\n", "<br/>");
                msgVisibility = View.VISIBLE;
            }

            if (mConfig.getPrivString(Settings.PROXY_IP_KEY).isEmpty() || mConfig.getPrivString(Settings.PROXY_PORTA_KEY).isEmpty()) {
            }
        }
        configMsgText.setText(msgText.isEmpty() ? "" : Html.fromHtml(msgText));
        configMsgLayout.setVisibility(msgVisibility);

        if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
            proxyText.setText("******");

            proxyLayout.setEnabled(false);

        } else {
            proxyLayout.setEnabled(!isRunning);

            if (proxy.equals("")) {
                proxyText.setText(R.string.squid);
            } else {

                proxyText.setText(String.format("%s:%s", proxy, mConfig.getPrivString(Settings.PROXY_PORTA_KEY)));
            }
        }

        int tunnelType = prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);
        if (tunnelType == Settings.bTUNNEL_TYPE_SLOWDNS) {
            Menu menuNav = drawerNavigationView.getMenu();
            settingsSSH = menuNav.findItem(R.id.miSettingsSSH);
            settingsSSH.setIcon(R.drawable.ic_stat_dnsurgent_color);
            settingsSSH.setTitle(R.string.slowdns_configuration);
        }

        if (tunnelType == Settings.bTUNNEL_TYPE_UDP) {
            Menu menuNav = drawerNavigationView.getMenu();
            settingsSSH = menuNav.findItem(R.id.miSettingsSSH);
            settingsSSH.setIcon(R.drawable.ic_stat_hysteria);
            settingsSSH.setTitle(R.string.settings_udp);
        }

        if (tunnelType == Settings.bTUNNEL_TYPE_SSH) {
            Menu menuNav = drawerNavigationView.getMenu();
            settingsSSH.setIcon(R.drawable.ic_cloud_line);
            settingsSSH = menuNav.findItem(R.id.miSettingsSSH);
            settingsSSH.setTitle(R.string.settings_ssh);
        }

        if (tunnelType == Settings.bTUNNEL_TYPE_V2RAY) {
            Menu menuNav = drawerNavigationView.getMenu();

            settingsSSH = menuNav.findItem(R.id.miSettingsSSH);
            settingsSSH.setIcon(R.drawable.ic_stat_v2ray);
            settingsSSH.setTitle(R.string.settings_v2);
        }
        if (tunnelType == Settings.bTUNNEL_TYPE_V2RAY) {
            Menu menuNav = drawerNavigationView.getMenu();

            settingsSSH = menuNav.findItem(R.id.miSettingsSSH);
            settingsSSH.setIcon(R.drawable.ic_stat_v2ray);
            settingsSSH.setTitle(R.string.settings_v2);
        }
        Menu gaga = drawerNavigationView.getMenu();
        if (mConfig.cambiarestadolatamsrc()) {//vip activo modo latamsrc
            MENUSTRINGVIPLATAMSRC = gaga.findItem(R.id.copiartokenlatamsrc);
            MENUSTRINGVIPLATAMSRC.setIcon(R.drawable.tlatamsrc);
            MENUSTRINGVIPLATAMSRC.setTitle("Token ID");

        } else {
            MENUSTRINGVIPLATAMSRC = gaga.findItem(R.id.copiartokenlatamsrc);
            MENUSTRINGVIPLATAMSRC.setIcon(R.drawable.ic_fingerprint);
            MENUSTRINGVIPLATAMSRC.setTitle("Harware ID");
        }

        switch (tunnelType) {
            case Settings.bTUNNEL_TYPE_SSH_DIRECT:
                if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
                    connectionCardview.setVisibility(View.VISIBLE);
                    payload.setVisibility(View.VISIBLE);
                payloadEdit.setVisibility(View.VISIBLE);
                    proxyLayout.setVisibility(View.GONE);
                    sslLayout.setVisibility(View.GONE);
                    tunnelInfo.setText(getString(R.string.direct) + getString(R.string.custom_payload1));
                    if (protect) {
                        payloadEdit.setEnabled(false);
                        payloadEdit.setText("******");
                    } else {
                        payloadEdit.setEnabled(!isRunning);
                        payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
                    }

                } else {
                    connectionCardview.setVisibility(View.GONE);
                    payload.setVisibility(View.GONE);
                payloadEdit.setVisibility(View.GONE);
                    proxyLayout.setVisibility(View.GONE);
                    sslLayout.setVisibility(View.GONE);
                    tunnelInfo.setText(getString(R.string.direct));
                }
                break;
            case Settings.bTUNNEL_TYPE_UDP:
                connectionCardview.setVisibility(View.GONE);
                tunnelInfo.setText("Hysteria");

                break;

            case Settings.bTUNNEL_TYPE_V2RAY:
                connectionCardview.setVisibility(View.GONE);
                tunnelInfo.setText("V2ray");

                break;

            case Settings.bTUNNEL_TYPE_SSL_RP:

                if (protect) {
                    payloadEdit.setEnabled(false);
                    payloadEdit.setText("******");
                } else {
                    payloadEdit.setEnabled(!isRunning);
                    payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
                    sslLayout.setEnabled(!isRunning);
                    String ssl = mConfig.getPrivString(Settings.CUSTOM_SNI);
                    if (ssl.isEmpty()) {
                        sniText.setText("www.google.com");
                    } else {
                        sniText.setText(ssl);
                    }
                }
                connectionCardview.setVisibility(View.VISIBLE);
                proxyLayout.setVisibility(View.VISIBLE);
                sslLayout.setVisibility(View.VISIBLE);
                payload.setVisibility(View.VISIBLE);
                tunnelInfo.setText(getString(R.string.sslrp));

                break;

            case Settings.bTUNNEL_TYPE_SSH_SSLTUNNEL:
                if (protect) {
                    sslLayout.setEnabled(false);
                    sniText.setText("******");
                } else {
                    sslLayout.setEnabled(!isRunning);
                    String ssl = mConfig.getPrivString(Settings.CUSTOM_SNI);
                    if (ssl.isEmpty()) {
                        sniText.setText("www.google.com");
                    } else {
                        sniText.setText(ssl);
                    }
                }
                connectionCardview.setVisibility(View.VISIBLE);
                payload.setVisibility(View.GONE);
            payloadEdit.setVisibility(View.GONE);
                proxyLayout.setVisibility(View.GONE);
                sslLayout.setVisibility(View.VISIBLE);
                tunnelInfo.setText(getString(R.string.ssl));
                break;

            case Settings.bTUNNEL_TYPE_PAY_SSL:
                if (protect) {
                    payloadEdit.setEnabled(false);
                    payloadEdit.setText("******");
                    sslLayout.setEnabled(false);
                    sniText.setText("******");
                } else {
                    payloadEdit.setEnabled(!isRunning);
                    payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
                    sslLayout.setEnabled(!isRunning);
                    String ssl = mConfig.getPrivString(Settings.CUSTOM_SNI);
                    if (ssl.isEmpty()) {
                        sniText.setText("com.google.com");
                    } else {
                        sniText.setText(ssl);
                    }
                }
                connectionCardview.setVisibility(View.VISIBLE);
                payload.setVisibility(View.VISIBLE);
            payloadEdit.setVisibility(View.VISIBLE);
                proxyLayout.setVisibility(View.GONE);
                sslLayout.setVisibility(View.VISIBLE);
                tunnelInfo.setText(getString(R.string.sslpay));
                break;

            case Settings.bTUNNEL_TYPE_SLOWDNS:
                connectionCardview.setVisibility(View.GONE);
                tunnelInfo.setText(getString(R.string.slowdns));

                break;

            case Settings.bTUNNEL_TYPE_SSH_PROXY:
                if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
                    connectionCardview.setVisibility(View.VISIBLE);

                    proxyLayout.setVisibility(View.VISIBLE);
                    if (protect) {
                        payloadEdit.setEnabled(false);
                        payloadEdit.setText("******");
                    } else {
                        payloadEdit.setEnabled(!isRunning);
                        payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
                    }
                }
                if (protect) {
                    sslLayout.setEnabled(false);
                    sniText.setText("******");
                } else {
                    sslLayout.setEnabled(!isRunning);
                    String ssl = mConfig.getPrivString(Settings.CUSTOM_SNI);
                    if (ssl.isEmpty()) {
                        sniText.setText("com.google.com");
                    } else {
                        sniText.setText(ssl);
                    }
                }
                connectionCardview.setVisibility(View.VISIBLE);
                payload.setVisibility(View.VISIBLE);
            payload.setVisibility(View.VISIBLE);
                proxyLayout.setVisibility(View.VISIBLE);
                sslLayout.setVisibility(View.GONE);
                tunnelInfo.setText(getString(R.string.http) + getString(R.string.custom_payload1));
                break;
        }
    }

    /**
     * Tunnel SSH
     */
    public void doTabs() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // deleteLogs = (FloatingActionButton)findViewById(R.id.delete_log);
        mLogAdapter = new LogsAdapter(layoutManager, this);
        logList = (RecyclerView) findViewById(R.id.recyclerLog);
        logList.setAdapter(mLogAdapter);
        logList.setLayoutManager(layoutManager);
        mLogAdapter.scrollToLastPosition();
        vp = (ViewPager) findViewById(R.id.viewpager);
        tabs = (TabLayout) findViewById(R.id.tablayout);
        vp.setAdapter(new MyAdapter(Arrays.asList(tabTitle)));
        vp.setOffscreenPageLimit(3);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setupWithViewPager(vp);
        vp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    settings.setIcon(R.drawable.ic_settings);
                    ifolder.setVisible(true);
                    settings.setVisible(true);
                    sharet.setVisible(false);
                    ifolder.setIcon(R.drawable.ic_config);
                    isHomeTab = true;
                } else if (position == 1) {
                    ifolder.setVisible(false);
                    sharet.setVisible(true);
                    settings.setVisible(true);
                    settings.setIcon(R.drawable.ic_delete_forever_white_24dp);
                    isHomeTab = false;
                } else if (position == 2) {
                    ifolder.setVisible(false);
                    sharet.setVisible(false);
                    settings.setVisible(false);
                    settings.setIcon(R.drawable.ic_delete_forever_white_24dp);
                    isHomeTab = false;
                }
            }
        });
    }


    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO: Implement this method
            return 2;
        }

        @Override
        public boolean isViewFromObject(View p1, Object p2) {
            // TODO: Implement this method
            return p1 == p2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int[] ids = new int[]{R.id.tab1, R.id.tab2};
            int id = 0;
            id = ids[position];
            // TODO: Implement this method
            return findViewById(id);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO: Implement this method

            return titles.get(position);

        }

        private List<String> titles;

        public MyAdapter(List<String> str) {
            titles = str;
        }
    }

    public void startOrStopTunnel(Activity activity) {
        SharedPreferences prefsPrivate = new Settings(activity).getPrefsPrivate();

        if (SkStatus.isTunnelActive()) {
            TunnelManagerHelper.stopSocksHttp(activity);
            stop();

        } else {
            // oculta teclado se vísivel, tá com bug, tela verde
            // Utils.hideKeyboard(activity);
            Settings config = new Settings(activity);
            Intent intent = new Intent(activity, LaunchVpn.class);
            intent.setAction(Intent.ACTION_MAIN);
            if (config.getHideLog()) {
                intent.putExtra(LaunchVpn.EXTRA_HIDELOG, true);
            }
            activity.startActivity(intent);
        }
    }

    public void setStarterButton(Button starterButton, Activity activity) {
        String state = SkStatus.getLastState();
        boolean isRunning = SkStatus.isTunnelActive();

        if (starterButton != null) {
            int resId;
            SharedPreferences prefsPrivate = new Settings(activity).getPrefsPrivate();

            if (ConfigParser.isValidadeExpirou(prefsPrivate.getLong(Settings.CONFIG_VALIDADE_KEY, 0))) {
                resId = R.string.expired;
                starterButton.setEnabled(false);
                if (isRunning) {
                    startOrStopTunnel(activity);
                }
            } else if (prefsPrivate.getBoolean(Settings.BLOQUEAR_ROOT_KEY, false) && ConfigParser.isDeviceRooted(activity)) {
                resId = R.string.blocked;
                starterButton.setEnabled(false);

                Toast.makeText(activity, R.string.error_root_detected, Toast.LENGTH_SHORT).show();

                if (isRunning) {
                    startOrStopTunnel(activity);
                }
            } else if (SkStatus.SSH_INICIANDO.equals(state)) {
                resId = R.string.stop;

                progress_indicator.setVisibility(View.VISIBLE);
            } else if (SkStatus.SSH_PARANDO.equals(state)) {
                resId = R.string.state_stopping;
                stop();
                progress_indicator.setVisibility(View.GONE);
            } else {
                resId = isRunning ? R.string.stop : R.string.start;
            }

            starterButton.setText(resId);
        }
    }

    /**
     * Drawer Main
     */
    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle toggle;

    public void doDrawerMain(Toolbar toolbar) {
        drawerNavigationView = (NavigationView) findViewById(R.id.drawerNavigationView);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutMain);

        // set drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.cancel);

        drawerLayout.setDrawerListener(toggle);
        final SecurePreferences prefsPrivate = mConfig.getPrefsPrivate();
        toggle.syncState();

        // set app info
        PackageInfo pinfo = Utils.getAppInfo(this);
        if (pinfo != null) {
            String version_nome = pinfo.versionName;
            int version_code = pinfo.versionCode;
            String header_text = String.format("%s (%d)", version_nome, version_code);

            View view = drawerNavigationView.getHeaderView(0);

            TextView app_info_text = view.findViewById(R.id.nav_headerAppVersion);
            app_info_text.setText(header_text);
        }

        // set navigation view
        drawerNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        if (toggle != null) toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (toggle != null) toggle.onConfigurationChanged(newConfig);
    }

    private synchronized void doSaveDatalatamsrc() {
        try {
            SharedPreferences prefs = mConfig.getPrefsPrivate();
            SharedPreferences.Editor edit = prefs.edit();
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View p1) {
        SharedPreferences prefs = mConfig.getPrefsPrivate();
        boolean isRunning = SkStatus.isTunnelActive();
        switch (p1.getId()) {
            case R.id.activity_starterButtonMain:
                if (mConfig.cambiarestadolatamsrc()) {
                    doSaveDatalatamsrc();
                    startOrStopTunnel(this);
                    if (mConfig.getUserOrHwid()) {
                        loadAd();
                        showInterstitial();
                        loadserverdata();
                             }else {
                        //AQUI CONTROLAS LA LOGICA PARA EL ANUNCIOS EN MODO POBRE


                        loadserverdataviplatamsrc();

                        //////////CONFIGURACION PARA EL SERVER PRIV LATMSRC
                    }

                } else {
                    doSaveData();
                    startOrStopTunnel(this);

                    //AQUI CONTROLAS LA LOGICA PARA EL ANUNCIOS EN MODO CUSTOM
                    loadAd();
                    showInterstitial();
                }

                break;


            case R.id.tunnelCardView:
                if (!isRunning) {
                    if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
                        startActivity(new Intent(this, TunnelActivity.class));
                    }
                }
                break;

            case R.id.proxyLayout:
                final SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
                doSaveData();
                if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
                    if (!isRunning) {
                        DialogFragment fragProxy = new ProxyRemoteDialogFragment();
                        fragProxy.show(getSupportFragmentManager(), "proxyDialog");
                    }
                }
                break;


            case R.id.sslLayout:
                doSaveData();
                if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
                    if (!isRunning) {
                        DialogFragment fragProxy = new sni();
                        fragProxy.show(getSupportFragmentManager(), "sni");
                    }
                }
                break;
        }
    }

    @Override
    public void updateState(final String state, String msg, int localizedResId, final ConnectionStatus level, Intent intent) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                doUpdateLayout();
                if (SkStatus.isTunnelActive()) {
                    if (level.equals(ConnectionStatus.LEVEL_CONNECTED)) {
                        progress_indicator.setVisibility(View.GONE);
                            sZacDevz.setText(R.string.state_connected);
						sZacDevz.setTextColor(Color.parseColor(getString(R.color.gen)));
                            sZacDevz1.setText(R.string.state_connected);
						sZacDevz1.setTextColor(Color.parseColor(getString(R.color.gen)));
                        start();
                    }
                    if (level.equals(ConnectionStatus.LEVEL_NOTCONNECTED)) {
                        // connectionsZacDevz.setText(R.string.servicestop);
                            sZacDevz.setText(R.string.state_disconnected);
						sZacDevz.setTextColor(Color.parseColor(getString(R.color.gen2)));
                       sZacDevz1.setText(R.string.state_disconnected);
						sZacDevz1.setTextColor(Color.parseColor(getString(R.color.gen2))); // checkupdate(false);
                    }

                    if (level.equals(ConnectionStatus.LEVEL_CONNECTING_SERVER_REPLIED)) {
                      sZacDevz1.setText(R.string.state_auth);  // connectionsZacDevz.setText(R.string.authenticating);sZacDevz.setText(R.string.state_auth);
                            sZacDevz.setText(R.string.state_auth);
                    }

                    if (level.equals(ConnectionStatus.LEVEL_CONNECTING_NO_SERVER_REPLY_YET)) {
                       sZacDevz1.setText(R.string.state_connecting);
						sZacDevz1.setTextColor(Color.parseColor(getString(R.color.gen3))); // connectionsZacDevz.setText(R.string.connecting);
                            sZacDevz.setText(R.string.state_connecting);
						sZacDevz.setTextColor(Color.parseColor(getString(R.color.gen3)));
                    }
                    if (level.equals(ConnectionStatus.LEVEL_AUTH_FAILED)) {
                            sZacDevz.setText(R.string.state_auth_failed);
                     sZacDevz1.setText(R.string.state_auth_failed);   // connectionsZacDevz.setText(R.string.authfailed);
                    }
                    if (level.equals(ConnectionStatus.UNKNOWN_LEVEL)) {
                            sZacDevz.setText(R.string.state_disconnected);
						sZacDevz.setTextColor(Color.parseColor(getString(R.color.gen3)));
                      sZacDevz1.setText(R.string.state_disconnected);
						sZacDevz1.setTextColor(Color.parseColor(getString(R.color.gen3)));  // connectionsZacDevz.setText(R.string.disconnected);
                    }
                }
                if (level.equals(ConnectionStatus.LEVEL_NONETWORK)) {
                        sZacDevz.setText(R.string.state_nonetwork);
                   sZacDevz1.setText(R.string.state_nonetwork); // connectionsZacDevz.setText(R.string.nonetwork);
                }
            }
        });


    }

    /**
     * Recebe locais Broadcast
     */
    public void licencia(View v) {
        // TODO: Implement this method
        startActivity(new Intent(this, LicenseActivity.class));
    }

    private BroadcastReceiver mActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;

            if (action.equals(UPDATE_VIEWS)) {
                doUpdateLayout();
                if (mConfig.cambiarestadolatamsrc()) {
                    setMainView();
                }

            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        settings = menu.findItem(R.id.miSettings);
        ifolder = menu.findItem(R.id.folder);
        sharet = menu.findItem(R.id.sharet);
        changelatamsrc = menu.findItem(R.id.changelatamsrc);
        changeestatelatamsrcbolean();
        if (mConfig.getUserOrHwid()) {
            changelatamsrc.setTitle("Modo VIP");
        } else {
            changelatamsrc.setTitle("Modo Pobre");
        }
        if (mConfig.cambiarestadolatamsrc()) {//vip activo modo latamsrc
            changelatamsrc.setVisible(true);
            ifolder.setVisible(false);
        } else {
            ifolder.setVisible(true);
            changelatamsrc.setVisible(false);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item1) {
        SharedPreferences prefs = mConfig.getPrefsPrivate();
        boolean isRunning = SkStatus.isTunnelActive();

        if (toggle != null && toggle.onOptionsItemSelected(item1)) {
            return true;
        }

        // Menu Itens
        switch (item1.getItemId()) {

            case R.id.changelatamsrc:
                if (SkStatus.isTunnelActive()) {
                    Toasty.error(this, (R.string.error_tunnel_service_execution), Toast.LENGTH_SHORT, true).show();
                } else {
                    if (mConfig.cambiarestadolatamsrc()) {
                        botoncambioestado();
                    } else {
                        Toasty.error(this, ("Cambia La Logica"), Toast.LENGTH_SHORT, true).show();
                    }
                }


                break;
            case R.id.miLimparConfig:
                if (!SkStatus.isTunnelActive()) {
                    if (mConfig.cambiarestadolatamsrc()) {//vip activo modo latamsrc
                        borrarlatamsrc();
                    } else {
                        ext();

                    }
                } else {
                    Toasty.error(this, (R.string.error_tunnel_service_execution), Toast.LENGTH_SHORT, true).show();
                }
                break;

            case R.id.cambiarestadolatamsrc:
                if (SkStatus.isTunnelActive()) {
                    Toasty.error(this, (R.string.error_tunnel_service_execution), Toast.LENGTH_SHORT, true).show();
                } else {
                    changeestatelatamsrcboton();
                }
                break;


            case R.id.miSettings:
                if (isHomeTab == true) {
                    Intent intentSettings = new Intent(this, ConfigGeralActivity.class);
                    // intentSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentSettings);
                } else {
                    mLogAdapter.clearLog();
                }
                break;

            case R.id.sharet:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, SkStatus.CopyLogs());
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, SkStatus.CopyLogs());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

                break;

            case R.id.miSettingImportar:
                if (SkStatus.isTunnelActive()) {
                    Toasty.error(this, (R.string.error_tunnel_service_execution), Toast.LENGTH_SHORT, true).show();
                } else {
                    Intent intentImport = new Intent(this, ConfigImportFileActivity.class);
                    startActivity(intentImport);
                }
                break;

            case R.id.miSettingExportar:
                if (SkStatus.isTunnelActive()) {
                    Toasty.error(this, (R.string.error_tunnel_service_execution), Toast.LENGTH_SHORT, true).show();
                } else if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
                    Toasty.error(this, (R.string.locked_msg), Toast.LENGTH_SHORT, true).show();
                } else {
                    Intent intentExport = new Intent(this, ConfigExportFileActivity.class);
                    startActivity(intentExport);
                }
                break;
        }

        return super.onOptionsItemSelected(item1);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.BATTERY_OPTIMIZATION:
                E0();
                break;
            case R.id.copiartokenlatamsrc:
                if (mConfig.cambiarestadolatamsrc()) {//vip activo modo latamsrc
                    btnHardwareID();

                } else {
                    id();

                }
                break;
            
            case R.id.offline:
                offlineUpdate();
                break;
            case R.id.expiracion:
                CheckUser();
                break;
            case R.id.miPhoneConfg:
                if (Build.VERSION.SDK_INT >= 30) {
                    Intent in = new Intent(Intent.ACTION_MAIN);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.setClassName("com.android.phone", "com.android.phone.settings.RadioInfo");
                    this.startActivity(in);
                } else {
                    Intent inTen = new Intent(Intent.ACTION_MAIN);
                    inTen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    inTen.setClassName("com.android.settings", "com.android.settings.RadioInfo");
                    this.startActivity(inTen);
                }
                break;

        /*case R.id.apnsettings:
        Intent intent1 = new Intent();
        intent1.setAction("android.settings.APN_SETTINGS");
        MainActivity.this.startActivity(intent1);
        break;*/


            case R.id.configUpdate:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                }
                updateConfig(false);
                buscar();
                break;
            case R.id.miSettingsSSH:
                if (mConfig.cambiarestadolatamsrc()) {
//          Intent intent = new Intent(MainActivity.this, ConfigGeralActivity.class);
//          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//          startActivity(intent);
                    Toast.makeText(this, "Primero Activa El Custom Mode", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences mPrefs = mConfig.getPrefsPrivate();
                    int tunnelType = mPrefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);

                    if (tunnelType == Settings.bTUNNEL_TYPE_SLOWDNS) {
                        Intent intent2 = new Intent(MainActivity.this, ConfigGeralActivity.class);
                        intent2.setAction(ConfigGeralActivity.OPEN_SETTINGS_DNS);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                    }

                    if (tunnelType == Settings.bTUNNEL_TYPE_SSH_DIRECT) {
                        Intent intent2g = new Intent(MainActivity.this, ConfigGeralActivity.class);
                        intent2g.setAction(ConfigGeralActivity.OPEN_SETTINGS_SSH);
                        intent2g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2g);
                    }

                    if (tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY) {
                        Intent intent2g = new Intent(MainActivity.this, ConfigGeralActivity.class);
                        intent2g.setAction(ConfigGeralActivity.OPEN_SETTINGS_SSH);
                        intent2g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2g);
                    }

                    if (tunnelType == Settings.bTUNNEL_TYPE_SSH_SSLTUNNEL) {
                        Intent intent2g = new Intent(MainActivity.this, ConfigGeralActivity.class);
                        intent2g.setAction(ConfigGeralActivity.OPEN_SETTINGS_SSH);
                        intent2g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2g);
                    }

                    if (tunnelType == Settings.bTUNNEL_TYPE_PAY_SSL) {
                        Intent intent2g = new Intent(MainActivity.this, ConfigGeralActivity.class);
                        intent2g.setAction(ConfigGeralActivity.OPEN_SETTINGS_SSH);
                        intent2g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2g);
                    }

                    if (tunnelType == Settings.bTUNNEL_TYPE_SSL_RP) {
                        Intent intent2g = new Intent(MainActivity.this, ConfigGeralActivity.class);
                        intent2g.setAction(ConfigGeralActivity.OPEN_SETTINGS_SSH);
                        intent2g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2g);
                    }

                    if (tunnelType == Settings.bTUNNEL_TYPE_UDP) {
                        Intent intent20 = new Intent(MainActivity.this, ConfigGeralActivity.class);
                        intent20.setAction(ConfigGeralActivity.OPEN_SETTINGS_UDP);
                        intent20.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent20);
                    }
                    if (tunnelType == Settings.bTUNNEL_TYPE_V2RAY) {
                        Intent intent20 = new Intent(MainActivity.this, ZacDevzRay.class);
                        intent20.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent20);
                    }
                }


                break;

            case R.id.miSettings:
                Intent intent = new Intent(MainActivity.this, ConfigGeralActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.vz:
                Intent intentg = new Intent(MainActivity.this, speed.class);
                intentg.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentg);
                break;


            case R.id.miExit:
                showExitDialog();
            break;
            case R.id.hostshare:
	             Intent hostshare2 = new Intent(MainActivity.this, ProxySettings.class);
		     	startActivity(hostshare2);
                break;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        }
        return true;
    }

    private int PICK_FILE;
    public void offlineUpdate() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE);

    }
    
    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        SkStatus.addStateListener(this);
        doSaveData();
        changeestatelatamsrcbolean();
        	ZacDevzCall();
        ZacDevzWork();
        ZacDevzWork1();
        BOLEANVIPLATAMSRC();
        if (mConfig.cambiarestadolatamsrc()) {
            setMainView();
        }
        if (!mTimerEnabled) {

            resumeTime();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SkStatus.removeStateListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        doSaveData();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mActivityReceiver);
    }


    private int mainposition() {
        SharedPreferences prefs = mConfig.getPrefsPrivate();
        return prefs.getInt("LastSelectedServer", 0);//bygatesccn
    }

    public void setMainView() {
        try {
            JSONObject object = config.getServersArray().getJSONObject(mainposition());
            String nombre = object.getString("Name");
            String info = object.getString("sInfo"); //tutomakebylatamsrc
            servername.setText(nombre);//bygatesccn
            if (info.isEmpty() || info == null) {
                serverinfo.setText(getString(R.string.app_name));
            } else {
                serverinfo.setText(info);//bygatesccn
            }

            setImagen(serverimage, object.getString("FLAG"));

        } catch (JSONException e) {

        } catch (Exception e) {

        }
    }

    public void setImagen(ImageView im, String nameo) throws Exception {
        InputStream inputStream = getAssets().open("flags/" + nameo + ".png");//bygatesccn
        im.setImageDrawable(Drawable.createFromStream(inputStream, nameo + ".png"));
    }

    private void id() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String hadweridr = (VPNUtils.getHWID());
        alertDialogBuilder.setTitle("ID de dispositivo");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(VPNUtils.getHWID());
        alertDialogBuilder.setPositiveButton("Copiar", (arg0, arg1) -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("text", hadweridr);
            clipboard.setPrimaryClip(clip);
        });
        alertDialogBuilder.setNegativeButton("Salir", (arg0, arg1) -> {
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void E0() {
        try {
            Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
            startActivity(intent.setData(Uri.parse("package:" + getPackageName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage("¿Estás seguro de queres salir de la app ?");
        alertDialogBuilder.setNegativeButton("Minimizar", (arg0, arg1) -> {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        });
        alertDialogBuilder.setNeutralButton("Cancelar", (arg0, arg1) -> {
        });
        alertDialogBuilder.setPositiveButton("Salir", (arg0, arg1) -> Utils.exitAll(MainActivity.this));
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void ext() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Atencion");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage("¿Estás seguro de que quieres restaurar la configuración actual?");
        alertDialogBuilder.setPositiveButton("Acepto", (arg0, arg1) -> {
            Settings.clearSettings(this);
            SkStatus.clearLog();
            MainActivity.updateMainViews(this);
        });
        alertDialogBuilder.setNegativeButton("Cancelar", (arg0, arg1) -> {
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void borrarlatamsrc() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Atencion");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage("ESTA OPCION BORRAR TOTALMENTE LOS DATOS DE LA APP");
        alertDialogBuilder.setPositiveButton("Formatear", (arg0, arg1) -> {
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("pm clear " + packageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancelar", (arg0, arg1) -> {
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Utils
     */
    public static void updateMainViews(Context context) {
        Intent updateView = new Intent(UPDATE_VIEWS);
        LocalBroadcastManager.getInstance(context).sendBroadcast(updateView);
    }

    private void loadserverdataviplatamsrc() {
        try {
            SharedPreferences prefs = mConfig.getPrefsPrivate();
            SharedPreferences.Editor edit = prefs.edit();
            int pos1 = mainposition();
            
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
        } catch (Exception e) {
            SkStatus.logInfo(e.getMessage());
        }
    }

    private void loadserverdata() {
        try {
            SharedPreferences prefs = mConfig.getPrefsPrivate();
            SharedPreferences.Editor edit = prefs.edit();
            int pos1 = mainposition();
            String ssh_server = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("ServerIP"));
            String remote_proxy =AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC, config.getServersArray().getJSONObject(pos1).getString("ProxyIP"));
            String proxy_port = config.getServersArray().getJSONObject(pos1).getString("ProxyPort");
            String ssh_user =AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC, config.getServersArray().getJSONObject(pos1).getString("ServerUser"));
            String ssh_pass = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("ServerPass"));
            String ssh_port = config.getServersArray().getJSONObject(pos1).getString("ServerPort");
            String ssl_port = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
            String payload =AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC, config.getServersArray().getJSONObject(pos1).getString("Payload"));
            String sni = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("SNI"));
            String chaveKey = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("Slowchave"));
            String serverNameKey = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("Nameserver"));
            String dnsKey = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("Slowdns"));

            String udpserver = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("udpserver"));
            String udpauth = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("udpauth"));
            String udpobfs = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("udpobfs"));

            String udpbuffer = config.getServersArray().getJSONObject(pos1).getString("udpbuffer");
            String udpdown = config.getServersArray().getJSONObject(pos1).getString("udpdown");
            String udpup = config.getServersArray().getJSONObject(pos1).getString("udpup");

            String authlatamsrc = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("apilatamsrcv2ray"));
            String v2rayJson = AESCrypt.decrypt(MainActivity.EncrypAESPASSWORDLatamSRC,config.getServersArray().getJSONObject(pos1).getString("v2rayJson"));
            String gatesccn = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            edit.putString(Settings.USUARIO_KEY, mConfig.getUserLogin());
            edit.putString(Settings.SENHA_KEY, mConfig.getPasswLogin());
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
            // UDP HYSTERIA
            if (isudp) {
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_UDP).apply();
                // prefs.edit().putString(Settings.UDP_BUFFER, udpbuffer).apply();
                prefs.edit().putString(Settings.UDP_SERVER, udpserver).apply();
                prefs.edit().putString(Settings.UDP_AUTH, udpauth).apply();
                prefs.edit().putString(Settings.UDP_OBFS, udpobfs).apply();
                prefs.edit().putString(Settings.UDP_DOWN, udpdown).apply();
                prefs.edit().putString(Settings.UDP_UP, udpup).apply();
            }


            if (iv2ray) {
                edit.putString(Settings.APILATAMIP, authlatamsrc);
                prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_V2RAY).apply();
                prefs.edit().putString(Settings.V2RAY_JSON, v2rayJson).apply();
            }

            edit.apply();
        } catch (Exception e) {
            SkStatus.logInfo(e.getMessage());
        }
    }

    private void restart_applatamvip() {
        this.startActivity(this.getIntent());
        this.finish();
        this.overridePendingTransition(0, 0);
    }

    public void changeestatelatamsrcbolean() {
        if (mConfig.cambiarestadolatamsrc()) {
            tunnelCardViewgatesccn.setVisibility(View.GONE);
            estadolatamsrc1.setVisibility(View.VISIBLE);
            estadolatamsrc2.setVisibility(View.VISIBLE);
            if (mConfig.getUserOrHwid()) {
                loadserverdata();
            } else {
                loadserverdataviplatamsrc();
            }
            adsBannerView.setVisibility(View.GONE);


        } else {
            tunnelCardViewgatesccn.setVisibility(View.VISIBLE);
            estadolatamsrc1.setVisibility(View.GONE);
            estadolatamsrc2.setVisibility(View.GONE);
            adsBannerView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (adsBannerView != null) {
                        adsBannerView.setVisibility(View.VISIBLE);
                    }
                }
            });
            adsBannerView.loadAd(new AdRequest.Builder().build());
        }
    }

    public void changeestatelatamsrcboton() {
        if (mConfig.cambiarestadolatamsrc()) {
            restart_applatamvip();
            mConfig.obtenerestadoviplatamsrc(false);
            setMainView();
            changeestatelatamsrc();
        } else {
            mConfig.obtenerestadoviplatamsrc(true);
            restart_applatamvip();
            changeestatelatamsrc();

        }
    }

    public void changeestatelatamsrc() {
        if (!SkStatus.isTunnelActive()) {
            Settings.clearSettings(this);
            SkStatus.clearLog();
            MainActivity.updateMainViews(this);
        } else {
            Toasty.error(this, (R.string.error_tunnel_service_execution), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void botoncambioestado() {
        if (mConfig.getUserOrHwid()) {
            mConfig.setUserOrHwid(false);
            restart_applatamvip();
        } else {
            mConfig.setUserOrHwid(true);
            restart_applatamvip();

        }
    }

    public void BOLEANVIPLATAMSRC() {
        if (mConfig.getUserOrHwid()) {//ONPOBRELATMSRC
            eresviplatamsrc.setVisibility(View.GONE);
            erespobrelatamsrc.setVisibility(View.VISIBLE);
        } else {//ONVIPLATMSRC
            eresviplatamsrc.setVisibility(View.VISIBLE);
            erespobrelatamsrc.setVisibility(View.GONE);
        }
    }


    private void updateConfig(final boolean isOnCreate) {
        new ConfigUpdate(this, new ConfigUpdate.OnUpdateListener() {
            @Override
            public void onUpdateListener(String result) {
                if (mConfig.getUserOrHwid()) {
                    try {
                        if (!result.contains("Error al obtener datos")) {
                            String json_data = AESCrypt.decrypt(config.PASSWORDDATEMODOPOBRE, result);
                            if (isNewVersion(json_data)) {
                                letUpdate(result);
                            } else {
                                if (!isOnCreate) {
                                    nueva();
                                    searchupdate.dismiss();
                                }
                            }
                        } else if (result.contains("Error al obtener datos") && !isOnCreate) {
                            error();
                            searchupdate.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (!result.contains("Error al obtener datos")) {
                            String json_data = AESCrypt.decrypt(config.PASSWORDDATEMODOVIP, result);
                            if (isNewVersion(json_data)) {
                                letUpdate(result);
                            } else {
                                if (!isOnCreate) {
                                    nueva();
                                    searchupdate.dismiss();
                                }
                            }
                        } else if (result.contains("Error al obtener datos") && !isOnCreate) {
                            error();
                            searchupdate.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start(isOnCreate);
    }

    private void letUpdate(final String result) {
        YourDataA = result.getBytes();
        pd = new ProgressDialog(this);
        pd.setTitle("Nueva Actualizacion");
        pd.setMessage("Actualizando Los Recursos..");
        pd.setMax(100);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface p1) {
                if (mConfig.getUserOrHwid()) {
                    try {
                        File file = new File(getFilesDir(), "Config.json");
                        OutputStream out = new FileOutputStream(file);
                        out.write(result.getBytes());
                        out.flush();
                        out.close();
                        to();
                        restart_applatamvip();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        File file = new File(getFilesDir(), "Config.mvgl");
                        OutputStream out = new FileOutputStream(file);
                        out.write(result.getBytes());
                        out.flush();
                        out.close();
                        to();
                        restart_applatamvip();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                status = 0;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < 100) {
                    status += 1;
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.setProgress(status);
                            if (status == 100) {
                                pd.dismiss();
                            }
                        }
                    });
                }
            }
        }).start();
        pd.show();
    }

    private boolean isNewVersion(String result) {
        if (mConfig.getUserOrHwid()) {
            try {
                String current = config.getVersion();
                String update = new JSONObject(result).getString("Version");
                return config.versionCompare(update, current);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String current = config.getVersion();
                String update = new JSONObject(result).getString("VIPversion");
                return config.versionCompare(update, current);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void nueva() {
        {
            View inflate = LayoutInflater.from(this).inflate(R.layout.nueva, null);
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
            alertDialogBuilder.setView(inflate);
            RelativeLayout ok = inflate.findViewById(R.id.appButton1);
            LottieAnimationView loading = inflate.findViewById(R.id.money);
            final AlertDialog alert = alertDialogBuilder.create();
            alert.setCanceledOnTouchOutside(false);
            loading.loop(true);
            loading.playAnimation();
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alert.getWindow().setGravity(Gravity.CENTER);
            alert.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.8d), -2);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p1) {
                    alert.dismiss();
                }
            });

            alert.show();
        }
    }

    public void error() {
        {
            View inflate = LayoutInflater.from(this).inflate(R.layout.error, null);
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
            alertDialogBuilder.setView(inflate);
            RelativeLayout ok = inflate.findViewById(R.id.appButton1);
            LottieAnimationView loading = inflate.findViewById(R.id.money);
            loading.loop(true);
            loading.playAnimation();
            final AlertDialog alert = alertDialogBuilder.create();
            alert.setCanceledOnTouchOutside(false);
            loading.loop(true);
            loading.playAnimation();
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alert.getWindow().setGravity(Gravity.CENTER);
            alert.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.8d), -2);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p1) {
                    alert.dismiss();
                }
            });

            alert.show();
        }
    }

    private void buscar() {

        View inflate = LayoutInflater.from(this).inflate(R.layout.listo, null);
        MaterialAlertDialogBuilder builer = new MaterialAlertDialogBuilder(this);
        builer.setView(inflate);
        searchupdate = builer.create();
        searchupdate.setCanceledOnTouchOutside(false);
        searchupdate.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.8d), -2);
        searchupdate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchupdate.getWindow().setGravity(Gravity.CENTER);
        searchupdate.show();
    }

    public void to() {
        Toast.makeText(MainActivity.this, "Servidores Actualizados Con Exito", Toast.LENGTH_LONG).show();
    }

    public void btnHardwareID() {
        String Tokenid = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", Tokenid);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(MainActivity.this, "ID Copiado", Toast.LENGTH_SHORT).show();
    }
    //ADS LOGICA BY LATAMSRC

    private void showInterstitial() {
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            //loadAd();
            //showInterstitial();
            //Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.admobinterst), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        MainActivity.this.interstitialAd = interstitialAd;
                        //Log.i(TAG, "onAdLoaded");
                        //Toast.makeText(MainActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.

                                        MainActivity.this.interstitialAd = null;
                                        //Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        loadAd();
                                        MainActivity.this.interstitialAd = null;
                                        //Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        //Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;

                        @SuppressLint("DefaultLocale") String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
               /* Toast.makeText(
                        MainActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                        .show();*/
                    }
                });
    }
    private void loadRewardedAd() {
        if (rewardedAd == null) {
            isLoading = true;
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(
                    this,getString(R.string.admobreward), adRequest,
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            rewardedAd = null;
                            Toasty.error(MainActivity.this, ("Error al inicializar anuncios verifique o contacte al Developer.."), Toast.LENGTH_SHORT, true).show();

                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            MainActivity.this.rewardedAd = rewardedAd;
                            MainActivity.this.isLoading = false;
                            showRewardedVideo();
                        }
                    });
        }
    }

    private void showRewardedVideo() {
        if (rewardedAd == null) {
            return;
        }
        rewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {}

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        rewardedAd = null;
                        addTime();
                    }
                });
        Activity activityContext = MainActivity.this;
        rewardedAd.show(
                activityContext,
                new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        addTime(); // Set button

                        long ads_time = 6000 * 10 * 60 * 2; // add time
                        addTime(ads_time); // set the time
                        Toasty.error(
                                        MainActivity.this,
                                        ("2 hours successfully added to your time!"),
                                        Toast.LENGTH_SHORT,
                                        true)
                                .show();
                    }
                });
    }

    // ADS LOGICA BY LATAMSRC
    private void start() {
        if (saved_ads_time == 1) {
            showInterstitial();
            loadAd();
            Toast.makeText(MainActivity.this, "Por Favor renovar acceso ahora.", Toast.LENGTH_LONG)
                    .show();
            long millisInput = 1000 * 3600;
            setTime(millisInput);
        }

        if (!mTimerRunning) {
            if (mConfig.getUserOrHwid()) {
                startTimer();
            }
        }
    }

    private void stop() {
        if (mTimerRunning) {
            pauseTimer();
        }

    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }
    private void updateCountDownText() {
        long days = TimeUnit.MILLISECONDS.toDays(mTimeLeftInMillis);
        long daysMillis = TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis - daysMillis);
        long hoursMillis = TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis - daysMillis - hoursMillis);
        long minutesMillis = TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis - daysMillis - hoursMillis - minutesMillis);
        String resultString = days + "d:" + hours + "h:" + minutes + "m:" + seconds + "s";
        mTextViewCountDown.setText(resultString);
    }

    private void setTime(long milliseconds) {
        saved_ads_time = mTimeLeftInMillis + milliseconds;
        mTimeLeftInMillis = saved_ads_time;
        updateCountDownText();

    }

    private void addTime(long time) {

        setTime(time);

        if (mTimerRunning) {
            pauseTimer();
        }

        if (mConfig.getUserOrHwid()) {
            startTimer();
        }
    }

    private void saveTime(long time) {
        SharedPreferences mTime = getSharedPreferences("time", Context.MODE_PRIVATE);

        SharedPreferences.Editor time_edit = mTime.edit();
        time_edit.putLong("SAVED_TIME", time).apply();
    }

    private void addTime() {
        long added = sp.getLong("isAdded", 0);

        if (added == 1) {
            long added_time = sp.getLong("AddedTime", 0);

            if (mTimerRunning) {
                addTime(added_time);
            } else {
                setTime(added_time);
            }
            sp.edit().putLong("isAdded", 0).apply();
            saveTime(mTimeLeftInMillis);
        }
    }
    private void btnTimer() {

        mBtnCountDown = new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftBtn = millisUntilFinished;
               // timershadowgatesccn.setEnabled(false);

                updateBtnText();
            }

            @Override
            public void onFinish() {
             //   timershadowgatesccn.setEnabled(true);
                // mButtonSet.setText("ADD + TIME");
            }

        }.start();

    }

    private void updateBtnText() {
        int seconds = (int) (mTimeLeftBtn / 1000) % 60;
        String timeLeftFormatted;
        if (seconds > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d", seconds);


        }
    }

    private void resumeTime() {

        SharedPreferences mTime = getSharedPreferences("time", Context.MODE_PRIVATE);

        long saved_time = mTime.getLong("SAVED_TIME", 1);
        setTime(saved_time);
        String state = SkStatus.getLastState();

        if (SkStatus.SSH_CONECTADO.equals(state)) {

            if (!mTimerRunning) {
                if (mConfig.getUserOrHwid()) {
                    startTimer();
                }
            }
        }

        mTimerEnabled = true;
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                saveTime(mTimeLeftInMillis);
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                pauseTimer();
                saved_ads_time = 0;
                showInterstitial();
                loadAd();
                Intent stopVPN = new Intent(SocksDNSService.TUNNEL_SSH_STOP_SERVICE);
                LocalBroadcastManager.getInstance(MainActivity.this)
                        .sendBroadcast(stopVPN);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View inflate = inflater.inflate(R.layout.timeadsmandarin, (ViewGroup) null);
                AlertDialog.Builder builer = new AlertDialog.Builder(MainActivity.this);
                builer.setView(inflate);
                ImageView iv = inflate.findViewById(R.id.icon);
                TextView title = inflate.findViewById(R.id.title);
                TextView ms = inflate.findViewById(R.id.message);
                ms.setText("MIRA UN ANUNCIO Y GANA MAS TIEMPO");
                TextView bubu = inflate.findViewById(R.id.positiveTxt);
                iv.setImageResource(R.drawable.astronauta);
                title.setText("¡Tiempo Terminado! Presiona en Añadir Tiempo");
                bubu.setText(" Agrega Mas Tiempo");
                final AlertDialog alert = builer.create();
                alert.setCanceledOnTouchOutside(false);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.getWindow().getAttributes().windowAnimations = R.style.AppAlertDialog;
                alert.show();
                bubu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Toast.makeText(MainActivity.this, "CARGANDO ANUNCIO", Toast.LENGTH_SHORT).show();
                            showInterstitial();
                            loadAd();
                            loadRewardedAd();
                            alert.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

            }

        }.start();
        mTimerRunning = true;


    }

    public void whatsapphome(View view) {
        btnHardwareID();
        String numero = "961311310";
        String mensaje = "Hola, Mi token "; // puedes personalizar el mensaje
        String url = "https://wa.me/" + numero + "?text=" + Uri.encode(mensaje);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage("com.whatsapp");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "⚠️ WhatsApp no está instalado", Toast.LENGTH_SHORT).show();
        }
    }


    public void menumandarin(View view) {
        timeadsmandarin();
    }

    public void timeadsmandarin() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.tiempoaddmandarin, (ViewGroup) null);
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setView(inflate);
        TextView title = inflate.findViewById(R.id.title);
        TextView ms = inflate.findViewById(R.id.mensajeone);
        LinearLayout btnd = inflate.findViewById(R.id.positivBtn);
        TextView btn = inflate.findViewById(R.id.positivTxt);
        title.setText("Atencion Usuario");
        ms.setText("Vea un anuncio y reciba  2 horas de conexion gratis.");
        final AlertDialog alert = builer.create();
        alert.setCanceledOnTouchOutside(true);
        alert.setCancelable(true);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().getAttributes().windowAnimations = R.style.AppAlertDialog;

        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Cargando", Toast.LENGTH_SHORT).show();

                loadAd();
                showInterstitial();
                loadRewardedAd();
                alert.dismiss();
            }
        });
        alert.show();
    }

    //CHECKUSER JAVA
//FINAL

    private void CheckUser() {
        final SharedPreferences prefs= mConfig.getPrefsPrivate();
        checkuser = prefs.getString(Settings.USUARIO_KEY, ""); //AQUI É ONDE PEGA O USUÁRIO USADO
        //String current_user = current_username.getText().toString();
        diasRestantes = (TextView) findViewById(R.id.txtdiasRestantes); ///NEED ADD ID ON XML
        vencimentoDate = (TextView) findViewById(R.id.txtVencimiento) ;
        avisos= (TextView) findViewById(R.id.txtAvisos) ;
        usuario = (TextView) findViewById(R.id.txtHwid);
        //String URLServer =  serverURL.getText().toString();//AQUI É ONDE PEGA A URL DO SERVIDOR DO BANCO DE DADOS/PAINEL
        usuario.setVisibility(View.VISIBLE);
        usuario.setText("Usuario:" + checkuser);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(URLServer);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user", checkuser);

                    Log.i("JSON", jsonParam.toString());

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());
                    os.flush();
                    os.close();

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                            String result;

                            while ((result = bufferedReader.readLine()) != null) {
                                String resultFinal = result.replace("\"", "");
                                if (resultFinal.equals("not exist")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            vencimentoDate.setVisibility(View.GONE);
                                            usuario.setVisibility(View.GONE);
                                            diasRestantes.setVisibility(View.GONE);
                                            avisos.setVisibility(View.VISIBLE);
                                            avisos.setText("USER NO EXISTE");
                                            avisos.setTextColor(Color.RED);
                                        }
                                    });

                                }else{
                                    showUserInfo(resultFinal);
                                }
                                Log.d("JSON",resultFinal);

                            }
                        }
                    }else {
                        vencimentoDate.setVisibility(View.GONE);
                        usuario.setVisibility(View.GONE);
                        diasRestantes.setVisibility(View.GONE);
                        avisos.setVisibility(View.VISIBLE);
                        avisos.setText("USER NO EXISTE :(");
                        avisos.setTextColor(Color.RED);
                        //ERRO NA CHECAGEM
                        // SkStatus.logInfo("USER NO EXISTE");
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private void showUserInfo(final String result) {
        //MOSTRA DATA E FAZ CHECAGEM
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date hoje = new Date();
        final String CurrentDate = dateFormat.format(hoje);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //FORMATA DATA
                String data = formatDate(result);
                //SETA DATA DE VENCIMENTO FORMATADA NO TEXTVIEW
                vencimentoDate.setVisibility(View.VISIBLE);
                vencimentoDate.setText("Vencimiento: "+data);

                //VERIFICA DIAS RESTANTES
                try {
                    Date firstDate = sdf.parse(CurrentDate);
                    Date secondDate = sdf.parse(data);

                    long diff = secondDate.getTime() - firstDate.getTime();
                    TimeUnit time = TimeUnit.DAYS;
                    long dias_diferenca = time.convert(diff, TimeUnit.MILLISECONDS);
                    int dias_diferenca_int = (int) dias_diferenca;

                    diasRestantes.setVisibility(View.VISIBLE);
                    diasRestantes.setText("Dias restantes: " + dias_diferenca_int);

                    avisos.setVisibility(View.VISIBLE);
                    if (dias_diferenca_int <= 3){
                        avisos.setText("TE RESTAN "+ dias_diferenca_int + " CLICK PARA RENOVAR");
                        avisos.setTextColor(Color.RED);

                        avisos.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                String url = "https://wa.me/59169008438";
                                Intent intentsuporte = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                intentsuporte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Intent.createChooser(intentsuporte, "Abrir com"));
                            }
                        });
                    }else{
                        //avisos.setText("USER FULL!");
                        //avisos.setTextColor(Color.BLUE);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public static String formatDate(String data){
        String dateStr = data;
        String returnString = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date date = sdf.parse(dateStr);
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            dateStr = sdf.format(date);
            returnString = dateStr;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnString;
    }

}
