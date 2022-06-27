/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.dw.enovia.mapping.processors;


import com.bjit.dw.enovia.utility.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

/**
 *
 * @author BJIT
 * @param <T>
 */
public class MappingXML<T> implements IMapper<T> {

    private Class<T> classReference;
    private String fileLocation;
    private File file;
    private T mappingObject;
    private String xmlMapFileAsString;
    final static org.apache.log4j.Logger MAPPING_XML_LOGGER = org.apache.log4j.Logger.getLogger(MappingXML.class.getName());

    /**
     * Initializes the mapper processor from a given class which is a XML map
     * POJO or model class
     *
     * @param classReference
     * @throws Exception
     */
    @Override
    public void __init__(Class<T> classReference) throws Exception {
        this.classReference = classReference;
        this.fileLocation = Configuration.LN_ENOVIA_XML_FILE_DIRECTORY;
        try {
            readXmlMapperFromFile();
        } catch (IOException ioExp) {
            MAPPING_XML_LOGGER.error("Xml Mapper couldn''t be read as string from resource location. " + this.fileLocation);
            throw ioExp;
        } catch (Exception exp) {
            MAPPING_XML_LOGGER.error("Xml Mapper couldn''t be read as string from resource location. " + this.fileLocation);
            throw exp;
        }
    }

    public void setMappingObject(T mappingObject) {
        this.mappingObject = mappingObject;
    }

    private void getXMLModelInstance() throws InstantiationException, IllegalAccessException {
        try {
            mappingObject = this.classReference.newInstance();
        } catch (InstantiationException | IllegalAccessException exp) {
            throw exp;
        }
    }

    /**
     * Creates Java object from the XML map file
     *
     * @return a model object T
     * @throws JAXBException
     */
    @Override
    public T getObject() throws JAXBException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(this.classReference);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            T object = (T) jaxbUnmarshaller.unmarshal(this.file);
            return object;
        } catch (JAXBException exp) {
            throw exp;
        }
    }

    /**
     * Creates Java object from the XML map files string value which has gotten
     * from the resource folder as input stream
     *
     * @return a model object T
     * @throws JAXBException
     */
    @Override
    public T getObjectFromString() throws JAXBException {
        try {
            MAPPING_XML_LOGGER.info("Creating xml map object from the xml map string");
            StringReader mappingXmlMapStringReader = new StringReader(xmlMapFileAsString);
            JAXBContext jaxbContext = JAXBContext.newInstance(this.classReference);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            T object = (T) jaxbUnmarshaller.unmarshal(mappingXmlMapStringReader);
            MAPPING_XML_LOGGER.info("Xml map object from the xml map string has been created successfully");
            return object;
        } catch (JAXBException | NullPointerException exp) {
            MAPPING_XML_LOGGER.error("Error occured when unmarshalling from Map String " + xmlMapFileAsString + ". Error : " + exp.getMessage());
            throw exp;
        }
    }

    /**
     * Sets the XML map to a file in the given directory
     *
     * @throws JAXBException
     */
    @Override
    public void setObject() throws JAXBException {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(this.classReference);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(mappingObject, this.file);
            jaxbMarshaller.marshal(mappingObject, System.out);

        } catch (JAXBException exp) {
            throw exp;
        }
    }

    /**
     * Sets the object which comes as parameters into a file to a given location
     * in the properties file
     *
     * @param object
     * @throws Exception
     */
    @Override
    public void setObject(T object) throws Exception {
        setMappingObject(object);
        setObject();
    }

    /**
     * Reads mapper from a hard file from the given location in the properties
     * file
     *
     * @throws Exception
     */
    private void readXmlMapperFromFile() throws Exception {
        try {
            this.fileLocation = Configuration.LN_ENOVIA_XML_FILE_DIRECTORY;
            MAPPING_XML_LOGGER.info("XML mapper files relative path : " + this.fileLocation);
            this.file = new File(this.fileLocation);
            MAPPING_XML_LOGGER.info("XML mapper files absolute path : " + this.file.getAbsolutePath());

            if (!this.file.exists()) {
                MAPPING_XML_LOGGER.error("Mapping.xml file is not exists");
                throw new Exception("Mapping.xml file is not exists");
            }

            getXMLModelInstance();
        } catch (FileNotFoundException | NullPointerException exp) {
            MAPPING_XML_LOGGER.error("Mapping.xml file is not found in " + this.file.getAbsolutePath());
            throw exp;
        }
    }
}
