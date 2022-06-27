package com.bjit.ex.integration.transfer.actions.transferItems;

import com.bjit.ex.integration.externalservices.PDM.service.PDMValItemTransferService;
import com.bjit.ex.integration.transfer.actions.utilities.*;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.DisassembleDataModel;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.infor.businessinterface.billofmaterial_val.ProcessItemBOMDataRequestType;
import matrix.db.*;

import javax.xml.ws.WebServiceException;
import java.util.Set;
import java.util.*;

public class BOMTransferActionImpl implements ITransferAction {

    private static final org.apache.log4j.Logger PROCESS_BOM_TRANSFER_ACTION_LOGGER = org.apache.log4j.Logger.getLogger(BOMTransferActionImpl.class);

    @Override
    public Object processItemTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, boolean isService) throws Exception {
        return null;
    }

    @Override
    public Object processItemsTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils) throws Exception {
        return null;
    }

    @Override
    public Object processBOMTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils, boolean isService) throws Exception {
        PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("BOM  Transfer Data Gathering Process is Started.");
        List<ResponseResult> responseResultList = new LinkedList<>();
        LinkedHashMap<String, BusinessObject> itemIdMap = new LinkedHashMap<>();
        List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL> bomValList = new ArrayList<>();
        Set<BusinessObject> duplicateObjectId = new HashSet<>();
        for (BusinessObject businessObject : businessObjectList) {
            businessObject.open(context);
            TransferUtils transferUtils = new TransferUtils();
            transferObjectUtils.__init__(context, businessObject);
            BomUtils bomUtils = new BomUtils();
            bomUtils.__init__(transferObjectUtils);
            StringBuilder responseResultStrBuilder = new StringBuilder();
            ResponseResult responseResult = null;
            try {
                PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Parent Item Type :: " + businessObject.getTypeName() + " Parent Item Name :: " + businessObject.getName());
                Set<String> missingChildItems = new HashSet<>();
                String transferredToErp = BusinessObjectUtils.getItemAttributeValue(context, businessObject.getObjectId(), ApplicationProperties.getProprtyValue("source.att.send.to.erp"));
                PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Item Already transferred : " + transferredToErp);
                HashMap<String, String[]> itemWithLengthWidthMap = new HashMap<>();
                HashMap<String, List<RelationshipWithSelect>> missingItemCheckInPDM = new HashMap<>();
                boolean createBOMforRoot = true;
                boolean isPartialBOM = false;
                boolean hasChild = false;
                boolean bomMissingInLN = false;
                boolean transferableChild = true;
                itemIdMap.put(businessObject.getName() + "_" + businessObject.getRevision(), businessObject);

                RelationshipWithSelectItr relationIterator = transferObjectUtils.expandBusinessObject(context, businessObject);

                while (relationIterator.next()) {
                    hasChild = true;
                    RelationshipWithSelect relationShipWithSelect = relationIterator.obj();
                    BusinessObjectWithSelect businessObjectWithSelect = relationShipWithSelect.getTarget();
                    String allowedTransferToERPstr = relationShipWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("item.transfer.to.erp") + "]");

                    String childObjId = businessObjectWithSelect.getSelectData("id");
                    String childObjType = businessObjectWithSelect.getSelectData("type");
                    String childObjName = businessObjectWithSelect.getSelectData("name");
                    String childObjRevision = businessObjectWithSelect.getSelectData("revision");

                    PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Child Item Type : " + childObjType + " Child Item Name : " + childObjName);

                    boolean isAllowedStructureToERP = false;
                    String childObjectMbomType = businessObjectWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("item.att.mbom.type") + "]");

                    if (childObjectMbomType.equalsIgnoreCase("Product_Model")) {
                        PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Child Item (Product_Model Will not considered for BOM transfer) :: " + childObjName + " MBOM_TYPE :: " + childObjectMbomType);
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

                    PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Child Item Name : " + childObjName + " BOM allowed in LN : " + isAllowedStructureToERP);

                    String childObjState = businessObjectWithSelect.getSelectData("current");
                    if (!isService) {
                        if (!childObjState.equalsIgnoreCase("RELEASED")) {
                            PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Child Item Name (Item not allowed, Only RELEASED Item allowed in BOM) : " + childObjName + " State : " + isAllowedStructureToERP);
                            bomMissingInLN = true;
                            missingChildItems.add(childObjName);
                            continue;
                        }
                    }

                    if (createBOMforRoot) {
                        bomUtils.createObjectLinesMap(businessObject);
                        businessObject.close(context);
                        createBOMforRoot = false;
                    }

                    String childObjProject = businessObjectWithSelect.getSelectData("project");
                    String childObjItemCode = businessObjectWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("source.item.code") + "]");

                    if (TransferObjectUtils.VALItemType.contains(childObjType.trim()) && isAllowedStructureToERP) {
                        if (TransferObjectUtils.isAlreadySentToLN(businessObjectWithSelect)) {
                            bomUtils.findBOMLines(context, relationShipWithSelect, itemWithLengthWidthMap);
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
                                PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Child Item Name (Item not allowed, Only transferred Item allowed in BOM) : " + childObjName + " Transferred to ERP : " + transferredToErp);
                                isPartialBOM = true;
                                bomMissingInLN = true;
                                missingChildItems.add(childObjName);
                            }
                        }
                    }
                }

                if (missingItemCheckInPDM != null && missingItemCheckInPDM.size() > 0) {
                    PDMValItemTransferService serviceResponse = new PDMValItemTransferService();
                    ArrayList requestVALItemListToPDM = new ArrayList(missingItemCheckInPDM.keySet());
                    PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Requested VAL item to PDM : " + requestVALItemListToPDM);
                    ArrayList<String> responseVALItemListFromPDM = new ArrayList<>();
                    try {
                        responseVALItemListFromPDM = serviceResponse.getVALItemSendServiceResult(missingItemCheckInPDM);
                        PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Responded VAL item from PDM : " + responseVALItemListFromPDM);
                    } catch (WebServiceException ex) {
                        PROCESS_BOM_TRANSFER_ACTION_LOGGER.error(ex.getMessage());
                        ex.printStackTrace();
                        PDMValItemTransferService.pdmServiceErrorResponse = ex.getMessage();
                    } catch (Exception ex) {
                        PROCESS_BOM_TRANSFER_ACTION_LOGGER.error(ex.getMessage());
                        ex.printStackTrace();
                        PDMValItemTransferService.pdmServiceErrorResponse = ApplicationProperties.getProprtyValue("pdm.ln.service.unexpected.exception.response.message");
                    }
                    if (requestVALItemListToPDM.size() > responseVALItemListFromPDM.size()) {

                        isPartialBOM = true;
                        bomMissingInLN = true;
//                        if (serviceResponse.getFailedValItemListToLN().size() > 0) {
//                            missingChildItems.addAll(serviceResponse.getFailedValItemListToLN());
//                        }
                        PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("VAL Child item is missing in LN, Partial BOM will not send to LN.");
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

                if (!isPartialBOM) {
                    List bomList = bomUtils.createBOMVALList();
                    bomValList.addAll(bomList);
                } else {
                    PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("Partial BOM. BOM will not send to LN!! ");
                    if (PDMValItemTransferService.pdmServiceErrorResponse.length() > 0) {
                        responseResultStrBuilder.append(PDMValItemTransferService.pdmServiceErrorResponse);
                    }
                    if (missingChildItems.size() > 0) {
                        String responseMessage = ApplicationProperties.getProprtyValue("ln.missing.child.item.message") + " : " + missingChildItems;
                        if (responseResultStrBuilder.length() > 0) {
                            responseResultStrBuilder.append("</br>");
                            responseResultStrBuilder.append(responseMessage);
                        } else {
                            responseResultStrBuilder.append(responseMessage);
                        }
                    }
                    responseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(), responseResultStrBuilder.toString(), false);
                    responseResultList.add(responseResult);
                }

            } catch (Exception exp) {
                exp.printStackTrace();
                PROCESS_BOM_TRANSFER_ACTION_LOGGER.error("Item Name :: " + businessObject.getName() + " Error occurred while preparing BOM data (Children info) for BOM transfer  : " + exp.getMessage());
                responseResult = new ResponseResult(businessObject.getName().toUpperCase(), businessObject.getRevision(),
                        "Error occurred while preparing BOM data (Children info) for BOM transfer :" + exp.getMessage(), false);
                responseResultList.add(responseResult);
                continue;
            } finally {
                businessObject.close(context);
            }
        }
        PROCESS_BOM_TRANSFER_ACTION_LOGGER.info("BOM  Transfer Data Gathering Process is Ended.");
        TransferUtils transferUtils = new TransferUtils();
        responseResultList.addAll((ArrayList<ResponseResult>) transferUtils.transferToLN(context, bomValList, itemIdMap, "BOMTransfer"));
        return responseResultList;
    }

    @Override
    public Object processItemBOMTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils, BomUtils bomUtils, boolean isService) throws Exception {
        return null;
    }

    @Override
    public Object processTranslationUpdateTransfer(Context context, HashMap itemTranslationMap, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) throws Exception {
        return null;
    }

    @Override
    public Object processSelectionCodeUpdateTransfer(Context context, ArrayList<BusinessObject> businessObjectList, HashMap itemSelectionCodeMap, TransferObjectUtils transferObjectUtils) throws Exception {
        return null;
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
}
