/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities;

import com.bjit.ex.integration.externalservices.GTS.bundleId.service.GTSBundleIdResponse;
import com.bjit.ex.integration.mapproject.xml_mapping_model.XmlMapElementAttribute;
import com.bjit.ex.integration.model.webservice.Item;
import com.bjit.ex.integration.transfer.service.impl.LN.Constants;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType.DataArea.ItemVAL;
import com.infor.businessinterface.item_val.TckitmMandatory;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import matrix.db.BusinessObject;
import matrix.db.BusinessObjectWithSelect;
import matrix.db.Context;
import matrix.util.MatrixException;
import matrix.util.SelectList;

/**
 * @author BJIT
 */
public class ItemUtils {

    private static final org.apache.log4j.Logger ITEM_UTILS_LOGGER = org.apache.log4j.Logger.getLogger(ItemUtils.class);
    private List<XmlMapElementAttribute> itemBusinessObjectAttributeList;
    private List<XmlMapElementAttribute> itemBusinessObjectPropertyList;
    private List<ItemVAL> itemValList;
    private HashMap<String, String> itemValueMap;
    private HashMap<String, HashMap<String, List<XmlMapElementAttribute>>> attributeAndPropertyMap;
    private SelectList selectBusStmts;
    private final String XML_ATTRIBUTE_MAPPING_KEY = Constants.XML_ATTRIBUTE_MAPPING_KEY;
    private final String XML_PROPERTY_MAPPING_KEY = Constants.XML_PROPERTY_MAPPING_KEY;
    public final static String DEFAULT_PSG_CODE = "------";
    public final static String DATE_FORMAT_ENOVIA = "MM/dd/yyyy hh:mm:ss a";

    private TransferObjectUtils transferObjectUtils;

    private enum NoteTypes {
        ITEM_TEXT("ItemText"),
        CHANGE_INFORMATION("ChangeInformationText"),
        ITEM_PURCHASING_TEXT("PurchaseItemText"),
        ITEM_MANUFACTURING_TEXT("ProductionItemText");

        private final String value;

