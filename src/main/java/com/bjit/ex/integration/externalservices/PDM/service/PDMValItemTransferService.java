/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.externalservices.PDM.service;

import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import matrix.db.RelationshipWithSelect;

import javax.xml.ws.WebServiceException;

/**
 *
 * @author Sajjad
 */
public class PDMValItemTransferService {

    HttpURLConnection urlConnection = null;
    String serviceURL = null;
    public static String pdmServiceErrorResponse = "";
    public String pdmServiceSystemError = "";
    private static final org.apache.log4j.Logger REST_SERVICE_REQ_AND_RES_LOGGER = org.apache.log4j.Logger.getLogger(PDMValItemTransferService.class);
    private List<String> requestVALItemListToPDM = new ArrayList<>();
    private List<Item> failedValItemListToLN = new ArrayList<>();

    private List<Item> successfulValItemListToLN = new ArrayList<>();

    public void requestPDMVALItemTransferToLN(HashMap<String, List<RelationshipWithSelect>> missingItemCheckInPDM) throws Exception {
        ArrayList<String> responseVALItemListFromPDM = new ArrayList<>();
        try {
            requestVALItemListToPDM = new ArrayList<>(missingItemCheckInPDM.keySet());
            String itemCodes = "";
            int i = 0;
            for (String itemCode : requestVALItemListToPDM) {
                if (i > 0) {
                    itemCodes += ",";
                }
                itemCodes += itemCode;
                i++;
            }
            //itemCodes = "VAL0024363,VAL0024364";
            //String erp = "LN1001";
            String erp = ApplicationProperties.getProprtyValue("LN.erp");
            String urlParam = itemCodes + "/" + erp;
            String PDMServiceURL = ApplicationProperties.getProprtyValue("pdm.service.transfer.to.ln.url");
            //serviceURL = PDMServiceURL + urlParam + "/json";
            serviceURL = PDMServiceURL + urlParam;
            REST_SERVICE_REQ_AND_RES_LOGGER.info("PDM missing VAL item service:: " + serviceURL);

            String PDMServiceCall = ApplicationProperties.getProprtyValue("pdm.ln.service.request.retry");

            for (int j = 0; j < Integer.parseInt(PDMServiceCall); j++) {
                BufferedReader responseReader = (BufferedReader) getPDMServiceResponse(serviceURL);
                parseResponseResult(responseReader, missingItemCheckInPDM);
                REST_SERVICE_REQ_AND_RES_LOGGER.debug("PDM missing VAL item response :: " + responseVALItemListFromPDM);
            }
            urlConnection.disconnect();
        } catch (Exception exp) {
            REST_SERVICE_REQ_AND_RES_LOGGER.error(exp.getMessage());
            throw exp;
        }
    }

    public Reader getPDMServiceResponse(String serviceURL) throws MalformedURLException, IOException {
        Reader reader = null;
        ZonedDateTime pdmServiceRequestStartTime = null;
        try {
            pdmServiceRequestStartTime = ZonedDateTime.now();
            REST_SERVICE_REQ_AND_RES_LOGGER.info("VAL item transfer to LN started: " + pdmServiceRequestStartTime);

            URL url = new URL(serviceURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("username", ApplicationProperties.getProprtyValue("pdm.service.user"));
            urlConnection.setRequestProperty("password", ApplicationProperties.getProprtyValue("pdm.service.pass"));
            urlConnection.setRequestProperty("Authorization", ApplicationProperties.getProprtyValue("pdm.service.api.key"));
            if (urlConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + urlConnection.getResponseCode());
            }
            REST_SERVICE_REQ_AND_RES_LOGGER.info("Reading data from '" + serviceURL + "'");
            reader = new BufferedReader(new InputStreamReader(
                    (urlConnection.getInputStream())));
        } catch (MalformedURLException e) {
            REST_SERVICE_REQ_AND_RES_LOGGER.trace(e);
            throw e;
        } catch (IOException e) {
            REST_SERVICE_REQ_AND_RES_LOGGER.trace(e);
            throw e;
        } catch (Exception e) {
            REST_SERVICE_REQ_AND_RES_LOGGER.trace(e);
            throw e;
        } finally {
            ZonedDateTime pdmServiceRequestEndTime = ZonedDateTime.now();
            REST_SERVICE_REQ_AND_RES_LOGGER.info("VAL item transfer to LN ended: " + pdmServiceRequestEndTime);
            Duration pdmServiceElapsedTime = Duration.between(pdmServiceRequestStartTime, pdmServiceRequestEndTime);
            REST_SERVICE_REQ_AND_RES_LOGGER.info("Total time taken for VAL item transfer to LN " + pdmServiceElapsedTime.toMillis() + " millisecond");
        }
        return reader;
    }

