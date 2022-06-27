
package com.infor.businessinterface.item_val;

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
 *               &lt;enumeration value="Item_VAL.*"/>
 *               &lt;enumeration value="Item_VAL.ItemID.*"/>
 *               &lt;enumeration value="Item_VAL.ItemID.ID"/>
 *               &lt;enumeration value="Item_VAL.description"/>
 *               &lt;enumeration value="Item_VAL.revision"/>
 *               &lt;enumeration value="Item_VAL.itemGroup"/>
 *               &lt;enumeration value="Item_VAL.itemType"/>
 *               &lt;enumeration value="Item_VAL.inventoryUnit"/>
 *               &lt;enumeration value="Item_VAL.productType"/>
 *               &lt;enumeration value="Item_VAL.productLine.*"/>
 *               &lt;enumeration value="Item_VAL.productLine.ID"/>
 *               &lt;enumeration value="Item_VAL.productLine.lineDescription"/>
 *               &lt;enumeration value="Item_VAL.productClass.*"/>
 *               &lt;enumeration value="Item_VAL.productClass.ID"/>
 *               &lt;enumeration value="Item_VAL.productClass.classDescription"/>
 *               &lt;enumeration value="Item_VAL.purchaseStatisticsGroup"/>
 *               &lt;enumeration value="Item_VAL.sourceItem"/>
 *               &lt;enumeration value="Item_VAL.signal"/>
 *               &lt;enumeration value="Item_VAL.commodityCode"/>
 *               &lt;enumeration value="Item_VAL.commodityCodeUS"/>
 *               &lt;enumeration value="Item_VAL.commodityCodeCN"/>
 *               &lt;enumeration value="Item_VAL.weight"/>
 *               &lt;enumeration value="Item_VAL.weightUnit"/>
 *               &lt;enumeration value="Item_VAL.unitSet"/>
 *               &lt;enumeration value="Item_VAL.drawingNumber"/>
 *               &lt;enumeration value="Item_VAL.material"/>
 *               &lt;enumeration value="Item_VAL.size"/>
 *               &lt;enumeration value="Item_VAL.standard"/>
 *               &lt;enumeration value="Item_VAL.selectionCode"/>
 *               &lt;enumeration value="Item_VAL.technicalCoordinator"/>
 *               &lt;enumeration value="Item_VAL.engineer"/>
 *               &lt;enumeration value="Item_VAL.purchaseUnit"/>
 *               &lt;enumeration value="Item_VAL.purchasePriceUnit"/>
 *               &lt;enumeration value="Item_VAL.salesUnit"/>
 *               &lt;enumeration value="Item_VAL.salesPriceUnit"/>
 *               &lt;enumeration value="Item_VAL.conversionFactor"/>
 *               &lt;enumeration value="Item_VAL.creationDate"/>
 *               &lt;enumeration value="Item_VAL.revisionDescription"/>
 *               &lt;enumeration value="Item_VAL.Note"/>
 *               &lt;enumeration value="Item_VAL.alternativeItem"/>
 *               &lt;enumeration value="Item_VAL.alternativeItemRevision"/>
 *               &lt;enumeration value="Item_VAL.phantom"/>
 *               &lt;enumeration value="Item_VAL.extraLeadTime"/>
 *               &lt;enumeration value="Item_VAL.defaultSupplySource"/>
 *               &lt;enumeration value="Item_VAL.lotControlled"/>
 *               &lt;enumeration value="Item_VAL.serialized"/>
 *               &lt;enumeration value="Item_VAL.extraInformation"/>
 *               &lt;enumeration value="Item_VAL.docID"/>
 *               &lt;enumeration value="Item_VAL.salesStatisticsGroup"/>
 *               &lt;enumeration value="Item_VAL.classOfRisk"/>
 *               &lt;enumeration value="Item_VAL.hazardousMaterial"/>
 *               &lt;enumeration value="Item_VAL.itemSubtype"/>
 *               &lt;enumeration value="Item_VAL.sparePart"/>
 *               &lt;enumeration value="Item_VAL.serviceTracked"/>
 *               &lt;enumeration value="Item_VAL.serviceTrackCategorization"/>
 *               &lt;enumeration value="Item_VAL.longItemDescription"/>
 *               &lt;enumeration value="Item_VAL.searchKey1"/>
 *               &lt;enumeration value="Item_VAL.searchKey2"/>
 *               &lt;enumeration value="Item_VAL.itemTaxonomy"/>
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
