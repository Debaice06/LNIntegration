/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions;

import com.bjit.context.ContextGeneration;
import com.bjit.ex.integration.transfer.actions.factory.TransferActionFactory;
import com.bjit.ex.integration.transfer.actions.factory.TransferActionType;
import com.bjit.ex.integration.transfer.actions.transferItems.ITransferAction;
import com.bjit.ex.integration.transfer.actions.utilities.*;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.DisassembleDataModel;
import com.bjit.ex.integration.transfer.resultsender.ResponseResultSender;
import com.bjit.ex.integration.transfer.resultsender.SenderType;
import com.bjit.ex.integration.transfer.resultsender.factory.ResultSenderFactory;
import com.bjit.ex.integration.transfer.service.impl.LN.ItemCostCalculation;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.FileDirProcess;
import com.bjit.ex.integration.transfer.util.ItemInfo;
import matrix.db.BusinessObject;
import matrix.db.Context;
import org.jdom.JDOMException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import matrix.db.Attribute;
import matrix.db.BusinessObjectWithSelect;
import matrix.util.MatrixException;
import matrix.util.SelectList;

/**
 * @author BJIT @revised sajjad
 */
public final class TransferAction {

    private static final org.apache.log4j.Logger TRANSFER_ACTION_LOGGER = org.apache.log4j.Logger.getLogger(TransferAction.class);
    private final static HashMap<String, String> STATE_AND_CLASS_NAMES_MAP = new HashMap<>();

    static {
        /*STATE_AND_CLASS_NAMES_MAP.put("IN_WORK", "Draft");*/
        STATE_AND_CLASS_NAMES_MAP.put("RELEASED", "Released");
        STATE_AND_CLASS_NAMES_MAP.put("DELIVERABLE", "Deliverable");
        STATE_AND_CLASS_NAMES_MAP.put("OBSOLETE", "Obsolete");
    }

    public TransferAction() {
        try {
            TRANSFER_ACTION_LOGGER.info("LN TRANSFER APP BUILD VERSION :: " + ApplicationProperties.getProprtyValue("ln.integration.release.build"));
            TRANSFER_ACTION_LOGGER.info("LN Transfer Process Has Been Started");
            loadXmlBusinessObjectFiles();
        } catch (Exception exp) {
            exp.printStackTrace();
            TRANSFER_ACTION_LOGGER.error(exp.getMessage());
        }
    }

