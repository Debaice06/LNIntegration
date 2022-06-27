
package com.infor.businessinterface.billofmaterial_val;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SelectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SelectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="selectionAttribute" maxOccurs="unbounded">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="*"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.*"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.ItemID.*"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.ItemID.ID"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.ItemID.mainItemRevision"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.ItemID.updateOneLine"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.*"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.component"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.position"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.width"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.length"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.numberOfUnits"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.netQuantity"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.componentRevision"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.extraInformation"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.Note"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.physicalPart"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.pseudoRow"/>
 *               &lt;enumeration value="BillOfMaterial_VAL.Lines.suppliedBySubcontractor"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelectionType", propOrder = {
    "selectionAttribute"
})
public class SelectionType {

    @XmlElement(required = true)
    protected List<String> selectionAttribute;

    /**
     * Gets the value of the selectionAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the selectionAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelectionAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSelectionAttribute() {
        if (selectionAttribute == null) {
            selectionAttribute = new ArrayList<String>();
        }
        return this.selectionAttribute;
    }

}
