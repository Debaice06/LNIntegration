package com.bjit.dw.enovia.action.processors;

import com.bjit.dw.enovia.model.UpdateItemBean;
import com.bjit.dw.enovia.utility.ItemSearchUtils;
import com.bjit.dw.enovia.validator.Validator;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import com.matrixone.apps.domain.util.MqlUtil;
import matrix.db.Context;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessOperations {
    private static final Logger BUSINESS_OPERATIONS_LOGGER = Logger.getLogger(BusinessOperations.class);
    private final MapperProcessor mapperProcessor;
    private final Validator validator;

    public BusinessOperations() {
        this.mapperProcessor = new MapperProcessor();
        this.validator = new Validator();
    }

    public List<UpdateItemBean> filterDWDataForUpdate(List<HashMap<String, String>> dwDataList) {
        List<UpdateItemBean> updateItemList = new ArrayList<>();
        dwDataList.forEach((dwData) -> {
            Map<String, String> attributes = new HashMap<>();
            if(validator.hasValidWeightUnit(dwData)) {
                attributes.put("WEIGHT", dwData.get("WEIGHT"));
                attributes.put("WEIGHTUNIT", dwData.get("WEIGHTUNIT"));
            }
            if(dwData.containsKey("RefItem") && dwData.containsKey("CommodityCode") && dwData.containsKey("CommodityCodeUS") && dwData.containsKey("CommodityCodeCN")) {
                attributes.put("RefItem", dwData.get("RefItem"));
                attributes.put("CommodityCode", dwData.get("CommodityCode"));
                attributes.put("CommodityCodeUS", dwData.get("CommodityCodeUS"));
                attributes.put("CommodityCodeCN", dwData.get("CommodityCodeCN"));
            }
            if(!attributes.isEmpty()) {
                UpdateItemBean item = new UpdateItemBean();
                item.setName(formatName(dwData.get("Item")));
                item.setAttributes(attributes);
                updateItemList.add(item);
            }
        });
        return updateItemList;
    }

    public List<UpdateItemBean> checkItemExistInEnovia(Context context, List<UpdateItemBean> LNItems) {
        List<UpdateItemBean> validExistedItems = new ArrayList<>();
        if(!NullOrEmptyChecker.isNullOrEmpty(LNItems)) {
            String type = mapperProcessor.getEnoviaUpdateAllowedTypes();
            String selectable = getSearchSelectable();
            ItemSearchUtils itemSearch = new ItemSearchUtils();
            LNItems.forEach(LNItem -> {
                String name = LNItem.getName();
                List<UpdateItemBean> searchedResult = itemSearch.search(context, type, name, selectable);
                List<UpdateItemBean> validItems = validator.validateItemBasedOnWeight(LNItem.getAttributes().get("Weight"), searchedResult);
                validItems = updateAttribute(LNItem, searchedResult);
                validExistedItems.addAll(validItems);
            });
        }
        return validExistedItems;
    }

    public List<UpdateItemBean> formatAttribute(List<UpdateItemBean> itemList) {
        Map<String, Map<String, String>> mappingAttributeMap = mapperProcessor.getMappingAttributeMap();
        itemList.forEach(item -> {
            Map<String, String> attributeList = mappingAttributeMap.get(item.getType());
            Map<String, String> formattedAttributeList = new HashMap<>();
            item.getAttributes().forEach((key, value) -> {
                if(attributeList.containsKey(key)) {
                    formattedAttributeList.put(attributeList.get(key), value);
                }
            });
            item.setAttributes(formattedAttributeList);
        });
        return itemList;
    }

    public void updateItem(Context context, UpdateItemBean updateItemBean) {
        try {
            if(!NullOrEmptyChecker.isNullOrEmpty(updateItemBean.getAttributes())) {
                BUSINESS_OPERATIONS_LOGGER.info("Executing MQL Update on " + updateItemBean.getType() + " " + updateItemBean.getName() + " " + updateItemBean.getRevision());
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("mod bus ")
                        .append(updateItemBean.getObjectId()).append(" ");
                updateItemBean.getAttributes().forEach((key, value) -> {
                    queryBuilder.append("'").append(key).append("' ")
                            .append("'").append(value).append("' ");
                });

                String mqlQuery = queryBuilder.toString();
                BUSINESS_OPERATIONS_LOGGER.info(mqlQuery);

//                String queryResult = MqlUtil.mqlCommand(context, mqlQuery);
            }
        } catch (Exception e) {
            BUSINESS_OPERATIONS_LOGGER.error(e.getMessage());
        }
    }

    private String formatName(String name) {
        String[] splitName = name.split("-");
        if(!NullOrEmptyChecker.isNullOrEmpty(splitName)) {
            if(splitName.length == 3) {
                String formattedPrefix = splitName[0].toLowerCase();
                name = formattedPrefix + name.substring(name.indexOf("-"));
            }
        }
        return name;
    }

    private String getSearchSelectable() {
        StringBuilder selectableBuilder = new StringBuilder();
        selectableBuilder.append("select id ")
                .append(String.join(" " , mapperProcessor.getAttributeSelectables()));
        return selectableBuilder.toString();
    }

    private List<UpdateItemBean> updateAttribute(UpdateItemBean LNItem, List<UpdateItemBean> EnoviaItemList) {
        Map<String,String> lnAttributes = LNItem.getAttributes();
        if(lnAttributes.containsKey("CommodityCode") && lnAttributes.containsKey("CommodityCodeUS") && lnAttributes.containsKey("CommodityCodeCN") && lnAttributes.containsKey("RefItem")) {
            EnoviaItemList.forEach(enoviaItem -> {
                Map<String, String> attributes = enoviaItem.getAttributes();
                attributes.put("CommodityCode", LNItem.getAttributes().get("CommodityCode"));
                attributes.put("CommodityCodeUS", LNItem.getAttributes().get("CommodityCodeUS"));
                attributes.put("CommodityCodeCN", LNItem.getAttributes().get("CommodityCodeCN"));
                attributes.put("RefItem", LNItem.getAttributes().get("RefItem"));
            });
        }
        if(lnAttributes.containsKey("WEIGHTUNIT") && lnAttributes.containsKey("WEIGHT")) {
            EnoviaItemList.forEach(enoviaItem -> {
                Map<String, String> attributes = enoviaItem.getAttributes();
                attributes.put("WEIGHT", LNItem.getAttributes().get("WEIGHT"));
                attributes.put("WEIGHTUNIT", LNItem.getAttributes().get("WEIGHTUNIT"));
            });
        }
        return EnoviaItemList;
    }

    private boolean hasWeightDifference(UpdateItemBean lnItem, UpdateItemBean enoviaItem) {
        boolean hasDifference = true;
        String lnWeight = lnItem.getAttributes().get("WEIGHT");
        String enoviaWeight = enoviaItem.getAttributes().get("WEIGHT");
        return hasDifference;
    }
}
