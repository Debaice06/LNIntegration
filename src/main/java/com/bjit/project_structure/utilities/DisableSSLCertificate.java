/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.project_structure.utilities;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.log4j.Logger;

/**
 *
 * @author BJIT
 */
public class DisableSSLCertificate {

    private static TrustManager[] trustAllCerts;
    private static HostnameVerifier allHostsValid;
    private final static Logger DISABLE_CERTIFICATE_LOGGER = Logger.getLogger(DisableSSLCertificate.class);

    public static void DisableCertificate() throws KeyManagementException, NoSuchAlgorithmException {
        try {
            DISABLE_CERTIFICATE_LOGGER.info("Disabling SSL certificates for the Uri request");
            trustAllCertificates();
            installTrustManager();
            validateAllHosts();
            installTrustedHost();
            doClear();
            DISABLE_CERTIFICATE_LOGGER.info("Disabling SSL certificates has been completed");
        } catch (KeyManagementException | NoSuchAlgorithmException exp) {
            DISABLE_CERTIFICATE_LOGGER.error("Disabling SSL certificates couldn't be completed because of : " + exp.getMessage());
            throw exp;
        }
    }

    private static void trustAllCertificates() {
        // Create a trust manager that does not validate certificate chains
        try {
            DISABLE_CERTIFICATE_LOGGER.info("Creating a trust manager which will not validate certificate chains");
            trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };
        } catch (Exception exp) {
            doClear();
            DISABLE_CERTIFICATE_LOGGER.error("Trust manager creation failed because of : " + exp.getMessage());
            throw exp;
        }

    }

    private static void installTrustManager() throws KeyManagementException, NoSuchAlgorithmException {
        // Install the all-trusting trust manager
        try {
            DISABLE_CERTIFICATE_LOGGER.info("Installing all trusting trust manager");
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException | KeyManagementException exp) {
            doClear();
            DISABLE_CERTIFICATE_LOGGER.error("All trusting trust manager couldn't be installed because of : " + exp.getMessage());
            throw exp;
        }
    }

    private static void validateAllHosts() {
        // Create all-trusting host name verifier
        //allHostsValid = (String hostname, SSLSession session) -> true;
        try {
            DISABLE_CERTIFICATE_LOGGER.info("Creating all-trusting host name verifier");
            allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
        } catch (Exception exp) {
            doClear();
            DISABLE_CERTIFICATE_LOGGER.error("All-trusting host name verifier couldn't be created because of : " + exp.getMessage());
            throw exp;
        }

    }

    private static void installTrustedHost() {
        // Install the all-trusting host verifier
        try {
            DISABLE_CERTIFICATE_LOGGER.info("Installing all-trusting host name verifier");
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception exp) {
            doClear();
            DISABLE_CERTIFICATE_LOGGER.error("All-trusting host name verifier couldn't be installed because of : " + exp.getMessage());
            throw exp;
        }
    }

    public static void doClear() {
        try {
            DISABLE_CERTIFICATE_LOGGER.info("Cleaning Objects");
            trustAllCerts = null;
            allHostsValid = null;
        } catch (Exception exp) {
            DISABLE_CERTIFICATE_LOGGER.error("Cleaning Objects has failed because of : " + exp.getMessage());
            throw exp;
        }
    }
}
