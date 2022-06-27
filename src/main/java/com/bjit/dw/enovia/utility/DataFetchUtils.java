package com.bjit.dw.enovia.utility;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFetchUtils {
    public static synchronized List<Map<String, String>> prepareDataListFromResultSet(ResultSet results) {
        List<Map<String, String>> dataMapList = new ArrayList<>();
        try {
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();
            while(results.next()) {
                Map<String, String> dataMap = new HashMap<>();
                for (int i = 1; i<= columnCount; i++) {
                    switch (metaData.getColumnType(i)) {
                        case Types.NVARCHAR:
                        case Types.TIMESTAMP:
                        default:
                            dataMap.put(metaData.getColumnLabel(i), results.getString(i));
                            break;
                        case Types.DECIMAL:
                            dataMap.put(metaData.getColumnLabel(i), results.getBigDecimal(i).setScale(8, BigDecimal.ROUND_HALF_EVEN).toPlainString());
                            break;
                    }
                }
                dataMapList.add(dataMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataMapList;
    }

    public static synchronized String convertItemListAsString(List<String> itemList) {
        String itemListAsString = "";
        StringBuilder itemListStringBuilder = new StringBuilder();
        for (int i = 0; i < itemList.size(); i++) {
            itemListStringBuilder.append("'").append(itemList.get(i)).append("'");
            if (itemList.size() > 1 && i < itemList.size() - 1) {
                itemListStringBuilder.append(",");
            }
        }
        itemListAsString = itemListStringBuilder.toString();
        return itemListAsString;
    }
}
