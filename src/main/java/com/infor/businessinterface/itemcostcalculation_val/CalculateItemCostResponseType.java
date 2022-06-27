
package com.infor.businessinterface.itemcostcalculation_val;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for calculateItemCostResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="calculateItemCostResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ItemCostCalculation_VAL" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="item" type="{http://www.infor.com/businessinterface/ItemCostCalculation_VAL}tcitem"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="InformationArea" type="{http://www.infor.com/businessinterface/ItemCostCalculation_VAL}InformationArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calculateItemCostResponseType", propOrder = {
    "dataArea",
    "informationArea"
})
public class CalculateItemCostResponseType {

    @XmlElement(name = "DataArea")
    protected CalculateItemCostResponseType.DataArea dataArea;
    @XmlElement(name = "InformationArea")
    protected InformationArea informationArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link CalculateItemCostResponseType.DataArea }
     *     
     */
    public CalculateItemCostResponseType.DataArea getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link CalculateItemCostResponseType.DataArea }
     *     
     */
    public void setDataArea(CalculateItemCostResponseType.DataArea value) {
        this.dataArea = value;
    }

    /**
     * Gets the value of the informationArea property.
     * 
     * @return
     *     possible object is
     *     {@link InformationArea }
     *     
     */
    public InformationArea getInformationArea() {
        return informationArea;
    }

    /**
     * Sets the value of the informationArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformationArea }
     *     
     */
    public void setInformationArea(InformationArea value) {
        this.informationArea = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ItemCostCalculation_VAL" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="item" type="{http://www.infor.com/businessinterface/ItemCostCalculation_VAL}tcitem"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
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
    @XmlType(name = "", propOrder = {
        "itemCostCalculationVAL"
    })
    public static class DataArea {

        @XmlElement(name = "ItemCostCalculation_VAL")
        protected List<CalculateItemCostResponseType.DataArea.ItemCostCalculationVAL> itemCostCalculationVAL;

        /**
         * Gets the value of the itemCostCalculationVAL property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the itemCostCalculationVAL property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItemCostCalculationVAL().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CalculateItemCostResponseType.DataArea.ItemCostCalculationVAL }
         * 
         * 
         */
        public List<CalculateItemCostResponseType.DataArea.ItemCostCalculationVAL> getItemCostCalculationVAL() {
            if (itemCostCalculationVAL == null) {
                itemCostCalculationVAL = new ArrayList<CalculateItemCostResponseType.DataArea.ItemCostCalculationVAL>();
            }
            return this.itemCostCalculationVAL;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="item" type="{http://www.infor.com/businessinterface/ItemCostCalculation_VAL}tcitem"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "item"
        })
        public static class ItemCostCalculationVAL {

            @XmlElement(required = true)
            protected String item;

            /**
             * Gets the value of the item property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getItem() {
                return item;
            }

            /**
             * Sets the value of the item property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setItem(String value) {
                this.item = value;
            }

        }

    }

}
