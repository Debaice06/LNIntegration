
package com.infor.businessinterface.billofmaterial_val;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="LogicalExpression" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}LogicalExpressionType"/>
 *         &lt;element name="ComparisonExpression" type="{http://www.infor.com/businessinterface/BillOfMaterial_VAL}ComparisonExpressionType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterType", propOrder = {
    "logicalExpression",
    "comparisonExpression"
})
public class FilterType {

    @XmlElement(name = "LogicalExpression")
    protected LogicalExpressionType logicalExpression;
    @XmlElement(name = "ComparisonExpression")
    protected ComparisonExpressionType comparisonExpression;

    /**
     * Gets the value of the logicalExpression property.
     * 
     * @return
     *     possible object is
     *     {@link LogicalExpressionType }
     *     
     */
    public LogicalExpressionType getLogicalExpression() {
        return logicalExpression;
    }

    /**
     * Sets the value of the logicalExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicalExpressionType }
     *     
     */
    public void setLogicalExpression(LogicalExpressionType value) {
        this.logicalExpression = value;
    }

    /**
     * Gets the value of the comparisonExpression property.
     * 
     * @return
     *     possible object is
     *     {@link ComparisonExpressionType }
     *     
     */
    public ComparisonExpressionType getComparisonExpression() {
        return comparisonExpression;
    }

    /**
     * Sets the value of the comparisonExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComparisonExpressionType }
     *     
     */
    public void setComparisonExpression(ComparisonExpressionType value) {
        this.comparisonExpression = value;
    }

}
