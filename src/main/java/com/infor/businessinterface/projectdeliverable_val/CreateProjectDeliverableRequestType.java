
package com.infor.businessinterface.projectdeliverable_val;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createProjectDeliverableRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createProjectDeliverableRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ControlArea" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}processingScope" minOccurs="0"/>
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
 *                             &lt;element name="quantity" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcqiv1" minOccurs="0"/>
 *                             &lt;element name="milestone" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tppdm.cact" minOccurs="0"/>
 *                             &lt;element name="userID" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcemno" minOccurs="0"/>
 *                             &lt;element name="contractDeliverable" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcmcs.str50" minOccurs="0"/>
 *                             &lt;element name="deleteContractDeliverable" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcmcs.str16" minOccurs="0"/>
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
@XmlType(name = "createProjectDeliverableRequestType", propOrder = {
    "controlArea",
    "dataArea"
})
public class CreateProjectDeliverableRequestType {

    @XmlElement(name = "ControlArea")
    protected CreateProjectDeliverableRequestType.ControlArea controlArea;
    @XmlElement(name = "DataArea")
    protected CreateProjectDeliverableRequestType.DataArea dataArea;

    /**
     * Gets the value of the controlArea property.
     * 
     * @return
     *     possible object is
     *     {@link CreateProjectDeliverableRequestType.ControlArea }
     *     
     */
    public CreateProjectDeliverableRequestType.ControlArea getControlArea() {
        return controlArea;
    }

    /**
     * Sets the value of the controlArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateProjectDeliverableRequestType.ControlArea }
     *     
     */
    public void setControlArea(CreateProjectDeliverableRequestType.ControlArea value) {
        this.controlArea = value;
    }

    /**
     * Gets the value of the dataArea property.
     * 
     * @return
     *     possible object is
     *     {@link CreateProjectDeliverableRequestType.DataArea }
     *     
     */
    public CreateProjectDeliverableRequestType.DataArea getDataArea() {
        return dataArea;
    }

    /**
     * Sets the value of the dataArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateProjectDeliverableRequestType.DataArea }
     *     
     */
    public void setDataArea(CreateProjectDeliverableRequestType.DataArea value) {
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
     *         &lt;element name="processingScope" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}processingScope" minOccurs="0"/>
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
     *                   &lt;element name="quantity" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcqiv1" minOccurs="0"/>
     *                   &lt;element name="milestone" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tppdm.cact" minOccurs="0"/>
     *                   &lt;element name="userID" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcemno" minOccurs="0"/>
     *                   &lt;element name="contractDeliverable" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcmcs.str50" minOccurs="0"/>
     *                   &lt;element name="deleteContractDeliverable" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcmcs.str16" minOccurs="0"/>
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
        "projectDeliverableVAL"
    })
    public static class DataArea {

        @XmlElement(name = "MessageID", required = true)
        protected Object messageID;
        @XmlElement(name = "Receiver", required = true)
        protected Object receiver;
        @XmlElement(name = "ProjectDeliverable_VAL")
        protected List<CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL> projectDeliverableVAL;

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
         * {@link CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL }
         * 
         * 
         */
        public List<CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL> getProjectDeliverableVAL() {
            if (projectDeliverableVAL == null) {
                projectDeliverableVAL = new ArrayList<CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL>();
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
         *         &lt;element name="quantity" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcqiv1" minOccurs="0"/>
         *         &lt;element name="milestone" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tppdm.cact" minOccurs="0"/>
         *         &lt;element name="userID" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcemno" minOccurs="0"/>
         *         &lt;element name="contractDeliverable" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcmcs.str50" minOccurs="0"/>
         *         &lt;element name="deleteContractDeliverable" type="{http://www.infor.com/businessinterface/ProjectDeliverable_VAL}tcmcs.str16" minOccurs="0"/>
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
            "quantity",
            "milestone",
            "userID",
            "contractDeliverable",
            "deleteContractDeliverable"
        })
        public static class ProjectDeliverableVAL {

            protected CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID itemID;
            protected String revision;
            protected String contractProject;
            protected String activity;
            protected BigDecimal quantity;
            protected String milestone;
            protected String userID;
            protected String contractDeliverable;
            protected String deleteContractDeliverable;

            /**
             * Gets the value of the itemID property.
             * 
             * @return
             *     possible object is
             *     {@link CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID }
             *     
             */
            public CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID getItemID() {
                return itemID;
            }

            /**
             * Sets the value of the itemID property.
             * 
             * @param value
             *     allowed object is
             *     {@link CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID }
             *     
             */
            public void setItemID(CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL.ItemID value) {
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

            /**
             * Gets the value of the milestone property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMilestone() {
                return milestone;
            }

            /**
             * Sets the value of the milestone property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMilestone(String value) {
                this.milestone = value;
            }

            /**
             * Gets the value of the userID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUserID() {
                return userID;
            }

            /**
             * Sets the value of the userID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUserID(String value) {
                this.userID = value;
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
             * Gets the value of the deleteContractDeliverable property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDeleteContractDeliverable() {
                return deleteContractDeliverable;
            }

            /**
             * Sets the value of the deleteContractDeliverable property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDeleteContractDeliverable(String value) {
                this.deleteContractDeliverable = value;
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
