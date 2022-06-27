
package com.infor.businessinterface.disassemblylines_val;

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
 *     &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.project"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.activity"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.contract"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.contractLine"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.deliverableNumber"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.deliverableItem"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.orderQty"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.disAssemblyItem"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.sortingPosition"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.disAssembleQuantity"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.Lines.position"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.Lines.subItem"/>
 *     &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.Lines.quantity"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "filterAttributeListDT")
@XmlEnum
public enum FilterAttributeListDT {

    @XmlEnumValue("DisAssemblyLines_VAL.contractDetails.project")
    DIS_ASSEMBLY_LINES_VAL_CONTRACT_DETAILS_PROJECT("DisAssemblyLines_VAL.contractDetails.project"),
    @XmlEnumValue("DisAssemblyLines_VAL.contractDetails.activity")
    DIS_ASSEMBLY_LINES_VAL_CONTRACT_DETAILS_ACTIVITY("DisAssemblyLines_VAL.contractDetails.activity"),
    @XmlEnumValue("DisAssemblyLines_VAL.contractDetails.contract")
    DIS_ASSEMBLY_LINES_VAL_CONTRACT_DETAILS_CONTRACT("DisAssemblyLines_VAL.contractDetails.contract"),
    @XmlEnumValue("DisAssemblyLines_VAL.contractDetails.contractLine")
    DIS_ASSEMBLY_LINES_VAL_CONTRACT_DETAILS_CONTRACT_LINE("DisAssemblyLines_VAL.contractDetails.contractLine"),
    @XmlEnumValue("DisAssemblyLines_VAL.contractDetails.deliverableNumber")
    DIS_ASSEMBLY_LINES_VAL_CONTRACT_DETAILS_DELIVERABLE_NUMBER("DisAssemblyLines_VAL.contractDetails.deliverableNumber"),
    @XmlEnumValue("DisAssemblyLines_VAL.contractDetails.deliverableItem")
    DIS_ASSEMBLY_LINES_VAL_CONTRACT_DETAILS_DELIVERABLE_ITEM("DisAssemblyLines_VAL.contractDetails.deliverableItem"),
    @XmlEnumValue("DisAssemblyLines_VAL.contractDetails.orderQty")
    DIS_ASSEMBLY_LINES_VAL_CONTRACT_DETAILS_ORDER_QTY("DisAssemblyLines_VAL.contractDetails.orderQty"),
    @XmlEnumValue("DisAssemblyLines_VAL.ItemDetails.disAssemblyItem")
    DIS_ASSEMBLY_LINES_VAL_ITEM_DETAILS_DIS_ASSEMBLY_ITEM("DisAssemblyLines_VAL.ItemDetails.disAssemblyItem"),
    @XmlEnumValue("DisAssemblyLines_VAL.ItemDetails.sortingPosition")
    DIS_ASSEMBLY_LINES_VAL_ITEM_DETAILS_SORTING_POSITION("DisAssemblyLines_VAL.ItemDetails.sortingPosition"),
    @XmlEnumValue("DisAssemblyLines_VAL.ItemDetails.disAssembleQuantity")
    DIS_ASSEMBLY_LINES_VAL_ITEM_DETAILS_DIS_ASSEMBLE_QUANTITY("DisAssemblyLines_VAL.ItemDetails.disAssembleQuantity"),
    @XmlEnumValue("DisAssemblyLines_VAL.ItemDetails.Lines.position")
    DIS_ASSEMBLY_LINES_VAL_ITEM_DETAILS_LINES_POSITION("DisAssemblyLines_VAL.ItemDetails.Lines.position"),
    @XmlEnumValue("DisAssemblyLines_VAL.ItemDetails.Lines.subItem")
    DIS_ASSEMBLY_LINES_VAL_ITEM_DETAILS_LINES_SUB_ITEM("DisAssemblyLines_VAL.ItemDetails.Lines.subItem"),
    @XmlEnumValue("DisAssemblyLines_VAL.ItemDetails.Lines.quantity")
    DIS_ASSEMBLY_LINES_VAL_ITEM_DETAILS_LINES_QUANTITY("DisAssemblyLines_VAL.ItemDetails.Lines.quantity");
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
