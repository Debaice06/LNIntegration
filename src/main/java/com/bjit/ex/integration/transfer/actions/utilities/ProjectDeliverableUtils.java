/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities;

import com.bjit.ex.integration.mapproject.xml_mapping_model.XmlMapElementAttribute;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.service.impl.LN.Constants;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.infor.businessinterface.projectdeliverable_val.CreateProjectDeliverableRequestType;
import com.infor.businessinterface.projectdeliverable_val.CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import matrix.db.BusinessObject;
import matrix.db.BusinessObjectWithSelect;
import matrix.db.Context;
import matrix.db.ExpansionWithSelect;
import matrix.db.RelationshipWithSelect;
import matrix.db.RelationshipWithSelectItr;
import matrix.util.MatrixException;
import matrix.util.Pattern;
import matrix.util.SelectList;

/**
 *
 * @author Sajjad
 * mandatory field to LN : name, rev, project, activity, quantity. 
 */
public class ProjectDeliverableUtils {

    private static final org.apache.log4j.Logger PROJECT_DELIVERABLE_UTILS_LOGGER = org.apache.log4j.Logger.getLogger(ProjectDeliverableUtils.class);
    private TransferObjectUtils transferObjectUtils;
    private List<XmlMapElementAttribute> projectDeliverableAttributeList;
    private List<XmlMapElementAttribute> projectDeliverablePropertyList;
    private HashMap<String, HashMap<String, List<XmlMapElementAttribute>>> relDeliverableAttributeAndPropertyMap;
    private List<XmlMapElementAttribute> relationShipAttributeList;
    private List<XmlMapElementAttribute> relationshipPropertyList;
    private HashMap<String, String> projectDeliverableItemValueMap;
    private HashMap<String, String> projectDeliverableRelValueMap;
    private final String XML_ATTRIBUTE_MAPPING_KEY = Constants.XML_ATTRIBUTE_MAPPING_KEY;
    private final String XML_PROPERTY_MAPPING_KEY = Constants.XML_PROPERTY_MAPPING_KEY;

    public void __init__(TransferObjectUtils transferObjectUtils) {
        this.transferObjectUtils = transferObjectUtils;
        this.relationShipAttributeList = new ArrayList<>();
        this.relationshipPropertyList = new ArrayList<>();
        this.projectDeliverableItemValueMap = new HashMap<>();
        this.projectDeliverableRelValueMap = new HashMap<>();
    }

