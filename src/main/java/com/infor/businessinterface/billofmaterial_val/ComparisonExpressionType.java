
package com.infor.businessinterface.billofmaterial_val;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComparisonExpressionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComparisonExpressionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="comparisonOperator">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="le"/>
 *               &lt;enumeration value="lt"/>
 *               &lt;enumeration value="ge"/>
 *               &lt;enumeration value="gt"/>
 *               &lt;enumeration value="ne"/>
 *               &lt;enumeration value="eq"/>
 *               &lt;enumeration value="like"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="attributeName" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}filterAttributeListDT"/>
 *           &lt;element name="emptyValue" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}EmptyValueType"/>
 *           &lt;element name="instanceValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="attributeName" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}filterAttributeListDT"/>
 *           &lt;element name="emptyValue" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}EmptyValueType"/>
 *           &lt;element name="instanceValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComparisonExpressionType", propOrder = {
    "content"
})
public class ComparisonExpressionType {

    @XmlElementRefs({
        @XmlElementRef(name = "comparisonOperator", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "emptyValue", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "instanceValue", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "attributeName", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> content;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "AttributeName" is used by two different parts of a schema. See: 
     * line 198 of https://eaibt.valmet.com:7843/EAI/Item_VAL/Rx/processItemBOMData?xsd=xsd0
     * line 193 of https://eaibt.valmet.com:7843/EAI/Item_VAL/Rx/processItemBOMData?xsd=xsd0
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link EmptyValueType }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link FilterAttributeListDT }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<?>>();
        }
        return this.content;
    }

}
