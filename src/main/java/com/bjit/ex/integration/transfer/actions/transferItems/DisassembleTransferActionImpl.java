/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.transferItems;

import com.bjit.ex.integration.externalservices.GTS.bundleId.service.GTSBundleIdResponse;
import com.bjit.ex.integration.mapproject.utility.MapperUtil;
import com.bjit.ex.integration.transfer.actions.utilities.BomUtils;
import com.bjit.ex.integration.transfer.actions.utilities.BusinessObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.GTSDataUtil;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.DisassembleDataModel;
import com.bjit.ex.integration.transfer.actions.utilities.ItemUtils;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.TransferUtils;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.item.DisassembleItemInfo;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.ContractDetailsInfo;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.ItemDetailInfo;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.Line;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.factory.ResultSenderFactory;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ContractDetails;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails.Lines;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType.DataArea.ItemVAL;
import com.matrixone.apps.domain.util.FrameworkException;
import com.matrixone.apps.domain.util.MqlUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import matrix.db.BusinessObject;
import matrix.db.BusinessObjectWithSelect;
import matrix.db.Context;
import matrix.db.ExpansionWithSelect;
import matrix.db.Relationship;
import matrix.db.RelationshipWithSelect;
import matrix.db.RelationshipWithSelectItr;
import matrix.util.MatrixException;
import matrix.util.Pattern;
import matrix.util.SelectList;

/**
 *
 * @author Sajjad
 */
public class DisassembleTransferActionImpl implements ITransferAction{
    
    private static final org.apache.log4j.Logger DISASSEMBLE_ITEM_TRANSFER_LOGGER = org.apache.log4j.Logger.getLogger(DisassembleTransferActionImpl.class);

    @Override
    public Object processItemBOMTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils, BomUtils bomUtils,boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processTranslationUpdateTransfer(Context context, HashMap itemTranslationMap, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processItemTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils,boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processItemsTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils) throws Exception {
        return null;
    }

 
    @Override
    public Object processSelectionCodeUpdateTransfer(Context context, ArrayList<BusinessObject> businessObjectList, HashMap itemSelectionCodeMap, TransferObjectUtils transferObjectUtils) throws Exception {
        return null;
    }

