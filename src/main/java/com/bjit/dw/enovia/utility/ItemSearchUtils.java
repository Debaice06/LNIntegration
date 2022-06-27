package com.bjit.dw.enovia.utility;

import com.bjit.dw.enovia.model.UpdateItemBean;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import com.matrixone.apps.domain.util.MqlUtil;
import matrix.db.Context;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ItemSearchUtils {
    private static final Logger ITEM_SEARCH_LOGGER = Logger.getLogger(ItemSearchUtils.class);

    public List<UpdateItemBean> search(Context context, String type, String name, String selectable) {
        List<UpdateItemBean> searchResult = new ArrayList<>();
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("temp query bus ")
                    .append(type +" ")
                    .append(name + " ")
                    .append("* ")
                    .append("where 'vault==\"vplm\"' ")
                    .append(selectable + " ")
                    .append("dump |");

            String mqlQuery = queryBuilder.toString();
            String queryResult = MqlUtil.mqlCommand(context, mqlQuery);

            String[] splitedQueryResult = queryResult.split("\n");
            for (String result : splitedQueryResult) {
                String[] splitedResult = result.split(Pattern.quote("|"), -1);
                if (splitedResult.length == 10) {
                    UpdateItemBean updateItem = new UpdateItemBean();
                    updateItem.setType(splitedResult[0].trim());
                    updateItem.setName(splitedResult[1].trim());
                    updateItem.setRevision(splitedResult[2].trim());
                    updateItem.setObjectId(splitedResult[3].trim());
                    /*updateItem.getAttributes().put("RefItem", splitedResult[7].trim());
                    updateItem.getAttributes().put("CommodityCode", splitedResult[6].trim());
                    updateItem.getAttributes().put("CommodityCodeUS", splitedResult[8].trim());
                    updateItem.getAttributes().put("CommodityCodeCN", splitedResult[4].trim());
                    String weight;
                    String resultWeight1 = splitedResult[5].trim();
                    String resultWeight2 = splitedResult[9].trim();
                    if(!NullOrEmptyChecker.isNullOrEmpty(resultWeight1)) {
                        weight = resultWeight1;
                    } else if(!NullOrEmptyChecker.isNullOrEmpty(resultWeight2)) {
                        weight = resultWeight2;
                    } else {
                        weight = "";
                    }
                    updateItem.getAttributes().put("WEIGHT", weight);*/
                    searchResult.add(updateItem);
                }
            }
        } catch (Exception e) {
            ITEM_SEARCH_LOGGER.error(e.getMessage());
        }
        return searchResult;
    }
}