    private ArrayList<String> parseResponseResult(BufferedReader responseReader, HashMap<String, List<RelationshipWithSelect>> missingItemCheckInPDM) throws Exception {
        try {
            //HashMap<String, RelationshipWithSelect> missingSuccessfulItemCheckInPDM = new HashMap<>();
            //boolean result = false;
            TransferResult tr = new Gson().fromJson(responseReader, TransferResult.class);
            if (tr == null) {
                throw new WebServiceException(ApplicationProperties.getProprtyValue("pdm.ln.service.null.response.message"));
            }
            if (tr.getErrorMessage() != null && tr.getErrorMessage().length() > 0) {
                throw new WebServiceException(tr.getErrorMessage());
            }
            List<Item> itemList = tr.getTransferResults().getItem();
            List<String> responseItemNameList = itemList.stream()
                    .map(item -> item.getName())
                    .collect(Collectors.toList());
            if (responseItemNameList.size() > 0) {
                requestVALItemListToPDM.forEach(item -> {
                    if (!responseItemNameList.contains(item)) {
                        setPdmServiceErrorResponse(ApplicationProperties.getProprtyValue("pdm.ln.service.item.not.found.message") + " : " + item);
                    }
                });
            }
            ArrayList<String> responseVALItemListFromPDM = new ArrayList<>();
            for (int i = 0; i < itemList.size(); i++) {
                ResponseResult responseResult = null;
                String transferResult = itemList.get(i).getTransferStatus();
                Item item = itemList.get(i);
                String itemName = item.getName();
                System.out.println("transferResult " + transferResult);
                //RelationshipWithSelect missingItemRelationshipWithSelect = missingVALItemList.get(itemName);
                //BusinessObjectWithSelect businessObjectWithSelect = missingItemRelationshipWithSelect.getTarget();

                //String revision = businessObjectWithSelect.getSelectData("revision");
                if (transferResult.contains("Successful")) {
                    //TransferObjectUtils.setMissingVALItemAttribute(missingItemRelationshipWithSelect);
                    //result = true;
                    responseResult = new ResponseResult(itemName, "RESULT OK", true);
                    REST_SERVICE_REQ_AND_RES_LOGGER.debug("Missing VAL item transfer Successful result: " + responseResult);
                    //missingSuccessfulItemCheckInPDM.put(itemName,missingItemRelationshipWithSelect);
                    TransferObjectUtils.setMissingVALItemAttribute(missingItemCheckInPDM.get(itemName).get(0));
                    responseVALItemListFromPDM.add(itemName);
                } else {
                    REST_SERVICE_REQ_AND_RES_LOGGER.debug("Missing VAL item transfer unsuccessful: " + transferResult);
//                    responseResult = new ResponseResult(itemName, itemList.get(i).getResultMessage(), true);
                    failedValItemListToLN.add(item);
                }

                Map<String, Object> itemInfoDetails = new LinkedHashMap<>();
                //itemInfoDetails.put("revision", revision);
/*                if (itemName!=null && itemName!="") {
                    ResponseResultSenderImpl.itemInfoMap.put(itemName.toUpperCase(), itemInfoDetails);
                    ResponseResultSenderImpl.successfulValItemResponseResult.add(responseResult);
                }*/
            }
            return responseVALItemListFromPDM;
        } catch (Exception exp) {
            REST_SERVICE_REQ_AND_RES_LOGGER.error(exp.getMessage());
            throw exp;
//            return null;
            //throw new Exception("May be service '" + serviceURL + "' is down now. Error : " + exp);
        }
    }