    /**
     *
     * @throws Exception
     */
    private void loadXmlBusinessObjectFiles() throws Exception {
        File[] businessObjectXmlFiles;
        XMLFileProcessUtil xmlFileProcessUtil = new XMLFileProcessUtil();
        try {
            businessObjectXmlFiles = xmlFileProcessUtil.getBusinessObjectXmlFiles();
            try {

                if (businessObjectXmlFiles.length < 1) {
                    TRANSFER_ACTION_LOGGER.error("There is no file in '" + xmlFileProcessUtil.getBusinessObjectXmlDirectory() + "' this directory");
                    throw new NullPointerException("There is no file in '" + xmlFileProcessUtil.getBusinessObjectXmlDirectory() + "' this directory");
                }

                Arrays.sort(businessObjectXmlFiles, new Comparator<File>() {
                    public int compare(File f1, File f2) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
                        Date creationDate1 = new Date();
                        Date creationDate2 = new Date();
                        try {
                            creationDate1 = format.parse(f1.getName().substring(f1.getName().lastIndexOf('_') + 1).replaceAll(".xml", ""));
                            creationDate2 = format.parse(f2.getName().substring(f2.getName().lastIndexOf('_') + 1).replaceAll(".xml", ""));
                        } catch (ParseException ex) {
                            Logger.getLogger(TransferAction.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return creationDate1.compareTo(creationDate2);
                    }
                });

                List<File> fileList = Arrays.asList(businessObjectXmlFiles);

                try {
                    processXmlBusinessFile(fileList);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    TRANSFER_ACTION_LOGGER.error("\n Transfer Error !!! " + ex);
                    TRANSFER_ACTION_LOGGER.trace(ex);
                }
            } catch (Exception e) {
                e.printStackTrace();
                TRANSFER_ACTION_LOGGER.error("Error Occurred : " + e);
                TRANSFER_ACTION_LOGGER.trace(e);
                throw e;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            TRANSFER_ACTION_LOGGER.error("Error Occurred : " + exp);
            TRANSFER_ACTION_LOGGER.error(exp);
            throw exp;
        }
    }

    /**
     *
     * @param fileList
     * @throws Exception
     */
    public void processXmlBusinessFile(List<File> fileList) throws Exception {
        Context context = null;
        try {
            context = ContextGeneration.createContext();
            XMLFileProcessUtil xmlFileProcessUtil = new XMLFileProcessUtil();

            ArrayList<BusinessObject> itemTransferBOList = new ArrayList<>();
            HashMap<String, BusinessObject> businessObjectNameMap = new HashMap<>();
            boolean multipleItemSendSingleTransfer = false;
            HashMap<String, File> itemFileMap = new HashMap<>();
            HashMap<String, ItemInfo> itemFileInfoMap = new HashMap<>();

            ArrayList<BusinessObject> updateItemBOList = new ArrayList<>();
            HashMap<String, BusinessObject> updateBusinessObjectNameMap = new HashMap<>();
            boolean multipleItemUpdateSingleTransfer = false;
            HashMap<String, File> updateItemFileMap = new HashMap<>();
            HashMap<String, ItemInfo> updateItemFileInfoMap = new HashMap<>();

            for (File file : fileList) {
                FileDirProcess fileDirProcess = new FileDirProcess();
                ItemInfo itemInfo = new ItemInfo();
                itemInfo = fileDirProcess.parseFile(file);
                String itemId = itemInfo.getItemId();
                String currentState = itemInfo.getNextState();
                BusinessObject businessObject = null;

                TRANSFER_ACTION_LOGGER.info("File Name : " + file.getName() + " Item : " + itemInfo.getItemName() + " ID : " + itemInfo.getItemId());

                try {
                    businessObject = new BusinessObject(itemId);
                    String transferAction = "";
                    businessObject.open(context);
                    String itemName = businessObject.getName();

                    /* Deliverable and Disassemble sending (General-System/DELI_VALDeliverable). */
                    if (businessObject.getTypeName()
                            .equalsIgnoreCase(ApplicationProperties.getProprtyValue("deliverable.object.type"))) {

                        processDeliverableAndDisassemble(context, businessObject, file);

                    } /* All types except DELI_VALDeliverable will send. */ else {
                        BusinessObjectUtils businessObjectUtils = new BusinessObjectUtils();
//                        String businessObjectsState = businessObjectUtils.findBusinessObjectsState(businessObject, context);
//                        TRANSFER_ACTION_LOGGER.info(itemName + " : Item State " + businessObjectsState);
//                        transferAction = STATE_AND_CLASS_NAMES_MAP.get(businessObjectsState);

                        TRANSFER_ACTION_LOGGER.info(itemName + " : Item State " + currentState);
                        transferAction = STATE_AND_CLASS_NAMES_MAP.get(currentState);
                        if (!businessObject.getRevision().equalsIgnoreCase("1.1")) {
                            TransferObjectUtils.checkPreviousRevision(context, businessObject);

                        }
                        /* First all 'Released Item' data will gather from fileList then process and send at once. */
                        if ("Released".equalsIgnoreCase(transferAction)) {
                            TRANSFER_ACTION_LOGGER.info("Collecting manufacturing item for multiple Item send : Item Name : " + itemName);
                            multipleItemSendSingleTransfer = true;
                            itemTransferBOList.add(businessObject);
                            businessObjectNameMap.put(businessObject.getName().toUpperCase(), businessObject);
                            itemFileMap.put(businessObject.getName().toUpperCase(), file);
                            itemFileInfoMap.put(businessObject.getName().toUpperCase(), itemInfo);
                            businessObject.close(context);
                            continue;
                        } /* For Item update (Item from RELEASED to OBSOLETE), system will process file one by one. */ else if ("Obsolete".equalsIgnoreCase(transferAction)) {
                            String transferredToErp = BusinessObjectUtils.getItemAttributeValue(context, businessObject.getObjectId(), ApplicationProperties.getProprtyValue("source.att.send.to.erp"));

                            if ("TRUE".equalsIgnoreCase(transferredToErp)) {
                                try {
                                    TRANSFER_ACTION_LOGGER.info("Collecting Obsolete Item for bulk update : Item Name : " + itemName);
                                    multipleItemUpdateSingleTransfer = true;
                                    updateItemBOList.add(businessObject);
                                    updateBusinessObjectNameMap.put(businessObject.getName().toUpperCase(), businessObject);
                                    updateItemFileMap.put(businessObject.getName().toUpperCase(), file);
                                    updateItemFileInfoMap.put(businessObject.getName().toUpperCase(), itemInfo);
                                    businessObject.close(context);
                                    continue;
                                } catch (Exception e) {
                                    TRANSFER_ACTION_LOGGER.error("Exception is occurred during item update (Released to Obsolete) " + e.getMessage());
                                }
                            } else {
                                TRANSFER_ACTION_LOGGER.info("Obsolete items are transferable if they are already transferred to LN");
                                TRANSFER_ACTION_LOGGER.info("File moving to Old directory : " + file.getName());
                                xmlFileProcessUtil.removeBusinessObjectXmlFilesToOldDirectory(file);
                                //mail can be sent to AMS. Should be system error. Is mail needed?
                            }
                        } /* Item's status does not match with transfer category.*/ else {
                            TRANSFER_ACTION_LOGGER.info("Item's status does not match with transfer category.");
                            TRANSFER_ACTION_LOGGER.info("File moving to Old directory : " + file.getName());
                            xmlFileProcessUtil.removeBusinessObjectXmlFilesToOldDirectory(file);
                            //Mail can be sent to User/AMS.
                        }
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                    TRANSFER_ACTION_LOGGER.error(exp.getMessage());
                    TRANSFER_ACTION_LOGGER.error(exp);
                    throw exp;
                } finally {
                    businessObject.close(context);
                }
            }

            /* System will process all 'Released Item' at once and send all item and BOM in a single transfer request. */
            if (multipleItemSendSingleTransfer && itemTransferBOList.size() > 0) {
                try {
                    processItemsForTransfer(context, itemTransferBOList, businessObjectNameMap, itemFileMap, itemFileInfoMap);

                } catch (Exception e) {
                    e.printStackTrace();
                    ResponseResultSender responseResultSender = ResultSenderFactory.getResultSender(SenderType.LN_TRANSFER_RESPONSE_SENDER);
                    responseResultSender.initializeResultSender();
                    List<ResponseResult> resultList = new ArrayList<>();

                    itemFileMap.forEach((key, value) -> {
                        ResponseResult result = new ResponseResult();
                        ItemInfo itemInfo = itemFileInfoMap.get(key);
                        result.setId(itemInfo.getItemId());
                        result.setItem(itemInfo.getItemName().toUpperCase());
                        result.setRevision(itemInfo.getItemRev());
                        result.setSuccessful(false);
                        result.setResultText("Connection Failed");
                        resultList.add(result);


                    });

                    responseResultSender.sendMultipleMail(context, resultList, businessObjectNameMap, itemFileInfoMap);
                    TRANSFER_ACTION_LOGGER.error("\nError Occurred While Transferring RELEASED Item.\nSystem Related Error.\n Transferring again" + e);
                    try {
                        processItemsForTransfer(context, itemTransferBOList, businessObjectNameMap, itemFileMap, itemFileInfoMap);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        TRANSFER_ACTION_LOGGER.error("\nError Occurred While Transferring Multiple RELEASED Item. \nSystem Related Error");
                        responseResultSender = ResultSenderFactory.getResultSender(SenderType.LN_TRANSFER_RESPONSE_SENDER);
                        responseResultSender.initializeResultSender();
                        List<ResponseResult> failResultList = new ArrayList<>();

                        itemFileMap.forEach((key, value) -> {
                            ResponseResult result = new ResponseResult();
                            ItemInfo itemInfo = itemFileInfoMap.get(key);
                            result.setId(itemInfo.getItemId());
                            result.setItem(itemInfo.getItemName().toUpperCase());
                            result.setRevision(itemInfo.getItemRev());
                            result.setSuccessful(false);
                            result.setResultText("Connection Failed");
                            failResultList.add(result);

                            File file = itemFileMap.get(itemInfo.getItemName().toUpperCase());
                            TRANSFER_ACTION_LOGGER.error(file.getName() + " : File moving to error directory.");
                            xmlFileProcessUtil.removeBusinessObjectXmlFilesToOldDirectory(file);

                        });

                        responseResultSender.sendMultipleMail(context, failResultList, businessObjectNameMap, itemFileInfoMap);

                    }
                }
            }

            /*
            * System will process all 'Obsolete Item' at once and update all item in a single transfer request.
            * Only item will update. No BOM sending required.
             */
            if (multipleItemUpdateSingleTransfer && updateItemBOList.size() > 0) {
                try {
                    processItemsForUpdate(context, updateItemBOList, updateBusinessObjectNameMap, updateItemFileMap, updateItemFileInfoMap);
                } catch (Exception e) {
                    e.printStackTrace();
                    TRANSFER_ACTION_LOGGER.error("\nError Occurred While Updating Multiple OBSOLETE Item." + e);
                }
            }
        } catch (IOException | JDOMException exp) {
            exp.printStackTrace();
            TRANSFER_ACTION_LOGGER.error(exp);
            throw exp;
        } finally {
            context.close();
        }
    }

    /**
     * Deliverable and Disassemble sending (General-System/DELI_VALDeliverable).
     * 3 steps : Disassemble Item, Deliverable, Disassemble Structure (BOM)
     *
     * @param context
     * @param businessObject
     * @param file
     */
    private void processDeliverableAndDisassemble(Context context, BusinessObject businessObject, File file) {
        TRANSFER_ACTION_LOGGER.info("\n Process Deliverable/Disassemble Transfer.");
        XMLFileProcessUtil xmlFileProcessUtil = new XMLFileProcessUtil();
        TransferObjectUtils transferObjectUtils = new TransferObjectUtils();
        TransferActionFactory transferActionFactory = new TransferActionFactory();

        ResponseResultSender responseResultSender = ResultSenderFactory.getResultSender(SenderType.LN_TRANSFER_RESPONSE_SENDER);
        responseResultSender.initializeResultSender(context, businessObject);
        ArrayList<ResponseResult> results = new ArrayList<>();
        ItemUtils itemUtils = new ItemUtils();

        boolean isDisassembleItemsProcessDone = false;
        boolean isDeliverableProcessDone = false;
        DisassembleDataModel disassembleDataModel = null;
        String itemName = "";
        try {
            /* Disassemble Items sending. */
            businessObject.open(context);
            itemName = businessObject.getName();
            ITransferAction iTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.DISASSEMBLE_ITEM);
            Object disassembleItemsObject = iTransferAction.processDisassembleItemsTransfer(context, businessObject, transferObjectUtils, itemUtils);

            if (disassembleItemsObject instanceof DisassembleDataModel) {
                disassembleDataModel = (DisassembleDataModel) disassembleItemsObject;
                isDisassembleItemsProcessDone = true;
            } else if (disassembleItemsObject instanceof ArrayList) {
                TRANSFER_ACTION_LOGGER.info(itemName + " : Disassemble/Deliverable failed to transfer (Disassemble Items Sending Failed).");
                TRANSFER_ACTION_LOGGER.info(file.getName() + " : File moving to error directory.");
                results = (ArrayList<ResponseResult>) disassembleItemsObject;
                responseResultSender.send(results);
                xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file);
                businessObject.close(context);
                return;
            }

            /* Deliverable sending. */
            if (isDisassembleItemsProcessDone) {
                initializeTheTransferUtilityClasses(context, businessObject, transferObjectUtils);
                iTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.DELIVERABLE);
                Object deliverableObj = iTransferAction.processDeliverableTransfer(context, businessObject, disassembleDataModel, transferObjectUtils);

                if (deliverableObj instanceof DisassembleDataModel) {
                    disassembleDataModel = (DisassembleDataModel) deliverableObj;
                    isDeliverableProcessDone = true;
                } else if (deliverableObj instanceof ArrayList) {
                    TRANSFER_ACTION_LOGGER.info(itemName + " : Disassemble/Deliverable failed to transfer (Deliverable Sending Failed).");
                    TRANSFER_ACTION_LOGGER.info(file.getName() + " : File moving to error directory.");

                    results = (ArrayList<ResponseResult>) deliverableObj;
                    responseResultSender.send(results);
                    xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file);
                    businessObject.close(context);
                    return;
                }
            }

            /*  Disassemble structure sending.
                If there is no item connected with Loading and Unloading object, then 'ItemDetails' will be empty.
             */
            if (isDisassembleItemsProcessDone && isDeliverableProcessDone
                    && (disassembleDataModel.getItemDetails() != null && !disassembleDataModel.getItemDetails().isEmpty())) {
                iTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.DISASSEMBLE_STRUCTURE);
                Object disassembleStrObj = iTransferAction.processDisassembleStructureTransfer(context, businessObject, transferObjectUtils, disassembleDataModel);
                if (disassembleStrObj instanceof ArrayList) {
                    results = (ArrayList<ResponseResult>) disassembleStrObj;
                    if (!results.isEmpty()) {
                        TRANSFER_ACTION_LOGGER.info(itemName + " : Disassemble/Deliverable failed to transfer (Disassemble Structure Sending Failed).");
                        TRANSFER_ACTION_LOGGER.info(file.getName() + " : File moving to error directory.");

                        responseResultSender.send(results);
                        xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file);
                        businessObject.close(context);
                        return;
                    }
                }
            }

            TRANSFER_ACTION_LOGGER.info(itemName + " : Disassemble/Deliverable Successfully Transferred.");
            TRANSFER_ACTION_LOGGER.info(file.getName() + " : File moving to old directory.");
            xmlFileProcessUtil.removeBusinessObjectXmlFilesToOldDirectory(file);

            ResponseResult responseResult = new ResponseResult(businessObject.getName(), businessObject.getRevision(), "RESULT OK", true);

            results.add(responseResult);
            responseResultSender.send(results);

        } catch (Exception e) {
            TRANSFER_ACTION_LOGGER.error(itemName + " : Exceptions are occurred during Deliverable/Disassemble transfer " + e.getMessage());
            TRANSFER_ACTION_LOGGER.error(file.getName() + " : File moving to error directory.");
            xmlFileProcessUtil.removeBusinessObjectXmlFilesToOldDirectory(file);
            //mail can be sent to AMS. system error here. Is mail needed?
        }
    }

    /**
     * System will process all 'Released Item' at once and send all item and BOM
     * in a single transfer request. Transfer Sequence : Item, Cost Calculation,
     * BOM, Selection Code update.
     *
     * @param context
     * @param itemTransferBOList
     * @param businessObjectNameMap
     * @param itemFileMap
     * @param itemFileInfoMap
     */
    private void processItemsForTransfer(Context context, ArrayList<BusinessObject> itemTransferBOList, HashMap<String, BusinessObject> businessObjectNameMap, HashMap<String, File> itemFileMap, HashMap<String, ItemInfo> itemFileInfoMap) throws Exception {
        TRANSFER_ACTION_LOGGER.info("\n Process Multiple Items.");

        XMLFileProcessUtil xmlFileProcessUtil = new XMLFileProcessUtil();
        List<ResponseResult> responseResultList = new ArrayList<>();

        TransferActionFactory transferActionFactory = new TransferActionFactory();
        TransferObjectUtils transferObjectUtils = new TransferObjectUtils();
        boolean isService = false;
        /* Item Transfer Process. */
        ITransferAction iTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.ITEMS);
        List<ResponseResult> itemResponseResultList = (List<ResponseResult>) iTransferAction.processItemsTransfer(context, itemTransferBOList, transferObjectUtils);
        TRANSFER_ACTION_LOGGER.debug("ITEM transfer response list :: " + itemResponseResultList);
        responseResultList.addAll(itemResponseResultList);

        //Filter Item Transfer result data for Cost calculation and BOM Transfer.
        ArrayList<BusinessObject> BOMTransferBOList = new ArrayList<>();
        ArrayList<String> checkUnspecifiedErrorItemList = new ArrayList<>();
        ArrayList<String> costCalculationItemList = new ArrayList<>();
        boolean unspecifiedErrorOccurredDuringItemTransfer = false;
        for (ResponseResult result : itemResponseResultList) {
            if (result != null) {
                if (result.isSuccessful()) {
                    String itemName_successfullyTransferred = result.getItem();
                    //Update "Transfer To ERP" attribute.
                    try {
                        BusinessObject successfullyTransferredBO = businessObjectNameMap.get(itemName_successfullyTransferred);
                        successfullyTransferredBO.setAttributeValue(context, ApplicationProperties.getProprtyValue("source.att.send.to.erp"), Boolean.TRUE.toString());
                        TRANSFER_ACTION_LOGGER.info(itemName_successfullyTransferred + " : 'Transfer to ERP' is updated to TRUE.");
                    } catch (Exception ex) {
                        TRANSFER_ACTION_LOGGER.error(itemName_successfullyTransferred + " : Error occurred during 'Transfer to ERP' value update.");
                    }
                    costCalculationItemList.add(itemName_successfullyTransferred);
                    BOMTransferBOList.add(businessObjectNameMap.get(itemName_successfullyTransferred));
                } else {
                    String itemName_failedTransfer = result.getItem();
                    File file_move_error = itemFileMap.get(itemName_failedTransfer);
                    if (file_move_error != null) {
                        TRANSFER_ACTION_LOGGER.info(itemName_failedTransfer + " : Transfer failed. Transfer Result : " + result.getResultText());
                        TRANSFER_ACTION_LOGGER.info(file_move_error.getName() + " : Moving File to Error Directory.");
                        xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file_move_error);
                        itemFileMap.remove(itemName_failedTransfer);
                    }
                }
                checkUnspecifiedErrorItemList.add(result.getItem());
            } else {
                itemResponseResultList.remove(null);
                unspecifiedErrorOccurredDuringItemTransfer = true;
                TRANSFER_ACTION_LOGGER.info("Response NULL. Unspecified error occurred during Item transfer. Probably no Item name from LN response.");
            }
        }

        //Unspecified error is very rare. Occurs if LN failed to provide response without Item name.
        if (unspecifiedErrorOccurredDuringItemTransfer) {
            for (BusinessObject bo : itemTransferBOList) {
                bo.open(context);
                String itemName = bo.getName();
                itemName = itemName.toUpperCase();
                if (!checkUnspecifiedErrorItemList.contains(itemName)) {
                    ResponseResult unspecifiedResponseResult = new ResponseResult(itemName, bo.getRevision(),
                            "Unspecified error occurred. Probably no Item name from LN response. ", false);
                    itemResponseResultList.add(unspecifiedResponseResult);
                    File file_move_error = itemFileMap.get(itemName);
                    if (file_move_error != null) {
                        TRANSFER_ACTION_LOGGER.info(itemName + " : Transfer failed. Transfer result : Unspecified error occurred during Item transfer. Probably no Item name from LN response.");
                        TRANSFER_ACTION_LOGGER.info(file_move_error.getName() + " : Moving File to Error Directory.");
                        xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file_move_error);
                        itemFileMap.remove(itemName);
                    }
                }
                bo.close(context);
            }
        }

        /* Cost Calculation */
        try {
            ItemCostCalculation itemCostCalculation = new ItemCostCalculation();
            if (costCalculationItemList != null && !costCalculationItemList.isEmpty()) {
                itemCostCalculation.calculateCost(costCalculationItemList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TRANSFER_ACTION_LOGGER.error("Error occurred during Cost Calculation!" + e);
        }

        /* BOM Transfer Process */
        List<ResponseResult> BOMResponseResultList = new ArrayList<>();
        if (!BOMTransferBOList.isEmpty()) {
            ITransferAction BOMTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.BOM);
            BOMResponseResultList = (List<ResponseResult>) BOMTransferAction.processBOMTransfer(context, BOMTransferBOList, transferObjectUtils, isService);
        }

        TRANSFER_ACTION_LOGGER.debug("BOM transfer response list :: " + BOMResponseResultList);
        responseResultList.addAll(BOMResponseResultList);

        //Filter BOM Transfer data for 'Selection Code' update.
        ArrayList<BusinessObject> selectionCodeUpdateTransferBOList = new ArrayList<>();
        HashMap<String, String> itemSelectionCodeMap = new HashMap<>();
        ArrayList<String> checkDuplicateBOM = new ArrayList<>();
        boolean unspecifiedErrorOccurredDuringBOMTransfer = false;
        SelectList buildRelStmnsts = transferObjectUtils.buildBusStmnts();

        //Gather item to update selection code item for item with child.
        for (ResponseResult result : BOMResponseResultList) {
            if (result != null) {
                String itemName = result.getItem();
                String selectionCode = "";
                BusinessObject businessObject = businessObjectNameMap.get(itemName);
                String selectCde = transferObjectUtils.getSelectionCode(context, businessObject, buildRelStmnsts);
                if (result.getSelectionCodeType() != null) {
                    if (result.getSelectionCodeType().name().equalsIgnoreCase(SelectionCodeType.ITM.name()) && selectCde.equalsIgnoreCase(SelectionCodeType.BOM.name())) {
                        selectionCode = result.getSelectionCodeType().name();
                    } else if (result.getSelectionCodeType().name().equalsIgnoreCase(SelectionCodeType.EXT.name()) && !selectCde.equalsIgnoreCase(SelectionCodeType.ITM.name())) {
                        selectionCode = SelectionCodeType.ERR.name();
                    } else if (result.getSelectionCodeType().name().equalsIgnoreCase(SelectionCodeType.ITM.name()) && !selectCde.equalsIgnoreCase(SelectionCodeType.ITM.name())) {
                        selectionCode = SelectionCodeType.EXT.name();
                    } else {
                        selectionCode = result.getSelectionCodeType().name();
                    }

                } else {
                    if (result.isSuccessful()) {
                        selectionCode = SelectionCodeType.BOM.name();
                    } else if (selectCde.equalsIgnoreCase(SelectionCodeType.ITM.name())) {
                        selectionCode = SelectionCodeType.EXT.name();
                        File file_move_error = itemFileMap.get(itemName);
                        if (file_move_error != null) {
                            itemFileMap.remove(itemName);
                            TRANSFER_ACTION_LOGGER.info(itemName + " : Transfer failed. Transfer result : " + result.getResultText());
                            TRANSFER_ACTION_LOGGER.info(file_move_error.getName() + " : Moving File to Error Directory.");
                            xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file_move_error);
                        }
                    } else {
                        selectionCode = SelectionCodeType.ERR.name();
                        File file_move_error = itemFileMap.get(itemName);
                        if (file_move_error != null) {
                            itemFileMap.remove(itemName);
                            TRANSFER_ACTION_LOGGER.info(itemName + " : Transfer failed. Transfer result : " + result.getResultText());
                            TRANSFER_ACTION_LOGGER.info(file_move_error.getName() + " : Moving File to Error Directory.");
                            xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file_move_error);
                        }
                    }
                }
                if (!itemSelectionCodeMap.containsKey(itemName)) {
                    itemSelectionCodeMap.put(itemName, selectionCode);
                } else {
                    //Selection Code priority : ERR, EXT/BOM, ITM
                    if (itemSelectionCodeMap.get(itemName).equalsIgnoreCase(SelectionCodeType.ERR.name())) {
                        itemSelectionCodeMap.put(itemName, SelectionCodeType.ERR.name());
                    } else if (itemSelectionCodeMap.get(itemName).equalsIgnoreCase(SelectionCodeType.ITM.name())) {
                        itemSelectionCodeMap.put(itemName, SelectionCodeType.ITM.name());
                    } else if (itemSelectionCodeMap.get(itemName).equalsIgnoreCase(SelectionCodeType.EXT.name())) {
                        if (selectionCode.equalsIgnoreCase(SelectionCodeType.ERR.name())) {
                            itemSelectionCodeMap.put(itemName, SelectionCodeType.ERR.name());
                        } else {
                            itemSelectionCodeMap.put(itemName, SelectionCodeType.EXT.name());
                        }
                    } else {
                        itemSelectionCodeMap.put(itemName, selectionCode);
                    }
                }

                if (!checkDuplicateBOM.contains(itemName)) {
                    checkDuplicateBOM.add(itemName);
                    selectionCodeUpdateTransferBOList.add(businessObjectNameMap.get(itemName));
                }
            } else {
                BOMResponseResultList.remove(null);
                unspecifiedErrorOccurredDuringBOMTransfer = true;
                TRANSFER_ACTION_LOGGER.info("Response NULL. Unspecified error occurred during BOM transfer. Probably no Item name from LN response.");
            }

        }

        //Gather item to update selection code item for standalone item.
        for (ResponseResult itemResult : itemResponseResultList) {
            if (itemResult != null) {
                String itemName = itemResult.getItem();
                if (!itemSelectionCodeMap.containsKey(itemName)) {
                    if (itemResult.isSuccessful()) {
                        itemSelectionCodeMap.put(itemName, SelectionCodeType.ITM.name());
                        if (!checkDuplicateBOM.contains(itemName)) {
                            checkDuplicateBOM.add(itemName);
                            selectionCodeUpdateTransferBOList.add(businessObjectNameMap.get(itemName));
                        }
                    }
                }
            }
        }

        // Unspecified error is very rare. Occurs if LN failed to provide response without Item name.
        if (unspecifiedErrorOccurredDuringBOMTransfer) {
            for (BusinessObject bo : BOMTransferBOList) {
                bo.open(context);
                String itemName = bo.getName();
                itemName = itemName.toUpperCase();
                if (!itemSelectionCodeMap.containsKey(itemName)) {
                    itemSelectionCodeMap.put(itemName, SelectionCodeType.ERR.name());
                    ResponseResult unspecifiedResponseResult = new ResponseResult(itemName, bo.getRevision(),
                            "Unspecified error occurred during BOM transfer. Probably no Item name from LN response. ", false);
                    BOMResponseResultList.add(unspecifiedResponseResult);
                    File file_move_error = itemFileMap.get(itemName);
                    if (file_move_error != null) {
                        itemFileMap.remove(itemName);
                        TRANSFER_ACTION_LOGGER.info(itemName + " : Transfer failed. Transfer result : Unspecified error occurred during BOM transfer. Probably no Item name from LN response.");
                        TRANSFER_ACTION_LOGGER.info(file_move_error.getName() + " : Moving File to Error Directory.");
                        xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file_move_error);
                    }
                }
                bo.close(context);
            }
        }

        TRANSFER_ACTION_LOGGER.info("Item Selection Code Map :: " + itemSelectionCodeMap);

        /* Selection Code update process */
        List<ResponseResult> selectionCodeUpdateResponseResultList = new ArrayList<>();
        if (!selectionCodeUpdateTransferBOList.isEmpty()) {
            ITransferAction selectionCodeTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.SELECTION_CODE_UPDATE);
            selectionCodeUpdateResponseResultList = (List<ResponseResult>) selectionCodeTransferAction.processSelectionCodeUpdateTransfer(context, selectionCodeUpdateTransferBOList, itemSelectionCodeMap, transferObjectUtils);
        }
        TRANSFER_ACTION_LOGGER.debug("Selection Code update transfer response list from LN :: " + selectionCodeUpdateResponseResultList);
        responseResultList.addAll(selectionCodeUpdateResponseResultList);
        ResponseResultSender responseResultSender = ResultSenderFactory.getResultSender(SenderType.LN_TRANSFER_RESPONSE_SENDER);
        responseResultSender.initializeResultSender();
        responseResultSender.sendMultipleMail(context, responseResultList, businessObjectNameMap, itemFileInfoMap);

        // Moving all successfully processed file to old directory.
        for (String itemName : itemFileMap.keySet()) {
            File file_move_old = itemFileMap.get(itemName);
            if (file_move_old != null) {
                TRANSFER_ACTION_LOGGER.info(itemName + " : Transfer Successful.");
                TRANSFER_ACTION_LOGGER.info(file_move_old.getName() + " : Moving File to Old Directory.");
                xmlFileProcessUtil.removeBusinessObjectXmlFilesToOldDirectory(file_move_old);
            }
        }
    }

    /**
     * System will process all 'Obsolete Item' at once and update all item in a
     * single transfer request. Only item will update. No BOM sending required.
     *
     * @param context
     * @param updateItemBOList
     * @param updateBusinessObjectNameMap
     * @param updateItemFileMap
     * @param updateItemFileInfoMap
     * @throws Exception
     */
    private void processItemsForUpdate(Context context, ArrayList<BusinessObject> updateItemBOList, HashMap<String, BusinessObject> updateBusinessObjectNameMap, HashMap<String, File> updateItemFileMap, HashMap<String, ItemInfo> updateItemFileInfoMap) throws Exception {
        TRANSFER_ACTION_LOGGER.info("\n Process Multiple Items for Update.");

        XMLFileProcessUtil xmlFileProcessUtil = new XMLFileProcessUtil();
        List<ResponseResult> responseResultList = new ArrayList<>();

        TransferActionFactory transferActionFactory = new TransferActionFactory();
        TransferObjectUtils transferObjectUtils = new TransferObjectUtils();

        ITransferAction iTransferAction = transferActionFactory.lnTransferFactory(TransferActionType.UPDATE_ITEMS);
        List<ResponseResult> updateItemResponseResultList = (List<ResponseResult>) iTransferAction.processItemsTransfer(context, updateItemBOList, transferObjectUtils);
        TRANSFER_ACTION_LOGGER.debug("Update ITEM transfer response list :: " + updateItemResponseResultList);
        responseResultList.addAll(updateItemResponseResultList);

        boolean unspecifiedErrorOccurredDuringUpdateItems = false;
        ArrayList<String> checkUnspecifiedErrorItemList = new ArrayList<>();

        for (ResponseResult result : responseResultList) {
            if (result != null) {
                String itemName = result.getItem();
                if (!result.isSuccessful()) {
                    File file_move_error = updateItemFileMap.get(itemName);
                    if (file_move_error != null) {
                        updateItemFileMap.remove(itemName);
                        TRANSFER_ACTION_LOGGER.info(itemName + " : Item Failed to update. Transfer result : " + result.getResultText());
                        TRANSFER_ACTION_LOGGER.info(file_move_error.getName() + " : Moving File to Error Directory.");
                        xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file_move_error);
                    }
                }
                checkUnspecifiedErrorItemList.add(itemName);
            } else {
                responseResultList.remove(null);
                unspecifiedErrorOccurredDuringUpdateItems = true;
                TRANSFER_ACTION_LOGGER.info("Response NULL. Unspecified error occurred during Item Update. Probably no Item name from LN response.");
            }
        }

        //Unspecified error is very rare. Occurs if LN failed to provide response without Item name.
        if (unspecifiedErrorOccurredDuringUpdateItems) {
            for (BusinessObject bo : updateItemBOList) {
                bo.open(context);
                String itemName = bo.getName();
                itemName = itemName.toUpperCase();
                if (!checkUnspecifiedErrorItemList.contains(itemName)) {
                    ResponseResult unspecifiedResponseResult = new ResponseResult(itemName, bo.getRevision(),
                            "Unspecified error occurred during update. Probably no Item name from LN response. ", false);
                    responseResultList.add(unspecifiedResponseResult);
                    File file_move_error = updateItemFileMap.get(itemName);
                    if (file_move_error != null) {
                        updateItemFileMap.remove(itemName);
                        TRANSFER_ACTION_LOGGER.info(itemName + " : Item Update failed. Unspecified error occurred during Item Update. Probably no Item name from LN response.");
                        TRANSFER_ACTION_LOGGER.info(file_move_error.getName() + " : Moving File to Error Directory.");
                        xmlFileProcessUtil.moveBusinessObjectXmlFilesToUnprocessedDirectory(file_move_error);
                    }
                }
                bo.close(context);
            }
        }

        ResponseResultSender responseResultSender = ResultSenderFactory.getResultSender(SenderType.LN_TRANSFER_RESPONSE_SENDER);
        responseResultSender.initializeResultSender();
        responseResultSender.sendMultipleMail(context, responseResultList, updateBusinessObjectNameMap, updateItemFileInfoMap);

        // Moving all successfully processed file to old directory.
        for (String itemName : updateItemFileMap.keySet()) {
            File file_move_old = updateItemFileMap.get(itemName);
            if (file_move_old != null) {
                TRANSFER_ACTION_LOGGER.info(itemName + " : Item Updated successfully.");
                TRANSFER_ACTION_LOGGER.info(file_move_old.getName() + " : Moving file to Old directory.");
                xmlFileProcessUtil.removeBusinessObjectXmlFilesToOldDirectory(file_move_old);
            }
        }
    }

    /**
     * @param context
     * @param bo
     * @param transferObjectUtils
     * @throws Exception
     */
    public void initializeTheTransferUtilityClasses(Context context, BusinessObject bo, TransferObjectUtils transferObjectUtils) throws Exception {
        try {
            transferObjectUtils.__init__(context, bo);
        } catch (Exception exp) {
            TRANSFER_ACTION_LOGGER.error("Error in initializing the transferObjectUtils.");
            throw exp;
        }
    }
}
