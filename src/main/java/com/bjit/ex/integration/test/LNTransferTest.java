/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.test;

import com.bjit.ex.integration.transfer.actions.TransferAction;

/**
 *
 * @author BJIT
 */
public class LNTransferTest {
    static final org.apache.log4j.Logger LN_TRANSFER_TEST_LOGGER = org.apache.log4j.Logger.getLogger(LNTransferTest.class.getName());

    public static void main(String[] args) {
        try{
            long LNTransferProcessStartTime = System.currentTimeMillis();

            TransferAction transferAction = new TransferAction();

            long LNTransferProcessEndTime = System.currentTimeMillis();
            long LNTransferProcessElapsedTime = LNTransferProcessEndTime - LNTransferProcessStartTime;
            LN_TRANSFER_TEST_LOGGER.info("LN Transfer Process Elapsed Time ::: "+LNTransferProcessElapsedTime);
        }
        catch(Exception exp){
            LN_TRANSFER_TEST_LOGGER.error("Errors occur during transfer.");
            LN_TRANSFER_TEST_LOGGER.error(exp.getMessage());
        }
    }
}