        NoteTypes(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public ItemUtils() {

    }

    public void __init__(TransferObjectUtils transferObjectUtils) {
        this.transferObjectUtils = transferObjectUtils;
        this.itemBusinessObjectAttributeList = this.transferObjectUtils.getItemBusinessObjectAttributeList();
        this.itemBusinessObjectPropertyList = this.transferObjectUtils.getItemBusinessObjectAttributeList();
        this.attributeAndPropertyMap = this.transferObjectUtils.getAttributeAndPropertyMap();
        this.itemValueMap = this.transferObjectUtils.getItemValueMap();
        this.selectBusStmts = this.transferObjectUtils.getSelectBusStatements();
        this.itemValList = new ArrayList<>();
    }

    private static Boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Creates an ItemValue Map where keys the destination names are and values
     * are the values from source entities of ENOVIA database
     *
     * @param businessObject
     * @param context
     * @throws MatrixException
     */
    public HashMap<String, String> createItemValueMap(BusinessObject businessObject, Context context) throws MatrixException {
        ITEM_UTILS_LOGGER.debug("Creating Item Value Map");
        itemBusinessObjectAttributeList = getItemObjectAttributeList(businessObject);
        itemBusinessObjectPropertyList = getItemObjectPropertyList(businessObject);

        BusinessObjectWithSelect busSelect = businessObject.select(context, selectBusStmts);
        businessObject.close(context);
        itemValueMap = this.transferObjectUtils.getBusinessObjectsAttributesAndProperties(context, busSelect, businessObject);
        return itemValueMap;
    }

    // Coding Started For Root Item
    /**
     * Searches for item properties of specific type and xmlMapAttribute
     *
     * @param businessObject
     * @return
     */
    public List<XmlMapElementAttribute> getItemObjectAttributeList(BusinessObject businessObject) {
        return attributeAndPropertyMap.get(businessObject.getTypeName()).get(XML_ATTRIBUTE_MAPPING_KEY);
    }

    /**
     * Searches for item properties of specific type and xmlMapProperty
     *
     * @param businessObject
     * @return
     */
    public List<XmlMapElementAttribute> getItemObjectPropertyList(BusinessObject businessObject) {
        return attributeAndPropertyMap.get(businessObject.getTypeName()).get(XML_PROPERTY_MAPPING_KEY);
    }

    // Coding Ended For Root Item
    /**
     * Returns the business objects attributes list by mapping the key business
     * objects type and by the key like xmlMapAttribute
     *
     * @param businessObjectWithSelect
     * @return
     */
    public List<XmlMapElementAttribute> getItemObjectAttributeList(BusinessObjectWithSelect businessObjectWithSelect) {
        return attributeAndPropertyMap.get(businessObjectWithSelect.getSelectData("type")).get(XML_ATTRIBUTE_MAPPING_KEY);
    }

    /**
     * Returns the business objects properties list by mapping the key business
     * objects type and by the key like xmlMapProperty
     *
     * @param businessObjectWithSelect
     * @return
     */
    public List<XmlMapElementAttribute> getItemObjectPropertyList(BusinessObjectWithSelect businessObjectWithSelect) {
        return attributeAndPropertyMap.get(businessObjectWithSelect.getSelectData("type")).get(XML_PROPERTY_MAPPING_KEY);
    }

    public List<XmlMapElementAttribute> getItemObjectAttributeAndPropertyList(BusinessObject bo) {
        List<XmlMapElementAttribute> itemObjAttrAndPropertyList = new ArrayList<>();
        itemObjAttrAndPropertyList.addAll(getItemObjectAttributeList(bo));
        itemObjAttrAndPropertyList.addAll(getItemObjectPropertyList(bo));
        return itemObjAttrAndPropertyList;
    }

    /**
     * Searches for the itemValue Creates an Item as the root item and Sets
     * attributes properties and returns an item
     *
     * @param itemsValueMap
     * @param businessObject
     * @param rootAbbreviationData
     * @return
     * @throws MatrixException
     */
    public ItemVAL setItemValues(HashMap<String, String> itemsValueMap, BusinessObject businessObject, GTSBundleIdResponse rootAbbreviationData, Context context) throws MatrixException {
        ITEM_UTILS_LOGGER.debug("Setting Item Values For : " + businessObject.getTypeName());
        itemBusinessObjectAttributeList = getItemObjectAttributeList(businessObject);
        itemBusinessObjectPropertyList = getItemObjectPropertyList(businessObject);

        if (itemsValueMap != null && itemsValueMap.size() > 0) {
            ItemVAL item = new ItemVAL();
            //item = setItemsAttributes(item, itemsValueMap, businessObject, rootAbbreviationData);
            item = setItemsAttributes(item, businessObject, itemsValueMap, rootAbbreviationData, context);
            item = setItemsProperties(item, itemsValueMap);
            item = setItemsStaticValues(item);
            return item;
        }
        return null;
    }

    /**
     * Creates an Item and sets it's attributes, properties and static values
     * for a property file.
     *
     * @param itemsValueMap
     * @param businessObjectWithSelect
     * @param abbreviationData
     * @return
     * @throws MatrixException
     */
    public ItemVAL setItemValues(HashMap<String, String> itemsValueMap, BusinessObjectWithSelect businessObjectWithSelect, GTSBundleIdResponse abbreviationData, Context context) throws MatrixException {
        ITEM_UTILS_LOGGER.debug("Setting Item Values For : " + businessObjectWithSelect.getSelectData("type"));
        itemBusinessObjectAttributeList = getItemObjectAttributeList(businessObjectWithSelect);
        itemBusinessObjectPropertyList = getItemObjectPropertyList(businessObjectWithSelect);
        BusinessObject bo = new BusinessObject(businessObjectWithSelect.getObjectId());

        if (itemsValueMap != null && itemsValueMap.size() > 0) {
            ItemVAL item = new ItemVAL();
            //item = setItemsAttributes(item, itemsValueMap, businessObjectWithSelect,abbreviationData);
            item = setItemsAttributes(item, bo, itemsValueMap, abbreviationData, context);
            item = setItemsProperties(item, itemsValueMap);
            item = setItemsStaticValues(item, businessObjectWithSelect);
            return item;
        }
        return null;
    }

    /**
     * Sets attributes to an item
     * <p>
     * attributeValueMap is the itemValueMap
     *
     * @param item
     * @param attributeValueMap
     * @param abbreviationData
     * @return
     */
    public ItemVAL setItemsAttributes(ItemVAL item, BusinessObject businessObject, HashMap<String, String> attributeValueMap, GTSBundleIdResponse abbreviationData, Context context) {
        String titleEnglish = "";
        String attrDrawingNumber = "";
        Map<String, String> noteTextMap = new HashMap<>();
        HashMap<String, HashMap<String, String>> abbreviationDataMap = new HashMap<>();
        for (XmlMapElementAttribute xmlMapAttribute : itemBusinessObjectAttributeList) {

            // Fetching description information from GTS
//            if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("description")) {
//                ItemVAL.Description description = new ItemVAL.Description();
//                description.setLanguageID("en_US");
//                String strDescription = attributeValueMap.get(xmlMapAttribute.getDestinationName());
//                strDescription = getLNSupportedStringsMaxLengthValue(strDescription);
//                description.setValue(strDescription);
//                item.getDescription().add(description);
//                if (strDescription != null && strDescription.length() > 0) {
//                    titleEnglish = strDescription;
//                }
//                if (abbreviationData != null) {
//                    abbreviationDataMap = abbreviationData.getAbbreviationDataMap();
//                    for (String languageCode : abbreviationDataMap.keySet()) {
//                        ItemVAL.Description otherLangDescription = new ItemVAL.Description();
//                        otherLangDescription.setLanguageID(languageCode);
//                        String abbreValue = abbreviationDataMap.get(languageCode);
//                        abbreValue = getLNSupportedStringsMaxLengthValue(abbreValue);
//                        otherLangDescription.setValue(abbreValue);
//                        item.getDescription().add(otherLangDescription);
//                    }
//                }
//            }
            if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Technical Designation")) {
                String technicalDesignation = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                noteTextMap.put("TechnicalDesignation", technicalDesignation);
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Item Common Text")) {
                String itemCommonText = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                noteTextMap.put("ItemCommonText", itemCommonText);
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Item Purchasing Text")) {
                String itemPurchasingText = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                noteTextMap.put("ItemPurchasingText", itemPurchasingText);
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Item Manufacturing Text")) {
                String itemManufacturingText = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                noteTextMap.put("ItemManufacturingText", itemManufacturingText);
            } /*else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Technical Coordinator")) {
                String personNumber = BusinessObjectUtils.getPersonHomeNumber(context, attributeValueMap.get(xmlMapAttribute.getDestinationName()));
                item.setTechnicalCoordinator(personNumber);
            } */ else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Commodity Code")) {
                item.setCommodityCode(attributeValueMap.get(xmlMapAttribute.getDestinationName()));
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Commodity Code US")) {
                item.setCommodityCodeUS(attributeValueMap.get(xmlMapAttribute.getDestinationName()));
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Commodity Code CH")) {
                //item.setCommodityCodeCH(attributeValueMap.get(xmlMapAttribute.getDestinationName()));
                item.setCommodityCodeCN(attributeValueMap.get(xmlMapAttribute.getDestinationName()));
            } /*else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Drawing Number")) {
                String drawNo = TransferObjectUtils.getDrawingInfo(context, businessObject);
                System.out.println("\n\n\n\n drawNo :::: "+drawNo);
                item.setDrawingNumber(drawNo);
//                if (drawNo.length() > 0) {
//                    String drawRev = attributeValueMap.get("Drawing Revision");
//                    String drawNoLN = drawNo + "_" + drawRev;
//                    item.setDrawingNumber(drawNoLN);
//                }
            }*/ //Newly added attribute ... not varified yet Start... 
            /*else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Product Line")) {
                ItemVAL.ProductLine productLine = new ItemVAL.ProductLine();
                productLine.setID("");
                productLine.setLineDescription("");
                item.setProductLine(productLine);
            } */ else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Weight")) {
                if (attributeValueMap.get(xmlMapAttribute.getDestinationName()).length() > 0) {
                    String weight = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                    int len = weight.length();
                    if (len > 20) {
                        weight = weight.substring(0, 20);
                    }
//                    BigDecimal weightDecimal = new BigDecimal(String.valueOf(weight));
//                    weightDecimal = weightDecimal.setScale(4, BigDecimal.ROUND_DOWN);
                    Double weightDoublevalue = TransferObjectUtils.roundToDouble(weight, 4, "floor");
                    if (weightDoublevalue != null) {
                        item.setWeight(new BigDecimal(String.valueOf(weightDoublevalue)));
                    }
                }
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Material")) {
                String material = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                item.setMaterial(material != null ? material : "");
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Size")) {
                String size = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                item.setSize(size != null ? size : "");
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Item Type")) {
                String outsourced = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                outsourced = (outsourced == null) ? "" : outsourced;
                outsourced = outsourced.trim();
                TckitmMandatory tckItmMandarotyItemType;
                if (outsourced.equals("0") || outsourced.equals("1")) {
                    tckItmMandarotyItemType = TckitmMandatory.MANUFACTURE;
                } else {
                    tckItmMandarotyItemType = TckitmMandatory.PURCHASE;
                }
                item.setItemType(tckItmMandarotyItemType);
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("PSG")) {
                String psg = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                if (psg != null && psg.length() > 0) {
                    item.setPurchaseStatisticsGroup(psg);
                } else {
                    item.setPurchaseStatisticsGroup(DEFAULT_PSG_CODE);
                }

            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Unit Set")) {
                String unitSet = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                item.setUnitSet(ApplicationProperties.getProprtyValue("ln.item.unitSet"));
                if (unitSet != null && unitSet.equalsIgnoreCase("Imperial")) {
                    item.setWeightUnit("lb");
                } else {
                    item.setWeightUnit("kg");
                }
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Inventory Unit")) {
                String inventoryUnit = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                if (inventoryUnit != null && !inventoryUnit.equals("")) {
                    if (inventoryUnit.equals("l")) {
                        item.setInventoryUnit("L");
                    } else {
                        item.setInventoryUnit(inventoryUnit);
                    }
                } else {
                    item.setInventoryUnit(ApplicationProperties.getProprtyValue("default.inventory.unit"));
                }
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Purchase Unit")) {
                String purchaseUnit = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                if (purchaseUnit != null && !purchaseUnit.equals("")) {
                    if (purchaseUnit.equals("l")) {
                        item.setPurchaseUnit("L");
                    } else {
                        item.setPurchaseUnit(purchaseUnit);
                    }
                } else {
                    item.setPurchaseUnit(ApplicationProperties.getProprtyValue("default.purchase.unit"));
                }
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Drawing Number")) {
                String hagDrawingNumber = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                if (hagDrawingNumber != null && !hagDrawingNumber.equals("")) {
                    attrDrawingNumber = hagDrawingNumber;
                }
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Source Item")) {
                item.setSourceItem(attributeValueMap.get(xmlMapAttribute.getDestinationName()));
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Serial Number Required")) {
                String isSerialized = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                if (isSerialized.equalsIgnoreCase("TRUE")) {
                    item.setSerialized(Boolean.TRUE.toString());
                }
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Lot Number Required")) {
                item.setLotControlled(attributeValueMap.get(xmlMapAttribute.getDestinationName()));
            } else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Selection Code")) {
                item.setSelectionCode(attributeValueMap.get(xmlMapAttribute.getDestinationName()));
            } /*else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Revision Description")) {
                String revDes = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                revDes = getLNSupportedStringsMaxLengthValue(revDes);
                item.setRevisionDescription(revDes);
                ItemVAL.Note revNote = new ItemVAL.Note();
                revNote.setLanguageID("EN-US");
                revNote.setType(noteType_changeInformation);
                String revNoteValue = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                revNoteValue = getLNSupportedStringsMaxLengthValue(revNoteValue);
                revNote.setValue(revNoteValue);
                item.getNote().add(revNote);
            }*/ else if (xmlMapAttribute.getDestinationName().equalsIgnoreCase("Revision Description")) {
                String revDes = attributeValueMap.get(xmlMapAttribute.getDestinationName());
                item.setRevisionDescription(getLNSupportedStringsMaxLengthValue(revDes));
                noteTextMap.put("ChangeInformation", revDes);
            }
        }

        setNoteValues(item, noteTextMap);

        setItemDescription(item, abbreviationData, noteTextMap.get("TechnicalDesignation"));

        setLongItemDescription(item, abbreviationData, noteTextMap.get("TechnicalDesignation"));

        Map<String, String> drawingInfo = TransferObjectUtils.getDrawingAndDocInfo(context, businessObject);
        setDrawingAndDocInfo(item, drawingInfo, attrDrawingNumber);

        for (int i = 0; i < itemBusinessObjectPropertyList.size(); i++) {
            if (itemBusinessObjectPropertyList.get(i).getDestinationName().equalsIgnoreCase("Technical Coordinator")) {
                String promoteUserName = TransferObjectUtils.getPromoteUser(businessObject, context);
                String personNumber = BusinessObjectUtils.getPersonHomeNumber(context, promoteUserName);
                item.setTechnicalCoordinator(personNumber);
                //item.setTechnicalCoordinator(attributeValueMap.get(itemBusinessObjectPropertyList.get(i).getDestinationName()).substring(0, 2));
            } else if (itemBusinessObjectPropertyList.get(i).getDestinationName().equalsIgnoreCase("Product Class")) {
                ItemVAL.ProductClass productClass = new ItemVAL.ProductClass();
                String project = attributeValueMap.get(itemBusinessObjectPropertyList.get(i).getDestinationName());
                productClass.setID(getProductClassFromProject(project));
                productClass.setClassDescription(project);
                item.setProductClass(productClass);
            } else if (itemBusinessObjectPropertyList.get(i).getDestinationName().equalsIgnoreCase("Creation Date")) {
                String created = attributeValueMap.get(itemBusinessObjectPropertyList.get(i).getDestinationName());
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_ENOVIA);
                Date createdDate;
                try {
                    createdDate = dateFormat.parse(created);
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(createdDate);
                    XMLGregorianCalendar creationDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
                    JAXBElement<XMLGregorianCalendar> createdJaxbElem = new JAXBElement<XMLGregorianCalendar>(new QName("creationDate"), XMLGregorianCalendar.class, creationDate);
                    //item.setCreationDate(createdJaxbElem);
                    item.setCreationDate(creationDate);
                } catch (ParseException ex) {
                    Logger.getLogger(ItemUtils.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(ItemUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return item;
    }

    private void setItemDescription(ItemVAL item, GTSBundleIdResponse abbreviationData, String technicalDesignation) {
        if (abbreviationData != null) {
            HashMap<String, HashMap<String, String>> abbreviationDataMap = abbreviationData.getAbbreviationDataMap();
            if (abbreviationDataMap != null && abbreviationDataMap.size() > 0) {
                for (String languageID : abbreviationDataMap.keySet()) {
                    StringBuilder descriptionBuilder = new StringBuilder();
                    ItemVAL.Description description = new ItemVAL.Description();
                    description.setLanguageID(languageID);
                    String shortName = abbreviationDataMap.get(languageID).get("abbreviation");

                    if (shortName != null && shortName.length() > 0) {
                        descriptionBuilder.append(shortName);
                    }

                    if (technicalDesignation != null && technicalDesignation.length() > 0) {
                        if (descriptionBuilder.length() > 0) {
                            descriptionBuilder.append(" ");
                        }
                        descriptionBuilder.append(technicalDesignation);
                    }

                    description.setValue(getLNSupportedStringsMaxLengthValueInByte(descriptionBuilder.toString()));
                    item.getDescription().add(description);
                }
            }
        }
    }

    private void setNoteValues(ItemVAL item, Map<String, String> noteValueMap) {
        StringBuilder itemTextBuilder = new StringBuilder();
        ItemVAL.Note itemTextNote = new ItemVAL.Note();
        itemTextNote.setLanguageID(ApplicationProperties.getProprtyValue("LN.default.language.id"));
        itemTextNote.setType(NoteTypes.ITEM_TEXT.getValue());
        if (noteValueMap.containsKey("TechnicalDesignation")) {
            String technicalDesigVal = noteValueMap.get("TechnicalDesignation");
            if (technicalDesigVal != null && technicalDesigVal.trim().length() > 0) {
                itemTextBuilder.append(ApplicationProperties.getProprtyValue("LN.item.text.technical.designation.prefix"));
                itemTextBuilder.append(":");
                itemTextBuilder.append(" ");
                itemTextBuilder.append(technicalDesigVal);
            }
        }
        if (noteValueMap.containsKey("ItemCommonText")) {
            String itemCommonTextVal = noteValueMap.get("ItemCommonText");
            if (itemCommonTextVal != null && itemCommonTextVal.trim().length() > 0) {

                if (itemTextBuilder.toString().trim().length() > 0) {

                    itemTextBuilder.append(ApplicationProperties.getProprtyValue("LN.note.text.separator.newline"));
//                    itemTextBuilder.append(" ");
                }
                itemTextBuilder.append(itemCommonTextVal);
            }
        }
        if (noteValueMap.containsKey("ItemPurchasingText")) {
            ItemVAL.Note purchasingTextNote = new ItemVAL.Note();
            purchasingTextNote.setLanguageID(ApplicationProperties.getProprtyValue("LN.default.language.id"));
            purchasingTextNote.setType(NoteTypes.ITEM_PURCHASING_TEXT.getValue());
            StringBuilder purchasingTextBuilder = new StringBuilder();
            String purchasingTextVal = noteValueMap.get("ItemPurchasingText");
            if (purchasingTextVal != null && purchasingTextVal.trim().length() > 0) {
                purchasingTextBuilder.append(purchasingTextVal);
            }
            purchasingTextNote.setValue(getLNSupportedStringsForNoteValues(purchasingTextBuilder.toString()));
            item.getNote().add(purchasingTextNote);
        }
        if (noteValueMap.containsKey("ChangeInformation")) {
            ItemVAL.Note changeInfoNote = new ItemVAL.Note();
            changeInfoNote.setType(NoteTypes.CHANGE_INFORMATION.getValue());
            changeInfoNote.setLanguageID(ApplicationProperties.getProprtyValue("LN.default.language.id"));
            StringBuilder changeInfoBuilder = new StringBuilder();
            String changeInfoVal = noteValueMap.get("ChangeInformation");
            if (changeInfoVal != null && changeInfoVal.trim().length() > 0) {
                changeInfoBuilder.append(changeInfoVal);
            }
            changeInfoNote.setValue(getLNSupportedStringsForNoteValues(changeInfoBuilder.toString()));
            item.getNote().add(changeInfoNote);
        }
        if (noteValueMap.containsKey("ItemManufacturingText")) {
            ItemVAL.Note itemManufacturingNote = new ItemVAL.Note();
            itemManufacturingNote.setType(NoteTypes.ITEM_MANUFACTURING_TEXT.getValue());
            itemManufacturingNote.setLanguageID(ApplicationProperties.getProprtyValue("LN.default.language.id"));
            StringBuilder manufacturingTextBuilder = new StringBuilder();
            String manufacturingTextVal = noteValueMap.get("ItemManufacturingText");
            if (manufacturingTextVal != null && manufacturingTextVal.trim().length() > 0) {
                manufacturingTextBuilder.append(manufacturingTextVal);
            }
            itemManufacturingNote.setValue(getLNSupportedStringsForNoteValues(manufacturingTextBuilder.toString()));
            item.getNote().add(itemManufacturingNote);
        }
        itemTextNote.setValue(getLNSupportedStringsForNoteValues(itemTextBuilder.toString()));
        item.getNote().add(itemTextNote);
    }

    /**
     * Sets Item properties of an item
     *
     * @param item
     * @param propertyValueMap
     * @return
     */
    public ItemVAL setItemsProperties(ItemVAL item, HashMap<String, String> propertyValueMap) {
        ITEM_UTILS_LOGGER.debug("Setting Item Properties ");
        itemBusinessObjectPropertyList.forEach(xmlMapProperty -> {
            if (xmlMapProperty.getDestinationName().equalsIgnoreCase("Discipline")) {
                item.setSignal(propertyValueMap.get(xmlMapProperty.getDestinationName()));
            }
            if (xmlMapProperty.getDestinationName().equalsIgnoreCase("Extensible")) {
                ItemVAL.ItemID itemID = new ItemVAL.ItemID();
                itemID.setID(propertyValueMap.get(xmlMapProperty.getDestinationName()));
                item.setItemID(itemID);
            }
        });

        if (ApplicationProperties.getProprtyValue("destination.object.current").equalsIgnoreCase("signal")) {
            item.setSignal(itemValueMap.get(ApplicationProperties.getProprtyValue("destination.object.current")));
        }
        if (ApplicationProperties.getProprtyValue("destination.object.selection").equalsIgnoreCase("selectionCode")) {
            item.setSelectionCode(itemValueMap.get(ApplicationProperties.getProprtyValue("destination.object.selection")));
        }
        if (ApplicationProperties.getProprtyValue("destination.object.name").equalsIgnoreCase("itemId")) {
            ItemVAL.ItemID itemID = new ItemVAL.ItemID();

            itemID.setID(itemValueMap.get(ApplicationProperties.getProprtyValue("destination.object.name")));
            item.setItemID(itemID);
        }
        item.setRevision(itemValueMap.get(ApplicationProperties.getProprtyValue("destination.object.revision")));
        return item;
    }

    /**
     * Assign properties or attributes of an item from a property file
     *
     * @param item
     * @return
     */
    public ItemVAL setItemsStaticValues(ItemVAL item) {
        item.setItemGroup(ApplicationProperties.getProprtyValue("ln.item.itemGroup"));
        item.setProductType(ApplicationProperties.getProprtyValue("ln.item.productType"));
        return item;
    }

    /**
     * Assign properties or attributes of an item from a property file
     *
     * @param item
     * @param businessObjectWithSelect
     * @return
     */
    public ItemVAL setItemsStaticValues(ItemVAL item, BusinessObjectWithSelect businessObjectWithSelect) {
        item.setItemGroup(ApplicationProperties.getProprtyValue("ln.item.itemGroup"));
        item.setProductType(ApplicationProperties.getProprtyValue("ln.item.productType"));
        return item;
    }

    //Getters
    public List<ItemVAL> getItemValList() {
        return this.itemValList;
    }

    /**
     * @param project
     * @return
     */
    public String getProductClassFromProject(String project) {
        String productClass = project;
        if (project.contains("_")) {
            int len = project.split("_")[1].length();
            if (len > 6) {
                productClass = project.split("_")[1].substring(0, 6);
            } else {
                productClass = project.split("_")[1].substring(0, len);
            }
        } else {
            int len = productClass.length();
            if (len > 6) {
                productClass = productClass.substring(0, 6);
            } else {
                productClass = productClass;
            }
        }
        return productClass;
    }

    /**
     * @param value
     * @return
     */
    public String getLNSupportedStringsMaxLengthValue(String value) {
        int maxLen = Integer.parseInt(ApplicationProperties.getProprtyValue("LN.description.length"));
        if (value.length() > maxLen) {
            return value.substring(0, maxLen);
        }
        return value;
    }

    /**
     *
     * @param value
     * @return
     */
    public String getLNSupportedStringsMaxLengthValueInByte(String value) {
        int maxBytes = Integer.parseInt(ApplicationProperties.getProprtyValue("LN.description.byte.length"));
        StringBuilder valueSubstring = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            if ((maxBytes -= value.substring(i, i + 1).getBytes(StandardCharsets.UTF_8).length) < 0) {
                break;
            }
            valueSubstring.append(value.charAt(i));
        }
        return valueSubstring.toString();
    }

    /**
     * @param value
     * @return
     */
    private String getLNSupportedStringsForNoteValues(String value) {
        int maxLen = Integer.parseInt(ApplicationProperties.getProprtyValue("LN.note.text.max.length"));
        if (value.length() > maxLen) {
            return value.substring(0, maxLen);
        }
        return value;
    }

    /**
     * @param value
     * @return
     */
    public String getLNSupportedStringForLongDescription(String value) {
        int maxLen = Integer.parseInt(ApplicationProperties.getProprtyValue("LN.longdescription.length"));
        if (value.length() > maxLen) {
            return value.substring(0, maxLen - 3).concat("...");
        }
        return value;
    }

    /**
     * @param item
     * @param abbreviation
     * @param technicalDesignation
     */
    public void setLongItemDescription(ItemVAL item, GTSBundleIdResponse abbreviation, String technicalDesignation) {
        if (abbreviation != null) {
            HashMap<String, HashMap<String, String>> abbreviationDataMap = abbreviation.getAbbreviationDataMap();
            if (abbreviationDataMap != null && abbreviationDataMap.size() > 0) {
                for (String languageCode : abbreviationDataMap.keySet()) {
                    ItemVAL.LongItemDescription longDescription = new ItemVAL.LongItemDescription();
                    StringBuilder longDescriptionBuilder = new StringBuilder();
                    String title = abbreviationDataMap.get(languageCode).get("text");

                    if (title != null && title.length() > 0) {
                        longDescriptionBuilder.append(title);
                    }

                    if (technicalDesignation != null && technicalDesignation.trim().length() > 0) {
                        longDescriptionBuilder.append(" ");
                        longDescriptionBuilder.append(technicalDesignation);
                    }

                    longDescription.setLanguageID(languageCode);
                    longDescription.setValue(getLNSupportedStringForLongDescription(longDescriptionBuilder.toString()));
                    item.getLongItemDescription().add(longDescription);
                }
            }
        }
    }

    private void setDrawingAndDocInfo(ItemVAL item, Map<String, String> drawingInforMap, String attributeDrawing) {
        boolean isDrawingNumberIsSet = false;
        if (drawingInforMap != null && drawingInforMap.size() > 0) {
            if (drawingInforMap.containsKey("drawingNumber")) {
                item.setDrawingNumber(drawingInforMap.get("drawingNumber"));
                isDrawingNumberIsSet = true;
            }

            if (drawingInforMap.containsKey("documentID")) {
                item.setDocID(drawingInforMap.get("documentID"));
            }
        }

        if (!isDrawingNumberIsSet) {
            item.setDrawingNumber(attributeDrawing);
        }
    }
}
