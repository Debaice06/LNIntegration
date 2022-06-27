package com.bjit.dw.enovia.mapping.mapping_model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class XmlMapAttributes {
    private List<XmlMapAttribute> xmlMapAttribute;

    public List<XmlMapAttribute> getXmlMapAttribute() {
        return xmlMapAttribute;
    }

    @XmlElement(name = "attribute")
    public void setXmlMapAttribute(List<XmlMapAttribute> xmlMapAttribute) {
        this.xmlMapAttribute = xmlMapAttribute;
    }
}
