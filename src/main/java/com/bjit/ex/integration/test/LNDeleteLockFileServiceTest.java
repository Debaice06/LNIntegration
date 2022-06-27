/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.test;

import com.bjit.ex.integration.serviceactions.DeleteLockFileAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sajjad
 */
public class LNDeleteLockFileServiceTest {
    static final org.apache.log4j.Logger LN_DELETE_LOCK_FILE_TEST_LOGGER = org.apache.log4j.Logger.getLogger(LNDeleteLockFileServiceTest.class.getName());

    public static void main(String[] args) {
        DeleteLockFileAction deleteAction = new DeleteLockFileAction();
        try {
            deleteAction.deleteFile();
        } catch (Exception ex) {
            LN_DELETE_LOCK_FILE_TEST_LOGGER.error(ex);
        }
    }
}
