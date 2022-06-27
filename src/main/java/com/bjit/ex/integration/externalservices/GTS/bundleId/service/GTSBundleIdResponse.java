/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.externalservices.GTS.bundleId.service;

import com.bjit.context.DisableSSLCertificate;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.RequestUtil;
import com.google.gson.Gson;

import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 *
 * @author Sajjad
 */
public class GTSBundleIdResponse {

    private static final org.apache.log4j.Logger GTSBundleIdResponse_LOGGER = org.apache.log4j.Logger.getLogger(GTSBundleIdResponse.class);

    private HashMap<String,HashMap<String,String>> abbreviationDataMap = new HashMap<>();
    
    public GTSBundleIdResponse(String bundleId) throws IOException,MalformedURLException, KeyManagementException, NoSuchAlgorithmException {
        try {
            String table = "all";
            String bundle_id = bundleId;
            String params = "table=" + table + "&" + "bundle_id=" + bundle_id;
            String gtsUrl = ApplicationProperties.getProprtyValue("gts.service.bundleId.url");
            String url = gtsUrl + params;
            GTSBundleIdResponse_LOGGER.info("GTS Url :: "+url);
            DisableSSLCertificate.DisableCertificate();
            String responseResult = RequestUtil.getResponse(url);
            GTSBundleIdResponse_LOGGER.debug("GTS Response :: "+responseResult);
            BundleIdResult result = new Gson().fromJson(responseResult, BundleIdResult.class);
            HashMap<String,String> LNLanguageIdMap = (HashMap<String,String>) ApplicationProperties.getLNLanguageIDMap();
            //HashMap<String,String> abbreviationDataMap = new HashMap<>();
            if (result.getStatus().equals("OK")) {
				if (result.getData().size() > 0) {
					for (int i = 0; i < result.getData().get(0).getDataList().size(); i++) {
						String lang = result.getData().get(0).getDataList().get(i).getLanguage();
						String abbreviation = result.getData().get(0).getDataList().get(i).getAbbreviation();
						String text = result.getData().get(0).getDataList().get(i).getText();
						if (text.length() > 0 || abbreviation.length() > 0) {
							String langCode = LNLanguageIdMap.get(lang);
							if (langCode != null && langCode.length() > 0) {
								abbreviationDataMap.put(langCode.split(";")[1], new HashMap<>());
								if (text != null && text.length() > 0){
									abbreviationDataMap.get(langCode.split(";")[1]).put("text", text);
								}
								if (abbreviation != null && abbreviation.length() > 0){
									abbreviationDataMap.get(langCode.split(";")[1]).put("abbreviation", abbreviation);
								}
							}
						}
					}
					setAbbreviationDataMap(abbreviationDataMap);
					GTSBundleIdResponse_LOGGER.debug("Abbreviation Data Map : "+abbreviationDataMap);
				}
			}
			else {
                throw new WebServiceException(result.getMessage());
            }	
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            GTSBundleIdResponse_LOGGER.trace(ex);
            throw ex;
        } catch (Exception ex){
            GTSBundleIdResponse_LOGGER.trace(ex);
            throw ex;
        }
    }
    
    
    public HashMap<String, HashMap<String,String>> getAbbreviationDataMap() {
        return abbreviationDataMap;
    }

    private void setAbbreviationDataMap(HashMap<String, HashMap<String,String>> abbreviationDataMap) {
        this.abbreviationDataMap = abbreviationDataMap;
    }
}
