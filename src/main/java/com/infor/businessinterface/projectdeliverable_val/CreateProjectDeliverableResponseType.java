
package com.infor.businessinterface.projectdeliverable_val;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createProjectDeliverableResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createProjectDeliverableResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ProjectDeliverable_VAL" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="itemID" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ID" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcitem"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="revision" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcibd.revi" minOccurs="0"/>
 *                             &lt;element name="contractProject" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tccprj" minOccurs="0"/>
 *                             &lt;element name="activity" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tppdm.cact" minOccurs="0"/>
 *                             &lt;element name="contractDeliverable" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcmcs.str50" minOccurs="0"/>
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
 *         &lt;element name="InformationArea" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}InformationArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createProjectDeliverableResponseType", propOrder = {
    "dataArea",
    "informationArea"
})
public class CreateProjectDeliverableResponseType {

    @XmlElement(name = "DataArea")
    protected CreateProjectDeliverableResponseType.DataArea dataArea;
    @XmlElement(name = "InformationArea")
    protected InformationArea informationArea;

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link CreateProjectDeliverableResponseType.DataArea }
     *     
     */
    public CreateProjectDeliverableResponseType.DataArea getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateProjectDeliverableResponseType.DataArea }
     *     
     */
    public void setDataArea(CreateProjectDeliverableResponseType.DataArea value) {
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
     *         &lt;element name="ProjectDeliverable_VAL" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="itemID" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ID" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcitem"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="revision" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcibd.revi" minOccurs="0"/>
     *                   &lt;element name="contractProject" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tccprj" minOccurs="0"/>
     *                   &lt;element name="activity" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tppdm.cact" minOccurs="0"/>
     *                   &lt;element name="contractDeliverable" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcmcs.str50" minOccurs="0"/>
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
        "projectDeliverableVAL"
    })
    public static class DataArea {

        @XmlElement(name = "ProjectDeliverable_VAL")
        protected List<CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL> projectDeliverableVAL;

        /**
         * Gets the value of the projectDeliverableVAL property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the projectDeliverableVAL property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProjectDeliverableVAL().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL }
         * 
         * 
         */
        public List<CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL> getProjectDeliverableVAL() {
            if (projectDeliverableVAL == null) {
                projectDeliverableVAL = new ArrayList<CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL>();
            }
            return this.projectDeliverableVAL;
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
         *         &lt;element name="itemID" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ID" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcitem"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="revision" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcibd.revi" minOccurs="0"/>
         *         &lt;element name="contractProject" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tccprj" minOccurs="0"/>
         *         &lt;element name="activity" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tppdm.cact" minOccurs="0"/>
         *         &lt;element name="contractDeliverable" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcmcs.str50" minOccurs="0"/>
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
            "revision",
            "contractProject",
            "activity",
            "contractDeliverable"
        })
        public static class ProjectDeliverableVAL {

            protected CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL.ItemID itemID;
            protected String revision;
            protected String contractProject;
            protected String activity;
            protected String contractDeliverable;

            /**
             * Gets the value of the itemID property.
             * 
             * @return
             *     possible object is
             *     {@link CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL.ItemID }
             *     
             */
            public CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL.ItemID getItemID() {
                return itemID;
            }

            /**
             * Sets the value of the itemID property.
             * 
             * @param value
             *     allowed object is
             *     {@link CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL.ItemID }
             *     
             */
            public void setItemID(CreateProjectDeliverableResponseType.DataArea.ProjectDeliverableVAL.ItemID value) {
                this.itemID = value;
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
             * Gets the value of the contractProject property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getContractProject() {
                return contractProject;
            }

            /**
             * Sets the value of the contractProject property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setContractProject(String value) {
                this.contractProject = value;
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
             * Gets the value of the contractDeliverable property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getContractDeliverable() {
                return contractDeliverable;
            }

            /**
             * Sets the value of the contractDeliverable property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setContractDeliverable(String value) {
                this.contractDeliverable = value;
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
             *         &lt;element name="ID" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcitem"/>
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

        }

    }

}
