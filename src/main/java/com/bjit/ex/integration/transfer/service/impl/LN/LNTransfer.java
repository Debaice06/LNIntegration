/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service.impl.LN;

import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.infor.businessinterface.billofmaterial_val.BillOfMaterialVALService;
import com.infor.businessinterface.billofmaterial_val.ProcessItemBOMDataRequestType;
import com.infor.businessinterface.billofmaterial_val.ProcessItemBOMDataResponseType;
import com.bjit.ex.integration.transfer.inputparam.TransferInputParams;
import com.bjit.ex.integration.transfer.service.ITransfer;
import com.bjit.context.DisableSSLCertificate;
import static com.bjit.ex.integration.transfer.service.impl.LN.LNResponseUtil.msgToBeIgnored;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.ws.soap.LNHandlerResolver;
import com.bjit.ex.integration.transfer.ws.soap.LNSoapHandler;
import com.bjit.ex.integration.transfer.actions.utilities.BusinessObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.SelectionCodeType;
import com.infor.businessinterface.item_val.ActivationType;
import com.infor.businessinterface.item_val.InformationMessage;
import com.infor.businessinterface.item_val.ItemVALService;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType.DataArea.ItemVAL;
import com.infor.businessinterface.item_val.ProcessItemDataResponseType;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.util.MatrixException;
import org.apache.log4j.Logger;

/**
 *
 * @author Sajjad
 */
public class LNTransfer implements ITransfer {

    private static final Logger LN_TRANSFER_LOGGER = Logger.getLogger(LNTransfer.class);
    private boolean isItemTransferedSuccessfully = true;
    private boolean isBOMTransferSuccessful = false;
    // private List<String> transferedItems = new ArrayList<>();
    private static final int transferRetryNumber = Integer.parseInt(ApplicationProperties.getProprtyValue("transfer.retry.count"));
    private int transferRetryCount = 0;

