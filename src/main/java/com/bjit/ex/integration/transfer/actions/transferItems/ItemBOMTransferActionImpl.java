/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.transferItems;

import com.bjit.ex.integration.externalservices.GTS.bundleId.service.GTSBundleIdResponse;
import com.bjit.ex.integration.externalservices.PDM.service.PDMValItemTransferService;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.factory.ResultSenderFactory;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.actions.utilities.BomUtils;
import com.bjit.ex.integration.transfer.actions.utilities.BusinessObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.DisassembleDataModel;
import com.bjit.ex.integration.transfer.actions.utilities.GTSDataUtil;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.ItemUtils;
import com.bjit.ex.integration.transfer.actions.utilities.SelectionCodeType;
import com.bjit.ex.integration.transfer.actions.utilities.TransferUtils;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.infor.businessinterface.billofmaterial_val.ProcessItemBOMDataRequestType;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType.DataArea.ItemVAL;

import java.util.*;

import matrix.db.BusinessObject;
import matrix.db.BusinessObjectWithSelect;
import matrix.db.Context;
import matrix.db.RelationshipWithSelect;
import matrix.db.RelationshipWithSelectItr;

import javax.xml.ws.WebServiceException;

/**
 *
 * @author BJIT
 */
public class ItemBOMTransferActionImpl implements ITransferAction {

    private static final org.apache.log4j.Logger LN_RELEASED_ITEM_TRANSFER_LOGGER = org.apache.log4j.Logger.getLogger(ItemBOMTransferActionImpl.class);
    private PDMValItemTransferService pdmValItemTransferService = new PDMValItemTransferService();

    //private final List<String> restrictItemSendingType = Arrays.asList("CreateAssembly", "Provide", "ElementaryEndItem", "CreateMaterial");
    public ItemBOMTransferActionImpl() {
        LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Ln Released Item Transfer Process Has Been Started");
    }

