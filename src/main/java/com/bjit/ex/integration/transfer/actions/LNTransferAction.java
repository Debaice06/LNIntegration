
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions;

import java.util.*;
import java.util.stream.Collectors;

import com.bjit.context.ContextGeneration;
import com.bjit.ex.integration.model.webservice.Item;
import com.bjit.ex.integration.transfer.actions.factory.TransferActionFactory;
import com.bjit.ex.integration.transfer.actions.factory.TransferActionType;
import com.bjit.ex.integration.transfer.actions.transferItems.ITransferAction;
import com.bjit.ex.integration.transfer.actions.utilities.BomUtils;
import com.bjit.ex.integration.transfer.actions.utilities.BusinessObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.ItemUtils;
import com.bjit.ex.integration.transfer.actions.utilities.SelectionCodeType;
import com.bjit.ex.integration.transfer.actions.utilities.SelectionCodeUpdate;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.factory.ResultSenderFactory;
import com.bjit.ex.integration.transfer.resultsender.impls.TransferResponseService;
import com.bjit.ex.integration.transfer.resultsender.impls.TransferResponseServiceImpl;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import java.io.File;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.db.StateItr;
import matrix.util.MatrixException;

/**
 * @author BJIT
 */
public final class LNTransferAction {

    private static final org.apache.log4j.Logger LN_TRANSFER_ACTION_LOGGER = org.apache.log4j.Logger.getLogger(LNTransferAction.class);
    private final static HashMap<String, String> STATE_AND_CLASS_NAMES_MAP = new HashMap<>();

    public Map<String, String> initiateTransferToLN(Context context, Item item, TransferActionType transferActionType, boolean isService) throws MatrixException, Exception {
        TransferActionFactory transferActionFactory = new TransferActionFactory();
        TransferObjectUtils transferObjectUtils = new TransferObjectUtils();
        ItemUtils itemUtils = new ItemUtils();
        BomUtils bomUtils = new BomUtils();
        BusinessObject businessObject = null;
        Set<ResponseResult> uniqueResponseResult = new LinkedHashSet<>();

        if (context == null) {
            context = ContextGeneration.createContext();
        }
        if (item.getId() != null && !item.getId().isEmpty()) {
            businessObject = new BusinessObject(item.getId());
        } else {
            businessObject = BusinessObjectUtils.getBOByTNR(context, item.getTnr());
        }
        List<ResponseResult> responseResultList = startTransferToLNProcess(context, businessObject, transferObjectUtils, transferActionFactory, itemUtils, bomUtils, transferActionType, item, isService);
        Map<String, String> itemMessageMap = responseResultList.stream()
                .collect(Collectors.toMap(ResponseResult::getItem, ResponseResult::getResultText, (oldValue, newValue) -> newValue));
        return itemMessageMap;
    }

