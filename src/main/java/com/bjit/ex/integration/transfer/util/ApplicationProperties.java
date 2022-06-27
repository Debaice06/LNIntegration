/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Sajjad
 */
public class ApplicationProperties {
    private static final org.apache.log4j.Logger APPLICATION_PROPERTIES_LOGGER = org.apache.log4j.Logger.getLogger(ApplicationProperties.class);
    public static String LN_INTEGRATION_PROPERTIES_FILE = "LNIntegrationProperties.properties";
    public static String LN_LANGUAGEID_PROPERTIES_FILE = "LNLanguageId.properties";
    public static String LN_INTEGRATION_ENV__CONFIG_FILE = "LNEnvConfig.properties";
    private static Map<String, String> propertyMap = new HashMap<String, String>();
    private static Map<String, String> LNLanguageIdMap = new HashMap<>();
    public static String LN_INTEGRATION_ENV__PROPERTIES_FILE;

    static {
        try {
            new ApplicationProperties().loadPropertiesValues();
            new ApplicationProperties().loadLNLanguageIdValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean reloadProperty() {
        try {
            APPLICATION_PROPERTIES_LOGGER.info("Reloading LN-Integration Property Files!!");
            propertyMap = new HashMap<String, String>();
            new ApplicationProperties().loadPropertiesValues();
            new ApplicationProperties().loadLNLanguageIdValues();
            APPLICATION_PROPERTIES_LOGGER.info("LN-Integration Property Files Reloaded Successfully!!");
            return true;
        } catch (IOException e) {
            APPLICATION_PROPERTIES_LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    private void loadPropertiesValues() throws IOException {
        /*Read environment property file from common*/
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(LN_INTEGRATION_PROPERTIES_FILE);
        setPropertyMap(input);
        /*Read environment property file from profile*/
        InputStream profileInput = this.getClass().getClassLoader().getResourceAsStream(LN_INTEGRATION_ENV__CONFIG_FILE);
        setPropertyMap(profileInput);
        /*Read environment property file from server directory.*/
        LN_INTEGRATION_ENV__PROPERTIES_FILE = getProprtyValue("ln.env.config.properties.dir");
        APPLICATION_PROPERTIES_LOGGER.info("Reading environment property file from location: " + LN_INTEGRATION_ENV__PROPERTIES_FILE);
        File envConfigPropertyFile = new File(LN_INTEGRATION_ENV__PROPERTIES_FILE);
        InputStream envInput = new FileInputStream(envConfigPropertyFile);
        setPropertyMap(envInput);
    }
    
    private void setPropertyMap(InputStream input) throws IOException{
        Properties properties = new Properties();
        if (input != null) {
            properties.load(input);
            Enumeration<Object> propertyKeys = properties.keys();
            while (propertyKeys.hasMoreElements()) {
                String key = (String) propertyKeys.nextElement();
                if (properties.get(key) != null) {
                    propertyMap.put(key, properties.get(key).toString());
                } else {
                    propertyMap.put(key, "");
                }
            }
        }
    }

    public static String getProprtyValue(String propertyKey) {
        String proprtiesValue = "";
        proprtiesValue = propertyMap.get(propertyKey);
        return proprtiesValue;
    }

    private void loadLNLanguageIdValues() throws IOException {
        Properties properties = new Properties();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(LN_LANGUAGEID_PROPERTIES_FILE);
        //InputStream input = input = new FileInputStream(LN_INTEGRATION_PROPERTIES_FILE);
        if (input != null) {
            properties.load(input);
            Enumeration<Object> propertyKeys = properties.keys();
            while (propertyKeys.hasMoreElements()) {
                String key = (String) propertyKeys.nextElement();
                if (properties.get(key) != null) {
                    LNLanguageIdMap.put(key, properties.get(key).toString());
                } else {
                    LNLanguageIdMap.put(key, "");
                }
            }
        }
    }
    public static String getLNLanguageIDProprtyValue(String propertyKey) {
        String proprtiesValue = "";
        proprtiesValue = LNLanguageIdMap.get(propertyKey);
        return proprtiesValue;
    }
    public static Map getLNLanguageIDMap(){
        return LNLanguageIdMap;
    }
    public static void updateLog4jConfiguration(String logFile) {
        Properties props = new Properties();
        try {
            InputStream configStream = ApplicationProperties.class.getClass().getResourceAsStream("/log4j.properties");
            props.load(configStream);
            configStream.close();
        } catch (IOException e) {
            System.out.println("Errornot laod configuration file ");
        }
        props.setProperty("log4j.appender.file.File", logFile);
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(props);
    }
}