    // BOM Transfer calling from LN transfer service
    @Override
    public Object processItemBOMTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils, BomUtils bomUtils, boolean isService) throws Exception {
        //ArrayList<ResponseResult> transferResult = new ArrayList<>();
        List<ResponseResult> responseResultList = new LinkedList<>();
        StringBuilder responseResultStrBuilder = new StringBuilder();
        ResponseResult responseResult = null;
        Set<BusinessObject> duplicateObjectId = new HashSet<>();
        List<String> transferedItems = new ArrayList<>();
        //  ResponseResult childItemErrorResponseResult = null;
        try {
            LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Root Type ::" + businessObject.getTypeName() + " Root Name :: " + businessObject.getName());
            ResponseResultSender responseResultSender = ResultSenderFactory.getResultSender(SenderType.LN_TRANSFER_RESPONSE_SENDER);
            GTSBundleIdResponse rootAbbreviationData = null;
            Set<String> missingChildItems = new HashSet<>();
            //List<ResponseResult> responseResultList = new LinkedList<>();
            ItemVAL itemVal = null;
            String transferredToErp = BusinessObjectUtils.getItemAttributeValue(context, businessObject.getObjectId(), ApplicationProperties.getProprtyValue("source.att.send.to.erp"));
            LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Item Already transferred : " + transferredToErp);
            transferedItems.add(businessObject.getName());
            LinkedHashMap<String, BusinessObject> itemIdMap = new LinkedHashMap<>();

            //if (transferredToErp.equalsIgnoreCase("FALSE")) {
            String rootTitleTermId = TransferObjectUtils.getObjTitleTermId(businessObject.getObjectId(), context);
            try {
                if (rootTitleTermId.length() > 0) {
                    rootAbbreviationData = GTSDataUtil.getAbbreviationData(rootTitleTermId);
                }
            } catch (Exception ex) {
                LN_RELEASED_ITEM_TRANSFER_LOGGER.error(ex.getMessage());
                ResponseResult gtsErrorResponseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(), ex.getMessage(), false);
                responseResultList.add(gtsErrorResponseResult);
                responseResultSender.send(responseResultList);
                throw ex;
            }

            HashMap<String, String> itemsValueMap = itemUtils.createItemValueMap(businessObject, context);
            itemVal = itemUtils.setItemValues(itemsValueMap, businessObject, rootAbbreviationData, context);

            if (itemVal != null) {
                itemUtils.getItemValList().add(itemVal);
                itemIdMap.put(itemVal.getItemID().getID() + "_" + businessObject.getRevision(), businessObject);
            }

            RelationshipWithSelectItr relationIterator = transferObjectUtils.expandBusinessObject(context, businessObject);
            HashMap<String, String[]> itemWithLengthWidthMap = new HashMap<>();
            HashMap<String, List<RelationshipWithSelect>> missingItemCheckInPDM = new HashMap<>();
            HashMap<String, GTSBundleIdResponse> abbreviationMap = new HashMap<>();
            GTSBundleIdResponse abbreviationData = null;
            boolean createBOMforRoot = true;
            boolean isPartialBOM = false;
            boolean hasChild = false;
            boolean bomMissingInLN = false;
            boolean transferableChild = true;
            while (relationIterator.next()) {
                hasChild = true;
                RelationshipWithSelect relationShipWithSelect = relationIterator.obj();
                BusinessObjectWithSelect businessObjectWithSelect = relationShipWithSelect.getTarget();
                String allowedTransferToERPstr = relationShipWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("item.transfer.to.erp") + "]");
                //allowedTransferToERPstr = "TRUE";
                String childObjId = businessObjectWithSelect.getSelectData("id");
                String childObjType = businessObjectWithSelect.getSelectData("type");
                String childObjName = businessObjectWithSelect.getSelectData("name");
                String childObjRevision = businessObjectWithSelect.getSelectData("revision");

                LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Child type : " + childObjType);
                LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Child name : " + childObjName);

                boolean isAllowedStructureToERP = false;
                String childObjectMbomType = businessObjectWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("item.att.mbom.type") + "]");

                if (childObjectMbomType.equalsIgnoreCase("Product_Model")) {
                    LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Child object :: " + businessObjectWithSelect.getSelectData("id") + " MBOM_TYPE :: " + childObjectMbomType);
                    bomMissingInLN = true;
                    missingChildItems.add(childObjName);
                    continue;
                }

                if (allowedTransferToERPstr.equalsIgnoreCase("TRUE") /*|| continiousAllowedTransferToERPstr.equalsIgnoreCase("TRUE")*/) {
                    isAllowedStructureToERP = true;
                } else {
                    missingChildItems.add(childObjName);
                    transferableChild = false;
                    bomMissingInLN = true;
                }

                LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Child : " + childObjName + " allowed in LN : " + isAllowedStructureToERP);

