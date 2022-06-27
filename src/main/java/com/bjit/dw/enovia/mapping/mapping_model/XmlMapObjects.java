package com.bjit.dw.enovia.mapping.mapping_model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class XmlMapObjects {
    private List<XmlMapObject> xmlMapObject;

    public List<XmlMapObject> getXmlMapObject() {
        return xmlMapObject;
    }

    @XmlElement(name = "object")
    public void setXmlMapObject(List<XmlMapObject> xmlMapObject) {
        this.xmlMapObject = xmlMapObject;
    }
}
