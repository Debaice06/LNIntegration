/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.transferItems;

import com.bjit.ex.integration.transfer.actions.utilities.BomUtils;
import com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure.DisassembleDataModel;
import com.bjit.ex.integration.transfer.actions.utilities.TransferObjectUtils;
import com.bjit.ex.integration.transfer.actions.utilities.ItemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import matrix.db.BusinessObject;
import matrix.db.Context;

/**
 *
 * @author BJIT
 */
public interface ITransferAction {
    Object processItemTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils,boolean isService) throws Exception;
    Object processItemsTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils) throws Exception;
    Object processBOMTransfer(Context context, ArrayList<BusinessObject> businessObjectList, TransferObjectUtils transferObjectUtils,boolean isService) throws Exception;
    Object processItemBOMTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils, BomUtils bomUtils,boolean isService) throws Exception;
    Object processTranslationUpdateTransfer(Context context, HashMap itemTranslationMap, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) throws Exception;
    Object processSelectionCodeUpdateTransfer(Context context, ArrayList<BusinessObject> businessObjectList, HashMap itemSelectionCodeMap, TransferObjectUtils transferObjectUtils) throws Exception;
    Object processDeliverableTransfer(Context context, BusinessObject businessObject, DisassembleDataModel disassembleDataModel, TransferObjectUtils transferObjectUtils) throws Exception;
    Object processDisassembleItemsTransfer(Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils, ItemUtils itemUtils) throws Exception;
    Object processDisassembleStructureTransfer (Context context, BusinessObject businessObject, TransferObjectUtils transferObjectUtils,DisassembleDataModel disassembleDataModel)throws Exception;
}
