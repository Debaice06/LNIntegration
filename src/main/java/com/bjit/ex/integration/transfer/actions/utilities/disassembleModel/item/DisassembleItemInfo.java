/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.item;

import matrix.db.BusinessObject;

/**
 *
 * @author Sajjad
 */
public class DisassembleItemInfo {
    private BusinessObject itemBO;
    private BusinessObject parentBO;
    private String level;
    private String position;
    private String parentPosition;
    private String connectionId;
    private boolean connectedRootItem;
    private boolean diassembleMainItem;

    public BusinessObject getItemBO() {
        return itemBO;
    }

    public void setItemBO(BusinessObject itemBO) {
        this.itemBO = itemBO;
    }

    public BusinessObject getParentBO() {
        return parentBO;
    }

    public void setParentBO(BusinessObject parentBO) {
        this.parentBO = parentBO;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(String parentPosition) {
        this.parentPosition = parentPosition;
    }
    
    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public boolean isConnectedRootItem() {
        return connectedRootItem;
    }

    public void setConnectedRootItem(boolean connectedRootItem) {
        this.connectedRootItem = connectedRootItem;
    }

    public boolean isDiassembleMainItem() {
        return diassembleMainItem;
    }

    public void setDiassembleMainItem(boolean diassembleMainItem) {
        this.diassembleMainItem = diassembleMainItem;
    }
    
}
