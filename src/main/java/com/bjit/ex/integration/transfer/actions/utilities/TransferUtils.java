/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities;

import com.bjit.ex.integration.transfer.factory.TransferFactory;
import com.bjit.ex.integration.transfer.inputparam.TransferInputParams;
import com.bjit.ex.integration.transfer.service.ITransfer;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesRequestType;
import com.infor.businessinterface.item_val.ProcessItemDataRequestType;
import com.infor.businessinterface.projectdeliverable_val.CreateProjectDeliverableRequestType.DataArea.ProjectDeliverableVAL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import matrix.db.BusinessObject;
import matrix.db.Context;

/**
 *
 * @author BJIT
 */
public class TransferUtils {

    private static final org.apache.log4j.Logger TRANSFER_UTILS_LOGGER = org.apache.log4j.Logger.getLogger(TransferUtils.class);

    /**
     * Returns JAXB Gregorian Calender type date format
     * @param date
     * @return JAXBElement<XMLGregorianCalendar>
     */
    private JAXBElement<XMLGregorianCalendar> getDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ApplicationProperties.getProprtyValue("date.format"));
        Date createdDate;
        JAXBElement<XMLGregorianCalendar> createdJaxbElem = null;
        try {
            createdDate = dateFormat.parse("18-07-2018 15:30:37");//date
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(createdDate);
            XMLGregorianCalendar xmlGregorCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
            createdJaxbElem = new JAXBElement<>(new QName("creationDate"), XMLGregorianCalendar.class, xmlGregorCal);
        } catch (ParseException | DatatypeConfigurationException ex) {
            TRANSFER_UTILS_LOGGER.info(ex.getMessage());
            ex.printStackTrace(System.out);
        }
        return createdJaxbElem;
    }

    // After gathering data transfer service call from LN transfer service (BOM)
    
    public synchronized Object transferToLN(List<ProcessItemDataRequestType.DataArea.ItemVAL> itemValList, List bomValList, LinkedHashMap<String, BusinessObject> itemIdMap, Context context, String transferType,boolean isService,List<String> transferedItems) throws Exception {
        Map<String, List> itemAndBomTransferObjectMap = new HashMap<>();
        itemAndBomTransferObjectMap.put("itemValList", itemValList);
        itemAndBomTransferObjectMap.put("bomValList", bomValList);
        TransferInputParams transferInputParams = new TransferInputParams();
        transferInputParams.setTrasnferObjMap(itemAndBomTransferObjectMap);
        transferInputParams.setItemIdMap(itemIdMap);
        transferInputParams.setContext(context);
        transferInputParams.setTransferType(transferType);
        // create a main class and add bellow lines
        TransferFactory transferFactory = new TransferFactory();
        ITransfer trans = transferFactory.getTransferObj(transferInputParams);
        return trans.transfer(transferInputParams,isService,transferedItems);
    }

    public synchronized Object transferToLN(Context context, ProjectDeliverableVAL deliverableVAL, BusinessObject deliverableBusinessObject,  String transferType) throws Exception {
        Map<String, Object> deliverableTransferObjectMap = new HashMap<>();
        deliverableTransferObjectMap.put("deliverableVAL", deliverableVAL);
        deliverableTransferObjectMap.put("deliverableBusinessObject", deliverableBusinessObject);
        TransferInputParams deliverableTransferInputParams = new TransferInputParams();
        deliverableTransferInputParams.setContext(context);
        deliverableTransferInputParams.setTransferType(transferType);
        deliverableTransferInputParams.setTrasnferObjMap(deliverableTransferObjectMap);
        TransferFactory transferFactory = new TransferFactory();
        ITransfer trans = transferFactory.getTransferObj(deliverableTransferInputParams);
        return trans.transfer(deliverableTransferInputParams,false);
    }
    
    public synchronized Object transferToLN(Context context, BusinessObject generalSystemBusinessObject, ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL disAssemblyLinesVALForRequest,  String transferType) throws Exception {
        Map<String, Object> disassembleStructureObjectMap = new HashMap<>();
        disassembleStructureObjectMap.put("disassembleLineRequestVAL", disAssemblyLinesVALForRequest);
        disassembleStructureObjectMap.put("generalSystemBusinessObject", generalSystemBusinessObject);
        TransferInputParams disassembleStructureTransferInputParams = new TransferInputParams();
        disassembleStructureTransferInputParams.setContext(context);
        disassembleStructureTransferInputParams.setTransferType(transferType);
        disassembleStructureTransferInputParams.setTrasnferObjMap(disassembleStructureObjectMap);
        TransferFactory transferFactory = new TransferFactory();
        ITransfer trans = transferFactory.getTransferObj(disassembleStructureTransferInputParams);
        return trans.transfer(disassembleStructureTransferInputParams,false);
    }

    
    public synchronized Object transferToLN(Context context, List valList, LinkedHashMap<String, BusinessObject> itemIdMap, String transferType) throws Exception {
        Map<String, List> itemAndBomTransferObjectMap = new HashMap<>();
        if (transferType.equalsIgnoreCase("BOMTransfer")) {
            itemAndBomTransferObjectMap.put("bomValList", valList);
        } else {
            itemAndBomTransferObjectMap.put("itemValList", valList);
        }
        TransferInputParams transferInputParams = new TransferInputParams();
        transferInputParams.setTrasnferObjMap(itemAndBomTransferObjectMap);
        transferInputParams.setItemIdMap(itemIdMap);
        transferInputParams.setContext(context);
        transferInputParams.setTransferType(transferType);
        // create a main class and add bellow lines
        TransferFactory transferFactory = new TransferFactory();
        ITransfer trans = transferFactory.getTransferObj(transferInputParams);
        return trans.transfer(transferInputParams,false);
    }
}