    @Override
    public Object transfer(TransferInputParams transferObj, boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private enum TransferType {
        ITEM, BOM;
    }

    // LN BOM Transfer Service
    @Override
    public Object transfer(TransferInputParams transferObj, boolean isService, List<String> transferedItems) throws Exception {
        ArrayList itemValData = (ArrayList) transferObj.getTrasnferObjMap().get("itemValList");
        ArrayList nonTransferredItemValData = new ArrayList<>();
        nonTransferredItemValData.addAll(itemValData);
        ArrayList bomValData = (ArrayList) transferObj.getTrasnferObjMap().get("bomValList");
        LinkedHashMap<String, BusinessObject> itemIdMap = transferObj.getItemIdMap();
        Context context = transferObj.getContext();
        boolean isNightlyTransfer = false;
        boolean bomMissingInLN = false;
        boolean isItemReleasePurposePlanning = false;
        if (transferObj.getTransferType().equalsIgnoreCase("NightlyUpdateToLN")) {
            isNightlyTransfer = true;
        } else if (transferObj.getTransferType().equalsIgnoreCase("LN")) {
            List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList = itemValData;
            /*
            In itemValList there should be only one itemVAL. because system is sending only one item and
            it's first level BOM.
             */
            for (ItemVAL itemVAL : itemValList) {
                String selectionCode = itemVAL.getSelectionCode();
                if (selectionCode != null && selectionCode.equalsIgnoreCase(ApplicationProperties.getProprtyValue("LN.selection.code.BOM.missing.in.LN"))) {
                    bomMissingInLN = true;
                }
                // String signalCode = itemVAL.getSignal();
                if (isService) {
                    try {
                        if (itemVAL.getSignal().equals(null)) {

                        } else {

                            String signalCode = itemVAL.getSignal();
                            if (signalCode.equalsIgnoreCase("DRA")) {

                                isItemReleasePurposePlanning = true;
                            }
                        }
                    } catch (Exception ex) {

                        isItemReleasePurposePlanning = true;
                    }
                } else {
                    String signalCode = itemVAL.getSignal();
                    if (signalCode.equalsIgnoreCase("DRA")) {

                        isItemReleasePurposePlanning = true;
                    }
                }
                String itemName = itemVAL.getItemID().getID();
                String revision = itemVAL.getRevision();
                String itemIdKey = itemName + "_" + revision;
                BusinessObject bo = itemIdMap.get(itemIdKey);
                String transferredToErp = BusinessObjectUtils.getItemAttributeValue(context, bo.getObjectId(), ApplicationProperties.getProprtyValue("source.att.send.to.erp"));
                // transferredToErp ="FALSE";
                /* VSIX-3717
                If item already transferred to LN, then item will not transfer to LN again but BOM will transfer.
                After BOM transfer, item will send to update selection code(BOM marking). "itemValData" contains
                all item data and "nonTransferredItemValData" contains only items which are not transferred yet.
                 */
                if (transferredToErp.equalsIgnoreCase("TRUE")) {
                    nonTransferredItemValData.remove(itemVAL);
                }
            }
        }

        /*
        Item transfer.
         */
//        long itemTransferStartTime = System.currentTimeMillis();
        List<ResponseResult> results = new ArrayList<>();

        // results = (List<ResponseResult>) iTransferAction.processItemsTransfer(context, itemBusinessObject, transferObjectUtils);
//        boolean isBeforeBOMTransfer = true;
//        try {
//            isItemTransferedSuccessfully = true;
//            List<ResponseResult> itemTransferResponseResults = null;
//            if (!nonTransferredItemValData.isEmpty()) {
//                if (isItemReleasePurposePlanning && ApplicationProperties.getProprtyValue("LN.DRA.item.twice.send").equalsIgnoreCase("true")) {
//                    LN_TRANSFER_LOGGER.info("Item's Release Purpose is planning, Item will send twice in LN.");
//                    for (int i = 0; i < 2; i++) {
//                        itemTransferResponseResults = itemTransfer(context, nonTransferredItemValData, itemIdMap, isBeforeBOMTransfer, false, bomMissingInLN, transferedItems);
//                    }
//                } else {
//                    itemTransferResponseResults = itemTransfer(context, nonTransferredItemValData, itemIdMap, isBeforeBOMTransfer, false, bomMissingInLN, transferedItems);
//                }
//
//            }
//            if (itemTransferResponseResults != null && itemTransferResponseResults.size() > 0) {
//                results.addAll(itemTransferResponseResults);
//            }
//        } catch (NoSuchAlgorithmException ex) {
//            throw ex;
//        } catch (MalformedURLException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            throw ex;
//        }
//        long itemTransferEndTime = System.currentTimeMillis();
//        LN_TRANSFER_LOGGER.info("Elapsed Time : Item Transfer Time " + (itemTransferEndTime - itemTransferStartTime));

        /*
                Cost Calculation.
         */
        if (isItemTransferedSuccessfully) {
            long itemCostCalculationStartTime = System.currentTimeMillis();
            ItemCostCalculation itemCostCalculation = new ItemCostCalculation();
            itemCostCalculation.calculateCost(transferedItems);
            long itemCostCalculationEndTime = System.currentTimeMillis();
            LN_TRANSFER_LOGGER.info("Elapsed Time : Item Cost Calculation Time " + (itemCostCalculationStartTime - itemCostCalculationEndTime));
        }

        /*
        BOM Transfer.
         */
        long bomTransferStartTime = System.currentTimeMillis();
        if (isItemTransferedSuccessfully && (bomValData != null) && !isNightlyTransfer) {
            if (bomValData.size() > 0) {
                List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL> bomValList = bomValData;
                Map<String, Object> itemInfoDetails = new LinkedHashMap<>();
                for (ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL bomVal : bomValList) {
                    for (ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines bomLine : bomVal.getLines()) {
                        itemInfoDetails.put("revision", bomLine.getComponentRevision());
                    }
                }
                try {
                    List<ResponseResult> bomTransferResponseResults = bomTransfer(bomValData);
                    if (bomTransferResponseResults != null && bomTransferResponseResults.size() > 0) {
                        results.addAll(bomTransferResponseResults);
                        isBOMTransferSuccessful = true;
                    }

                } catch (NoSuchAlgorithmException ex) {
                    isBOMTransferSuccessful = false;
                    LN_TRANSFER_LOGGER.error("Is BOM Transfer Successful : " + isBOMTransferSuccessful);
                    throw ex;
                } catch (MalformedURLException ex) {
                    isBOMTransferSuccessful = false;
                    LN_TRANSFER_LOGGER.error("Is BOM Transfer Successful : " + isBOMTransferSuccessful);
                    throw ex;
                } catch (Exception ex) {
                    isBOMTransferSuccessful = false;
                    LN_TRANSFER_LOGGER.error("Is BOM Transfer Successful : " + isBOMTransferSuccessful);
                    throw ex;
                }
            } else {
                LN_TRANSFER_LOGGER.info("Standalone Item : BOM Transfer is not possible!! ");
            }

        } else {
            LN_TRANSFER_LOGGER.info("Error in item sending or there is no BOM data.");
        }
        long bomTransferEndTime = System.currentTimeMillis();
        LN_TRANSFER_LOGGER.info("Elapsed Time : BOM Transfer Time " + (bomTransferEndTime - bomTransferStartTime));
        //   LN_TRANSFER_LOGGER.info("Is BOM Transfer Successful : " + isBOMTransferSuccessful);
        /*
        Update selection code by transfering ITEM after BOM  transfer. 
         */
        //   isBeforeBOMTransfer = false;

        //Gather item to update selection code item for item with child.
//        if (bomValData != null && bomValData.size() > 0 && !isNightlyTransfer) {
//            LN_TRANSFER_LOGGER.info("Update 'Selection Code' by transfering ITEM after BOM transfer.");
//            long itemTransferStartTime1 = System.currentTimeMillis();
//            try {
//                List<ResponseResult> itemTransferResponseResults = null;
//                if (!itemValData.isEmpty()) {
//                    itemTransferResponseResults = itemTransfer(context, itemValData, itemIdMap, isBeforeBOMTransfer, isBOMTransferSuccessful, bomMissingInLN);
//                }
//                if (itemTransferResponseResults != null && itemTransferResponseResults.size() > 0) {
//                    results.addAll(itemTransferResponseResults);
//                }
//
//            } catch (NoSuchAlgorithmException ex) {
//                throw ex;
//            } catch (MalformedURLException ex) {
//                throw ex;
//            } catch (Exception ex) {
//                throw ex;
//            }
//            long itemTransferEndTime1 = System.currentTimeMillis();
//            LN_TRANSFER_LOGGER.info("Elapsed Time : Update selection code (Item Transfer Time) " + (itemTransferEndTime1 - itemTransferStartTime1));
//        }
//        ResponseResultSenderImpl resultSender = new ResponseResultSenderImpl();
//        if (isNightlyTransfer) {
//            LN_TRANSFER_LOGGER.info("Nightly transfer. No BOM will send to LN.");
//            //resultSender.nightlyUpdateResultSend(results);
//            return results;
//        } else {
//            resultSender.send(results);
//        }
        if (!ResponseResultSender.isAllItemTransfered) {
//            throw new Exception("One or more of item is not transfered!");
            LN_TRANSFER_LOGGER.error("One or more of item is not transferred!");
        }
        return results;
    }

    private ArrayList<ResponseResult> itemTransfer(Context context, ArrayList itemValData,
            LinkedHashMap<String, BusinessObject> itemIdMap, boolean isBeforeBOMTransfer, boolean isBOMTransferSuccessful, boolean bomMissingInLN, List<String> transferedItems) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, Exception {
        TransferResult transferResult = new TransferResult();
        ArrayList<ResponseResult> results = null;
        ProcessItemDataResponseType response = null;
        Map<String, String> itemRevMap = new HashMap<>();
        try {
            if (!isBeforeBOMTransfer) {
                LN_TRANSFER_LOGGER.info("\n" + ":::::: ITEM TRANSFER (Update Selection Code) :::::");
            } else {
                LN_TRANSFER_LOGGER.info("\n" + ":::::: ITEM TRANSFER :::::");
            }
            try {
                String certificatePropertyValue = ApplicationProperties.getProprtyValue("LN.disable.certificate");
                LN_TRANSFER_LOGGER.info("Certificate disable for LN : " + certificatePropertyValue);
                if (certificatePropertyValue.equalsIgnoreCase("true")) {
                    DisableSSLCertificate.DisableCertificate();
                }
            } catch (KeyManagementException ex) {
                LN_TRANSFER_LOGGER.error(ex.getMessage());
                throw ex;
            } catch (NoSuchAlgorithmException ex) {
                LN_TRANSFER_LOGGER.error(ex.getMessage());
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
            LN_TRANSFER_LOGGER.info("item WSDL Url :: " + itemWSDLUrl);
            URL url = null;
            try {
                url = new URL(itemWSDLUrl);
            } catch (MalformedURLException ex) {
                LN_TRANSFER_LOGGER.info(ex.getMessage());
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
            if (!isBeforeBOMTransfer) {
                /*
            In itemValList there should be only one itemVAL. because system is sending only one item and
            it's first level BOM.
                 */
                LN_TRANSFER_LOGGER.info("Is BOM Transfer Successful : " + isBOMTransferSuccessful);
                itemValList.forEach((itemVAL) -> {
                    if (isBOMTransferSuccessful) {
                        if (bomMissingInLN) {
                            itemVAL.setSelectionCode(ApplicationProperties.getProprtyValue("LN.selection.code.BOM.missing.in.LN"));
                        } else {
                            itemVAL.setSelectionCode(ApplicationProperties.getProprtyValue("LN.selection.code.BOM.transfer.successful.in.LN"));
                        }
                    } else {
                        itemVAL.setSelectionCode(ApplicationProperties.getProprtyValue("LN.selection.code.BOM.transfer.error.in.LN"));
                    }
                });
            }

            dataArea.getItemVAL().addAll(itemValList);

            try {
                LN_TRANSFER_LOGGER.info("Item Transfer Is In Progress..... Request Data ::: " + requestDataType);
                //Revision Adding: 1. fetch itemValList From Request
                List<ItemVAL> itemVALList = requestDataType.getDataArea().getItemVAL();
                //Revision Adding: 2. iterate over itemValList and keep items details to static Map
                for (ItemVAL itemVAL : itemVALList) {
                    String revision = itemVAL.getRevision();
                    Map<String, Object> itemInfoDetails = new LinkedHashMap<String, Object>();
                    itemInfoDetails.put("revision", revision);
                    itemRevMap.put(itemVAL.getItemID().getID().toUpperCase(), revision);
                }

                response = itemValService.processItemData(requestDataType);
                LN_TRANSFER_LOGGER.info("Item Transfer Is Completed... Response Data ::: " + response);

            } catch (com.infor.businessinterface.item_val.Result re) {
                isItemTransferedSuccessfully = false;
                LN_TRANSFER_LOGGER.error("com.infor.businessinterface.item_val.Result Exception Occured! caused by: " + re.getMessage());
                re.printStackTrace();
                transferResult = LNResponseUtil.processErrorResult(re, itemRevMap);
                //throw ex;
            } catch (SOAPFaultException e) {
                isItemTransferedSuccessfully = false;
                LN_TRANSFER_LOGGER.error("SOAPFaultException Occured! caused by: " + e.getMessage());
                reTransferItem(context, itemValData, itemIdMap, isBeforeBOMTransfer, isBOMTransferSuccessful, bomMissingInLN, transferedItems);
                e.printStackTrace();
                transferResult = LNResponseUtil.getDefaultItemTransferResultDuringException(e, itemValList);
            } catch (WebServiceException we) {
                isItemTransferedSuccessfully = false;
                //we.printStackTrace();
                LN_TRANSFER_LOGGER.error("WebServiceException Occured During Item Transfer! caused by: " + we.getMessage());
                we.printStackTrace();
                reTransferItem(context, itemValData, itemIdMap, isBeforeBOMTransfer, isBOMTransferSuccessful, bomMissingInLN, transferedItems);
                transferResult = LNResponseUtil.getDefaultItemTransferResultDuringException(we, itemValList);
                //WebServiceException webex = new WebServiceException(we.getMessage(),we);
                //throw webex;
            } catch (Exception ex) {
                isItemTransferedSuccessfully = false;
                LN_TRANSFER_LOGGER.error("Exception Occured During Item Transfer! caused by: " + ex.getMessage());
                ex.printStackTrace();
                reTransferItem(context, itemValData, itemIdMap, isBeforeBOMTransfer, isBOMTransferSuccessful, bomMissingInLN, transferedItems);
                String defaultErrorMessage = LNResponseUtil.getDefaultErrorMessage(ex, "ITEM");
                transferResult = LNResponseUtil.getDefaultItemTransferResultDuringException(new Exception(defaultErrorMessage), itemValData);
                //throw ex;
            }
        } catch (Exception exception) {
            isItemTransferedSuccessfully = false;
            LN_TRANSFER_LOGGER.error("Exception Occured During Item Transfer! caused by: " + exception.getMessage());
            exception.printStackTrace();
            String defaultErrorMessage = LNResponseUtil.getDefaultErrorMessage(exception, "ITEM");
            transferResult = LNResponseUtil.getDefaultItemTransferResultDuringException(new Exception(defaultErrorMessage), itemValData);
        }

        ArrayList<String> successfullyTransferredItemList = new ArrayList<>();
        if (response != null) {
            transferResult = processResponse(response, itemIdMap, context, itemRevMap, transferedItems);
        }
        results = transferResult.getResults();

        if (results != null) {
            results.forEach(responseResult -> {
                LN_TRANSFER_LOGGER.info("Item Name: " + responseResult.getItem() + " Result: " + responseResult.getResultText());
                if (responseResult.isSuccessful() == true) {
                    successfullyTransferredItemList.add(responseResult.getItem());
                }
            });
        }

        LN_TRANSFER_LOGGER.info("\n" + "Successfully Transferred ItemList ::: " + successfullyTransferredItemList);
        return results;
    }

    private ArrayList<ResponseResult> bomTransfer(ArrayList bomValData) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, Exception {
        ArrayList<ResponseResult> results = null;
        ProcessItemBOMDataResponseType response = null;
        TransferResult transferResult = new TransferResult();
        List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL> bomValList = bomValData;
        Map<String, String> itemRevMap = new HashMap<>();
        bomValList.forEach(bomVal -> {
            itemRevMap.put(bomVal.getItemID().getID().toUpperCase(), bomVal.getItemID().getMainItemRevision());
            List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines> lines = bomVal.getLines();
            lines.forEach(line -> itemRevMap.put(line.getComponent().toUpperCase(), line.getComponentRevision()));
        }
        );
        LN_TRANSFER_LOGGER.info("\n" + ":::::: BOM TRANSFER :::::");
        try {
            try {
                String certificatePropertyValue = ApplicationProperties.getProprtyValue("LN.disable.certificate");
                LN_TRANSFER_LOGGER.info("Certificate disable for LN : " + certificatePropertyValue);
                if (certificatePropertyValue.equalsIgnoreCase("true")) {
                    DisableSSLCertificate.DisableCertificate();
                }
            } catch (KeyManagementException ex) {
                LN_TRANSFER_LOGGER.info(ex.getMessage());
                throw ex;
            } catch (NoSuchAlgorithmException ex) {
                LN_TRANSFER_LOGGER.info(ex.getMessage());
                throw ex;
            }
            LNHandlerResolver handeler = null;
            Integer compnay = Integer.parseInt(ApplicationProperties.getProprtyValue("ln.activationType.company"));
            handeler = new LNHandlerResolver();
            ActivationType activationType = new ActivationType();
            activationType.setCompany(compnay);
            activationType.setSender(ApplicationProperties.getProprtyValue("ln.activationType.sender"));
            LNSoapHandler soapHandler = new LNSoapHandler(activationType);
            handeler.addHandler(soapHandler);
            String bomWSDLUrl = ApplicationProperties.getProprtyValue("ln.bom.transfer.wsdl.url");
            LN_TRANSFER_LOGGER.info("bom WSDL Url :: " + bomWSDLUrl);
            URL url = null;
            try {
                url = new URL(bomWSDLUrl);
            } catch (MalformedURLException ex) {
                LN_TRANSFER_LOGGER.info(ex.getMessage());
                throw ex;
            }
            BillOfMaterialVALService service = new BillOfMaterialVALService(url);
            service.setHandlerResolver(handeler);
            com.infor.businessinterface.billofmaterial_val.BillOfMaterialVAL billOfMaterialService = service.getBillOfMaterialVALSoapPort();

            LNResponseUtil.setWebServiceTimeoutLimit((BindingProvider) billOfMaterialService);

            ProcessItemBOMDataRequestType.ControlArea controlArea = new ProcessItemBOMDataRequestType.ControlArea();
            controlArea.setProcessingScope(com.infor.businessinterface.billofmaterial_val.ProcessingScope.BUSINESS_ENTITY);

            ProcessItemBOMDataRequestType.DataArea dataArea = new ProcessItemBOMDataRequestType.DataArea();
            dataArea.setMessageID(ApplicationProperties.getProprtyValue("ln.data.area.bom.message.id"));
            dataArea.setReceiver(ApplicationProperties.getProprtyValue("ln.data.area.receiver"));
            dataArea.getBillOfMaterialVAL().addAll(bomValList);

            ProcessItemBOMDataRequestType processItemBOMDataRequest = new ProcessItemBOMDataRequestType();
            processItemBOMDataRequest.setControlArea(controlArea);
            processItemBOMDataRequest.setDataArea(dataArea);

            try {
                LN_TRANSFER_LOGGER.info("BOM Transfer Is In Progress..... Request Data ::: " + processItemBOMDataRequest);
                response = billOfMaterialService.processItemBOMData(processItemBOMDataRequest);
                //processItemBOMDataRequest.getDataArea().getBillOfMaterialVAL()
                LN_TRANSFER_LOGGER.info("BOM Transfer Is In Progress..... Request Data ::: " + processItemBOMDataRequest);
            } catch (com.infor.businessinterface.billofmaterial_val.Result ex) {
                isBOMTransferSuccessful = false;
                LN_TRANSFER_LOGGER.error("Is BOM transfer successful : " + isBOMTransferSuccessful);
                ex.printStackTrace();
                transferResult = LNResponseUtil.processErrorResult(ex, itemRevMap);
                LN_TRANSFER_LOGGER.error("com.infor.businessinterface.billofmaterial_val.Result Exception Occured! caused by " + ex.getMessage());
            } catch (SOAPFaultException e) {
                isBOMTransferSuccessful = false;
                LN_TRANSFER_LOGGER.error("Is BOM transfer successful : " + isBOMTransferSuccessful);
                e.printStackTrace();
                reTransferBOM(bomValData);
                transferResult = LNResponseUtil.getDefaultBOMTransferResultDuringException(e, bomValList);
                LN_TRANSFER_LOGGER.error("SOAPFaultException Occured! , caused by : " + e.getMessage());
            } catch (WebServiceException we) {
                isBOMTransferSuccessful = false;
                LN_TRANSFER_LOGGER.error("Is BOM transfer successful : " + isBOMTransferSuccessful);
                LN_TRANSFER_LOGGER.error("WebServiceException Occured! caused by: " + we.getMessage());
                we.printStackTrace();
                reTransferBOM(bomValData);
                transferResult = LNResponseUtil.getDefaultBOMTransferResultDuringException(we, bomValList);
                //WebServiceException webex = new WebServiceException(we.getMessage(),we);
                //throw webex;
            } catch (Exception ex) {
                isBOMTransferSuccessful = false;
                LN_TRANSFER_LOGGER.error("Is BOM transfer successful : " + isBOMTransferSuccessful);
                LN_TRANSFER_LOGGER.error("Exception Occured! , caused by : " + ex.getMessage());
                ex.printStackTrace();
                reTransferBOM(bomValData);
                String defaultErrorMessage = LNResponseUtil.getDefaultErrorMessage(ex, "BOM");
                transferResult = LNResponseUtil.getDefaultBOMTransferResultDuringException(new Exception(defaultErrorMessage), bomValList);
            }
        } catch (Exception exception) {
            isBOMTransferSuccessful = false;
            LN_TRANSFER_LOGGER.error("Is BOM transfer successful : " + isBOMTransferSuccessful);
            LN_TRANSFER_LOGGER.error("Exception Occured! , caused by : " + exception.getMessage());
            exception.printStackTrace();
            String defaultErrorMessage = LNResponseUtil.getDefaultErrorMessage(exception, "BOM");
            transferResult = LNResponseUtil.getDefaultBOMTransferResultDuringException(new Exception(defaultErrorMessage), bomValList);
        }
        if (response != null) {
            transferResult = processResponse(response, itemRevMap);//result...
            results = transferResult.getResults();
        } else {
            results = transferResult.getResults();
        }

        for (int i = 0; i < results.size(); i++) {
            ResponseResult responseResult = results.get(i);
            LN_TRANSFER_LOGGER.info("BOM Name: " + responseResult.getItem() + "  Result: " + responseResult.getResultText());
        }

        return results;
    }

    private TransferResult processResponse(ProcessItemDataResponseType response, LinkedHashMap<String, BusinessObject> itemIdMap, Context context, Map<String, String> itemRevMap, List<String> transferedItems) {
        List<ProcessItemDataResponseType.DataArea.ItemVAL> successfulItemValList = response.getDataArea().getItemVAL();
        ArrayList<ResponseResult> resultList = new ArrayList<>();
        for (ProcessItemDataResponseType.DataArea.ItemVAL successfulItemVal : successfulItemValList) {
            String itemName = successfulItemVal.getItemID().getID().trim();
            ResponseResult responseResult = new ResponseResult(itemName, "RESULT OK", true);
            resultList.add(responseResult);
            transferedItems.add(itemName);
        }

        if (response.getInformationArea() != null) {
            isItemTransferedSuccessfully = false;
            List<InformationMessage> failedItemMsgList = response.getInformationArea().getMessage();
            for (InformationMessage failedItemMsg : failedItemMsgList) {
                if (!msgToBeIgnored.contains(failedItemMsg.getMessageCode())) {
                    ResponseResult responseResult = LNResponseUtil.parseErrorMessage(failedItemMsg.getMessageText(), itemRevMap);
                    resultList.add(responseResult);
                }
            }
        }

        LinkedHashMap<ProcessItemDataResponseType.DataArea.ItemVAL.ItemID, BusinessObject> successfulItemIdMap = fetchSuccessfulItemIdMap(itemIdMap, successfulItemValList);
        for (Map.Entry<ProcessItemDataResponseType.DataArea.ItemVAL.ItemID, BusinessObject> entry : successfulItemIdMap.entrySet()) {
            BusinessObject itemObject = entry.getValue();
            try {
                itemObject.setAttributeValue(context, ApplicationProperties.getProprtyValue("source.att.send.to.erp"), Boolean.TRUE.toString());
            } catch (MatrixException ex) {
                LN_TRANSFER_LOGGER.error("MatrixException Occured! caused by: " + ex.getMessage());
            }
        }
        TransferResult tr = new TransferResult();
        tr.setResults(resultList);
        return tr;
    }

    private LinkedHashMap<ProcessItemDataResponseType.DataArea.ItemVAL.ItemID, BusinessObject> fetchSuccessfulItemIdMap(LinkedHashMap<String, BusinessObject> itemIdMap,
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
        }
        return successfulItemIdMap;
    }

    private TransferResult processResponse(ProcessItemBOMDataResponseType response, Map<String, String> itemRevMap) {
        ArrayList<ResponseResult> resultList = new ArrayList<>();
        List<ProcessItemBOMDataResponseType.DataArea.BillOfMaterialVAL> successfulBOMValList = response.getDataArea().getBillOfMaterialVAL();
        for (ProcessItemBOMDataResponseType.DataArea.BillOfMaterialVAL successfulBOM : successfulBOMValList) {
            String parentItemName = successfulBOM.getItemID().getID().trim();
            ResponseResult responseResult = new ResponseResult(parentItemName, "RESULT OK", true);
            resultList.add(responseResult);
        }
        if (response.getInformationArea() != null) {
            isBOMTransferSuccessful = false;
            List<com.infor.businessinterface.billofmaterial_val.InformationMessage> failedBOMMessageList = response.getInformationArea().getMessage();
            for (com.infor.businessinterface.billofmaterial_val.InformationMessage failedBOMMessage : failedBOMMessageList) {
                if (!msgToBeIgnored.contains(failedBOMMessage.getMessageCode())) {
                    ResponseResult responseResult = LNResponseUtil.parseErrorMessage(failedBOMMessage.getMessageText(), itemRevMap);
                    resultList.add(responseResult);
                }
            }
        }

        TransferResult tr = new TransferResult();
        tr.setResults(resultList);
        return tr;
    }

    private void reTransferItem(Context context, ArrayList itemValData,
            LinkedHashMap<String, BusinessObject> itemIdMap,
            boolean isBeforeBOMTransfer, boolean isBOMTransferSuccessful,
            boolean bomMissingInLN, List<String> transferedItems) throws Exception {

        this.transferRetryCount++;
        if (this.transferRetryCount <= transferRetryNumber) {
            LN_TRANSFER_LOGGER.error("Item transfer failed. Going to re-transfer. Re-try count: " + this.transferRetryCount);
            itemTransfer(context, itemValData, itemIdMap, isBeforeBOMTransfer, isBOMTransferSuccessful, bomMissingInLN, transferedItems);
        } else if (this.transferRetryCount > transferRetryCount && transferRetryNumber != 0) {
            // Will send mail to AMS and user
            ResponseResultSender.recipients.add(ApplicationProperties.getProprtyValue("ams.mail.recipient"));
        }
    }

    private void reTransferBOM(ArrayList bomValData) throws Exception {
        this.transferRetryCount++;
        if (this.transferRetryCount <= transferRetryNumber) {
            LN_TRANSFER_LOGGER.error("BOM transfer failed. Going to re-transfer. Re-try count: " + this.transferRetryCount);
            bomTransfer(bomValData);
        } else if (this.transferRetryCount > transferRetryCount && transferRetryNumber != 0) {
            // Will send mail to AMS and user
            ResponseResultSender.recipients.add(ApplicationProperties.getProprtyValue("ams.mail.recipient"));
        }
    }
}
