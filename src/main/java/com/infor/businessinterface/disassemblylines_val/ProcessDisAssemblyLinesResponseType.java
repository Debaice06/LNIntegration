
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
 * <p>Java class for processDisAssemblyLinesResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processDisAssemblyLinesResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
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
 *         &lt;element name="InformationArea" type="{http://www.infor.com/businessinterface/DisAssemblyLines_VAL}InformationArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processDisAssemblyLinesResponseType", propOrder = {
    "dataArea",
    "informationArea"
})
public class ProcessDisAssemblyLinesResponseType {

    @XmlElement(name = "DataArea")
    protected ProcessDisAssemblyLinesResponseType.DataArea dataArea;
    @XmlElement(name = "InformationArea")
    protected InformationArea informationArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessDisAssemblyLinesResponseType.DataArea }
     *     
     */
    public ProcessDisAssemblyLinesResponseType.DataArea getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessDisAssemblyLinesResponseType.DataArea }
     *     
     */
    public void setDataArea(ProcessDisAssemblyLinesResponseType.DataArea value) {
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
        "disAssemblyLinesVAL"
    })
    public static class DataArea {

        @XmlElement(name = "DisAssemblyLines_VAL")
        protected List<ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL> disAssemblyLinesVAL;

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
         * {@link ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL }
         * 
         * 
         */
        public List<ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL> getDisAssemblyLinesVAL() {
            if (disAssemblyLinesVAL == null) {
                disAssemblyLinesVAL = new ArrayList<ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL>();
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

            protected ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ContractDetails contractDetails;
            @XmlElement(name = "ItemDetails", required = true)
            protected List<ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ItemDetails> itemDetails;

            /**
             * Gets the value of the contractDetails property.
             * 
             * @return
             *     possible object is
             *     {@link ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ContractDetails }
             *     
             */
            public ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ContractDetails getContractDetails() {
                return contractDetails;
            }

            /**
             * Sets the value of the contractDetails property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ContractDetails }
             *     
             */
            public void setContractDetails(ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ContractDetails value) {
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
             * {@link ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ItemDetails }
             * 
             * 
             */
            public List<ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ItemDetails> getItemDetails() {
                if (itemDetails == null) {
                    itemDetails = new ArrayList<ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ItemDetails>();
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
                "disAssemblyItem"
            })
            public static class ItemDetails {

                @XmlElement(required = true)
                protected String disAssemblyItem;

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

            }

        }

    }

}
