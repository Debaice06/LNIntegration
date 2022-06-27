package com.bjit.dw.enovia.utility;

import com.bjit.dw.enovia.model.WeightData;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.project_structure.utilities.DisableSSLCertificate;
import com.bjit.project_structure.utilities.JSON;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeightUtil {
    private static final org.apache.log4j.Logger WEIGHT_UTIL_LOGGER = org.apache.log4j.Logger.getLogger(WeightUtil.class);

    public WeightUtil() throws Exception {
        WEIGHT_UTIL_LOGGER.info(":: LN DW-ENOVIA UTILITY WEIGHT SERVICE- Started ::");
    }

    HttpURLConnection urlConnection = null;
    String serviceURL = ApplicationProperties.getProprtyValue("ln.project.or.task.or.milestone.fetching.url");
    String company = ApplicationProperties.getProprtyValue("weight.data.json.query.ln.company");
    String productType = ApplicationProperties.getProprtyValue("weight.data.json.query.product.type");
    String sender = ApplicationProperties.getProprtyValue("weight.data.json.query.sender");
    String receiver = ApplicationProperties.getProprtyValue("weight.data.json.query.receiver");
    String query = ApplicationProperties.getProprtyValue("weight.data.json.query.string");

    public static String response = "";


    public List<HashMap<String, String>> getResult(String startDateTime, String endDateTime) throws Exception {
        DisableSSLCertificate.DisableCertificate();
        List<HashMap<String, String>> list = null;
        try {
            WEIGHT_UTIL_LOGGER.info("URL:: "+serviceURL);

            BufferedReader responseReader = (BufferedReader) getResponse(serviceURL, startDateTime, endDateTime);
            list = parseResponseResult(responseReader);
            responseReader.close();
            return list;
        } catch (IOException exp) {
            WEIGHT_UTIL_LOGGER.error(exp.getMessage());
            throw exp;
        }
    }

    public Reader getResponse(String serviceURL, String startDateTime, String endDateTime) throws MalformedURLException, IOException {
        Reader reader = null;
        ZonedDateTime startTime = null;
        try {
            startTime = ZonedDateTime.now();
            WEIGHT_UTIL_LOGGER.info("Weight service started: "+ startTime);

            URL url = new URL(serviceURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setRequestProperty("Content-Type", "application/json");

            String requestBody = MessageFormat.format(query, startDateTime, endDateTime, company, productType, sender, receiver);
            System.out.println(requestBody);
            OutputStream os = urlConnection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(requestBody);
            osw.flush();
            osw.close();
            os.close();

            if (urlConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + urlConnection.getResponseCode());
            }
            WEIGHT_UTIL_LOGGER.info("Reading data from '" + serviceURL + "'");
            reader = new BufferedReader(new InputStreamReader(
                    (urlConnection.getInputStream())));
        } catch (MalformedURLException e) {
            WEIGHT_UTIL_LOGGER.trace(e);
            throw e;
        } catch (IOException e) {
            WEIGHT_UTIL_LOGGER.trace(e);
            throw e;
        } finally {
            ZonedDateTime pdmServiceRequestEndTime = ZonedDateTime.now();
            WEIGHT_UTIL_LOGGER.info("Service call ended at: "+pdmServiceRequestEndTime);
            Duration serviceElapsedTime = Duration.between(startTime,pdmServiceRequestEndTime);
            WEIGHT_UTIL_LOGGER.info("Total time taken: "+serviceElapsedTime.toMillis()+ " millisecond");
        }
        return reader;
    }

    private List<HashMap<String, String>> parseResponseResult(BufferedReader responseReader) throws Exception {
        try {
            final StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = responseReader.readLine()) != null) {
                jsonString.append(line);
            }
            String trimmedResponse = jsonString.toString().trim();
            JSONObject jsonObject = JSONObject.fromObject(trimmedResponse);
            String jsonStringData = jsonObject.toString();
            WEIGHT_UTIL_LOGGER.info("Weight Resp: " + jsonStringData);
            JSON jsonizer = new JSON();
            WeightData data = jsonizer.deserialize(jsonStringData, WeightData.class);
            if(data.getError() != null){
                HashMap<String, String> map = new HashMap();
                List<HashMap<String, String>> errorList = new ArrayList();
                map.put("Error", data.getError());
                errorList.add(map);
                return errorList;
            }
            return data.getData();
        } catch (Exception exp) {
            WEIGHT_UTIL_LOGGER.error(exp.getMessage());
            throw new Exception("May be service '" + serviceURL + "' is down now. Error : " + exp);
        }
    }
}
