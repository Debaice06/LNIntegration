/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.ws.soap;

import com.infor.businessinterface.item_val.ActivationType;
import com.infor.businessinterface.item_val.ObjectFactory;
import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import com.bjit.ex.integration.transfer.util.LoggerProcessUtil;

/**
 *
 * @author Sajjad
 */
public class LNSoapHandler
        implements SOAPHandler<SOAPMessageContext> {

    private static final org.apache.log4j.Logger LN_SOAP_HANDLER_LOGGER = org.apache.log4j.Logger.getLogger(LNSoapHandler.class);
    private static final String SOAP_PREFIX = "soapenv";
    private ActivationType activationType;

    public LNSoapHandler(ActivationType activationType) {
        this.activationType = activationType;
    }

    public void close(MessageContext arg0) {
    }

    public boolean handleFault(SOAPMessageContext context) {
        logToSystemOut(context);
        return true;
    }

    public boolean handleMessage(SOAPMessageContext context) {
        System.out.println("IN message context!!");
        Boolean isSoapRequest = (Boolean) context.get("javax.xml.ws.handler.message.outbound");
        if (isSoapRequest.booleanValue()) {
            try {
                SOAPMessage soapMsg = context.getMessage();
                SOAPEnvelope env = soapMsg.getSOAPPart().getEnvelope();
                env.setPrefix(SOAP_PREFIX);
                SOAPBody body = soapMsg.getSOAPBody();
                body.setPrefix(SOAP_PREFIX);

                if (env.getHeader() != null) {
                    env.getHeader().detachNode();
                }
                SOAPHeader header = env.addHeader();
                ObjectFactory objFac = new ObjectFactory();
                JAXBElement<ActivationType> activation = objFac.createActivation(this.activationType);

                Marshaller marshaller = JAXBContext.newInstance(ActivationType.class).createMarshaller();
                marshaller.marshal(activation, header);

                soapMsg.saveChanges();
            } catch (SOAPException | JAXBException e) {
                e.printStackTrace();
            }
        }
        logToSystemOut(context);
        return true;
    }

    public Set<QName> getHeaders() {
        return null;
    }

    private void logToSystemOut(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {         
            LN_SOAP_HANDLER_LOGGER.info("\nOutbound message/Request::");
        } else {
            LN_SOAP_HANDLER_LOGGER.info("\nInbound message/Response::");
        }

        SOAPMessage message = smc.getMessage();
        try {
            //message.writeTo(System.out);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            message.writeTo(stream);
            String responseMsg = new String(stream.toByteArray());
            LN_SOAP_HANDLER_LOGGER.info("--------------------------------------------------------------------------------------------");
            LoggerProcessUtil loggerProcessUtil = new LoggerProcessUtil();
            LN_SOAP_HANDLER_LOGGER.info(loggerProcessUtil.prettyPrintXml(responseMsg));
            LN_SOAP_HANDLER_LOGGER.info(responseMsg);
            System.out.println("");   // just to add a newline
        } catch (Exception e) {
            LN_SOAP_HANDLER_LOGGER.info(e);
            System.out.println("Exception in handler: " + e);
        }
    }
}
