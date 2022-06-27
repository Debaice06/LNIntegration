
package com.infor.businessinterface.disassemblylines_val;

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
 *               &lt;enumeration value="DisAssemblyLines_VAL.*"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.*"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.project"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.activity"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.contract"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.contractLine"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.deliverableNumber"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.deliverableItem"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.contractDetails.orderQty"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.*"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.disAssemblyItem"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.sortingPosition"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.disAssembleQuantity"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.Lines.*"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.Lines.position"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.Lines.subItem"/>
 *               &lt;enumeration value="DisAssemblyLines_VAL.ItemDetails.Lines.quantity"/>
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
