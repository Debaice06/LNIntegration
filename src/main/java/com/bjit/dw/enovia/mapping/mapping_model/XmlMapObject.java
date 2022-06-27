package com.bjit.dw.enovia.mapping.mapping_model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class XmlMapObject {
    private String type;
    private XmlMapAttributes xmlMapAttributes;

    public String getType() {
        return type;
    }

    @XmlAttribute
    public void setType(String type) {
        this.type = type;
    }

    public XmlMapAttributes getXmlMapAttributes() {
        return xmlMapAttributes;
    }

    @XmlElement(name = "attributes")
    public void setXmlMapAttributes(XmlMapAttributes xmlMapAttributes) {
        this.xmlMapAttributes = xmlMapAttributes;
    }
}
