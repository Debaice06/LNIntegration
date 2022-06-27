/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.transferItems;

import com.bjit.ex.integration.externalservices.GTS.bundleId.service.GTSBundleIdResponse;
import com.bjit.ex.integration.transfer.actions.utilities.*;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.DisassembleDataModel;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.factory.ResultSenderFactory;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType;

import java.util.*;

import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.util.MatrixException;

/**
 *
 * @author Sajjad
 */
public class UpdatedItemsTransferActionImpl implements ITransferAction {

    private static final org.apache.log4j.Logger UPDATE_ITEM_TRANSFER_LOGGER = org.apache.log4j.Logger.getLogger(ItemBOMTransferActionImpl.class);

    @Override
    public Object processItemBOMTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils, BomUtils bomUtils, boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processTranslationUpdateTransfer(Context context, HashMap itemTranslationMap, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) {
        ResponseResultSender responseResultSender = ResultSenderFactory.getResultSender(SenderType.NIGHTLY_TRANSFER_RESPONSE_SENDER);
        ArrayList<ResponseResult> results = new ArrayList<ResponseResult>();
        try {
            LinkedHashMap<String, BusinessObject> itemIdMap = new LinkedHashMap<>();
            List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList = new ArrayList<>();
            responseResultSender.initializeResultSender();
            try {
                for (Object itemID : itemTranslationMap.keySet()) {
                    String item = (String) itemID;
                    GTSBundleIdResponse abbreviationData = (GTSBundleIdResponse) itemTranslationMap.get(itemID);

                    BusinessObject businessObject = new BusinessObject(item);
                    businessObject.open(context);
                    transferObjectUtils.__init__(context, businessObject);
                    itemUtils.__init__(transferObjectUtils);
                    //ResponseResultSenderImpl.initializeResponseResultSender(businessObject, context);
                    try {
                        String mastership = businessObject.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.mastership.att")).getValue();
                        if (mastership.contains("PDM")) {
                            businessObject.close(context);
                            continue;
                        }
                    } catch (MatrixException e) {
                        UPDATE_ITEM_TRANSFER_LOGGER.error(e.getMessage());
                    }

                    UPDATE_ITEM_TRANSFER_LOGGER.info("Item preparing to re-transfer ::: " + businessObject.getName());
                    ProcessItemDataRequestType.DataArea.ItemVAL itemVal = null;
                    HashMap<String, String> itemsValueMap = itemUtils.createItemValueMap(businessObject, context);
                    itemVal = itemUtils.setItemValues(itemsValueMap, businessObject, abbreviationData, context);

                    if (itemVal != null) {
                        itemUtils.getItemValList().add(itemVal);
                        itemIdMap.put(itemVal.getItemID().getID() + "_" + businessObject.getRevision(), businessObject);
                    }
                    businessObject.close(context);
                    itemValList.add(itemVal);
                }
            } catch (MatrixException ex) {
                ex.printStackTrace();
                UPDATE_ITEM_TRANSFER_LOGGER.error(ex);
            }
            if (itemValList.size() > 0) {
                //List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList = itemUtils.getItemValList();
                TransferUtils transferUtils = new TransferUtils();
                results = (ArrayList<ResponseResult>) transferUtils.transferToLN(context, itemValList, itemIdMap, "NightlyUpdateToLN");
                responseResultSender.send(results);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            UPDATE_ITEM_TRANSFER_LOGGER.error(ex);
        }
        return true;
    }
   // Item transfer calling from LN transfer service
    @Override
    public Object processItemTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, boolean isService) throws Exception {
        Boolean isSuccessfulTransfer = Boolean.FALSE;
        ResponseResultSender responseResultSender = ResultSenderFactory.getResultSender(SenderType.LN_TRANSFER_RESPONSE_SENDER);
        ArrayList<ResponseResult> results = new ArrayList<ResponseResult>();
        ItemUtils itemUtils = new ItemUtils();
        TransferUtils transferUtils = new TransferUtils();
        ProcessItemDataRequestType.DataArea.ItemVAL itemVal = null;
        LinkedHashMap<String, BusinessObject> itemIdMap = new LinkedHashMap<>();
        List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList = new ArrayList<>();
        try {
            businessObject.open(context);
            // Initialize the utility classes
            transferObjectUtils.__init__(context, businessObject);
            itemUtils.__init__(transferObjectUtils);
            responseResultSender.initializeResultSender(context, businessObject);

            UPDATE_ITEM_TRANSFER_LOGGER.info("Item preparing to re-transfer ::: " + businessObject.getName());
            HashMap<String, String> itemsValueMap = itemUtils.createItemValueMap(businessObject, context);
            itemVal = itemUtils.setItemValues(itemsValueMap, businessObject, null, context);

            if (itemVal != null) {
                itemUtils.getItemValList().add(itemVal);
                itemIdMap.put(itemVal.getItemID().getID() + "_" + businessObject.getRevision(), businessObject);
            }
            itemValList.add(itemVal);

            transferUtils = new TransferUtils();
            results = (ArrayList<ResponseResult>) transferUtils.transferToLN(context, itemValList, itemIdMap, "ItemsTransfer");
            //responseResultSender.send(results);

            if (!ResponseResultSender.isAllItemTransfered) {
                throw new Exception("One or more of item is not transferred!");
            } else {
                isSuccessfulTransfer = Boolean.TRUE;
            }
        } catch (MatrixException ex) {
            ex.printStackTrace();
            UPDATE_ITEM_TRANSFER_LOGGER.error(ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            UPDATE_ITEM_TRANSFER_LOGGER.error(ex.getMessage());
            throw ex;
        } finally {
            businessObject.close(context);
        }

        return results;
    }

//    @Override
//    public Object processItemsTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils) throws Exception {
//        UPDATE_ITEM_TRANSFER_LOGGER.info("Item Data Collection Process is Started!!");
//        List<ResponseResult> responseResultList = new LinkedList<>();
//        ProcessItemDataRequestType.DataArea.ItemVAL itemVal = null;
//        LinkedHashMap<String, BusinessObject> itemIdMap = new LinkedHashMap<>();
//        List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList = new ArrayList<>();
//        for (BusinessObject businessObject : businessObjectList) {
//            try {
//                businessObject.open(context);
//                ItemUtils itemUtils = new ItemUtils();
//                // Initialize the utility classes
//                transferObjectUtils.__init__(context, businessObject);
//                itemUtils.__init__(transferObjectUtils);
//
//                UPDATE_ITEM_TRANSFER_LOGGER.info("Item preparing to transfer ::: " + businessObject.getName());
//                HashMap<String, String> itemsValueMap = itemUtils.createItemValueMap(businessObject, context);
//                itemVal = itemUtils.setItemValues(itemsValueMap, businessObject, null, context);
//
//                if (itemVal != null) {
//                    itemUtils.getItemValList().add(itemVal);
//                    itemIdMap.put(itemVal.getItemID().getID() + "_" + businessObject.getRevision(), businessObject);
//                }
//                itemValList.add(itemVal);
//
//            } catch (MatrixException ex) {
//                ex.printStackTrace();
//                UPDATE_ITEM_TRANSFER_LOGGER.error("Item Name :: " + businessObject.getName()
//                        + " Error occurred while preparing data for Item transfer  : " + ex.getMessage());
//                ResponseResult errorResponseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(),
//                        "Error occurred while preparing data for Item transfer  : " + ex.getMessage(), false);
//                responseResultList.add(errorResponseResult);
//                continue;
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                UPDATE_ITEM_TRANSFER_LOGGER.error("Item Name :: " + businessObject.getName()
//                        + " Error occurred while preparing data for Item transfer  : " + ex.getMessage());
//                ResponseResult errorResponseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(),
//                        "Error occurred while preparing data for Item transfer  : " + ex.getMessage(), false);
//                responseResultList.add(errorResponseResult);
//                continue;
//            } finally {
//                businessObject.close(context);
//            }
//        }
//        UPDATE_ITEM_TRANSFER_LOGGER.info("Item Data Collection Process is Ended!!");
//        if (!itemValList.isEmpty()) {
//            TransferUtils transferUtils = new TransferUtils();
//            responseResultList.addAll((ArrayList<ResponseResult>) transferUtils.transferToLN(context, itemValList, itemIdMap, "ItemsTransfer"));
//        } else {
//            UPDATE_ITEM_TRANSFER_LOGGER.info("itemValList is empty!! No Item Found to Transfer!! ");
//        }
//        return responseResultList;
//    }

  

    @Override
    public Object processSelectionCodeUpdateTransfer(Context context, ArrayList<BusinessObject> businessObjectList, HashMap itemSelectionCodeMap, TransferObjectUtils transferObjectUtils) throws Exception {
        return null;
    }

    @Override
    public Object processDisassembleItemsTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processDeliverableTransfer(Context context, BusinessObject businessObject, DisassembleDataModel disassembleDataModel, TransferObjectUtils transferObjectUtils) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processDisassembleStructureTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, DisassembleDataModel disassembleDataModel) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processItemsTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processBOMTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils, boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
