package com.bjit.dw.enovia.action;

import com.bjit.dw.enovia.listener.DWConnector;
import com.bjit.dw.enovia.utility.DataFetchUtils;
import com.bjit.dw.enovia.validator.DataValidator;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class DWDataFetchAction {
    private static final Logger DW_DATA_FETCH_ACTION_LOGGER = Logger.getLogger(DWDataFetchAction.class);

    public List<Map<String, String>> fetchDWData(List<String> itemList, String lastExecutionDateTime) throws RuntimeException, SQLException {
        DataValidator.validateRequestedItemList(itemList);
        DataValidator.validateRequestedLastExecutionDateTime(lastExecutionDateTime);

        String itemListAsString = DataFetchUtils.convertItemListAsString(itemList);

        ResultSet results = performDataFetchQueryByItemListAndExecutionTime(itemListAsString, lastExecutionDateTime);
        List<Map<String, String>> resultMapList = DataFetchUtils.prepareDataListFromResultSet(results);
        DWConnector.getInstance().closeConnection();
        return resultMapList;
    }

    public List<Map<String, String>> fetchDWData(String lastExecutionDateTime) throws RuntimeException, SQLException{
        DataValidator.validateRequestedLastExecutionDateTime(lastExecutionDateTime);

        ResultSet results = performDataFetchQueryByExecutionTime(lastExecutionDateTime);
        List<Map<String, String>> resultMapList = DataFetchUtils.prepareDataListFromResultSet(results);
        DWConnector.getInstance().closeConnection();
        return resultMapList;
    }

    private ResultSet performDataFetchQueryByExecutionTime(String lastExecutionDateTime) throws SQLException {
        Connection connection = null;
        try {
            connection = DWConnector.getInstance().getConnection();
        } catch(SQLException e) {
            DW_DATA_FETCH_ACTION_LOGGER.error(e.getMessage());
            throw e;
        }
        Instant startDWQuery = Instant.now();
        ResultSet results = null;
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(
                    MessageFormat.format(
                            ApplicationProperties.getProprtyValue("dw.sql.sub.query"),
                            ApplicationProperties.getProprtyValue("dw.sql.query.ln.company"),
                            lastExecutionDateTime
                    )
            );

            String query = queryBuilder.toString();
            Statement statement = connection.createStatement();
            results = statement.executeQuery(query);
        } catch (SQLException e) {
            DW_DATA_FETCH_ACTION_LOGGER.error(e.getMessage());
            throw e;
        } finally {
            Instant endDWQuery = Instant.now();
            Duration timeTakenForQuery = Duration.between(startDWQuery, endDWQuery);
            DW_DATA_FETCH_ACTION_LOGGER.info("Time Taken for DW Query : " + timeTakenForQuery.toMillis());
        }
        return results;
    }

    private ResultSet performDataFetchQueryByItemListAndExecutionTime(String itemListAsString, String lastExecutionDateTime) throws SQLException {
        Connection connection = null;
        try {
            connection = DWConnector.getInstance().getConnection();
        } catch(SQLException e) {
            DW_DATA_FETCH_ACTION_LOGGER.error(e.getMessage());
            throw e;
        }
        Instant startDWQuery = Instant.now();
        ResultSet results = null;
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(
                            MessageFormat.format(
                                    ApplicationProperties.getProprtyValue("dw.sql.sub.query"),
                                    ApplicationProperties.getProprtyValue("dw.sql.query.ln.company"),
                                    lastExecutionDateTime
                            )
                    )
                    .append(" ")
                    .append(
                            MessageFormat.format(
                                    ApplicationProperties.getProprtyValue("dw.sql.sub.query.where.item"),
                                    itemListAsString
                            )
                    );

            String query = queryBuilder.toString();
            Statement statement = connection.createStatement();
            results = statement.executeQuery(query);
        } catch (SQLException e) {
            DW_DATA_FETCH_ACTION_LOGGER.error(e.getMessage());
            throw e;
        } finally {
            Instant endDWQuery = Instant.now();
            Duration timeTakenForQuery = Duration.between(startDWQuery, endDWQuery);
            DW_DATA_FETCH_ACTION_LOGGER.info("Time Taken for DW Query : " + timeTakenForQuery.toMillis());
        }
        return results;
    }
}
