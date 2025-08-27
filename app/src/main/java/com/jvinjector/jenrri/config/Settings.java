/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.speedlite.vpn.util.securepreferences.SecurePreferences;
import com.speedlite.vpn.util.securepreferences.model.SecurityConfig;

/**
* Configurações
*/

public class Settings implements SettingsConstants
{
	private Context mContext;
	private SharedPreferences mPrefs;
	private SecurePreferences mPrefsPrivate;
	
	private static SecurityConfig minimumConfig = new SecurityConfig.Builder("fubgf777gf6").build();
	
	public boolean sshCompression() {
		return mPrefs.getBoolean(SSH_COMPRESSION, true);
	}
	
	public Settings(Context context) {
		mContext = context;

		mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		mPrefsPrivate = SecurePreferences.getInstance(mContext, "SecureData", minimumConfig);
	}

	
	
	public String getPrivString(String key) {
		String defaultStr = "";
	
		switch (key) {
			case PORTA_LOCAL_KEY:
				defaultStr = "1080";
			break;
		}
		
		return mPrefsPrivate.getString(key, defaultStr);
	}
	
	public SecurePreferences getPrefsPrivate() {
		return mPrefsPrivate;
	}
	
	
	/**
	* Config File
	*/
	
	public String getMensagemConfigExportar() {
		return mPrefs.getString(CONFIG_MENSAGEM_EXPORTAR_KEY, "");
	}

	public void setMensagemConfigExportar(String str) {
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(CONFIG_MENSAGEM_EXPORTAR_KEY, str);
		editor.commit();
	}
    
    public String getAutorMsg() {
		return mPrefs.getString(CONFIG_AUTOR_KEY, "");
	}

	public void setAutorMsg(String str) {
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(CONFIG_AUTOR_KEY, str);
		editor.commit();
	}
	
	public boolean getAutoReplace() {
        return mPrefs.getBoolean(AUTOREPLACE_KEY, false);
    }
    
    public void setAutoReplace(boolean replace) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(AUTOREPLACE_KEY, replace);
        editor.commit();
    }
    
    public String getLineProxy() {
        return mPrefs.getString(PROXY_LINE_INPUT, "");
    }
    
    public void setLineProxy(String proxyline) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PROXY_LINE_INPUT, proxyline);
        editor.commit();
    }
	public void setWakelock(boolean use){
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putBoolean(WAKELOCK_KEY, use);
		editor.commit();
	}
    //Wakelock
	
	/**
	* Geral
	*/
	
	
	public String getModoNoturno() {
		return mPrefs.getString(MODO_NOTURNO_KEY, "off");
	}

	public void setModoNoturno(String str) {
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(MODO_NOTURNO_KEY, str);
		editor.commit();
	}
    public boolean setAutoPing() {
		return mPrefs.getBoolean(AUTO_PINGER, false);
	}

	public String setPinger() {
		return mPrefs.getString(PINGER, "");

	}
