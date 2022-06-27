
package com.infor.businessinterface.disassemblylines_val;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processDisAssemblyLinesRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processDisAssemblyLinesRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ControlArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}processingScope" minOccurs="0"/>
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
 *                   &lt;element name="DisAssemblyLines_VAL" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="contractDetails" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="project" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tccprj"/>
 *                                       &lt;element name="activity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.cact"/>
 *                                       &lt;element name="contract" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tccono_mandatory"/>
 *                                       &lt;element name="contractLine" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tpctm.cnln"/>
 *                                       &lt;element name="deliverableNumber" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcpono"/>
 *                                       &lt;element name="deliverableItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem" minOccurs="0"/>
 *                                       &lt;element name="orderQty" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.qajc" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="ItemDetails" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="disAssemblyItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem"/>
 *                                       &lt;element name="sortingPosition" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcmcs.str7" minOccurs="0"/>
 *                                       &lt;element name="disAssembleQuantity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tiqbm2" minOccurs="0"/>
 *                                       &lt;element name="Lines" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="position" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcpono" minOccurs="0"/>
 *                                                 &lt;element name="subItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem" minOccurs="0"/>
 *                                                 &lt;element name="quantity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.qajc" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
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
@XmlType(name = "processDisAssemblyLinesRequestType", propOrder = {
    "controlArea",
    "dataArea"
})
public class ProcessDisAssemblyLinesRequestType {

    @XmlElement(name = "ControlArea")
    protected ProcessDisAssemblyLinesRequestType.ControlArea controlArea;
    @XmlElement(name = "DataArea")
    protected ProcessDisAssemblyLinesRequestType.DataArea dataArea;

    /**
     * Gets the value of the controlArea property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessDisAssemblyLinesRequestType.ControlArea }
     *     
     */
    public ProcessDisAssemblyLinesRequestType.ControlArea getControlArea() {
        return controlArea;
    }

