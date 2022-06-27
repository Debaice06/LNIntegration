package com.bjit.dw.enovia.ex.dw.service;

import com.bjit.dw.enovia.ex.dw.model.DWResponseBean;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import com.bjit.project_structure.utilities.JSON;
import com.bjit.project_structure.utilities.ObjectUtility;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DWServiceRequester {
    private static final Logger DW_DATA_FETCH_LOGGER = Logger.getLogger(DWServiceRequester.class);

    public List<HashMap<String, String>> fetchLNDWItemListNightly(String lastExecutionDateTime) throws Exception {
        try {
            validate(lastExecutionDateTime, "lastExecutionDateTime");
            Map<String, String> headers = new HashMap<>();
            headers.put("lastExecutionDateTime", lastExecutionDateTime);
            DW_DATA_FETCH_LOGGER.info(":: Fetching DW Data ::");
            String url = ApplicationProperties.getProprtyValue("dw.data.fetch.service.url");
            DW_DATA_FETCH_LOGGER.info("REQUEST URL: " + url);
            DW_DATA_FETCH_LOGGER.info("HEADER: lastExecutionDateTime " + lastExecutionDateTime);
            String serviceResponse = callService(url, "GET", headers, null, null);
            String trimmedResponse = serviceResponse.trim();
            JSONObject jsonObject = JSONObject.fromObject(trimmedResponse);
            String jsonStringData = jsonObject.toString();
            DW_DATA_FETCH_LOGGER.info("DW RESPONSE: " + jsonStringData);
            JSON jsonizer = new JSON();
            DWResponseBean dwResponse = jsonizer.deserialize(jsonStringData, DWResponseBean.class);
            if(!NullOrEmptyChecker.isNullOrEmpty(dwResponse.getStatus()) && dwResponse.getStatus().equals("FAILED")) {
                if(!NullOrEmptyChecker.isNullOrEmpty(dwResponse.getSystemErrors())) {
                    String error = String.join(" " , dwResponse.getSystemErrors());
                    throw new Exception(error);
                }
            }
            return dwResponse.getData();
        } catch (Exception e) {
            DW_DATA_FETCH_LOGGER.error("Error occurred while fetching DW Data: " + e.getMessage());
            throw e;
        }
    }

    public String callService(String url, String method, Map<String, String> headers, Map<String, String> params, String requestBody) throws Exception {
        String response = null;
            validate(url, "URL");
            URL serviceURL;
            // Query Parameters
            if (!NullOrEmptyChecker.isNullOrEmpty(params)) {
                StringBuilder serviceURLWithParamsBuilder = new StringBuilder(url);
                serviceURLWithParamsBuilder.append("?");
                Set<String> keySet = params.keySet();
                Iterator<String> keyIterator = keySet.iterator();
                while (keyIterator.hasNext()) {
                    String paramKey = keyIterator.next();
                    String paramValue = params.get(paramKey);
                    serviceURLWithParamsBuilder.append(paramKey + "=" + paramValue);
                    if (keyIterator.hasNext()) {
                        serviceURLWithParamsBuilder.append("&");
                    }
                }
                serviceURL = new URL(serviceURLWithParamsBuilder.toString());
            } else {
                serviceURL = new URL(url);
            }

            validate(method, "Method");
            HttpURLConnection httpURLConnection = (HttpURLConnection) serviceURL.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            // Headers
            if(!NullOrEmptyChecker.isNullOrEmpty(headers)) {
                headers.forEach(httpURLConnection::setRequestProperty);
            }
            // Request Body
            if(method.equals("POST")) {
                setRequestBody(httpURLConnection, requestBody);
            }
            // Gathering Response
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode >= 300)  {
                String errorMessage = getResponse(httpURLConnection.getErrorStream());
                try {
                    String trimmedResponse = errorMessage.trim();
                    JSONObject jsonObject = JSONObject.fromObject(trimmedResponse);
                    String jsonStringData = jsonObject.toString();
                    JSON jsonizer = new JSON();
                    DWResponseBean dwResponse = jsonizer.deserialize(jsonStringData, DWResponseBean.class);
                    if(!NullOrEmptyChecker.isNullOrEmpty(dwResponse.getStatus()) && dwResponse.getStatus().equals("FAILED")) {
                        if(!NullOrEmptyChecker.isNullOrEmpty(dwResponse.getSystemErrors())) {
                            String error = String.join(" " , dwResponse.getSystemErrors());
                            throw new Exception(error);
                        }
                    } else {
                        throw new Exception(errorMessage);
                    }
                } catch(Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
            response = getResponse(httpURLConnection.getInputStream());
            if (!NullOrEmptyChecker.isNull(httpURLConnection)) {
                httpURLConnection.disconnect();
            }
        return response;
    }

    private String getResponse(InputStream inputStream) throws Exception {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuilder jsonString = new StringBuilder();
            String output;
            while ((output = bufferedReader.readLine()) != null) {
                jsonString.append(output);
            }
            bufferedReader.close();
            return jsonString.toString();
        } catch (Exception exp) {
            throw exp;
        }
    }

    private void setRequestBody(HttpURLConnection httpUrlConnection, String bodyData) throws UnsupportedEncodingException, IOException, NullPointerException {
        this.validate(bodyData, "Request body");
        byte[] outputInBytes = bodyData.getBytes(StandardCharsets.UTF_8);
        try (OutputStream outputStream = httpUrlConnection.getOutputStream()) {
            outputStream.write(outputInBytes);
            outputStream.flush();
        } catch (Exception exp) {
            throw exp;
        }
    }

    private void validate(String property, String propertyType) throws NullPointerException {
        if (ObjectUtility.isNullOrEmpty(property)) {
            throw new NullPointerException(propertyType + " is empty");
        }
    }
}
