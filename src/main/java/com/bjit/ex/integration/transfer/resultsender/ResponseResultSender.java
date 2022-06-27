package com.bjit.ex.integration.transfer.resultsender;

import com.bjit.ex.integration.model.webservice.Item;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.ItemInfo;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.util.MatrixException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.*;

public abstract class ResponseResultSender {
    public static boolean emailIsNecessary = false;
    //public static Map<String, Map<String, Object>> itemInfoMap = new LinkedHashMap<String, Map<String, Object>>();
    //public static Map<String, List<Object>> itemInfoMapDetails = new LinkedHashMap<String, List<Object>>();

    private static final org.apache.log4j.Logger RESULT_SENDER_LOGGER = org.apache.log4j.Logger.getLogger(ResponseResultSender.class);
    private static String emailSenderKey = "ln.transfer.error.message.sender.email";
    private static String companyNamePropertiesKey = "ln.activationType.company";
    private static String enoviaToLnTransferSuccessfulEmailSubjectKey = "ln.transfer.successful.email.subject";
    private static String enoviaToLnTransferSuccessfulMessageHeaderKey = "ln.transfer.successful.message.header";
    private static String enoviaToLnTransferErrorMessageHeaderKey = "ln.transfer.error.message.header";
    private static String enoviaToLnTransferUnrecognizedMessageHeaderKey = "ln.transfer.unrecognized.message.header";
    //private static String itemExpandLevelKey = "expand.level";

    public static   String project = "";
    public static   String activity = "";
    public static   String changeActionName = "";

    public static String emailSender = "";
    //public static String bomLevel = "";
    //public static String isPurchaseItemStructureSent = "";

    public static String emailSubject = "";
    public static String enoviaToLnTransferSuccessfulMessageHeader = "";
    public static String enoviaToLnTransferErrorMessageHeader = "";
    public static String enoviaToLnTransferUnrecognizedMessageHeader = "";

    //public static String rootItemName = "";
    //public static String rootItemRevision = "";
    public static String title = "";
    public static Boolean isAllItemTransfered = true;
    public static Set<String> recipients = new LinkedHashSet<String>();
    //public static List<ResponseResult> successfulValItemResponseResult = new ArrayList<ResponseResult>();

    public abstract void initializeResultSender(Context context, BusinessObject businessObject);
    public abstract void initializeResultSender();
    public abstract void initializeResultSender(Context context, BusinessObject businessObject,  Item item,boolean isService);

    public abstract void send(List<ResponseResult> results);
    public abstract void sendMultipleMail(Context context, List<ResponseResult> results, HashMap<String, BusinessObject> businessObjectNameMap, HashMap<String, ItemInfo> itemInfoMap ) throws MatrixException;

    public Map<String, Object> sendMail(String from, StringBuilder emailBody) {

        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            String host = "smtp.valmet.com";
            boolean sessionDebug = false;
            Properties props = System.getProperties();

            props.put("mail.host", host);
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.from", from);
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(sessionDebug);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (String receipent : ResponseResultSender.recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(receipent));
            }
            message.setSubject(getMailSubject(emailSubject));
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(emailBody.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            RESULT_SENDER_LOGGER.info("####################################################################");
            RESULT_SENDER_LOGGER.info("######################### SENDING EMAIL ############################");
            RESULT_SENDER_LOGGER.info("######################### HOST : " + host + " ##################");
            RESULT_SENDER_LOGGER.info("######################### SENDER : " + from + " ################");
            RESULT_SENDER_LOGGER.info("######################### RECEIVER : " + recipients + " ################");
            RESULT_SENDER_LOGGER.info("######################### SUBJECT : " + message.getSubject() + " ################");
            Transport.send(message);
            RESULT_SENDER_LOGGER.info("######################   RESULT : EMAIL SENT  ######################");
            RESULT_SENDER_LOGGER.info("####################################################################");
            RESULT_SENDER_LOGGER.info("####################################################################");
            resultMap.put("sendResult", "Successfully Email Sent.");

        } catch (MessagingException ex) {
            resultMap.put("sendResult", "Failed To Send Email. Cause: " + ex.getMessage());
        }

        return resultMap;
    }

    private String getMailSubject(String subject){
        StringBuilder mailSubBuilder = new StringBuilder();
        String env = ApplicationProperties.getProprtyValue("info.enovia.environment");
        if (env != null && !"".equals(env.trim())){
            mailSubBuilder.append(env);
            mailSubBuilder.append(": ");
        }
        mailSubBuilder.append(subject);
        mailSubBuilder.append(" ");
        mailSubBuilder.append(!"".equals(project)?"Project: ".concat(project): "");
        mailSubBuilder.append(!"".equals(project)?" ": "");
        mailSubBuilder.append(!"".equals(activity)?"Activity: ".concat(activity): "");
        return mailSubBuilder.toString();
    }

}
