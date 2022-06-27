package com.bjit.ex.integration.transfer.resultsender.factory;

import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.impls.ResponseResultSenderImpl;
import com.bjit.ex.integration.transfer.resultsender.impls.NightlyItemTransferResponseResultSenderImpl;
import com.bjit.ex.integration.transfer.resultsender.impls.ServiceResponseResultSenderImpl;

public class ResultSenderFactory {

    public static ResponseResultSender getResultSender(SenderType resultSenderType) {
        switch (resultSenderType) {
            case LN_TRANSFER_RESPONSE_SENDER:
                return new ResponseResultSenderImpl();
            case NIGHTLY_TRANSFER_RESPONSE_SENDER:
                return new NightlyItemTransferResponseResultSenderImpl();
            case SERVICE_RESPONSE_SENDER:
                return new ServiceResponseResultSenderImpl();
            default:
                return null;
        }
    }

}
