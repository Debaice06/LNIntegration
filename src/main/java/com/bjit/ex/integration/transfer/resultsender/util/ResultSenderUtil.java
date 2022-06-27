package com.bjit.ex.integration.transfer.resultsender.util;

import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;

import java.util.*;
import java.util.stream.Collectors;

public class ResultSenderUtil {

    private static final org.apache.log4j.Logger RESULT_SENDER_UTIL_LOGGER = org.apache.log4j.Logger.getLogger(ResponseResultSender.class);

    boolean successfullItemMessageFound = false;
    boolean failedItemMessageFound = false;
    boolean unrecognizedMessageFound = false;
    Set<String> uniqueItems = new LinkedHashSet<>();
    Set<ResponseResult> responseResultList = new HashSet<>();
    List<ResponseResult> failedItemResults = new ArrayList<>();
    Map<String, List<String>> failedItemResultMessageMap = new HashMap<>();
    List<ResponseResult> successfulItemResults = new ArrayList<>();
    private String changeActionName = "";

    public void clearResults(){
        this.uniqueItems.clear();
        this.responseResultList.clear();
        this.failedItemResults.clear();
        this.failedItemResultMessageMap.clear();
        this.successfulItemResults.clear();
    }


    public  StringBuilder getEmailBody(List<ResponseResult> results, SenderType senderType) {
//        for (ResponseResult responseResult : results) {
//            String item = responseResult.getItem();
//            if (!uniqueItems.contains(item)) {
//                uniqueItems.add(item);
//                responseResultList.add(responseResult);
//            }
//        }

        for (ResponseResult responseResult : results){
            if (responseResult.isSuccessful() && !uniqueItems.contains(responseResult.getItem())){
                successfulItemResults.add(responseResult);
                uniqueItems.add(responseResult.getItem());
            } else if (!responseResult.isSuccessful() && responseResult.getResultText() != null){
                List<String> msgList = failedItemResultMessageMap.get(responseResult.getItem());
                if (msgList == null){
                    List<String> responseMessageList = new ArrayList<>();
                    responseMessageList.add(responseResult.getResultText());
                    failedItemResultMessageMap.put(responseResult.getItem(), responseMessageList);
                    failedItemResults.add(responseResult);
                } else if (!msgList.contains(responseResult.getResultText())){
                    msgList.add(responseResult.getResultText());
                    failedItemResults.add(responseResult);
                }
            }
        }



        responseResultList.addAll(failedItemResults);
        responseResultList.addAll(successfulItemResults);

        /*for (ResponseResult responseResult : ResponseResultSender.successfulValItemResponseResult) {
            String item = responseResult.getItem();
            if (!uniqueItems.contains(item)) {
                uniqueItems.add(item);
                uniqueItemsResponseResults.add(responseResult);
            }
        }*/

        StringBuilder successfulItemsTable = new StringBuilder("<table border=\"1\">");
        successfulItemsTable.append("<thead>\n"
                + "	<tr>\n"
                + "	  <th align=\"center\">Item Name</th>\n"
                + "	  <th align=\"center\">REV</th>\n"
                + "	  <th align=\"center\">Message</th>\n"
                + "	</tr>\n"
                + "</thead>").append("<tbody>");

        StringBuilder failedItemsTable = new StringBuilder("<table border=\"1\">");
        failedItemsTable.append("<thead>\n"
                + "	<tr>\n"
                + "	  <th align=\"center\">Item Name</th>\n"
                + "	  <th align=\"center\">REV</th>\n"
                + "	  <th align=\"center\">Message</th>\n"
                + "	</tr>\n"
                + "</thead>").append("<tbody>");

        StringBuilder unrecognizedMessageParagraphs = new StringBuilder("<div>");

        int unrecognizedMessageCounter = 1;

        for (ResponseResult uniqueItemsResponseResult : responseResultList) {
            String itemName = uniqueItemsResponseResult.getItem();
            String resultText = uniqueItemsResponseResult.getResultText();
            String revision = uniqueItemsResponseResult.getRevision();

            if (itemName != null && resultText.equalsIgnoreCase("RESULT OK")) {
                successfullItemMessageFound = true;
                successfulItemsTable.append("<tr>" + "<td>" + itemName + "</td><td>" + revision + "</td>" + "<td>Ok</td>" + "</tr>");
            } else if (itemName != null && !resultText.equalsIgnoreCase("RESULT OK")) {
                ResponseResultSender.isAllItemTransfered = false;
                failedItemMessageFound = true;
                failedItemsTable.append("<tr>" + "<td>" + itemName + "</td><td>" + revision + "</td>" + "<td>" + resultText + "</td>" + "</tr>");
            } else {
                ResponseResultSender.isAllItemTransfered = false;
                unrecognizedMessageFound = true;
                unrecognizedMessageParagraphs.append("<p>" + unrecognizedMessageCounter + ". " + resultText + "</p>");
                unrecognizedMessageCounter++;
            }
        }

        successfulItemsTable.append("</tbody>" + "</table>");
        failedItemsTable.append("</tbody>" + "</table>");
        unrecognizedMessageParagraphs.append("</div>");

        StringBuilder emailHtmlBody = new StringBuilder("<html>");
        emailHtmlBody.append("<head>");
        emailHtmlBody.append("<style>");
        emailHtmlBody.append("#messages table {\n"
                + "  border-collapse: collapse;\n"
                + "  border: 1px solid black;\n"
                + "}\n"
                + "#messages table {\n"
                + "  border: 1px solid black;\n"
                + "}\n"
                + "#messages table td{\n"
                + "  border: 1px solid black;\n"
                + "}\n"
                + "#messages table th {\n"
                + "  border: 1px solid black;\n"
                + "}"
                + "table td{\n"
                + "  padding: 4px;\n"
                + "}");

        emailHtmlBody.append("</style>");
        emailHtmlBody.append("</head>");
        emailHtmlBody.append("<body>");
        emailHtmlBody.append("<div id='messages'>");

        if (successfullItemMessageFound) {
            ResponseResultSender.emailIsNecessary = true;
            emailHtmlBody.append("<p>");
            emailHtmlBody.append(ResponseResultSender.enoviaToLnTransferSuccessfulMessageHeader);
            emailHtmlBody.append("</p>");
            emailHtmlBody.append(successfulItemsTable);
        }
        emailHtmlBody.append("</div>");

        if (failedItemMessageFound) {
            ResponseResultSender.emailIsNecessary = true;
            emailHtmlBody.append("<p>");
            emailHtmlBody.append(ResponseResultSender.enoviaToLnTransferErrorMessageHeader);
            emailHtmlBody.append("</p>");
            emailHtmlBody.append(failedItemsTable);
        }
        emailHtmlBody.append("</div>");

        if (unrecognizedMessageFound) {
            ResponseResultSender.emailIsNecessary = true;
            emailHtmlBody.append("<p>");
            emailHtmlBody.append(ResponseResultSender.enoviaToLnTransferUnrecognizedMessageHeader);
            emailHtmlBody.append("</p>");
            emailHtmlBody.append(unrecognizedMessageParagraphs);
        }

        if (!senderType.equals(SenderType.NIGHTLY_TRANSFER_RESPONSE_SENDER)){
            emailHtmlBody.append("<p>");
            emailHtmlBody.append("Setup data : <table>");
            emailHtmlBody.append("<tr>");
            //emailHtmlBody.append("<td>Root item");
            if (ResponseResultSender.title != null && ResponseResultSender.title.trim().length() != 0) {
                emailHtmlBody.append(", title");
            }
            //emailHtmlBody.append("</td><td>: " + ResponseResultSender.rootItemName);
            if (ResponseResultSender.title != null && ResponseResultSender.title.trim().length() != 0) {
                emailHtmlBody.append(", " + ResponseResultSender.title);
            }
            emailHtmlBody.append("</td>");
            emailHtmlBody.append("</tr>");
            if (!"".equals(getChangeActionName())){
                emailHtmlBody.append("<tr><td>Under Change</td>");
                emailHtmlBody.append("<td>: ");
                emailHtmlBody.append(getChangeActionName());
                emailHtmlBody.append("</td>");
                emailHtmlBody.append("</tr>");
            }
            emailHtmlBody.append("</table>");
            emailHtmlBody.append("</br>");
            emailHtmlBody.append("</p>");
        }
        emailHtmlBody.append("</body>");
        emailHtmlBody.append("</html>");

        return emailHtmlBody;
    }

    public String getChangeActionName() {
        return changeActionName;
    }

    public void setChangeActionName(String changeActionName) {
        this.changeActionName = changeActionName;
    }

}