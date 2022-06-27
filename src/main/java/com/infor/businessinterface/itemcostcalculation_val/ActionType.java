
package com.infor.businessinterface.itemcostcalculation_val;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for actionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="actionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="create"/>
 *     &lt;enumeration value="createOrChange"/>
 *     &lt;enumeration value="change"/>
 *     &lt;enumeration value="delete"/>
 *     &lt;enumeration value="unchanged"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "actionType")
@XmlEnum
public enum ActionType {

    @XmlEnumValue("create")
    CREATE("create"),
    @XmlEnumValue("createOrChange")
    CREATE_OR_CHANGE("createOrChange"),
    @XmlEnumValue("change")
    CHANGE("change"),
    @XmlEnumValue("delete")
    DELETE("delete"),
    @XmlEnumValue("unchanged")
    UNCHANGED("unchanged");
    private final String value;

    ActionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActionType fromValue(String v) {
        for (ActionType c: ActionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
