/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities;

import com.bjit.ex.integration.model.webservice.TNR;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import com.matrixone.apps.domain.util.FrameworkException;
import com.matrixone.apps.domain.util.MqlUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import matrix.db.*;
import matrix.db.Attribute;
import matrix.db.BusinessObject;
import matrix.db.BusinessObjectWithSelect;
import matrix.db.Context;
import matrix.util.MatrixException;
import matrix.util.SelectList;
import matrix.util.StringList;

import static java.util.stream.Collectors.toMap;
import matrix.util.Pattern;

/**
 *
 * @author 88017
 */
public class BusinessObjectUtils {

    private static final org.apache.log4j.Logger BUSINESS_OBJECTS_UTILS_LOGGER = org.apache.log4j.Logger.getLogger(BusinessObjectUtils.class);

    public static Map<String, Object> getObjectInfo(Context context, BusinessObject businessObject) throws MatrixException {
        Map<String, Object> objectInfo = new HashMap<>();
        businessObject.open(context);
        List<Attribute> attributes = businessObject.getAttributeValues(context);

        SelectList sList = new SelectList();
        for (Attribute attribute : attributes) {
            sList.addAttribute(attribute.getName());
        }
        BusinessObjectWithSelect busWithSelect = businessObject.select(context, sList);
        for (int i = 0; i < attributes.size(); i++) {
            String selectData = busWithSelect.getSelectData((String) sList.get(i));
            objectInfo.put(attributes.get(i).getName(), selectData);
        }
        return objectInfo;
    }

    public static Map<String, Object> getObjectInfoByAttributeList(Context context, BusinessObject businessObject, List<String> attributes) throws MatrixException {
        Map<String, Object> objectInfo = new HashMap<>();
        businessObject.open(context);

        SelectList sList = new SelectList();
        for (String attribute : attributes) {
            sList.addAttribute(attribute);
        }
        BusinessObjectWithSelect busWithSelect = businessObject.select(context, sList);
        for (int i = 0; i < attributes.size(); i++) {
            String selectData = busWithSelect.getSelectData((String) sList.get(i));
            objectInfo.put(attributes.get(i), selectData);
        }
        return objectInfo;
    }

