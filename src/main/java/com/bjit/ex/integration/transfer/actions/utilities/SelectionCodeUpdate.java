/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities;

import com.bjit.ex.integration.transfer.actions.factory.TransferActionFactory;
import com.bjit.ex.integration.transfer.actions.factory.TransferActionType;
import com.bjit.ex.integration.transfer.actions.transferItems.ITransferAction;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.util.MatrixException;
import matrix.util.SelectList;

/**
 *
 * @author BJIT
 */
public class SelectionCodeUpdate {

    private static final org.apache.log4j.Logger SELECTION_CODE_UPDATE_LOGGER = org.apache.log4j.Logger.getLogger(SelectionCodeUpdate.class);

    public List<ResponseResult> selectionCodeProcess(Context context, BusinessObject businessObject, List<ResponseResult> BOMResponseResultList, TransferObjectUtils transferObjectUtils) throws NoSuchMethodException, ClassNotFoundException, MatrixException {
        List<ResponseResult> responseResultList = new ArrayList<>();
        
        TransferActionFactory transferActionFactory = new TransferActionFactory();
        ArrayList<BusinessObject> selectionCodeUpdateTransferBOList = new ArrayList<>();
        HashMap<String, String> itemSelectionCodeMap = new HashMap<>();
        ArrayList<String> checkDuplicateBOM = new ArrayList<>();
        boolean unspecifiedErrorOccurredDuringBOMTransfer = false;
        SelectList buildRelStmnsts = transferObjectUtils.buildBusStmnts();

        //Gather item to update selection code item for item with child.
        for (ResponseResult result : BOMResponseResultList) {
            if (result != null) {
                String itemName = result.getItem();
                String selectionCode = "";
                String selectCde = transferObjectUtils.getSelectionCode(context, businessObject, buildRelStmnsts);
                if (result.getSelectionCodeType() != null) {
                    if (result.getSelectionCodeType().name().equalsIgnoreCase(SelectionCodeType.ITM.name()) && selectCde.equalsIgnoreCase(SelectionCodeType.BOM.name())) {
                        selectionCode = result.getSelectionCodeType().name();
                    } else if (result.getSelectionCodeType().name().equalsIgnoreCase(SelectionCodeType.EXT.name()) && !selectCde.equalsIgnoreCase(SelectionCodeType.ITM.name())) {
                        selectionCode = SelectionCodeType.ERR.name();
                    } else if (result.getSelectionCodeType().name().equalsIgnoreCase(SelectionCodeType.ITM.name()) && !selectCde.equalsIgnoreCase(SelectionCodeType.ITM.name())) {
                        selectionCode = SelectionCodeType.EXT.name();
                    } else {
                        selectionCode = result.getSelectionCodeType().name();
                    }

                } else {
                    if (result.isSuccessful()) {
                        selectionCode = SelectionCodeType.BOM.name();
                    } else if (selectCde.equalsIgnoreCase(SelectionCodeType.ITM.name())) {
                        selectionCode = SelectionCodeType.EXT.name();
                    } else {
                        selectionCode = SelectionCodeType.ERR.name();
                    }
                }
                if (!itemSelectionCodeMap.containsKey(itemName)) {
                    itemSelectionCodeMap.put(itemName, selectionCode);
                } else {
                    //Selection Code priority : ERR, EXT/BOM, ITM
                    if (itemSelectionCodeMap.get(itemName).equalsIgnoreCase(SelectionCodeType.ERR.name())) {
                        itemSelectionCodeMap.put(itemName, SelectionCodeType.ERR.name());
                    } else if (itemSelectionCodeMap.get(itemName).equalsIgnoreCase(SelectionCodeType.ITM.name())) {
                        itemSelectionCodeMap.put(itemName, SelectionCodeType.ITM.name());
                    } else if (itemSelectionCodeMap.get(itemName).equalsIgnoreCase(SelectionCodeType.EXT.name())) {
                        if (selectionCode.equalsIgnoreCase(SelectionCodeType.ERR.name())) {
                            itemSelectionCodeMap.put(itemName, SelectionCodeType.ERR.name());
                        } else {
                            itemSelectionCodeMap.put(itemName, SelectionCodeType.EXT.name());
                        }
                    } else {
                        itemSelectionCodeMap.put(itemName, selectionCode);
                    }
                }

                if (!checkDuplicateBOM.contains(itemName)) {
                    checkDuplicateBOM.add(itemName);
                    selectionCodeUpdateTransferBOList.add(businessObject);
                }
            } else {
                BOMResponseResultList.remove(null);
                unspecifiedErrorOccurredDuringBOMTransfer = true;
                SELECTION_CODE_UPDATE_LOGGER.info("Response NULL. Unspecified error occurred during BOM transfer. Probably no Item name from LN response.");
            }

        }


        /* Selection Code update process */
        List<ResponseResult> selectionCodeUpdateResponseResultList = new ArrayList<>();
        if (!selectionCodeUpdateTransferBOList.isEmpty()) {
            ITransferAction selectionCodeTransferAction;
            try {
                selectionCodeTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.SELECTION_CODE_UPDATE);
                selectionCodeUpdateResponseResultList = (List<ResponseResult>) selectionCodeTransferAction.processSelectionCodeUpdateTransfer(context, selectionCodeUpdateTransferBOList, itemSelectionCodeMap, transferObjectUtils);
            } catch (InstantiationException ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        SELECTION_CODE_UPDATE_LOGGER.debug("Selection Code update transfer response list from LN :: " + selectionCodeUpdateResponseResultList);
        responseResultList.addAll(selectionCodeUpdateResponseResultList);
        return responseResultList;
    }

    public void updateTransferToErpUpdate(Context context, BusinessObject businessObject, List<ResponseResult> itemResponseResultList) {

        for (ResponseResult result : itemResponseResultList) {
            if (result != null) {
                if (result.isSuccessful()) {
                    String itemName_successfullyTransferred = result.getItem();
                    //Update "Transfer To ERP" attribute.
                    try {
                        //                     BusinessObject successfullyTransferredBO = businessObjectNameMap.get(itemName_successfullyTransferred);
                        businessObject.setAttributeValue(context, ApplicationProperties.getProprtyValue("source.att.send.to.erp"), Boolean.TRUE.toString());
                        SELECTION_CODE_UPDATE_LOGGER.info(itemName_successfullyTransferred + " : 'Transfer to ERP' is updated to TRUE.");
                    } catch (Exception ex) {
                        SELECTION_CODE_UPDATE_LOGGER.error(itemName_successfullyTransferred + " : Error occurred during 'Transfer to ERP' value update.");
                    }

                } else {
                    String itemName_failedTransfer = result.getItem();

                    SELECTION_CODE_UPDATE_LOGGER.info(itemName_failedTransfer + " : Transfer failed. Transfer Result : " + result.getResultText());

                }

            } else {
                itemResponseResultList.remove(null);
                SELECTION_CODE_UPDATE_LOGGER.info("Response NULL. Unspecified error occurred during Item transfer. Probably no Item name from LN response.");
            }
        }

    }

    public List<ResponseResult> itemBOMTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils, String transferType,boolean isService) throws Exception {
        TransferActionFactory transferActionFactory = new TransferActionFactory();
        List<ResponseResult> responseResult = new ArrayList<>();
        if (transferType.equalsIgnoreCase("ITM")) {
            ITransferAction iTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.ITEMS);
            responseResult = (List<ResponseResult>) iTransferAction.processItemsTransfer(context, businessObjectList, transferObjectUtils);
        } else {
            ITransferAction BOMTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.BOM);
            responseResult = (List<ResponseResult>) BOMTransferAction.processBOMTransfer(context, businessObjectList, transferObjectUtils,isService);

        }
        return responseResult;

    }

    public List<ResponseResult> itemSelectionCodeUpdate(Context context, BusinessObject businessObject, List<ResponseResult> itemResponseResultList, TransferObjectUtils transferObjectUtils) {
        HashMap<String, String> itemSelectionCodeMap = new HashMap<>();
        ArrayList<String> checkDuplicateBOM = new ArrayList<>();
        TransferActionFactory transferActionFactory = new TransferActionFactory();
        ArrayList<BusinessObject> selectionCodeUpdateTransferBOList = new ArrayList<>();
        List<ResponseResult> responseResultList = new ArrayList<>();
        for (ResponseResult itemResult : itemResponseResultList) {
            if (itemResult != null) {
                String itemName = itemResult.getItem();
                if (!itemSelectionCodeMap.containsKey(itemName)) {
                    if (itemResult.isSuccessful()) {
                        itemSelectionCodeMap.put(itemName, SelectionCodeType.ITM.name());
                        if (!checkDuplicateBOM.contains(itemName)) {
                            checkDuplicateBOM.add(itemName);
                            selectionCodeUpdateTransferBOList.add(businessObject);
                        }
                    }
                }
            }
        }

        /* Selection Code update process */
        List<ResponseResult> selectionCodeUpdateResponseResultList = new ArrayList<>();
        if (!selectionCodeUpdateTransferBOList.isEmpty()) {
            ITransferAction selectionCodeTransferAction;
            try {
                selectionCodeTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.SELECTION_CODE_UPDATE);
                selectionCodeUpdateResponseResultList = (List<ResponseResult>) selectionCodeTransferAction.processSelectionCodeUpdateTransfer(context, selectionCodeUpdateTransferBOList, itemSelectionCodeMap, transferObjectUtils);
            } catch (InstantiationException ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(SelectionCodeUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        SELECTION_CODE_UPDATE_LOGGER.debug("Selection Code update transfer response list from LN :: " + selectionCodeUpdateResponseResultList);
        responseResultList.addAll(selectionCodeUpdateResponseResultList);
        return responseResultList;

    }
}