    @Override
    public Object processDisassembleItemsTransfer(Context context, BusinessObject generalSystemBO, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) throws Exception {
        DisassembleDataModel disassembleDataModel = new DisassembleDataModel();
        ArrayList<ResponseResult> resultList = new ArrayList<>();
        List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList = new ArrayList<>();
        generalSystemBO.open(context);
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Disassemble Item ::" + generalSystemBO.getTypeName() + " Root Name :: " + generalSystemBO.getName());
        LinkedHashMap<String, BusinessObject> itemIdMap = new LinkedHashMap<>();
        DisassembleTransferActionImpl disassembleAction = new DisassembleTransferActionImpl(); 
        BusinessObjectUtils businessObjectUtils = new BusinessObjectUtils();

        /*
        Expand 'general system/deliverable' to get item from unloading object.
        */
        RelationshipWithSelectItr relationIterator = disassembleAction.expandGeneralSystem(context, generalSystemBO);
        if (relationIterator == null) {
            ResponseResult responseResult = new ResponseResult(generalSystemBO.getName(),ApplicationProperties.getProprtyValue("disassemble.message.no.item.connected") , false);
            responseResult.setRevision(generalSystemBO.getRevision());
            resultList.add(responseResult);
            return resultList;
        }
        BusinessObject connectedRootItemBO = null ;
        BusinessObject disassembleMainItemBO = null;
        
        HashMap<String, ArrayList<DisassembleItemInfo>> disassembleItemMap = new HashMap<>(); 
        TreeMap<Integer, ArrayList<String>> levelParentMap = new TreeMap<>();
        HashMap<String, TreeMap<Integer, String>> parentChildPositionMap = new HashMap<>();
        boolean isStandaloneDeliverableTransfer = false;
        while (relationIterator.next()) {
            RelationshipWithSelect relationShipWithSelect = relationIterator.obj();
            BusinessObjectWithSelect businessObjectWithSelect = relationShipWithSelect.getTarget();

            String childObjId = businessObjectWithSelect.getSelectData("id");            
            
            DisassembleItemInfo disassembleItemInfo = null;
            try {
                DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Finding item connected with Object Id ::: "+ childObjId);
                disassembleItemInfo = getLinkedItem(context, childObjId);
            } catch (Exception e) {
                DISASSEMBLE_ITEM_TRANSFER_LOGGER.error("Connected/Linked item found failed ::: "+e);
            }

            if (disassembleItemInfo != null) {
                if (disassembleItemInfo.isConnectedRootItem()) {                    
                    connectedRootItemBO = disassembleItemInfo.getItemBO();
                    String transferredToErp = BusinessObjectUtils.getItemAttributeValue(context, connectedRootItemBO.getObjectId(), ApplicationProperties.getProprtyValue("source.att.send.to.erp"));
                    String releasePurpose = BusinessObjectUtils.getItemAttributeValue(context, connectedRootItemBO.getObjectId(), ApplicationProperties.getProprtyValue("source.att.ReleasePurpose"));
                    if (transferredToErp.equalsIgnoreCase("FALSE") /*|| !releasePurpose.equalsIgnoreCase("Production")*/) {
                        connectedRootItemBO.open(context);
                        String itemName = connectedRootItemBO.getName();
                        String itemRev = connectedRootItemBO.getRevision();
                        ResponseResult responseResult = new ResponseResult(generalSystemBO.getName(),
                                MessageFormat.format(ApplicationProperties.getProprtyValue("disassemble.message.top.item.release.purpose"),
                                        itemName, itemRev, transferredToErp), false);
                        responseResult.setRevision(generalSystemBO.getRevision());
                        connectedRootItemBO.close(context);
                        resultList.add(responseResult);                        
                        DISASSEMBLE_ITEM_TRANSFER_LOGGER.info(MessageFormat.format(ApplicationProperties.getProprtyValue("disassemble.message.top.item.release.purpose"),
                                        itemName, itemRev, transferredToErp));
                        return resultList;
                    }
                    disassembleDataModel.setRootItemBO(disassembleItemInfo.getItemBO());

                    if (releasePurpose.equalsIgnoreCase("Planning")) {
                        connectedRootItemBO.open(context);
                        String itemName = connectedRootItemBO.getName();
                        String itemRev = connectedRootItemBO.getRevision();
                        DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Release Purpose is Planning for Top Item. Only Deliverable will send: " + itemName + " " + itemRev);
                        connectedRootItemBO.close(context);
                        isStandaloneDeliverableTransfer = true;
                        break;
                    }
                } else if (disassembleItemInfo.isDiassembleMainItem()) {
                    disassembleMainItemBO = disassembleItemInfo.getItemBO();
                    String transferredToErp = BusinessObjectUtils.getItemAttributeValue(context, disassembleMainItemBO.getObjectId(), ApplicationProperties.getProprtyValue("source.att.send.to.erp"));
                    if (transferredToErp.equalsIgnoreCase("FALSE")) {
                        disassembleMainItemBO.open(context);
                        String itemName = disassembleMainItemBO.getName();
                        String itemRev = disassembleMainItemBO.getRevision();
                        ResponseResult responseResult = new ResponseResult(generalSystemBO.getName(), 
                                MessageFormat.format(ApplicationProperties.getProprtyValue("disassemble.message.item.transfer.status"), 
                                        itemName,  itemRev, transferredToErp), false);
                        responseResult.setRevision(generalSystemBO.getRevision());
                        disassembleMainItemBO.close(context);
                        resultList.add(responseResult);                         
                        DISASSEMBLE_ITEM_TRANSFER_LOGGER.info(MessageFormat.format(ApplicationProperties.getProprtyValue("disassemble.message.item.transfer.status"), 
                                        itemName,  itemRev, transferredToErp));
                        return resultList;
                    }
                } else if (!disassembleItemInfo.isConnectedRootItem() && !disassembleItemInfo.isDiassembleMainItem()) {
                    BusinessObject itemBO = disassembleItemInfo.getItemBO();
                    BusinessObject parentBO = disassembleItemInfo.getParentBO();
                    String level = disassembleItemInfo.getLevel();
                    int levelInt = Integer.parseInt(level);
                    String position = disassembleItemInfo.getPosition();
                    String parentPosition = disassembleItemInfo.getParentPosition();
                    
                    String transferredToErp = BusinessObjectUtils.getItemAttributeValue(context, itemBO.getObjectId(), ApplicationProperties.getProprtyValue("source.att.send.to.erp"));

                    if (transferredToErp.equalsIgnoreCase("FALSE")) {
                        itemBO.open(context);
                        String itemName = itemBO.getName();
                        String itemRev = itemBO.getRevision();
                        ResponseResult responseResult = new ResponseResult(generalSystemBO.getName(),
                                MessageFormat.format(ApplicationProperties.getProprtyValue("disassemble.message.item.transfer.status"),
                                        itemName, itemRev, transferredToErp), false);
                        responseResult.setRevision(generalSystemBO.getRevision());
                        itemBO.close(context);
                        resultList.add(responseResult);                        
                        DISASSEMBLE_ITEM_TRANSFER_LOGGER.info(MessageFormat.format(ApplicationProperties.getProprtyValue("disassemble.message.item.transfer.status"),
                                        itemName, itemRev, transferredToErp));
                        return resultList;
                    }                    
                    
                    String parentKey = "";
                    String childKey = "";
                    
                    parentBO.open(context);
                    String parentType = parentBO.getTypeName();
                    String parentName = parentBO.getName();  
                    String parentRev = parentBO.getRevision();
                    parentKey = parentType+parentName+parentRev+level+parentPosition;
                    parentBO.close(context);

                    
                    itemBO.open(context);
                    String itemType = itemBO.getTypeName();
                    String itemName = itemBO.getName();
                    String itemRev = itemBO.getRevision();
                    childKey = itemType+itemName+itemRev+position;
                    itemBO.close(context);
                                      
                    String parentChildKey = parentKey + childKey;
                    
                    /*
                    Start: Populating parent and child data to level and position wise item sorting
                    */
                    if (levelParentMap.containsKey(levelInt)) {                        
                        ArrayList<String> parentList = levelParentMap.get(levelInt);
                        if (!parentList.contains(parentKey)) {
                            parentList.add(parentKey);
                        }                        
                        levelParentMap.put(levelInt, parentList);                        
                    } else {
                        ArrayList<String> parentList = new ArrayList<>();
                        parentList.add(parentKey);
                        levelParentMap.put(levelInt, parentList);
                    }
                    
                    if (parentChildPositionMap.containsKey(parentKey)) {
                        TreeMap<Integer, String> positionChildMap = parentChildPositionMap.get(parentKey);
                        int positionInt = Integer.parseInt(position);
                        if (!positionChildMap.containsKey(positionInt)) {                            
                            positionChildMap.put(positionInt, childKey);
                        } else {
                            DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Already child exists in the same position of the parent ::: "+ parentKey);
                        }
                    } else {
                        TreeMap<Integer, String> positionChildMap = new TreeMap<>();
                        int positionInt = Integer.parseInt(position);
                        positionChildMap.put(positionInt, childKey);
                        parentChildPositionMap.put(parentKey, positionChildMap);
                    }
                    /*
                    End: Populationg paretn and child data to level and position wise item sorting
                    */
                    
                    /*
                    Populating same level child in the same position in a list to count disassemble qunatity.
                    */ 
                    if (disassembleItemMap.containsKey(parentChildKey)) {                        
                        ArrayList<DisassembleItemInfo> disassembleItemList = disassembleItemMap.get(parentChildKey);
                        disassembleItemList.add(disassembleItemInfo);
                        disassembleItemMap.put(parentChildKey, disassembleItemList);
                    } else {
                        ArrayList<DisassembleItemInfo> disassembleItemList = new ArrayList<>();
                        disassembleItemList.add(disassembleItemInfo);
                        disassembleItemMap.put(parentChildKey, disassembleItemList);                        
                    }
                    
                }
            }               
        }
        
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Connected Root Item BO ::: "+connectedRootItemBO);
        if (disassembleMainItemBO == null) {
            //disassembleMainItemBO = connectedRootItemBO;
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("No Item connected with Loading Operation. "
                    + "Disassemble Item and Structure will no Transfer. "
                    + "Only Deliverable will send to LN.");
        }
        
        ArrayList<ItemDetailInfo> itemDetailInfoList = new ArrayList<>();
        String titleTermId = "";
        GTSBundleIdResponse abbreviationData = null;
        HashMap <String, GTSBundleIdResponse> translationMap = new HashMap<>();
        if (disassembleMainItemBO != null && !isStandaloneDeliverableTransfer) {
            // generate top item name and prepare LN item sending. 
            disassembleMainItemBO.open(context);
            String rootItemName = disassembleMainItemBO.getName();
            String[] rootItemNameArr = disassembleMainItemBO.getName().split("-");
            String subSequenceRootItemName = "";
            if (rootItemNameArr.length == 3){
                //For V6 item.
                subSequenceRootItemName = rootItemNameArr[0] + "-" + rootItemNameArr[2];
            } else{
                //For PDM name. 
                subSequenceRootItemName = disassembleMainItemBO.getName();
            }
            
            String disassembleItemName = rootItemName + "-" + "00" + "000" + "-" + subSequenceRootItemName;
            disassembleMainItemBO.close(context);
            
            titleTermId = TransferObjectUtils.getObjTitleTermId(disassembleMainItemBO.getObjectId(), context);
            if (translationMap.containsKey(titleTermId)){
                abbreviationData = translationMap.get(titleTermId);
            } else {
                abbreviationData = GTSDataUtil.getAbbreviationData(titleTermId);
                if (abbreviationData != null){
                    translationMap.put(titleTermId, abbreviationData);
                }
            }
            ItemVAL itemVal = null;
            itemVal = createItemVAL(context, disassembleMainItemBO, disassembleItemName, transferObjectUtils, itemUtils, itemIdMap, abbreviationData);
            itemVal.setInventoryUnit(ApplicationProperties.getProprtyValue("default.inventory.unit.for.disassemble.item"));
            itemVal.setPurchaseUnit(ApplicationProperties.getProprtyValue("default.purchase.unit.for.disassemble.item"));
            itemVal.setSourceItem(ApplicationProperties.getProprtyValue("default.reference.item.for.disassemble.structure"));        
            itemValList.add(itemVal);

            String deliverableQuantity = generalSystemBO.getAttributeValues(context, ApplicationProperties.getProprtyValue("source.att.deliberable.quantity")).getValue();
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Deliverable qunatity ::: " + deliverableQuantity);
            // Add top item in the disassemble-structure. 
            ItemDetailInfo itemDetailInfo = new ItemDetailInfo();
            itemDetailInfo.setDisAssemblyItem(disassembleItemName);
            itemDetailInfo.setSortingPosition("1");
            itemDetailInfo.setDisAssembleQuantity("1");

            ArrayList<Line> lineList = new ArrayList<>();
            Line line = new Line();
            line.setSubItem(rootItemName);
            line.setQuantity("1");
            line.setPosition("1");
            lineList.add(line);
            itemDetailInfo.setLines(lineList);

            itemDetailInfoList.add(itemDetailInfo);

            int sortingPosition = 2;
            /*
                Item sending to LN from Top to Bottom sorting approach. 
             */

            for (int level : levelParentMap.keySet()) {
                ArrayList<String> parentList = levelParentMap.get(level);
                for (String parentKey : parentList) {
                    TreeMap<Integer, String> positionChildMap = parentChildPositionMap.get(parentKey);
                    for (int position : positionChildMap.keySet()) {
                        String childKey = positionChildMap.get(position);
                        String parentChildKey = parentKey + childKey;
                        DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Parent and child info (parentChildKey) ::: " + parentChildKey);
                        ArrayList<DisassembleItemInfo> disassembleList = new ArrayList<>();
                        disassembleList = disassembleItemMap.get(parentChildKey);
                        int numberOfSameChild = disassembleList.size();

                        if (numberOfSameChild > 0) {
                            //same item will not send multiple time to LN.
                            DisassembleItemInfo disassembleItemInfo = disassembleList.get(0);

                            BusinessObject itemBO = disassembleItemInfo.getItemBO();
                            BusinessObject parentBO = disassembleItemInfo.getParentBO();
    //                      String level = disassembleItemInfo.getLevel();
    //                      String position = disassembleItemInfo.getPosition();

                            /*String onlyDegit = "\\d+";
                            if (level.matches(onlyDegit)) {
                                level = String.format("%02d", Integer.parseInt(level));
                            }
                            if (position.matches(onlyDegit)) {
                                position = String.format("%03d", Integer.parseInt(position));
                            }*/
                            String levelStr = String.format("%02d", level);
                            String positionStr = String.format("%03d", position);

                            parentBO.open(context);
                            String parentName = parentBO.getName();
                            parentBO.close(context);
                            String[] parentNameArr = parentName.split("-");
                            String subSequenceParentName = "";
                            if (parentNameArr.length==3) {
                                subSequenceParentName = parentNameArr[0] + "-" + parentNameArr[2];
                            } else{
                                subSequenceParentName = parentName;
                            }                         
                            itemBO.open(context);
                            String itemName = itemBO.getName();
                            itemBO.close(context);

                            String levelPosition = levelStr + positionStr;
                            disassembleItemName = itemName + "-" + levelPosition + "-" + subSequenceParentName;
                            
                            titleTermId = TransferObjectUtils.getObjTitleTermId(itemBO.getObjectId(), context);
                            if (translationMap.containsKey(titleTermId)) {
                                abbreviationData = translationMap.get(titleTermId);
                            } else {
                                abbreviationData = GTSDataUtil.getAbbreviationData(titleTermId);
                                if (abbreviationData != null) {
                                    translationMap.put(titleTermId, abbreviationData);
                                }
                            }
                            
                            //child item send preparation to LN
                            itemVal = createItemVAL(context, itemBO, disassembleItemName, transferObjectUtils, itemUtils, itemIdMap, abbreviationData);
                            itemVal.setInventoryUnit(ApplicationProperties.getProprtyValue("default.inventory.unit.for.disassemble.item"));
                            itemVal.setPurchaseUnit(ApplicationProperties.getProprtyValue("default.purchase.unit.for.disassemble.item"));
                            itemVal.setSourceItem(ApplicationProperties.getProprtyValue("default.reference.item.for.disassemble.structure"));
                            itemValList.add(itemVal);

                            // Add top item in the disassemble-structure. 
                            itemDetailInfo = new ItemDetailInfo();
                            itemDetailInfo.setDisAssemblyItem(disassembleItemName);
                            itemDetailInfo.setSortingPosition("" + sortingPosition);
                            itemDetailInfo.setDisAssembleQuantity("" + numberOfSameChild);

                            lineList = new ArrayList<>();
                            line = new Line();
                            line.setSubItem(rootItemName);
                            line.setQuantity("" + numberOfSameChild);
                            line.setPosition("1");
                            lineList.add(line);
                            itemDetailInfo.setLines(lineList);

                            itemDetailInfoList.add(itemDetailInfo);

                            sortingPosition++;
                        }

                    }

                }
            }
        }
        /*
        Item sending without level and position wise sorting. 
        */
        /*
        for (String parentChildKey : disassembleItemMap.keySet()) {            
            ArrayList<DisassembleItemInfo> disassembleList = disassembleItemMap.get(parentChildKey);
            int numberOfSameChild = disassembleList.size();
            //same item will not send multiple time to LN. 
            if (numberOfSameChild > 0) {
                DisassembleItemInfo disassembleItemInfo =  disassembleList.get(0);
                
                BusinessObject itemBO = disassembleItemInfo.getItemBO();
                BusinessObject parentBO = disassembleItemInfo.getParentBO();
                String level = disassembleItemInfo.getLevel();
                String position = disassembleItemInfo.getPosition();
                
                DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Level ::: " + level);
                DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("position ::: " + position);
                
                String onlyDegit = "\\d+";
                if (level.matches(onlyDegit)) {
                    level = String.format("%02d", Integer.parseInt(level));
                }
                if (position.matches(onlyDegit)) {
                    position = String.format("%03d", Integer.parseInt(position));
                }                
                String childKey = "";

                parentBO.open(context);
                String parentName = parentBO.getName();
                parentBO.close(context);
                String[] parentNameArr = parentName.split("-");
                String subSequenceParentName = parentNameArr[0] + "-" + parentNameArr[2];

                itemBO.open(context);
                String itemName = itemBO.getName();
                itemBO.close(context);

                String levelPosition = level + position;                    
                disassembleItemName = itemName + "-" + levelPosition + "-" + subSequenceParentName;
                
                //child item send preparation to LN
                itemVal = createItemVAL(context,itemBO,disassembleItemName,transferObjectUtils, itemUtils,itemIdMap);
                itemValList.add(itemVal);
                
                // Add top item in the disassemble-structure. 
                itemDetailInfo = new ItemDetailInfo();
                itemDetailInfo.setDisAssemblyItem(disassembleItemName);
                itemDetailInfo.setSortingPosition(""+sortingPosition);
                itemDetailInfo.setDisAssembleQuantity(""+numberOfSameChild);

                lineList = new ArrayList<>();
                line = new Line();
                line.setSubItem(rootItemName);
                line.setQuantity(""+numberOfSameChild);
                line.setPosition("1");
                lineList.add(line);
                itemDetailInfo.setLines(lineList);

                itemDetailInfoList.add(itemDetailInfo);
                
                sortingPosition ++;
            }
        }*/
        generalSystemBO.close(context);
        disassembleDataModel.setItemDetails(itemDetailInfoList);
        disassembleDataModel.setDisassembleItemVALList(itemValList);
        TransferUtils transferUtils = new TransferUtils();
        boolean isAllDisassebleItemsTransferDone = true;
        if (!itemValList.isEmpty()) {
            resultList = (ArrayList<ResponseResult>) transferUtils.transferToLN(context, itemValList, itemIdMap, "DisassembleItems");
            //transferUtils.transferToLN(context, itemValList, itemIdMap, "DisassembleItems");
            if (resultList != null && !resultList.isEmpty()) {
                for (ResponseResult result : resultList) {
                    if (!result.isSuccessful()) {
                        isAllDisassebleItemsTransferDone = false;
                    }
                }
            }
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("All Disassemble Item Transferred Successfully ::: " + isAllDisassebleItemsTransferDone);
        } else {
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("No Disassemble Item Found for Trnasfer!!");
        }
        
        if (isAllDisassebleItemsTransferDone) {
            return disassembleDataModel;
        }
        return resultList;
    }

