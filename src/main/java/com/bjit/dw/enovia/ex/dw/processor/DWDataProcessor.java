package com.bjit.dw.enovia.ex.dw.processor;

import com.bjit.dw.enovia.ex.dw.service.DWServiceRequester;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DWDataProcessor {
    private static final Logger DW_DATA_PROCESSOR = Logger.getLogger(DWDataProcessor.class);

    public List<HashMap<String, String>> getDWData() throws Exception {
        List<HashMap<String, String>> dwDataList = new ArrayList<>();
        try {
            String lastExecutionDateTime = getLastExecutionDateTime();
            DWServiceRequester serviceRequester = new DWServiceRequester();
            dwDataList = serviceRequester.fetchLNDWItemListNightly(lastExecutionDateTime);
        } catch (Exception e) {
            DW_DATA_PROCESSOR.error(e.getMessage());
            throw e;
        }
        return dwDataList;
    }

    private String getLastExecutionDateTime() {
        String lastExecutionDateTime = "";
        String lastSuccessfulExecutionDateTime = ApplicationProperties.getProprtyValue("dw.data.successful.update.date.time");
        String lastFailedExecutionDateTime = ApplicationProperties.getProprtyValue("dw.data.failed.update.date.time");
        DW_DATA_PROCESSOR.info("Last Successful Execution Date ::: '" + lastSuccessfulExecutionDateTime + "'");
        if(!NullOrEmptyChecker.isNullOrEmpty(lastSuccessfulExecutionDateTime)) {
            lastExecutionDateTime = lastSuccessfulExecutionDateTime;
        }
        else if(!NullOrEmptyChecker.isNullOrEmpty(lastFailedExecutionDateTime)) {
            DW_DATA_PROCESSOR.info("Last Failed Execution Date ::: '" + lastFailedExecutionDateTime + "'");
            lastExecutionDateTime = lastFailedExecutionDateTime;
        }
        else {
            DW_DATA_PROCESSOR.info("Calculating Last Execution Date...\n");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date yesterday = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            lastExecutionDateTime = dateFormat.format(yesterday);
        }
        return lastExecutionDateTime;
    }

    public List<HashMap<String, String>> getDWTestData() {
        List<HashMap<String, String>> data = new ArrayList<>();
        HashMap<String, String> item1 = new HashMap<>();
        item1.put("Item", "ELEI-EPS1-00002593");
        item1.put("CommodityCodeUS", "39079110");
        item1.put("CommodityCode", "");
        item1.put("CommodityCode_Changed", "2021-10-22 13:45:15.0957669");
        item1.put("CommodityCodeUS_Changed", "2021-10-22 13:45:15.0957669");
        item1.put("CommodityCodeCN", "");
        item1.put("CommodityCodeCN_Changed", "2021-10-22 13:45:15.0957669");
        item1.put("RefItem_Changed", "2021-10-22 13:45:15.0957669");
        item1.put("RefItem", "REF0000007");
        data.add(item1);
        HashMap<String, String> item2 = new HashMap<>();
        item2.put("Item", "MASS-EPS1-00012777");
        item2.put("CommodityCodeUS", "39079110");
        item2.put("CommodityCode", "39033000");
        item2.put("CommodityCode_Changed", "2021-10-22 13:45:15.0957669");
        item2.put("CommodityCodeUS_Changed", "2021-10-22 13:45:15.0957669");
        item2.put("CommodityCodeCN", "39073000");
        item2.put("CommodityCodeCN_Changed", "2021-10-22 13:45:15.0957669");
        item2.put("RefItem_Changed", "2021-10-22 13:45:15.0957669");
        item2.put("RefItem", "REF0000021");
        data.add(item2);
        return data;
    }
}