//                String childObjState = businessObjectWithSelect.getSelectData("current");
//                if (!childObjState.equalsIgnoreCase("RELEASED")) {
//                    LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Child Item Name (Item not allowed, Only RELEASED Item allowed in BOM) : " + childObjName + " State : " + isAllowedStructureToERP);
//                    bomMissingInLN = true;
//                    missingChildItems.add(childObjName);
//                    continue;
//                }

                if (createBOMforRoot) {
                    bomUtils.createObjectLinesMap(businessObject);
                    businessObject.close(context);
                    createBOMforRoot = false;
                }

                String childObjProject = businessObjectWithSelect.getSelectData("project");
                String childObjItemCode = businessObjectWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("source.item.code") + "]");

                if (TransferObjectUtils.VALItemType.contains(childObjType.trim()) && isAllowedStructureToERP /*&& childObjProject.equalsIgnoreCase("GLOBAL_COMPONENTS_INTERNAL")*/ /*&& (childObjItemCode.startsWith("VAL") || childObjItemCode.startsWith("GDI"))*/) {
                    //if (childObjName.contains("VAL") && childObjProject.equalsIgnoreCase(ApplicationProperties.getProprtyValue("collaboration.space.global.component"))) {

                    if (TransferObjectUtils.isAlreadySentToLN(businessObjectWithSelect)) {
                        //if (isAllowedStructureToERP) {
                        bomUtils.findBOMLines(context, relationShipWithSelect, itemWithLengthWidthMap);
                        //}
                    } else {
                        List<RelationshipWithSelect> VALItemRelList = new ArrayList<>();
                        if (missingItemCheckInPDM.containsKey(childObjName)) {
                            VALItemRelList = missingItemCheckInPDM.get(childObjName);
                        }
                        VALItemRelList.add(relationShipWithSelect);
                        missingItemCheckInPDM.put(childObjName, VALItemRelList);
                    }
                } else {

                    if (isAllowedStructureToERP) {
                        transferredToErp = BusinessObjectUtils.getItemAttributeValue(context, businessObjectWithSelect.getObjectId(), ApplicationProperties.getProprtyValue("source.att.send.to.erp"));
                        if (transferredToErp.equalsIgnoreCase("true")) {
                            bomUtils.findBOMLines(context, relationShipWithSelect, itemWithLengthWidthMap);
                        } else {
                            LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Child Name : " + childObjName + " Transfered to ERP : " + transferredToErp);
                            isPartialBOM = true;
                            bomMissingInLN = true;
                            missingChildItems.add(childObjName);
                        }
                    }
                    /*
                    Item sending for child is not considered. because system transfering item from the bottom 
                    of the structure. So it is expected that before sending parent all child will be transfered.
                    If an item is not transfered successfully then system will not send BOM. 
                     */
                }
            }

            if (missingItemCheckInPDM != null && missingItemCheckInPDM.size() > 0) {
                PDMValItemTransferService serviceResponse = new PDMValItemTransferService();
                ArrayList requestVALItemListToPDM = new ArrayList(missingItemCheckInPDM.keySet());
                LN_RELEASED_ITEM_TRANSFER_LOGGER.info("Requested VAL item to PDM : " + requestVALItemListToPDM);
                try {
                    pdmValItemTransferService.requestPDMVALItemTransferToLN(missingItemCheckInPDM);
                } catch (WebServiceException ex) {
                    LN_RELEASED_ITEM_TRANSFER_LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                    pdmValItemTransferService.setPDMServiceSystemError(ex.getMessage());
                } catch (Exception ex) {
                    LN_RELEASED_ITEM_TRANSFER_LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                    pdmValItemTransferService.setPDMServiceSystemError(ApplicationProperties.getProprtyValue("pdm.ln.service.unexpected.exception.response.message"));
                }
                if (!"".equals(pdmValItemTransferService.getPDMServiceSystemError()) || pdmValItemTransferService.getFailedValItemListToLN().size() > 0) {
                    isPartialBOM = true;
                    bomMissingInLN = true;
                    LN_RELEASED_ITEM_TRANSFER_LOGGER.info("VAL Child item is missing in LN, BOM will not send.");
                } else {
                    for (String itemName : missingItemCheckInPDM.keySet()) {
                        List<RelationshipWithSelect> relationshipWithSelectList = missingItemCheckInPDM.get(itemName);
                        for (RelationshipWithSelect relationshipWithSelect : relationshipWithSelectList) {
                            bomUtils.findBOMLines(context, relationshipWithSelect, itemWithLengthWidthMap);
                        }
                    }
                }
            }

            bomUtils.clearDuplicateLineMap();

            List<ItemVAL> itemValList = itemUtils.getItemValList();
            List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL> bomVallList = null;
            /*
                In itemValList there should be only one itemVAL. because system is sending only one item and
                it's first level BOM.
             */
