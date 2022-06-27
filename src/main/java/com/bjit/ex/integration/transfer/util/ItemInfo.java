package com.bjit.ex.integration.transfer.util;

public class ItemInfo {
    private String itemName;
    private String itemRev;
    private String itemId;
    private String currentState;
    private String nextState;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemRev() {
        return itemRev;
    }

    public void setItemRev(String itemRev) {
        this.itemRev = itemRev;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getNextState() {
        return nextState;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }
}