    public HashMap<String, String> getProjectDeliverableBusinessObjectsAttributesAndProperties(Context context, BusinessObjectWithSelect busSelect, BusinessObject itemBO) throws MatrixException {
        //String type = busSelect.getSelectData(ApplicationProperties.getProprtyValue("source.object.type"));
        itemBO.open(context);
        String type = itemBO.getTypeName();
        projectDeliverablePropertyList = transferObjectUtils.getAttributeAndPropertyMap().get(type).get(XML_PROPERTY_MAPPING_KEY);
        projectDeliverableAttributeList = transferObjectUtils.getAttributeAndPropertyMap().get(type).get(XML_ATTRIBUTE_MAPPING_KEY);
        projectDeliverablePropertyList.forEach(xmlMapObjectAttribute -> {
            try {
                projectDeliverableItemValueMap.put(xmlMapObjectAttribute.getDestinationName(), itemBO.getAttributeValues(context, xmlMapObjectAttribute.getSourceName().trim()).getValue());
                //projectDeliverableItemValueMap.put(xmlMapObjectAttribute.getDestinationName(), busSelect.getSelectData(xmlMapObjectAttribute.getSelectable().trim()));
            } catch (MatrixException ex) {
                //Logger.getLogger(ProjectDeliverableUtils.class.getName()).log(Level.SEVERE, null, ex);
            }            
        });
        projectDeliverableAttributeList.forEach(xmlMapObjectAttribute -> {
            try {
                projectDeliverableItemValueMap.put(xmlMapObjectAttribute.getDestinationName(), itemBO.getAttributeValues(context, xmlMapObjectAttribute.getSourceName().trim()).getValue());
                //projectDeliverableItemValueMap.put(xmlMapObjectAttribute.getDestinationName(), busSelect.getSelectData(xmlMapObjectAttribute.getSelectable().trim()));
            } catch (MatrixException ex) {
                //Logger.getLogger(ProjectDeliverableUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        projectDeliverableItemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.name"), itemBO.getName());
        projectDeliverableItemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.revision"), itemBO.getRevision());
        itemBO.close(context);
        return projectDeliverableItemValueMap;
    }

    public HashMap<String, String> getProjectDeliverableRelationshipsAttributesAndProperties(RelationshipWithSelect relationShipWithSelect) {
        String relType = relationShipWithSelect.getSelectData("type");
        relationShipAttributeList = transferObjectUtils.getAttributeAndPropertyMap().get(relType).get(XML_ATTRIBUTE_MAPPING_KEY);
        relationShipAttributeList.forEach(xmlMapObjectAttribute -> {
            projectDeliverableRelValueMap.put(xmlMapObjectAttribute.getDestinationName(), relationShipWithSelect.getSelectData(xmlMapObjectAttribute.getSelectable().trim()));
        });

        return projectDeliverableRelValueMap;
    }

    public CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL createProjectDeliverableData(ProjectDeliverableVAL projectDeliverableVAL, BusinessObjectWithSelect busSelect, HashMap projectDeliverableItemValueMap, HashMap projectDeliverableRelValueMap) {
        projectDeliverableVAL = setProjectDeliverableStaticValue(projectDeliverableVAL, busSelect);
        projectDeliverableVAL = setProjectDeliverableItemAttribute(projectDeliverableVAL, projectDeliverableItemValueMap);
        projectDeliverableVAL = setProjectDeliverableItemProperty(projectDeliverableVAL, projectDeliverableItemValueMap);
        projectDeliverableVAL = setProjectDeliverableRelAttributeValue(projectDeliverableVAL, projectDeliverableRelValueMap);
        return projectDeliverableVAL;
    }

    private ProjectDeliverableVAL setProjectDeliverableStaticValue(ProjectDeliverableVAL projectDeliverableVAL, BusinessObjectWithSelect busSelect) {
        //projectDeliverableVAL.setMilestone("");
        return projectDeliverableVAL;
    }

    private ProjectDeliverableVAL setProjectDeliverableItemAttribute(ProjectDeliverableVAL projectDeliverableVAL, HashMap<String,String> projectDeliverableItemValueMap) {
        projectDeliverableAttributeList.forEach((XmlMapElementAttribute xmlMapAttribute) -> {
            if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Task destination 2")) {
                //projectDeliverableVAL.setActivity(projectDeliverableItemValueMap.get(xmlMapAttribute.getDestinationName()));
                //projectDeliverableVAL.setActivity("102001");//can't be empty
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Task destination 3")) {
                //projectDeliverableVAL.setContractDeliverable(projectDeliverableItemValueMap.get(xmlMapAttribute.getDestinationName()));
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Task destination 4")) {
                //projectDeliverableVAL.setContractProject(projectDeliverableItemValueMap.get(xmlMapAttribute.getDestinationName()));
            }
//            projectDeliverableVAL.setDeleteContractDeliverable("");/*true/false*/
//            projectDeliverableVAL.setMilestone("");
        });
        return projectDeliverableVAL;
    }

    private ProjectDeliverableVAL setProjectDeliverableItemProperty(ProjectDeliverableVAL projectDeliverableVAL, HashMap<String,String>  projectDeliverableItemValueMap) {
        ProjectDeliverableVAL.ItemID itemID = new ProjectDeliverableVAL.ItemID();
        itemID.setID((String) projectDeliverableItemValueMap.get(ApplicationProperties.getProprtyValue("destination.object.name")));
        projectDeliverableVAL.setItemID(itemID);
        projectDeliverableVAL.setRevision(projectDeliverableItemValueMap.get(ApplicationProperties.getProprtyValue("destination.object.revision")));
        projectDeliverableVAL.setActivity(projectDeliverableItemValueMap.get("Activity"));
        projectDeliverableVAL.setContractProject(projectDeliverableItemValueMap.get("ContractProject"));//Contract Project field cannot be empty in request XML.
        projectDeliverableVAL.getMilestone();
        projectDeliverableVAL.setUserID("");
        projectDeliverableVAL.setContractDeliverable("");
        projectDeliverableVAL.setQuantity(BigDecimal.ONE);
        return projectDeliverableVAL;
    }

    private ProjectDeliverableVAL setProjectDeliverableRelAttributeValue(ProjectDeliverableVAL projectDeliverableVAL, HashMap projectDeliverableRelValueMap) {
        //projectDeliverableVAL.setQuantity(BigDecimal.ONE);//XML tag 'quantity' cannot have zero value.
        projectDeliverableVAL.setQuantity(new BigDecimal("1.0"));
        return projectDeliverableVAL;
    }

    public String expandTaskObject(Context context, BusinessObject taskObj) {
        String projectSpaceName = "";
        try {
            SelectList selectBusStmts = new SelectList();
            SelectList selectRelStmts = new SelectList();
            Pattern typePattern = new Pattern("Project Space") ;
            Pattern relPattern = new Pattern("Subtask");
            String busWhereExpression = "";
            String relWhereExpression = "";
            ExpansionWithSelect expandResult;
            RelationshipWithSelectItr relItr = null;
            Short expandLevel = new Short("1");
            
            expandResult = taskObj.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    true, false, // Get from .... true, false -> get to
                    expandLevel, busWhereExpression, relWhereExpression, false);
            relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
            while (relItr.next()) {
                RelationshipWithSelect relSelect = relItr.obj();
                BusinessObject parentBo = relSelect.getFrom();
                parentBo.open(context);
                projectSpaceName = parentBo.getName();
                parentBo.close(context);
            }            
        } catch (MatrixException ex) {
            Logger.getLogger(ProjectDeliverableUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return "N7009AXF1";
        return projectSpaceName;
    }
}
