package com.bjit.dw.enovia.utility;

import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UpdateUtil {
    private static final Logger UPDATE_UTIL_LOGGER = Logger.getLogger(UpdateUtil.class);

    public static synchronized void noteUpdateDateTime(String noteType) {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration(ApplicationProperties.getProprtyValue("ln.env.config.properties.dir"));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            if (noteType.equalsIgnoreCase("success")) {
                UPDATE_UTIL_LOGGER.info("Taking note of successful update date time in config file");
                config.setProperty("dw.data.successful.update.date.time", currentDate);
                config.setProperty("weight.data.successful.update.date.time", currentDate);
            } else {
                UPDATE_UTIL_LOGGER.info("Taking note of failed update date time in config file");
                config.setProperty("dw.data.failed.update.date.time", currentDate);
                config.setProperty("weight.data.failed.update.date.time", currentDate);
            }
            config.save();
        } catch (ConfigurationException e) {
            UPDATE_UTIL_LOGGER.error(e.getMessage());
        } catch (Exception e) {
            UPDATE_UTIL_LOGGER.error(e.getMessage());
        }
    }

    public static synchronized List<HashMap<String,String>> combineDWDataAndWeightData(List<HashMap<String, String>> dwDataList, List<HashMap<String, String>> weightDataList) {
        List<HashMap<String, String>> combinedList = new ArrayList<>();
        if(!NullOrEmptyChecker.isNullOrEmpty(dwDataList)) {
            combinedList.addAll(dwDataList);
        }
        if(NullOrEmptyChecker.isNullOrEmpty(combinedList)) {
            weightDataList.forEach(weightData -> {
                HashMap<String, String> data = new HashMap<>();
                data.putAll(weightData);
                data.put("Item", weightData.get("Item"));
                data.remove("ITEM");
            });
        } else {
            HashMap<String, HashMap<String, String>> weightDataListIndexMap = new HashMap<>();
            weightDataList.forEach(weightData -> {
                weightDataListIndexMap.put(weightData.get("ITEM"), weightData);
            });
            List<String> matchedWeightData = new ArrayList<>();
            combinedList.forEach(combinedData -> {
                String dwItem = combinedData.get("Item");
                if(weightDataListIndexMap.containsKey(dwItem)) {
                    HashMap<String, String> weightData = weightDataListIndexMap.get(dwItem);
                    combinedData.put("WEIGHTUNIT", weightData.get("WEIGHTUNIT"));
                    combinedData.put("WEIGHT", weightData.get("WEIGHT"));
                    matchedWeightData.add(dwItem);
                }
            });
            weightDataList.forEach(weightData -> {
                if(!matchedWeightData.contains(weightData.get("ITEM"))) {
                    HashMap<String, String> weight = new HashMap<>();
                    weight.put("Item", weightData.get("ITEM"));
                    weight.put("WEIGHTUNIT", weightData.get("WEIGHTUNIT"));
                    weight.put("WEIGHT", weightData.get("WEIGHT"));
                    combinedList.add(weight);
                }
            });
        }
        return combinedList;
    }
}
