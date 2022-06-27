
package com.infor.businessinterface.disassemblylines_val;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.infor.businessinterface.disassemblylines_val package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Result_QNAME = new QName("http://www.infor.com/businessinterface/DisAssemblyLines_VAL", "Result");
    private final static QName _Activation_QNAME = new QName("http://www.infor.com/businessinterface/DisAssemblyLines_VAL", "Activation");
    private final static QName _ComparisonExpressionTypeAttributeName_QNAME = new QName("", "attributeName");
    private final static QName _ComparisonExpressionTypeInstanceValue_QNAME = new QName("", "instanceValue");
    private final static QName _ComparisonExpressionTypeEmptyValue_QNAME = new QName("", "emptyValue");
    private final static QName _ComparisonExpressionTypeComparisonOperator_QNAME = new QName("", "comparisonOperator");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.infor.businessinterface.disassemblylines_val
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesRequestType }
     * 
     */
    public ProcessDisAssemblyLinesRequestType createProcessDisAssemblyLinesRequestType() {
        return new ProcessDisAssemblyLinesRequestType();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesRequestType.DataArea }
     * 
     */
    public ProcessDisAssemblyLinesRequestType.DataArea createProcessDisAssemblyLinesRequestTypeDataArea() {
        return new ProcessDisAssemblyLinesRequestType.DataArea();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL }
     * 
     */
    public ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL createProcessDisAssemblyLinesRequestTypeDataAreaDisAssemblyLinesVAL() {
        return new ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails }
     * 
     */
    public ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails createProcessDisAssemblyLinesRequestTypeDataAreaDisAssemblyLinesVALItemDetails() {
        return new ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesResponseType }
     * 
     */
    public ProcessDisAssemblyLinesResponseType createProcessDisAssemblyLinesResponseType() {
        return new ProcessDisAssemblyLinesResponseType();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesResponseType.DataArea }
     * 
     */
    public ProcessDisAssemblyLinesResponseType.DataArea createProcessDisAssemblyLinesResponseTypeDataArea() {
        return new ProcessDisAssemblyLinesResponseType.DataArea();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL }
     * 
     */
    public ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL createProcessDisAssemblyLinesResponseTypeDataAreaDisAssemblyLinesVAL() {
        return new ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL();
    }

    /**
     * Create an instance of {@link ActivationType }
     * 
     */
    public ActivationType createActivationType() {
        return new ActivationType();
    }

    /**
     * Create an instance of {@link ComparisonExpressionType }
     * 
     */
    public ComparisonExpressionType createComparisonExpressionType() {
        return new ComparisonExpressionType();
    }

    /**
     * Create an instance of {@link InformationArea }
     * 
     */
    public InformationArea createInformationArea() {
        return new InformationArea();
    }

    /**
     * Create an instance of {@link SelectionType }
     * 
     */
    public SelectionType createSelectionType() {
        return new SelectionType();
    }

    /**
     * Create an instance of {@link DetailMessage }
     * 
     */
    public DetailMessage createDetailMessage() {
        return new DetailMessage();
    }

    /**
     * Create an instance of {@link FilterType }
     * 
     */
    public FilterType createFilterType() {
        return new FilterType();
    }

    /**
     * Create an instance of {@link LogicalExpressionType }
     * 
     */
    public LogicalExpressionType createLogicalExpressionType() {
        return new LogicalExpressionType();
    }

    /**
     * Create an instance of {@link InformationMessage }
     * 
     */
    public InformationMessage createInformationMessage() {
        return new InformationMessage();
    }

    /**
     * Create an instance of {@link EmptyValueType }
     * 
     */
    public EmptyValueType createEmptyValueType() {
        return new EmptyValueType();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesRequestType.ControlArea }
     * 
     */
    public ProcessDisAssemblyLinesRequestType.ControlArea createProcessDisAssemblyLinesRequestTypeControlArea() {
        return new ProcessDisAssemblyLinesRequestType.ControlArea();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ContractDetails }
     * 
     */
    public ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ContractDetails createProcessDisAssemblyLinesRequestTypeDataAreaDisAssemblyLinesVALContractDetails() {
        return new ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ContractDetails();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails.Lines }
     * 
     */
    public ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails.Lines createProcessDisAssemblyLinesRequestTypeDataAreaDisAssemblyLinesVALItemDetailsLines() {
        return new ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL.ItemDetails.Lines();
    }

    /**
     * Create an instance of {@link Message.MessageDetails }
     * 
     */
    public Message.MessageDetails createMessageMessageDetails() {
        return new Message.MessageDetails();
    }

    /**
     * Create an instance of {@link Message.MessageReference }
     * 
     */
    public Message.MessageReference createMessageMessageReference() {
        return new Message.MessageReference();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ContractDetails }
     * 
     */
    public ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ContractDetails createProcessDisAssemblyLinesResponseTypeDataAreaDisAssemblyLinesVALContractDetails() {
        return new ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ContractDetails();
    }

    /**
     * Create an instance of {@link ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ItemDetails }
     * 
     */
    public ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ItemDetails createProcessDisAssemblyLinesResponseTypeDataAreaDisAssemblyLinesVALItemDetails() {
        return new ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ItemDetails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Message }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.infor.com/businessinterface/DisAssemblyLines_VAL", name = "Result")
    public JAXBElement<Message> createResult(Message value) {
        return new JAXBElement<Message>(_Result_QNAME, Message.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActivationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.infor.com/businessinterface/DisAssemblyLines_VAL", name = "Activation")
    public JAXBElement<ActivationType> createActivation(ActivationType value) {
        return new JAXBElement<ActivationType>(_Activation_QNAME, ActivationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FilterAttributeListDT }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "attributeName", scope = ComparisonExpressionType.class)
    public JAXBElement<FilterAttributeListDT> createComparisonExpressionTypeAttributeName(FilterAttributeListDT value) {
        return new JAXBElement<FilterAttributeListDT>(_ComparisonExpressionTypeAttributeName_QNAME, FilterAttributeListDT.class, ComparisonExpressionType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "instanceValue", scope = ComparisonExpressionType.class)
    public JAXBElement<String> createComparisonExpressionTypeInstanceValue(String value) {
        return new JAXBElement<String>(_ComparisonExpressionTypeInstanceValue_QNAME, String.class, ComparisonExpressionType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "emptyValue", scope = ComparisonExpressionType.class)
    public JAXBElement<EmptyValueType> createComparisonExpressionTypeEmptyValue(EmptyValueType value) {
        return new JAXBElement<EmptyValueType>(_ComparisonExpressionTypeEmptyValue_QNAME, EmptyValueType.class, ComparisonExpressionType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "comparisonOperator", scope = ComparisonExpressionType.class)
    public JAXBElement<String> createComparisonExpressionTypeComparisonOperator(String value) {
        return new JAXBElement<String>(_ComparisonExpressionTypeComparisonOperator_QNAME, String.class, ComparisonExpressionType.class, value);
    }

}
