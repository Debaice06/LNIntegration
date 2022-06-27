/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
/**
 *
 * @author Sajjad
 */
public class LoggerProcessUtil {
    public String prettyPrintXml(String xml) {
        Writer out = new StringWriter();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));
            Transformer tf = TransformerFactory.newInstance().newTransformer();

            tf.setOutputProperty(OutputKeys.INDENT, "yes");
//            tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
            tf.transform(new DOMSource(document), new StreamResult(out));
        }
        catch (IOException ioex){
            ioex.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return out.toString();
    }
}
