/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service.impl.LN;

import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.infor.businessinterface.billofmaterial_val.ProcessItemBOMDataRequestType;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType;
import com.infor.businessinterface.projectdeliverable_val.CreateProjectDeliverableRequestType;

import java.util.*;
import java.util.stream.Collectors;
import javax.xml.ws.BindingProvider;

import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.util.MatrixException;
import org.apache.log4j.Logger;

/**
 *
 * @author Sajjad
 */
public class LNResponseUtil {
	private static final Logger RESPONSE_UTIL_LOGGER = Logger.getLogger(LNResponseUtil.class);
    public static final String[] listOfIgnoredMsgCode = new String[]{"tlbctsb0011", "tlbctstgen"};
    public static final Set<String> msgToBeIgnored = new HashSet<String>(Arrays.asList(listOfIgnoredMsgCode));

    public static void setWebServiceTimeoutLimit(BindingProvider bindingProvider) {
        int timeoutInMiliSec = Integer.parseInt(ApplicationProperties.getProprtyValue("LN.response.timeout.limit"));
        //Setting Request timeout
        bindingProvider.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", timeoutInMiliSec);
        bindingProvider.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", timeoutInMiliSec);
    }
    
    public static String getDefaultErrorMessage(Exception exception, String transferType) {
    	String defaultMsg = transferType.equalsIgnoreCase("ITEM")? 
    			ApplicationProperties.getProprtyValue("LN.item.sending.fail.default.message")
    			: ApplicationProperties.getProprtyValue("LN.bom.sending.fail.default.message");
    	StringBuilder responseMsg = new StringBuilder(defaultMsg);
    	responseMsg.append(" ").append(ApplicationProperties.getProprtyValue("LN.erp"));
    	responseMsg.append(".\n").append("Getting error while processing the request due to ");
    	try {
    		if (exception.getMessage() != null) {
        		String exceptionMsg = exception.getMessage();
        		int i = exceptionMsg.indexOf("response code");
        		if (i !=-1) {
        			String subStr = exceptionMsg.substring(i, exceptionMsg.length()-1);
                    String[] strArr= subStr.split(" ");
                    String errorCode = strArr[2];
                    responseMsg.append("server returned error code ").append(errorCode);
        		} else if(exceptionMsg.contains("Failed to access the WSDL")){
        		    responseMsg.append("failure of accessing LN service");
                }
        		else {
        			responseMsg.append("failure of processing the request.");
        		}
        	} else {
        		responseMsg.append("Empty or null response.");
        	}
    	} catch (Exception ex) {
    		RESPONSE_UTIL_LOGGER.error(ex);
		}
    	return responseMsg.toString();
    }
    
    public static ResponseResult parseErrorMessage(String messageText, Map<String,String> itemRevMap) {

        if (messageText.contains(":")) {
            String[] splitedMessage = messageText.split(":", 2);
            String possibleItemId = splitedMessage[0].trim();
            if (possibleItemId.length() < 50 /*&& Pattern.matches("[a-zA-Z0-9]+",possibleItemId)*/) {
                if (possibleItemId.contains("/")){
                    String childItemName = "";
                    String childItemRev = "";
                    String[] parentToBOMToken = possibleItemId.split("/");
                    if (parentToBOMToken.length == 3){
                        childItemName = parentToBOMToken[2].toUpperCase();
                        childItemRev = itemRevMap.containsKey(childItemName)? itemRevMap.get(childItemName) : "";
                        if (childItemName != null && !"".equals(childItemName)){
                            return new ResponseResult(childItemName, childItemRev, splitedMessage[1].trim(), false);
                        }
                    }
                }
                String revision = itemRevMap.containsKey(possibleItemId) ? itemRevMap.get(possibleItemId):"";
                return new ResponseResult(possibleItemId, revision, splitedMessage[1].trim(), false);
            }

            if (possibleItemId.contains("/") /*&& Pattern.matches("[a-zA-Z0-9]+",possibleItemId)*/) {
                String possibleBomParentId = possibleItemId.split("/")[0].trim();
                if (possibleBomParentId.length() < 50) {
                    String revision = itemRevMap.containsKey(possibleItemId) ? itemRevMap.get(possibleItemId):"";
                    return new ResponseResult(possibleBomParentId, revision, messageText.trim(), false);
                }
            }
        }
        return null;
    }

    public static ResponseResult parseErrorMessage(String messageText) {

        if (messageText.contains(":")) {
            String[] splitedMessage = messageText.split(":", 2);
            String possibleItemId = splitedMessage[0].trim();
            if (possibleItemId.length() < 50 /*&& Pattern.matches("[a-zA-Z0-9]+",possibleItemId)*/) {
                return new ResponseResult(possibleItemId, splitedMessage[1].trim(), false);
            }

            if (possibleItemId.contains("/") /*&& Pattern.matches("[a-zA-Z0-9]+",possibleItemId)*/) {
                String possibleBomParentId = possibleItemId.split("/")[0].trim();
                if (possibleBomParentId.length() < 50) {
                    return new ResponseResult(possibleBomParentId, splitedMessage[1].trim(), false);
                }
            }
        }
        return null;
    }

