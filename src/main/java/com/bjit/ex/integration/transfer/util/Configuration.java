/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.util;

/**
 *
 * @author BJIT
 */
public class Configuration {
    //public static String MAPPING_XML_FILE_PATH = "/mapper_files/mapping_LN_attr.xml";
    public static String TASK_MILESTONE_XML_FILE_PATH = "/mapper_files/mapping_LN_task_milestone.xml";
    public static String TNRC_PROPERTIES_FILE_PATH = "mapper_files/TNRCProperties.properties";
    public static String MAPPING_XML_FILE_DIRECTORY = ApplicationProperties.getProprtyValue("xml.att.mapping.file.dir");
//    public static String TASK_MILESTONE_XML_FILE_DIRECTORY = ApplicationProperties.getProprtyValue("xml.att.mapping.file.dir");
    public static String TASK_MILESTONE_XML_FILE_DIRECTORY = ApplicationProperties.getProprtyValue("xml.taskmilstone.att.mapping.file.dir");
    //public static String MAPPING_XML_FILE_DIRECTORY = "C:\\Users\\BJIT\\Desktop\\New folder (3)\\old\\mapping_LN_attr.xml";
    //public static String MAPPING_XML_FILE_DIRECTORY = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "mapping_LN_attr.xml";
}
