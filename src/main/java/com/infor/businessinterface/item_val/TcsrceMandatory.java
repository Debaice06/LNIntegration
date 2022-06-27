
package com.infor.businessinterface.item_val;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tcsrce_mandatory.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tcsrce_mandatory">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="not.appl"/>
 *     &lt;enumeration value="shopfloor"/>
 *     &lt;enumeration value="repetitive"/>
 *     &lt;enumeration value="assembly"/>
 *     &lt;enumeration value="purchase"/>
 *     &lt;enumeration value="subcontract"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tcsrce_mandatory")
@XmlEnum
public enum TcsrceMandatory {

    @XmlEnumValue("not.appl")
    NOT_APPL("not.appl"),
    @XmlEnumValue("shopfloor")
    SHOPFLOOR("shopfloor"),
    @XmlEnumValue("repetitive")
    REPETITIVE("repetitive"),
    @XmlEnumValue("assembly")
    ASSEMBLY("assembly"),
    @XmlEnumValue("purchase")
    PURCHASE("purchase"),
    @XmlEnumValue("subcontract")
    SUBCONTRACT("subcontract");
    private final String value;

    TcsrceMandatory(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TcsrceMandatory fromValue(String v) {
        for (TcsrceMandatory c: TcsrceMandatory.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
