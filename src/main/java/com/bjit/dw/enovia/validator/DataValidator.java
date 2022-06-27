package com.bjit.dw.enovia.validator;

import java.util.List;

public class DataValidator {
    public static synchronized void validateRequestedItemList(List<String> itemList) throws RuntimeException {
        if (itemList == null || itemList.isEmpty()) {
            throw new RuntimeException("Item List couldn't be null or empty!");
        }
    }

    public static synchronized void validateRequestedLastExecutionDateTime(String lastExecutionDateTime) throws RuntimeException {
        if (lastExecutionDateTime == null || lastExecutionDateTime.isEmpty()) {
            throw new RuntimeException("Last Execution Date Time couldn't be empty!");
        }
    }
}
