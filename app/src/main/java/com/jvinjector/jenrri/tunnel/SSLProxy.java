/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */

/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.tunnel;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.speedlite.vpn.logger.SkStatus;
import com.trilead.ssh2.HTTPProxyException;
import com.trilead.ssh2.ProxyData;
import com.trilead.ssh2.crypto.Base64;
import com.trilead.ssh2.sftp.Packet;
import com.trilead.ssh2.transport.ClientServerHello;

import org.conscrypt.Conscrypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLProxy implements ProxyData
{

	static {
		try
		{
			Security.insertProviderAt(Conscrypt.newProvider(), 1);

		}
		catch (NoClassDefFoundError e)
		{
			e.printStackTrace();
		}}

	private final String proxyUser;
	private final String proxyPass;
	private SharedPreferences sp;
	private final String requestPayload;
	class HandshakeTunnelCompletedListener implements HandshakeCompletedListener {
		private final int val$port;
		private final SSLSocket val$sslSocket;

		HandshakeTunnelCompletedListener( String str, int i, SSLSocket sSLSocket) {
			this.val$port = i;
			this.val$sslSocket = sSLSocket;

		}

		public void handshakeCompleted(HandshakeCompletedEvent handshakeCompletedEvent) {
			try
			{
				//addLog(new StringBuffer().append("<b>Established ").append(handshakeCompletedEvent.getSession().getProtocol()).append(" connection with ").append(val$host).append(":").append(this.val$port).append(" using ").append(handshakeCompletedEvent.getCipherSuite()).append("</b>").toString());
				//addLog(new StringBuffer().append("<b>Established ").append(handshakeCompletedEvent.getSession().getProtocol()).append(" connection ").append("using ").append(handshakeCompletedEvent.getCipherSuite()).append("</b>").toString());
				//addLog(new StringBuffer().append("Supported cipher suites: ").append(Arrays.toString(this.val$sslSocket.getSupportedCipherSuites())).toString());
				//addLog(new StringBuffer().append("Enabled cipher suites: ").append(Arrays.toString(this.val$sslSocket.getEnabledCipherSuites())).toString());
				// addLog(new StringBuffer().append("SSL: Supported protocols: <br>").append(Arrays.toString(val$sslSocket.getSupportedProtocols())).toString().replace("[", "").replace("]", "").replace(",", "<br>"));
				// addLog(new StringBuffer().append("SSL: Enabled protocols: <br>").append(Arrays.toString(val$sslSocket.getEnabledProtocols())).toString().replace("[", "").replace("]", "").replace(",", "<br>"));
				//addLog("SSL: Using cipher " + handshakeCompletedEvent.getSession().getCipherSuite());
				addLog("SSL: Using protocol " + handshakeCompletedEvent.getSession().getProtocol());
				SkStatus.logInfo("" + handshakeCompletedEvent.getPeerPrincipal().toString());
				addLog("SSL: Handshake finished");
			}
			catch (Exception e){
			}
		}
	}

	private final String stunnelServer;
	private int stunnelPort = 443;
	private final String stunnelHostSNI;

	private Socket mSocket;

	public SSLProxy(String server, int port, String hostSni,String requestPayload) {
		this.stunnelServer = server;
		this.stunnelPort = port;
		this.stunnelHostSNI = hostSni;
		this.proxyUser = null;
		this.proxyPass = null;
		this.requestPayload = requestPayload;
	}

	@SuppressLint("NewApi")
	@Override
	public Socket openConnection(String hostname, int port, int connectTimeout, int readTimeout) throws IOException
	{
		mSocket = SocketChannel.open().socket();
		mSocket.connect(new InetSocketAddress(stunnelServer, stunnelPort));

		if (mSocket.isConnected()) {
			mSocket = doSSLHandshake(hostname, stunnelHostSNI, port);
			//inicia
			String requestPayload = getRequestPayload(stunnelServer, stunnelPort);
			mSocket.setKeepAlive(true);
			mSocket.setTcpNoDelay(true);
			SkStatus.logInfo("SSL KEEP ALIVE: " + mSocket.getKeepAlive());
			SkStatus.logInfo("SSL TCP DELAY: " + mSocket.getTcpNoDelay());
			OutputStream out = mSocket.getOutputStream();
			// suporte a [split] na payload
			if (!TunnelUtils.injectSplitPayload(requestPayload, out))
			{
				try
				{
					out.write(requestPayload.getBytes(StandardCharsets.ISO_8859_1));
				}
				catch (UnsupportedEncodingException e2)
				{
					out.write(requestPayload.getBytes());
				}
				out.flush();
			}
			addLog("<b>HTTP: Sending Payload</b>");

			byte[] buffer = new byte[1024];
			InputStream in = mSocket.getInputStream();

			// lÃª primeira linha
			int len = ClientServerHello.readLineRN(in, buffer);

			String httpReponseFirstLine = "";
			httpReponseFirstLine = new String(buffer, 0, len, StandardCharsets.ISO_8859_1);

			addLog("<strong>" + httpReponseFirstLine + "</strong>");

			String str2 = httpReponseFirstLine;
			int parseInt = Integer.parseInt(str2.substring(9, 12));

//				if (parseInt == 200) {
//				return this.socket;
//				}else if (parseInt == 101) {
//				addLog("<b>HTTP/1.1 200 Connection established</b>");
//

			if (str2.contains("200"))
			{
				return this.mSocket;
			}
			else if (parseInt == 101)
			{
				addLog("set auto replace response");
				if (str2.split(" ")[0].equals("HTTP/1.1"))
				{addLog("replace 200 OK");
					addLog("<b>Status: 200 (Connection established) Successfull</b> - The action requested by the client was successful.");
				}

				return this.mSocket;
			}


			if (!httpReponseFirstLine.startsWith("HTTP/"))
			{
				throw new IOException("The proxy did not send back a valid HTTP response.");
			}
			else if (httpReponseFirstLine.length() >= 14 && httpReponseFirstLine.charAt(8) == ' ' && httpReponseFirstLine.charAt(12) == ' ')
			{
				try
				{
					int errorCode = Integer.parseInt(httpReponseFirstLine.substring(9, 12));
					if (errorCode < 0 || errorCode > 999)
					{
						throw new IOException("The proxy did not send back a valid HTTP response.");
					}
					else if (errorCode != Packet.SSH_FXP_EXTENDED)
					{
						throw new HTTPProxyException(httpReponseFirstLine.substring(13), errorCode);
					}
					else
					{
						return mSocket;
					}
				}
				catch (NumberFormatException e4)
				{
					throw new IOException("The proxy did not send back a valid HTTP response.");
				}
			}
			else
			{
				throw new IOException("The proxy did not send back a valid HTTP response.");
			}
		}
		return null;
	}
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	private String getRequestPayload(String hostname, int port) {
		String payload = this.requestPayload;

//("GET / HTTP/1.1[crlf]Host: "+stunnelHostSNI+"[crlf]Upgrade: websocket[crlf][crlf]");


		if (payload != null)
		{
			payload = TunnelUtils.formatCustomPayload(hostname, port, payload);
		}
		else
		{
			StringBuffer sb = new StringBuffer();

			sb.append("CONNECT ");
			sb.append(hostname);
			sb.append(':');
			sb.append(port);
			sb.append(" HTTP/1.0\r\n");
			if (!(this.proxyUser == null || this.proxyPass == null))
			{
				char[] encoded;
				String credentials = this.proxyUser + ":" + this.proxyPass;
				encoded = Base64.encode(credentials.getBytes(StandardCharsets.ISO_8859_1));
				sb.append("Proxy-Authorization: Basic ");
				sb.append(encoded);
				sb.append("\r\n");
			}
			sb.append("\r\n");

			payload = sb.toString();
		}

		return payload;
	}



	private Socket doSSLHandshake(Socket socket, String host, String sni, int port) throws IOException {
		@SuppressLint("CustomX509TrustManager") TrustManager[] trustAllCerts = new TrustManager[] {
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					@SuppressLint("TrustAllX509TrustManager")
					public void checkClientTrusted(
							java.security.cert.X509Certificate[] certs, String authType) {
					}
					@SuppressLint("TrustAllX509TrustManager")
					public void checkServerTrusted(
							java.security.cert.X509Certificate[] certs, String authType) {
					}
				}
		};
		try {
			SSLContext sSLContext = SSLContext.getInstance("SSL","Conscrypt");
			KeyManager[] keyManagerArr = null;
			sSLContext.init(keyManagerArr, trustAllCerts, new SecureRandom());
			SSLSocket socket3 = (SSLSocket) sSLContext.getSocketFactory().createSocket(socket, host, port, true);
			if (sSLContext.getSocketFactory() instanceof android.net.SSLCertificateSocketFactory && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				((android.net.SSLCertificateSocketFactory)sSLContext.getSocketFactory()).setHostname(socket, sni);
			} else {
				try {
					socket.getClass().getMethod("setHostname", String.class).invoke(socket, sni);
					SkStatus.logInfo("Setting up SNI: ");
				} catch (Throwable e) {
					// ignore any error, we just can't set the hostname...
				}
			}
			socket3.setEnabledProtocols(socket3.getSupportedProtocols());
			socket3.addHandshakeCompletedListener(new HandshakeTunnelCompletedListener(host, port, socket3));
			SkStatus.logInfo("Starting SSL Handshake...");
			socket3.startHandshake();
			return socket3;
		} catch (Exception e) {
			throw new IOException("Could not do SSL handshake: " + e);
		}
	}

	private SSLSocket doSSLHandshake(String host, String sni, int port) throws IOException {
		try {
			TLSSocketFactory tsf = new TLSSocketFactory();
			SSLSocket socket = (SSLSocket) tsf.createSocket(host, port);
			try {
				socket.getClass().getMethod("setHostname", String.class).invoke(socket, sni);
				SkStatus.logInfo("Setting up SNI: ");
			} catch (Throwable e) {
				// ignore any error, we just can't set the hostname...
			}

			//socket.setEnabledProtocols(socket.getSupportedProtocols());
			socket.addHandshakeCompletedListener(new HandshakeTunnelCompletedListener(host, port, socket));
			SkStatus.logInfo("Starting SSL Handshake...");
			socket.startHandshake();
			return socket;
		} catch (Exception e) {
			throw new IOException("Could not do SSL handshake: " + e);
		}
	}
	@Override
	public void close()
	{
		try {
			if (mSocket != null) {
				mSocket.close();
			}
		} catch(IOException e) {}
	}

	private void addLog(String msg){
		SkStatus.logInfo(msg);
	}

}



