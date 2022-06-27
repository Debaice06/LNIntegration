/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.compareBOM.Action;

import com.bjit.compareBOM.MultiLevelBomDataModel.BomLineBean;
import com.bjit.compareBOM.MultiLevelBomDataModel.MultilevelBomDetailsBean;
import com.bjit.compareBOM.service.LN.MultilevelBOMDetails;
import com.bjit.project_structure.utilities.NullOrEmptyChecker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infor.businessinterface.multilevelbomdetails_val.GetBOMDetailsRequestType;
import com.infor.businessinterface.multilevelbomdetails_val.GetBOMDetailsResponseType;
import com.infor.businessinterface.multilevelbomdetails_val.Message;
import com.infor.businessinterface.multilevelbomdetails_val.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.bjit.context.ContextGeneration;
import static com.bjit.context.ContextGeneration.context;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import matrix.db.Context;

/**
 *
 * @author Sajjad
 */
public class BOMCompareAction {

    static final org.apache.log4j.Logger BOM_COMPARE_ACTION = org.apache.log4j.Logger
            .getLogger(BOMCompareAction.class.getName());

    public BOMCompareAction() {
    }

    public BOMCompareAction(String physicalID) {
    }

    public BOMCompareAction(String type, String name, String rev, String level) {
        fatchMultiLevelBOMfromLN(type, name, rev, level);
    }

