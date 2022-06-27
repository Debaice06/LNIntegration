
package com.infor.businessinterface.multilevelbomdetails_val;

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
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.item"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.level"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.itemRevision"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.drawingNumber"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.inventoryUnit"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.itemGroup"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.itemType"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.itemDescription"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.itemDescription.languageID"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.signalCode"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.selectionCode"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.Note"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.Note.type"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.Note.languageID"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.descriptionLanguageID"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.textLanguageID"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.productType"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.componentItem"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.componentRevision"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.drawingNumber"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.inventoryUnit"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.itemGroup"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.itemType"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.componentItemDescription"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.componentItemDescription.languageID"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.position"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.length"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.width"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.noOfUnit"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.netQuantity"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.signalCode"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.selectionCode"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.Note"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.Note.type"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.Note.languageID"/>
 *     &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.productType"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "filterAttributeListDT")
@XmlEnum
public enum FilterAttributeListDT {

    @XmlEnumValue("MultilevelBOMDetails_VAL.item")
    MULTILEVEL_BOM_DETAILS_VAL_ITEM("MultilevelBOMDetails_VAL.item"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.level")
    MULTILEVEL_BOM_DETAILS_VAL_LEVEL("MultilevelBOMDetails_VAL.level"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.itemRevision")
    MULTILEVEL_BOM_DETAILS_VAL_ITEM_REVISION("MultilevelBOMDetails_VAL.itemRevision"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.drawingNumber")
    MULTILEVEL_BOM_DETAILS_VAL_DRAWING_NUMBER("MultilevelBOMDetails_VAL.drawingNumber"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.inventoryUnit")
    MULTILEVEL_BOM_DETAILS_VAL_INVENTORY_UNIT("MultilevelBOMDetails_VAL.inventoryUnit"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.itemGroup")
    MULTILEVEL_BOM_DETAILS_VAL_ITEM_GROUP("MultilevelBOMDetails_VAL.itemGroup"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.itemType")
    MULTILEVEL_BOM_DETAILS_VAL_ITEM_TYPE("MultilevelBOMDetails_VAL.itemType"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.itemDescription")
    MULTILEVEL_BOM_DETAILS_VAL_ITEM_DESCRIPTION("MultilevelBOMDetails_VAL.itemDescription"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.itemDescription.languageID")
    MULTILEVEL_BOM_DETAILS_VAL_ITEM_DESCRIPTION_LANGUAGE_ID("MultilevelBOMDetails_VAL.itemDescription.languageID"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.signalCode")
    MULTILEVEL_BOM_DETAILS_VAL_SIGNAL_CODE("MultilevelBOMDetails_VAL.signalCode"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.selectionCode")
    MULTILEVEL_BOM_DETAILS_VAL_SELECTION_CODE("MultilevelBOMDetails_VAL.selectionCode"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.Note")
    MULTILEVEL_BOM_DETAILS_VAL_NOTE("MultilevelBOMDetails_VAL.Note"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.Note.type")
    MULTILEVEL_BOM_DETAILS_VAL_NOTE_TYPE("MultilevelBOMDetails_VAL.Note.type"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.Note.languageID")
    MULTILEVEL_BOM_DETAILS_VAL_NOTE_LANGUAGE_ID("MultilevelBOMDetails_VAL.Note.languageID"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.descriptionLanguageID")
    MULTILEVEL_BOM_DETAILS_VAL_DESCRIPTION_LANGUAGE_ID("MultilevelBOMDetails_VAL.descriptionLanguageID"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.textLanguageID")
    MULTILEVEL_BOM_DETAILS_VAL_TEXT_LANGUAGE_ID("MultilevelBOMDetails_VAL.textLanguageID"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.productType")
    MULTILEVEL_BOM_DETAILS_VAL_PRODUCT_TYPE("MultilevelBOMDetails_VAL.productType"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.componentItem")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_COMPONENT_ITEM("MultilevelBOMDetails_VAL.BOMDetails.componentItem"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.componentRevision")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_COMPONENT_REVISION("MultilevelBOMDetails_VAL.BOMDetails.componentRevision"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.drawingNumber")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_DRAWING_NUMBER("MultilevelBOMDetails_VAL.BOMDetails.drawingNumber"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.inventoryUnit")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_INVENTORY_UNIT("MultilevelBOMDetails_VAL.BOMDetails.inventoryUnit"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.itemGroup")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_ITEM_GROUP("MultilevelBOMDetails_VAL.BOMDetails.itemGroup"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.itemType")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_ITEM_TYPE("MultilevelBOMDetails_VAL.BOMDetails.itemType"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.componentItemDescription")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_COMPONENT_ITEM_DESCRIPTION("MultilevelBOMDetails_VAL.BOMDetails.componentItemDescription"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.componentItemDescription.languageID")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_COMPONENT_ITEM_DESCRIPTION_LANGUAGE_ID("MultilevelBOMDetails_VAL.BOMDetails.componentItemDescription.languageID"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.position")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_POSITION("MultilevelBOMDetails_VAL.BOMDetails.position"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.length")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_LENGTH("MultilevelBOMDetails_VAL.BOMDetails.length"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.width")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_WIDTH("MultilevelBOMDetails_VAL.BOMDetails.width"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.noOfUnit")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_NO_OF_UNIT("MultilevelBOMDetails_VAL.BOMDetails.noOfUnit"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.netQuantity")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_NET_QUANTITY("MultilevelBOMDetails_VAL.BOMDetails.netQuantity"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.signalCode")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_SIGNAL_CODE("MultilevelBOMDetails_VAL.BOMDetails.signalCode"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.selectionCode")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_SELECTION_CODE("MultilevelBOMDetails_VAL.BOMDetails.selectionCode"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.Note")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_NOTE("MultilevelBOMDetails_VAL.BOMDetails.Note"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.Note.type")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_NOTE_TYPE("MultilevelBOMDetails_VAL.BOMDetails.Note.type"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.Note.languageID")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_NOTE_LANGUAGE_ID("MultilevelBOMDetails_VAL.BOMDetails.Note.languageID"),
    @XmlEnumValue("MultilevelBOMDetails_VAL.BOMDetails.productType")
    MULTILEVEL_BOM_DETAILS_VAL_BOM_DETAILS_PRODUCT_TYPE("MultilevelBOMDetails_VAL.BOMDetails.productType");
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
