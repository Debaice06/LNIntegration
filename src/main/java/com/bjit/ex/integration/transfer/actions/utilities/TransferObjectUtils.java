/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities;

import com.bjit.compareBOM.MultiLevelBomDataModel.MultilevelBomDetailsBean;
import com.bjit.compareBOM.service.LN.MultilevelBOMDetails;
import com.bjit.context.ContextGeneration;
import com.bjit.ex.integration.mapproject.builder.MapperBuilder;
import com.bjit.ex.integration.mapproject.xml_mapping_model.Mapping;
import com.bjit.ex.integration.mapproject.xml_mapping_model.XmlMapElementAttribute;
import com.bjit.ex.integration.model.webservice.Item;
import com.bjit.ex.integration.transfer.service.impl.LN.Constants;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.FileDirProcess;
import com.bjit.project_structure.utilities.NullOrEmptyChecker;
import com.infor.businessinterface.multilevelbomdetails_val.GetBOMDetailsRequestType;
import com.infor.businessinterface.multilevelbomdetails_val.GetBOMDetailsResponseType;
import com.infor.businessinterface.multilevelbomdetails_val.Message;
import com.infor.businessinterface.multilevelbomdetails_val.Result;
import com.matrixone.apps.domain.util.FrameworkException;
import com.matrixone.apps.domain.util.MqlUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import matrix.db.BusinessObject;
import matrix.db.BusinessObjectWithSelect;
import matrix.db.Context;
import matrix.db.ExpansionWithSelect;
import matrix.db.MQLCommand;
import matrix.db.RelationshipWithSelect;
import matrix.db.RelationshipWithSelectItr;
import matrix.db.Signature;
import matrix.db.SignatureList;
import matrix.util.MatrixException;
import matrix.util.Pattern;
import matrix.util.SelectList;

/**
 * @author BJIT
 */
public class TransferObjectUtils {

    private static final org.apache.log4j.Logger TRANSFER_OBJECT_UTILS_LOGGER = org.apache.log4j.Logger.getLogger(TransferObjectUtils.class);
    //private Context context;
    private SelectList selectBusStmts;
    private List<XmlMapElementAttribute> itemBusinessObjectPropertyList;
    private List<XmlMapElementAttribute> itemBusinessObjectAttributeList;
    private static HashMap<String, String> STATE_MAP = new HashMap<>();
    private Mapping mapper;
    private HashMap<String, String> itemValueMap;
    private HashMap<String, HashMap<String, List<XmlMapElementAttribute>>> attributeAndPropertyMap;
    private final String XML_ATTRIBUTE_MAPPING_KEY = Constants.XML_ATTRIBUTE_MAPPING_KEY;
    private final String XML_PROPERTY_MAPPING_KEY = Constants.XML_PROPERTY_MAPPING_KEY;
    public static final List<String> VALItemType = Arrays.asList("VAL_VALComponent", "VAL_VALComponentMaterial");

    public void __init__(Context context, BusinessObject bo) throws Exception {
        clearPreviousData();
        itemBusinessObjectPropertyList = new ArrayList<>();
        itemBusinessObjectAttributeList = new ArrayList<>();
        itemValueMap = new HashMap<>();
        attributeAndPropertyMap = new HashMap<>();
        //this.context = context;
        try {
            mapper = new MapperBuilder().getMapper(MapperBuilder.XML);
        } catch (Exception exp) {
            throw exp;
        }
        this.createAttributeAndPropertyList(bo);
        this.createMappersAttributeAndPropertyMap();
        this.selectBusStmts = buildBusStmnts();
    }

    public void __init__(Context context) throws Exception {
        clearPreviousData();
        itemBusinessObjectPropertyList = new ArrayList<>();
        itemBusinessObjectAttributeList = new ArrayList<>();
        itemValueMap = new HashMap<>();
        attributeAndPropertyMap = new HashMap<>();
        //this.context = context;
        try {
            mapper = new MapperBuilder().getMapper(MapperBuilder.XML);
        } catch (Exception exp) {
            throw exp;
        }
        this.createAttributeAndPropertyList();
        this.createMappersAttributeAndPropertyMap();
        this.selectBusStmts = buildBusStmnts();
    }

    private void clearPreviousData() {
        attributeAndPropertyMap = null;
        mapper = null;
    }

