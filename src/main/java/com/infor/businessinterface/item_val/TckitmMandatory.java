
package com.infor.businessinterface.item_val;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tckitm_mandatory.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tckitm_mandatory">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="purchase"/>
 *     &lt;enumeration value="manufacture"/>
 *     &lt;enumeration value="generic"/>
 *     &lt;enumeration value="cost"/>
 *     &lt;enumeration value="service"/>
 *     &lt;enumeration value="subcontracting"/>
 *     &lt;enumeration value="list"/>
 *     &lt;enumeration value="tool"/>
 *     &lt;enumeration value="equipment"/>
 *     &lt;enumeration value="engineering"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tckitm_mandatory")
@XmlEnum
public enum TckitmMandatory {

    @XmlEnumValue("purchase")
    PURCHASE("purchase"),
    @XmlEnumValue("manufacture")
    MANUFACTURE("manufacture"),
    @XmlEnumValue("generic")
    GENERIC("generic"),
    @XmlEnumValue("cost")
    COST("cost"),
    @XmlEnumValue("service")
    SERVICE("service"),
    @XmlEnumValue("subcontracting")
    SUBCONTRACTING("subcontracting"),
    @XmlEnumValue("list")
    LIST("list"),
    @XmlEnumValue("tool")
    TOOL("tool"),
    @XmlEnumValue("equipment")
    EQUIPMENT("equipment"),
    @XmlEnumValue("engineering")
    ENGINEERING("engineering");
    private final String value;

    TckitmMandatory(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TckitmMandatory fromValue(String v) {
        for (TckitmMandatory c: TckitmMandatory.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
