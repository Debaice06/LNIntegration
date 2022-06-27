
package com.infor.businessinterface.multilevelbomdetails_val;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetBOMDetailsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetBOMDetailsRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ControlArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}processingScope" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DataArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MessageID" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="Receiver" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="MultilevelBOMDetails_VAL" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="item" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem"/>
 *                             &lt;element name="level" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str4" minOccurs="0"/>
 *                             &lt;element name="descriptionLanguageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" minOccurs="0"/>
 *                             &lt;element name="textLanguageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" minOccurs="0"/>
 *                             &lt;element name="productType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcctyp" minOccurs="0"/>
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
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetBOMDetailsRequestType", propOrder = {
    "controlArea",
    "dataArea"
})
public class GetBOMDetailsRequestType {

    @XmlElement(name = "ControlArea")
    protected GetBOMDetailsRequestType.ControlArea controlArea;
    @XmlElement(name = "DataArea")
    protected GetBOMDetailsRequestType.DataArea dataArea;

    /**
     * Gets the value of the controlArea property.
     * 
     * @return
     *     possible object is
     *     {@link GetBOMDetailsRequestType.ControlArea }
     *     
     */
    public GetBOMDetailsRequestType.ControlArea getControlArea() {
        return controlArea;
    }

    /**
     * Sets the value of the controlArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetBOMDetailsRequestType.ControlArea }
     *     
     */
    public void setControlArea(GetBOMDetailsRequestType.ControlArea value) {
        this.controlArea = value;
    }

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link GetBOMDetailsRequestType.DataArea }
     *     
     */
    public GetBOMDetailsRequestType.DataArea getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetBOMDetailsRequestType.DataArea }
     *     
     */
    public void setDataArea(GetBOMDetailsRequestType.DataArea value) {
        this.dataArea = value;
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
     *         &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}processingScope" minOccurs="0"/>
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
        "processingScope"
    })
    public static class ControlArea {

        @XmlElement(defaultValue = "request")
        protected ProcessingScope processingScope;

        /**
         * Gets the value of the processingScope property.
         * 
         * @return
         *     possible object is
         *     {@link ProcessingScope }
         *     
         */
        public ProcessingScope getProcessingScope() {
            return processingScope;
        }

        /**
         * Sets the value of the processingScope property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProcessingScope }
         *     
         */
        public void setProcessingScope(ProcessingScope value) {
            this.processingScope = value;
        }

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
     *         &lt;element name="MessageID" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="Receiver" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="MultilevelBOMDetails_VAL" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="item" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem"/>
     *                   &lt;element name="level" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str4" minOccurs="0"/>
     *                   &lt;element name="descriptionLanguageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" minOccurs="0"/>
     *                   &lt;element name="textLanguageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" minOccurs="0"/>
     *                   &lt;element name="productType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcctyp" minOccurs="0"/>
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
        "messageID",
        "receiver",
        "multilevelBOMDetailsVAL"
    })
    public static class DataArea {

        @XmlElement(name = "MessageID", required = true)
        protected Object messageID;
        @XmlElement(name = "Receiver", required = true)
        protected Object receiver;
        @XmlElement(name = "MultilevelBOMDetails_VAL")
        protected List<GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL> multilevelBOMDetailsVAL;

        /**
         * Gets the value of the messageID property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getMessageID() {
            return messageID;
        }

        /**
         * Sets the value of the messageID property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setMessageID(Object value) {
            this.messageID = value;
        }

        /**
         * Gets the value of the receiver property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getReceiver() {
            return receiver;
        }

        /**
         * Sets the value of the receiver property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setReceiver(Object value) {
            this.receiver = value;
        }

        /**
         * Gets the value of the multilevelBOMDetailsVAL property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the multilevelBOMDetailsVAL property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMultilevelBOMDetailsVAL().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL }
         * 
         * 
         */
        public List<GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL> getMultilevelBOMDetailsVAL() {
            if (multilevelBOMDetailsVAL == null) {
                multilevelBOMDetailsVAL = new ArrayList<GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL>();
            }
            return this.multilevelBOMDetailsVAL;
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
         *         &lt;element name="item" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem"/>
         *         &lt;element name="level" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str4" minOccurs="0"/>
         *         &lt;element name="descriptionLanguageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" minOccurs="0"/>
         *         &lt;element name="textLanguageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" minOccurs="0"/>
         *         &lt;element name="productType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcctyp" minOccurs="0"/>
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
            "item",
            "level",
            "descriptionLanguageID",
            "textLanguageID",
            "productType"
        })
        public static class MultilevelBOMDetailsVAL {

            @XmlElement(required = true)
            protected String item;
            protected String level;
            protected String descriptionLanguageID;
            protected String textLanguageID;
            protected String productType;

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

            /**
             * Gets the value of the level property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLevel() {
                return level;
            }

            /**
             * Sets the value of the level property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLevel(String value) {
                this.level = value;
            }

            /**
             * Gets the value of the descriptionLanguageID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescriptionLanguageID() {
                return descriptionLanguageID;
            }

            /**
             * Sets the value of the descriptionLanguageID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescriptionLanguageID(String value) {
                this.descriptionLanguageID = value;
            }

            /**
             * Gets the value of the textLanguageID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTextLanguageID() {
                return textLanguageID;
            }

            /**
             * Sets the value of the textLanguageID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTextLanguageID(String value) {
                this.textLanguageID = value;
            }

            /**
             * Gets the value of the productType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProductType() {
                return productType;
            }

            /**
             * Sets the value of the productType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProductType(String value) {
                this.productType = value;
            }

        }

    }

}
