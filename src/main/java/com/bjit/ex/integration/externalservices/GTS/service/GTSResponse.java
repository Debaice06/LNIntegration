/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.externalservices.GTS.service;

import com.bjit.context.DisableSSLCertificate;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.RequestUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sajjad
 */
public class GTSResponse {

    private static final org.apache.log4j.Logger GTSResponse_LOGGER = org.apache.log4j.Logger.getLogger(GTSResponse.class);

    public static String getBungdleID(String itemID) throws IOException, MalformedURLException {
        String gtsUrl = ApplicationProperties.getProprtyValue("gts.service.translations.url");
        //String param = itemId;
        //String param = "S0136987;S0137070";
        String param = "S0136987";
        String url = gtsUrl + param;
        String sb = RequestUtil.getResponse(url);
        System.out.println("Response :: " + sb.toString());
        boolean result = false;
        TranslationsResult tr = new Gson().fromJson(sb, TranslationsResult.class);
        String bundleId = Integer.toString(tr.getData().get(0).getBundleId());
        System.out.println("bundleId ::: " + bundleId);
        return bundleId;
    }

    public static List<String> getUpdatedBundleIds() throws KeyManagementException, NoSuchAlgorithmException, IOException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String startDate = "";
        String lastSuccessfulTrasnferDate = ApplicationProperties.getProprtyValue("gts.nightly.succeful.transfer.date");
        GTSResponse_LOGGER.info("Last Successful Transfer Date ::: "+lastSuccessfulTrasnferDate);
        
        if (lastSuccessfulTrasnferDate.equals("") || lastSuccessfulTrasnferDate == null){
            startDate = dateFormat.format(yesterday);
        }else{
            startDate = lastSuccessfulTrasnferDate;
        }

        String getsServiceUrl = ApplicationProperties.getProprtyValue("gts.service.bundleId.url").trim() + "start_date=" + startDate + "&end_date=today";
        //String getsServiceUrl = "https://jkls0326vm21a.vstage.co/gts_tempsit/api/translations.php?start_date=2020-07-14&end_date=2020-07-16";
        GTSResponse_LOGGER.info("GTS updated bundle id URL ::: " + getsServiceUrl);
        DisableSSLCertificate.DisableCertificate();
        String result = RequestUtil.getResponse(getsServiceUrl);
        List<String> bundleIdList;
        try {
            bundleIdList = fetchBundleIds(result);
        } catch (Exception ex) {
            bundleIdList = new ArrayList<String>();
            GTSResponse_LOGGER.error("Exception occured while fetching bundle Ids from response. " + ex.getCause());
        }
        GTSResponse_LOGGER.debug("Bundle List for LN: " + bundleIdList);
        return bundleIdList;
    }

    private static List<String> fetchBundleIds(String response) throws Exception {
        JsonElement jelement = new JsonParser().parse(response);
        JsonObject jObject = jelement.getAsJsonObject();
        jObject = jObject.getAsJsonObject("data");
        JsonArray bundleIdArray = jObject.getAsJsonArray("bundle_id");
        List<String> bundleIdList = new ArrayList<>();
        for (int i = 0; i < bundleIdArray.size(); i++) {
            bundleIdList.add(bundleIdArray.get(i).getAsString());
        }
        return bundleIdList;
    }

}
