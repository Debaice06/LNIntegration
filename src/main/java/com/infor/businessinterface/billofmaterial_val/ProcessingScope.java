
package com.infor.businessinterface.billofmaterial_val;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processingScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="processingScope">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="not_applicable"/>
 *     &lt;enumeration value="request"/>
 *     &lt;enumeration value="business_entity"/>
 *     &lt;enumeration value="business_entity_component"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "processingScope")
@XmlEnum
public enum ProcessingScope {

    @XmlEnumValue("not_applicable")
    NOT_APPLICABLE("not_applicable"),
    @XmlEnumValue("request")
    REQUEST("request"),
    @XmlEnumValue("business_entity")
    BUSINESS_ENTITY("business_entity"),
    @XmlEnumValue("business_entity_component")
    BUSINESS_ENTITY_COMPONENT("business_entity_component");
    private final String value;

    ProcessingScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProcessingScope fromValue(String v) {
        for (ProcessingScope c: ProcessingScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
