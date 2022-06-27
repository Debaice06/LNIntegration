package com.bjit.dw.enovia.action;

import com.bjit.dw.enovia.action.processors.UpdateProcessor;
import com.bjit.dw.enovia.ex.dw.processor.DWDataProcessor;
import com.bjit.dw.enovia.ex.dw.processor.WeightDataProcessor;
import com.bjit.dw.enovia.utility.UpdateUtil;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class EnoviaNightlyUpdateAction {

    private static final Logger ENOVIA_UPDATE_ACTION_LOGGER = Logger.getLogger(EnoviaNightlyUpdateAction.class);

    public EnoviaNightlyUpdateAction() throws Exception {
        ENOVIA_UPDATE_ACTION_LOGGER.info(":: LN DW-ENOVIA UPDATE - Started ::");
        initiateUpdate();
    }

    private void initiateUpdate() throws Exception {
        try {
            List<HashMap<String, String>> dwDataList = new ArrayList<>();
            List<HashMap<String, String>> weightDataList = new ArrayList<>();
            try {
                DWDataProcessor dwDataProcessor = new DWDataProcessor();
                dwDataList = dwDataProcessor.getDWData();
            } catch (Exception ex) {
                ENOVIA_UPDATE_ACTION_LOGGER.info(":: Error occur from DW service ::" + ex.getMessage());
            }
            try {
                WeightDataProcessor weightDataProcessor = new WeightDataProcessor();
                weightDataList = weightDataProcessor.getWeightData();
            } catch (Exception ex) {
                ENOVIA_UPDATE_ACTION_LOGGER.info(":: Error occur from Weight service ::" + ex.getMessage());
            }
            List<HashMap<String, String>> mergedList = UpdateUtil.combineDWDataAndWeightData(dwDataList, weightDataList);

            if (!NullOrEmptyChecker.isNullOrEmpty(mergedList)) {
                UpdateProcessor updateProcessor = new UpdateProcessor();
                updateProcessor.update(mergedList);
            }
            UpdateUtil.noteUpdateDateTime("success");
        } catch (Exception e) {
            UpdateUtil.noteUpdateDateTime("failed");
            throw e;
        }

    }
}