    public MultilevelBomDetailsBean fatchMultiLevelBOMfromLN(String type, String name, String rev, String level) {
        GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL multilevelBOMDetailsVal = new GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL();
        multilevelBOMDetailsVal.setItem(name);
        multilevelBOMDetailsVal.setLevel(level);
        List<GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL> multilevelBOMDetailsValList = new ArrayList<GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL>();
        multilevelBOMDetailsValList.add(multilevelBOMDetailsVal);
        MultilevelBomDetailsBean multilevelBomDetailsBean = new MultilevelBomDetailsBean();
        MultilevelBOMDetails multiBOM = new MultilevelBOMDetails();
        try {
            GetBOMDetailsResponseType bomDetailsResponse = multiBOM.getBOMDetailsFromLn(multilevelBOMDetailsValList);
            if (bomDetailsResponse != null) {
                //multilevelBomDetailsBean = processBOMDetailsResponse(bomDetailsResponse);
                multilevelBomDetailsBean = processBOMDetailsResponse(bomDetailsResponse);

            } else {
                BOM_COMPARE_ACTION.error("BOM Details response found NULL!");
            }
        } catch (Result ex) {
            Logger.getLogger(BOMCompareAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return multilevelBomDetailsBean;
    }

    /*public static MultilevelBomDetailsBean processBOMDetailsResponse(GetBOMDetailsResponseType BOMDetailsResponse) {
        MultilevelBomDetailsBean multilevelBomDetailsBean = new MultilevelBomDetailsBean();
        ArrayList<BomLineBean> lineTrackerList = new ArrayList<>();
        try {
            List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> multiLevelBOMDetailsList = BOMDetailsResponse
                    .getDataArea().getMultilevelBOMDetailsVAL();
            Map<String, GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> itemNameBOMDetailsMap = getItemNameBOMDetailsMap(multiLevelBOMDetailsList);
            processBOMResponse(BOMDetailsResponse);
            String rootItem = multiLevelBOMDetailsList.get(0).getItem().trim();
            BOM_COMPARE_ACTION.error("Root Item: " + rootItem);
            for (GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL multiLevelBOMDetails : multiLevelBOMDetailsList) {
                BomLineBean rootBomLine = new BomLineBean();
                if (rootItem.equals(multiLevelBOMDetails.getItem().trim())) {
                    rootBomLine.setType(multiLevelBOMDetails.getItemType().trim());
                    rootBomLine.setName(multiLevelBOMDetails.getItem().trim());
                    rootBomLine.setRevision(multiLevelBOMDetails.getItemRevision().trim());
                    rootBomLine.setPosition("");
                    rootBomLine.setQty("");
                    BOM_COMPARE_ACTION.error("Root item initialized.");
                    for (GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails bomLineDetils : multiLevelBOMDetails.getBOMDetails()) {
                        BOM_COMPARE_ACTION.error("Root item iteration begins.");
                        BomLineBean bomLine = new BomLineBean();
                        bomLine.setType(bomLineDetils.getItemType().trim());
                        bomLine.setName(bomLineDetils.getComponentItem().trim());
                        bomLine.setRevision(bomLineDetils.getComponentRevision().trim());
                        bomLine.setPosition(bomLineDetils.getPosition().toString());
                        bomLine.setQty(bomLineDetils.getNetQuantity().toString());
                        if (bomLine.getBomLines() == null) {
                            bomLine.setBomLines(new ArrayList<>());
                        }
                        //is parent root check for this. i.e. these are level 1 items. 
                        // All level 1 items get added.
                        if (rootItem.equals(multiLevelBOMDetails.getItem().trim())) {
                            if (rootBomLine.getBomLines() == null) {
                                rootBomLine.setBomLines(new ArrayList<>());
                            }
                            rootBomLine.getBomLines().add(bomLine);
                            //level 1 items getting added in tracker list.
                            lineTrackerList.add(bomLine);
                        }
                    }
                    if (rootItem.equals(multiLevelBOMDetails.getItem().trim())) {
                        multilevelBomDetailsBean.setStructure(rootBomLine);
                    }
                } else {
                    // it means we have started checking the child boms
                    for (int i = 0; i < lineTrackerList.size(); i++) {
                        BomLineBean currentBOMLine = lineTrackerList.get(i);
                        if (currentBOMLine.getName().equals(multiLevelBOMDetails.getItem().trim())) {
                            for (GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails bomLineDetils : multiLevelBOMDetails.getBOMDetails()) {
                                BomLineBean bomLine = new BomLineBean();
                                bomLine.setType(bomLineDetils.getItemType().trim());
                                bomLine.setName(bomLineDetils.getComponentItem().trim());
                                bomLine.setRevision(bomLineDetils.getComponentRevision().trim());
                                bomLine.setPosition(bomLineDetils.getPosition().toString());
                                bomLine.setQty(bomLineDetils.getNetQuantity().toString());
                                if (bomLine.getBomLines() == null) {
                                    bomLine.setBomLines(new ArrayList<>());
                                }
                                currentBOMLine.getBomLines().add(bomLine);
                            }
                            lineTrackerList.remove(currentBOMLine);
                        }
                    }
                }
            }

            ObjectMapper objMapper = new ObjectMapper();
            String jsonMultilevelBOMVal = objMapper.writeValueAsString(multilevelBomDetailsBean);
            BOM_COMPARE_ACTION.info(jsonMultilevelBOMVal);

        } catch (IOException e) {
            e.printStackTrace();
            BOM_COMPARE_ACTION.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            BOM_COMPARE_ACTION.error(e.getMessage());
        }
        return multilevelBomDetailsBean;
    }*/
    public static MultilevelBomDetailsBean processBOMDetailsResponse(GetBOMDetailsResponseType BOMDetailsResponse) {
        MultilevelBomDetailsBean multilevelBomDetailsBean = new MultilevelBomDetailsBean();
        try {
            checkForErrorResponse(BOMDetailsResponse);
            getrRootBOMLines(BOMDetailsResponse, multilevelBomDetailsBean);

            ObjectMapper objMapper = new ObjectMapper();
            String jsonMultilevelBOMVal = objMapper.writeValueAsString(multilevelBomDetailsBean);
            BOM_COMPARE_ACTION.info(jsonMultilevelBOMVal);
        } catch (IOException e) {
            e.printStackTrace();
            BOM_COMPARE_ACTION.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            BOM_COMPARE_ACTION.error(e.getMessage());
        }
        return multilevelBomDetailsBean;
    }

    public static Map<String, GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> getItemNameBOMDetailsMap(List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> multiLevelBOMDetailsList) {
        Map<String, GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> itemNameBOMDetailsMap = new HashMap<>();
        for (int i = 0; i < multiLevelBOMDetailsList.size(); i++) {
            GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL multiLevelBOMDetails = multiLevelBOMDetailsList.get(i);
            itemNameBOMDetailsMap.put(multiLevelBOMDetails.getItem().trim(), multiLevelBOMDetails);
        }
        return itemNameBOMDetailsMap;
    }

    public static void getrBOMDetailsByItemName(BomLineBean BOMLine, Map<String, GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> itemNameBOMDetailsMap) {
        Context context = null;
        try {

            context = ContextGeneration.createContext();

            GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL itemBOMDetails = itemNameBOMDetailsMap.get(BOMLine.getName());
            if (itemBOMDetails != null) {
                if (!NullOrEmptyChecker.isNullOrEmpty(itemBOMDetails.getBOMDetails())) {
                    for (GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL.BOMDetails bomLineDetails : itemBOMDetails.getBOMDetails()) {
                        BomLineBean bomLine = new BomLineBean();
                        bomLine.setType(bomLineDetails.getItemType().trim());
                        bomLine.setName(bomLineDetails.getComponentItem().trim());
                        bomLine.setRevision(bomLineDetails.getComponentRevision().trim());
                        bomLine.setPosition(bomLineDetails.getPosition().toString());
                        bomLine.setQty(bomLineDetails.getNetQuantity().toString());
                        bomLine.setSignalCode(bomLineDetails.getSignalCode().trim());
                        bomLine.setItemType(bomLineDetails.getItemType().trim());
                        bomLine.setDrawingNumber(bomLineDetails.getDrawingNumber().trim());
                        BOMLine.getBomLines().add(bomLine);
                        getrBOMDetailsByItemName(bomLine, itemNameBOMDetailsMap);
                    }
                } else {
                    BOMLine.setBomLines(new ArrayList<>());
                }
            } else {
                BOMLine.setBomLines(new ArrayList<>());
            }
        } catch (Exception exp) {
            exp.printStackTrace();

        } finally {
            context.close();
        }
    }

    public static void getrRootBOMLines(GetBOMDetailsResponseType BOMDetailsResponse, MultilevelBomDetailsBean multilevelBomDetailsBean) {
        List<GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> multilevelBOMDetailsList = BOMDetailsResponse.getDataArea().getMultilevelBOMDetailsVAL();
        Map<String, GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL> itemNameBOMlinesMap = getItemNameBOMDetailsMap(multilevelBOMDetailsList);

        GetBOMDetailsResponseType.DataArea.MultilevelBOMDetailsVAL rootItemBOMDetails = multilevelBOMDetailsList.get(0);

        BomLineBean rootBomLine = new BomLineBean();
        rootBomLine.setType(rootItemBOMDetails.getItemType().trim());
        rootBomLine.setName(rootItemBOMDetails.getItem().trim());
        rootBomLine.setRevision(rootItemBOMDetails.getItemRevision().trim());
        rootBomLine.setSignalCode(rootItemBOMDetails.getSignalCode().trim());
        rootBomLine.setItemType(rootItemBOMDetails.getItemType().trim());
        rootBomLine.setDrawingNumber(rootItemBOMDetails.getDrawingNumber().trim());
        rootBomLine.setPosition("");
        rootBomLine.setQty("");
        multilevelBomDetailsBean.setStructure(rootBomLine);
        getrBOMDetailsByItemName(rootBomLine, itemNameBOMlinesMap);
    }

    public static void checkForErrorResponse(GetBOMDetailsResponseType BOMDetailsResponse) throws Exception {
        if (!NullOrEmptyChecker.isNull(BOMDetailsResponse.getInformationArea())) {
            if (!NullOrEmptyChecker.isNull(BOMDetailsResponse.getInformationArea().getMessage())) {
                StringBuilder errorBuilder = new StringBuilder();
                for (Message msg : BOMDetailsResponse.getInformationArea().getMessage()) {
                    if (!NullOrEmptyChecker.isNullOrEmpty(msg.getMessageText())) {
                        errorBuilder.append(msg.getMessageText());
                    }
                }
                throw new Exception(errorBuilder.toString());
            }
        }
    }
}
