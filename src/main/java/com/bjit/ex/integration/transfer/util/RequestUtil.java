/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sajjad
 */
public class RequestUtil {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final org.apache.log4j.Logger Request_Util_LOGGER = org.apache.log4j.Logger.getLogger(RequestUtil.class);

    public static String getResponse(String url) throws MalformedURLException, IOException{
        String response = null;
        HttpURLConnection con = null;
        try {
            URL urlObj = new URL(url);
            con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer responseBuffer  = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseBuffer.append(inputLine);
                }
                in.close();
                response = responseBuffer.toString();
                System.out.println(responseBuffer.toString());
            } else {
                System.out.println("GET request not worked");
                Request_Util_LOGGER.info("responseCode : "+responseCode+" Unable to connect to : "+url);
            }
        } catch (MalformedURLException ex) {
            Request_Util_LOGGER.error(ex);
            throw ex;
        } catch (IOException e) {
            Request_Util_LOGGER.error(e);
            throw e;
        } finally{
            if(con!=null){
                con.disconnect();
            }            
        }
        return response;
    }
}
