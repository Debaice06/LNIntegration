package com.infor.businessinterface.multilevelbomdetails_val;

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
 * <p>
 * Java class for GetBOMDetailsResponseType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="GetBOMDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MultilevelBOMDetails_VAL" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="item" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem"/>
 *                             &lt;element name="itemRevision" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiedm.revi" minOccurs="0"/>
 *                             &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
 *                             &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccuni" minOccurs="0"/>
 *                             &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccitg" minOccurs="0"/>
 *                             &lt;element name="itemType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
 *                             &lt;element name="itemDescription" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="signalCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsig" minOccurs="0"/>
 *                             &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsel" minOccurs="0"/>
 *                             &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str100" />
 *                                     &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="productType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcctyp" minOccurs="0"/>
 *                             &lt;element name="BOMDetails" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="componentItem" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem" minOccurs="0"/>
 *                                       &lt;element name="componentRevision" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiedm.revi" minOccurs="0"/>
 *                                       &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
 *                                       &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccuni" minOccurs="0"/>
 *                                       &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccitg" minOccurs="0"/>
 *                                       &lt;element name="itemType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
 *                                       &lt;element name="componentItemDescription" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="position" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcpono" minOccurs="0"/>
 *                                       &lt;element name="length" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcleng" minOccurs="0"/>
 *                                       &lt;element name="width" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcwidt" minOccurs="0"/>
 *                                       &lt;element name="noOfUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tinoun" minOccurs="0"/>
 *                                       &lt;element name="netQuantity" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiqbm2" minOccurs="0"/>
 *                                       &lt;element name="signalCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsig" minOccurs="0"/>
 *                                       &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsel" minOccurs="0"/>
 *                                       &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="type" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str100" />
 *                                               &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="productType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcctyp" minOccurs="0"/>
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
 *         &lt;element name="InformationArea" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}InformationArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetBOMDetailsResponseType", propOrder = {
    "dataArea",
    "informationArea"
})
public class GetBOMDetailsResponseType {

    @XmlElement(name = "DataArea")
    protected GetBOMDetailsResponseType.DataArea dataArea;
    @XmlElement(name = "InformationArea")
    protected InformationArea informationArea;

    /**
     * Gets the value of the dataArea property.
     *
     * @return possible object is {@link GetBOMDetailsResponseType.DataArea }
     *
     */
    public GetBOMDetailsResponseType.DataArea getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     *
     * @param value allowed object is
     *     {@link GetBOMDetailsResponseType.DataArea }
     *
     */
    public void setDataArea(GetBOMDetailsResponseType.DataArea value) {
        this.dataArea = value;
    }

    /**
     * Gets the value of the informationArea property.
     *
     * @return possible object is {@link InformationArea }
     *
     */
    public InformationArea getInformationArea() {
        return informationArea;
    }