//            for (ItemVAL itemVAL : itemValList) {
//                if (bomMissingInLN) {
//                    itemVAL.setSelectionCode(ApplicationProperties.getProprtyValue("LN.selection.code.BOM.missing.in.LN"));
//                } else if (!hasChild) {
//                    itemVal.setSelectionCode(ApplicationProperties.getProprtyValue("LN.selection.code.stand.alone.item"));
//                }
//            }
            //List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL> bomValList = null;
            if (bomMissingInLN == true && isPartialBOM == true) {
                //PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("BOM Missing in LN. Selection Code should be : EXT");
                responseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(), SelectionCodeType.EXT);
                responseResultList.add(responseResult);
            }
            if (!hasChild || (transferableChild == false && isPartialBOM == false)) {
                String checkSelectionCode = transferObjectUtils.getSelectionCode(context, businessObject, null);
                if (checkSelectionCode.equalsIgnoreCase("BOM")) {
                    if (!duplicateObjectId.contains(businessObject)) {
                        duplicateObjectId.add(businessObject);
                        bomUtils.createObjectLinesMap(businessObject);
                        businessObject.close(context);
                    }
                }

                responseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(), SelectionCodeType.ITM);
                responseResultList.add(responseResult);
            }

            List<ResponseResult> childItemErrorResponseResult = new ArrayList<>();

            if (!isPartialBOM) {
                bomVallList = bomUtils.createBOMVALList();
            } else {
                if (!"".equals(pdmValItemTransferService.getPDMServiceSystemError())) {
                    childItemErrorResponseResult.add(new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(), pdmValItemTransferService.getPDMServiceSystemError(), Boolean.FALSE));
                } else if (pdmValItemTransferService.getFailedValItemListToLN().size() > 0) {
                    childItemErrorResponseResult.addAll(pdmValItemTransferService.getFailedItemResponseResults());
                }
                if (missingChildItems.size() > 0) {

                    childItemErrorResponseResult.add(new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(), ApplicationProperties.getProprtyValue("ln.missing.child.item.message") + " : " + missingChildItems, Boolean.FALSE));

                }
            }
            TransferUtils transferUtils = new TransferUtils();

            ArrayList<ResponseResult> LNResponseResult = (ArrayList<ResponseResult>) transferUtils.transferToLN(itemValList, bomVallList, itemIdMap, /*missingItemCheckInPDM,*/ context, "LN", isService,transferedItems);
            responseResultList.addAll(LNResponseResult);

            if (childItemErrorResponseResult.size() > 0) {
                responseResultList.addAll(childItemErrorResponseResult);
            }
        } catch (Exception exp) {
            LN_RELEASED_ITEM_TRANSFER_LOGGER.error(exp.getMessage());
            throw exp;
        }
        return responseResultList;
    }

    @Override
    public Object processTranslationUpdateTransfer(Context context, HashMap itemTranslationMap,
            TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processDisassembleItemsTransfer(Context context, BusinessObject businessObject,
            TransferObjectUtils transferObjectUtils, ItemUtils itemUtils
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processDisassembleStructureTransfer(Context context, BusinessObject businessObject,
            TransferObjectUtils transferObjectUtils, DisassembleDataModel disassembleDataModel) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processItemTransfer(Context context, BusinessObject businessObject,
            TransferObjectUtils transferObjectUtils,
            boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processItemsTransfer(Context context, ArrayList<BusinessObject> businessObjectList,
            TransferObjectUtils transferObjectUtils) throws Exception {
        return null;
    }



    @Override
    public Object processSelectionCodeUpdateTransfer(Context context, ArrayList<BusinessObject> businessObjectList,
            HashMap itemSelectionCodeMap, TransferObjectUtils transferObjectUtils) throws Exception {
        return null;
    }

    @Override
    public Object processDeliverableTransfer(Context context, BusinessObject businessObject,
            DisassembleDataModel disassembleDataModel, TransferObjectUtils transferObjectUtils) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processBOMTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils, boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
