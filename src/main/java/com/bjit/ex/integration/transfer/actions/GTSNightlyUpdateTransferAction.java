/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions;

import com.bjit.ex.integration.externalservices.GTS.bundleId.service.GTSBundleIdResponse;
import com.bjit.context.ContextGeneration;
import com.bjit.ex.integration.mapproject.builder.MapperBuilder;
import com.bjit.ex.integration.mapproject.xml_mapping_model.Mapping;
import com.bjit.ex.integration.transfer.actions.factory.TransferActionFactory;
import com.bjit.ex.integration.transfer.actions.factory.TransferActionType;
import com.bjit.ex.integration.transfer.actions.transferItems.ITransferAction;
import com.bjit.ex.integration.transfer.actions.utilities.GTSDataUtil;
import com.bjit.ex.integration.transfer.actions.utilities.ItemUtils;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.matrixone.apps.domain.util.FrameworkException;
import com.matrixone.apps.domain.util.MqlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import matrix.db.Context;

/**
 * @author Sajjad
 */
public class GTSNightlyUpdateTransferAction {

    private static final org.apache.log4j.Logger GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER = org.apache.log4j.Logger.getLogger(GTSNightlyUpdateTransferAction.class);
    String transferAction = "UpdatedBundleID";

    public GTSNightlyUpdateTransferAction(List<String> bundleIDList) {
        processBundleID(bundleIDList);
    }

    public GTSNightlyUpdateTransferAction(boolean isService) {
        try {
            List<String> bundleIDList = GTSDataUtil.getUpdatedBundleIds();
            processBundleID(bundleIDList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processBundleID(List<String> bundleIDList) {
        try {
            GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER.info("LN TRANSPER APP BUILD VERSION :: " + ApplicationProperties.getProprtyValue("ln.integration.release.build"));
            GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER.info("### Nighly update to LN process start (GTS title).");
            Context context = null;
            context = ContextGeneration.createContext();
            HashMap itemTranslationMap = new HashMap();
            for (String bundleID : bundleIDList) {
                List<String> itemIdList = bundleIDWhereUsed(context, bundleID);
                System.out.println("itemIdList " + itemIdList);
                if (itemIdList != null && !itemIdList.isEmpty()) {
                    GTSBundleIdResponse abbreviationData = GTSDataUtil.getAbbreviationData(bundleID);
                    for (String itemId : itemIdList) {
                        itemTranslationMap.put(itemId, abbreviationData);
                    }
                }
            }
            processItemForReTransfer(context, itemTranslationMap);

        } catch (Exception ex) {
            GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER.error(ex);
        }
    }

    private void processItemForReTransfer(Context context, HashMap itemTranslationMap) {
        try {
            if (itemTranslationMap == null || itemTranslationMap.isEmpty()) {
                GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER.info("### No Item to update.");
            } else {
                TransferObjectUtils transferObjectUtils = new TransferObjectUtils();
                ItemUtils itemUtils = new ItemUtils();

                TransferActionFactory transferActionFactory = new TransferActionFactory();

                ITransferAction iTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.NIGHTLY_TRANSLATION_UPDATE);
                iTransferAction.processTranslationUpdateTransfer(context, itemTranslationMap, transferObjectUtils, itemUtils);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER.error(ex);
        } finally {
            context.close();
            GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER.info("### Nightly update to LN process end (GTS title).");
        }
    }

//    public void initilizeTheTransferUtilityClasses(Context context, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) throws Exception {
//        try {
//            //transferObjectUtils.__init__(context);
//            //itemUtils.__init__(transferObjectUtils);
//        } catch (Exception exp) {
//            GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER.error("Error in initializing the transferObjectUtils or itemUtils or bomUtils");
//            throw exp;
//        }
//    }

//    temp query bus CreateAssembly,Provide,ElementaryEndItem * * where "
//            (current=='RELEASED') && (attribute[MBOM_MBOMERP.MBOM_TransferredtoERP].value ~~ TRUE)
//            && (attribute[MBOM_MBOMPDM.MBOM_Mastership].value != PDM)
//            &&  (attribute[TRS_TermID.TRS_TermID].value == 54583)" select id dump |;
    public List<String> bundleIDWhereUsed(Context context, String bundleID) throws FrameworkException {
        Mapping mapper = null;
        String restrictedItemsStr = ApplicationProperties.getProprtyValue("LN.nightly.translation.update.restricted.item.type");
        List<String> restrictedItemList = Arrays.asList(restrictedItemsStr.split(","));
        List<String> itemIdList = new ArrayList<>();
        String attBundleID = ApplicationProperties.getProprtyValue("source.att.title.termId");
        String attTransferToERP = ApplicationProperties.getProprtyValue("source.att.send.to.erp");
        String attMastership = ApplicationProperties.getProprtyValue("item.mastership.att");
        String attExternalID = ApplicationProperties.getProprtyValue("source.object.ExternalID");
        StringBuilder queryBuilder = new StringBuilder();

        try {
            mapper = new MapperBuilder().getMapper(MapperBuilder.XML);

            List<String> allowedItemTypes = mapper.getXmlMapElementObjects().getXmlMapElementObject().stream()
                    .filter(xmlMapElementObject -> !restrictedItemList.contains(xmlMapElementObject.getType()))
                    .map(xmlMapElementObject -> xmlMapElementObject.getType())
                    .collect(Collectors.toList());

            queryBuilder.append("temp query bus ")
                    .append(String.join(",", allowedItemTypes))
                    .append(" * * where \" ")
                    .append("(current=='RELEASED') && ")
                    .append("(attribute[").append(attTransferToERP).append("].value ~~ TRUE) && ")
                    .append("(attribute[").append(attExternalID).append("].value  !~~ VAL*) && ")
                    .append(" (attribute[").append(attBundleID).append("].value == ").append(bundleID)
                    .append(")\" select id dump |;");

            GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER.info("Query for fetching itemID out of bundle id: " + queryBuilder);
        } catch (Exception ex) {
            GTS_NIGTHLY_UPDATE_TRANSFER_ACTION_LOGGER.info(ex.getMessage());
        }

        String queryResult = MqlUtil.mqlCommand(context, queryBuilder.toString());
        String[] splitedQueryResult = queryResult.split("\n");
        for (String result : splitedQueryResult) {
            String[] splitedResult = result.split("\\|");
            if (splitedResult.length == 4) {
                itemIdList.add(splitedResult[3].trim());
            }
        }
        return itemIdList;
    }
}
