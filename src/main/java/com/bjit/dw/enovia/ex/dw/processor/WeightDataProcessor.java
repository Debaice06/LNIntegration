package com.bjit.dw.enovia.ex.dw.processor;

import com.bjit.dw.enovia.ex.dw.service.WeightServiceRequester;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeightDataProcessor {
    private static final Logger WEIGHT_DATA_PROCESSOR = Logger.getLogger(WeightDataProcessor.class);

    public List<HashMap<String, String>> getWeightData() throws Exception {
        List<HashMap<String, String>> weightDataList = new ArrayList<>();
        try {
            String startDateTime = getLastExecutionDateTime();
            String endDateTime = getEndDateTime();
            WeightServiceRequester serviceRequester = new WeightServiceRequester();
            weightDataList = serviceRequester.fetchLNWeightItemListNightly(startDateTime, endDateTime);
        } catch (Exception e) {
            WEIGHT_DATA_PROCESSOR.error(e.getMessage());
            throw e;
        }
        return weightDataList;
    }

    private String getLastExecutionDateTime() {
        String lastExecutionDateTime = "";
        String lastSuccessfulExecutionDateTime = ApplicationProperties.getProprtyValue("weight.data.successful.update.date.time");
        String lastFailedExecutionDateTime = ApplicationProperties.getProprtyValue("weight.data.failed.update.date.time");
        WEIGHT_DATA_PROCESSOR.info("Last Successful Execution Date ::: '" + lastSuccessfulExecutionDateTime + "'");
        if(!NullOrEmptyChecker.isNullOrEmpty(lastSuccessfulExecutionDateTime)) {
            lastExecutionDateTime = lastSuccessfulExecutionDateTime;
        }
        else if(!NullOrEmptyChecker.isNullOrEmpty(lastFailedExecutionDateTime)) {
            WEIGHT_DATA_PROCESSOR.info("Last Failed Execution Date ::: '" + lastFailedExecutionDateTime + "'");
            lastExecutionDateTime = lastFailedExecutionDateTime;
        }
        else {
            WEIGHT_DATA_PROCESSOR.info("Calculating Last Execution Date...\n");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date yesterday = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            lastExecutionDateTime = dateFormat.format(yesterday);
        }
        return lastExecutionDateTime;
    }

    private String getEndDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public List<HashMap<String, String>> getWeightTestData() {
        List<HashMap<String, String>> data = new ArrayList<>();
        HashMap<String, String> item1 = new HashMap<>();
        item1.put("WEIGHTUNIT", "kg");
        item1.put("PRODUCTTYPE", "ENV");
        item1.put("ITEM", "MASS-EPS1-00021081");
        item1.put("LOGISTICCOMPANY", "1001");
        item1.put("WEIGHT", "15.000045");
        item1.put("Modified_Date", "2021-12-08 11:10:13");
        data.add(item1);
        HashMap<String, String> item2 = new HashMap<>();
        item2.put("WEIGHTUNIT", "kg");
        item2.put("PRODUCTTYPE", "ENV");
        item2.put("ITEM", "MASS-EPS1-00012777"); //MASS-EPS1-00018705
        item2.put("LOGISTICCOMPANY", "1001");
        item2.put("WEIGHT", "2.75");
        item2.put("Modified_Date", "2021-12-16 07:50:54");
        data.add(item2);
        return data;
    }
}
