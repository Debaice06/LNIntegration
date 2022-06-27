
package com.infor.businessinterface.item_val;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for filterAttributeListDT.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="filterAttributeListDT">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Item_VAL.ItemID.ID"/>
 *     &lt;enumeration value="Item_VAL.description"/>
 *     &lt;enumeration value="Item_VAL.description.languageID"/>
 *     &lt;enumeration value="Item_VAL.revision"/>
 *     &lt;enumeration value="Item_VAL.itemGroup"/>
 *     &lt;enumeration value="Item_VAL.itemType"/>
 *     &lt;enumeration value="Item_VAL.inventoryUnit"/>
 *     &lt;enumeration value="Item_VAL.productType"/>
 *     &lt;enumeration value="Item_VAL.productLine.ID"/>
 *     &lt;enumeration value="Item_VAL.productLine.lineDescription"/>
 *     &lt;enumeration value="Item_VAL.productClass.ID"/>
 *     &lt;enumeration value="Item_VAL.productClass.classDescription"/>
 *     &lt;enumeration value="Item_VAL.purchaseStatisticsGroup"/>
 *     &lt;enumeration value="Item_VAL.sourceItem"/>
 *     &lt;enumeration value="Item_VAL.signal"/>
 *     &lt;enumeration value="Item_VAL.commodityCode"/>
 *     &lt;enumeration value="Item_VAL.commodityCodeUS"/>
 *     &lt;enumeration value="Item_VAL.commodityCodeCN"/>
 *     &lt;enumeration value="Item_VAL.weight"/>
 *     &lt;enumeration value="Item_VAL.weightUnit"/>
 *     &lt;enumeration value="Item_VAL.unitSet"/>
 *     &lt;enumeration value="Item_VAL.drawingNumber"/>
 *     &lt;enumeration value="Item_VAL.material"/>
 *     &lt;enumeration value="Item_VAL.size"/>
 *     &lt;enumeration value="Item_VAL.standard"/>
 *     &lt;enumeration value="Item_VAL.selectionCode"/>
 *     &lt;enumeration value="Item_VAL.technicalCoordinator"/>
 *     &lt;enumeration value="Item_VAL.engineer"/>
 *     &lt;enumeration value="Item_VAL.purchaseUnit"/>
 *     &lt;enumeration value="Item_VAL.purchasePriceUnit"/>
 *     &lt;enumeration value="Item_VAL.salesUnit"/>
 *     &lt;enumeration value="Item_VAL.salesPriceUnit"/>
 *     &lt;enumeration value="Item_VAL.conversionFactor"/>
 *     &lt;enumeration value="Item_VAL.creationDate"/>
 *     &lt;enumeration value="Item_VAL.revisionDescription"/>
 *     &lt;enumeration value="Item_VAL.Note"/>
 *     &lt;enumeration value="Item_VAL.Note.type"/>
 *     &lt;enumeration value="Item_VAL.Note.languageID"/>
 *     &lt;enumeration value="Item_VAL.alternativeItem"/>
 *     &lt;enumeration value="Item_VAL.alternativeItem.action"/>
 *     &lt;enumeration value="Item_VAL.alternativeItemRevision"/>
 *     &lt;enumeration value="Item_VAL.phantom"/>
 *     &lt;enumeration value="Item_VAL.extraLeadTime"/>
 *     &lt;enumeration value="Item_VAL.defaultSupplySource"/>
 *     &lt;enumeration value="Item_VAL.lotControlled"/>
 *     &lt;enumeration value="Item_VAL.serialized"/>
 *     &lt;enumeration value="Item_VAL.extraInformation"/>
 *     &lt;enumeration value="Item_VAL.docID"/>
 *     &lt;enumeration value="Item_VAL.salesStatisticsGroup"/>
 *     &lt;enumeration value="Item_VAL.classOfRisk"/>
 *     &lt;enumeration value="Item_VAL.hazardousMaterial"/>
 *     &lt;enumeration value="Item_VAL.itemSubtype"/>
 *     &lt;enumeration value="Item_VAL.sparePart"/>
 *     &lt;enumeration value="Item_VAL.serviceTracked"/>
 *     &lt;enumeration value="Item_VAL.serviceTrackCategorization"/>
 *     &lt;enumeration value="Item_VAL.longItemDescription"/>
 *     &lt;enumeration value="Item_VAL.longItemDescription.languageID"/>
 *     &lt;enumeration value="Item_VAL.searchKey1"/>
 *     &lt;enumeration value="Item_VAL.searchKey2"/>
 *     &lt;enumeration value="Item_VAL.itemTaxonomy"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "filterAttributeListDT")
@XmlEnum
public enum FilterAttributeListDT {