    @Override
    public Object processDisassembleStructureTransfer(Context context, BusinessObject GeneralSystemBO, TransferObjectUtils transferObjectUtils,DisassembleDataModel disassembleDataModel) throws Exception {
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Disassemble Structure Data Generation to LN Service Class.");
        ArrayList<ResponseResult> results = new ArrayList<>();
        DisAssemblyLinesVAL disAssemblyLinesVALForRequest = new DisAssemblyLinesVAL();
        
        ContractDetailsInfo contractDetailsInfo = disassembleDataModel.getContractDetailsInfo();
        
        ContractDetails contractDetails = new ContractDetails();
        //add contractDetails info.
        disAssemblyLinesVALForRequest.setContractDetails(contractDetails);
        contractDetails.setActivity(contractDetailsInfo.getActivity());
        contractDetails.setOrderQty(new BigDecimal(contractDetailsInfo.getOrderQty()));
        contractDetails.setProject(contractDetailsInfo.getProject());
        contractDetails.setContract(contractDetailsInfo.getContract());
        contractDetails.setContractLine(contractDetailsInfo.getContractLine());
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Deliverable Number ::: "+contractDetailsInfo.getDeliverableNumber());
        contractDetails.setDeliverableNumber(new BigInteger(contractDetailsInfo.getDeliverableNumber()));
        contractDetails.setDeliverableItem(contractDetailsInfo.getDeliverableItem());
                
        ArrayList<ItemDetailInfo> itemDetailInfoList = disassembleDataModel.getItemDetails();
        for (ItemDetailInfo itemDetailInfo : itemDetailInfoList) {
            
            ItemDetails itemDetails = new ItemDetails();
            disAssemblyLinesVALForRequest.getItemDetails().add(itemDetails);

            itemDetails.setDisAssemblyItem(itemDetailInfo.getDisAssemblyItem());
            itemDetails.setSortingPosition(itemDetailInfo.getSortingPosition());
            itemDetails.setDisAssembleQuantity(new BigDecimal(itemDetailInfo.getDisAssembleQuantity()));

            ArrayList<Line> linesInfo = itemDetailInfo.getLines();
            
            for (Line lineInfo : linesInfo) {
                Lines lines = new Lines(); 
                itemDetails.getLines().add(lines);
                lines.setSubItem(lineInfo.getSubItem());
                lines.setPosition(new BigInteger("1"));
                lines.setQuantity(new BigDecimal(lineInfo.getQuantity()));   
                
            }      
           
        }
        TransferUtils transferUtils = new TransferUtils();
        results = (ArrayList<ResponseResult>) transferUtils.transferToLN(context, GeneralSystemBO, disAssemblyLinesVALForRequest, "DisassembleStructure");
        return results;
    }
    /**
     * 
     * @param context
     * @param generalSystemBO
     * @return 
     */
    
