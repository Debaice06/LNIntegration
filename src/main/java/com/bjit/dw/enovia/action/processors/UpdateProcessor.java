package com.bjit.dw.enovia.action.processors;

import com.bjit.context.ContextGeneration;
import com.bjit.dw.enovia.model.UpdateItemBean;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import matrix.db.Context;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class UpdateProcessor {
    private static final Logger UPDATE_PROCESSOR_LOGGER = Logger.getLogger(UpdateProcessor.class);
    private BusinessOperations businessOperations;

    public UpdateProcessor() {
        this.businessOperations = new BusinessOperations();
    }

    public void update(List<HashMap<String, String>> dwDataList) {
        try {
            if(!NullOrEmptyChecker.isNullOrEmpty(dwDataList)) {
                final Context context;
                context = ContextGeneration.createContext();
                List<UpdateItemBean> filteredItemList = businessOperations.filterDWDataForUpdate(dwDataList);
                List<UpdateItemBean> updatableItemList = businessOperations.checkItemExistInEnovia(context, filteredItemList);
                updatableItemList = businessOperations.formatAttribute(updatableItemList);
                updatableItemList.forEach(updatableItem -> {
                    businessOperations.updateItem(context, updatableItem);
                });
            }
        } catch (Exception e) {
            UPDATE_PROCESSOR_LOGGER.error(e.getMessage());
        }
    }
}