public boolean ssh_compression() {
		return mPrefs.getBoolean(SSH_COMPRESSION, true);
	}

    public boolean getWakelock() {
        return mPrefs.getBoolean(WAKELOCK_KEY, true);
	}
    
    
	public String getIdioma() {
		return mPrefs.getString(IDIOMA_KEY, "default");
	}
	
	public void setIdioma(String str) {
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(IDIOMA_KEY, str);
		editor.commit();
	}
	public boolean getModoDebug() {
		return mPrefs.getBoolean(MODO_DEBUG_KEY, false);
	}
	
	public void setModoDebug(boolean is) {
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putBoolean(MODO_DEBUG_KEY, is);
		editor.commit();
	}
	
	public int getMaximoThreadsSocks() {
		String n = mPrefs.getString(MAXIMO_THREADS_KEY, "8th");
		if (n == null || n.isEmpty()) {
			n = "8th";
		}
		return Integer.parseInt(n.replace("th", ""));
	}
	
	public boolean getHideLog() {
		return mPrefs.getBoolean(HIDE_LOG_KEY, false);
	}
	
	public boolean getAutoClearLog() {
		return mPrefs.getBoolean(AUTO_CLEAR_LOGS_KEY, true);
	}
	
	public boolean getIsFilterApps() {
		return mPrefs.getBoolean(FILTER_APPS, false);
	}
	
	public boolean getIsFilterBypassMode() {
		return mPrefs.getBoolean(FILTER_BYPASS_MODE, false);
	}
	
	public String[] getFilterApps() {
		String txt = mPrefs.getString(FILTER_APPS_LIST, "");
		if (txt.isEmpty()) {
			return new String[]{};
		}
		else {
			return txt.split("\n");
		}
	}
	
	public boolean getIsTetheringSubnet() {
		return mPrefs.getBoolean(TETHERING_SUBNET, false);
	}

    

	public boolean getIsDisabledDelaySSH() {
		return mPrefs.getBoolean(DISABLE_DELAY_KEY, false);
	}
	
	/**
	* Vpn Settings
	*/
	
	public boolean getVpnDnsForward(){
		return mPrefs.getBoolean(DNSFORWARD_KEY, false);
	}
	
    	public boolean network_meter() {
		return mPrefs.getBoolean(NETWORK_SPEED, false);
	}
    
    
	public void setVpnDnsForward(boolean use){
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putBoolean(DNSFORWARD_KEY, use);
		editor.commit();
	}
	
	public String getVpnDnsResolver2(){
		return mPrefs.getString(DNSRESOLVER_KEY2, "8.8.4.4");
	}
	public String getVpnDnsResolver1(){
		return mPrefs.getString(DNSRESOLVER_KEY1, "8.8.8.8");
	}
	public void setVpnDnsResolver2(String str) {
		if (str == null || str.isEmpty()) {
			str = "8.8.4.4";
		}
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(DNSRESOLVER_KEY1, str);
		editor.commit();
	}
	public void setVpnDnsResolver1(String str) {
		if (str == null || str.isEmpty()) {
			str = "8.8.8.8";
		}
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(DNSRESOLVER_KEY2, str);
		editor.commit();
	}

	public boolean getVpnUdpForward(){
		return mPrefs.getBoolean(UDPFORWARD_KEY, true);
	}
	
	public void setVpnUdpForward(boolean use){
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putBoolean(UDPFORWARD_KEY, use);
		editor.commit();
	}

	public boolean getBypass(){
		return mPrefs.getBoolean(BYPASS_KEY, false);
	}

	public void setBypass(boolean use){
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putBoolean(BYPASS_KEY, use);
		editor.commit();
	}
	
	public String getVpnUdpResolver(){
		return mPrefs.getString(UDPRESOLVER_KEY, "127.0.0.1:7300");
	}
	
	public void setVpnUdpResolver(String str) {
		if (str == null || str.isEmpty()) {
			str = "127.0.0.1:7300";
		}
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(UDPRESOLVER_KEY, str);
		editor.commit();
	}
	
	/**
	* SSH Settings
	*/
	
	
	public String getSSHKeypath() {
		return mPrefs.getString(KEYPATH_KEY, "");
	}

	public int getSSHPinger() {
		String ping = mPrefs.getString(PINGER_KEY, "3");
		if (ping == null || ping.isEmpty()) {
			ping = "3";
		}
		return Integer.parseInt(ping);
	}
	

	/**
	* Utils
	*/
	public boolean cambiarestadolatamsrc(){
		return mPrefs.getBoolean(TUNNEL_TYPE_LATAMSRC, true);
	}
	public void obtenerestadoviplatamsrc(boolean use){
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putBoolean(TUNNEL_TYPE_LATAMSRC, use);
		editor.commit();
	}
	public boolean getUserOrHwid(){
		return mPrefs.getBoolean(USER_HWID, false);
	}

	public void setUserOrHwid(boolean use){
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putBoolean(USER_HWID, use);
		editor.commit();
	}
	public String getUserLogin(){
		return mPrefs.getString(USER_LOGIN, "");
	}

	public void setUserLogin(String str) {
		if (str == null || str.isEmpty()) {
			str = "";
		}
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(USER_LOGIN, str);
		editor.commit();
	}

	public String getPasswLogin(){
		return mPrefs.getString(PASSW_LOGIN, "");
	}

	public void setPasswLogin(String str) {
		if (str == null || str.isEmpty()) {
			str = "";
		}
		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(PASSW_LOGIN, str);
		editor.commit();
	}


	public static void setDefaultConfig(Context context){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(SSH_COMPRESSION, true);
		editor.putBoolean(DNSFORWARD_KEY, false);
		editor.putString(DNSRESOLVER_KEY1, "8.8.4.4");
		editor.putString(DNSRESOLVER_KEY2, "8.8.8.8");
		editor.putBoolean(UDPFORWARD_KEY, true);
		editor.putString(UDPRESOLVER_KEY, "127.0.0.1:7300");
		editor.putString(PINGER_KEY, "3");
        editor.putString(MODO_NOTURNO_KEY, "Modo Claro");
        editor.putBoolean(AUTO_PINGER, false);
        editor.putString(PINGER, "clients3.google.com");
		editor.putString(MAXIMO_THREADS_KEY, "8th");
		editor.remove(MODO_DEBUG_KEY);
		editor.remove(HIDE_LOG_KEY);
		editor.remove(AUTO_CLEAR_LOGS_KEY);
        editor.putBoolean(VIBRATE, true);
		editor.remove(FILTER_APPS);
        editor.putBoolean(NETWORK_SPEED, true);
        editor.putBoolean(SSH_COMPRESSION, true);
        editor.putBoolean(WAKELOCK_KEY, true);
		editor.remove(FILTER_BYPASS_MODE);
		editor.remove(FILTER_APPS_LIST);
		editor.commit();
	}
	
	public static void clearSettings(Context context) {
		SharedPreferences priv = SecurePreferences.getInstance(context, "SecureData", minimumConfig);
		SharedPreferences.Editor edit = priv.edit();
		edit.clear();
		edit.commit();
	}
	
}