    /**
     * Creates the bus statement by all the properties and attributes declared
     * in the XML map. These lists generated at the beginning of the program
     * execution.
     *
     * @return
     */
    public SelectList buildBusStmnts() {
        selectBusStmts = new SelectList();

        itemBusinessObjectPropertyList.forEach(xmlMapItemObjectProperty -> {
            selectBusStmts.add(xmlMapItemObjectProperty.getSourceName());
        });

        itemBusinessObjectAttributeList.forEach(xmlMapItemObjectAttribute -> {
            selectBusStmts.addAttribute(xmlMapItemObjectAttribute.getSourceName());
        });

        selectBusStmts.add(ApplicationProperties.getProprtyValue("source.object.id"));
        selectBusStmts.add(ApplicationProperties.getProprtyValue("source.object.type"));
        selectBusStmts.add(ApplicationProperties.getProprtyValue("source.object.name"));
        selectBusStmts.add(ApplicationProperties.getProprtyValue("source.object.revision"));
        selectBusStmts.add(ApplicationProperties.getProprtyValue("source.object.current"));
        selectBusStmts.add(ApplicationProperties.getProprtyValue("source.object.project"));
        selectBusStmts.add(ApplicationProperties.getProprtyValue("source.object.owner"));
        selectBusStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.object.ExternalID") + "]");
        selectBusStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.item.code") + "]");
        selectBusStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.att.title.termId") + "]");
        selectBusStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.att.send.to.erp") + "]");
        selectBusStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.VAL.att.inventoryUnit") + "]");
        selectBusStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.att.ReleasePurpose") + "]");
        selectBusStmts.add("attribute[" + ApplicationProperties.getProprtyValue("item.att.mbom.type") + "]");
        selectBusStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.att.selection.code") + "]");
        /*
        Have to add new attribute on Bus Statement 
         */
        return selectBusStmts;
    }
    // ************ Ending of Codes For Item ************

    /**
     * Creates a select list for relation statements for expanding object
     *
     * @return SelectList
     */
    public SelectList buildRelStmnsts() {
        SelectList selectRelStmts = new SelectList();
        selectRelStmts.add(ApplicationProperties.getProprtyValue("source.rel.type"));
        selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.rel.position") + "]");
        selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.rel.subContractor") + "]");
        //selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.rel.quantity") + "]");
        selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.rel.continious.quantity") + "]");
        selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.rel.length") + "]");
        selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.rel.width") + "]");
        selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("item.transfer.to.erp") + "]");
        selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("rel.rfn.byproduct") + "]");
        selectRelStmts.addAttribute(ApplicationProperties.getProprtyValue("rel.common.text"));
        selectRelStmts.addAttribute(ApplicationProperties.getProprtyValue("rel.purchasing.text"));
        selectRelStmts.addAttribute(ApplicationProperties.getProprtyValue("rel.manufacturing.text"));
        //selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.rel.delivery.quantity") + "]");
        //selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("continious.item.transfer.to.erp") + "]");
        //selectRelStmts.add("attribute[" + ApplicationProperties.getProprtyValue("source.rel.continious.position") + "]");
        /*relationShipAttributeList.forEach(attribute -> {
            System.out.println("\n\n\n\n attribute.getSelectable() "+attribute.getSelectable());
            selectRelStmts.addAttribute(attribute.getSelectable());
        });*/
        return selectRelStmts;
    }

    /**
     * Returns type pattern for expanding object
     *
     * @return Pattern
     */
    public Pattern buildTypePattern() {
        StringBuilder typePatternBuilder = new StringBuilder();
        for (int i = 0; i < mapper.getXmlMapElementObjects().getXmlMapElementObject().size(); i++) {
            if (i > 0) {
                typePatternBuilder.append(",");
            }
            String typeName = mapper.getXmlMapElementObjects().getXmlMapElementObject().get(i).getType();
            if (typeName.contains(" ")) {
                typePatternBuilder.append("\"");
                typePatternBuilder.append(typeName);
                typePatternBuilder.append("\"");
            } else {
                typePatternBuilder.append(typeName);
            }

        }
        Pattern typePattern;
        if (typePatternBuilder.length() > 0) {
            typePattern = new Pattern(typePatternBuilder.toString());
        } else {
            typePattern = new Pattern("*");
        }
        return typePattern;
    }

    /**
     * Builds relationship patterns for expanding object
     *
     * @return Pattern
     */
    public Pattern buildRelPattern() {
        StringBuilder relPatternBuilder = new StringBuilder();
        for (int i = 0; i < mapper.getXmlMapElementBOMRelationships().getXmlMapElementBOMRelationship().size(); i++) {
            if (i > 0) {
                relPatternBuilder.append(",");
            }
            String relName = mapper.getXmlMapElementBOMRelationships().getXmlMapElementBOMRelationship().get(i).getName();
//            if (relName.contains(" ")) {
//                relPatternBuilder.append("\"");
//                relPatternBuilder.append(relName);
//                relPatternBuilder.append("\"");
//            } else {
            relPatternBuilder.append(relName);
//            }
        }

        Pattern relPattern;

        if (relPatternBuilder.length() > 0) {
            relPattern = new Pattern(relPatternBuilder.toString());
        } else {
            relPattern = new Pattern("*");
        }
        return relPattern;
    }

    /**
     * Builds where expression for expanding object
     *
     * @return String
     */
    public String buildBusWhereExp() {
        //String busWhereExpression = "type!=Provide && type!=ProcessContinuousProvide";
        //return busWhereExpression;
        return "";
    }

    /**
     * Builds relationship where expression
     *
     * @return String
     */
    public String buildRelWhereExp() {
        String relWhereExpression = "";
        return relWhereExpression;
    }

    /**
     * Expand a business object to nth level. In here level is set as 99.
     *
     * @param context
     * @param businessObject
     * @return RelationshipWithSelectItr
     */
    public RelationshipWithSelectItr expandBusinessObject(Context context, BusinessObject businessObject) {
        SelectList selectRelStmts = buildRelStmnsts();
        Pattern typePattern = buildTypePattern();
        Pattern relPattern = buildRelPattern();
        String busWhereExpression = buildBusWhereExp();
        String relWhereExpression = buildRelWhereExp();

        /*System.out.println("\n\n\n");
        System.out.println("------------------- ||| ------------------- Expand Statements ------------------- ||| -------------------");
        System.out.println("Bus Statement : " + selectBusStmts);
        System.out.println("Rel Statement : " + selectRelStmts);
        System.out.println("Type Pattern  : " + typePattern.getPattern());
        System.out.println("Rel Pattern   : " + relPattern.getPattern());
        System.out.println("Bus Where Expr: " + busWhereExpression);
        System.out.println("Rel Where Expr: " + relWhereExpression);
        System.out.println("------------------- ||| ------------------- Expand Statements ------------------- ||| -------------------");
        System.out.println("\n\n\n");*/
        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short(ApplicationProperties.getProprtyValue("expand.level"));

        try {
            long expandStartTime = System.currentTimeMillis();
            expandResult = businessObject.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    false, true, // Get from .... true, false -> get to
                    expandLevel, busWhereExpression, relWhereExpression, false);
            relItr = new RelationshipWithSelectItr(
                    expandResult.getRelationships());
            long expandEndTime = System.currentTimeMillis();
            long expandElpsedTime = expandEndTime - expandStartTime;
            TRANSFER_OBJECT_UTILS_LOGGER.debug("Expand Elapsed Time ::: " + expandElpsedTime);
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }

        return relItr;
    }

    /**
     * length and width value come from "Physical Product" (VPMReference)
     * connected with "Implement Link".
     *
     * @param context
     * @param businessObject
     * @return
     */
    public HashMap expandBusinessObjectForLengthWidthValue(Context context, BusinessObject businessObject) {
        TRANSFER_OBJECT_UTILS_LOGGER.info("Checking length and width value!!");
        HashMap<String, String[]> itemWithLengthWidthMap = new HashMap<>();
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("expand bus ")
                .append(businessObject.getObjectId())
                .append(" from rel ")
                .append(ApplicationProperties.getProprtyValue("rel.types.for.length.width"))
                .append(" withroots select rel attribute[").append(ApplicationProperties.getProprtyValue("source.rel.position")).append("] paths.path")
                .append(" dump |; ");

        String expandQuery = commandBuilder.toString();

        String expandResult = "";

        try {
            expandResult = MqlUtil.mqlCommand(context, expandQuery);
            String[] expandResultArray = expandResult.split("\n");
            for (String result : expandResultArray) {
                if (result.contains("SemanticRelation^connection")) {
                    String[] resultArray = result.split("\\|");
                    if (resultArray.length > 7) {
                        TRANSFER_OBJECT_UTILS_LOGGER.info("Child Item Name : " + businessObject.getName());
                        String childItemName = resultArray[4];
                        String childItemRev = resultArray[5];
                        String childPosition = resultArray[6];
                        String semanticRelConnection = resultArray[7];
                        String[] semanticRelConnectionArray = semanticRelConnection.split(",");
                        String implementLink = semanticRelConnectionArray[2];

                        String physicalProductID = getPhysicalProductID(context, implementLink);
                        String[] length_width = getLengthWidthFromPhysicalProduct(context, physicalProductID);

                        itemWithLengthWidthMap.put(childItemName + "#" + childItemRev + "#" + childPosition, length_width);
                    }
                }
            }
        } catch (FrameworkException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }

        return itemWithLengthWidthMap;
    }

    /**
     * @param context
     * @param implementLink
     * @return
     */
    private String getPhysicalProductID(Context context, String implementLink) {
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("print connection ")
                .append(implementLink)
                .append(" select to.id dump;");

        String query = commandBuilder.toString();
        System.out.println("query : " + query);
        String result = "";
        try {
            result = MqlUtil.mqlCommand(context, query);
        } catch (FrameworkException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }
        return result;
    }

    /**
     * @param context
     * @param physicalProductID
     * @return
     */
    private String[] getLengthWidthFromPhysicalProduct(Context context, String physicalProductID) {
        String[] length_width = {"", ""};
        try {
            BusinessObject bo = new BusinessObject(physicalProductID);
            try {
                length_width[0] = bo.getAttributeValues(context, ApplicationProperties.getProprtyValue("physical.product.att.length")).getValue();
                TRANSFER_OBJECT_UTILS_LOGGER.info("Length value " + length_width[0]);
            } catch (MatrixException ex) {
                TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
            }
            try {
                length_width[1] = bo.getAttributeValues(context, ApplicationProperties.getProprtyValue("physical.product.att.width")).getValue();
                TRANSFER_OBJECT_UTILS_LOGGER.info("Width value " + length_width[1]);
            } catch (MatrixException ex) {
                TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
            }
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }
        return length_width;
    }

    /**
     * Expand a business object to nth level. In here level is set as 99.
     *
     * @param context
     * @param businessObject
     * @return RelationshipWithSelectItr
     */
    public RelationshipWithSelectItr expandDeliverableBusinessObject(Context context, BusinessObject businessObject) {
        SelectList selectRelStmts = buildRelStmnsts();
        Pattern typePattern = buildTypePattern();
        Pattern relPattern = buildRelPattern();
        String busWhereExpression = buildBusWhereExp();
        String relWhereExpression = buildRelWhereExp();

        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short(ApplicationProperties.getProprtyValue("expand.level"));

        try {
            long expandStartTime = System.currentTimeMillis();
            expandResult = businessObject.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    true, false, // Get from .... true, false -> get to
                    expandLevel, busWhereExpression, relWhereExpression, false);
            relItr = new RelationshipWithSelectItr(
                    expandResult.getRelationships());
            long expandEndTime = System.currentTimeMillis();
            long expandElpsedTime = expandEndTime - expandStartTime;
            TRANSFER_OBJECT_UTILS_LOGGER.debug("Expand Elapsed Time ::: " + expandElpsedTime);
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }

        return relItr;
    }

    /**
     *
     * @param context
     * @param taskBO
     * @return
     */
    public BusinessObject getProjectNameFromTaskObj(Context context, BusinessObject taskBO) {
        SelectList selectBusStmts = new SelectList();
        SelectList selectRelStmts = new SelectList();
        Pattern typePattern = new Pattern("Project Space,Task");
        Pattern relPattern = new Pattern("Subtask");
        String busWhereExpression = "";
        String relWhereExpression = "";
        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short("5");
        String projectSpaceName = "";
        BusinessObject projectBO = null;
        try {
            expandResult = taskBO.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    true, false,
                    expandLevel, busWhereExpression, relWhereExpression, false);
            relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
            while (relItr.next()) {
                RelationshipWithSelect relSelect = relItr.obj();
                projectBO = relSelect.getFrom();
                projectBO.open(context);
                if (projectBO.getTypeName().equalsIgnoreCase("Project Space")) {
                    projectSpaceName = projectBO.getName();
                    TRANSFER_OBJECT_UTILS_LOGGER.info("Project Name : " + projectSpaceName);
                    projectBO.close(context);
                    return projectBO;
                }
                projectBO.close(context);
            }
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error("Error occurred while fetching Project : " + ex.getMessage());
        } catch (Exception ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error("Error occurred while fetching Project : " + ex.getMessage());
        }
        return projectBO;
    }

    public String getMilestoneFromTaskObj(Context context, BusinessObject taskBO) throws MatrixException {
        SelectList selectBusStmts = new SelectList();
        SelectList selectRelStmts = new SelectList();
        Pattern typePattern = new Pattern("Milestone");
        Pattern relPattern = new Pattern("Dependency");
        String busWhereExpression = "";
        String relWhereExpression = "";
        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short("1");
        String milestone = "";

        expandResult = taskBO.expandSelect(
                context, relPattern.getPattern(),
                typePattern.getPattern(), selectBusStmts, selectRelStmts,
                false, true,
                expandLevel, busWhereExpression, relWhereExpression, false);
        relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
        while (relItr.next()) {
            RelationshipWithSelect relSelect = relItr.obj();
            BusinessObject bo = relSelect.getTo();
            bo.open(context);
            if (bo.getTypeName().equalsIgnoreCase("Milestone")) {
                milestone = bo.getName();
                bo.close(context);
                break;
            }
            bo.close(context);
        }
        return milestone;
    }

    /**
     * Retrieves attributes and properties of a business object which were
     * generated from bus select statement at the beginning of this program
     * execution.
     *
     * Here itemValueMap initializes for each business object from the values in
     * bus select statement
     *
     * In itemValueMap keys are the destination of the XML mapper In
     * itemValueMap values are the source of the XML mapper
     *
     * From the bus select statement data retrieves by the source of the
     * XMLMapper's, objects attributes and properties.
     *
     * @param busSelect
     * @return
     */
    public HashMap<String, String> getBusinessObjectsAttributesAndProperties(Context context, BusinessObjectWithSelect busSelect, BusinessObject businessObject) {
        TRANSFER_OBJECT_UTILS_LOGGER.debug("Creating Business Objects's Select Statements Map : ");
        try {
            itemBusinessObjectAttributeList.forEach(xmlMapObjectAttribute -> {
                itemValueMap.put(xmlMapObjectAttribute.getDestinationName(), busSelect.getSelectData(xmlMapObjectAttribute.getSelectable().trim()));
                //itemValueMap.put(xmlMapObjectAttribute.getDestinationName(), busSelect.getSelectData("attribute[DELFmiFunctionPPRContinuousReference.V_EstimatedWeight]"));
            });

            itemBusinessObjectPropertyList.forEach(xmpMapObjectProperty -> {
                itemValueMap.put(xmpMapObjectProperty.getDestinationName(), busSelect.getSelectData(xmpMapObjectProperty.getSelectable()));
            });

            itemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.type"), busSelect.getSelectData(ApplicationProperties.getProprtyValue("source.object.type")));
            //itemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.name"), busSelect.getSelectData(ApplicationProperties.getProprtyValue("source.object.name")));
            itemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.name"), busSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("source.object.ExternalID") + "]"));
            String mastership = "";
            String PDMRev = "";
            boolean isPDMitem = false;
            try {
                //mastership = busSelect.getSelectData("attribute[MBOM_MBOMPDM.MBOM_Mastership]");// Does not work like this in mig2.[March 22, 2021]
                mastership = busSelect.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.att.mastership")).getValue();
                if (mastership.equalsIgnoreCase("PDM")) {
                    isPDMitem = true;
                    PDMRev = busSelect.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.att.PDM.rev")).getValue();
                    TRANSFER_OBJECT_UTILS_LOGGER.info("Item's Mastership is PDM. PDM rev will send.");
                }
            } catch (Exception e) {
                TRANSFER_OBJECT_UTILS_LOGGER.error(e.getMessage());
            }
            if (isPDMitem) {
                /*
                VSIX-4464 It is possible to create Disassemble Item with PDM item. And in case of that PDM rev will send.
                PDM items are not sending from V6 but Disassemble item can from with PDM item. 
                 */
                itemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.revision"), PDMRev);
            } else {
                itemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.revision"), busSelect.getSelectData(ApplicationProperties.getProprtyValue("source.object.revision")));
            }
            itemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.current"), busSelect.getSelectData(ApplicationProperties.getProprtyValue("source.object.current")));
            //itemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.selection"), busSelect.getSelectData(ApplicationProperties.getProprtyValue("source.object.current")));
            itemValueMap.put(ApplicationProperties.getProprtyValue("destination.object.owner"), busSelect.getSelectData(ApplicationProperties.getProprtyValue("source.object.owner")));

            RelationshipWithSelectItr relationIterator = expandBusinessObject(context, businessObject);
            //if (relationIterator == null) {
            boolean hasChild = false;
            while (relationIterator.next()) {
                hasChild = true;

            }
            if (!hasChild) {
                itemValueMap.put("selectionCode", "ITM");
            }
            if (!getObjStateMap().containsKey(itemValueMap.get("signal"))) {
                itemValueMap.put("signal", "NEW");
            } else {
                String currentState = busSelect.getSelectData(ApplicationProperties.getProprtyValue("source.object.current"));
                if (currentState.equalsIgnoreCase("RELEASED")) {
                    String releasePurpose = busSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("source.att.ReleasePurpose") + "]");
                    if (releasePurpose.equalsIgnoreCase("Planning")) {
                        boolean itemExistInLN = checkIfItemExistInLN(businessObject.getName());
                        if (!itemExistInLN) {
                            itemValueMap.put("signal", "DRA");
                        } else {
                            itemValueMap.remove("signal");
                            TRANSFER_OBJECT_UTILS_LOGGER.info("Not Updating Signal Code as Maturity State 'RELEASED' and ReleasePurpose 'Planning' and Item already exist in LN.");
                        }
                    } else if (releasePurpose.equalsIgnoreCase("Production")) {
                        itemValueMap.put("signal", "NEW");
                    }
                } else if ("OBSOLETE".equalsIgnoreCase(currentState)) {
                    itemValueMap.put("signal", "FRB");
                } else {
                    itemValueMap.put("signal", getObjStateMap().get(itemValueMap.get("signal")));
                }
            }
            return itemValueMap;
        } catch (Exception exp) {
            throw new NullPointerException("itemBusinessObjectAttributeList or itemBusinessObjectPropertyList or itemValueMap may be null. Error : " + exp.getMessage());
        }
    }

    /**
     * Creates a businessObjectAttributeList and businessObjectPropertyList for
     * making the select list which is buildBusStmt (Build Bus Statements)
     */
    public void createAttributeAndPropertyList(BusinessObject bo) {
        itemBusinessObjectAttributeList.clear();
        itemBusinessObjectPropertyList.clear();
        itemValueMap.clear();

        try {
            mapper.getXmlMapElementObjects().getXmlMapElementObject().forEach(xmlMapItemObject -> {
                if (xmlMapItemObject.getType().equalsIgnoreCase(bo.getTypeName().trim())) {
                    xmlMapItemObject.getXmlMapElementAttributes().getXmlMapElementAttribute().forEach(xmlMapAttribute -> {
                        if (xmlMapAttribute.getIsProperty().equalsIgnoreCase("false")) {
                            itemBusinessObjectAttributeList.add(xmlMapAttribute);
                        } else {
                            itemBusinessObjectPropertyList.add(xmlMapAttribute);
                        }
                    });
                }
            });
        } catch (Exception exp) {
            throw new NullPointerException("Mapper or itemBusinessObjectAttributeList or itemBusinessObjectPropertyList may be null. Error " + exp.getMessage());
        }
    }

    public void createAttributeAndPropertyList() {
        itemBusinessObjectAttributeList.clear();
        itemBusinessObjectPropertyList.clear();
        itemValueMap.clear();
        try {
            mapper.getXmlMapElementObjects().getXmlMapElementObject().forEach(xmlMapItemObject -> {
                xmlMapItemObject.getXmlMapElementAttributes().getXmlMapElementAttribute().forEach(xmlMapAttribute -> {
                    if (xmlMapAttribute.getIsProperty().equalsIgnoreCase("false")) {
                        itemBusinessObjectAttributeList.add(xmlMapAttribute);
                    } else {
                        itemBusinessObjectPropertyList.add(xmlMapAttribute);
                    }
                });
            });
        } catch (Exception exp) {
            throw new NullPointerException("Mapper or itemBusinessObjectAttributeList or itemBusinessObjectPropertyList may be null. Error " + exp.getMessage());
        }
    }

    /**
     * Creates a map where key is the string type of business object and name of
     * the relationship and the value is another map with keys like
     * "attributeMap" and "propertyMap" and values are the values of those
     * attributes and properties. These mapping found from the XML map which
     * resides at the resources folder.
     */
    public void createMappersAttributeAndPropertyMap() {
        try {
            mapper.getXmlMapElementObjects().getXmlMapElementObject().forEach(xmlMapElementObject -> {
                attributeAndPropertyMap.put(xmlMapElementObject.getType(), new HashMap<>());
                attributeAndPropertyMap.get(xmlMapElementObject.getType()).put(XML_ATTRIBUTE_MAPPING_KEY, new ArrayList<>());
                attributeAndPropertyMap.get(xmlMapElementObject.getType()).put(XML_PROPERTY_MAPPING_KEY, new ArrayList<>());
                xmlMapElementObject.getXmlMapElementAttributes().getXmlMapElementAttribute().forEach(xmlMapAttribute -> {
                    if (Boolean.valueOf(xmlMapAttribute.getIsProperty())) {
                        attributeAndPropertyMap.get(xmlMapElementObject.getType()).get(XML_PROPERTY_MAPPING_KEY).add(xmlMapAttribute);
                    } else {
                        attributeAndPropertyMap.get(xmlMapElementObject.getType()).get(XML_ATTRIBUTE_MAPPING_KEY).add(xmlMapAttribute);
                    }
                });
            });

            mapper.getXmlMapElementBOMRelationships().getXmlMapElementBOMRelationship().forEach(xmlMapBomRelationship -> {
                attributeAndPropertyMap.put(xmlMapBomRelationship.getName(), new HashMap<>());
                attributeAndPropertyMap.get(xmlMapBomRelationship.getName()).put(XML_ATTRIBUTE_MAPPING_KEY, new ArrayList<>());

                xmlMapBomRelationship.getXmlMapElementAttributes().getXmlMapElementAttribute().forEach(xmlMapAttribute -> {
                    attributeAndPropertyMap.get(xmlMapBomRelationship.getName()).get(XML_ATTRIBUTE_MAPPING_KEY).add(xmlMapAttribute);
                });
            });
        } catch (Exception exp) {
            throw new NullPointerException("Mapper or attributeAndPropertyMap may be null. Error " + exp.getMessage());
        }
    }

    /**
     * Returns objects current state
     *
     * @return HashMap<String, String>
     */
    public HashMap<String, String> getObjStateMap() {
        STATE_MAP.put("PRIVATE", "DRA");
        STATE_MAP.put("IN_WORK", "DRA");
        STATE_MAP.put("FROZEN", "FRB");
        STATE_MAP.put("RELEASED", "NEW");
        STATE_MAP.put("OBSOLETE", "FRB");
        return STATE_MAP;
    }

    /*Getters*/
    public List<XmlMapElementAttribute> getItemBusinessObjectAttributeList() {
        return this.itemBusinessObjectAttributeList;
    }

    public SelectList getSelectBusStatements() {
        return this.selectBusStmts;
    }

    public HashMap<String, String> getItemValueMap() {
        return this.itemValueMap;
    }

    public HashMap<String, HashMap<String, List<XmlMapElementAttribute>>> getAttributeAndPropertyMap() {
        return this.attributeAndPropertyMap;
    }

    public synchronized static boolean isAlreadySentToLN(BusinessObjectWithSelect businessObjectWithSelect) {
        boolean sentToLN = false;
        try {
            String objType = businessObjectWithSelect.getSelectData("type");
            if (objType.equalsIgnoreCase(ApplicationProperties.getProprtyValue("val.componant.type.name"))) {
                //VAL_VALComponent.VAL_TransferredToERP
                String isVALComponentAlreadySendToLN = businessObjectWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("source.att.send.to.erp") + "]");
                if (isVALComponentAlreadySendToLN.length() == 0 || isVALComponentAlreadySendToLN.equalsIgnoreCase("FALSE")) {
                    sentToLN = false;
                } else if (isVALComponentAlreadySendToLN.equalsIgnoreCase("TRUE")) {
                    sentToLN = true;
                }
            } else if (objType.equalsIgnoreCase(ApplicationProperties.getProprtyValue("val.componant.matarial.type.name"))) {
                String isVALComponentMaterialAlreadySendToLN = businessObjectWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("source.att.send.to.erp") + "]");
                if (isVALComponentMaterialAlreadySendToLN.length() == 0 || isVALComponentMaterialAlreadySendToLN.equalsIgnoreCase("FALSE")) {
                    sentToLN = false;
                } else if (isVALComponentMaterialAlreadySendToLN.equalsIgnoreCase("TRUE")) {
                    sentToLN = true;
                }
            }
        } catch (Exception e) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(e.getMessage());
        }
        //sentToLN = false;
        return sentToLN;
    }

    public static void setMissingVALItemAttribute(RelationshipWithSelect relationShipWithSelect) throws Exception {
        Context context = ContextGeneration.context;
        BusinessObjectWithSelect businessObjectWithSelect = relationShipWithSelect.getTarget();
        String objId = businessObjectWithSelect.getSelectData("id");
        BusinessObject bo = null;
        try {
            bo = new BusinessObject(objId);
            bo.open(context);
            bo.setAttributeValue(context, ApplicationProperties.getProprtyValue("source.att.send.to.erp"), "TRUE");
            /*if (bo.getTypeName().equalsIgnoreCase(ApplicationProperties.getProprtyValue("val.componant.type.name"))) {               
                bo.setAttributeValue(context,ApplicationProperties.getProprtyValue("source.att.send.to.erp"), "TRUE");                

            } else if (bo.getTypeName().equalsIgnoreCase(ApplicationProperties.getProprtyValue("val.componant.matarial.type.name"))) {            
                bo.setAttributeValue(context, ApplicationProperties.getProprtyValue("source.att.send.to.erp"), "TRUE");
                
            }*/
        } catch (MatrixException ex) {
            ex.printStackTrace();
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        } finally {
            bo.close(context);
        }
    }

    public static void updateAttribute(Context context, String objectId, String attributeName, String attributeValue) throws MatrixException {
        BusinessObject bo = null;
        try {
            bo = new BusinessObject(objectId);
            bo.open(context);
            bo.setAttributeValue(context, attributeName, attributeValue);
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
            throw ex;
        } finally {
            bo.close(context);
        }
    }

    public static String getObjTitleTermId(String objectID, Context context) throws MatrixException, MalformedURLException, IOException {
        String termId = "";
        String command = "print bus " + objectID + " select attribute[" + ApplicationProperties.getProprtyValue("source.att.title.termId") + "].value dump |";
        MQLCommand mqlCommand = new MQLCommand();
        try {
            mqlCommand.executeCommand(context, command);
        } catch (MatrixException e) {
            e.printStackTrace();
            throw e;
        }
        termId = mqlCommand.getResult().trim();
        return termId;
    }

    public static Double roundToDouble(String value, int precision, String roundMode) {
        BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(value));
        if (value != null && !"".equals(value)) {
            double doubleValue;
            if (roundMode.equalsIgnoreCase("floor")) {
                doubleValue = bigDecimalValue.setScale(precision, BigDecimal.ROUND_DOWN).doubleValue();
            } else {
                doubleValue = bigDecimalValue.setScale(precision, BigDecimal.ROUND_UP).doubleValue();
            }
            return doubleValue;
        }
        return null;
    }

    public static Integer roundToInteger(String value, int precision, String roundMode) {
        BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(value));
        if (value != null && !"".equals(value)) {
            int intValue;
            if (roundMode.equalsIgnoreCase("floor")) {
                intValue = bigDecimalValue.setScale(precision, BigDecimal.ROUND_DOWN).intValue();
            } else {
                intValue = bigDecimalValue.setScale(precision, BigDecimal.ROUND_UP).intValue();
            }
            return intValue;
        }
        return null;
    }

    public static String getPromoteUser(BusinessObject bo, Context context) {
        SignatureList signatures;
        String promoteUserName = "";
        try {

            signatures = bo.getSignatures(context, FileDirProcess.itemCurrentStatus, FileDirProcess.itemNextStatus);

            Signature promoteSignature = null;
            if (signatures != null && signatures.size() >= 1) {
                promoteSignature = signatures.get(signatures.size() - 1);
            }
            promoteUserName = promoteSignature.getSigner();
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }
        return promoteUserName;
    }

    public static Map<String, String> getDrawingAndDocInfo(Context context, BusinessObject bo) {
        Map<String, String> drawingInfo = new HashMap<>();
        BusinessObject VPMReferenceBO = null;
        List<String> documentPhyIDList = new ArrayList<>();
        List<String> connectionObjectType = new ArrayList<String>() {
            {
                add(ApplicationProperties.getProprtyValue("item.type.resulting.product.process.cnx"));
                add(ApplicationProperties.getProprtyValue("item.type.process.cnx"));
                add(ApplicationProperties.getProprtyValue("item.type.plm.doc.conn"));
            }
        };
        List<String> connectionRelType = new ArrayList<String>() {
            {
                add(ApplicationProperties.getProprtyValue("rel.item.to.process.cnx"));
            }
        };

        List<BusinessObject> connectionObjectList = getConnectionObjectList(context, bo, connectionObjectType, connectionRelType);

        try {
            for (BusinessObject businessObject : connectionObjectList) {
                businessObject.open(context);
                if (businessObject.getTypeName().equals(ApplicationProperties.getProprtyValue("item.type.resulting.product.process.cnx"))
                        || businessObject.getTypeName().equals(ApplicationProperties.getProprtyValue("item.type.process.cnx"))) {
                    VPMReferenceBO = getVPMReferenceBO(businessObject, context);
                } else if (businessObject.getTypeName().equals(ApplicationProperties.getProprtyValue("item.type.plm.doc.conn"))) {
                    String documentPhysicalId = getDocumentPhyIDStr(context, businessObject);
                    if (documentPhysicalId.length() > 0 && isDrawingOrDocSupported(context, new BusinessObject(documentPhysicalId))) {
                        documentPhyIDList.add(documentPhysicalId);
                    }
                }
                businessObject.close(context);
            }

            if (VPMReferenceBO != null) {
                Map<String, List<String>> drawingInfoMap = expandBOForDrawingAndDocInfo(context, VPMReferenceBO);
                List<String> drawingNumberList = drawingInfoMap.get("drawingNumberList");
                List<String> documentAndDrawingPhyIDList = drawingInfoMap.get("documentPhyIdList");
                if (drawingNumberList.size() > 0) {
                    drawingInfo.put("drawingNumber", drawingNumberList.get(0));
                }
                if (documentAndDrawingPhyIDList.size() > 0) {
                    documentPhyIDList.addAll(documentAndDrawingPhyIDList);
                }
            }
            if (documentPhyIDList.size() > 0) {
                drawingInfo.put("documentID", String.join(",", new HashSet<String>(documentPhyIDList)));
            }
        } catch (Exception ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }
        return drawingInfo;
    }

    public static BusinessObject getVPMReferenceBO(BusinessObject ProcessImplementCnx, Context context) {
        String queryResult = "";
        BusinessObject VPMReferenceBO = null;
        try {
            String processImplementCnxID = "";
            if (ProcessImplementCnx != null) {
                processImplementCnxID = ProcessImplementCnx.getObjectId();
                if (processImplementCnxID.length() > 0) {
                    StringBuilder queryBuilder = new StringBuilder();
                    queryBuilder.append("print bus ").append(processImplementCnxID)
                            .append(" select paths.path dump |");
                    queryResult = MqlUtil.mqlCommand(context, queryBuilder.toString());
                    if ((queryResult != null && queryResult.length() > 0) && (queryResult.contains(ApplicationProperties.getProprtyValue("item.type.vpm.reference")))) {
                        String[] queryResultArr = queryResult.split(",");
                        List<String> queryResultList = Arrays.asList(queryResultArr);
                        int i = queryResultList.indexOf(ApplicationProperties.getProprtyValue("item.type.vpm.reference"));
                        String physicalID_VPMReference = (String) queryResultList.get(i + 1);
                        VPMReferenceBO = new BusinessObject(physicalID_VPMReference);
                    }
                }
            }
        } catch (Exception ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }
        return VPMReferenceBO;
    }

    public static List<BusinessObject> getConnectionObjectList(Context context, BusinessObject bo, List<String> typeList, List<String> rel) {
        List<BusinessObject> connectionObjectList = new ArrayList<>();
        SelectList selectBusStmts = new SelectList();
        SelectList selectRelStmts = new SelectList();
        String patternDelim = ",";
        Pattern typePattern = new Pattern(String.join(patternDelim, typeList));
        Pattern relPattern = new Pattern(String.join(",", rel));
        String busWhereExpression = "";
        String relWhereExpression = "";
        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short("1");

        try {
            expandResult = bo.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    false, true,
                    expandLevel, busWhereExpression, relWhereExpression, false);
            relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
            BusinessObject childBO = null;
            while (relItr.next()) {
                RelationshipWithSelect relSelect = relItr.obj();
                childBO = relSelect.getTo();
                if (childBO != null) {
                    connectionObjectList.add(childBO);
                }
            }
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        } catch (Exception ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }
        return connectionObjectList;
    }

    public static Map<String, List<String>> expandBOForDrawingAndDocInfo(Context context, BusinessObject bo) throws MatrixException {
        SelectList selectBusStmts = new SelectList();
        SelectList selectRelStmts = new SelectList();
        List<String> typeList = new ArrayList<String>() {
            {
                add(ApplicationProperties.getProprtyValue("item.type.drawing"));
                add(ApplicationProperties.getProprtyValue("item.type.plm.doc.conn"));
            }
        };
        List<String> relList = new ArrayList<String>() {
            {
                add(ApplicationProperties.getProprtyValue("rel.vpmreference.to.drwaing"));
                add(ApplicationProperties.getProprtyValue("rel.item.to.process.cnx"));
            }
        };

        Pattern typePattern = new Pattern(String.join(",", typeList));
        Pattern relPattern = new Pattern(String.join(",", relList));
        String busWhereExpression = "";
        String relWhereExpression = "";
        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short("1");
        String drawingNumber = "";
        Map<String, List<String>> drawingInfoMap = new HashMap<>();
        List<String> drawingNumberList = new LinkedList<>();
        List<String> documentPhyIdList = new ArrayList<>();
        SelectList selectBODataList = new SelectList();
        selectBODataList.add("name");
        selectBODataList.add("revision");
        selectBODataList.add("physicalid");
        selectBODataList.add("attribute[" + ApplicationProperties.getProprtyValue("item.att.document.id") + "]");

        try {
            expandResult = bo.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    false, true,
                    expandLevel, busWhereExpression, relWhereExpression, false);
            relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
            BusinessObject childBO = null;
            while (relItr.next()) {
                RelationshipWithSelect relSelect = relItr.obj();
                childBO = relSelect.getTo();
                childBO.open(context);
                if (childBO.getTypeName().equals(ApplicationProperties.getProprtyValue("item.type.plm.doc.conn"))) {
                    String documentPhyID = getDocumentPhyIDStr(context, childBO);
                    if (documentPhyID.length() > 0 && isDrawingOrDocSupported(context, new BusinessObject(documentPhyID))) {
                        documentPhyIdList.add(documentPhyID);
                    }
                } else {
                    BusinessObjectWithSelect drawingBOWithSelect = childBO.select(context, selectBODataList);
                    String name = drawingBOWithSelect.getSelectData("name");
                    String revision = drawingBOWithSelect.getSelectData("revision");
                    String documentID = drawingBOWithSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("item.att.document.id") + "]");
                    TRANSFER_OBJECT_UTILS_LOGGER.info("\n\n\nPrinting Drawing Info " + "Name: " + name + ", Revision: " + revision + ", Document Id: " + documentID + ", Physical ID: " + drawingBOWithSelect.getSelectData("physicalid"));
                    String drawingPhysicalId = drawingBOWithSelect.getSelectData("physicalid");
                    if (drawingPhysicalId.length() > 0 && isDrawingOrDocSupported(context, new BusinessObject(drawingPhysicalId))) {
                        drawingNumber = documentID.equals("") ? name + "_" + revision : documentID + " " + revision;
                        drawingNumberList.add(drawingNumber);
                        documentPhyIdList.add(drawingPhysicalId);
                    }
                }
                childBO.close(context);
            }
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        } catch (Exception ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }
        Collections.sort(drawingNumberList);
        drawingInfoMap.put("drawingNumberList", drawingNumberList);
        drawingInfoMap.put("documentPhyIdList", documentPhyIdList);
        return drawingInfoMap;
    }

    private static String getDocumentPhyIDStr(Context context, BusinessObject bo) {
        String queryResult = "";
        String physicalIdOfDocument = "";
        String documentObjectId = bo.getObjectId();
        if (documentObjectId.length() > 0) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("print bus ").append(documentObjectId)
                    .append(" select paths.path dump |");
            try {
                MQLCommand mqlCommand = new MQLCommand();
                mqlCommand.executeCommand(context, queryBuilder.toString());
                queryResult = mqlCommand.getResult();
                TRANSFER_OBJECT_UTILS_LOGGER.info("Query result for Document::  " + queryResult);
            } catch (MatrixException ex) {
                TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
            } catch (Exception ex) {
                TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
            }
            if ((queryResult != null && queryResult.length() > 0)
                    && (queryResult.contains(ApplicationProperties.getProprtyValue("item.type.doc"))
                    || queryResult.contains(ApplicationProperties.getProprtyValue("item.type.plm.dmt.doc")))) {
                String[] queryResultArr = queryResult.split(",");
                List<String> queryResultList = Arrays.asList(queryResultArr);
                int i = queryResultList.indexOf(ApplicationProperties.getProprtyValue("item.type.doc")) == -1
                        ? queryResultList.indexOf(ApplicationProperties.getProprtyValue("item.type.plm.dmt.doc"))
                        : queryResultList.indexOf(ApplicationProperties.getProprtyValue("item.type.doc"));
                physicalIdOfDocument = queryResultList.get(i + 1);
            }
        }
        return physicalIdOfDocument;
    }

    /**
     *
     * @param context
     * @param bo
     * @return
     */
    public static boolean isDrawingOrDocSupported(Context context, BusinessObject bo) {
        try {
            bo.open(context);
            if (bo.getTypeName().equalsIgnoreCase(ApplicationProperties.getProprtyValue("item.type.doc"))) {
                return true;
            }
            String docDistributionListValue = bo.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.attribute.DocDistributionList")).getValue();
            TRANSFER_OBJECT_UTILS_LOGGER.info("Drawing or Doc (DocDistributionList) Attribute value: " + docDistributionListValue);
            String supportedValuesAsStr = ApplicationProperties.getProprtyValue("supported.drawing.doc.att.DOC_DocDistributionList");
            List<String> SupportedValuesList = Arrays.asList(supportedValuesAsStr.split(","));
            if (SupportedValuesList.size() > 0 && SupportedValuesList.contains(docDistributionListValue)) {
                TRANSFER_OBJECT_UTILS_LOGGER.info("Drawing or Doc is supported to LN.");
                return true;
            }
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex);
        } catch (Exception ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex);
        } finally {
            try {
                bo.close(context);
            } catch (MatrixException ex) {
                TRANSFER_OBJECT_UTILS_LOGGER.error(ex);
            }
        }
        TRANSFER_OBJECT_UTILS_LOGGER.info("Drawing or Doc is not supported to LN.");
        return false;
    }

    /**
     *
     * @param context
     * @param changeActionBO
     * @return
     */
    public BusinessObject getTaskBOFromChangeAction(Context context, BusinessObject changeActionBO) {
        SelectList selectBusStmts = new SelectList();
        SelectList selectRelStmts = new SelectList();
        Pattern typePattern = new Pattern("Task");
        Pattern relPattern = new Pattern("Task Deliverable");
        String busWhereExpression = "";
        String relWhereExpression = "";
        ExpansionWithSelect expandResult;
        RelationshipWithSelectItr relItr = null;
        Short expandLevel = new Short("1");
        BusinessObject taskBusinessObject = null;
        try {
            expandResult = changeActionBO.expandSelect(
                    context, relPattern.getPattern(),
                    typePattern.getPattern(), selectBusStmts, selectRelStmts,
                    true, false,
                    expandLevel, busWhereExpression, relWhereExpression, false);
            relItr = new RelationshipWithSelectItr(expandResult.getRelationships());
            while (relItr.next()) {
                RelationshipWithSelect relSelect = relItr.obj();
                BusinessObject childBo = relSelect.getFrom();
                childBo.open(context);
                if (childBo.getTypeName().equalsIgnoreCase("Task")) {
                    taskBusinessObject = childBo;
                    break;
                }
                childBo.close(context);
            }
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        } catch (Exception ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }

        return taskBusinessObject;
    }

    /**
     *
     * @param context
     * @param changeActionBO
     * @return
     */
    public Map<String, String> getTaskAndProjectNameFromCA(Context context, BusinessObject changeActionBO) {
        Map<String, String> taskProjectMap = new HashMap<>();
        BusinessObject taskBO = getTaskBOFromChangeAction(context, changeActionBO);
        try {
            if (taskBO != null) {
                taskBO.open(context);
                taskProjectMap.put("task", taskBO.getName());
                TRANSFER_OBJECT_UTILS_LOGGER.info("Task/Activity Name : " + taskBO.getName());
                BusinessObject projectBO = getProjectNameFromTaskObj(context, taskBO);
                if (projectBO != null) {
                    projectBO.open(context);
                    taskProjectMap.put("project", projectBO.getName());
                    projectBO.close(context);
                } else {
                    TRANSFER_OBJECT_UTILS_LOGGER.info("No Project found with the Task/Activity!");
                }
                taskBO.close(context);
            } else {
                TRANSFER_OBJECT_UTILS_LOGGER.info("No Task found with the CA!");
            }
        } catch (MatrixException ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        }
        return taskProjectMap;
    }

    /**
     *
     * @param itemName
     * @return itemExistInLN
     */
    public boolean checkIfItemExistInLN(String itemName) {
        Boolean itemExist = false;
        GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL multilevelBOMDetailsVal = new GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL();
        multilevelBOMDetailsVal.setItem(itemName);
        multilevelBOMDetailsVal.setLevel("1");
        List<GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL> multilevelBOMDetailsValList = new ArrayList<GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL>();
        multilevelBOMDetailsValList.add(multilevelBOMDetailsVal);
        MultilevelBomDetailsBean multilevelBomDetailsBean = new MultilevelBomDetailsBean();
        MultilevelBOMDetails multiBOM = new MultilevelBOMDetails();
        try {
            GetBOMDetailsResponseType bomDetailsResponse = multiBOM.getBOMDetailsFromLn(multilevelBOMDetailsValList);
            String error = checkErrorInBOMDetailResponse(bomDetailsResponse);
            if (error == null || error.isEmpty()) {
                List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> multilevelBOMDetailsVALList = bomDetailsResponse.getDataArea().getMultilevelBOMDetailsVAL();
                if (multilevelBOMDetailsVALList != null && !multilevelBOMDetailsVALList.isEmpty()) {
                    String itemSearchedFromLN = multilevelBOMDetailsVALList.get(0).getItem().trim();
                    itemExist = itemSearchedFromLN.equalsIgnoreCase(itemName);
                }
            }
        } catch (Result ex) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(ex.getMessage());
        } catch (Exception e) {
            TRANSFER_OBJECT_UTILS_LOGGER.error(e.getMessage());
        }
        return itemExist;
    }

    /**
     *
     * @param BOMDetailsResponse
     * @return errorMessage
     * @throws Exception
     */
    public String checkErrorInBOMDetailResponse(GetBOMDetailsResponseType BOMDetailsResponse) throws Exception {
        String errorMessage = "";
        if (!NullOrEmptyChecker.isNull(BOMDetailsResponse.getInformationArea())) {
            if (!NullOrEmptyChecker.isNull(BOMDetailsResponse.getInformationArea().getMessage())) {
                StringBuilder errorBuilder = new StringBuilder();
                for (Message msg : BOMDetailsResponse.getInformationArea().getMessage()) {
                    if (!NullOrEmptyChecker.isNullOrEmpty(msg.getMessageText())) {
                        errorBuilder.append(msg.getMessageText());
                    }
                }
                errorMessage = errorBuilder.toString();
            }
        }
        return errorMessage;
    }

    /**
     *
     * @param context
     * @param bo
     * @return laterRevision
     */
    public static void checkPreviousRevision(Context context, BusinessObject bo) throws MatrixException {

        StringBuilder cmdBuilder = new StringBuilder();
        String selectionCode = "";
        try {
            cmdBuilder.append("temp query bus ")
                    .append(bo.getTypeName()).append(" ")
                    .append(bo.getName()).append(" ")
                    .append("1.1").append(" ")
                    .append("select attribute[" + ApplicationProperties.getProprtyValue("source.att.selection.code") + "].value dump |");
            String s = MqlUtil.mqlCommand(context, cmdBuilder.toString());
            String[] split = s.split("\n");
            for (int i = 0; i < split.length; i++) {
                String[] laterDetails = split[i].split("\\|");
                selectionCode = laterDetails[3];
            }
        } catch (MatrixException ex) {
            System.out.println(ex.getMessage());
        }
        updateAttribute(context, bo.getObjectId(), ApplicationProperties.getProprtyValue("source.att.selection.code"), selectionCode);
    }

    /**
     *
     * @param context
     * @param bo
     * @return revision
     */
    public String getSelectionCode(Context context, BusinessObject bo, SelectList buildBusStmnts) throws MatrixException {

        if (buildBusStmnts == null) {
            buildBusStmnts = buildBusStmnts();
        }
        BusinessObjectWithSelect busSelect = bo.select(context, buildBusStmnts);
        String selectionCode = busSelect.getSelectData("attribute[" + ApplicationProperties.getProprtyValue("source.att.selection.code") + "]");

        return selectionCode;
    }

    public String isTransfferedToErp(Context context, BusinessObject bo) throws MatrixException {

        StringBuilder cmdBuilder = new StringBuilder();
        String transferredToERP = "";
        try {
            cmdBuilder.append("temp query bus ")
                    .append(bo.getTypeName()).append(" ")
                    .append(bo.getName()).append(" ")
                    .append(bo.getRevision()).append(" ")
                    .append("select attribute[" + ApplicationProperties.getProprtyValue("source.att.send.to.erp") + "].value dump |");
            String s = MqlUtil.mqlCommand(context, cmdBuilder.toString());
            String[] split = s.split("\n");
            for (int i = 0; i < split.length; i++) {
                String[] laterDetails = split[i].split("\\|");
                transferredToERP = laterDetails[3];
                TRANSFER_OBJECT_UTILS_LOGGER.info("Transferred To ERP " + transferredToERP);

            }
        } catch (MatrixException ex) {
            System.out.println(ex.getMessage());
        }

        return transferredToERP;

    }

    public String getAllowTransferredToErpValue(Context context, BusinessObject bo) throws MatrixException {

        SelectList selectRel = buildRelStmnsts();
        BusinessObjectWithSelect busSelect = bo.select(context, selectRel);
        String transferredToERP = busSelect.getSelectData("item.transfer.to.erp");
        return transferredToERP;

    }

    public String getMBOMPosition(Context context, BusinessObject bo) throws MatrixException {

        SelectList selectRel = buildRelStmnsts();
        BusinessObjectWithSelect busSelect = bo.select(context, selectRel);

        String position = busSelect.getSelectData("source.rel.position");

        return position;

    }

    public String mastershipCheck(Context context, BusinessObject bo) throws MatrixException {

        BusinessObjectWithSelect busSelect = bo.select(context, selectBusStmts);

        String mastership = busSelect.getAttributeValues(context, ApplicationProperties.getProprtyValue("item.att.mastership")).getValue();

        return mastership;

    }

    public static String checkRevision(Context context, String name) throws MatrixException {

        StringBuilder cmdBuilder = new StringBuilder();
        String rev = "";
        try {
            cmdBuilder.append("temp query bus * ")
                    .append(name).append(" * ")
                    .append("select revision dump |");
            String s = MqlUtil.mqlCommand(context, cmdBuilder.toString());
            String[] split = s.split("\n");
            for (int i = 0; i < split.length; i++) {
                String[] laterDetails = split[i].split("\\|");
                rev = laterDetails[2];
            }
        } catch (MatrixException ex) {
            System.out.println(ex.getMessage());
        }
        return rev;

    }
}