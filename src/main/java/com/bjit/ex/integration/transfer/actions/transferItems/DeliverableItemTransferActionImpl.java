/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.transferItems;

import com.bjit.ex.integration.transfer.actions.utilities.BomUtils;
import com.bjit.ex.integration.transfer.actions.utilities.BusinessObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.ContractDetailsInfo;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.DisassembleDataModel;
import com.bjit.ex.integration.transfer.actions.utilities.ItemUtils;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.TransferUtils;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.infor.businessinterface.projectdeliverable_val.CreateProjectDeliverableRequestType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import matrix.db.BusinessObject;
import matrix.db.BusinessObjectWithSelect;
import matrix.db.Context;
import matrix.db.RelationshipList;
import matrix.db.RelationshipWithSelect;
import matrix.db.RelationshipWithSelectItr;

/**
 *
 * @author Sajjad
 * expand bus "Project Space" ProjectDeliverableTest 941567689807799 from type "Task,CreateAssembly" recurse to 3 withroots
 */
public class DeliverableItemTransferActionImpl implements ITransferAction{
    private static final org.apache.log4j.Logger DELIVERABLE_TRANSFER_LOGGER = org.apache.log4j.Logger.getLogger(DeliverableItemTransferActionImpl.class);
    @Override
    public Object processItemTransfer(Context context, BusinessObject deliverableBusinessObject, TransferObjectUtils transferObjectUtils,boolean isService) throws Exception {
        CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL deliverableVAL = new CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL();
        String contractDeliverable = deliverableBusinessObject.getAttributeValues(context, ApplicationProperties.getProprtyValue("source.att.contract.deliberable")).getValue();        
        BusinessObjectUtils businessObjectUtils = new BusinessObjectUtils();
        String deliverableState = businessObjectUtils.getCurrentState(context, deliverableBusinessObject);  
        
        boolean updateDeliverable = false;
        boolean deleteDeliverable = false;
        
        if (deliverableState.equalsIgnoreCase("OBSOLETE")) {
            deleteDeliverable = true;
        } else {
            if (contractDeliverable != null && !contractDeliverable.isEmpty()) {
                System.out.println("Update Deliverable ::: ");
                updateDeliverable = true;
            } else {
                contractDeliverable = "";
            }
        }       
        

        RelationshipWithSelectItr relationIterator = transferObjectUtils.expandDeliverableBusinessObject(context, deliverableBusinessObject);
        while (relationIterator.next()) {
            RelationshipWithSelect relationShipWithSelect = relationIterator.obj();
            BusinessObjectWithSelect businessObjectWithSelect = relationShipWithSelect.getTarget();
            
            String childObjId = businessObjectWithSelect.getSelectData("id");
            String childObjType = businessObjectWithSelect.getSelectData("type");
            String childObjName = businessObjectWithSelect.getSelectData("name");
            String childObjRevision = businessObjectWithSelect.getSelectData("revision");
            String deli_quantity = businessObjectWithSelect.getSelectData("attribute["+ApplicationProperties.getProprtyValue("source.att.deliberable.quantity")+"]");
            System.out.println("deli_quantity :: "+deli_quantity);
            System.out.println("\nTASK ::: "+childObjType);
            
            if(childObjType.equalsIgnoreCase("Task")){ 
                String activity = childObjName;
                BusinessObject taskBO = new BusinessObject(childObjId);                
                RelationshipList relList = taskBO.getToRelationship(context);
                BusinessObject contractProjectBO = transferObjectUtils.getProjectNameFromTaskObj(context,taskBO);
                String contractProject = "";
                if (contractProjectBO != null) {
                    contractProjectBO.open(context);
                    contractProject = contractProjectBO.getName();
                    contractProjectBO.close(context);
                }
                String milestone = transferObjectUtils.getMilestoneFromTaskObj(context, taskBO);
                deliverableVAL.setActivity(activity);
                deliverableVAL.setContractProject(contractProject);
                deliverableVAL.setMilestone(milestone);                
            } else {
                //String quantity = relationShipWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("source.rel.delivery.quantity") + "]");
                //String quantity = "1.0";
                String itemName = childObjName;
                String itemRev = childObjRevision;
                
                CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID itemID = new CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID();
                itemID.setID(itemName);
                deliverableVAL.setItemID(itemID);
                deliverableVAL.setRevision(itemRev);
                deliverableVAL.setContractDeliverable(contractDeliverable);
                
                if(deleteDeliverable){
                    deliverableVAL.setDeleteContractDeliverable("TRUE");
                }
                System.out.println("quantity ::: "+deli_quantity);
                        
                deliverableVAL.setQuantity(new BigDecimal(deli_quantity.trim()));
                /*if(updateDeliverable){
                    deliverableVAL.setQuantity(new BigDecimal("5.0"));
                } else{
                    deliverableVAL.setQuantity(new BigDecimal("2.0"));
                } */               
            }
        }
        TransferUtils transferUtils = new TransferUtils();
        boolean isDeliverableSendingSuccessful = (boolean) transferUtils.transferToLN(context, deliverableVAL, deliverableBusinessObject, "DeliverableLN");
        if (isDeliverableSendingSuccessful) {
            return deliverableVAL;
        } 
        
        return false;
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
    public Object processTranslationUpdateTransfer(Context context, HashMap itemTranslationMap, TransferObjectUtils businessObjectUtils, ItemUtils itemUtils) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processItemBOMTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils, BomUtils bomUtils,boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     @Override
    public Object processDisassembleItemsTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processDisassembleStructureTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils,DisassembleDataModel disassembleDataModel) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object processDeliverableTransfer(Context context, BusinessObject deliverableBusinessObject, DisassembleDataModel disassembleDataModel, TransferObjectUtils transferObjectUtils) throws Exception {
        ArrayList<ResponseResult> resultList = new ArrayList<>();        
        CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL deliverableVAL = new CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL();
        String contractDeliverable = deliverableBusinessObject.getAttributeValues(context, ApplicationProperties.getProprtyValue("source.att.contract.deliberable")).getValue();        
        String deliverableQuantity = deliverableBusinessObject.getAttributeValues(context, ApplicationProperties.getProprtyValue("source.att.deliberable.quantity")).getValue(); 
        if (deliverableQuantity == null || deliverableQuantity.equalsIgnoreCase("0")){
            deliverableBusinessObject.open(context);
            ResponseResult responseResult = new ResponseResult(deliverableBusinessObject.getName(), "Delivery quantity can not be Null or 0.", false);
            responseResult.setRevision(deliverableBusinessObject.getRevision());
            deliverableBusinessObject.close(context);
            resultList.add(responseResult);
        }
        BusinessObjectUtils businessObjectUtils = new BusinessObjectUtils();
        String deliverableState = businessObjectUtils.getCurrentState(context, deliverableBusinessObject);       
        
        boolean updateDeliverable = false;
        boolean deleteDeliverable = false;
        
        if (deliverableState.equalsIgnoreCase("OBSOLETE")) {
            DELIVERABLE_TRANSFER_LOGGER.info("Deliverable will be deleted (State changed to OBSOLOTE) ");
            deleteDeliverable = true;
        } else {
            if (contractDeliverable != null && !contractDeliverable.isEmpty()) {
                DELIVERABLE_TRANSFER_LOGGER.info("Contract Deliverable :::  "+contractDeliverable);
                DELIVERABLE_TRANSFER_LOGGER.info("Updating Deliverable !! ");
                updateDeliverable = true;
            } else {
                DELIVERABLE_TRANSFER_LOGGER.info("Contract Deliverable value is empty !! System will send Deliverable for the first time.");
                contractDeliverable = "";
            }
        }  
        ContractDetailsInfo contractDetailsInfo = new ContractDetailsInfo();
        BusinessObject rootItemBO = disassembleDataModel.getRootItemBO();
        rootItemBO.open(context);
        CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID itemID = new CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID();
        itemID.setID(rootItemBO.getName());
        deliverableVAL.setItemID(itemID);
        String mastership = "";
        String PDMRev = "";
        boolean isPDMitem = false;
        try {
            mastership = rootItemBO.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.att.mastership")).getValue();
            if (mastership.equalsIgnoreCase("PDM")) {
                /*VSIX-4464*/
                isPDMitem = true;
                PDMRev = rootItemBO.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.att.PDM.rev")).getValue();
            }
        } catch (Exception e) {
            DELIVERABLE_TRANSFER_LOGGER.error(e.getMessage());
        }
        String rev = "";
        if (isPDMitem) {
            rev = PDMRev;
        } else {
            rev = rootItemBO.getRevision();
        }
        deliverableVAL.setRevision(rev);
        contractDetailsInfo.setDeliverableItem(rootItemBO.getName());
        rootItemBO.close(context);
        
        deliverableVAL.setContractDeliverable(contractDeliverable);
        deliverableVAL.setQuantity(new BigDecimal(deliverableQuantity.trim())); 
        contractDetailsInfo.setOrderQty(deliverableQuantity);
        if(deleteDeliverable){
            deliverableVAL.setDeleteContractDeliverable("TRUE");
        }
        
        
        RelationshipWithSelectItr relationIterator = transferObjectUtils.expandDeliverableBusinessObject(context, deliverableBusinessObject);
        while (relationIterator.next()) {
            RelationshipWithSelect relationShipWithSelect = relationIterator.obj();
            BusinessObjectWithSelect businessObjectWithSelect = relationShipWithSelect.getTarget();
            
            String childObjId = businessObjectWithSelect.getSelectData("id");
            String childObjType = businessObjectWithSelect.getSelectData("type");
            String childObjName = businessObjectWithSelect.getSelectData("name");
            String childObjRevision = businessObjectWithSelect.getSelectData("revision");
//            String deli_quantity = businessObjectWithSelect.getSelectData("attribute["+ApplicationProperties.getProprtyValue("source.att.deliberable.quantity")+"]");
//            System.out.println("deli_quantity :: "+deli_quantity);
            
            if(childObjType.equalsIgnoreCase("Task")){ 
                DELIVERABLE_TRANSFER_LOGGER.info("Task Name connected with deliverable/general-system ::: " + childObjName);
                String activity = childObjName;
                BusinessObject taskBO = new BusinessObject(childObjId);                
                RelationshipList relList = taskBO.getToRelationship(context);
                BusinessObject contractProjectBO = transferObjectUtils.getProjectNameFromTaskObj(context,taskBO);
                String contractProject = "";
                if (contractProjectBO != null) {
                    contractProjectBO.open(context);
                    contractProject = contractProjectBO.getName();
                    contractProjectBO.close(context);
                }
                DELIVERABLE_TRANSFER_LOGGER.info("Project Name connected with Task ::: "+contractProject);
                String milestone = transferObjectUtils.getMilestoneFromTaskObj(context, taskBO);
                deliverableVAL.setActivity(activity);
                deliverableVAL.setContractProject(contractProject);
                deliverableVAL.setMilestone(milestone); 
                
                contractDetailsInfo.setActivity(activity);
                contractDetailsInfo.setMilestrone(milestone);
                contractDetailsInfo.setProject(contractProject);               
                
            } /*else {
                //String quantity = relationShipWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("source.rel.delivery.quantity") + "]");
                //String quantity = "1.0";
                String itemName = childObjName;
                String itemRev = childObjRevision;
                
                CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID itemID = new CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID();
                itemID.setID(itemName);
                deliverableVAL.setItemID(itemID);
                deliverableVAL.setRevision(itemRev);
                deliverableVAL.setContractDeliverable(contractDeliverable);
                
                if(deleteDeliverable){
                    deliverableVAL.setDeleteContractDeliverable("TRUE");
                }
                System.out.println("quantity ::: "+deli_quantity);
                        
                deliverableVAL.setQuantity(new BigDecimal(deli_quantity.trim()));
                if(updateDeliverable){
                    deliverableVAL.setQuantity(new BigDecimal("5.0"));
                } else{
                    deliverableVAL.setQuantity(new BigDecimal("2.0"));
                }                
            }*/
        }
        
        String activity = deliverableVAL.getActivity();
        String project = deliverableVAL.getContractProject();
        if (activity == null || activity.isEmpty() || project == null || project.isEmpty()) {
            deliverableBusinessObject.open(context);
            ResponseResult responseResult = new ResponseResult(deliverableBusinessObject.getName(), "Task/Project can not be empty.", false);
            responseResult.setRevision(deliverableBusinessObject.getRevision());
            deliverableBusinessObject.close(context);
            resultList.add(responseResult);
        }
        
        if (!resultList.isEmpty()) {
            return resultList;
        }
        
        boolean isDeliverableTransferSuccessful = true;
        TransferUtils transferUtils = new TransferUtils();
        resultList = (ArrayList<ResponseResult>) transferUtils.transferToLN(context, deliverableVAL, deliverableBusinessObject, "DeliverableLN");
        if (resultList != null && !resultList.isEmpty()){
            for (ResponseResult result : resultList) {
                if (!result.isSuccessful()) {
                    isDeliverableTransferSuccessful = false;
                }
            }            
        }
        
        DELIVERABLE_TRANSFER_LOGGER.info("Deliverable Transfered Successfully ::: "+isDeliverableTransferSuccessful);
        
        if (isDeliverableTransferSuccessful) {        
            contractDeliverable = deliverableBusinessObject.getAttributeValues(context, ApplicationProperties.getProprtyValue("source.att.contract.deliberable")).getValue(); 
            String [] contractDeliverableArr = contractDeliverable.split("_");
            if(contractDeliverableArr.length==3){
                contractDetailsInfo.setContract(contractDeliverableArr[0]);
                contractDetailsInfo.setContractLine(contractDeliverableArr[1]);
                contractDetailsInfo.setDeliverableNumber(contractDeliverableArr[2]);
            }
            disassembleDataModel.setContractDetailsInfo(contractDetailsInfo);
            return disassembleDataModel;
        } 
        
        return resultList;
    
    }

    @Override
    public Object processBOMTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils, boolean isService) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
