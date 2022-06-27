
package com.infor.businessinterface.billofmaterial_val;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for processItemBOMDataRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processItemBOMDataRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ControlArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}processingScope" minOccurs="0"/>
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
 *                   &lt;element name="BillOfMaterial_VAL" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ItemID" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ID" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcitem"/>
 *                                       &lt;element name="mainItemRevision" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiedm.revi" minOccurs="0"/>
 *                                       &lt;element name="updateOneLine" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Lines" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="component" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcitem" minOccurs="0"/>
 *                                       &lt;element name="position" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcpono" minOccurs="0"/>
 *                                       &lt;element name="width" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcwidt" minOccurs="0"/>
 *                                       &lt;element name="length" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcleng" minOccurs="0"/>
 *                                       &lt;element name="numberOfUnits" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tinoun" minOccurs="0"/>
 *                                       &lt;element name="netQuantity" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiqbm2" minOccurs="0"/>
 *                                       &lt;element name="componentRevision" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiedm.revi" minOccurs="0"/>
 *                                       &lt;element name="extraInformation" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcexin" minOccurs="0"/>
 *                                       &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="type" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str100" />
 *                                               &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="physicalPart" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
 *                                       &lt;element name="pseudoRow" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
 *                                       &lt;element name="suppliedBySubcontractor" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
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
@XmlType(name = "processItemBOMDataRequestType", propOrder = {
    "controlArea",
    "dataArea"
})
public class ProcessItemBOMDataRequestType {

    @XmlElement(name = "ControlArea")
    protected ProcessItemBOMDataRequestType.ControlArea controlArea;
    @XmlElement(name = "DataArea")
    protected ProcessItemBOMDataRequestType.DataArea dataArea;

    /**
     * Gets the value of the controlArea property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessItemBOMDataRequestType.ControlArea }
     *     
     */
    public ProcessItemBOMDataRequestType.ControlArea getControlArea() {
        return controlArea;
    }