     public RelationshipWithSelectItr expandGeneralSystem (Context context, BusinessObject generalSystemBO) {

        SelectList selectRelStmts = new SelectList();
        SelectList selectBusStmts = new SelectList();
        selectBusStmts.add(ApplicationProperties.getProprtyValue("source.object.id"));
        Pattern typePattern = new Pattern(ApplicationProperties.getProprtyValue("item.type.link"));
        Pattern relPattern = new Pattern(ApplicationProperties.getProprtyValue("rel.item.to.process.cnx"));
        String busWhereExpression = "";
        String relWhereExpression = "";

        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short("1");

        try {
            long expandStartTime = System.currentTimeMillis();
            expandResult = generalSystemBO.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    false, true, // Get from .... true, false -> get to
                    expandLevel, busWhereExpression, relWhereExpression, false);
            int noOfConnection = expandResult.getRelationships().size();
            if (expandResult.getRelationships().isEmpty()) return null;
            relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("General System rel list size (Total Link/Conection Object)::: "+noOfConnection);
            long expandEndTime = System.currentTimeMillis();
            long expandElpsedTime = expandEndTime - expandStartTime;
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Expand Elapsed Time ::: " + expandElpsedTime);
        } catch (MatrixException ex) {
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.error(ex.getMessage());
        }

