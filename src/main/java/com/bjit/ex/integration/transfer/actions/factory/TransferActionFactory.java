/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.factory;

import com.bjit.ex.integration.transfer.actions.transferItems.*;

import java.lang.reflect.InvocationTargetException;

/**
 * @author BJIT
 */
public class TransferActionFactory {
    private static final org.apache.log4j.Logger TRANSFER_ACTION_FACTORY_LOGGER = org.apache.log4j.Logger.getLogger(TransferActionFactory.class);

    /*public ITransferAction lnTransferFactory(String transferItem) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException{
        StringBuilder transferActionName = new StringBuilder();
        transferActionName
                .append("com.bjit.ex.integration.transfer.actions.transferItems.")
                .append("Ln")
                .append(transferItem)
                .append("ItemTransferActionImpl");
        //com.bjit.ex.integration.transfer.actions.transferItems.LnReleasedItemTransferActionImpl
        try {
            ITransferAction transferAction = (ITransferAction) Class.forName(transferActionName.toString()).getConstructor().newInstance();
            return transferAction;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exp) {
            TRANSFER_ACTION_FACTORY_LOGGER.error(exp.getMessage());
            throw exp;
        }
    }*/

    public ITransferAction lnTransferFactory(TransferActionType transferActionType) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        ITransferAction transferAction = null;
        switch (transferActionType) {
            case ITEMS:
            case SELECTION_CODE_UPDATE:
                transferAction = new ItemsTransferActionImpl();
                break;
            case BOM:
                transferAction = new BOMTransferActionImpl();
                break;
            case ITEM_BOM:
                transferAction = new ItemBOMTransferActionImpl();
                break;
            case NIGHTLY_TRANSLATION_UPDATE:
            case UPDATED_ITEM:
            case UPDATE_ITEMS:
                transferAction = new UpdatedItemsTransferActionImpl();
                break;
            case DELIVERABLE:
                transferAction = new DeliverableItemTransferActionImpl();
                break;
            case DISASSEMBLE_ITEM:
            case DISASSEMBLE_STRUCTURE:
                transferAction = new DisassembleTransferActionImpl();
                break;
        }
        
        return transferAction;
    }
}