    public static TransferResult processErrorResult(com.infor.businessinterface.item_val.Result e, Map<String,String> itemRevMap) {
        TransferResult transferResult = new TransferResult();
        String faultInfo = e.getFaultInfo().getMessageText();
        //LN_NIGHTLY_TRANSFER_LOGGER.error(e.getMessage());

        if (faultInfo.equalsIgnoreCase("Multiple messages are reported")) {
            List<com.infor.businessinterface.item_val.DetailMessage> failItemMessageList = e.getFaultInfo().getMessageDetails().getMessage();
            for (com.infor.businessinterface.item_val.Message failedtemMessage : failItemMessageList) {
                if (!msgToBeIgnored.contains(failedtemMessage.getMessageCode())) {
                    String messageText = failedtemMessage.getMessageText();
                    ResponseResult responseResult = LNResponseUtil.parseErrorMessage(messageText, itemRevMap);
                    if (responseResult != null) {
                        transferResult.addNewResult(responseResult);
                    } else {
                        String item = null;
                        responseResult = new ResponseResult(item, messageText, false);
                        transferResult.addNewResult(responseResult);
                    }
                }
            }
        } else {
            transferResult.addNewResult(LNResponseUtil.parseErrorMessage(faultInfo, itemRevMap));
        }
        return transferResult;
    }

    public static TransferResult processErrorResult(com.infor.businessinterface.billofmaterial_val.Result e, Map<String, String> itemRevMap) {
        TransferResult responseResult = new TransferResult();
        String faultInfo = e.getFaultInfo().getMessageText();
        if (faultInfo.equalsIgnoreCase("Multiple messages are reported")) {
            List<com.infor.businessinterface.billofmaterial_val.DetailMessage> failBOMMessageList = e.getFaultInfo().getMessageDetails().getMessage();
            for (com.infor.businessinterface.billofmaterial_val.Message failedtemMessage : failBOMMessageList) {
                if (!msgToBeIgnored.contains(failedtemMessage.getMessageCode())) {
                    responseResult.addNewResult(parseErrorMessage(failedtemMessage.getMessageText(), itemRevMap));
                }
            }
        } else {
            responseResult.addNewResult(parseErrorMessage(faultInfo, itemRevMap));
        }

        return responseResult;
    }

    // Error Message Processing for Project Deliverable
    public static TransferResult processErrorResult(com.infor.businessinterface.projectdeliverable_val.Result e){
        TransferResult transferResult=new TransferResult();
        String faultInfo=e.getFaultInfo().getMessageText();
        //LN_DELIVERABLE_TRANSFER_LOGGER.error(e.getMessage());
        
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
    }

    public static TransferResult getDefaultItemTransferResultDuringException(Exception e, List<ProcessItemDataRequestType.DataArea.ItemVAL> itemVALList) {
        TransferResult transferResult = new TransferResult();
        for (ProcessItemDataRequestType.DataArea.ItemVAL itemVAL : itemVALList) {
            String id = itemVAL.getItemID().getID();
            String rev = itemVAL.getRevision();
            ResponseResult responseResult = new ResponseResult(id, e.getMessage(), false);
            responseResult.setRevision(rev);
            transferResult.addNewResult(responseResult);
        }
        return transferResult;
    }

    public static TransferResult getDefaultBOMTransferResultDuringException(Exception e, List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL> bomValList) {
        TransferResult transferResult = new TransferResult();
        for (ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL bomVAL : bomValList) {
            String id = bomVAL.getItemID().getID();
            String rev = bomVAL.getItemID().getMainItemRevision();
            ResponseResult responseResult = new ResponseResult(id, e.getMessage(), false);
            responseResult.setRevision(rev);
            transferResult.addNewResult(responseResult);
        }
        return transferResult;
    }

    public static TransferResult getDefaultProjectDeliverableTransferResultDuringException(Exception e, CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL projectDeliverableVal) {
        TransferResult transferResult = new TransferResult();
        String id = projectDeliverableVal.getItemID().getID();
        ResponseResult responseResult = new ResponseResult(id, e.getMessage(), false);
        transferResult.addNewResult(responseResult);
        return transferResult;
    }

    // builds and returns the response results for unspecified item name in the ln error response
    public static List<ResponseResult> getUnspecifiedItemsResponseResults(Context context, Map<String, BusinessObject> itemNameBusMap, List<ResponseResult> lnResponseResult) throws MatrixException {
        Set<String> itemRevName = itemNameBusMap.keySet();
        List<String> itemNames = new ArrayList<>();
        List<ResponseResult> unspecifiedResponseResult = new ArrayList<>();
        List<String> specifiedItemNamesInLNResponse = new ArrayList<>();
        Map<String, BusinessObject> filteredItemNameBusMap = new HashMap<>();

        itemNameBusMap.keySet().forEach(itemName -> {
            String[] itemRevArr = itemName.split("_");
            String splittedItem = itemRevArr[0];
            itemNames.add(splittedItem.toLowerCase());
            filteredItemNameBusMap.put(splittedItem.toLowerCase(), itemNameBusMap.get(itemName));
        });

        lnResponseResult.forEach(responseResult -> {
            if (responseResult != null && responseResult.getItem() != null){
                specifiedItemNamesInLNResponse.add(responseResult.getItem().toLowerCase());
            }
        });


        if (itemRevName.size() == 1 && specifiedItemNamesInLNResponse.size() == 0){
            String itemName = itemRevName.stream().findFirst().get();
            BusinessObject businessObject = itemNameBusMap.get(itemName);
            ResponseResult responseResult = lnResponseResult.stream().findFirst().get();
            unspecifiedResponseResult.add(new ResponseResult(businessObject.getName(context).toUpperCase(),businessObject.getRevision(),responseResult.getResultText(),false));
            return unspecifiedResponseResult;
        }

        itemNames.removeAll(specifiedItemNamesInLNResponse);


         if (itemNames.size() > 0){
            unspecifiedResponseResult = itemNames.stream().
                    map(itemName -> {
                        BusinessObject businessObject = filteredItemNameBusMap.get(itemName);
                        try {
                            return new ResponseResult(businessObject.getName(context).toUpperCase(), businessObject.getRevision(), "Unspecified error occurred while transferring item to LN", false);
                        } catch (MatrixException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        }
        return unspecifiedResponseResult;
    }
}
