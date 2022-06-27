
package com.infor.businessinterface.item_val;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Message complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Message">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageIndex" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="messageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="messageType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Error"/>
 *               &lt;enumeration value="Warning"/>
 *               &lt;enumeration value="Information"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="messageText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="messageSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MessageDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Message" type="{http://www.infor.com/businessinterface/Item_VAL}DetailMessage" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="messageCorrectiveAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="messageAdditionalHelpText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;element name="messageAdditionalHelpURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="MessageReference" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="messageReferenceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="messageReferenceInfo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
@XmlType(name = "Message", propOrder = {
    "messageIndex",
    "messageCode",
    "messageType",
    "messageText",
    "messageSource",
    "messageDetails",
    "messageCorrectiveAction",
    "messageAdditionalHelpText",
    "messageAdditionalHelpURI",
    "messageReference"
})
@XmlSeeAlso({
    InformationMessage.class,
    DetailMessage.class
})
public class Message {

    protected BigInteger messageIndex;
    @XmlElement(required = true)
    protected String messageCode;
    @XmlElement(required = true)
    protected String messageType;
    @XmlElement(required = true)
    protected String messageText;
    @XmlElement(required = true)
    protected String messageSource;
    @XmlElement(name = "MessageDetails")
    protected Message.MessageDetails messageDetails;
    protected String messageCorrectiveAction;
    protected String messageAdditionalHelpText;
    @XmlSchemaType(name = "anyURI")
    protected String messageAdditionalHelpURI;
    @XmlElement(name = "MessageReference")
    protected Message.MessageReference messageReference;

    /**
     * Gets the value of the messageIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMessageIndex() {
        return messageIndex;
    }

    /**
     * Sets the value of the messageIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMessageIndex(BigInteger value) {
        this.messageIndex = value;
    }

    /**
     * Gets the value of the messageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * Sets the value of the messageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageCode(String value) {
        this.messageCode = value;
    }

    /**
     * Gets the value of the messageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Sets the value of the messageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageType(String value) {
        this.messageType = value;
    }

    /**
     * Gets the value of the messageText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * Sets the value of the messageText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageText(String value) {
        this.messageText = value;
    }

    /**
     * Gets the value of the messageSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageSource() {
        return messageSource;
    }

    /**
     * Sets the value of the messageSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageSource(String value) {
        this.messageSource = value;
    }

    /**
     * Gets the value of the messageDetails property.
     * 
     * @return
     *     possible object is
     *     {@link Message.MessageDetails }
     *     
     */
    public Message.MessageDetails getMessageDetails() {
        return messageDetails;
    }

    /**
     * Sets the value of the messageDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Message.MessageDetails }
     *     
     */
    public void setMessageDetails(Message.MessageDetails value) {
        this.messageDetails = value;
    }

    /**
     * Gets the value of the messageCorrectiveAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageCorrectiveAction() {
        return messageCorrectiveAction;
    }

    /**
     * Sets the value of the messageCorrectiveAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageCorrectiveAction(String value) {
        this.messageCorrectiveAction = value;
    }

    /**
     * Gets the value of the messageAdditionalHelpText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageAdditionalHelpText() {
        return messageAdditionalHelpText;
    }

    /**
     * Sets the value of the messageAdditionalHelpText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageAdditionalHelpText(String value) {
        this.messageAdditionalHelpText = value;
    }

    /**
     * Gets the value of the messageAdditionalHelpURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageAdditionalHelpURI() {
        return messageAdditionalHelpURI;
    }

    /**
     * Sets the value of the messageAdditionalHelpURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageAdditionalHelpURI(String value) {
        this.messageAdditionalHelpURI = value;
    }

    /**
     * Gets the value of the messageReference property.
     * 
     * @return
     *     possible object is
     *     {@link Message.MessageReference }
     *     
     */
    public Message.MessageReference getMessageReference() {
        return messageReference;
    }

    /**
     * Sets the value of the messageReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link Message.MessageReference }
     *     
     */
    public void setMessageReference(Message.MessageReference value) {
        this.messageReference = value;
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
     *         &lt;element name="Message" type="{http://www.infor.com/businessinterface/Item_VAL}DetailMessage" maxOccurs="unbounded"/>
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
        "message"
    })
    public static class MessageDetails {

        @XmlElement(name = "Message", required = true)
        protected List<DetailMessage> message;

        /**
         * Gets the value of the message property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the message property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMessage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DetailMessage }
         * 
         * 
         */
        public List<DetailMessage> getMessage() {
            if (message == null) {
                message = new ArrayList<DetailMessage>();
            }
            return this.message;
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
     *         &lt;element name="messageReferenceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="messageReferenceInfo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
        "messageReferenceType",
        "messageReferenceInfo"
    })
    public static class MessageReference {

        @XmlElement(required = true)
        protected String messageReferenceType;
        @XmlElement(required = true)
        protected Object messageReferenceInfo;

        /**
         * Gets the value of the messageReferenceType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageReferenceType() {
            return messageReferenceType;
        }

        /**
         * Sets the value of the messageReferenceType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageReferenceType(String value) {
            this.messageReferenceType = value;
        }

        /**
         * Gets the value of the messageReferenceInfo property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getMessageReferenceInfo() {
            return messageReferenceInfo;
        }

        /**
         * Sets the value of the messageReferenceInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setMessageReferenceInfo(Object value) {
            this.messageReferenceInfo = value;
        }

    }

}