    /**
     * Sets the value of the controlArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessDisAssemblyLinesRequestType.ControlArea }
     *     
     */
    public void setControlArea(ProcessDisAssemblyLinesRequestType.ControlArea value) {
        this.controlArea = value;
    }

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessDisAssemblyLinesRequestType.DataArea }
     *     
     */
    public ProcessDisAssemblyLinesRequestType.DataArea getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessDisAssemblyLinesRequestType.DataArea }
     *     
     */
    public void setDataArea(ProcessDisAssemblyLinesRequestType.DataArea value) {
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
     *         &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}processingScope" minOccurs="0"/>
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
     *         &lt;element name="DisAssemblyLines_VAL" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="contractDetails" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="project" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tccprj"/>
     *                             &lt;element name="activity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.cact"/>
     *                             &lt;element name="contract" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tccono_mandatory"/>
     *                             &lt;element name="contractLine" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tpctm.cnln"/>
     *                             &lt;element name="deliverableNumber" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcpono"/>
     *                             &lt;element name="deliverableItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem" minOccurs="0"/>
     *                             &lt;element name="orderQty" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.qajc" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="ItemDetails" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="disAssemblyItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem"/>
     *                             &lt;element name="sortingPosition" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcmcs.str7" minOccurs="0"/>
     *                             &lt;element name="disAssembleQuantity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tiqbm2" minOccurs="0"/>
     *                             &lt;element name="Lines" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="position" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcpono" minOccurs="0"/>
     *                                       &lt;element name="subItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem" minOccurs="0"/>
     *                                       &lt;element name="quantity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.qajc" minOccurs="0"/>
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
    @XmlType(name = "", propOrder = {
        "messageID",
        "receiver",
        "disAssemblyLinesVAL"
    })
    public static class DataArea {

        @XmlElement(name = "MessageID", required = true)
        protected Object messageID;
        @XmlElement(name = "Receiver", required = true)
        protected Object receiver;
        @XmlElement(name = "DisAssemblyLines_VAL")
        protected List<ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL> disAssemblyLinesVAL;

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
         * Gets the value of the disAssemblyLinesVAL property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the disAssemblyLinesVAL property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDisAssemblyLinesVAL().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL }
         * 
         * 
         */
        public List<ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL> getDisAssemblyLinesVAL() {
            if (disAssemblyLinesVAL == null) {
                disAssemblyLinesVAL = new ArrayList<ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL>();
            }
            return this.disAssemblyLinesVAL;
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
         *         &lt;element name="contractDetails" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="project" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tccprj"/>
         *                   &lt;element name="activity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.cact"/>
         *                   &lt;element name="contract" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tccono_mandatory"/>
         *                   &lt;element name="contractLine" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tpctm.cnln"/>
         *                   &lt;element name="deliverableNumber" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcpono"/>
         *                   &lt;element name="deliverableItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem" minOccurs="0"/>
         *                   &lt;element name="orderQty" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.qajc" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="ItemDetails" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="disAssemblyItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem"/>
         *                   &lt;element name="sortingPosition" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcmcs.str7" minOccurs="0"/>
         *                   &lt;element name="disAssembleQuantity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tiqbm2" minOccurs="0"/>
         *                   &lt;element name="Lines" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="position" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcpono" minOccurs="0"/>
         *                             &lt;element name="subItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem" minOccurs="0"/>
         *                             &lt;element name="quantity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.qajc" minOccurs="0"/>
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
            "contractDetails",
            "itemDetails"
        })
        public static class DisAssemblyLinesVAL {

            protected ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ContractDetails contractDetails;
            @XmlElement(name = "ItemDetails", required = true)
            protected List<ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails> itemDetails;

            /**
             * Gets the value of the contractDetails property.
             * 
             * @return
             *     possible object is
             *     {@link ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ContractDetails }
             *     
             */
            public ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ContractDetails getContractDetails() {
                return contractDetails;
            }

            /**
             * Sets the value of the contractDetails property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ContractDetails }
             *     
             */
            public void setContractDetails(ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ContractDetails value) {
                this.contractDetails = value;
            }

            /**
             * Gets the value of the itemDetails property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the itemDetails property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getItemDetails().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails }
             * 
             * 
             */
            public List<ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails> getItemDetails() {
                if (itemDetails == null) {
                    itemDetails = new ArrayList<ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails>();
                }
                return this.itemDetails;
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
             *         &lt;element name="project" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tccprj"/>
             *         &lt;element name="activity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.cact"/>
             *         &lt;element name="contract" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tccono_mandatory"/>
             *         &lt;element name="contractLine" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tpctm.cnln"/>
             *         &lt;element name="deliverableNumber" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcpono"/>
             *         &lt;element name="deliverableItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem" minOccurs="0"/>
             *         &lt;element name="orderQty" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.qajc" minOccurs="0"/>
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
                "project",
                "activity",
                "contract",
                "contractLine",
                "deliverableNumber",
                "deliverableItem",
                "orderQty"
            })
            public static class ContractDetails {

                @XmlElement(required = true)
                protected String project;
                @XmlElement(required = true)
                protected String activity;
                @XmlElement(required = true)
                protected String contract;
                @XmlElement(required = true)
                protected String contractLine;
                @XmlElement(required = true)
                protected BigInteger deliverableNumber;
                protected String deliverableItem;
                protected BigDecimal orderQty;

                /**
                 * Gets the value of the project property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getProject() {
                    return project;
                }

                /**
                 * Sets the value of the project property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setProject(String value) {
                    this.project = value;
                }

                /**
                 * Gets the value of the activity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getActivity() {
                    return activity;
                }

                /**
                 * Sets the value of the activity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setActivity(String value) {
                    this.activity = value;
                }

                /**
                 * Gets the value of the contract property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getContract() {
                    return contract;
                }

                /**
                 * Sets the value of the contract property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setContract(String value) {
                    this.contract = value;
                }

                /**
                 * Gets the value of the contractLine property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getContractLine() {
                    return contractLine;
                }

                /**
                 * Sets the value of the contractLine property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setContractLine(String value) {
                    this.contractLine = value;
                }

                /**
                 * Gets the value of the deliverableNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getDeliverableNumber() {
                    return deliverableNumber;
                }

                /**
                 * Sets the value of the deliverableNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setDeliverableNumber(BigInteger value) {
                    this.deliverableNumber = value;
                }

                /**
                 * Gets the value of the deliverableItem property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDeliverableItem() {
                    return deliverableItem;
                }

                /**
                 * Sets the value of the deliverableItem property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDeliverableItem(String value) {
                    this.deliverableItem = value;
                }

                /**
                 * Gets the value of the orderQty property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getOrderQty() {
                    return orderQty;
                }

                /**
                 * Sets the value of the orderQty property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setOrderQty(BigDecimal value) {
                    this.orderQty = value;
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
             *         &lt;element name="disAssemblyItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem"/>
             *         &lt;element name="sortingPosition" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcmcs.str7" minOccurs="0"/>
             *         &lt;element name="disAssembleQuantity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tiqbm2" minOccurs="0"/>
             *         &lt;element name="Lines" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="position" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcpono" minOccurs="0"/>
             *                   &lt;element name="subItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem" minOccurs="0"/>
             *                   &lt;element name="quantity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.qajc" minOccurs="0"/>
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
                "disAssemblyItem",
                "sortingPosition",
                "disAssembleQuantity",
                "lines"
            })
            public static class ItemDetails {

                @XmlElement(required = true)
                protected String disAssemblyItem;
                protected String sortingPosition;
                protected BigDecimal disAssembleQuantity;
                @XmlElement(name = "Lines", required = true)
                protected List<ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails.Lines> lines;

                /**
                 * Gets the value of the disAssemblyItem property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDisAssemblyItem() {
                    return disAssemblyItem;
                }

                /**
                 * Sets the value of the disAssemblyItem property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDisAssemblyItem(String value) {
                    this.disAssemblyItem = value;
                }

                /**
                 * Gets the value of the sortingPosition property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSortingPosition() {
                    return sortingPosition;
                }

                /**
                 * Sets the value of the sortingPosition property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSortingPosition(String value) {
                    this.sortingPosition = value;
                }

                /**
                 * Gets the value of the disAssembleQuantity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getDisAssembleQuantity() {
                    return disAssembleQuantity;
                }

                /**
                 * Sets the value of the disAssembleQuantity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setDisAssembleQuantity(BigDecimal value) {
                    this.disAssembleQuantity = value;
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
                 * {@link ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails.Lines }
                 * 
                 * 
                 */
                public List<ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails.Lines> getLines() {
                    if (lines == null) {
                        lines = new ArrayList<ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails.Lines>();
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
                 *         &lt;element name="position" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcpono" minOccurs="0"/>
                 *         &lt;element name="subItem" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tcitem" minOccurs="0"/>
                 *         &lt;element name="quantity" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}tppdm.qajc" minOccurs="0"/>
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
                    "position",
                    "subItem",
                    "quantity"
                })
                public static class Lines {

                    protected BigInteger position;
                    protected String subItem;
                    protected BigDecimal quantity;

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
                     * Gets the value of the subItem property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSubItem() {
                        return subItem;
                    }

                    /**
                     * Sets the value of the subItem property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSubItem(String value) {
                        this.subItem = value;
                    }

                    /**
                     * Gets the value of the quantity property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getQuantity() {
                        return quantity;
                    }

                    /**
                     * Sets the value of the quantity property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setQuantity(BigDecimal value) {
                        this.quantity = value;
                    }

                }

            }

        }

    }

}