    @XmlEnumValue("Item_VAL.ItemID.ID")
    ITEM_VAL_ITEM_ID_ID("Item_VAL.ItemID.ID"),
    @XmlEnumValue("Item_VAL.description")
    ITEM_VAL_DESCRIPTION("Item_VAL.description"),
    @XmlEnumValue("Item_VAL.description.languageID")
    ITEM_VAL_DESCRIPTION_LANGUAGE_ID("Item_VAL.description.languageID"),
    @XmlEnumValue("Item_VAL.revision")
    ITEM_VAL_REVISION("Item_VAL.revision"),
    @XmlEnumValue("Item_VAL.itemGroup")
    ITEM_VAL_ITEM_GROUP("Item_VAL.itemGroup"),
    @XmlEnumValue("Item_VAL.itemType")
    ITEM_VAL_ITEM_TYPE("Item_VAL.itemType"),
    @XmlEnumValue("Item_VAL.inventoryUnit")
    ITEM_VAL_INVENTORY_UNIT("Item_VAL.inventoryUnit"),
    @XmlEnumValue("Item_VAL.productType")
    ITEM_VAL_PRODUCT_TYPE("Item_VAL.productType"),
    @XmlEnumValue("Item_VAL.productLine.ID")
    ITEM_VAL_PRODUCT_LINE_ID("Item_VAL.productLine.ID"),
    @XmlEnumValue("Item_VAL.productLine.lineDescription")
    ITEM_VAL_PRODUCT_LINE_LINE_DESCRIPTION("Item_VAL.productLine.lineDescription"),
    @XmlEnumValue("Item_VAL.productClass.ID")
    ITEM_VAL_PRODUCT_CLASS_ID("Item_VAL.productClass.ID"),
    @XmlEnumValue("Item_VAL.productClass.classDescription")
    ITEM_VAL_PRODUCT_CLASS_CLASS_DESCRIPTION("Item_VAL.productClass.classDescription"),
    @XmlEnumValue("Item_VAL.purchaseStatisticsGroup")
    ITEM_VAL_PURCHASE_STATISTICS_GROUP("Item_VAL.purchaseStatisticsGroup"),
    @XmlEnumValue("Item_VAL.sourceItem")
    ITEM_VAL_SOURCE_ITEM("Item_VAL.sourceItem"),
    @XmlEnumValue("Item_VAL.signal")
    ITEM_VAL_SIGNAL("Item_VAL.signal"),
    @XmlEnumValue("Item_VAL.commodityCode")
    ITEM_VAL_COMMODITY_CODE("Item_VAL.commodityCode"),
    @XmlEnumValue("Item_VAL.commodityCodeUS")
    ITEM_VAL_COMMODITY_CODE_US("Item_VAL.commodityCodeUS"),
    @XmlEnumValue("Item_VAL.commodityCodeCN")
    ITEM_VAL_COMMODITY_CODE_CN("Item_VAL.commodityCodeCN"),
    @XmlEnumValue("Item_VAL.weight")
    ITEM_VAL_WEIGHT("Item_VAL.weight"),
    @XmlEnumValue("Item_VAL.weightUnit")
    ITEM_VAL_WEIGHT_UNIT("Item_VAL.weightUnit"),
    @XmlEnumValue("Item_VAL.unitSet")
    ITEM_VAL_UNIT_SET("Item_VAL.unitSet"),
    @XmlEnumValue("Item_VAL.drawingNumber")
    ITEM_VAL_DRAWING_NUMBER("Item_VAL.drawingNumber"),
    @XmlEnumValue("Item_VAL.material")
    ITEM_VAL_MATERIAL("Item_VAL.material"),
    @XmlEnumValue("Item_VAL.size")
    ITEM_VAL_SIZE("Item_VAL.size"),
    @XmlEnumValue("Item_VAL.standard")
    ITEM_VAL_STANDARD("Item_VAL.standard"),
    @XmlEnumValue("Item_VAL.selectionCode")
    ITEM_VAL_SELECTION_CODE("Item_VAL.selectionCode"),
    @XmlEnumValue("Item_VAL.technicalCoordinator")
    ITEM_VAL_TECHNICAL_COORDINATOR("Item_VAL.technicalCoordinator"),
    @XmlEnumValue("Item_VAL.engineer")
    ITEM_VAL_ENGINEER("Item_VAL.engineer"),
    @XmlEnumValue("Item_VAL.purchaseUnit")
    ITEM_VAL_PURCHASE_UNIT("Item_VAL.purchaseUnit"),
    @XmlEnumValue("Item_VAL.purchasePriceUnit")
    ITEM_VAL_PURCHASE_PRICE_UNIT("Item_VAL.purchasePriceUnit"),
    @XmlEnumValue("Item_VAL.salesUnit")
    ITEM_VAL_SALES_UNIT("Item_VAL.salesUnit"),
    @XmlEnumValue("Item_VAL.salesPriceUnit")
    ITEM_VAL_SALES_PRICE_UNIT("Item_VAL.salesPriceUnit"),
    @XmlEnumValue("Item_VAL.conversionFactor")
    ITEM_VAL_CONVERSION_FACTOR("Item_VAL.conversionFactor"),
    @XmlEnumValue("Item_VAL.creationDate")
    ITEM_VAL_CREATION_DATE("Item_VAL.creationDate"),
    @XmlEnumValue("Item_VAL.revisionDescription")
    ITEM_VAL_REVISION_DESCRIPTION("Item_VAL.revisionDescription"),
    @XmlEnumValue("Item_VAL.Note")
    ITEM_VAL_NOTE("Item_VAL.Note"),
    @XmlEnumValue("Item_VAL.Note.type")
    ITEM_VAL_NOTE_TYPE("Item_VAL.Note.type"),
    @XmlEnumValue("Item_VAL.Note.languageID")
    ITEM_VAL_NOTE_LANGUAGE_ID("Item_VAL.Note.languageID"),
    @XmlEnumValue("Item_VAL.alternativeItem")
    ITEM_VAL_ALTERNATIVE_ITEM("Item_VAL.alternativeItem"),
    @XmlEnumValue("Item_VAL.alternativeItem.action")
    ITEM_VAL_ALTERNATIVE_ITEM_ACTION("Item_VAL.alternativeItem.action"),
    @XmlEnumValue("Item_VAL.alternativeItemRevision")
    ITEM_VAL_ALTERNATIVE_ITEM_REVISION("Item_VAL.alternativeItemRevision"),
    @XmlEnumValue("Item_VAL.phantom")
    ITEM_VAL_PHANTOM("Item_VAL.phantom"),
    @XmlEnumValue("Item_VAL.extraLeadTime")
    ITEM_VAL_EXTRA_LEAD_TIME("Item_VAL.extraLeadTime"),
    @XmlEnumValue("Item_VAL.defaultSupplySource")
    ITEM_VAL_DEFAULT_SUPPLY_SOURCE("Item_VAL.defaultSupplySource"),
    @XmlEnumValue("Item_VAL.lotControlled")
    ITEM_VAL_LOT_CONTROLLED("Item_VAL.lotControlled"),
    @XmlEnumValue("Item_VAL.serialized")
    ITEM_VAL_SERIALIZED("Item_VAL.serialized"),
    @XmlEnumValue("Item_VAL.extraInformation")
    ITEM_VAL_EXTRA_INFORMATION("Item_VAL.extraInformation"),
    @XmlEnumValue("Item_VAL.docID")
    ITEM_VAL_DOC_ID("Item_VAL.docID"),
    @XmlEnumValue("Item_VAL.salesStatisticsGroup")
    ITEM_VAL_SALES_STATISTICS_GROUP("Item_VAL.salesStatisticsGroup"),
    @XmlEnumValue("Item_VAL.classOfRisk")
    ITEM_VAL_CLASS_OF_RISK("Item_VAL.classOfRisk"),
    @XmlEnumValue("Item_VAL.hazardousMaterial")
    ITEM_VAL_HAZARDOUS_MATERIAL("Item_VAL.hazardousMaterial"),
    @XmlEnumValue("Item_VAL.itemSubtype")
    ITEM_VAL_ITEM_SUBTYPE("Item_VAL.itemSubtype"),
    @XmlEnumValue("Item_VAL.sparePart")
    ITEM_VAL_SPARE_PART("Item_VAL.sparePart"),
    @XmlEnumValue("Item_VAL.serviceTracked")
    ITEM_VAL_SERVICE_TRACKED("Item_VAL.serviceTracked"),
    @XmlEnumValue("Item_VAL.serviceTrackCategorization")
    ITEM_VAL_SERVICE_TRACK_CATEGORIZATION("Item_VAL.serviceTrackCategorization"),
    @XmlEnumValue("Item_VAL.longItemDescription")
    ITEM_VAL_LONG_ITEM_DESCRIPTION("Item_VAL.longItemDescription"),
    @XmlEnumValue("Item_VAL.longItemDescription.languageID")
    ITEM_VAL_LONG_ITEM_DESCRIPTION_LANGUAGE_ID("Item_VAL.longItemDescription.languageID"),
    @XmlEnumValue("Item_VAL.searchKey1")
    ITEM_VAL_SEARCH_KEY_1("Item_VAL.searchKey1"),
    @XmlEnumValue("Item_VAL.searchKey2")
    ITEM_VAL_SEARCH_KEY_2("Item_VAL.searchKey2"),
    @XmlEnumValue("Item_VAL.itemTaxonomy")
    ITEM_VAL_ITEM_TAXONOMY("Item_VAL.itemTaxonomy");
    private final String value;

    FilterAttributeListDT(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FilterAttributeListDT fromValue(String v) {
        for (FilterAttributeListDT c: FilterAttributeListDT.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