    public static Set<String> getRecipientsByOwnerName(Context context, String ownerName) {
        Set<String> recipients = new LinkedHashSet<String>();
        try {
            String revision = "";
            String vault = "";
            List<String> attributeList = new ArrayList<String>();
            attributeList.add("Email Address");

            Map<String, String> personInfo = getPersonInfo(context, ownerName, revision, vault, attributeList);
            String emailAddress = personInfo.get("Email Address");
            BUSINESS_OBJECTS_UTILS_LOGGER.error("Email Address :: " + emailAddress);
            recipients.add(emailAddress);
            return recipients;
        } catch (MatrixException ex) {
            //Logger.getLogger(ResponseResultSenderImpl.class.getName()).log(Level.SEVERE, null, ex);
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex.getMessage());
        }
        return recipients;
    }

    public static String getPersonHomeNumber(Context context, String ownerName) {
        try {
            String revision = "";
            String vault = "";
            List<String> attributeList = new ArrayList<String>();
            attributeList.add("Home Phone Number");

            Map<String, String> personInfo = getPersonInfo(context, ownerName, revision, vault, attributeList);
            String homePhoneNo = personInfo.get("Home Phone Number");
            BUSINESS_OBJECTS_UTILS_LOGGER.info("Home Phone No : " + homePhoneNo);
            return homePhoneNo;
        } catch (MatrixException ex) {
            //Logger.getLogger(BusinessObjectUtils.class.getName()).log(Level.SEVERE, null, ex);
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex.getMessage());
        }
        return "";
    }

    public static Map<String, String> getPersonInfo(Context context, String userName, String revision, String vault, List<String> attributeList) throws MatrixException {
        Map<String, String> personInfoList = new HashMap<>();
        if ("".equals(revision) || revision == null) {
            revision = "-";
        }
        if ("".equals(vault) || vault == null) {
            vault = "eService Production";
        }
        BusinessObject bus = new BusinessObject("Person", userName, revision, vault);
        bus.open(context);
        bus.getAttributes(context);
        SelectList sList = new SelectList();
        for (int i = 0; i < attributeList.size(); i++) {
            sList.addAttribute(attributeList.get(i));
        }
        BusinessObjectWithSelect busWithSelect = bus.select(context, sList);
        for (int i = 0; i < attributeList.size(); i++) {
            String selectData = busWithSelect.getSelectData((String) sList.get(i));
            personInfoList.put(attributeList.get(i), selectData);
        }
        return personInfoList;
    }

    public static String getItemAttributeValue(Context context, String itemId, String attrName) {
        String attributeValue = "";
        StringBuilder commandBuilder = new StringBuilder();
        try {
            commandBuilder.append("print bus ").append(itemId)
                    .append(" select attribute[").append(attrName)
                    .append("].value dump");
            attributeValue = MqlUtil.mqlCommand(context, commandBuilder.toString());
            BUSINESS_OBJECTS_UTILS_LOGGER.info("attr Name : " + attrName + " attr Value : " + attributeValue);
        } catch (FrameworkException ex) {
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex.getMessage());
            //Logger.getLogger(ResponseResultSenderImpl.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
        return attributeValue;
    }

    public String getCurrentState(Context context, BusinessObject bo) throws FrameworkException {
        String currentState = "";
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("print bus ").append(bo.getObjectId()).append(" select current dump |;");
        currentState = MqlUtil.mqlCommand(context, commandBuilder.toString());
        return currentState.trim();
    }

    public static BusinessObject getLatestRevBO(Context context, String type, String name) throws MatrixException {
        StringList busSelect = new StringList(4);
        busSelect.addElement("id");
        busSelect.addElement("type");
        busSelect.addElement("name");
        busSelect.addElement("revision");
        busSelect.addElement("originated");

        Query query = new Query();
        query.setBusinessObjectType(type);
        query.setBusinessObjectName(name);
        query.setBusinessObjectRevision("*");

        BusinessObjectWithSelectList bwsList = new BusinessObjectWithSelectList();

        bwsList = query.selectTmp(context, busSelect);
        if (bwsList.isEmpty()) {
            return null;
        }

        HashMap<String, Date> itemOriginatedDateMap = new HashMap<>();
        for (int i = 0; i < bwsList.size(); i++) {
            BusinessObjectWithSelect bws = (BusinessObjectWithSelect) bwsList.elementAt(i);
            String id = (String) bws.getSelectData("id");
            String sRevision = (String) bws.getSelectData("revision");
            String created = (String) bws.getSelectData("originated");
            itemOriginatedDateMap.put(id, new Date(created));
        }

        LinkedHashMap<String, Date> itemOriginatedDateMapSorted = itemOriginatedDateMap
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        BusinessObject latestBO = new BusinessObject(itemOriginatedDateMapSorted.entrySet().iterator().next().getKey());
        return latestBO;
    }

    public static ArrayList<BusinessObject> findBO(Context context, TNR tnr) throws MatrixException {
        String tempQuery = "temp query bus '" + tnr.getType() + "' '" + tnr.getName() + "' '" + tnr.getRevision() + "' select id dump";
        BUSINESS_OBJECTS_UTILS_LOGGER.debug(tempQuery);
        MQLCommand mqlCommand = new MQLCommand();
        String mqlResult = MqlUtil.mqlCommand(context, mqlCommand, tempQuery);
        BUSINESS_OBJECTS_UTILS_LOGGER.debug(mqlResult);

        ArrayList<BusinessObject> boList = new ArrayList<>();

        if (!NullOrEmptyChecker.isNullOrEmpty(mqlResult)) {
            List<String> dataList = Arrays.asList(mqlResult.split("\n"));

            dataList.stream().forEach((String objectData) -> {
                try {
                    String[] objectProperties = objectData.split(",");

                    boList.add(new BusinessObject(objectProperties[objectProperties.length - 1]));
                } catch (MatrixException ex) {
                    BUSINESS_OBJECTS_UTILS_LOGGER.error(ex);
                }

            });
        }

        return boList;
    }

    public static BusinessObject getBOByTNR(Context context, TNR tnr) {
        BusinessObject bo = null;
        try {
            if (tnr.getRevision() == null || tnr.getRevision().isEmpty()) {
                bo = getLatestRevBO(context, tnr.getType(), tnr.getName());
            } else {
                bo = findBO(context, tnr).get(0);
            }
        } catch (Exception ex) {
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex);
        }
        return bo;
    }

    public String findBusinessObjectsState(BusinessObject businessObject, Context context) throws MatrixException {
        try {
            StateItr stateItr = new StateItr(businessObject.getStates(context));
            while (stateItr.next()) {
                if (stateItr.obj().isCurrent()) {
                    break;
                }
            }
            return stateItr.obj().getName();

        } catch (MatrixException exp) {
            BUSINESS_OBJECTS_UTILS_LOGGER.error("Error occurred in searching the current state of the object");
            BUSINESS_OBJECTS_UTILS_LOGGER.error(exp.getMessage());
            throw exp;
        }
    }

    /**
     *
     * @param context
     * @param bo
     * @return phusicalID of the Item
     * @throws com.matrixone.apps.domain.util.FrameworkException
     */
    public String getItemPhysicalID(Context context, BusinessObject bo) throws FrameworkException {
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("print bus ").append(bo.getObjectId()).append(" select physicalid dump |;");
        String physicalID = "";
        try {
            physicalID = MqlUtil.mqlCommand(context, commandBuilder.toString());
        } catch (FrameworkException ex) {
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex);
            throw ex;
        }
        return physicalID;
    }

    /**
     *
     * @param context
     * @param physicalID
     * @return proposedActivityBO
     * @throws MatrixException
     */
    public BusinessObject getProposedActivityFromItem(Context context, String physicalID) throws MatrixException {
        /*StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("temp query bus 'Proposed Activity' * * where 'paths[Proposed Activity.Where].path ~= ")
                .append("\"*")
                .append(physicalID)
                .append("*\" '")
                .append(" select id dump |;");
        String queryResult = MqlUtil.mqlCommand(context, commandBuilder.toString());
        String proposedActivityID = queryResult.split("\\|")[3];*/
        BusinessObject proposedActivityBO = null;
        StringList busSelect = new StringList(4);
        busSelect.addElement("id");
        busSelect.addElement("type");
        busSelect.addElement("name");
        busSelect.addElement("revision");

        Query query = new Query();
        query.setBusinessObjectType("Proposed Activity");
        query.setBusinessObjectName("*");
        query.setBusinessObjectRevision("*");

        StringBuilder whereExpBuilder = new StringBuilder();
        whereExpBuilder.append("paths[Proposed Activity.Where].path ~=")
                .append("\"*")
                .append(physicalID)
                .append("*\"");

        String whereExpression = whereExpBuilder.toString();
        query.setWhereExpression(whereExpression);

        BusinessObjectWithSelectList bwsList = new BusinessObjectWithSelectList();
        try {
            bwsList = query.selectTmp(context, busSelect);
        } catch (MatrixException ex) {
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex);
            throw ex;
        }
        ArrayList<String> boList = new ArrayList<>();

        for (int itr = 0; itr < bwsList.size(); itr++) {
            BusinessObjectWithSelect bws = (BusinessObjectWithSelect) bwsList.elementAt(itr);
            String id = (String) bws.getSelectData("id");
            boList.add(id);
        }
        String proposedActivityID = "";
        if (!boList.isEmpty()) {
            // There should one Proposed Activity
            proposedActivityID = boList.get(0);
            proposedActivityBO = new BusinessObject(proposedActivityID);
        }
        return proposedActivityBO;
    }

    /**
     *
     * @param context
     * @param proposedActivityBO
     * @return changeActionBO
     * @throws MatrixException
     */
    public BusinessObject getChangeActionFromProposedActivity(Context context, BusinessObject proposedActivityBO) throws MatrixException {
        SelectList selectBusStmts = new SelectList();
        SelectList selectRelStmts = new SelectList();
        Pattern typePattern = new Pattern("Change Action");
        Pattern relPattern = new Pattern("Proposed Activities");
        String busWhereExpression = "";
        String relWhereExpression = "";
        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short("1");
        String changeActionID = "";
        BusinessObject changeActionBO = null;
        try {
            expandResult = proposedActivityBO.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    true, false,
                    expandLevel, busWhereExpression, relWhereExpression, false);
            relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
            while (relItr.next()) {
                RelationshipWithSelect relSelect = relItr.obj();
                BusinessObject parentBo = relSelect.getFrom();
                parentBo.open(context);
                if (parentBo.getTypeName().equalsIgnoreCase("Change Action")) {
                    changeActionBO = parentBo;
                    break;
                }
                parentBo.close(context);
            }

        } catch (MatrixException ex) {
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex);
            throw ex;
        }
        return changeActionBO;
    }

    /**
     * <<<<<<< HEAD
     *
     * @param context
     * @param physicalID
     * @return realizedActivityBO
     * @throws MatrixException
     */
    public BusinessObject getRealizedActivityFromItem(Context context, String physicalID) throws MatrixException {
        // temp query bus 'Realized Activity' * * where 'paths[Realized Activity.Where].path.element[0].physicalId~=??'
        BusinessObject realizedActivityBO = null;

        StringList busSelectStmnt = new StringList();
        busSelectStmnt.add("id");
        busSelectStmnt.add("type");
        busSelectStmnt.add("name");
        busSelectStmnt.add("revision");

        Query query = new Query();
        query.setBusinessObjectType("Realized Activity");
        query.setBusinessObjectName("*");
        query.setBusinessObjectRevision("*");

        StringBuilder whereExprBuilder = new StringBuilder();
        whereExprBuilder.append("paths[Realized Activity.Where].path.element[0].physicalid==")
                .append(physicalID);

        String whereExpression = whereExprBuilder.toString();
        query.setWhereExpression(whereExpression);

        BusinessObjectWithSelectList bwsList = new BusinessObjectWithSelectList();
        try {
            bwsList = query.selectTmp(context, busSelectStmnt);
        } catch (MatrixException ex) {
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex);
            throw ex;
        }

        ArrayList<String> boList = new ArrayList<>();
        for (int itr = 0; itr < bwsList.size(); itr++) {
            BusinessObjectWithSelect bws = (BusinessObjectWithSelect) bwsList.elementAt(itr);
            String id = (String) bws.getSelectData("id");
            boList.add(id);
        }
        String realizedActivityID = "";
        if (!boList.isEmpty()) {
            // There should be one Realized Activity
            realizedActivityID = boList.get(0);
            realizedActivityBO = new BusinessObject(realizedActivityID);
        }
        return realizedActivityBO;
    }

    /**
     *
     * @param context
     * @param realizedActivityBO
     * @return changeActionBO
     * @throws MatrixException
     */
    public BusinessObject getChangeActionFromRealizedActivity(Context context, BusinessObject realizedActivityBO) throws MatrixException {
        SelectList selectBusStmts = new SelectList();
        SelectList selectRelStmts = new SelectList();
        Pattern typePattern = new Pattern("Change Action");
        Pattern relPattern = new Pattern("Realized Activities");
        String busWhereExpression = "";
        String relWhereExpression = "";
        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short("1");
        String changeActionID = "";
        BusinessObject changeActionBO = null;
        try {
            expandResult = realizedActivityBO.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    true, false,
                    expandLevel, busWhereExpression, relWhereExpression, false);
            relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
            while (relItr.next()) {
                RelationshipWithSelect relSelect = relItr.obj();
                BusinessObject parentBo = relSelect.getFrom();
                parentBo.open(context);
                if (parentBo.getTypeName().equalsIgnoreCase("Change Action")) {
                    changeActionBO = parentBo;
                    break;
                }
                parentBo.close(context);
            }

        } catch (MatrixException ex) {
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex);
            throw ex;
        }
        return changeActionBO;
    }

    /**
     * ======= >>>>>>> d915d52289651714d751fb1612627e4f8e6c6755
     *
     * @param context
     * @param changeActionBO
     * @return reviewerBO List
     * @throws MatrixException
     */
    public ArrayList<BusinessObject> getChangeActionReviewersFromChangeAction(Context context, BusinessObject changeActionBO) throws MatrixException {
        SelectList selectBusStmts = new SelectList();
        SelectList selectRelStmts = new SelectList();
        Pattern typePattern = new Pattern("Person");
        Pattern relPattern = new Pattern("Change Reviewer");
        String busWhereExpression = "";
        String relWhereExpression = "";
        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short("1");
        String changeReviewerID = "";

        BusinessObject reviewerBO = null;
        ArrayList<BusinessObject> reviewerBOList = new ArrayList<>();
        try {
            expandResult = changeActionBO.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    false, true,
                    expandLevel, busWhereExpression, relWhereExpression, false);

            relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
            while (relItr.next()) {
                RelationshipWithSelect relSelect = relItr.obj();
                BusinessObject childBo = relSelect.getTo();
                childBo.open(context);
                if (childBo.getTypeName().equalsIgnoreCase("Person")) {
                    reviewerBOList.add(childBo);
                }
                childBo.close(context);
            }
        } catch (MatrixException ex) {
            BUSINESS_OBJECTS_UTILS_LOGGER.error(ex);
            throw ex;
        }
        return reviewerBOList;
    }

    /**
     *
     * @param context
     * @param itemBO
     * @return
     * @throws FrameworkException
     * @throws MatrixException
     */
    public ArrayList<BusinessObject> getChangeActionReviewersFromItem(Context context, BusinessObject itemBO) throws FrameworkException, MatrixException {
        BusinessObjectUtils businessObjectUtil = new BusinessObjectUtils();
        String itemPhysicalID = businessObjectUtil.getItemPhysicalID(context, itemBO);
        BusinessObject proposedActivityBO = businessObjectUtil.getProposedActivityFromItem(context, itemPhysicalID);
        BusinessObject changeActionBO = businessObjectUtil.getChangeActionFromProposedActivity(context, proposedActivityBO);
        ArrayList<BusinessObject> reviewerBOList = businessObjectUtil.getChangeActionReviewersFromChangeAction(context, changeActionBO);
        return reviewerBOList;
    }

    /**
     *
     * @param context
     * @param itemBO
     * @return changeActionBO
     * @throws FrameworkException
     * @throws MatrixException
     */
    public BusinessObject getChangeActionFromItem(Context context, BusinessObject itemBO) throws FrameworkException, MatrixException {
        BusinessObject changeActionBO = null;
        BusinessObjectUtils businessObjectUtil = new BusinessObjectUtils();
        String itemPhysicalID = businessObjectUtil.getItemPhysicalID(context, itemBO);
        BusinessObject proposedActivityBO = businessObjectUtil.getProposedActivityFromItem(context, itemPhysicalID);
        if (proposedActivityBO != null) {
            changeActionBO = businessObjectUtil.getChangeActionFromProposedActivity(context, proposedActivityBO);
        } else {
            BUSINESS_OBJECTS_UTILS_LOGGER.error("Proposed activity not found. Unable to fetch change action!!");
        }
        if (changeActionBO == null) {
            BusinessObject realizedActivityBO = businessObjectUtil.getRealizedActivityFromItem(context, itemPhysicalID);
            if (realizedActivityBO != null) {
                changeActionBO = businessObjectUtil.getChangeActionFromRealizedActivity(context, realizedActivityBO);
            } else {
                BUSINESS_OBJECTS_UTILS_LOGGER.error("Realized activity not found. Unable to fetch change action!!");
            }
        }
        return changeActionBO;
    }

    /**
     *
     * @param context
     * @param bo
     * @return laterRevision
     */
    public static String findLaterRevisionTransferredToLN(Context context, BusinessObject bo) {
        BUSINESS_OBJECTS_UTILS_LOGGER.info("Later Revision Checking Process is Started!!");
        String laterRev = "";
        StringBuilder cmdBuilder = new StringBuilder();
        String itemRevision = bo.getRevision();
        try {
            cmdBuilder.append("temp query bus ")
                    .append(bo.getTypeName()).append(" ")
                    .append(bo.getName()).append(" ")
                    .append("*").append(" ")
                    .append("select attribute[" + ApplicationProperties.getProprtyValue("source.att.send.to.erp") + "].value dump |");
            String s = MqlUtil.mqlCommand(context, cmdBuilder.toString());
            String[] split = s.split("\n");
            for (int i = 0; i < split.length; i++) {
                String[] laterDetails = split[i].split("\\|");
                String laterRevision = laterDetails[2];
                if (itemRevision.compareTo(laterRevision) < 0) {
                    String transferredToERP = laterDetails[3];
                    if (transferredToERP.equals("TRUE")) {
                        laterRev = laterRevision;
                        BUSINESS_OBJECTS_UTILS_LOGGER.info("Found Later Revision: " + bo.getName() + " " + laterRev + " TransferredToERP TRUE.");
                        return laterRev;
                    }
                }
            }
        } catch (MatrixException ex) {
            System.out.println(ex.getMessage());
        }
        if (laterRev == null || laterRev.equals("")) {
            BUSINESS_OBJECTS_UTILS_LOGGER.info("No Later Revision with TransferredToERP TRUE found for " + bo.getName() + " " + bo.getRevision());
        }
        return laterRev;
    }

}
