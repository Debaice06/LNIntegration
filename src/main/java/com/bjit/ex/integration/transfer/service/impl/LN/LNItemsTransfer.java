/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service.impl.LN;

import com.bjit.context.DisableSSLCertificate;
import com.bjit.ex.integration.transfer.inputparam.TransferInputParams;
import com.bjit.ex.integration.transfer.service.ITransfer;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.ws.soap.LNHandlerResolver;
import com.bjit.ex.integration.transfer.ws.soap.LNSoapHandler;
import com.infor.businessinterface.item_val.*;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.util.MatrixException;
import org.apache.log4j.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.bjit.ex.integration.transfer.service.impl.LN.LNResponseUtil.msgToBeIgnored;
import com.bjit.ex.integration.transfer.util.MQLUtli;
import java.util.logging.Level;
import matrix.db.MQLCommand;

/**
 *
 * @author Sajjad
 */
public class LNItemsTransfer implements ITransfer {

    private static final Logger LN_ITEMS_TRANSFER_LOGGER = Logger.getLogger(LNItemsTransfer.class);
    private boolean isItemTransferedSuccessfully = true;
    private List<String> transferedItems = new ArrayList<>();

    @Override
    public Object transfer(TransferInputParams transferObj,boolean isService) throws NoSuchAlgorithmException, MalformedURLException, Exception {
        LN_ITEMS_TRANSFER_LOGGER.info("Updated items transfer process initiated...");
        ArrayList itemValData = (ArrayList) transferObj.getTrasnferObjMap().get("itemValList");
        LinkedHashMap<String, BusinessObject> itemIdMap = transferObj.getItemIdMap();
        Context context = transferObj.getContext();
        List<ResponseResult> itemTransferResponseResults = null;
        ArrayList<ResponseResult> results = new ArrayList<ResponseResult>();
        try {
            itemTransferResponseResults = itemTransfer(context, itemValData, itemIdMap);
            if (itemTransferResponseResults != null && itemTransferResponseResults.size() > 0) {
                results.addAll(itemTransferResponseResults);
                //return false;
                return results;
            }
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            LN_ITEMS_TRANSFER_LOGGER.error(ex.getMessage());
            throw ex;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            LN_ITEMS_TRANSFER_LOGGER.error(ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            LN_ITEMS_TRANSFER_LOGGER.error(ex.getMessage());
            throw ex;
        }
        return results;

    }

    /**
     *
     * @param context
     * @param itemValData
     * @param itemIdMap
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws MalformedURLException
     * @throws Exception
     */
    private ArrayList<ResponseResult> itemTransfer(Context context, ArrayList itemValData, LinkedHashMap<String, BusinessObject> itemIdMap)
            throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, Exception {
        TransferResult transferResult = new TransferResult();
        ArrayList<ResponseResult> results = null;
        ProcessItemDataResponseType response = null;
        Map<String, String> itemRevMap = new HashMap<>();
        try {
            try {
                String certificatePropertyValue = ApplicationProperties.getProprtyValue("LN.disable.certificate");
                LN_ITEMS_TRANSFER_LOGGER.info("Certificate disable for LN : " + certificatePropertyValue);
                if (certificatePropertyValue.equalsIgnoreCase("true")) {
                    DisableSSLCertificate.DisableCertificate();
                }
            } catch (KeyManagementException ex) {
                LN_ITEMS_TRANSFER_LOGGER.error(ex.getMessage());
                throw ex;
            } catch (NoSuchAlgorithmException ex) {
                LN_ITEMS_TRANSFER_LOGGER.error(ex.getMessage());
                throw ex;
            }

            //LN_TRANSFER_LOGGER.info("missingVALItemMap :::" + missingVALItemMap);
            LNHandlerResolver handeler = null;
            Integer compnay = Integer.parseInt(ApplicationProperties.getProprtyValue("ln.activationType.company"));
            handeler = new LNHandlerResolver();
            ActivationType activationType = new ActivationType();
            activationType.setCompany(compnay);
            activationType.setSender(ApplicationProperties.getProprtyValue("ln.activationType.sender"));

            LNSoapHandler soapHandler = new LNSoapHandler(activationType);
            handeler.addHandler(soapHandler);
            String itemWSDLUrl = ApplicationProperties.getProprtyValue("ln.item.transfer.wsdl.url");
            LN_ITEMS_TRANSFER_LOGGER.info("item WSDL Url :: " + itemWSDLUrl);
            URL url = null;
            try {
                url = new URL(itemWSDLUrl);
            } catch (MalformedURLException ex) {
                LN_ITEMS_TRANSFER_LOGGER.info(ex.getMessage());
                throw ex;
            }
            ItemVALService service = new ItemVALService(url);
            service.setHandlerResolver(handeler);
            com.infor.businessinterface.item_val.ItemVAL itemValService = service.getItemVALSoapPort();
            LNResponseUtil.setWebServiceTimeoutLimit((BindingProvider) itemValService);
            ProcessItemDataRequestType.ControlArea controlArea = new ProcessItemDataRequestType.ControlArea();
            controlArea.setProcessingScope(com.infor.businessinterface.item_val.ProcessingScope.BUSINESS_ENTITY);

            ProcessItemDataRequestType.DataArea dataArea = new ProcessItemDataRequestType.DataArea();
            dataArea.setMessageID(ApplicationProperties.getProprtyValue("ln.data.area.item.message.id"));
            dataArea.setReceiver(ApplicationProperties.getProprtyValue("ln.data.area.receiver"));

            ProcessItemDataRequestType requestDataType = new ProcessItemDataRequestType();
            requestDataType.setControlArea(controlArea);

            requestDataType.setDataArea(dataArea);

            List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList = itemValData;

            dataArea.getItemVAL().addAll(itemValList);

            try {
                LN_ITEMS_TRANSFER_LOGGER.info("Item Transfer Is In Progress..... Request Data ::: " + requestDataType);
                //Revision Adding: 1. fetch itemValList From Request
                List<ProcessItemDataRequestType.DataArea.ItemVAL> itemVALList = requestDataType.getDataArea().getItemVAL();
                //Revision Adding: 2. iterate over itemValList and keep items details to static Map
                for (ProcessItemDataRequestType.DataArea.ItemVAL itemVAL : itemVALList) {
                    String revision = itemVAL.getRevision();
                    Map<String, Object> itemInfoDetails = new LinkedHashMap<String, Object>();
                    itemInfoDetails.put("revision", revision);
                    itemRevMap.put(itemVAL.getItemID().getID().toUpperCase(), revision);
                }

                response = itemValService.processItemData(requestDataType);
                LN_ITEMS_TRANSFER_LOGGER.info("Item Transfer Is Completed... Response Data ::: " + response);

            } catch (com.infor.businessinterface.item_val.Result ex) {
                isItemTransferedSuccessfully = false;
                //LN Down issue. Error found here. Need to send mail to AMS.
                LN_ITEMS_TRANSFER_LOGGER.error("com.infor.businessinterface.item_val.Result Exception Occured! caused by: " + ex.getMessage());
                transferResult = LNResponseUtil.processErrorResult(ex, itemRevMap);
                //throw ex;
            } catch (SOAPFaultException e) {
                isItemTransferedSuccessfully = false;
                LN_ITEMS_TRANSFER_LOGGER.error("SOAPFaultException Occured! caused by: " + e.getMessage());
                transferResult = LNResponseUtil.getDefaultItemTransferResultDuringException(e, itemValList);
            } catch (WebServiceException we) {
                isItemTransferedSuccessfully = false;
                //we.printStackTrace();
                LN_ITEMS_TRANSFER_LOGGER.error("WebServiceException Occured During Item Transfer! caused by: " + we.getMessage());
                transferResult = LNResponseUtil.getDefaultItemTransferResultDuringException(we, itemValList);
                //WebServiceException webex = new WebServiceException(we.getMessage(),we);
                //throw webex;
            } catch (Exception ex) {
                isItemTransferedSuccessfully = false;
                //ex.printStackTrace();
                LN_ITEMS_TRANSFER_LOGGER.error("Exception Occured During Item Transfer! caused by: " + ex.getMessage());
                String defaultErrorMessage = LNResponseUtil.getDefaultErrorMessage(ex, "ITEM");
                transferResult = LNResponseUtil.getDefaultItemTransferResultDuringException(new Exception(defaultErrorMessage), itemValData);
                //throw ex;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            isItemTransferedSuccessfully = false;
            LN_ITEMS_TRANSFER_LOGGER.error("Exception Occurred During Item Transfer! caused by: " + exception.getMessage());
            String defaultErrorMessage = LNResponseUtil.getDefaultErrorMessage(exception, "ITEM");
            transferResult = LNResponseUtil.getDefaultItemTransferResultDuringException(new Exception(defaultErrorMessage), itemValData);
        }

        ArrayList<String> successfullyTransferredItemList = new ArrayList<>();
        if (response != null) {
            transferResult = processResponse(response, itemIdMap, context, itemRevMap);
        }

        results = transferResult.getResults();
        List<ResponseResult> unspecifiedResponseResult = LNResponseUtil.getUnspecifiedItemsResponseResults(context, itemIdMap, results);
        results.addAll(unspecifiedResponseResult);
        results.removeIf(responseResult -> responseResult == null || responseResult.getItem() == null);

        /*if (results != null && results.size() > 0) {
            results = transferResult.getResults();
            for (int i = 0; i < results.size(); i++) {
                ResponseResult responseResult = (ResponseResult) transferResult.getResults().get(i);
                if (responseResult !=null ) {
                    LN_ITEMS_TRANSFER_LOGGER.info("Item Name: " + responseResult.getItem() + " Result: " + responseResult.getResultText());
                    successfullyTransferredItemList.add(responseResult.getItem());
                }
            }
        }
        LN_ITEMS_TRANSFER_LOGGER.info("\n" + "Successfully Transferred ItemList ::: " + successfullyTransferredItemList);
         */
        return results;

    }

    /**
     *
     * @param response
     * @param itemIdMap
     * @param context
     * @return
     */
    private TransferResult processResponse(ProcessItemDataResponseType response, LinkedHashMap<String, BusinessObject> itemIdMap, Context context, Map<String, String> itemRevMap) {
        List<ProcessItemDataResponseType.DataArea.ItemVAL> successfulItemValList = response.getDataArea().getItemVAL();
        ArrayList<ResponseResult> resultList = new ArrayList<>();
        for (ProcessItemDataResponseType.DataArea.ItemVAL successfulItemVal : successfulItemValList) {
            String itemName = successfulItemVal.getItemID().getID().trim();
            String revision = "";

            if (itemRevMap.containsKey(itemName)) {
                revision = itemRevMap.get(itemName);
            }

            ResponseResult responseResult = new ResponseResult(itemName, revision, "RESULT OK", true);
            resultList.add(responseResult);
            transferedItems.add(itemName);
        }

        if (response.getInformationArea() != null) {
            isItemTransferedSuccessfully = false;
            List<InformationMessage> failedItemMsgList = response.getInformationArea().getMessage();
            for (InformationMessage failedItemMsg : failedItemMsgList) {
                if (!msgToBeIgnored.contains(failedItemMsg.getMessageCode())) {
                    ResponseResult responseResult = LNResponseUtil.parseErrorMessage(failedItemMsg.getMessageText(),itemRevMap);
                    resultList.add(responseResult);
                }
            }
        }

        /*LinkedHashMap<ProcessItemDataResponseType.DataArea.ItemVAL.ItemID, BusinessObject> successfulItemIdMap = fetchSuccessfulItemIdMap(itemIdMap, successfulItemValList);
        for (Map.Entry<ProcessItemDataResponseType.DataArea.ItemVAL.ItemID, BusinessObject> entry : successfulItemIdMap.entrySet()) {
            BusinessObject itemObject = entry.getValue();
            try {
                //itemObject.setAttributeValue(context, ApplicationProperties.getProprtyValue("source.att.send.to.erp"), Boolean.TRUE.toString());
                LN_ITEMS_TRANSFER_LOGGER.debug(entry.getKey().getID().trim() + " : 'Transfer to ERP' value updated to TRUE.");
            } catch (Exception ex) {
                LN_ITEMS_TRANSFER_LOGGER.error(entry.getKey().getID().trim() + " : MatrixException Occurred! caused by: " + ex.getMessage());
            }
        }*/
        TransferResult tr = new TransferResult();
        tr.setResults(resultList);
        return tr;
    }

    @Override
    public Object transfer(TransferInputParams transferObj, boolean isService, List<String> transferedItems) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param itemIdMap
     * @param successfulItemValList
     * @return
     */
    /*private LinkedHashMap<ProcessItemDataResponseType.DataArea.ItemVAL.ItemID, BusinessObject> fetchSuccessfulItemIdMap(LinkedHashMap<String, BusinessObject> itemIdMap,
            List<ProcessItemDataResponseType.DataArea.ItemVAL> successfulItemValList) {
        LinkedHashMap<ProcessItemDataResponseType.DataArea.ItemVAL.ItemID, BusinessObject> successfulItemIdMap = new LinkedHashMap<>();
        int itemCounter = 0;
        for (Map.Entry<String, BusinessObject> entry : itemIdMap.entrySet()) {
            for (int itemIterator = itemCounter; itemIterator < successfulItemValList.size(); itemIterator++) {
                if (entry.getKey().split("_")[0].equalsIgnoreCase(successfulItemValList.get(itemCounter).getItemID().getID().trim())) {
                    successfulItemIdMap.put(successfulItemValList.get(itemCounter).getItemID(), entry.getValue());
                    itemCounter = itemIterator;
                }
            }

        return successfulItemIdMap;
    }*/
//    public String getRevision(Context context, String itemName) {
//        String revision = "";
//        if (itemName.contains("-")) {
//            String[] part = itemName.split("-", 2);
//            String lowarCase = part[0].toLowerCase();
//            itemName = lowarCase + "-" + part[1];
//        }
//
//        try {
//            StringBuilder qureyBuilder = new StringBuilder();
//            String command = "";
//            qureyBuilder = qureyBuilder.append("temp query bus ").append("* ").append(itemName).append(" * select revision");
//            MQLCommand mqlCommand = new MQLCommand();
//            command = qureyBuilder.toString();
//            mqlCommand.executeCommand(context, command);
//            String result = mqlCommand.getResult();
//            revision = result.split("revision = ")[1].trim();
//            mqlCommand.close(context);
//
//        } catch (MatrixException ex) {
//            LN_ITEMS_TRANSFER_LOGGER.error("MQL Error" + ex);
//        }
//        return revision;
//    }
//           public List<Map<String, String>> getResultFromQuery(String queryResult, String[] attrs) {
//        List<Map<String, String>> resultArray = new ArrayList<>();
//        String[] tempList = queryResult.split("[\\r\\n]+");
//        for (int i = 0; i < tempList.length; i++) {
//            Map<String, String> objectId = new HashMap<>();
//            String[] tempSingleList = tempList[i].split("\\|");
//            objectId.put("type", tempSingleList[0]);
//            objectId.put("name", tempSingleList[1]);
//            objectId.put("revision", tempSingleList[2]);
//            for (int k = 3; k < tempSingleList.length; k++) {
//                String key = attrs[k - 3];
//                String value = tempSingleList[k];
//                objectId.put(key, value);
//            }
//            resultArray.add(objectId);
//        }
//        return resultArray;
//    }
}
