
package com.infor.businessinterface.billofmaterial_val;

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
 *     &lt;enumeration value="BillOfMaterial_VAL.ItemID.ID"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.ItemID.mainItemRevision"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.ItemID.updateOneLine"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.component"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.position"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.width"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.length"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.numberOfUnits"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.netQuantity"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.componentRevision"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.extraInformation"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.Note"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.Note.type"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.Note.languageID"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.physicalPart"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.pseudoRow"/>
 *     &lt;enumeration value="BillOfMaterial_VAL.Lines.suppliedBySubcontractor"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "filterAttributeListDT")
@XmlEnum
public enum FilterAttributeListDT {

    @XmlEnumValue("BillOfMaterial_VAL.ItemID.ID")
    BILL_OF_MATERIAL_VAL_ITEM_ID_ID("BillOfMaterial_VAL.ItemID.ID"),
    @XmlEnumValue("BillOfMaterial_VAL.ItemID.mainItemRevision")
    BILL_OF_MATERIAL_VAL_ITEM_ID_MAIN_ITEM_REVISION("BillOfMaterial_VAL.ItemID.mainItemRevision"),
    @XmlEnumValue("BillOfMaterial_VAL.ItemID.updateOneLine")
    BILL_OF_MATERIAL_VAL_ITEM_ID_UPDATE_ONE_LINE("BillOfMaterial_VAL.ItemID.updateOneLine"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.component")
    BILL_OF_MATERIAL_VAL_LINES_COMPONENT("BillOfMaterial_VAL.Lines.component"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.position")
    BILL_OF_MATERIAL_VAL_LINES_POSITION("BillOfMaterial_VAL.Lines.position"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.width")
    BILL_OF_MATERIAL_VAL_LINES_WIDTH("BillOfMaterial_VAL.Lines.width"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.length")
    BILL_OF_MATERIAL_VAL_LINES_LENGTH("BillOfMaterial_VAL.Lines.length"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.numberOfUnits")
    BILL_OF_MATERIAL_VAL_LINES_NUMBER_OF_UNITS("BillOfMaterial_VAL.Lines.numberOfUnits"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.netQuantity")
    BILL_OF_MATERIAL_VAL_LINES_NET_QUANTITY("BillOfMaterial_VAL.Lines.netQuantity"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.componentRevision")
    BILL_OF_MATERIAL_VAL_LINES_COMPONENT_REVISION("BillOfMaterial_VAL.Lines.componentRevision"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.extraInformation")
    BILL_OF_MATERIAL_VAL_LINES_EXTRA_INFORMATION("BillOfMaterial_VAL.Lines.extraInformation"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.Note")
    BILL_OF_MATERIAL_VAL_LINES_NOTE("BillOfMaterial_VAL.Lines.Note"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.Note.type")
    BILL_OF_MATERIAL_VAL_LINES_NOTE_TYPE("BillOfMaterial_VAL.Lines.Note.type"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.Note.languageID")
    BILL_OF_MATERIAL_VAL_LINES_NOTE_LANGUAGE_ID("BillOfMaterial_VAL.Lines.Note.languageID"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.physicalPart")
    BILL_OF_MATERIAL_VAL_LINES_PHYSICAL_PART("BillOfMaterial_VAL.Lines.physicalPart"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.pseudoRow")
    BILL_OF_MATERIAL_VAL_LINES_PSEUDO_ROW("BillOfMaterial_VAL.Lines.pseudoRow"),
    @XmlEnumValue("BillOfMaterial_VAL.Lines.suppliedBySubcontractor")
    BILL_OF_MATERIAL_VAL_LINES_SUPPLIED_BY_SUBCONTRACTOR("BillOfMaterial_VAL.Lines.suppliedBySubcontractor");
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
