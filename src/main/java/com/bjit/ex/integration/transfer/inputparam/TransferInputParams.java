/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.inputparam;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.db.RelationshipWithSelect;

/**
 *
 * @author Sajjad
 */
public class TransferInputParams {
    private Map TrasnferObjMap ;
    private String transferType;
    private HashMap<String,RelationshipWithSelect> missingVALItemMap;
    private LinkedHashMap<String, BusinessObject> itemIdMap;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public HashMap<String, RelationshipWithSelect> getMissingVALItemMap() {
        return missingVALItemMap;
    }

    public void setMissingVALItemMap(HashMap<String, RelationshipWithSelect> missingVALItemMap) {
        this.missingVALItemMap = missingVALItemMap;
    }
    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
    public Map getTrasnferObjMap() {
        return TrasnferObjMap;
    }

    public void setTrasnferObjMap(Map TrasnferObjMap) {
        this.TrasnferObjMap = TrasnferObjMap;
    }

    public LinkedHashMap<String, BusinessObject> getItemIdMap() {
        return itemIdMap;
    }

    public void setItemIdMap(LinkedHashMap<String, BusinessObject> itemIdMap) {
        this.itemIdMap = itemIdMap;
    }
}
