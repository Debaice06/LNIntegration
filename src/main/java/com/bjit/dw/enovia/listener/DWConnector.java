package com.bjit.dw.enovia.listener;

import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DWConnector {
    private static final Logger DW_CONNECTOR_LOGGER = Logger.getLogger(DWConnector.class);

    private static DWConnector instance;
    private final Connection connection;

    private DWConnector() throws SQLException {
        Instant startConnectionTime = Instant.now();
        try {
            Map<String, String> dwInfo = validateCredentials();
            Properties credentials = new Properties();
            credentials.setProperty("user", dwInfo.get("user"));
            credentials.setProperty("password", dwInfo.get("password"));
            credentials.setProperty("databaseName", dwInfo.get("schema"));

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            connection = DriverManager.getConnection(dwInfo.get("url"), credentials);
        } catch (SQLException e) {
            DW_CONNECTOR_LOGGER.error("DW Connection Creation Failed : " + e.getMessage());
            throw e;
        } finally {
            Instant endConnectionTime = Instant.now();
            Duration timeTakenForConnection = Duration.between(startConnectionTime, endConnectionTime);
            DW_CONNECTOR_LOGGER.info("Time taken for DW Connection :" + timeTakenForConnection.toMillis());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DWConnector getInstance() throws SQLException {
        if(instance == null) {
            instance = new DWConnector();
        }
        else if (instance.getConnection().isClosed()) {
            instance = new DWConnector();
        }
        return instance;
    }

    public void closeConnection() throws SQLException  {
        if (instance.getConnection() != null && !instance.getConnection().isClosed()) {
            instance.getConnection().close();
        }
    }

    public Map<String, String> validateCredentials() throws RuntimeException {
        Map<String, String> validatedCredentials = new HashMap<>();
        validatedCredentials.put("url", ApplicationProperties.getProprtyValue("dw.server.url"));
        validatedCredentials.put("schema", ApplicationProperties.getProprtyValue("dw.server.schema"));
        validatedCredentials.put("user", ApplicationProperties.getProprtyValue("dw.server.user"));
        validatedCredentials.put("password", ApplicationProperties.getProprtyValue("dw.server.password"));
        if(validatedCredentials.get("user") == null || validatedCredentials.get("user").isEmpty()) {
            DW_CONNECTOR_LOGGER.error("dw 'user' property value should not be empty");
            throw new RuntimeException("dw 'user' property value should not be empty");
        }
        if(validatedCredentials.get("password") == null || validatedCredentials.get("password").isEmpty()) {
            DW_CONNECTOR_LOGGER.error("dw 'password' property value should not be empty");
            throw new RuntimeException("dw 'password' property value should not be empty");
        }
        if(validatedCredentials.get("schema") == null || validatedCredentials.get("schema").isEmpty()) {
            DW_CONNECTOR_LOGGER.error("dw 'schema' property value should not be empty");
            throw new RuntimeException("dw 'schema' property value should not be empty");
        }
        if(validatedCredentials.get("url") == null || validatedCredentials.get("url").isEmpty()) {
            DW_CONNECTOR_LOGGER.error("dw 'url' property value should not be empty");
            throw new RuntimeException("dw 'url' property value should not be empty");
        }
        return validatedCredentials;
    }
}
