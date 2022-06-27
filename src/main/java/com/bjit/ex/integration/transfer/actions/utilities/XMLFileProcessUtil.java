package com.bjit.ex.integration.transfer.actions.utilities;

import com.bjit.ex.integration.transfer.actions.TransferAction;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.FileDirProcess;

import java.io.File;
import java.io.FileFilter;

public class XMLFileProcessUtil {
    private static String xmlBusinessObjectFileDirectory = "xml.file.folder.dir";
    private static String xmlBusinessObjectFileOldDirectory = "xml.old.file.folder.dir";
    private static String xmlBusinessObjectFileUnprocessedDirectory = "xml.unprocessed.file.folder.dir";
    private static final org.apache.log4j.Logger XML_FILE_PROCESS_UTIL_LOGGER = org.apache.log4j.Logger.getLogger(TransferAction.class);

    /**
     *
     * @param directoryNodeFromPropertiesFile
     * @return
     * @throws Exception
     */
    public File[] getBusinessObjectXmlFiles(String directoryNodeFromPropertiesFile) throws Exception {
        xmlBusinessObjectFileDirectory = directoryNodeFromPropertiesFile;
        String dir = ApplicationProperties.getProprtyValue(directoryNodeFromPropertiesFile);
        XML_FILE_PROCESS_UTIL_LOGGER.info("Loading XML from location: " + dir);
        File directoryName = new File(dir);

        try {
            FileFilter filter = FileDirProcess.getFileFilter(directoryName, "xml");
            File[] listOfFiles = directoryName.listFiles(filter);
            if (listOfFiles.length < 1) {
                XML_FILE_PROCESS_UTIL_LOGGER.info("No XML file found on the directory !!! ");
                throw new NullPointerException("No XML file found on the directory !!! ");
            }
            return listOfFiles;
        } catch (Exception exp) {
            XML_FILE_PROCESS_UTIL_LOGGER.error(exp.getMessage());
            throw exp;
        }
    }

    public void removeBusinessObjectXmlFilesToOldDirectory(File file) {
        removeBusinessObjectXmlFilesToOldDirectory(file, xmlBusinessObjectFileOldDirectory);
    }

    /**
     *
     * @param file
     * @param oldDirectoryNodeFromPropertiesFile
     */
    public void removeBusinessObjectXmlFilesToOldDirectory(File file, String oldDirectoryNodeFromPropertiesFile) {
        xmlBusinessObjectFileOldDirectory = oldDirectoryNodeFromPropertiesFile;
        String oldDir = ApplicationProperties.getProprtyValue(oldDirectoryNodeFromPropertiesFile);
        XML_FILE_PROCESS_UTIL_LOGGER.info("Moving XML to location: "+ oldDir);
        try {
            FileDirProcess.moveFile(file, oldDir);
        } catch (Exception ex) {
            XML_FILE_PROCESS_UTIL_LOGGER.error(ex.getMessage());
        }
    }

    /**
     *
     * @param file
     */
    public void moveBusinessObjectXmlFilesToUnprocessedDirectory(File file) {
        String unprocessedDir = ApplicationProperties.getProprtyValue(xmlBusinessObjectFileUnprocessedDirectory);
        XML_FILE_PROCESS_UTIL_LOGGER.info("Moving XML to location: "+unprocessedDir);
        try {
            FileDirProcess.moveFile(file, unprocessedDir);
        } catch (Exception ex) {
            XML_FILE_PROCESS_UTIL_LOGGER.error(ex.getMessage());
        }
    }

    public String getBusinessObjectXmlDirectory() {
        return ApplicationProperties.getProprtyValue(xmlBusinessObjectFileDirectory);
    }

    public String getBusinessObjectXmlOldDirectory() {
        return ApplicationProperties.getProprtyValue(xmlBusinessObjectFileOldDirectory);
    }

    public File[] getBusinessObjectXmlFiles() throws Exception {
        return getBusinessObjectXmlFiles(xmlBusinessObjectFileDirectory);
    }
}
