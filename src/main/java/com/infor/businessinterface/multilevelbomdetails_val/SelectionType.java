
package com.infor.businessinterface.multilevelbomdetails_val;

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
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.*"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.item"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.level"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.itemRevision"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.drawingNumber"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.inventoryUnit"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.itemGroup"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.itemType"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.itemDescription"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.signalCode"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.selectionCode"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.Note"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.descriptionLanguageID"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.textLanguageID"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.productType"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.*"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.componentItem"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.componentRevision"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.drawingNumber"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.inventoryUnit"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.itemGroup"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.itemType"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.componentItemDescription"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.position"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.length"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.width"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.noOfUnit"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.netQuantity"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.signalCode"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.selectionCode"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.Note"/>
 *               &lt;enumeration value="MultilevelBOMDetails_VAL.BOMDetails.productType"/>
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