    /**
     * Sets the value of the controlArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessItemBOMDataRequestType.ControlArea }
     *     
     */
    public void setControlArea(ProcessItemBOMDataRequestType.ControlArea value) {
        this.controlArea = value;
    }

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessItemBOMDataRequestType.DataArea }
     *     
     */
    public ProcessItemBOMDataRequestType.DataArea getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessItemBOMDataRequestType.DataArea }
     *     
     */
    public void setDataArea(ProcessItemBOMDataRequestType.DataArea value) {
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
     *         &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}processingScope" minOccurs="0"/>
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
     *         &lt;element name="BillOfMaterial_VAL" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ItemID" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ID" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcitem"/>
     *                             &lt;element name="mainItemRevision" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiedm.revi" minOccurs="0"/>
     *                             &lt;element name="updateOneLine" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Lines" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="component" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcitem" minOccurs="0"/>
     *                             &lt;element name="position" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcpono" minOccurs="0"/>
     *                             &lt;element name="width" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcwidt" minOccurs="0"/>
     *                             &lt;element name="length" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcleng" minOccurs="0"/>
     *                             &lt;element name="numberOfUnits" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tinoun" minOccurs="0"/>
     *                             &lt;element name="netQuantity" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiqbm2" minOccurs="0"/>
     *                             &lt;element name="componentRevision" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiedm.revi" minOccurs="0"/>
     *                             &lt;element name="extraInformation" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcexin" minOccurs="0"/>
     *                             &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="type" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str100" />
     *                                     &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="physicalPart" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
     *                             &lt;element name="pseudoRow" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
     *                             &lt;element name="suppliedBySubcontractor" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
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
    @XmlType(name = "", propOrder = {
        "messageID",
        "receiver",
        "billOfMaterialVAL"
    })
    public static class DataArea {

        @XmlElement(name = "MessageID", required = true)
        protected Object messageID;
        @XmlElement(name = "Receiver", required = true)
        protected Object receiver;
        @XmlElement(name = "BillOfMaterial_VAL")
        protected List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL> billOfMaterialVAL;

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
         * Gets the value of the billOfMaterialVAL property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the billOfMaterialVAL property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBillOfMaterialVAL().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL }
         * 
         * 
         */
        public List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL> getBillOfMaterialVAL() {
            if (billOfMaterialVAL == null) {
                billOfMaterialVAL = new ArrayList<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL>();
            }
            return this.billOfMaterialVAL;
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
         *         &lt;element name="ItemID" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ID" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcitem"/>
         *                   &lt;element name="mainItemRevision" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiedm.revi" minOccurs="0"/>
         *                   &lt;element name="updateOneLine" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Lines" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="component" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcitem" minOccurs="0"/>
         *                   &lt;element name="position" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcpono" minOccurs="0"/>
         *                   &lt;element name="width" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcwidt" minOccurs="0"/>
         *                   &lt;element name="length" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcleng" minOccurs="0"/>
         *                   &lt;element name="numberOfUnits" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tinoun" minOccurs="0"/>
         *                   &lt;element name="netQuantity" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiqbm2" minOccurs="0"/>
         *                   &lt;element name="componentRevision" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiedm.revi" minOccurs="0"/>
         *                   &lt;element name="extraInformation" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcexin" minOccurs="0"/>
         *                   &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="type" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str100" />
         *                           &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="physicalPart" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
         *                   &lt;element name="pseudoRow" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
         *                   &lt;element name="suppliedBySubcontractor" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
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
            "itemID",
            "lines"
        })
        public static class BillOfMaterialVAL {

            @XmlElement(name = "ItemID")
            protected ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.ItemID itemID;
            @XmlElement(name = "Lines")
            protected List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines> lines;

            /**
             * Gets the value of the itemID property.
             * 
             * @return
             *     possible object is
             *     {@link ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.ItemID }
             *     
             */
            public ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.ItemID getItemID() {
                return itemID;
            }

            /**
             * Sets the value of the itemID property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.ItemID }
             *     
             */
            public void setItemID(ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.ItemID value) {
                this.itemID = value;
            }

            /**
             * Gets the value of the lines property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the lines property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLines().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines }
             * 
             * 
             */
            public List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines> getLines() {
                if (lines == null) {
                    lines = new ArrayList<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines>();
                }
                return this.lines;
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
             *         &lt;element name="ID" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcitem"/>
             *         &lt;element name="mainItemRevision" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiedm.revi" minOccurs="0"/>
             *         &lt;element name="updateOneLine" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
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
                "id",
                "mainItemRevision",
                "updateOneLine"
            })
            public static class ItemID {

                @XmlElement(name = "ID", required = true)
                protected String id;
                protected String mainItemRevision;
                protected String updateOneLine;

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getID() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setID(String value) {
                    this.id = value;
                }

                /**
                 * Gets the value of the mainItemRevision property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getMainItemRevision() {
                    return mainItemRevision;
                }

                /**
                 * Sets the value of the mainItemRevision property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setMainItemRevision(String value) {
                    this.mainItemRevision = value;
                }

                /**
                 * Gets the value of the updateOneLine property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUpdateOneLine() {
                    return updateOneLine;
                }

                /**
                 * Sets the value of the updateOneLine property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUpdateOneLine(String value) {
                    this.updateOneLine = value;
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
             *         &lt;element name="component" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcitem" minOccurs="0"/>
             *         &lt;element name="position" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcpono" minOccurs="0"/>
             *         &lt;element name="width" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcwidt" minOccurs="0"/>
             *         &lt;element name="length" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcleng" minOccurs="0"/>
             *         &lt;element name="numberOfUnits" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tinoun" minOccurs="0"/>
             *         &lt;element name="netQuantity" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiqbm2" minOccurs="0"/>
             *         &lt;element name="componentRevision" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tiedm.revi" minOccurs="0"/>
             *         &lt;element name="extraInformation" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcexin" minOccurs="0"/>
             *         &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="type" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str100" />
             *                 &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" />
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="physicalPart" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
             *         &lt;element name="pseudoRow" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
             *         &lt;element name="suppliedBySubcontractor" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" minOccurs="0"/>
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
                "component",
                "position",
                "width",
                "length",
                "numberOfUnits",
                "netQuantity",
                "componentRevision",
                "extraInformation",
                "note",
                "physicalPart",
                "pseudoRow",
                "suppliedBySubcontractor"
            })
            public static class Lines {

                protected String component;
                protected BigInteger position;
                protected BigDecimal width;
                protected BigDecimal length;
                protected Long numberOfUnits;
                protected BigDecimal netQuantity;
                protected String componentRevision;
                protected String extraInformation;
                @XmlElement(name = "Note")
                protected List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines.Note> note;
                protected String physicalPart;
                protected String pseudoRow;
                protected String suppliedBySubcontractor;

                /**
                 * Gets the value of the component property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getComponent() {
                    return component;
                }

                /**
                 * Sets the value of the component property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setComponent(String value) {
                    this.component = value;
                }

                /**
                 * Gets the value of the position property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getPosition() {
                    return position;
                }

                /**
                 * Sets the value of the position property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setPosition(BigInteger value) {
                    this.position = value;
                }

                /**
                 * Gets the value of the width property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getWidth() {
                    return width;
                }

                /**
                 * Sets the value of the width property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setWidth(BigDecimal value) {
                    this.width = value;
                }

                /**
                 * Gets the value of the length property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getLength() {
                    return length;
                }

                /**
                 * Sets the value of the length property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setLength(BigDecimal value) {
                    this.length = value;
                }

                /**
                 * Gets the value of the numberOfUnits property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getNumberOfUnits() {
                    return numberOfUnits;
                }

                /**
                 * Sets the value of the numberOfUnits property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setNumberOfUnits(Long value) {
                    this.numberOfUnits = value;
                }

                /**
                 * Gets the value of the netQuantity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getNetQuantity() {
                    return netQuantity;
                }

                /**
                 * Sets the value of the netQuantity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setNetQuantity(BigDecimal value) {
                    this.netQuantity = value;
                }

                /**
                 * Gets the value of the componentRevision property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getComponentRevision() {
                    return componentRevision;
                }

                /**
                 * Sets the value of the componentRevision property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setComponentRevision(String value) {
                    this.componentRevision = value;
                }

                /**
                 * Gets the value of the extraInformation property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExtraInformation() {
                    return extraInformation;
                }

                /**
                 * Sets the value of the extraInformation property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExtraInformation(String value) {
                    this.extraInformation = value;
                }

                /**
                 * Gets the value of the note property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the note property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getNote().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines.Note }
                 * 
                 * 
                 */
                public List<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines.Note> getNote() {
                    if (note == null) {
                        note = new ArrayList<ProcessItemBOMDataRequestType.DataArea.BillOfMaterialVAL.Lines.Note>();
                    }
                    return this.note;
                }

                /**
                 * Gets the value of the physicalPart property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPhysicalPart() {
                    return physicalPart;
                }

                /**
                 * Sets the value of the physicalPart property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPhysicalPart(String value) {
                    this.physicalPart = value;
                }

                /**
                 * Gets the value of the pseudoRow property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPseudoRow() {
                    return pseudoRow;
                }

                /**
                 * Sets the value of the pseudoRow property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPseudoRow(String value) {
                    this.pseudoRow = value;
                }

                /**
                 * Gets the value of the suppliedBySubcontractor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSuppliedBySubcontractor() {
                    return suppliedBySubcontractor;
                }

                /**
                 * Sets the value of the suppliedBySubcontractor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSuppliedBySubcontractor(String value) {
                    this.suppliedBySubcontractor = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="type" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str100" />
                 *       &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}tcmcs.str10" />
                 *     &lt;/extension>
                 *   &lt;/simpleContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "value"
                })
                public static class Note {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "type")
                    protected String type;
                    @XmlAttribute(name = "languageID")
                    protected String languageID;

                    /**
                     * Gets the value of the value property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValue() {
                        return value;
                    }

                    /**
                     * Sets the value of the value property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValue(String value) {
                        this.value = value;
                    }

                    /**
                     * Gets the value of the type property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getType() {
                        return type;
                    }

                    /**
                     * Sets the value of the type property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setType(String value) {
                        this.type = value;
                    }

                    /**
                     * Gets the value of the languageID property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getLanguageID() {
                        return languageID;
                    }

                    /**
                     * Sets the value of the languageID property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setLanguageID(String value) {
                        this.languageID = value;
                    }

                }

            }

        }

    }

}
