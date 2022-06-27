package com.bjit.ex.integration.transfer.service.impl.LN;

import com.bjit.context.DisableSSLCertificate;
import com.bjit.ex.integration.transfer.inputparam.TransferInputParams;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.service.ITransfer;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.ws.soap.LNHandlerResolver;
import com.bjit.ex.integration.transfer.ws.soap.LNSoapHandler;
import com.infor.businessinterface.billofmaterial_val.BillOfMaterialVALService;
import com.infor.businessinterface.billofmaterial_val.ProcessItemBOMDataRequestType;
import com.infor.businessinterface.billofmaterial_val.ProcessItemBOMDataResponseType;
import com.infor.businessinterface.item_val.ActivationType;
import matrix.db.BusinessObject;
import matrix.db.Context;
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

public class LNBOMTransfer implements ITransfer {
    private static final Logger LN_BOM_TRANSFER_LOGGER = Logger.getLogger(LNBOMTransfer.class);
    private List<String> transferedItems = new ArrayList<>();

    @Override
    public Object transfer(TransferInputParams transferObj,boolean isService) throws Exception {
        ArrayList bomValData = (ArrayList) transferObj.getTrasnferObjMap().get("bomValList");
        LinkedHashMap<String, BusinessObject> itemIdMap = transferObj.getItemIdMap();
        Context context = transferObj.getContext();
        ArrayList<ResponseResult> results = new ArrayList<ResponseResult>();

        long bomTransferStartTime = System.currentTimeMillis();
        if (bomValData != null) {
            if (bomValData.size() > 0) {
                try {
                    List<ResponseResult> bomTransferResponseResults = bomTransfer(bomValData);
                    if (bomTransferResponseResults != null && bomTransferResponseResults.size() > 0) {
                        results.addAll(bomTransferResponseResults);
                    }

                } catch (NoSuchAlgorithmException ex) {
                    LN_BOM_TRANSFER_LOGGER.error("BOM Transfer Error : " + ex.getMessage());
                    throw ex;
                } catch (MalformedURLException ex) {
                    LN_BOM_TRANSFER_LOGGER.error("BOM Transfer Error : " + ex.getMessage());
                    throw ex;
                } catch (Exception ex) {
                    LN_BOM_TRANSFER_LOGGER.error("BOM Transfer Error : " + ex.getMessage());
                    throw ex;
                }
            } else {
                LN_BOM_TRANSFER_LOGGER.info("Standalone Item : There is no BOM!! ");
            }

        } else {
            LN_BOM_TRANSFER_LOGGER.info("Error in item sending or there is no BOM data.");
        }
        long bomTransferEndTime = System.currentTimeMillis();
        LN_BOM_TRANSFER_LOGGER.info("Elapsed Time : BOM Transfer Time " + (bomTransferEndTime - bomTransferStartTime));

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
        LN_BOM_TRANSFER_LOGGER.info("\n" + ":::::: BOM TRANSFER :::::");
        try {
            try {
                String certificatePropertyValue = ApplicationProperties.getProprtyValue("LN.disable.certificate");
                LN_BOM_TRANSFER_LOGGER.info("Certificate disable for LN : "+ certificatePropertyValue);
                if (certificatePropertyValue.equalsIgnoreCase("true")){
                    DisableSSLCertificate.DisableCertificate();
                }
            } catch (KeyManagementException ex) {
                LN_BOM_TRANSFER_LOGGER.info(ex.getMessage());
                throw ex;
            } catch (NoSuchAlgorithmException ex) {
                LN_BOM_TRANSFER_LOGGER.info(ex.getMessage());
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
            LN_BOM_TRANSFER_LOGGER.info("BOM Transfer WSDL Url :: "+bomWSDLUrl);
            URL url = null;
            try {
                url = new URL(bomWSDLUrl);
            } catch (MalformedURLException ex) {
                LN_BOM_TRANSFER_LOGGER.info(ex.getMessage());
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
                LN_BOM_TRANSFER_LOGGER.info("BOM Transfer Is In Progress : Request Data : " + processItemBOMDataRequest);
                response = billOfMaterialService.processItemBOMData(processItemBOMDataRequest);
                LN_BOM_TRANSFER_LOGGER.info("BOM Transfer End : Response Data : " + response);
                //processItemBOMDataRequest.getDataArea().getBillOfMaterialVAL()
            } catch (SOAPFaultException e) {
                LN_BOM_TRANSFER_LOGGER.error("BOM Transfer Error : " + e.getMessage());
                e.printStackTrace();
                transferResult = LNResponseUtil.getDefaultBOMTransferResultDuringException(e, bomValList);
            } catch (com.infor.businessinterface.billofmaterial_val.Result ex) {
                LN_BOM_TRANSFER_LOGGER.error("BOM Transfer Error : " + ex.getMessage());
                ex.printStackTrace();
                transferResult = LNResponseUtil.processErrorResult(ex, itemRevMap);
            } catch (WebServiceException we) {
                LN_BOM_TRANSFER_LOGGER.error("BOM Transfer Error : " + we.getMessage());
                we.printStackTrace();
                transferResult = LNResponseUtil.getDefaultBOMTransferResultDuringException(we, bomValList);
                //WebServiceException webex = new WebServiceException(we.getMessage(),we);
                //throw webex;
            } catch (Exception ex) {
                LN_BOM_TRANSFER_LOGGER.error("BOM Transfer Error : " + ex.getMessage());
                ex.printStackTrace();
                String defaultErrorMessage = LNResponseUtil.getDefaultErrorMessage(ex,"BOM");
                transferResult = LNResponseUtil.getDefaultBOMTransferResultDuringException(new Exception(defaultErrorMessage), bomValList);
            }
        } catch (Exception exception) {
            LN_BOM_TRANSFER_LOGGER.error("BOM Transfer Error : " + exception.getMessage());
            exception.printStackTrace();
            String defaultErrorMessage = LNResponseUtil.getDefaultErrorMessage(exception,"BOM");
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
            if (responseResult != null) {
                LN_BOM_TRANSFER_LOGGER.info("BOM Name: " + responseResult.getItem() + "  Result: " + responseResult.getResultText());
            }
        }
        return results;
    }

    private TransferResult processResponse(ProcessItemBOMDataResponseType response, Map<String,String> itemRevMap) {
        ArrayList<ResponseResult> resultList = new ArrayList<>();
        List<ProcessItemBOMDataResponseType.DataArea.BillOfMaterialVAL> successfulBOMValList = response.getDataArea().getBillOfMaterialVAL();
        for (ProcessItemBOMDataResponseType.DataArea.BillOfMaterialVAL successfulBOM : successfulBOMValList) {
            String parentItemName = successfulBOM.getItemID().getID().trim();
            ResponseResult responseResult = new ResponseResult(parentItemName, "RESULT OK", true);
            resultList.add(responseResult);
        }
        if (response.getInformationArea() != null) {
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

    @Override
    public Object transfer(TransferInputParams transferObj, boolean isService, List<String> transferedItems) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
