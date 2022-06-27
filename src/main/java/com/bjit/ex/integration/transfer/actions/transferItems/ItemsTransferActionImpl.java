/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.transferItems;

import com.bjit.ex.integration.externalservices.GTS.bundleId.service.GTSBundleIdResponse;
import com.bjit.ex.integration.transfer.actions.utilities.*;
import static com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils.updateAttribute;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.DisassembleDataModel;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.factory.ResultSenderFactory;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.infor.businessinterface.item_val.ItemVAL;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.util.MatrixException;

import java.util.*;
import matrix.db.BusinessObjectWithSelect;
import matrix.db.RelationshipWithSelectItr;
import matrix.util.SelectList;

/**
 *
 * @author Sajjad
 */
public class ItemsTransferActionImpl implements ITransferAction {

    private static final org.apache.log4j.Logger PROCESS_ITEM_TRANSFER_ACTION_LOGGER = org.apache.log4j.Logger.getLogger(ItemsTransferActionImpl.class);

    @Override
    public Object processItemBOMTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils, BomUtils bomUtils,boolean isService) throws Exception {
        return null;
    }

    @Override
    public Object processTranslationUpdateTransfer(Context context, HashMap itemTranslationMap, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) throws Exception {
        return null;
    }

    @Override
    public Object processItemTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils,boolean isService) throws Exception {
        return null;
    }

    @Override
    public Object processItemsTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils) throws Exception {
        PROCESS_ITEM_TRANSFER_ACTION_LOGGER.info("Item Data Collection Process is Started!!");
        List<ResponseResult> responseResultList = new LinkedList<>();
        ProcessItemDataRequestType.DataArea.ItemVAL itemVal = null;
        LinkedHashMap<String, BusinessObject> itemIdMap = new LinkedHashMap<>();
        List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList = new ArrayList<>();
        GTSBundleIdResponse abbreviationData = null;
        HashMap<String, GTSBundleIdResponse> translationDataMap = new HashMap<>();
        for (BusinessObject businessObject : businessObjectList) {
            try {
                ItemUtils itemUtils = new ItemUtils();
                // Initialize the utility classes
                transferObjectUtils.__init__(context, businessObject);
                itemUtils.__init__(transferObjectUtils);

                /* VSIX-5305 start */
                String laterRevisionTransferredToLN = BusinessObjectUtils.findLaterRevisionTransferredToLN(context, businessObject);
                if (laterRevisionTransferredToLN != null && !laterRevisionTransferredToLN.isEmpty()) {
                    String errorMessage = "Later revision " + businessObject.getName() + " " + laterRevisionTransferredToLN + " already transferred to LN";
                    ResponseResult errorResponseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(),
                            errorMessage, false);
                    responseResultList.add(errorResponseResult);
                    continue;
                }
                /* VSIX-5305 end */

                businessObject.open(context);
                String titleTermId = TransferObjectUtils.getObjTitleTermId(businessObject.getObjectId(), context);

                if (translationDataMap.containsKey(titleTermId)) {
                    abbreviationData = translationDataMap.get(titleTermId);
                } else {
                    try {
                        if (titleTermId.length() > 0) {
                            abbreviationData = GTSDataUtil.getAbbreviationData(titleTermId);
                            translationDataMap.put(titleTermId, abbreviationData);
                        }
                    } catch (Exception ex) {
                        PROCESS_ITEM_TRANSFER_ACTION_LOGGER.error("Item Name :: " + businessObject.getName()
                                + " Error occurred while fetching translation data from GTS : " + ex.getMessage());
                        ResponseResult errorResponseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(),
                                "Error occurred while fetching translation data from GTS : " + ex.getMessage(), false);
                        responseResultList.add(errorResponseResult);
                        continue;
                    }
                }

                PROCESS_ITEM_TRANSFER_ACTION_LOGGER.info("Item preparing to transfer ::: " + businessObject.getName());
                HashMap<String, String> itemsValueMap = itemUtils.createItemValueMap(businessObject, context);
                itemVal = itemUtils.setItemValues(itemsValueMap, businessObject, abbreviationData, context);

                if (itemVal != null) {
                    itemUtils.getItemValList().add(itemVal);
                    itemIdMap.put(itemVal.getItemID().getID() + "_" + businessObject.getRevision(), businessObject);
                }
                itemValList.add(itemVal);

                // VSIX-3823 : Item's Release Purpose is 'Planning', Item will send twice in LN.
                String releasePurpose = "";
                try {
                    releasePurpose = BusinessObjectUtils.getItemAttributeValue(context, businessObject.getObjectId(),
                            ApplicationProperties.getProprtyValue("source.att.ReleasePurpose"));
                    if (releasePurpose.equalsIgnoreCase("Planning") && itemsValueMap.containsKey("signal")) {
                        PROCESS_ITEM_TRANSFER_ACTION_LOGGER.info(businessObject.getName() + " : Item's Release Purpose is 'Planning', Item will send twice in LN.");
                        itemValList.add(itemVal);
                    }
                } catch (Exception e) {
                    PROCESS_ITEM_TRANSFER_ACTION_LOGGER.error("Item Name :: " + businessObject.getName() + " Error occurred : " + e.getMessage());
                }
            } catch (MatrixException ex) {
                ex.printStackTrace();
                PROCESS_ITEM_TRANSFER_ACTION_LOGGER.error("Item Name :: " + businessObject.getName()
                        + " Error occurred while preparing data for Item transfer  : " + ex.getMessage());
                ResponseResult errorResponseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(),
                        "Error occurred while preparing data for Item transfer  : " + ex.getMessage(), false);
                responseResultList.add(errorResponseResult);
                continue;
            } catch (Exception ex) {
                ex.printStackTrace();
                PROCESS_ITEM_TRANSFER_ACTION_LOGGER.error("Item Name :: " + businessObject.getName()
                        + " Error occurred while preparing data for Item transfer  : " + ex.getMessage());
                ResponseResult errorResponseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(),
                        "Error occurred while preparing data for Item transfer  : " + ex.getMessage(), false);
                responseResultList.add(errorResponseResult);
                continue;
            } finally {
                businessObject.close(context);
            }
        }
        PROCESS_ITEM_TRANSFER_ACTION_LOGGER.info("Item Data Collection Process is Ended!!");
        if (!itemValList.isEmpty()) {
            TransferUtils transferUtils = new TransferUtils();
            responseResultList.addAll((ArrayList<ResponseResult>) transferUtils.transferToLN(context, itemValList, itemIdMap, "ItemsTransfer"));
        } else {
            PROCESS_ITEM_TRANSFER_ACTION_LOGGER.info("itemValList is empty!! No Item Found to Transfer!! ");
        }
        return responseResultList;
    }



    @Override
    public Object processSelectionCodeUpdateTransfer(Context context, ArrayList<BusinessObject> businessObjectList, HashMap itemSelectionCodeMap, TransferObjectUtils transferObjectUtils) throws Exception {
        PROCESS_ITEM_TRANSFER_ACTION_LOGGER.info("Selection Code Update Process is Started!!");
        List<ResponseResult> responseResultList = new LinkedList<>();
        List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList = new ArrayList<>();
        LinkedHashMap<String, BusinessObject> itemIdMap = new LinkedHashMap<>();
        SelectList buildBusStmnsts = transferObjectUtils.buildBusStmnts();
        for (BusinessObject businessObject : businessObjectList) {
            businessObject.open(context);
            String itemName = businessObject.getName();
            String objectId = businessObject.getObjectId();
            String selectCde = transferObjectUtils.getSelectionCode(context,businessObject,buildBusStmnsts);
            String selectionCode = (String) itemSelectionCodeMap.get(itemName.toUpperCase());
            PROCESS_ITEM_TRANSFER_ACTION_LOGGER.info("Item Name :: " + itemName + " Selection Code :: " + selectionCode);

            if (selectionCode.equalsIgnoreCase("ITM") && selectCde.equalsIgnoreCase("ITM")) {
                ResponseResult responseResult = new ResponseResult();
                responseResult.setItem(itemName.toUpperCase());
                responseResult.setRevision(businessObject.getRevision());
                responseResult.setResultText("RESULT OK");
                responseResult.setSuccessful(true);
                responseResultList.add(responseResult);

            } else {
                itemIdMap.put(itemName + "_" + businessObject.getRevision(), businessObject);
                ProcessItemDataRequestType.DataArea.ItemVAL itemVal = new ProcessItemDataRequestType.DataArea.ItemVAL();
                ProcessItemDataRequestType.DataArea.ItemVAL.ItemID itemID = new ProcessItemDataRequestType.DataArea.ItemVAL.ItemID();
                itemID.setID(itemName);
                itemVal.setItemID(itemID);
                itemVal.setSelectionCode(selectionCode);
                itemValList.add(itemVal);
                TransferObjectUtils.updateAttribute(context, objectId, ApplicationProperties.getProprtyValue("source.att.selection.code"), selectionCode);
                businessObject.close(context);
            }

        }

        TransferUtils transferUtils = new TransferUtils();
        if (itemValList.size() > 0) {
            responseResultList = (List<ResponseResult>) transferUtils.transferToLN(context, itemValList, itemIdMap, "ItemsTransfer");
        }
        return responseResultList;
    }

    @Override
    public Object processDeliverableTransfer(Context context, BusinessObject businessObject, DisassembleDataModel disassembleDataModel, TransferObjectUtils transferObjectUtils) throws Exception {
        return null;
    }

    @Override
    public Object processDisassembleItemsTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) throws Exception {
        return null;
    }

    @Override
    public Object processDisassembleStructureTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, DisassembleDataModel disassembleDataModel) throws Exception {
        return null;
    }

    @Override
    public Object processBOMTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils, boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
