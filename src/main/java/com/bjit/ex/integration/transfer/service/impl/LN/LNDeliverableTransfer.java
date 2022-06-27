/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service.impl.LN;

import com.bjit.context.DisableSSLCertificate;
import com.bjit.ex.integration.transfer.inputparam.TransferInputParams;
import com.bjit.ex.integration.transfer.service.ITransfer;
import static com.bjit.ex.integration.transfer.service.impl.LN.LNResponseUtil.msgToBeIgnored;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.ws.soap.LNHandlerResolver;
import com.bjit.ex.integration.transfer.ws.soap.LNSoapHandler;
import com.infor.businessinterface.item_val.ActivationType;
import com.infor.businessinterface.projectdeliverable_val.CreateProjectDeliverableRequestType;
import com.infor.businessinterface.projectdeliverable_val.CreateProjectDeliverableResponseType;
import com.infor.businessinterface.projectdeliverable_val.ProjectDeliverableVALService;
import com.infor.businessinterface.projectdeliverable_val.Result;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class LNDeliverableTransfer implements ITransfer{
    private static final Logger LN_DELIVERABLE_TRANSFER_LOGGER = Logger.getLogger(LNDeliverableTransfer.class);
    @Override
    public Object transfer(TransferInputParams transferObj,boolean isService) throws Exception {
        String transferType = transferObj.getTransferType();

        CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL deliverableVAL = (CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL) transferObj.getTrasnferObjMap().get("deliverableVAL");
        BusinessObject deliverableBusinessObject = (BusinessObject) transferObj.getTrasnferObjMap().get("deliverableBusinessObject");
        Context context = transferObj.getContext();
        TransferResult transferResult = sentProjectDeliverableToLn(context, deliverableVAL, deliverableBusinessObject);
        LN_DELIVERABLE_TRANSFER_LOGGER.debug("Deliverable Transfer Result List ::: "+transferResult.getResults());
        return transferResult.getResults();
    }
      /*public TransferResult sentProjectDeliverableToLn(List<CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL> projectDeliverableValList, com.infor.businessinterface.projectdeliverable_val.ProcessingScope projectValProcessingScope) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException*/
    
    public TransferResult sentProjectDeliverableToLn(Context context, CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL projectDeliverableVal, BusinessObject deliverableBusinessObject) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException {
        com.infor.businessinterface.projectdeliverable_val.ProcessingScope projectValProcessingScope = com.infor.businessinterface.projectdeliverable_val.ProcessingScope.BUSINESS_ENTITY;
        TransferResult transferResult = new TransferResult();
        LN_DELIVERABLE_TRANSFER_LOGGER.info("\n" + ":::::: PROJECT DELIVERABLE TRANSFER :::::");
        try {
            DisableSSLCertificate.DisableCertificate();
        } catch (KeyManagementException ex) {
            LN_DELIVERABLE_TRANSFER_LOGGER.info(ex.getMessage());
            throw ex;
        } catch (NoSuchAlgorithmException ex) {
            LN_DELIVERABLE_TRANSFER_LOGGER.info(ex.getMessage());
            throw ex;
        }
        try {
            LNHandlerResolver handeler = null;
            Integer compnay = Integer.parseInt(ApplicationProperties.getProprtyValue("ln.activationType.company"));
            handeler = new LNHandlerResolver();
            ActivationType activationType = new ActivationType();
            activationType.setCompany(compnay);
            activationType.setSender(ApplicationProperties.getProprtyValue("ln.activationType.sender"));
            
            LNSoapHandler soapHandler = new LNSoapHandler(activationType);
            handeler.addHandler(soapHandler);

            URL url = new URL(ApplicationProperties.getProprtyValue("ln.project.deliverable.tranfer.wsdl.url"));
            ProjectDeliverableVALService service = new ProjectDeliverableVALService(url);

            service.setHandlerResolver(handeler);
            com.infor.businessinterface.projectdeliverable_val.ProjectDeliverableVAL projectDeliveryService = service.getProjectDeliverableVALSoapPort();

            CreateProjectDeliverableRequestType createProjectDeliverableRequest = new CreateProjectDeliverableRequestType();
            CreateProjectDeliverableRequestType.ControlArea controlArea = new CreateProjectDeliverableRequestType.ControlArea();
            controlArea.setProcessingScope(projectValProcessingScope);
            createProjectDeliverableRequest.setControlArea(controlArea);

            CreateProjectDeliverableRequestType.DataArea dataArea = new CreateProjectDeliverableRequestType.DataArea();
            dataArea.setMessageID(ApplicationProperties.getProprtyValue("ln.data.area.project.deliverable.message.id"));
            dataArea.setReceiver(ApplicationProperties.getProprtyValue("ln.data.area.receiver"));
            createProjectDeliverableRequest.setDataArea(dataArea);

            //dataArea.getProjectDeliverableVAL().addAll(projectDeliverableValList);
            dataArea.getProjectDeliverableVAL().add(projectDeliverableVal);
            
            String revision = projectDeliverableVal.getRevision();
            Map<String,Object> itemInfoDetails = new LinkedHashMap<>();
            itemInfoDetails.put("revision", revision);
            //ProjectDeliverableResponseResultSenderImpl.itemInfoMap.put(projectDeliverableVal.getItemID().getID().toUpperCase(), itemInfoDetails);

            CreateProjectDeliverableResponseType response = projectDeliveryService.createProjectDeliverable(createProjectDeliverableRequest);
            System.out.println("response ::: "+response);
            transferResult = processResponse(response, context, deliverableBusinessObject);
            
        }  catch (Result ex) {
            LN_DELIVERABLE_TRANSFER_LOGGER.error("com.infor.businessinterface.projectdeliverable_val.Result Exception Occured! caused by: " + ex.getMessage());
            transferResult = LNResponseUtil.processErrorResult(ex);
            
        } catch (SOAPFaultException sfe) {
            LN_DELIVERABLE_TRANSFER_LOGGER.error("SOAPFaultException Occured! caused by: " + sfe.getMessage());
            transferResult = LNResponseUtil.getDefaultProjectDeliverableTransferResultDuringException(sfe,projectDeliverableVal);
            
        } catch (WebServiceException we) {
            LN_DELIVERABLE_TRANSFER_LOGGER.error("WebServiceException Occured During Project Deliverable Transfer! caused by: " + we.getMessage());
            transferResult = LNResponseUtil.getDefaultProjectDeliverableTransferResultDuringException(we,projectDeliverableVal);
            
        } catch (NumberFormatException | MalformedURLException e) {
            LN_DELIVERABLE_TRANSFER_LOGGER.error("Exception Occured During Project Deliverable Transfer! caused by: " + e.getMessage());
            transferResult = LNResponseUtil.getDefaultProjectDeliverableTransferResultDuringException(e,projectDeliverableVal);
        }
        
//        ResponseResultSender resultSender = new ProjectDeliverableResponseResultSenderImpl();
//        resultSender.send(transferResult.getResults());
        
        return transferResult;
    }

    private TransferResult processResponse(CreateProjectDeliverableResponseType response, Context context, BusinessObject deliverableBusinessObject) {
        ArrayList<ResponseResult> resultList = new ArrayList<>();
        TransferResult tr = new TransferResult();
        if (response != null) {
            List<CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL> successfulProjectDeliverableVALList = response.getDataArea().getProjectDeliverableVAL();
            for (CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL projectDeliverableVAL : successfulProjectDeliverableVALList) {
                try {
                    String itemName = projectDeliverableVAL.getItemID().getID().trim();
                    String contractDeliverable = projectDeliverableVAL.getContractDeliverable();
                    deliverableBusinessObject.setAttributeValue(context, ApplicationProperties.getProprtyValue("source.att.contract.deliberable"), contractDeliverable);
                    ResponseResult responseResult = new ResponseResult(itemName, "RESULT OK", true);
                    resultList.add(responseResult);
                } catch (MatrixException ex) {                    
                    LN_DELIVERABLE_TRANSFER_LOGGER.error(ex.getMessages());
                }
            }

            if (response.getInformationArea() != null) {
                System.out.println("<------ Some Error Respose found in ITEM,  parsing..... ------>");
                List<com.infor.businessinterface.projectdeliverable_val.InformationMessage> failedProjectdeliverableMessageList = response.getInformationArea().getMessage();

                for (com.infor.businessinterface.projectdeliverable_val.Message failedProjectdeliverableMessage : failedProjectdeliverableMessageList) {
                    if (!msgToBeIgnored.contains(failedProjectdeliverableMessage.getMessageCode())) {
                        ResponseResult responseResult = LNResponseUtil.parseErrorMessage(failedProjectdeliverableMessage.getMessageText());
                        resultList.add(responseResult);
                    }
                }
            }
            tr.setResults(resultList);
        }
        return tr;
    }
//    private ResponseResult parseErrorMessage(String messageText) {
//
//        if (messageText.contains(":")) {
//            String[] splitedMessage = messageText.split(":", 2);
//            String possibleItemId = splitedMessage[0].trim();
//            if (possibleItemId.length() < 50 /*&& Pattern.matches("[a-zA-Z0-9]+",possibleItemId)*/) {
//                return new ResponseResult(possibleItemId, splitedMessage[1].trim(), false);
//            }
//
//            if (possibleItemId.contains("/") /*&& Pattern.matches("[a-zA-Z0-9]+",possibleItemId)*/) {
//                String possibleBomParentId = possibleItemId.split("/")[0].trim();
//                if (possibleBomParentId.length() < 50) {
//                    return new ResponseResult(possibleBomParentId, splitedMessage[1].trim(), false);
//                }
//            }
//        }
//        return null;
//    }
    /*private TransferResult getDefaultProjectDeliverableTransferResultDuringException(Exception e, CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL projectDeliverableVal) {
        TransferResult transferResult = new TransferResult();
        String id = projectDeliverableVal.getItemID().getID();
        ResponseResult responseResult = new ResponseResult(id, e.getMessage(), false);
        transferResult.addNewResult(responseResult);
        return transferResult;
    }*/
    /*private TransferResult processErrorResult(Result e){
        TransferResult transferResult=new TransferResult();
        String faultInfo=e.getFaultInfo().getMessageText();
        LN_DELIVERABLE_TRANSFER_LOGGER.error(e.getMessage());
        
        if(faultInfo.equalsIgnoreCase("Multiple messages are reported")){
                List<com.infor.businessinterface.projectdeliverable_val.DetailMessage> failItemMessageList =  e.getFaultInfo().getMessageDetails().getMessage();
                for(com.infor.businessinterface.projectdeliverable_val.Message failedtemMessage : failItemMessageList){
                    if(!msgToBeIgnored.contains(failedtemMessage.getMessageCode())){
                        String messageText = failedtemMessage.getMessageText();
                        ResponseResult responseResult = LNResponseUtil.parseErrorMessage(messageText);
                        if (responseResult!=null) {
                            transferResult.addNewResult(responseResult);
                        }else{
                            String item = null;
                            responseResult = new ResponseResult(item, messageText, false);
                            transferResult.addNewResult(responseResult);
                        }
                        
                    }                                
                }
        } else {
                transferResult.addNewResult(LNResponseUtil.parseErrorMessage(faultInfo));
        }
        return transferResult;
    }*/

    @Override
    public Object transfer(TransferInputParams transferObj, boolean isService, List<String> transferedItems) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
