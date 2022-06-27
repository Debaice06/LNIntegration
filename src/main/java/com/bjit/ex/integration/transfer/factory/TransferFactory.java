/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.factory;

import com.bjit.ex.integration.transfer.inputparam.TransferInputParams;
import com.bjit.ex.integration.transfer.service.ITransfer;
import com.bjit.ex.integration.transfer.service.impl.LN.*;


/**
 *
 * @author Sajjad
 */
public class TransferFactory {
    public ITransfer getTransferObj(TransferInputParams transferObj){
        ITransfer transfer = null;
        if(transferObj.getTransferType().equalsIgnoreCase("LN") ){
            transfer = new LNTransfer();
        } else if (transferObj.getTransferType().equalsIgnoreCase("DisassembleItems") ){
            transfer = new LNItemsTransfer();
        } else if(transferObj.getTransferType().equalsIgnoreCase("DeliverableLN")){
            transfer = new LNDeliverableTransfer();
        } else if(transferObj.getTransferType().equalsIgnoreCase("DisassembleStructure")){
            transfer = new LNDisassembleTransfer();
        } else if (transferObj.getTransferType().equalsIgnoreCase("ItemsTransfer")
                || transferObj.getTransferType().equalsIgnoreCase("NightlyUpdateToLN")
                || transferObj.getTransferType().equalsIgnoreCase("UpdatedItem")) {
            transfer = new LNItemsTransfer();
        } else if (transferObj.getTransferType().equalsIgnoreCase("BOMTransfer")) {
            transfer = new LNBOMTransfer();
        }
        else {
            throw new IllegalArgumentException("Transfer Type not Supported!");
        }
        return transfer;
    }
}
