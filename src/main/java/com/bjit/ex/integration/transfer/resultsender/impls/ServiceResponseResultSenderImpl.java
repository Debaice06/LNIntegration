package com.bjit.ex.integration.transfer.resultsender.impls;

import com.bjit.ex.integration.model.webservice.Item;
import com.bjit.ex.integration.transfer.actions.utilities.BusinessObjectUtils;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.util.ResultSenderUtil;
import com.bjit.ex.integration.transfer.service.impl.LN.Constants;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.ItemInfo;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.db.Signature;
import matrix.db.SignatureList;
import matrix.util.MatrixException;

import java.util.*;

public class ServiceResponseResultSenderImpl extends ResponseResultSender {

    private static final org.apache.log4j.Logger SERVICE_RESPONSE_RESULT_SENDER_LOGGER = org.apache.log4j.Logger.getLogger(ServiceResponseResultSenderImpl.class);

    @Override
    public void initializeResultSender(Context context, BusinessObject businessObject) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initializeResultSender() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initializeResultSender(Context context, BusinessObject businessObject, Item item, boolean isService) {
        try {
            emailIsNecessary = false;
            //itemInfoMap = new LinkedHashMap<>();
            //successfulValItemResponseResult = new ArrayList<>();
            isAllItemTransfered = true;
            recipients = new LinkedHashSet<>();
            List<String> attributes = new ArrayList<>();
            attributes.add(ApplicationProperties.getProprtyValue("source.CreateAssembly.att.outsourced"));

            BusinessObjectUtils businessObjectUtils = new BusinessObjectUtils();
            String findBusinessObjectsState = businessObjectUtils.findBusinessObjectsState(businessObject, context);

            SignatureList signatures = businessObject.getSignatures(context, findBusinessObjectsState, findBusinessObjectsState);
            String promoteUserName = "";
            Signature promoteSignature = null;
            if (signatures != null && signatures.size() >= 1) {
                promoteSignature = signatures.get(signatures.size() - 1);
            }
            if (!isService) {
                promoteUserName = promoteSignature.getSigner();
            }
            if (promoteUserName != null && !"".equals(promoteUserName)) {
                SERVICE_RESPONSE_RESULT_SENDER_LOGGER.error("No promote user found there for Mail is not send");
                recipients = BusinessObjectUtils.getRecipientsByOwnerName(context, promoteUserName);

            } else {
                SERVICE_RESPONSE_RESULT_SENDER_LOGGER.error("No promote user found. Error mail will be sent to AMS.");
                recipients.add(ApplicationProperties.getProprtyValue("ams.mail.recipient"));

            }
            emailSender = ApplicationProperties.getProprtyValue(Constants.emailSenderKey);
            emailSubject = ApplicationProperties.getProprtyValue(Constants.successfulEmailSubjectKey);
            enoviaToLnTransferSuccessfulMessageHeader = ApplicationProperties.getProprtyValue(Constants.nightlyUpdateMessageHeaderKey);
            enoviaToLnTransferErrorMessageHeader = ApplicationProperties.getProprtyValue(Constants.errorMessageHeaderKey);
            enoviaToLnTransferUnrecognizedMessageHeader = ApplicationProperties.getProprtyValue(Constants.unrecognizedMessageHeaderKey);
        } catch (MatrixException ex) {
            SERVICE_RESPONSE_RESULT_SENDER_LOGGER.error(ex);
        }
    }

    @Override
    public void send(List<ResponseResult> results) {

        ResultSenderUtil resultSenderUtil = new ResultSenderUtil();
        StringBuilder emailBody = resultSenderUtil.getEmailBody(results, SenderType.SERVICE_RESPONSE_SENDER);

        SERVICE_RESPONSE_RESULT_SENDER_LOGGER.info("Prepared Email Body: \n" + emailBody.toString());

        if (recipients.size() > 0 && emailIsNecessary) {
            Map<String, Object> returnResult = sendMail(emailSender, emailBody);
            SERVICE_RESPONSE_RESULT_SENDER_LOGGER.info(returnResult.get("sendResult"));
        } else {
            SERVICE_RESPONSE_RESULT_SENDER_LOGGER.error("Email Recipients List is Empty! Or No Error Found.");
        }
    }

    @Override
    public void sendMultipleMail(Context context, List<ResponseResult> results, HashMap<String, BusinessObject> businessObjectNameMap, HashMap<String, ItemInfo> itemInfoMap) {

    }
}
