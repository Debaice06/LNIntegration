package com.bjit.ex.integration.transfer.resultsender.impls;

import com.bjit.ex.integration.model.webservice.Item;
import com.bjit.ex.integration.transfer.actions.utilities.GTSDataUtil;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.util.ResultSenderUtil;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.ItemInfo;
import matrix.db.BusinessObject;
import matrix.db.Context;

import java.util.*;

public class NightlyItemTransferResponseResultSenderImpl extends ResponseResultSender {

    private static final org.apache.log4j.Logger NIGHTLY_TRANSFER_RESPONSE_RESULT_SENDER_LOGGER = org.apache.log4j.Logger.getLogger(NightlyItemTransferResponseResultSenderImpl.class);

    @Override
    public void initializeResultSender() {
        try {
            emailIsNecessary = false;
            //itemInfoMap = new LinkedHashMap<String, Map<String, Object>>();
            //itemInfoMapDetails = new LinkedHashMap<String, List<Object>>();
            //successfulValItemResponseResult = new ArrayList<ResponseResult>();
            isAllItemTransfered = true;

            recipients = getNightlyItemTransferRecipientList();

            emailSender = ApplicationProperties.getProprtyValue("ln.transfer.error.message.sender.email");

            emailSubject = ApplicationProperties.getProprtyValue("gts.nightly.update.email.subject");
            enoviaToLnTransferSuccessfulMessageHeader = ApplicationProperties.getProprtyValue("ln.transfer.successful.message.header");
            enoviaToLnTransferErrorMessageHeader = ApplicationProperties.getProprtyValue("ln.transfer.error.message.header");
            enoviaToLnTransferUnrecognizedMessageHeader = ApplicationProperties.getProprtyValue("ln.transfer.unrecognized.message.header");

        } catch (Exception ex) {
            NIGHTLY_TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void initializeResultSender(Context context, BusinessObject businessObject) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initializeResultSender(Context context, BusinessObject businessObject, Item item, boolean isService) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(List<ResponseResult> results) {

        ResultSenderUtil resultSenderUtil = new ResultSenderUtil();
        StringBuilder emailBody = resultSenderUtil.getEmailBody(results, SenderType.NIGHTLY_TRANSFER_RESPONSE_SENDER);

        NIGHTLY_TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info("Prepared Email Body: \n" + emailBody.toString());

        if (recipients.size() > 0 && emailIsNecessary) {
            Map<String, Object> returnResult = sendMail(emailSender, emailBody);
            NIGHTLY_TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.info(returnResult.get("sendResult"));
            GTSDataUtil.noteTransferDate("failed");
        } else {
            NIGHTLY_TRANSFER_RESPONSE_RESULT_SENDER_LOGGER.error("Email Recipients List is Empty! Or No Error Found.");
            GTSDataUtil.noteTransferDate("success");
        }
    }

    @Override
    public void sendMultipleMail(Context context, List<ResponseResult> results, HashMap<String, BusinessObject> businessObjectNameMap, HashMap<String, ItemInfo> itemInfoMap) {

    }

    private Set<String> getNightlyItemTransferRecipientList() {
        String recipientListAsStr = ApplicationProperties.getProprtyValue("ln.nightly.update.email.recipient");
        return new LinkedHashSet<String>(Arrays.asList(recipientListAsStr.split(",")));
    }
}