    /**
     * Sets the value of the informationArea property.
     *
     * @param value allowed object is {@link InformationArea }
     *
     */
    public void setInformationArea(InformationArea value) {
        this.informationArea = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="MultilevelBOMDetails_VAL" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="item" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem"/>
     *                   &lt;element name="itemRevision" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiedm.revi" minOccurs="0"/>
     *                   &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
     *                   &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccuni" minOccurs="0"/>
     *                   &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccitg" minOccurs="0"/>
     *                   &lt;element name="itemType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
     *                   &lt;element name="itemDescription" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="signalCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsig" minOccurs="0"/>
     *                   &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsel" minOccurs="0"/>
     *                   &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str100" />
     *                           &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="productType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcctyp" minOccurs="0"/>
     *                   &lt;element name="BOMDetails" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="componentItem" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem" minOccurs="0"/>
     *                             &lt;element name="componentRevision" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiedm.revi" minOccurs="0"/>
     *                             &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
     *                             &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccuni" minOccurs="0"/>
     *                             &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccitg" minOccurs="0"/>
     *                             &lt;element name="itemType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
     *                             &lt;element name="componentItemDescription" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="position" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcpono" minOccurs="0"/>
     *                             &lt;element name="length" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcleng" minOccurs="0"/>
     *                             &lt;element name="width" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcwidt" minOccurs="0"/>
     *                             &lt;element name="noOfUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tinoun" minOccurs="0"/>
     *                             &lt;element name="netQuantity" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiqbm2" minOccurs="0"/>
     *                             &lt;element name="signalCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsig" minOccurs="0"/>
     *                             &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsel" minOccurs="0"/>
     *                             &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="type" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str100" />
     *                                     &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
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
    @XmlType(name = "", propOrder = {
        "multilevelBOMDetailsVAL"
    })
    public static class DataArea {

        @XmlElement(name = "MultilevelBOMDetails_VAL")
        protected List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> multilevelBOMDetailsVAL;

        /**
         * Gets the value of the multilevelBOMDetailsVAL property.
         *
         * <p>
         * This accessor method returns a reference to the live list, not a
         * snapshot. Therefore any modification you make to the returned list
         * will be present inside the JAXB object. This is why there is not a
         * <CODE>set</CODE> method for the multilevelBOMDetailsVAL property.
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
         * {@link GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL }
         *
         *
         */
        public List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> getMultilevelBOMDetailsVAL() {
            if (multilevelBOMDetailsVAL == null) {
                multilevelBOMDetailsVAL = new ArrayList<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL>();
            }
            return this.multilevelBOMDetailsVAL;
        }

        /**
         * <p>
         * Java class for anonymous complex type.
         *
         * <p>
         * The following schema fragment specifies the expected content
         * contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="item" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem"/>
         *         &lt;element name="itemRevision" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiedm.revi" minOccurs="0"/>
         *         &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
         *         &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccuni" minOccurs="0"/>
         *         &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccitg" minOccurs="0"/>
         *         &lt;element name="itemType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
         *         &lt;element name="itemDescription" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="signalCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsig" minOccurs="0"/>
         *         &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsel" minOccurs="0"/>
         *         &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str100" />
         *                 &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="productType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcctyp" minOccurs="0"/>
         *         &lt;element name="BOMDetails" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="componentItem" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem" minOccurs="0"/>
         *                   &lt;element name="componentRevision" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiedm.revi" minOccurs="0"/>
         *                   &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
         *                   &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccuni" minOccurs="0"/>
         *                   &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccitg" minOccurs="0"/>
         *                   &lt;element name="itemType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
         *                   &lt;element name="componentItemDescription" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="position" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcpono" minOccurs="0"/>
         *                   &lt;element name="length" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcleng" minOccurs="0"/>
         *                   &lt;element name="width" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcwidt" minOccurs="0"/>
         *                   &lt;element name="noOfUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tinoun" minOccurs="0"/>
         *                   &lt;element name="netQuantity" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiqbm2" minOccurs="0"/>
         *                   &lt;element name="signalCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsig" minOccurs="0"/>
         *                   &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsel" minOccurs="0"/>
         *                   &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="type" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str100" />
         *                           &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
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
            "id",
            "item",
            "itemRevision",
            "drawingNumber",
            "inventoryUnit",
            "itemGroup",
            "itemType",
            "itemDescription",
            "signalCode",
            "selectionCode",
            "note",
            "productType",
            "bomDetails"
        })
        public static class MultilevelBOMDetailsVAL {

            @XmlElement(required = true)
            protected String id;
            protected String item;
            protected String itemRevision;
            protected String drawingNumber;
            protected String inventoryUnit;
            protected String itemGroup;
            protected String itemType;
            protected List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.ItemDescription> itemDescription;
            protected String signalCode;
            protected String selectionCode;
            @XmlElement(name = "Note")
            protected List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.Note> note;
            protected String productType;
            @XmlElement(name = "BOMDetails")
            protected List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails> bomDetails;

            /**
             * Gets the value of the item property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getId() {
                return id;
            }

            public void setId(String value) {
                this.id = value;
            }

            public String getItem() {
                return item;
            }

            /**
             * Sets the value of the item property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setItem(String value) {
                this.item = value;
            }

            /**
             * Gets the value of the itemRevision property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getItemRevision() {
                return itemRevision;
            }

            /**
             * Sets the value of the itemRevision property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setItemRevision(String value) {
                this.itemRevision = value;
            }

            /**
             * Gets the value of the drawingNumber property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getDrawingNumber() {
                return drawingNumber;
            }

            /**
             * Sets the value of the drawingNumber property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setDrawingNumber(String value) {
                this.drawingNumber = value;
            }

            /**
             * Gets the value of the inventoryUnit property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getInventoryUnit() {
                return inventoryUnit;
            }

            /**
             * Sets the value of the inventoryUnit property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setInventoryUnit(String value) {
                this.inventoryUnit = value;
            }

            /**
             * Gets the value of the itemGroup property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getItemGroup() {
                return itemGroup;
            }

            /**
             * Sets the value of the itemGroup property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setItemGroup(String value) {
                this.itemGroup = value;
            }

            /**
             * Gets the value of the itemType property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getItemType() {
                return itemType;
            }

            /**
             * Sets the value of the itemType property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setItemType(String value) {
                this.itemType = value;
            }

            /**
             * Gets the value of the itemDescription property.
             *
             * <p>
             * This accessor method returns a reference to the live list, not a
             * snapshot. Therefore any modification you make to the returned
             * list will be present inside the JAXB object. This is why there is
             * not a <CODE>set</CODE> method for the itemDescription property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getItemDescription().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.ItemDescription }
             *
             *
             */
            public List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.ItemDescription> getItemDescription() {
                if (itemDescription == null) {
                    itemDescription = new ArrayList<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.ItemDescription>();
                }
                return this.itemDescription;
            }

            /**
             * Gets the value of the signalCode property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getSignalCode() {
                return signalCode;
            }

            /**
             * Sets the value of the signalCode property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setSignalCode(String value) {
                this.signalCode = value;
            }

            /**
             * Gets the value of the selectionCode property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getSelectionCode() {
                return selectionCode;
            }

            /**
             * Sets the value of the selectionCode property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setSelectionCode(String value) {
                this.selectionCode = value;
            }

            /**
             * Gets the value of the note property.
             *
             * <p>
             * This accessor method returns a reference to the live list, not a
             * snapshot. Therefore any modification you make to the returned
             * list will be present inside the JAXB object. This is why there is
             * not a <CODE>set</CODE> method for the note property.
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
             * {@link GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.Note }
             *
             *
             */
            public List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.Note> getNote() {
                if (note == null) {
                    note = new ArrayList<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.Note>();
                }
                return this.note;
            }

            /**
             * Gets the value of the productType property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getProductType() {
                return productType;
            }

            /**
             * Sets the value of the productType property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setProductType(String value) {
                this.productType = value;
            }

            /**
             * Gets the value of the bomDetails property.
             *
             * <p>
             * This accessor method returns a reference to the live list, not a
             * snapshot. Therefore any modification you make to the returned
             * list will be present inside the JAXB object. This is why there is
             * not a <CODE>set</CODE> method for the bomDetails property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBOMDetails().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails }
             *
             *
             */
            public List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails> getBOMDetails() {
                if (bomDetails == null) {
                    bomDetails = new ArrayList<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails>();
                }
                return this.bomDetails;
            }

            /**
             * <p>
             * Java class for anonymous complex type.
             *
             * <p>
             * The following schema fragment specifies the expected content
             * contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="componentItem" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcitem" minOccurs="0"/>
             *         &lt;element name="componentRevision" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiedm.revi" minOccurs="0"/>
             *         &lt;element name="drawingNumber" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
             *         &lt;element name="inventoryUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccuni" minOccurs="0"/>
             *         &lt;element name="itemGroup" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccitg" minOccurs="0"/>
             *         &lt;element name="itemType" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" minOccurs="0"/>
             *         &lt;element name="componentItemDescription" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" />
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="position" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcpono" minOccurs="0"/>
             *         &lt;element name="length" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcleng" minOccurs="0"/>
             *         &lt;element name="width" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcwidt" minOccurs="0"/>
             *         &lt;element name="noOfUnit" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tinoun" minOccurs="0"/>
             *         &lt;element name="netQuantity" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tiqbm2" minOccurs="0"/>
             *         &lt;element name="signalCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsig" minOccurs="0"/>
             *         &lt;element name="selectionCode" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tccsel" minOccurs="0"/>
             *         &lt;element name="Note" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="type" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str100" />
             *                 &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" />
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
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
                "componentItem",
                "componentRevision",
                "drawingNumber",
                "inventoryUnit",
                "itemGroup",
                "itemType",
                "componentItemDescription",
                "position",
                "length",
                "width",
                "noOfUnit",
                "netQuantity",
                "signalCode",
                "selectionCode",
                "note",
                "productType"
            })
            public static class BOMDetails {

                protected String componentItem;
                protected String componentRevision;
                protected String drawingNumber;
                protected String inventoryUnit;
                protected String itemGroup;
                protected String itemType;
                protected List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails.ComponentItemDescription> componentItemDescription;
                protected BigInteger position;
                protected BigDecimal length;
                protected BigDecimal width;
                protected Long noOfUnit;
                protected BigDecimal netQuantity;
                protected String signalCode;
                protected String selectionCode;
                @XmlElement(name = "Note")
                protected List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails.Note> note;
                protected String productType;

                /**
                 * Gets the value of the componentItem property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getComponentItem() {
                    return componentItem;
                }

                /**
                 * Sets the value of the componentItem property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setComponentItem(String value) {
                    this.componentItem = value;
                }

                /**
                 * Gets the value of the componentRevision property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getComponentRevision() {
                    return componentRevision;
                }

                /**
                 * Sets the value of the componentRevision property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setComponentRevision(String value) {
                    this.componentRevision = value;
                }

                /**
                 * Gets the value of the drawingNumber property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getDrawingNumber() {
                    return drawingNumber;
                }

                /**
                 * Sets the value of the drawingNumber property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setDrawingNumber(String value) {
                    this.drawingNumber = value;
                }

                /**
                 * Gets the value of the inventoryUnit property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getInventoryUnit() {
                    return inventoryUnit;
                }

                /**
                 * Sets the value of the inventoryUnit property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setInventoryUnit(String value) {
                    this.inventoryUnit = value;
                }

                /**
                 * Gets the value of the itemGroup property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getItemGroup() {
                    return itemGroup;
                }

                /**
                 * Sets the value of the itemGroup property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setItemGroup(String value) {
                    this.itemGroup = value;
                }

                /**
                 * Gets the value of the itemType property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getItemType() {
                    return itemType;
                }

                /**
                 * Sets the value of the itemType property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setItemType(String value) {
                    this.itemType = value;
                }

                /**
                 * Gets the value of the componentItemDescription property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the
                 * componentItemDescription property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getComponentItemDescription().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails.ComponentItemDescription }
                 *
                 *
                 */
                public List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails.ComponentItemDescription> getComponentItemDescription() {
                    if (componentItemDescription == null) {
                        componentItemDescription = new ArrayList<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails.ComponentItemDescription>();
                    }
                    return this.componentItemDescription;
                }

                /**
                 * Gets the value of the position property.
                 *
                 * @return possible object is {@link BigInteger }
                 *
                 */
                public BigInteger getPosition() {
                    return position;
                }

                /**
                 * Sets the value of the position property.
                 *
                 * @param value allowed object is {@link BigInteger }
                 *
                 */
                public void setPosition(BigInteger value) {
                    this.position = value;
                }

                /**
                 * Gets the value of the length property.
                 *
                 * @return possible object is {@link BigDecimal }
                 *
                 */
                public BigDecimal getLength() {
                    return length;
                }

                /**
                 * Sets the value of the length property.
                 *
                 * @param value allowed object is {@link BigDecimal }
                 *
                 */
                public void setLength(BigDecimal value) {
                    this.length = value;
                }

                /**
                 * Gets the value of the width property.
                 *
                 * @return possible object is {@link BigDecimal }
                 *
                 */
                public BigDecimal getWidth() {
                    return width;
                }

                /**
                 * Sets the value of the width property.
                 *
                 * @param value allowed object is {@link BigDecimal }
                 *
                 */
                public void setWidth(BigDecimal value) {
                    this.width = value;
                }

                /**
                 * Gets the value of the noOfUnit property.
                 *
                 * @return possible object is {@link Long }
                 *
                 */
                public Long getNoOfUnit() {
                    return noOfUnit;
                }

                /**
                 * Sets the value of the noOfUnit property.
                 *
                 * @param value allowed object is {@link Long }
                 *
                 */
                public void setNoOfUnit(Long value) {
                    this.noOfUnit = value;
                }

                /**
                 * Gets the value of the netQuantity property.
                 *
                 * @return possible object is {@link BigDecimal }
                 *
                 */
                public BigDecimal getNetQuantity() {
                    return netQuantity;
                }

                /**
                 * Sets the value of the netQuantity property.
                 *
                 * @param value allowed object is {@link BigDecimal }
                 *
                 */
                public void setNetQuantity(BigDecimal value) {
                    this.netQuantity = value;
                }

                /**
                 * Gets the value of the signalCode property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getSignalCode() {
                    return signalCode;
                }

                /**
                 * Sets the value of the signalCode property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setSignalCode(String value) {
                    this.signalCode = value;
                }

                /**
                 * Gets the value of the selectionCode property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getSelectionCode() {
                    return selectionCode;
                }

                /**
                 * Sets the value of the selectionCode property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setSelectionCode(String value) {
                    this.selectionCode = value;
                }

                /**
                 * Gets the value of the note property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the note
                 * property.
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
                 * {@link GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails.Note }
                 *
                 *
                 */
                public List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails.Note> getNote() {
                    if (note == null) {
                        note = new ArrayList<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails.Note>();
                    }
                    return this.note;
                }

                /**
                 * Gets the value of the productType property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getProductType() {
                    return productType;
                }

                /**
                 * Sets the value of the productType property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setProductType(String value) {
                    this.productType = value;
                }

                /**
                 * <p>
                 * Java class for anonymous complex type.
                 *
                 * <p>
                 * The following schema fragment specifies the expected content
                 * contained within this class.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" />
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
                public static class ComponentItemDescription {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "languageID")
                    protected String languageID;

                    /**
                     * Gets the value of the value property.
                     *
                     * @return possible object is {@link String }
                     *
                     */
                    public String getValue() {
                        return value;
                    }

                    /**
                     * Sets the value of the value property.
                     *
                     * @param value allowed object is {@link String }
                     *
                     */
                    public void setValue(String value) {
                        this.value = value;
                    }

                    /**
                     * Gets the value of the languageID property.
                     *
                     * @return possible object is {@link String }
                     *
                     */
                    public String getLanguageID() {
                        return languageID;
                    }

                    /**
                     * Sets the value of the languageID property.
                     *
                     * @param value allowed object is {@link String }
                     *
                     */
                    public void setLanguageID(String value) {
                        this.languageID = value;
                    }

                }

                /**
                 * <p>
                 * Java class for anonymous complex type.
                 *
                 * <p>
                 * The following schema fragment specifies the expected content
                 * contained within this class.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="type" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str100" />
                 *       &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" />
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
                     * @return possible object is {@link String }
                     *
                     */
                    public String getValue() {
                        return value;
                    }

                    /**
                     * Sets the value of the value property.
                     *
                     * @param value allowed object is {@link String }
                     *
                     */
                    public void setValue(String value) {
                        this.value = value;
                    }

                    /**
                     * Gets the value of the type property.
                     *
                     * @return possible object is {@link String }
                     *
                     */
                    public String getType() {
                        return type;
                    }

                    /**
                     * Sets the value of the type property.
                     *
                     * @param value allowed object is {@link String }
                     *
                     */
                    public void setType(String value) {
                        this.type = value;
                    }

                    /**
                     * Gets the value of the languageID property.
                     *
                     * @return possible object is {@link String }
                     *
                     */
                    public String getLanguageID() {
                        return languageID;
                    }

                    /**
                     * Sets the value of the languageID property.
                     *
                     * @param value allowed object is {@link String }
                     *
                     */
                    public void setLanguageID(String value) {
                        this.languageID = value;
                    }

                }

            }

            /**
             * <p>
             * Java class for anonymous complex type.
             *
             * <p>
             * The following schema fragment specifies the expected content
             * contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str25" />
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
            public static class ItemDescription {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "languageID")
                protected String languageID;

                /**
                 * Gets the value of the value property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the languageID property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getLanguageID() {
                    return languageID;
                }

                /**
                 * Sets the value of the languageID property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setLanguageID(String value) {
                    this.languageID = value;
                }

            }

            /**
             * <p>
             * Java class for anonymous complex type.
             *
             * <p>
             * The following schema fragment specifies the expected content
             * contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str100" />
             *       &lt;attribute name="languageID" type="{http://www.infor.com/businessinterface/MultilevelBOMDetails_VAL}tcmcs.str30" />
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
                 * @return possible object is {@link String }
                 *
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the type property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Sets the value of the type property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Gets the value of the languageID property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getLanguageID() {
                    return languageID;
                }

                /**
                 * Sets the value of the languageID property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setLanguageID(String value) {
                    this.languageID = value;
                }

            }

        }

    }

}