    public void setPDMServiceSystemError(String errorResponse) {
        StringBuilder pdmServiceErrorResponseBuilder = new StringBuilder(pdmServiceSystemError);
        if (pdmServiceErrorResponseBuilder.length() > 0) {
            pdmServiceErrorResponseBuilder.append("</br>");
            pdmServiceErrorResponseBuilder.append(errorResponse);
        } else {
            pdmServiceErrorResponseBuilder.append(errorResponse);
        }
        pdmServiceSystemError = pdmServiceErrorResponseBuilder.toString();
    }

//    public List<Item> getFailedValItemListToLN() {
//        return failedValItemListToLN;
//    }
    public List<Item> getSuccessfulValItemListToLN() {
        return successfulValItemListToLN;
    }

    public String getPDMServiceSystemError() {
        return pdmServiceSystemError;
    }

    public List<String> getRequestedVALItemListToPDM() {
        return requestVALItemListToPDM;
    }

    public List<Item> getFailedValItemListToLN() {
        return failedValItemListToLN;
    }

    public List<ResponseResult> getFailedItemResponseResults() {
        List<ResponseResult> responseResults = new ArrayList<>();
        failedValItemListToLN.forEach(item -> {
            responseResults.add(new ResponseResult(item.getName(), item.getResultMessage(), Boolean.FALSE));
        });
        return responseResults;
    }

    public ArrayList<String> getVALItemSendServiceResult(HashMap<String, List<RelationshipWithSelect>> missingItemCheckInPDM) throws Exception {
        ArrayList<String> responseVALItemListFromPDM = new ArrayList<>();
        try {
            requestVALItemListToPDM = new ArrayList<>(missingItemCheckInPDM.keySet());
            String itemCodes = "";
            int i = 0;
            for (String itemCode : requestVALItemListToPDM) {
                if (i > 0) {
                    itemCodes += ",";
                }
                itemCodes += itemCode;
                i++;
            }
            //itemCodes = "VAL0024363,VAL0024364";
            //String erp = "LN1001";
            String erp = ApplicationProperties.getProprtyValue("LN.erp");
            String urlParam = itemCodes + "/" + erp;
            String PDMServiceURL = ApplicationProperties.getProprtyValue("pdm.service.transfer.to.ln.url");
            //serviceURL = PDMServiceURL + urlParam + "/json";
            serviceURL = PDMServiceURL + urlParam;
            REST_SERVICE_REQ_AND_RES_LOGGER.info("PDM missing VAL item service:: " + serviceURL);

            String PDMServiceCall = ApplicationProperties.getProprtyValue("pdm.ln.service.request.retry");

            for (int j = 0; j < Integer.parseInt(PDMServiceCall); j++) {
                BufferedReader responseReader = (BufferedReader) getPDMServiceResponse(serviceURL);
                responseVALItemListFromPDM = parseResponseResult(responseReader, missingItemCheckInPDM);
                REST_SERVICE_REQ_AND_RES_LOGGER.debug("PDM missing VAL item response :: " + responseVALItemListFromPDM);
                if (responseVALItemListFromPDM != null) {
                    //missingItemCheckInPDM=parseResponseResultMap;
                    return responseVALItemListFromPDM;
                }
            }
            urlConnection.disconnect();
        } catch (Exception exp) {
            REST_SERVICE_REQ_AND_RES_LOGGER.error(exp.getMessage());
            throw exp;
        }
        return responseVALItemListFromPDM;
    }

    public void setPdmServiceErrorResponse(String errorResponse) {
        StringBuilder pdmServiceErrorResponseBuilder = new StringBuilder(pdmServiceErrorResponse);
        if (pdmServiceErrorResponseBuilder.length() > 0) {
            pdmServiceErrorResponseBuilder.append("</br>");
            pdmServiceErrorResponseBuilder.append(errorResponse);
        } else {
            pdmServiceErrorResponseBuilder.append(errorResponse);
        }
        pdmServiceErrorResponse = pdmServiceErrorResponseBuilder.toString();
    }

}
