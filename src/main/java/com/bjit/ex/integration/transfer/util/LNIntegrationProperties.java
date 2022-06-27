/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class takes a property file name at the time of initialization of the
 * object. Retrieves all the properties from the file and puts them into a
 * HashMap. When the "getProprtyValue()" get called then it returns the
 * properties HashMap to the caller.
 *
 * @author BJIT / Md.Omour Faruq
 */
public final class LNIntegrationProperties {

    private final String propertiesFileName;
    private final HashMap<String, String> propertyMap;
    static final org.apache.log4j.Logger LN_INTEGRATION_PROPERTIES_LOGGER = org.apache.log4j.Logger.getLogger(LNIntegrationProperties.class.getName());

    public LNIntegrationProperties(String propertiesFileName) throws IOException {
        try {
            LN_INTEGRATION_PROPERTIES_LOGGER.info("Name of properties file is " + propertiesFileName + "");
            propertyMap = new HashMap<>();
            this.propertiesFileName = propertiesFileName;
            __init__();
            LN_INTEGRATION_PROPERTIES_LOGGER.info("Retrievieng properties from " + propertiesFileName + " has been done successfully");
        } catch (IOException exp) {
            exp.printStackTrace(System.out);
            LN_INTEGRATION_PROPERTIES_LOGGER.error("Properties Initialization has failed. " + exp.getMessage());
            throw exp;
        }
    }

    /**
     * Calls the "loadPropertiesValues()" method to initialize and load
     * properties from the properties file.
     */
    private void __init__() throws IOException {
        try {
            LN_INTEGRATION_PROPERTIES_LOGGER.info("Initializing Properties");
            this.loadPropertiesValues();
        } catch (IOException exp) {
            exp.printStackTrace(System.out);
            LN_INTEGRATION_PROPERTIES_LOGGER.error("Properties Initialization has failed. " + exp.getMessage());
            throw exp;
        }
    }

    /**
     *
     * Loads the properties HashMap from the properties file, name given at the
     * initialization time of the object.
     */
    private void loadPropertiesValues() throws IOException {
        Properties properties = new Properties();
        try (InputStream propertiesInputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (propertiesInputStream != null) {
                properties.load(propertiesInputStream);
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
        } catch (IOException exp) {
            exp.printStackTrace(System.out);
            LN_INTEGRATION_PROPERTIES_LOGGER.error("Properties Initialization has failed. " + exp.getMessage());
        }
    }

    /**
     *
     *
     * @return properties HashMap from the properties file, name given at the
     * initialization time of the object.
     */
    public HashMap<String, String> getProprtyValue() {
        return propertyMap;
    }
}
