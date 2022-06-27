package com.bjit.dw.enovia.datafetcher;

import com.bjit.dw.enovia.listener.DWConnector;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DWDataFetcher {
    public List<Map<String, String>> fetchDWData(List<String> itemList, String lastExecutionDateTime) throws RuntimeException, SQLException {
        List<Map<String, String>> resultMapList = null;
        if(lastExecutionDateTime != null && !lastExecutionDateTime.isEmpty()) {
            if (itemList != null && !itemList.isEmpty()) {
                String itemListAsString = "";
                StringBuilder itemListStringBuilder = new StringBuilder();
                for (int i = 0; i < itemList.size(); i++) {
                    itemListStringBuilder.append("'").append(itemList.get(i)).append("'");
                    if (itemList.size() > 1 && i < itemList.size() - 1) {
                        itemListStringBuilder.append(",");
                    }
                }
                itemListAsString = itemListStringBuilder.toString();

                Connection connection = DWConnector.getInstance().getConnection();
                ResultSet results = performDataFetchQueryByItemListAndExecutionTime(connection, itemListAsString, lastExecutionDateTime);
                resultMapList = convertResultIntoMapList(connection, results);
                return resultMapList;
            } else {
                throw new RuntimeException("Item List couldn't be empty!");
            }
        }
        else {
            throw new RuntimeException("Last Execution Date Time couldn't be empty!");
        }
    }

    public List<Map<String, String>> fetchDWData(String lastExecutionDateTime) throws Exception{
        List<Map<String, String>> resultMapList = null;
        if(lastExecutionDateTime != null && !lastExecutionDateTime.isEmpty()) {
            Connection connection = DWConnector.getInstance().getConnection();
            ResultSet results = performDataFetchQueryByExecutionTime(connection, lastExecutionDateTime);
            resultMapList = convertResultIntoMapList(connection, results);
            return resultMapList;
        }
        else {
            throw new Exception("Last Execution Date Time couldn't be empty!");
        }
    }

    private ResultSet performDataFetchQueryByExecutionTime(Connection connection, String lastExecutionDateTime) {
        ResultSet results = null;

        if (connection != null) {
            try {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT [Item], [Weight], [CommodityCode], [CommodityCodeUS], [CommodityCodeCN], [RefItem], [CommodityCode_Changed], [CommodityCodeUS_Changed], [CommodityCodeCN_Changed], [RefItem_Changed] FROM [LN].[DW_COM_ITEMS_LN] WHERE LogComp IN ('1001')");

                queryBuilder.append(" AND ( ")
                        .append("CommodityCode_Changed > ").append("'").append(lastExecutionDateTime).append("' ")
                        .append(" OR ").append("CommodityCodeUS_Changed > ").append("'").append(lastExecutionDateTime).append("' ")
                        .append(" OR ").append("CommodityCodeCN_Changed > ").append("'").append(lastExecutionDateTime).append("' ")
                        .append(" OR ").append("RefItem_Changed > ").append("'").append(lastExecutionDateTime).append("' ")
                        .append(" )");

                String query = queryBuilder.toString();
                Statement statement = connection.createStatement();
                results = statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private ResultSet performDataFetchQueryByItemListAndExecutionTime(Connection connection, String itemListAsString, String lastExecutionDateTime) {
        ResultSet results = null;

        if (connection != null) {
            try {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT [Item], [Weight], [CommodityCode], [CommodityCodeUS], [CommodityCodeCN], [RefItem], [CommodityCode_Changed], [CommodityCodeUS_Changed], [CommodityCodeCN_Changed], [RefItem_Changed] FROM [LN].[DW_COM_ITEMS_LN] WHERE LogComp IN ('1001')");

                queryBuilder.append(" AND ( ")
                        .append("CommodityCode_Changed > ").append("'").append(lastExecutionDateTime).append("' ")
                        .append(" OR ").append("CommodityCodeUS_Changed > ").append("'").append(lastExecutionDateTime).append("' ")
                        .append(" OR ").append("CommodityCodeCN_Changed > ").append("'").append(lastExecutionDateTime).append("' ")
                        .append(" OR ").append("RefItem_Changed > ").append("'").append(lastExecutionDateTime).append("' ")
                        .append(" )");

                queryBuilder.append(" AND ").append("Item IN ").append("( ").append(itemListAsString).append(" )");

                String query = queryBuilder.toString();
                Statement statement = connection.createStatement();
                results = statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private List<Map<String, String>> convertResultIntoMapList(Connection connection, ResultSet results) {
        List<Map<String, String>> itemMapList = new ArrayList<>();
        try {
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();
            while(results.next()) {
                Map<String, String> resultObject = new HashMap<>();
                for (int i = 1; i<= columnCount; i++) {
                    switch (metaData.getColumnType(i)) {
                        case Types.NVARCHAR:
                        case Types.TIMESTAMP:
                        default:
                            resultObject.put(metaData.getColumnLabel(i), results.getString(i));
                            break;
                        case Types.DECIMAL:
                            resultObject.put(metaData.getColumnLabel(i), results.getBigDecimal(i).setScale(8, BigDecimal.ROUND_HALF_EVEN).toPlainString());
                            break;
                    }
                }
                itemMapList.add(resultObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return itemMapList;
    }
}