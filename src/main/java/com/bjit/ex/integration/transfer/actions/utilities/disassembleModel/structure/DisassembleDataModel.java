/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure;

import com.infor.businessinterface.item_val.ProcessItemDataRequestType.DataArea.ItemVAL;
import java.util.ArrayList;
import java.util.List;
import matrix.db.BusinessObject;

/**
 *
 * @author Sajjad
 */
public class DisassembleDataModel {
    private BusinessObject rootItemBO;
    private List<ItemVAL> disassembleItemVALList; 
    private ContractDetailsInfo contractDetailsInfo;
    private ArrayList<ItemDetailInfo> ItemDetails;

    public BusinessObject getRootItemBO() {
        return rootItemBO;
    }

    public void setRootItemBO(BusinessObject rootItemBO) {
        this.rootItemBO = rootItemBO;
    }

    public List<ItemVAL> getDisassembleItemVALList() {
        return disassembleItemVALList;
    }

    public void setDisassembleItemVALList(List<ItemVAL> disassembleItemVALList) {
        this.disassembleItemVALList = disassembleItemVALList;
    }

    public ContractDetailsInfo getContractDetailsInfo() {
        return contractDetailsInfo;
    }

    public void setContractDetailsInfo(ContractDetailsInfo contractDetailsInfo) {
        this.contractDetailsInfo = contractDetailsInfo;
    }

    public ArrayList<ItemDetailInfo> getItemDetails() {
        return ItemDetails;
    }

    public void setItemDetails(ArrayList<ItemDetailInfo> ItemDetails) {
        this.ItemDetails = ItemDetails;
    }    
}
