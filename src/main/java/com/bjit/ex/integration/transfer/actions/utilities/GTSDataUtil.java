/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities;

import com.bjit.ex.integration.externalservices.GTS.bundleId.service.GTSBundleIdResponse;
import com.bjit.ex.integration.externalservices.GTS.service.GTSResponse;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author Sajjad
 */
public class GTSDataUtil {

    private static final org.apache.log4j.Logger GTSDataUtil_LOGGER = org.apache.log4j.Logger.getLogger(GTSDataUtil.class);

    public static String getBundleId(String itemId) throws IOException {
        String bundleId = GTSResponse.getBungdleID(itemId);
        return bundleId;
    }

    public static List<String> getUpdatedBundleIds() throws KeyManagementException, NoSuchAlgorithmException, IOException {
        return GTSResponse.getUpdatedBundleIds();
    }

    public static GTSBundleIdResponse getAbbreviationData(String bundleId) throws IOException, MalformedURLException, KeyManagementException, NoSuchAlgorithmException {
        GTSBundleIdResponse abbreviationData = new GTSBundleIdResponse(bundleId);
        return abbreviationData;
    }

    public static void noteTransferDate(String noteType) {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration(ApplicationProperties.getProprtyValue("ln.env.config.properties.dir"));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            if (noteType.equalsIgnoreCase("success")) {
                GTSDataUtil_LOGGER.info("Taking note of successful transfer date in config file");
                config.setProperty("gts.nightly.succeful.transfer.date", currentDate);
            } else {
                GTSDataUtil_LOGGER.info("Taking note of failed transfer date in config file");
                config.setProperty("gts.nightly.failed.transfer.date", currentDate);
            }
            config.save();
        } catch (ConfigurationException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