        return relItr;
    }
     
     /**
      * 
      * @param context
      * @param childObjId
      * @return
      * @throws MatrixException 
      */
    private DisassembleItemInfo getLinkedItem(Context context, String childObjId) throws Exception {
        DisassembleItemInfo disassembleItemInfo = null;        
        HashMap<String,String> parentChildMap =  new HashMap<>();
        BusinessObject MfgProductionPlanningBO =  new BusinessObject(childObjId);
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("print bus ")
                .append(childObjId)
                .append(" select paths.path ")
                .append(" dump |; ");                

        String connectedItemFindQuery = commandBuilder.toString();  
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Connected Item Find Query :: "+connectedItemFindQuery);
        String pathFindResult = ""; 
        try {
            pathFindResult = MqlUtil.mqlCommand(context, connectedItemFindQuery);
        } catch (FrameworkException e) {
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.error("Find Connection Query Failed :: " + e);
            return null;
        }
        ArrayList<String> relList = new ArrayList<>();
        java.util.regex.Pattern relTypePattern = null;
        try {
            MapperUtil mapperUtil = new MapperUtil();
            relList = mapperUtil.getRelNameList();            
            if (relList != null && !relList.isEmpty()) {
                StringBuilder relTypeRegExpBuilder = new StringBuilder();
                for (int i = 0; i < relList.size(); i++) {
                    if (i > 0) {
                        relTypeRegExpBuilder.append("|");
                    }
                    relTypeRegExpBuilder.append("(?=.*").append(relList.get(i)).append(")");
                }
                relTypePattern = java.util.regex.Pattern.compile(relTypeRegExpBuilder.toString());
                DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Rel pattern : "+relTypePattern.toString());
            }
        } catch (Exception e) {
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.error("Faield to read rel name from XML map or create rel pattern :: " + e);
        }        
        
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Connection/Link Path Find Result ::  "+pathFindResult);
        if (pathFindResult.contains(ApplicationProperties.getProprtyValue("item.type.unloading"))) {//DELLmiLoadingOperationInstance
            String[] unloadingItemFindResultArr = pathFindResult.split("\\|");
            if (unloadingItemFindResultArr.length == 2) {
                DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Finding Item conneted with Unloading Object!!");
                for (int i = 0; i < unloadingItemFindResultArr.length; i++) {
                    String unloadingConnectionResult = unloadingItemFindResultArr[i];
                    if (!unloadingConnectionResult.contains(ApplicationProperties.getProprtyValue("item.type.unloading"))) {
                        int level = 0;
                        String parentRelID = "";
                        String childRelID = "";
                        String lastLevelChildConnectionID = "";
                        ArrayList<String> connectionIdList = new ArrayList<>();
                        String[] unloadingConnectionResultArr = unloadingConnectionResult.split(",");
                        for (int j = 0; j < unloadingConnectionResultArr.length; j++) {
                            //if (unloadingConnectionResultArr[j].equalsIgnoreCase("DELFmiFunctionIdentifiedInstance"))
                            if (relTypePattern != null) {
                                if (relTypePattern.matcher(unloadingConnectionResultArr[j]).find()) {
                                    lastLevelChildConnectionID = unloadingConnectionResultArr[j + 1];
                                    connectionIdList.add(lastLevelChildConnectionID);
                                    level++;
                                }
                            }                            
                        }                           
                        if (lastLevelChildConnectionID != null && !lastLevelChildConnectionID.isEmpty()) {
                            parentChildMap = getParentChildFromUnloadingRel(context, lastLevelChildConnectionID);
                            if (parentChildMap != null && !parentChildMap.isEmpty()) {
                                String itemId = parentChildMap.get("ChildID");
                                String parentId = parentChildMap.get("ParentID");

                                BusinessObject itemBO = new BusinessObject(itemId);
                                BusinessObject parentBO = new BusinessObject(parentId);

                                Relationship relationship = new Relationship(lastLevelChildConnectionID);
                                String position = "";
                                String parentPosition = "";
                                try {
                                    position = relationship.getAttributeValues(context, ApplicationProperties.getProprtyValue("source.rel.position")).getValue();
                                } catch (Exception e) {
                                    DISASSEMBLE_ITEM_TRANSFER_LOGGER.error("Position value read ::  " + e.getMessage());
                                }
                                if (connectionIdList.size() == 1) {
                                    parentPosition = "0"; // item's parent is root item. 
                                } else if (connectionIdList.size() > 1) {
                                    int connectionListSize = connectionIdList.size();
                                    String parentConnetionId = connectionIdList.get(connectionListSize - 2);
                                    Relationship parentRelationship = new Relationship(parentConnetionId);
                                    parentPosition = parentRelationship.getAttributeValues(context, ApplicationProperties.getProprtyValue("source.rel.position")).getValue();

                                }
                                disassembleItemInfo = new DisassembleItemInfo();
                                disassembleItemInfo.setPosition(position);
                                disassembleItemInfo.setParentPosition(parentPosition);
                                disassembleItemInfo.setItemBO(itemBO);
                                disassembleItemInfo.setParentBO(parentBO);
                                disassembleItemInfo.setConnectionId(lastLevelChildConnectionID);
                                disassembleItemInfo.setLevel(Integer.toString(level));
                                disassembleItemInfo.setDiassembleMainItem(false);
                                disassembleItemInfo.setConnectedRootItem(false);

                            }
                        }                       
                        
                        DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Child level (From Unloading Item): " + level);
                        DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Patent-Child Map From Unloading Item (Scope link) :: " + parentChildMap);
                    }
                }              
            }
        } else if (pathFindResult.contains(ApplicationProperties.getProprtyValue("deliverable.object.type"))){
            String[] unloadingItemFindResultArr = pathFindResult.split("\\|");
            if (unloadingItemFindResultArr.length == 2) {
                DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Finding Top-Item conneted with Disassemble Item!!");
                for (int i = 0; i < unloadingItemFindResultArr.length; i++) {
                    String unloadingConnectionResult = unloadingItemFindResultArr[i];
                    if (!unloadingConnectionResult.contains(ApplicationProperties.getProprtyValue("deliverable.object.type"))) {
                        String[] unloadingConnectionResultArr = unloadingConnectionResult.split(",");
                        String connectedRootPhysicalID = unloadingConnectionResultArr[2];
                        //parentChildMap.put("CONNECTED-ROOT-ITEM", connectedRootPhysicalID);
                        //DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Root Item From Unloading Item (Scope link) :: " + parentChildMap);
                        try {
                            BusinessObject itemBO = new BusinessObject(connectedRootPhysicalID);
                            disassembleItemInfo = new DisassembleItemInfo();
                            disassembleItemInfo.setItemBO(itemBO);
                            disassembleItemInfo.setDiassembleMainItem(false);
                            disassembleItemInfo.setConnectedRootItem(true);
                        } catch (MatrixException e) {
                            DISASSEMBLE_ITEM_TRANSFER_LOGGER.error("Finding Top-Item conneted with Disassemble Item Failed ::: "+e);
                        }                                              
                    }
                }
            }
        } else if (pathFindResult.contains(ApplicationProperties.getProprtyValue("item.type.loading"))) { //DELLmiLoadingOperationInstance
            String[] loadingItemFindResultArr = pathFindResult.split("\\|");
               if (loadingItemFindResultArr.length == 2) {
                DISASSEMBLE_ITEM_TRANSFER_LOGGER.info("Finding Item conneted with Loading Object!!");
                for (int i = 0; i < loadingItemFindResultArr.length; i++) {
                    String loadingConnectionResult = loadingItemFindResultArr[i];
                    if (!loadingConnectionResult.contains(ApplicationProperties.getProprtyValue("item.type.loading"))) {
                        if (relTypePattern!= null && relTypePattern.matcher(loadingConnectionResult).find()) {
                            int level = 0;
                            String parentRelID = "";
                            String childRelID = "";
                            String lastLevelChildConnectionID = "";
                            String[] loadingConnectionResultArr = loadingConnectionResult.split(",");
                            String childPhysicalID = "";
                            for (int j = 0; j < loadingConnectionResultArr.length; j++) {
                                if (relTypePattern.matcher(loadingConnectionResultArr[j]).find()) {
                                    lastLevelChildConnectionID = loadingConnectionResultArr[j + 1];
                                    level++;
                                }
                            }
                            if (lastLevelChildConnectionID != null && !lastLevelChildConnectionID.isEmpty()) {
                                childPhysicalID = getChildFromRel(context, lastLevelChildConnectionID);
                                //parentChildMap.put("DISS-LOADING-MAIN-ITEM", childPhysicalID);
                                if (childPhysicalID != null && !childPhysicalID.isEmpty()) {
                                    DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Finding Item conneted with Loading Object (Child-Item) ::: " + childPhysicalID);
                                    try {
                                        BusinessObject itemBO = new BusinessObject(childPhysicalID);
                                        disassembleItemInfo = new DisassembleItemInfo();
                                        disassembleItemInfo.setItemBO(itemBO);
                                        disassembleItemInfo.setConnectionId(lastLevelChildConnectionID);
                                        disassembleItemInfo.setDiassembleMainItem(true);
                                        disassembleItemInfo.setConnectedRootItem(false);
                                    } catch (MatrixException e) {
                                        DISASSEMBLE_ITEM_TRANSFER_LOGGER.error("Finding Item conneted with Loading Object Failed ::: " + e);
                                    }
                                }
                            }
                        } else {
                            String[] loadingConnectionResultArr = loadingConnectionResult.split(",");
                            String mainItemPhysicalID = loadingConnectionResultArr[2];
                            DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Finding Item conneted with Loading Object (Top-Item) ::: " + mainItemPhysicalID);
                            try {
                                BusinessObject itemBO = new BusinessObject(mainItemPhysicalID);
                                disassembleItemInfo = new DisassembleItemInfo();
                                disassembleItemInfo.setItemBO(itemBO);
                                disassembleItemInfo.setDiassembleMainItem(true);
                                disassembleItemInfo.setConnectedRootItem(false);
                            } catch (MatrixException e) {
                                DISASSEMBLE_ITEM_TRANSFER_LOGGER.error("Finding Item conneted with Loading Object Failed ::: " + e);
                            }
                        }
                    }
                }              
            }
        }
        return disassembleItemInfo;
    }
    /**
     * 
     * @param context
     * @param unloadingConnectionID
     * @return
     * @throws FrameworkException 
     */
    private HashMap getParentChildFromUnloadingRel(Context context, String unloadingConnectionID) throws FrameworkException {
        HashMap<String,String> parentChildMap =  new HashMap<>();
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("print connection ")
                .append(unloadingConnectionID)
                .append(" select to.id from.id ")
                .append(" dump |; ");                

        String parentChildFromUploadingQuery = commandBuilder.toString();  
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Parent-Child From Unloading Query :: "+parentChildFromUploadingQuery);
        String parentChildFromUnloadingQueryResult = ""; 
        try {
            parentChildFromUnloadingQueryResult = MqlUtil.mqlCommand(context, parentChildFromUploadingQuery);
            String[] parentChildFromUnloadingQueryResultArr = parentChildFromUnloadingQueryResult.split("\\|");

            if (parentChildFromUnloadingQueryResultArr.length == 2) {
                parentChildMap.put("ParentID", parentChildFromUnloadingQueryResultArr[1]);
                parentChildMap.put("ChildID", parentChildFromUnloadingQueryResultArr[0]);
            }
        } catch (FrameworkException e) {
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.error("Extranct Item Name from Unloading Item failed :: " + e);
        }
        return parentChildMap;
    }
    /*private String getParentFromRel(Context context, String unloadingConnectionID) throws FrameworkException {
        
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("print connection ")
                .append(unloadingConnectionID)
                .append(" select from.id ")
                .append(" dump |; ");                

        String parentFromUploadingQuery = commandBuilder.toString();  
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Parent From Unloading Query :: "+parentFromUploadingQuery);
        String parentFromUnloadingQueryResult = ""; 
        parentFromUnloadingQueryResult = MqlUtil.mqlCommand(context, parentFromUploadingQuery);
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Parent From Unloading Query Result :: "+parentFromUnloadingQueryResult);
        return parentFromUnloadingQueryResult;
    }*/
    
    /**
     * 
     * @param context
     * @param unloadingConnectionID
     * @return
     * @throws FrameworkException 
     */
    private String getChildFromRel(Context context, String unloadingConnectionID) throws FrameworkException {

        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("print connection ")
                .append(unloadingConnectionID)
                .append(" select to.id")
                .append(" dump |; ");                

        String childFromUploadingQuery = commandBuilder.toString();  
        DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Child From Unloading Query :: "+childFromUploadingQuery);
        String childFromUnloadingQueryResult = ""; 
        try {
            childFromUnloadingQueryResult = MqlUtil.mqlCommand(context, childFromUploadingQuery);
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.debug("Child From Unloading Query Result :: " + childFromUnloadingQueryResult);
        } catch (FrameworkException e) {
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.error("Find Child from Rel id Failed :: " + e);
        }
        return childFromUnloadingQueryResult;
    }
    /**
     * 
     * @param context
     * @param businessObject
     * @param disassembleItemName
     * @param transferObjectUtils
     * @param itemUtils
     * @param itemIdMap
     * @return
     * @throws Exception 
     */
    private ItemVAL createItemVAL(Context context, BusinessObject businessObject,String disassembleItemName, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils,LinkedHashMap<String, BusinessObject> itemIdMap, GTSBundleIdResponse abbreviationData) throws Exception {
        ItemVAL itemVal = null;
        transferObjectUtils.__init__(context, businessObject);
        itemUtils.__init__(transferObjectUtils);
        ResponseResultSender responseResultSender = ResultSenderFactory.getResultSender(SenderType.LN_TRANSFER_RESPONSE_SENDER);
        responseResultSender.initializeResultSender(context, businessObject);
        HashMap<String, String> itemsValueMap = itemUtils.createItemValueMap(businessObject, context);
        itemVal = itemUtils.setItemValues(itemsValueMap, businessObject, abbreviationData, context);
        String mastership = "";
        String PDMRev = "";
        boolean isPDMitem = false;
        try {
            mastership = businessObject.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.att.mastership")).getValue();
            if (mastership.equalsIgnoreCase("PDM")) {
                /*VSIX-4464*/
                isPDMitem = true;
                PDMRev = businessObject.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.att.PDM.rev")).getValue();
            }
        } catch (Exception e) {
            DISASSEMBLE_ITEM_TRANSFER_LOGGER.error(e.getMessage());
        }
        String rev = "";
        if (itemVal != null) {
            itemUtils.getItemValList().add(itemVal);
            if (isPDMitem) {
                rev = PDMRev;
            } else {
                rev = businessObject.getRevision();
            }
            itemIdMap.put(disassembleItemName, businessObject);
        }
        ItemVAL.ItemID itemID = new ItemVAL.ItemID();
        itemID.setID(disassembleItemName);
        itemVal.setItemID(itemID);
        return itemVal;
    }

    @Override
    public Object processDeliverableTransfer(Context context, BusinessObject businessObject, DisassembleDataModel disassembleDataModel, TransferObjectUtils transferObjectUtils) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processBOMTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils, boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
