package com.bjit.dw.enovia.mapping.mapping_model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"sourceName", "destinationName", "selectable", "isProperty"})
public class XmlMapAttribute {
    private String sourceName;
    private String destinationName;
    private String selectable;
    private String isProperty;

    public String getIsProperty() {
        return isProperty;
    }

    @XmlAttribute
    public void setIsProperty(String isProperty) {
        this.isProperty = isProperty;
    }

    public String getSourceName() {
        return sourceName;
    }

    @XmlElement(name= "source-name")
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    @XmlElement(name= "destination-name")
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getSelectable() {
        return selectable;
    }

    @XmlElement(name= "selectable")
    public void setSelectable(String selectable) {
        this.selectable = selectable;
    }
}
