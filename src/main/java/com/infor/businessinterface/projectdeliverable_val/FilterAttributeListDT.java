
package com.infor.businessinterface.projectdeliverable_val;

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
 *     &lt;enumeration value="ProjectDeliverable_VAL.itemID.ID"/>
 *     &lt;enumeration value="ProjectDeliverable_VAL.revision"/>
 *     &lt;enumeration value="ProjectDeliverable_VAL.contractProject"/>
 *     &lt;enumeration value="ProjectDeliverable_VAL.activity"/>
 *     &lt;enumeration value="ProjectDeliverable_VAL.quantity"/>
 *     &lt;enumeration value="ProjectDeliverable_VAL.milestone"/>
 *     &lt;enumeration value="ProjectDeliverable_VAL.userID"/>
 *     &lt;enumeration value="ProjectDeliverable_VAL.contractDeliverable"/>
 *     &lt;enumeration value="ProjectDeliverable_VAL.deleteContractDeliverable"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "filterAttributeListDT")
@XmlEnum
public enum FilterAttributeListDT {

    @XmlEnumValue("ProjectDeliverable_VAL.itemID.ID")
    PROJECT_DELIVERABLE_VAL_ITEM_ID_ID("ProjectDeliverable_VAL.itemID.ID"),
    @XmlEnumValue("ProjectDeliverable_VAL.revision")
    PROJECT_DELIVERABLE_VAL_REVISION("ProjectDeliverable_VAL.revision"),
    @XmlEnumValue("ProjectDeliverable_VAL.contractProject")
    PROJECT_DELIVERABLE_VAL_CONTRACT_PROJECT("ProjectDeliverable_VAL.contractProject"),
    @XmlEnumValue("ProjectDeliverable_VAL.activity")
    PROJECT_DELIVERABLE_VAL_ACTIVITY("ProjectDeliverable_VAL.activity"),
    @XmlEnumValue("ProjectDeliverable_VAL.quantity")
    PROJECT_DELIVERABLE_VAL_QUANTITY("ProjectDeliverable_VAL.quantity"),
    @XmlEnumValue("ProjectDeliverable_VAL.milestone")
    PROJECT_DELIVERABLE_VAL_MILESTONE("ProjectDeliverable_VAL.milestone"),
    @XmlEnumValue("ProjectDeliverable_VAL.userID")
    PROJECT_DELIVERABLE_VAL_USER_ID("ProjectDeliverable_VAL.userID"),
    @XmlEnumValue("ProjectDeliverable_VAL.contractDeliverable")
    PROJECT_DELIVERABLE_VAL_CONTRACT_DELIVERABLE("ProjectDeliverable_VAL.contractDeliverable"),
    @XmlEnumValue("ProjectDeliverable_VAL.deleteContractDeliverable")
    PROJECT_DELIVERABLE_VAL_DELETE_CONTRACT_DELIVERABLE("ProjectDeliverable_VAL.deleteContractDeliverable");
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
