
package com.infor.businessinterface.item_val;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for processItemDataRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processItemDataRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ControlArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/Item_VAL}processingScope" minOccurs="0"/>
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
 *                   &lt;element name="Item_VAL" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ItemID" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="description" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="revision" type="{http://www.infor.com/businessinterface/Item_VAL}tiedm.revi" minOccurs="0"/>
 *                             &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/Item_VAL}tccitg" minOccurs="0"/>
 *                             &lt;element name="itemType" type="{http://www.infor.com/businessinterface/Item_VAL}tckitm_mandatory" minOccurs="0"/>
 *                             &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
 *                             &lt;element name="productType" type="{http://www.infor.com/businessinterface/Item_VAL}tcctyp" minOccurs="0"/>
 *                             &lt;element name="productLine" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.cpln" minOccurs="0"/>
 *                                       &lt;element name="lineDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="productClass" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.cpcl" minOccurs="0"/>
 *                                       &lt;element name="classDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="purchaseStatisticsGroup" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem" minOccurs="0"/>
 *                             &lt;element name="sourceItem" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem" minOccurs="0"/>
 *                             &lt;element name="signal" type="{http://www.infor.com/businessinterface/Item_VAL}tccsig" minOccurs="0"/>
 *                             &lt;element name="commodityCode" type="{http://www.infor.com/businessinterface/Item_VAL}tcccde" minOccurs="0"/>
 *                             &lt;element name="commodityCodeUS" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str25" minOccurs="0"/>
 *                             &lt;element name="commodityCodeCN" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str25" minOccurs="0"/>
 *                             &lt;element name="weight" type="{http://www.infor.com/businessinterface/Item_VAL}tcwght" minOccurs="0"/>
 *                             &lt;element name="weightUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
 *                             &lt;element name="unitSet" type="{http://www.infor.com/businessinterface/Item_VAL}tcuset" minOccurs="0"/>
 *                             &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
 *                             &lt;element name="material" type="{http://www.infor.com/businessinterface/Item_VAL}tcdscb" minOccurs="0"/>
 *                             &lt;element name="size" type="{http://www.infor.com/businessinterface/Item_VAL}tcdscc" minOccurs="0"/>
 *                             &lt;element name="standard" type="{http://www.infor.com/businessinterface/Item_VAL}tcdscb" minOccurs="0"/>
 *                             &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/Item_VAL}tccsel" minOccurs="0"/>
 *                             &lt;element name="technicalCoordinator" type="{http://www.infor.com/businessinterface/Item_VAL}tcemno" minOccurs="0"/>
 *                             &lt;element name="engineer" type="{http://www.infor.com/businessinterface/Item_VAL}tcemno" minOccurs="0"/>
 *                             &lt;element name="purchaseUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
 *                             &lt;element name="purchasePriceUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
 *                             &lt;element name="salesUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
 *                             &lt;element name="salesPriceUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
 *                             &lt;element name="conversionFactor" type="{http://www.infor.com/businessinterface/Item_VAL}tcqiv1" minOccurs="0"/>
 *                             &lt;element name="creationDate" type="{http://www.infor.com/businessinterface/Item_VAL}tcdate" minOccurs="0"/>
 *                             &lt;element name="revisionDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
 *                             &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str100" />
 *                                     &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="alternativeItem" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="action" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="alternativeItemRevision" type="{http://www.infor.com/businessinterface/Item_VAL}tiedm.revi" minOccurs="0"/>
 *                             &lt;element name="phantom" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" minOccurs="0"/>
 *                             &lt;element name="extraLeadTime" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
 *                             &lt;element name="defaultSupplySource" type="{http://www.infor.com/businessinterface/Item_VAL}tcsrce_mandatory" minOccurs="0"/>
 *                             &lt;element name="lotControlled" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
 *                             &lt;element name="serialized" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
 *                             &lt;element name="extraInformation" type="{http://www.infor.com/businessinterface/Item_VAL}tcexin" minOccurs="0"/>
 *                             &lt;element name="docID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.s512m" minOccurs="0"/>
 *                             &lt;element name="salesStatisticsGroup" type="{http://www.infor.com/businessinterface/Item_VAL}tccsgp" minOccurs="0"/>
 *                             &lt;element name="classOfRisk" type="{http://www.infor.com/businessinterface/Item_VAL}whwmd.risk" minOccurs="0"/>
 *                             &lt;element name="hazardousMaterial" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
 *                             &lt;element name="itemSubtype" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
 *                             &lt;element name="sparePart" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
 *                             &lt;element name="serviceTracked" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
 *                             &lt;element name="serviceTrackCategorization" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
 *                             &lt;element name="longItemDescription" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str7" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="searchKey1" type="{http://www.infor.com/businessinterface/Item_VAL}tcseak" minOccurs="0"/>
 *                             &lt;element name="searchKey2" type="{http://www.infor.com/businessinterface/Item_VAL}tcseak" minOccurs="0"/>
 *                             &lt;element name="itemTaxonomy" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str50" minOccurs="0"/>
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
@XmlType(name = "processItemDataRequestType", propOrder = {
    "controlArea",
    "dataArea"
})
public class ProcessItemDataRequestType {

    @XmlElement(name = "ControlArea")
    protected ProcessItemDataRequestType.ControlArea controlArea;
    @XmlElement(name = "DataArea")
    protected ProcessItemDataRequestType.DataArea dataArea;

    /**
     * Gets the value of the controlArea property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessItemDataRequestType.ControlArea }
     *     
     */
    public ProcessItemDataRequestType.ControlArea getControlArea() {
        return controlArea;
    }

    /**
     * Sets the value of the controlArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessItemDataRequestType.ControlArea }
     *     
     */
    public void setControlArea(ProcessItemDataRequestType.ControlArea value) {
        this.controlArea = value;
    }

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessItemDataRequestType.DataArea }
     *     
     */
    public ProcessItemDataRequestType.DataArea getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessItemDataRequestType.DataArea }
     *     
     */
    public void setDataArea(ProcessItemDataRequestType.DataArea value) {
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
     *         &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/Item_VAL}processingScope" minOccurs="0"/>
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
     *         &lt;element name="Item_VAL" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ItemID" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="description" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="revision" type="{http://www.infor.com/businessinterface/Item_VAL}tiedm.revi" minOccurs="0"/>
     *                   &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/Item_VAL}tccitg" minOccurs="0"/>
     *                   &lt;element name="itemType" type="{http://www.infor.com/businessinterface/Item_VAL}tckitm_mandatory" minOccurs="0"/>
     *                   &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
     *                   &lt;element name="productType" type="{http://www.infor.com/businessinterface/Item_VAL}tcctyp" minOccurs="0"/>
     *                   &lt;element name="productLine" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.cpln" minOccurs="0"/>
     *                             &lt;element name="lineDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="productClass" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.cpcl" minOccurs="0"/>
     *                             &lt;element name="classDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="purchaseStatisticsGroup" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem" minOccurs="0"/>
     *                   &lt;element name="sourceItem" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem" minOccurs="0"/>
     *                   &lt;element name="signal" type="{http://www.infor.com/businessinterface/Item_VAL}tccsig" minOccurs="0"/>
     *                   &lt;element name="commodityCode" type="{http://www.infor.com/businessinterface/Item_VAL}tcccde" minOccurs="0"/>
     *                   &lt;element name="commodityCodeUS" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str25" minOccurs="0"/>
     *                   &lt;element name="commodityCodeCN" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str25" minOccurs="0"/>
     *                   &lt;element name="weight" type="{http://www.infor.com/businessinterface/Item_VAL}tcwght" minOccurs="0"/>
     *                   &lt;element name="weightUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
     *                   &lt;element name="unitSet" type="{http://www.infor.com/businessinterface/Item_VAL}tcuset" minOccurs="0"/>
     *                   &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
     *                   &lt;element name="material" type="{http://www.infor.com/businessinterface/Item_VAL}tcdscb" minOccurs="0"/>
     *                   &lt;element name="size" type="{http://www.infor.com/businessinterface/Item_VAL}tcdscc" minOccurs="0"/>
     *                   &lt;element name="standard" type="{http://www.infor.com/businessinterface/Item_VAL}tcdscb" minOccurs="0"/>
     *                   &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/Item_VAL}tccsel" minOccurs="0"/>
     *                   &lt;element name="technicalCoordinator" type="{http://www.infor.com/businessinterface/Item_VAL}tcemno" minOccurs="0"/>
     *                   &lt;element name="engineer" type="{http://www.infor.com/businessinterface/Item_VAL}tcemno" minOccurs="0"/>
     *                   &lt;element name="purchaseUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
     *                   &lt;element name="purchasePriceUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
     *                   &lt;element name="salesUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
     *                   &lt;element name="salesPriceUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
     *                   &lt;element name="conversionFactor" type="{http://www.infor.com/businessinterface/Item_VAL}tcqiv1" minOccurs="0"/>
     *                   &lt;element name="creationDate" type="{http://www.infor.com/businessinterface/Item_VAL}tcdate" minOccurs="0"/>
     *                   &lt;element name="revisionDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
     *                   &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str100" />
     *                           &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="alternativeItem" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="action" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="alternativeItemRevision" type="{http://www.infor.com/businessinterface/Item_VAL}tiedm.revi" minOccurs="0"/>
     *                   &lt;element name="phantom" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" minOccurs="0"/>
     *                   &lt;element name="extraLeadTime" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
     *                   &lt;element name="defaultSupplySource" type="{http://www.infor.com/businessinterface/Item_VAL}tcsrce_mandatory" minOccurs="0"/>
     *                   &lt;element name="lotControlled" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
     *                   &lt;element name="serialized" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
     *                   &lt;element name="extraInformation" type="{http://www.infor.com/businessinterface/Item_VAL}tcexin" minOccurs="0"/>
     *                   &lt;element name="docID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.s512m" minOccurs="0"/>
     *                   &lt;element name="salesStatisticsGroup" type="{http://www.infor.com/businessinterface/Item_VAL}tccsgp" minOccurs="0"/>
     *                   &lt;element name="classOfRisk" type="{http://www.infor.com/businessinterface/Item_VAL}whwmd.risk" minOccurs="0"/>
     *                   &lt;element name="hazardousMaterial" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
     *                   &lt;element name="itemSubtype" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
     *                   &lt;element name="sparePart" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
     *                   &lt;element name="serviceTracked" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
     *                   &lt;element name="serviceTrackCategorization" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
     *                   &lt;element name="longItemDescription" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str7" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="searchKey1" type="{http://www.infor.com/businessinterface/Item_VAL}tcseak" minOccurs="0"/>
     *                   &lt;element name="searchKey2" type="{http://www.infor.com/businessinterface/Item_VAL}tcseak" minOccurs="0"/>
     *                   &lt;element name="itemTaxonomy" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str50" minOccurs="0"/>
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
        "itemVAL"
    })
    public static class DataArea {

        @XmlElement(name = "MessageID", required = true)
        protected Object messageID;
        @XmlElement(name = "Receiver", required = true)
        protected Object receiver;
        @XmlElement(name = "Item_VAL")
        protected List<ProcessItemDataRequestType.DataArea.ItemVAL> itemVAL;

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
         * Gets the value of the itemVAL property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the itemVAL property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItemVAL().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProcessItemDataRequestType.DataArea.ItemVAL }
         * 
         * 
         */
        public List<ProcessItemDataRequestType.DataArea.ItemVAL> getItemVAL() {
            if (itemVAL == null) {
                itemVAL = new ArrayList<ProcessItemDataRequestType.DataArea.ItemVAL>();
            }
            return this.itemVAL;
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
         *                   &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="description" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="revision" type="{http://www.infor.com/businessinterface/Item_VAL}tiedm.revi" minOccurs="0"/>
         *         &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/Item_VAL}tccitg" minOccurs="0"/>
         *         &lt;element name="itemType" type="{http://www.infor.com/businessinterface/Item_VAL}tckitm_mandatory" minOccurs="0"/>
         *         &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
         *         &lt;element name="productType" type="{http://www.infor.com/businessinterface/Item_VAL}tcctyp" minOccurs="0"/>
         *         &lt;element name="productLine" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.cpln" minOccurs="0"/>
         *                   &lt;element name="lineDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="productClass" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.cpcl" minOccurs="0"/>
         *                   &lt;element name="classDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="purchaseStatisticsGroup" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem" minOccurs="0"/>
         *         &lt;element name="sourceItem" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem" minOccurs="0"/>
         *         &lt;element name="signal" type="{http://www.infor.com/businessinterface/Item_VAL}tccsig" minOccurs="0"/>
         *         &lt;element name="commodityCode" type="{http://www.infor.com/businessinterface/Item_VAL}tcccde" minOccurs="0"/>
         *         &lt;element name="commodityCodeUS" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str25" minOccurs="0"/>
         *         &lt;element name="commodityCodeCN" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str25" minOccurs="0"/>
         *         &lt;element name="weight" type="{http://www.infor.com/businessinterface/Item_VAL}tcwght" minOccurs="0"/>
         *         &lt;element name="weightUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
         *         &lt;element name="unitSet" type="{http://www.infor.com/businessinterface/Item_VAL}tcuset" minOccurs="0"/>
         *         &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
         *         &lt;element name="material" type="{http://www.infor.com/businessinterface/Item_VAL}tcdscb" minOccurs="0"/>
         *         &lt;element name="size" type="{http://www.infor.com/businessinterface/Item_VAL}tcdscc" minOccurs="0"/>
         *         &lt;element name="standard" type="{http://www.infor.com/businessinterface/Item_VAL}tcdscb" minOccurs="0"/>
         *         &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/Item_VAL}tccsel" minOccurs="0"/>
         *         &lt;element name="technicalCoordinator" type="{http://www.infor.com/businessinterface/Item_VAL}tcemno" minOccurs="0"/>
         *         &lt;element name="engineer" type="{http://www.infor.com/businessinterface/Item_VAL}tcemno" minOccurs="0"/>
         *         &lt;element name="purchaseUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
         *         &lt;element name="purchasePriceUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
         *         &lt;element name="salesUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
         *         &lt;element name="salesPriceUnit" type="{http://www.infor.com/businessinterface/Item_VAL}tccuni" minOccurs="0"/>
         *         &lt;element name="conversionFactor" type="{http://www.infor.com/businessinterface/Item_VAL}tcqiv1" minOccurs="0"/>
         *         &lt;element name="creationDate" type="{http://www.infor.com/businessinterface/Item_VAL}tcdate" minOccurs="0"/>
         *         &lt;element name="revisionDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
         *         &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str100" />
         *                 &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="alternativeItem" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="action" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="alternativeItemRevision" type="{http://www.infor.com/businessinterface/Item_VAL}tiedm.revi" minOccurs="0"/>
         *         &lt;element name="phantom" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" minOccurs="0"/>
         *         &lt;element name="extraLeadTime" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
         *         &lt;element name="defaultSupplySource" type="{http://www.infor.com/businessinterface/Item_VAL}tcsrce_mandatory" minOccurs="0"/>
         *         &lt;element name="lotControlled" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
         *         &lt;element name="serialized" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
         *         &lt;element name="extraInformation" type="{http://www.infor.com/businessinterface/Item_VAL}tcexin" minOccurs="0"/>
         *         &lt;element name="docID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.s512m" minOccurs="0"/>
         *         &lt;element name="salesStatisticsGroup" type="{http://www.infor.com/businessinterface/Item_VAL}tccsgp" minOccurs="0"/>
         *         &lt;element name="classOfRisk" type="{http://www.infor.com/businessinterface/Item_VAL}whwmd.risk" minOccurs="0"/>
         *         &lt;element name="hazardousMaterial" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
         *         &lt;element name="itemSubtype" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
         *         &lt;element name="sparePart" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
         *         &lt;element name="serviceTracked" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str6" minOccurs="0"/>
         *         &lt;element name="serviceTrackCategorization" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str30" minOccurs="0"/>
         *         &lt;element name="longItemDescription" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str7" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="searchKey1" type="{http://www.infor.com/businessinterface/Item_VAL}tcseak" minOccurs="0"/>
         *         &lt;element name="searchKey2" type="{http://www.infor.com/businessinterface/Item_VAL}tcseak" minOccurs="0"/>
         *         &lt;element name="itemTaxonomy" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str50" minOccurs="0"/>
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
            "description",
            "revision",
            "itemGroup",
            "itemType",
            "inventoryUnit",
            "productType",
            "productLine",
            "productClass",
            "purchaseStatisticsGroup",
            "sourceItem",
            "signal",
            "commodityCode",
            "commodityCodeUS",
            "commodityCodeCN",
            "weight",
            "weightUnit",
            "unitSet",
            "drawingNumber",
            "material",
            "size",
            "standard",
            "selectionCode",
            "lotNumberRequired",
            "technicalCoordinator",
            "engineer",
            "purchaseUnit",
            "purchasePriceUnit",
            "salesUnit",
            "salesPriceUnit",
            "conversionFactor",
            "creationDate",
            "revisionDescription",
            "note",
            "alternativeItem",
            "alternativeItemRevision",
            "phantom",
            "extraLeadTime",
            "defaultSupplySource",
            "lotControlled",
            "serialized",
            "extraInformation",
            "docID",
            "salesStatisticsGroup",
            "classOfRisk",
            "hazardousMaterial",
            "itemSubtype",
            "sparePart",
            "serviceTracked",
            "serviceTrackCategorization",
            "longItemDescription",
            "searchKey1",
            "searchKey2",
            "itemTaxonomy"
        })
        public static class ItemVAL {

            @XmlElement(name = "ItemID")
            protected ProcessItemDataRequestType.DataArea.ItemVAL.ItemID itemID;
            protected List<ProcessItemDataRequestType.DataArea.ItemVAL.Description> description;
            protected String revision;
            protected String itemGroup;
            protected TckitmMandatory itemType;
            protected String inventoryUnit;
            protected String productType;
            protected ProcessItemDataRequestType.DataArea.ItemVAL.ProductLine productLine;
            protected ProcessItemDataRequestType.DataArea.ItemVAL.ProductClass productClass;
            protected String purchaseStatisticsGroup;
            protected String sourceItem;
            protected String signal;
            protected String commodityCode;
            protected String commodityCodeUS;
            protected String commodityCodeCN;
            protected BigDecimal weight;
            protected String weightUnit;
            protected String unitSet;
            protected String drawingNumber;
            protected String material;
            protected String size;
            protected String standard;
            protected String selectionCode;
            protected String lotNumberRequired;
            protected String technicalCoordinator;
            protected String engineer;
            protected String purchaseUnit;
            protected String purchasePriceUnit;
            protected String salesUnit;
            protected String salesPriceUnit;
            protected BigDecimal conversionFactor;
            protected XMLGregorianCalendar creationDate;
            protected String revisionDescription;
            @XmlElement(name = "Note")
            protected List<ProcessItemDataRequestType.DataArea.ItemVAL.Note> note;
            protected List<ProcessItemDataRequestType.DataArea.ItemVAL.AlternativeItem> alternativeItem;
            protected String alternativeItemRevision;
            protected String phantom;
            protected String extraLeadTime;
            protected TcsrceMandatory defaultSupplySource;
            protected String lotControlled;
            protected String serialized;
            protected String extraInformation;
            protected String docID;
            protected String salesStatisticsGroup;
            protected String classOfRisk;
            protected String hazardousMaterial;
            protected String itemSubtype;
            protected String sparePart;
            protected String serviceTracked;
            protected String serviceTrackCategorization;
            protected List<ProcessItemDataRequestType.DataArea.ItemVAL.LongItemDescription> longItemDescription;
            protected String searchKey1;
            protected String searchKey2;
            protected String itemTaxonomy;

            /**
             * Gets the value of the itemID property.
             * 
             * @return
             *     possible object is
             *     {@link ProcessItemDataRequestType.DataArea.ItemVAL.ItemID }
             *     
             */
            public ProcessItemDataRequestType.DataArea.ItemVAL.ItemID getItemID() {
                return itemID;
            }

            /**
             * Sets the value of the itemID property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProcessItemDataRequestType.DataArea.ItemVAL.ItemID }
             *     
             */
            public void setItemID(ProcessItemDataRequestType.DataArea.ItemVAL.ItemID value) {
                this.itemID = value;
            }

            /**
             * Gets the value of the description property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the description property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDescription().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ProcessItemDataRequestType.DataArea.ItemVAL.Description }
             * 
             * 
             */
            public List<ProcessItemDataRequestType.DataArea.ItemVAL.Description> getDescription() {
                if (description == null) {
                    description = new ArrayList<ProcessItemDataRequestType.DataArea.ItemVAL.Description>();
                }
                return this.description;
            }

            /**
             * Gets the value of the revision property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRevision() {
                return revision;
            }

            /**
             * Sets the value of the revision property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRevision(String value) {
                this.revision = value;
            }

            /**
             * Gets the value of the itemGroup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getItemGroup() {
                return itemGroup;
            }

            /**
             * Sets the value of the itemGroup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setItemGroup(String value) {
                this.itemGroup = value;
            }

            /**
             * Gets the value of the itemType property.
             * 
             * @return
             *     possible object is
             *     {@link TckitmMandatory }
             *     
             */
            public TckitmMandatory getItemType() {
                return itemType;
            }

            /**
             * Sets the value of the itemType property.
             * 
             * @param value
             *     allowed object is
             *     {@link TckitmMandatory }
             *     
             */
            public void setItemType(TckitmMandatory value) {
                this.itemType = value;
            }

            /**
             * Gets the value of the inventoryUnit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInventoryUnit() {
                return inventoryUnit;
            }

            /**
             * Sets the value of the inventoryUnit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInventoryUnit(String value) {
                this.inventoryUnit = value;
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

            /**
             * Gets the value of the productLine property.
             * 
             * @return
             *     possible object is
             *     {@link ProcessItemDataRequestType.DataArea.ItemVAL.ProductLine }
             *     
             */
            public ProcessItemDataRequestType.DataArea.ItemVAL.ProductLine getProductLine() {
                return productLine;
            }

            /**
             * Sets the value of the productLine property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProcessItemDataRequestType.DataArea.ItemVAL.ProductLine }
             *     
             */
            public void setProductLine(ProcessItemDataRequestType.DataArea.ItemVAL.ProductLine value) {
                this.productLine = value;
            }

            /**
             * Gets the value of the productClass property.
             * 
             * @return
             *     possible object is
             *     {@link ProcessItemDataRequestType.DataArea.ItemVAL.ProductClass }
             *     
             */
            public ProcessItemDataRequestType.DataArea.ItemVAL.ProductClass getProductClass() {
                return productClass;
            }

            /**
             * Sets the value of the productClass property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProcessItemDataRequestType.DataArea.ItemVAL.ProductClass }
             *     
             */
            public void setProductClass(ProcessItemDataRequestType.DataArea.ItemVAL.ProductClass value) {
                this.productClass = value;
            }

            /**
             * Gets the value of the purchaseStatisticsGroup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPurchaseStatisticsGroup() {
                return purchaseStatisticsGroup;
            }

            /**
             * Sets the value of the purchaseStatisticsGroup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPurchaseStatisticsGroup(String value) {
                this.purchaseStatisticsGroup = value;
            }

            /**
             * Gets the value of the sourceItem property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSourceItem() {
                return sourceItem;
            }

            /**
             * Sets the value of the sourceItem property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSourceItem(String value) {
                this.sourceItem = value;
            }

            /**
             * Gets the value of the signal property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSignal() {
                return signal;
            }

            /**
             * Sets the value of the signal property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSignal(String value) {
                this.signal = value;
            }

            /**
             * Gets the value of the commodityCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCommodityCode() {
                return commodityCode;
            }

            /**
             * Sets the value of the commodityCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCommodityCode(String value) {
                this.commodityCode = value;
            }

            /**
             * Gets the value of the commodityCodeUS property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCommodityCodeUS() {
                return commodityCodeUS;
            }

            /**
             * Sets the value of the commodityCodeUS property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCommodityCodeUS(String value) {
                this.commodityCodeUS = value;
            }

            /**
             * Gets the value of the commodityCodeCN property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCommodityCodeCN() {
                return commodityCodeCN;
            }

            /**
             * Sets the value of the commodityCodeCN property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCommodityCodeCN(String value) {
                this.commodityCodeCN = value;
            }

            /**
             * Gets the value of the weight property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getWeight() {
                return weight;
            }

            /**
             * Sets the value of the weight property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setWeight(BigDecimal value) {
                this.weight = value;
            }

            /**
             * Gets the value of the weightUnit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getWeightUnit() {
                return weightUnit;
            }

            /**
             * Sets the value of the weightUnit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setWeightUnit(String value) {
                this.weightUnit = value;
            }

            /**
             * Gets the value of the unitSet property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnitSet() {
                return unitSet;
            }

            /**
             * Sets the value of the unitSet property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnitSet(String value) {
                this.unitSet = value;
            }

            /**
             * Gets the value of the drawingNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDrawingNumber() {
                return drawingNumber;
            }

            /**
             * Sets the value of the drawingNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDrawingNumber(String value) {
                this.drawingNumber = value;
            }

            /**
             * Gets the value of the material property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMaterial() {
                return material;
            }

            /**
             * Sets the value of the material property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMaterial(String value) {
                this.material = value;
            }

            /**
             * Gets the value of the size property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSize() {
                return size;
            }

            /**
             * Sets the value of the size property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSize(String value) {
                this.size = value;
            }

            /**
             * Gets the value of the standard property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStandard() {
                return standard;
            }

            /**
             * Sets the value of the standard property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStandard(String value) {
                this.standard = value;
            }

            /**
             * Gets the value of the selectionCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSelectionCode() {
                return selectionCode;
            }

            /**
             * Sets the value of the selectionCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSelectionCode(String value) {
                this.selectionCode = value;
            }

                   /**
             * Gets the value of the lotNumberRequired property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLotNumberRequired() {
                return lotNumberRequired;
            }

            /**
             * Sets the value of the lotNumberRequired property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLotNumberRequired(String value) {
                this.lotNumberRequired = value;
            }     
            
            /**
             * Gets the value of the technicalCoordinator property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTechnicalCoordinator() {
                return technicalCoordinator;
            }

            /**
             * Sets the value of the technicalCoordinator property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTechnicalCoordinator(String value) {
                this.technicalCoordinator = value;
            }

            /**
             * Gets the value of the engineer property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEngineer() {
                return engineer;
            }

            /**
             * Sets the value of the engineer property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEngineer(String value) {
                this.engineer = value;
            }

            /**
             * Gets the value of the purchaseUnit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPurchaseUnit() {
                return purchaseUnit;
            }

            /**
             * Sets the value of the purchaseUnit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPurchaseUnit(String value) {
                this.purchaseUnit = value;
            }

            /**
             * Gets the value of the purchasePriceUnit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPurchasePriceUnit() {
                return purchasePriceUnit;
            }

            /**
             * Sets the value of the purchasePriceUnit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPurchasePriceUnit(String value) {
                this.purchasePriceUnit = value;
            }

            /**
             * Gets the value of the salesUnit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSalesUnit() {
                return salesUnit;
            }

            /**
             * Sets the value of the salesUnit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSalesUnit(String value) {
                this.salesUnit = value;
            }

            /**
             * Gets the value of the salesPriceUnit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSalesPriceUnit() {
                return salesPriceUnit;
            }

            /**
             * Sets the value of the salesPriceUnit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSalesPriceUnit(String value) {
                this.salesPriceUnit = value;
            }

            /**
             * Gets the value of the conversionFactor property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getConversionFactor() {
                return conversionFactor;
            }

            /**
             * Sets the value of the conversionFactor property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setConversionFactor(BigDecimal value) {
                this.conversionFactor = value;
            }

            /**
             * Gets the value of the creationDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getCreationDate() {
                return creationDate;
            }

            /**
             * Sets the value of the creationDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setCreationDate(XMLGregorianCalendar value) {
                this.creationDate = value;
            }

            /**
             * Gets the value of the revisionDescription property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRevisionDescription() {
                return revisionDescription;
            }

            /**
             * Sets the value of the revisionDescription property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRevisionDescription(String value) {
                this.revisionDescription = value;
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
             * {@link ProcessItemDataRequestType.DataArea.ItemVAL.Note }
             * 
             * 
             */
            public List<ProcessItemDataRequestType.DataArea.ItemVAL.Note> getNote() {
                if (note == null) {
                    note = new ArrayList<ProcessItemDataRequestType.DataArea.ItemVAL.Note>();
                }
                return this.note;
            }

            /**
             * Gets the value of the alternativeItem property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the alternativeItem property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAlternativeItem().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ProcessItemDataRequestType.DataArea.ItemVAL.AlternativeItem }
             * 
             * 
             */
            public List<ProcessItemDataRequestType.DataArea.ItemVAL.AlternativeItem> getAlternativeItem() {
                if (alternativeItem == null) {
                    alternativeItem = new ArrayList<ProcessItemDataRequestType.DataArea.ItemVAL.AlternativeItem>();
                }
                return this.alternativeItem;
            }

            /**
             * Gets the value of the alternativeItemRevision property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAlternativeItemRevision() {
                return alternativeItemRevision;
            }

            /**
             * Sets the value of the alternativeItemRevision property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAlternativeItemRevision(String value) {
                this.alternativeItemRevision = value;
            }

            /**
             * Gets the value of the phantom property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPhantom() {
                return phantom;
            }

            /**
             * Sets the value of the phantom property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPhantom(String value) {
                this.phantom = value;
            }

            /**
             * Gets the value of the extraLeadTime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExtraLeadTime() {
                return extraLeadTime;
            }

            /**
             * Sets the value of the extraLeadTime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExtraLeadTime(String value) {
                this.extraLeadTime = value;
            }

            /**
             * Gets the value of the defaultSupplySource property.
             * 
             * @return
             *     possible object is
             *     {@link TcsrceMandatory }
             *     
             */
            public TcsrceMandatory getDefaultSupplySource() {
                return defaultSupplySource;
            }

            /**
             * Sets the value of the defaultSupplySource property.
             * 
             * @param value
             *     allowed object is
             *     {@link TcsrceMandatory }
             *     
             */
            public void setDefaultSupplySource(TcsrceMandatory value) {
                this.defaultSupplySource = value;
            }

            /**
             * Gets the value of the lotControlled property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLotControlled() {
                return lotControlled;
            }

            /**
             * Sets the value of the lotControlled property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLotControlled(String value) {
                this.lotControlled = value;
            }

            /**
             * Gets the value of the serialized property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSerialized() {
                return serialized;
            }

            /**
             * Sets the value of the serialized property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSerialized(String value) {
                this.serialized = value;
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
             * Gets the value of the docID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDocID() {
                return docID;
            }

            /**
             * Sets the value of the docID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDocID(String value) {
                this.docID = value;
            }

            /**
             * Gets the value of the salesStatisticsGroup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSalesStatisticsGroup() {
                return salesStatisticsGroup;
            }

            /**
             * Sets the value of the salesStatisticsGroup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSalesStatisticsGroup(String value) {
                this.salesStatisticsGroup = value;
            }

            /**
             * Gets the value of the classOfRisk property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getClassOfRisk() {
                return classOfRisk;
            }

            /**
             * Sets the value of the classOfRisk property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setClassOfRisk(String value) {
                this.classOfRisk = value;
            }

            /**
             * Gets the value of the hazardousMaterial property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHazardousMaterial() {
                return hazardousMaterial;
            }

            /**
             * Sets the value of the hazardousMaterial property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHazardousMaterial(String value) {
                this.hazardousMaterial = value;
            }

            /**
             * Gets the value of the itemSubtype property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getItemSubtype() {
                return itemSubtype;
            }

            /**
             * Sets the value of the itemSubtype property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setItemSubtype(String value) {
                this.itemSubtype = value;
            }

            /**
             * Gets the value of the sparePart property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSparePart() {
                return sparePart;
            }

            /**
             * Sets the value of the sparePart property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSparePart(String value) {
                this.sparePart = value;
            }

            /**
             * Gets the value of the serviceTracked property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getServiceTracked() {
                return serviceTracked;
            }

            /**
             * Sets the value of the serviceTracked property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setServiceTracked(String value) {
                this.serviceTracked = value;
            }

            /**
             * Gets the value of the serviceTrackCategorization property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getServiceTrackCategorization() {
                return serviceTrackCategorization;
            }

            /**
             * Sets the value of the serviceTrackCategorization property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setServiceTrackCategorization(String value) {
                this.serviceTrackCategorization = value;
            }

            /**
             * Gets the value of the longItemDescription property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the longItemDescription property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLongItemDescription().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ProcessItemDataRequestType.DataArea.ItemVAL.LongItemDescription }
             * 
             * 
             */
            public List<ProcessItemDataRequestType.DataArea.ItemVAL.LongItemDescription> getLongItemDescription() {
                if (longItemDescription == null) {
                    longItemDescription = new ArrayList<ProcessItemDataRequestType.DataArea.ItemVAL.LongItemDescription>();
                }
                return this.longItemDescription;
            }

            /**
             * Gets the value of the searchKey1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSearchKey1() {
                return searchKey1;
            }

            /**
             * Sets the value of the searchKey1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSearchKey1(String value) {
                this.searchKey1 = value;
            }

            /**
             * Gets the value of the searchKey2 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSearchKey2() {
                return searchKey2;
            }

            /**
             * Sets the value of the searchKey2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSearchKey2(String value) {
                this.searchKey2 = value;
            }

            /**
             * Gets the value of the itemTaxonomy property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getItemTaxonomy() {
                return itemTaxonomy;
            }

            /**
             * Sets the value of the itemTaxonomy property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setItemTaxonomy(String value) {
                this.itemTaxonomy = value;
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
             *       &lt;attribute name="action" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
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
            public static class AlternativeItem {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "action")
                protected String action;

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
                 * Gets the value of the action property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAction() {
                    return action;
                }

                /**
                 * Sets the value of the action property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAction(String value) {
                    this.action = value;
                }

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
             *       &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
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
            public static class Description {

                @XmlValue
                protected String value;
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
             *         &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcitem"/>
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
                "id"
            })
            public static class ItemID {

                @XmlElement(name = "ID", required = true)
                protected String id;

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
             *       &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str7" />
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
            public static class LongItemDescription {

                @XmlValue
                protected String value;
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


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str100" />
             *       &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.str10" />
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
             *         &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.cpcl" minOccurs="0"/>
             *         &lt;element name="classDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
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
                "classDescription"
            })
            public static class ProductClass {

                @XmlElement(name = "ID")
                protected String id;
                protected String classDescription;

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
                 * Gets the value of the classDescription property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getClassDescription() {
                    return classDescription;
                }

                /**
                 * Sets the value of the classDescription property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setClassDescription(String value) {
                    this.classDescription = value;
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
             *         &lt;element name="ID" type="{http://www.infor.com/businessinterface/Item_VAL}tcmcs.cpln" minOccurs="0"/>
             *         &lt;element name="lineDescription" type="{http://www.infor.com/businessinterface/Item_VAL}tcdsca" minOccurs="0"/>
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
                "lineDescription"
            })
            public static class ProductLine {

                @XmlElement(name = "ID")
                protected String id;
                protected String lineDescription;

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
                 * Gets the value of the lineDescription property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLineDescription() {
                    return lineDescription;
                }

                /**
                 * Sets the value of the lineDescription property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLineDescription(String value) {
                    this.lineDescription = value;
                }

            }

        }

    }

}
