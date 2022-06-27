package com.bjit.dw.enovia.validator;

import com.bjit.dw.enovia.model.UpdateItemBean;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Validator {
    private static final Logger VALIDATOR_LOGGER = Logger.getLogger(Validator.class);

    public boolean hasValidWeightUnit(Map<String, String> dwItem) {
        boolean isValid = false;
        if(dwItem.containsKey("WEIGHTUNIT")) {
            if(!dwItem.get("WEIGHTUNIT").equals("kg")) {
                VALIDATOR_LOGGER.info("WEIGHT UPDATE IGNORED ::: LN DW Item '" + dwItem.get("Item") + "' Weight Unit is  ::: " + dwItem.get("WEIGHTUNIT"));
            }
            else {
                isValid = true;
            }
        }
        return isValid;
    }

    public List<UpdateItemBean> validateItemBasedOnWeight(String LNWeight, List<UpdateItemBean> items) {
        if(!NullOrEmptyChecker.isNull(LNWeight)) {
            List<Integer> filterIndex = new ArrayList<>();
            for(int i = 0; i<items.size(); i++) {
                boolean removeWeight = !hasWeightDifference(LNWeight, items.get(i).getAttributes().get("Weight"));
                if(removeWeight) {
                    filterIndex.add(i);
                } else {
                    items.get(i).getAttributes().put("Weight", LNWeight);
                }
            }
            filterIndex.forEach(index -> {
                items.get(index).getAttributes().remove("Weight");
            });
        } else {
            items.forEach(item -> {
                item.getAttributes().remove("Weight");
            });
        }
        return items;
    }

    private boolean hasWeightDifference(String LNWeight, String EnoviaWeight) {
        boolean hasDifference = false;
        if(!NullOrEmptyChecker.isNullOrEmpty(LNWeight) && !NullOrEmptyChecker.isNullOrEmpty(EnoviaWeight)) {
            BigDecimal lnWightValue = new BigDecimal(LNWeight);
            BigDecimal enoviaWeightValue = new BigDecimal(EnoviaWeight);
            if(lnWightValue.compareTo(enoviaWeightValue) != 0) {
                hasDifference = true;
            }
        } else if(NullOrEmptyChecker.isNullOrEmpty(LNWeight) && !NullOrEmptyChecker.isNullOrEmpty(EnoviaWeight)) {
            hasDifference = true;
        } else if(!NullOrEmptyChecker.isNullOrEmpty(LNWeight) && NullOrEmptyChecker.isNullOrEmpty(EnoviaWeight)) {
            hasDifference = true;
        }
        return hasDifference;
    }

    public List<UpdateItemBean> validateItemBasedOnAttribute(String attributeName, String LNAttributeValue, List<UpdateItemBean> items) {
        if(!NullOrEmptyChecker.isNull(LNAttributeValue)) {
            List<Integer> filterIndex = new ArrayList<>();
            for(int i=0; i< items.size(); i++) {
                boolean removeAttribute = !hasAttributeDifference(LNAttributeValue, items.get(i).getAttributes().get(attributeName));
                if(removeAttribute) {
                    filterIndex.add(i);
                }
                else {
                    items.get(i).getAttributes().put(attributeName, LNAttributeValue);
                }
            }
            filterIndex.forEach(index -> {
                items.get(index).getAttributes().remove(attributeName);
            });
        }
        else {
            items.forEach(item -> {
                item.getAttributes().remove(attributeName);
            });
        }
        return items;
    }

    private boolean hasAttributeDifference(String LNAttributeValue, String EnoviaAttributeValue) {
        boolean hasDifference = false;
        if(!NullOrEmptyChecker.isNullOrEmpty(LNAttributeValue) && !NullOrEmptyChecker.isNullOrEmpty(EnoviaAttributeValue)) {
            if(!LNAttributeValue.equals(EnoviaAttributeValue)) {
                hasDifference = true;
            }
        }
        else if(!NullOrEmptyChecker.isNullOrEmpty(LNAttributeValue) && NullOrEmptyChecker.isNullOrEmpty(EnoviaAttributeValue)) {
            hasDifference = true;
        }
        else if(NullOrEmptyChecker.isNullOrEmpty(LNAttributeValue) && !NullOrEmptyChecker.isNullOrEmpty(EnoviaAttributeValue)) {
            hasDifference = true;
        }
        return hasDifference;
    }
}
