/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */

/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.tunnel;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import com.speedlite.vpn.logger.SkStatus;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.nio.channels.SocketChannel;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.io.BufferedReader;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.io.InputStreamReader;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.io.OutputStream;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.net.InetAddress;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.net.InetSocketAddress;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.net.Socket;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.util.Random;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.util.Date;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import com.trilead.ssh2.Connection;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import com.trilead.ssh2.LocalPortForwarder;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.net.HttpURLConnection;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
import java.net.URL;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
public class Pinger extends Thread {
    /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    private final Connection a;
    /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
	private final String b;
    /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    private LocalPortForwarder c;
    /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    private boolean d;
    /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    private Socket f;
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    public Pinger(Connection connection, String str) {
        /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
        this.a = connection;
        /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
		this.b = str;
        /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    }
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    private int b(/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */) {
        /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
        return (new Random().nextInt(6) + 2) * 1000;
        /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    }
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    public void close(/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */)/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */ {
        synchronized (this/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */) {
            /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
            this.d = /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */false;
            /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
            interrupt(/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */);
            /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
        }/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    }/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
   /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */ public void run(/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */) {
       /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */ try/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */ {
           /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */ this.c =/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */ this.a.createLocalPortForwarder(9395, this.b, 80);
          /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */  this.d =/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */ true;
         /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */   while (this.d) {
                try {/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
				//	SkStatus.logInfo("(Ping) ping server: " + this.b + " "  + "with (HEAD)");
				/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */	InetAddress address = InetAddress.getByName(this.b);
				/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */	String ipAddress = address.getHostAddress();/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
                 /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */   this.f = new Socket("127.0.0.1", 9395);
                  /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */  this.f.setSoTimeout(5000);
                  /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */  OutputStream outputStream = this.f.getOutputStream();
              /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */     outputStream.write(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append("GET http://").append(this.b).toString()).append("/ HTTP/1.1\r\nHost: ").toString()).append(this.b).toString()).append("\r\n\r\n").toString().getBytes());
              /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */      outputStream.flush(/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */);
                 /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.f.getInputStream()));
                    bufferedReader.readLine(/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */);
					HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(new StringBuffer().append("https://").append(this.b).toString()).openConnection();
                 /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */   httpURLConnection.setRequestMethod("HEAD");
                    long currentTimeMillis = System.currentTimeMillis();
                /* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */    int responseCode = httpURLConnection.getResponseCode();
				/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */	currentTimeMillis = System.currentTimeMillis() - currentTimeMillis;
          if (currentTimeMillis < 150) {
            SkStatus.logInfo(
                "Ping "
                    + responseCode
                    + " OK "
                    + " <font color=\"green\">"
                    + "("
                    + currentTimeMillis
                    + "ms"
                    + ")"
                    + "</font>");
          } else if (currentTimeMillis < 200) {
            SkStatus.logInfo(
                "Ping "
                    + responseCode
                    + " OK "
                    + " <font color=\"#ffc107\">"
                    + "("
                    + currentTimeMillis
                    + "ms"
                    + ")"
                    + "</font>");
          } else if (currentTimeMillis > 200) {
            SkStatus.logInfo(
                "Ping "
                    + responseCode
                    + " OK "
                    + " <font color=\"red\">"
                    + "("
                    + currentTimeMillis
                    + "ms"
                    + ")"
                    + "</font>");
          } else {
						//  SkStatus.logInfo("<font color='#C33E3D'>No Data</font>");
					}
				//	bufferedReader.close();
				//	outputStream.close();
				//	this.f.close();
				} catch (Exception e) {
				//	SkStatus.logInfo("Error occurred: " + e.getMessage());
				}
                try {
                    Thread.sleep(b());
                } catch (Exception e2) {
					//  SkStatus.logInfo("ping stopped");
                 //   this.c.close();
                //    this.c = null;
                    return;
                }
            }
        } catch (Exception e3) {
           SkStatus.logInfo(new StringBuffer().append("Ping: ").append(e3.toString()).toString());
        }/* EDICIÓN DE PANDA , NO DE OTRO GIL - @iPANDAX EN TG */
    }
}
