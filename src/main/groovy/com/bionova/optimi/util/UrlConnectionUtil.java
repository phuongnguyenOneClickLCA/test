package com.bionova.optimi.util;

import groovy.transform.CompileStatic;
import org.apache.commons.io.IOUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;

/**
 * @author Pasi-Markus Mäkeä
 */
@CompileStatic
public class UrlConnectionUtil {

    public static String getContentFromSslUrl(String address) {
        String content = "";

        try {
            doTrustToCertificates();
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            content = IOUtils.toString(is, "UTF-8");
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error in getting content from url " + address + ": " + e);
        }
        return content;
    }

    private static void doTrustToCertificates() throws NoSuchAlgorithmException, KeyManagementException {
        Security.addProvider(Security.getProvider("SunJSSE"));
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        return;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        return;
                    }
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
}
