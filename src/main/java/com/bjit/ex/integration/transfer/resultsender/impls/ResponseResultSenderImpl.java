package com.bjit.ex.integration.transfer.resultsender.impls;

import com.bjit.ex.integration.model.webservice.Item;
import com.bjit.ex.integration.transfer.actions.utilities.BusinessObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.util.ResultSenderUtil;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.FileDirProcess;
import com.bjit.ex.integration.transfer.util.ItemInfo;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.db.Signature;
import matrix.db.SignatureList;
import matrix.util.MatrixException;

import java.util.*;

public class ResponseResultSenderImpl extends ResponseResultSender {

    private static final org.apache.log4j.Logger TRANSFER_RESPONSE_RESULT_SENDER_LOGGER = org.apache.log4j.Logger.getLogger(ResponseResultSenderImpl.class);

    private ResultSenderUtil emailBodyBuilder = new ResultSenderUtil();

    @Override
    public void initializeResultSender(Context context, BusinessObject businessObject) {
        try {
            emailIsNecessary = false;
            //itemInfoMapDetails = new LinkedHashMap<String, List<Object>>();
            //successfulValItemResponseResult = new ArrayList<ResponseResult>();
            isAllItemTransfered = true;
            //rootItemName = businessObject.getName();
            //rootItemRevision = businessObject.getRevision();

            SignatureList signatures = businessObject.getSignatures(context, FileDirProcess.itemCurrentStatus, FileDirProcess.itemNextStatus);
            String promoteUserName = "";
            Signature promoteSignature = null;
            if (signatures != null && signatures.size() >= 1) {
                promoteSignature = signatures.get(signatures.size() - 1);
            }
            promoteUserName = promoteSignature.getSigner();
            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info("Promote User Name: " + promoteUserName);
            if ("".equalsIgnoreCase(promoteUserName) || promoteUserName == null) {
                TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("No promote user found. Error mail will be sent to AMS.");
                recipients.add(ApplicationProperties.getProprtyValue("ams.mail.recipient"));
            } else {
                BusinessObjectUtils businessObjectUtil = new BusinessObjectUtils();
                if (promoteUserName.equalsIgnoreCase("User Agent")) {
                    String changeActionId = BusinessObjectUtils.getItemAttributeValue(context, businessObject.getObjectId(), ApplicationProperties.getProprtyValue("item.att.change.id"));
                    //String changeActionId = businessObject.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.att.change.id")).getValue();
                    System.out.println("changeActionId ::: " + changeActionId);
                    String reviewerName = "";
                    ArrayList<BusinessObject> reviewerBOList = new ArrayList<>();
                    if (changeActionId == null || changeActionId.isEmpty()) {
                        try {
                            BusinessObject changeActionBO = businessObjectUtil.getChangeActionFromItem(context, businessObject);
                            setProjectTaskAndChangeActionName(context, changeActionBO);
                            reviewerBOList = businessObjectUtil.getChangeActionReviewersFromChangeAction(context, changeActionBO);
                        } catch (MatrixException e) {
                            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("Error occurred while fetching reviewer from Change Action. " + e.getMessage());
                            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("No promote user found. Error mail will be sent to AMS.");
                            recipients.add(ApplicationProperties.getProprtyValue("ams.mail.recipient"));
                        }
                    } else {
                        BusinessObject changeActionBO = new BusinessObject(changeActionId);
                        try {
                            setProjectTaskAndChangeActionName(context, changeActionBO);
                            reviewerBOList = businessObjectUtil.getChangeActionReviewersFromChangeAction(context, changeActionBO);
                        } catch (MatrixException e) {
                            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error(e);
                            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("No promote user found. Error mail will be sent to AMS.");
                            recipients.add(ApplicationProperties.getProprtyValue("ams.mail.recipient"));
                        }
                    }
                    if (!reviewerBOList.isEmpty()) {
                        for (BusinessObject reviewerBO : reviewerBOList) {
                            reviewerBO.open(context);
                            reviewerName = reviewerBO.getName();
                            recipients.addAll(BusinessObjectUtils.getRecipientsByOwnerName(context, reviewerName));
                            reviewerBO.close(context);
                        }
                    } else {
                        TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("No promote user found. Error mail will be sent to AMS.");
                        recipients.add(ApplicationProperties.getProprtyValue("ams.mail.recipient"));
                    }

                } else {
                    recipients = BusinessObjectUtils.getRecipientsByOwnerName(context, promoteUserName);
                }
            }

            emailSender = ApplicationProperties.getProprtyValue("ln.transfer.error.message.sender.email");

            emailSubject = ApplicationProperties.getProprtyValue("ln.transfer.successful.email.subject");
            enoviaToLnTransferSuccessfulMessageHeader = ApplicationProperties.getProprtyValue("ln.transfer.successful.message.header");
            enoviaToLnTransferErrorMessageHeader = ApplicationProperties.getProprtyValue("ln.transfer.error.message.header");
            enoviaToLnTransferUnrecognizedMessageHeader = ApplicationProperties.getProprtyValue("ln.transfer.unrecognized.message.header");

            try {
                title = businessObject.getDescription(context);
            } catch (MatrixException ex) {
                TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error(ex.getMessage());
            }

        } catch (MatrixException ex) {
            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void initializeResultSender() {
        isAllItemTransfered = true;
        emailSender = ApplicationProperties.getProprtyValue("ln.transfer.error.message.sender.email");
        emailSubject = ApplicationProperties.getProprtyValue("ln.transfer.successful.email.subject");
        enoviaToLnTransferSuccessfulMessageHeader = ApplicationProperties.getProprtyValue("ln.transfer.successful.message.header");
        enoviaToLnTransferErrorMessageHeader = ApplicationProperties.getProprtyValue("ln.transfer.error.message.header");
        enoviaToLnTransferUnrecognizedMessageHeader = ApplicationProperties.getProprtyValue("ln.transfer.unrecognized.message.header");
    }

    @Override
    public void initializeResultSender(Context context, BusinessObject businessObject, Item item,boolean isService) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(List<ResponseResult> results) {
        StringBuilder emailBody = emailBodyBuilder.getEmailBody(results, SenderType.LN_TRANSFER_RESPONSE_SENDER);
        TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info("Prepared Email Body: \n" + emailBody.toString());

        if (recipients.size() > 0 && emailIsNecessary) {
            Map<String, Object> returnResult = sendMail(emailSender, emailBody);
            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info(returnResult.get("sendResult"));
        } else {
            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("Email Recipients List is Empty! Or No Error Found.");
        }
    }

    @Override
    public void sendMultipleMail(Context context, List<ResponseResult> results, HashMap<String, BusinessObject> businessObjectNameMap, HashMap<String, ItemInfo> itemInfoMap) throws MatrixException {
        HashMap<String, List<ResponseResult>> userResponseMap = new HashMap<>();
        HashMap<String, List<ResponseResult>> changeActionResponseMap = new HashMap<>();
        HashMap<String, BusinessObject> changeActionNameMap = new HashMap<>();
        BusinessObjectUtils businessObjectUtil = new BusinessObjectUtils();
        for (ResponseResult result : results) {
            if (result != null) {
                String itemName = result.getItem();

//                if (itemName.contains("-")) {
//                    String[] part = itemName.split("-", 2);
//                    String lowarCase = part[0].toUpperCase();
//                    itemName = lowarCase + "-" + part[1];
//                }

                ItemInfo itemInfo = itemInfoMap.get(itemName);
                String fromState = itemInfo.getCurrentState();
                String toState = itemInfo.getNextState();
                BusinessObject businessObject = businessObjectNameMap.get(itemName);
                try {
                    SignatureList signatures = businessObject.getSignatures(context, fromState, toState);
                    String promoteUserName = "";
                    Signature promoteSignature = null;
                    if (signatures != null && signatures.size() >= 1) {
                        promoteSignature = signatures.get(signatures.size() - 1);
                    }
                    promoteUserName = promoteSignature.getSigner();
                    if ("".equalsIgnoreCase(promoteUserName) || promoteUserName == null) {
                        TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("Item Name :: " + itemName + " No promote user found. Error mail will be sent to AMS.");
                        String recipient_AMS = ApplicationProperties.getProprtyValue("ams.mail.recipient");
                        List<ResponseResult> resultList;
                        if (!userResponseMap.containsKey(recipient_AMS)) {
                            resultList = new ArrayList<>();
                        } else {
                            resultList = userResponseMap.get(recipient_AMS);
                        }
                        resultList.add(result);
                        userResponseMap.put(recipient_AMS, resultList);
                    } else {
                        if (promoteUserName.equalsIgnoreCase("User Agent")) {

                            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info("Item Name :: " + itemName + " Promote User Name : " + promoteUserName);

                            String changeActionId = BusinessObjectUtils.getItemAttributeValue(context, businessObject.getObjectId(),
                                    ApplicationProperties.getProprtyValue("item.att.change.id"));
                            BusinessObject changeActionBO = null;
                            List<ResponseResult> resultList;
                            if (changeActionId == null || changeActionId.isEmpty()) {
                                try {
                                    changeActionBO = businessObjectUtil.getChangeActionFromItem(context, businessObject);
                                } catch (MatrixException e) {
                                    TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error(e);
                                    TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("No promote user found. Error mail will be sent to AMS.");
                                    //recipients.add(ApplicationProperties.getProprtyValue("ams.mail.recipient"));
                                    //Mail can be sent to AMS.
                                }
                            } else {
                                changeActionBO = new BusinessObject(changeActionId);
                            }

                            // VSIX-4907 : Child Item is not transferred to LN, if we released parent, child at once by CA
                            if (changeActionBO != null) {
                                changeActionBO.open(context);
                                String changeActionName = changeActionBO.getName();
                                changeActionNameMap.put(changeActionName, changeActionBO);
                                if (!changeActionResponseMap.containsKey(changeActionName)) {
                                    resultList = new ArrayList<>();
                                } else {
                                    resultList = changeActionResponseMap.get(changeActionName);
                                }
                                resultList.add(result);
                                changeActionResponseMap.put(changeActionName, resultList);
                            }
                        } else {
                            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info("Item Name :: " + itemName + " Promote User Name : " + promoteUserName);
                            String recipient_User = promoteUserName;
                            List<ResponseResult> resultList;
                            if (!userResponseMap.containsKey(recipient_User)) {
                                resultList = new ArrayList<>();
                            } else {
                                resultList = userResponseMap.get(recipient_User);
                            }
                            resultList.add(result);
                            userResponseMap.put(recipient_User, resultList);
                        }
                    }
                } catch (MatrixException e) {
                    TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error(e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info("User Mail ID and Response Map :: " + userResponseMap);
        for (String user : userResponseMap.keySet()) {
            recipients = new LinkedHashSet<>();
            if (user.equalsIgnoreCase(ApplicationProperties.getProprtyValue("ams.mail.recipient"))) {
                recipients.add(user);
            } else {
                recipients = BusinessObjectUtils.getRecipientsByOwnerName(context, user);
            }

            List<ResponseResult> responseResultList = userResponseMap.get(user);
            emailBodyBuilder.clearResults();

            StringBuilder emailBody = emailBodyBuilder.getEmailBody(responseResultList, SenderType.LN_TRANSFER_RESPONSE_SENDER);
            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info("Prepared Email Body: " + emailBody.toString());

            if (emailIsNecessary) {
                Map<String, Object> returnResult = sendMail(emailSender, emailBody);
                TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info(returnResult.get("sendResult"));
            } else {
                TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("Email Recipients List is Empty! Or No Error Found.");
            }
        }

        TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info("Change Action Name and Response Map :: " + changeActionResponseMap);
        for (String changeActionName : changeActionResponseMap.keySet()) {
            recipients = new LinkedHashSet<>();
            ArrayList<BusinessObject> reviewerBOList = new ArrayList<>();
            BusinessObject changeActionBO = changeActionNameMap.get(changeActionName);
            reviewerBOList = businessObjectUtil.getChangeActionReviewersFromChangeAction(context, changeActionBO);
            setProjectTaskAndChangeActionName(context, changeActionBO);

            if (!reviewerBOList.isEmpty()) {
                for (BusinessObject reviewerBO : reviewerBOList) {
                    reviewerBO.open(context);
                    String reviewerName = reviewerBO.getName();
                    recipients.addAll(BusinessObjectUtils.getRecipientsByOwnerName(context, reviewerName));
                    reviewerBO.close(context);
                }
            } else {
                TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("No promote user found. Error mail will be sent to AMS.");
                //recipients.add(ApplicationProperties.getProprtyValue("ams.mail.recipient"));
            }

            List<ResponseResult> responseResultList = changeActionResponseMap.get(changeActionName);

            StringBuilder emailBody = emailBodyBuilder.getEmailBody(responseResultList, SenderType.LN_TRANSFER_RESPONSE_SENDER);
            TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info("Prepared Email Body: " + emailBody.toString());

            if (emailIsNecessary) {
                Map<String, Object> returnResult = sendMail(emailSender, emailBody);
                TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info(returnResult.get("sendResult"));
            } else {
                TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("Email Recipients List is Empty! Or No Error Found.");
            }
        }
    }

    /**
     * VSIX-4899
     *
     * @param context
     * @param changeActionBO
     */
    private void setProjectTaskAndChangeActionName(Context context, BusinessObject changeActionBO) {
        TransferObjectUtils transferObjectUtils = new TransferObjectUtils();
        Map<String, String> projectTaskMap = transferObjectUtils.getTaskAndProjectNameFromCA(context, changeActionBO);
        if (projectTaskMap.containsKey("task")) {
            activity = projectTaskMap.get("task");
        }
        if (projectTaskMap.containsKey("project")) {
            project = projectTaskMap.get("project");
        }
        if (changeActionBO != null) {
            try {
                changeActionBO.open(context);
                emailBodyBuilder.setChangeActionName(changeActionBO.getName());
                changeActionBO.close(context);
            } catch (MatrixException ex) {
                TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("Error occurred while fetching " + ex.getMessage());
            }
        }
    }

}