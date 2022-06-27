package com.bjit.ex.integration.mapproject.xml_mapping_model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class XmlMapElementObjects {

    private List<XmlMapElementObject> xmlMapElementObject;

    public List<XmlMapElementObject> getXmlMapElementObject() {
        return xmlMapElementObject;
    }

    //@XmlElementWrapper(name="objects")
    @XmlElement(name = "object")
    public void setXmlMapElementObject(List<XmlMapElementObject> xmlMapElementObject) {
        this.xmlMapElementObject = xmlMapElementObject;
    }
}
