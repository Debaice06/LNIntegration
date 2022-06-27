/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.serviceactions;

import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.FileDirProcess;
import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Sajjad
 */
public class DeleteLockFileAction {
    
    static final org.apache.log4j.Logger LN_DELETE_LOCK_FILE_ACTION_LOGGER = org.apache.log4j.Logger.getLogger(DeleteLockFileAction.class.getName());

    public static final String lockFileDirectory = ApplicationProperties.getProprtyValue("xml.file.folder.dir");
    
    public void deleteFile () throws Exception {
        LN_DELETE_LOCK_FILE_ACTION_LOGGER.info("Lock File Directory Name :: " + lockFileDirectory);
        File directoryName = new File(lockFileDirectory);
        FileFilter filter = FileDirProcess.getFileFilter(directoryName, "lock");
        File[] listOfFiles = directoryName.listFiles(filter);
        for (File file : listOfFiles) {
            LN_DELETE_LOCK_FILE_ACTION_LOGGER.info("Lock File Name :: " + file.getName());
            file.delete();
            LN_DELETE_LOCK_FILE_ACTION_LOGGER.info("Lock File Deleted !!");
        }
    }
    
    
}
