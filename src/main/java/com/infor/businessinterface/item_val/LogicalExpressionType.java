
package com.infor.businessinterface.item_val;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LogicalExpressionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LogicalExpressionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="logicalOperator">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="or"/>
 *               &lt;enumeration value="and"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;choice maxOccurs="unbounded" minOccurs="2">
 *           &lt;element name="LogicalExpression" type="{http://www.infor.com/businessinterface/Item_VAL}LogicalExpressionType" maxOccurs="unbounded"/>
 *           &lt;element name="ComparisonExpression" type="{http://www.infor.com/businessinterface/Item_VAL}ComparisonExpressionType" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogicalExpressionType", propOrder = {
    "logicalOperator",
    "logicalExpressionOrComparisonExpression"
})
public class LogicalExpressionType {

    @XmlElement(required = true)
    protected String logicalOperator;
    @XmlElements({
        @XmlElement(name = "LogicalExpression", type = LogicalExpressionType.class),
        @XmlElement(name = "ComparisonExpression", type = ComparisonExpressionType.class)
    })
    protected List<Object> logicalExpressionOrComparisonExpression;

    /**
     * Gets the value of the logicalOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogicalOperator() {
        return logicalOperator;
    }

    /**
     * Sets the value of the logicalOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogicalOperator(String value) {
        this.logicalOperator = value;
    }

    /**
     * Gets the value of the logicalExpressionOrComparisonExpression property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logicalExpressionOrComparisonExpression property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogicalExpressionOrComparisonExpression().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LogicalExpressionType }
     * {@link ComparisonExpressionType }
     * 
     * 
     */
    public List<Object> getLogicalExpressionOrComparisonExpression() {
        if (logicalExpressionOrComparisonExpression == null) {
            logicalExpressionOrComparisonExpression = new ArrayList<Object>();
        }
        return this.logicalExpressionOrComparisonExpression;
    }

}
