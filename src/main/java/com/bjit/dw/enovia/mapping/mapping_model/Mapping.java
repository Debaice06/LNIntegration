package com.bjit.dw.enovia.mapping.mapping_model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mapping {

    private XmlMapObjects xmlMapObjects;

    @XmlElement(name = "objects")
    public XmlMapObjects getXmlMapObjects() {
        return xmlMapObjects;
    }

    public void setXmlMapObjects(XmlMapObjects xmlMapObjects) {
        this.xmlMapObjects = xmlMapObjects;
    }
}