    private List<ResponseResult> startTransferToLNProcess(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, TransferActionFactory transferActionFactory, ItemUtils itemUtils, BomUtils bomUtils, TransferActionType transferType, Item item, boolean isService) throws Exception {
        List<ResponseResult> responseResults = new ArrayList<>();
        List<ResponseResult> itemResponseResults = new ArrayList<>();
        List<ResponseResult> updateResponseResult = new ArrayList<>();
        ArrayList<BusinessObject> itemBusinessObject = new ArrayList<>();
        SelectionCodeUpdate selectionCodeUpdate = new SelectionCodeUpdate();
        TransferResponseService transferResponseService = new TransferResponseServiceImpl();
        ResponseResultSender responseResultSender = ResultSenderFactory.getResultSender(SenderType.SERVICE_RESPONSE_SENDER);
        String type = "";
        try {
            businessObject.open(context);
            String itemName = businessObject.getName();
            ITransferAction iTransferAction = transferActionFactory.lnTransferFactory(transferType);
            if (!businessObject.getRevision().equalsIgnoreCase("1.1")) {
                TransferObjectUtils.checkPreviousRevision(context, businessObject);

            }
            switch (transferType) {
                // BOM Transfer calling from LN transfer service
                case ITEM_BOM:
                    LN_TRANSFER_ACTION_LOGGER.info("Manufacturing Item found : Item Name : " + itemName);
                    initilizeTheTransferUtilityClasses(context, businessObject, transferObjectUtils, itemUtils, bomUtils);

                    itemBusinessObject.add(businessObject);

                    type = "ITM";
                    String transffered = transferObjectUtils.getAllowTransferredToErpValue(context, businessObject);

                    if (TransferObjectUtils.VALItemType.contains(businessObject.getTypeName()) && transffered.equalsIgnoreCase("TRUE")) {
                        responseResults = transferResponseService.lNResponse(itemName);
                    } else {
                        responseResults = selectionCodeUpdate.itemBOMTransfer(context, itemBusinessObject, transferObjectUtils, type, isService);
                        if (responseResults.isEmpty()) {
                            responseResults = transferResponseService.lNResponse(itemName);
                        }
                        selectionCodeUpdate.updateTransferToErpUpdate(context, businessObject, responseResults);
                    }

                    return responseResults;

                case BOM:
                    LN_TRANSFER_ACTION_LOGGER.info("Manufacturing Item found : Item Name : " + itemName);
                    initilizeTheTransferUtilityClasses(context, businessObject, transferObjectUtils, itemUtils, bomUtils);
                    if (!businessObject.getRevision().equalsIgnoreCase("1.1")) {
                        TransferObjectUtils.checkPreviousRevision(context, businessObject);
                    }
                    itemBusinessObject.add(businessObject);
                    type = "BOM";


                    responseResults = selectionCodeUpdate.itemBOMTransfer(context, itemBusinessObject, transferObjectUtils, type, isService);
                    if (responseResults.isEmpty()) {
                        responseResults = transferResponseService.lNResponse(itemName);

                    }
                    updateResponseResult = selectionCodeUpdate.selectionCodeProcess(context, businessObject, responseResults, transferObjectUtils);
                    responseResultSender.initializeResultSender(context, businessObject, item, isService);
                    responseResultSender.send(updateResponseResult);

                    if (responseResults.isEmpty()) {
                        return updateResponseResult;
                    } else {

                        return responseResults;
                    }

                // Item transfer calling from LN transfer service
                case ITEMS:
                    LN_TRANSFER_ACTION_LOGGER.info("Obsolete Manufacturing Item found : Item Name : " + itemName);
                    if (!businessObject.getRevision().equalsIgnoreCase("1.1")) {
                        TransferObjectUtils.checkPreviousRevision(context, businessObject);
                    }
                    itemBusinessObject.add(businessObject);

                    String transfferedItem = transferObjectUtils.getAllowTransferredToErpValue(context, businessObject);

                    if (TransferObjectUtils.VALItemType.contains(businessObject.getTypeName()) && transfferedItem.equalsIgnoreCase("TRUE")) {
                    } else {
                        itemResponseResults = (List<ResponseResult>) iTransferAction.processItemsTransfer(context, itemBusinessObject, transferObjectUtils);
                        selectionCodeUpdate.updateTransferToErpUpdate(context, businessObject, itemResponseResults);
                        updateResponseResult = selectionCodeUpdate.itemSelectionCodeUpdate(context, businessObject, itemResponseResults, transferObjectUtils);

                        responseResultSender.initializeResultSender(context, businessObject, item, isService);
                        responseResultSender.send(updateResponseResult);

                    }
                    return itemResponseResults;

                case NIGHTLY_TRANSLATION_UPDATE:
                    GTSNightlyUpdateTransferAction action = new GTSNightlyUpdateTransferAction(isService);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            LN_TRANSFER_ACTION_LOGGER.error(e);
            throw e;
        } finally {
            businessObject.close(context);
        }

        return updateResponseResult;
    }

    private void initilizeTheTransferUtilityClasses(Context context, BusinessObject bo, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils, BomUtils bomUtils) throws Exception {
        try {
            transferObjectUtils.__init__(context, bo);
            itemUtils.__init__(transferObjectUtils);
            bomUtils.__init__(transferObjectUtils);
        } catch (Exception exp) {
            LN_TRANSFER_ACTION_LOGGER.error("Error in initializing the transferObjectUtils or itemUtils or bomUtils");
            throw exp;
        }
    }

    private String findBusinessObjectsState(BusinessObject businessObject, Context context) throws MatrixException {
        try {
            StateItr stateItr = new StateItr(businessObject.getStates(context));
            while (stateItr.next()) {
                if (stateItr.obj().isCurrent()) {
                    break;
                }
            }
            return stateItr.obj().getName();

        } catch (MatrixException exp) {
            LN_TRANSFER_ACTION_LOGGER.error("Error occurred in searching the current state of the object");
            LN_TRANSFER_ACTION_LOGGER.error(exp.getMessage());
            throw exp;
        }
    }
}
