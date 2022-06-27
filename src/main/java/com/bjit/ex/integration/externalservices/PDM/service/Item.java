
package com.bjit.ex.integration.externalservices.PDM.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Transfer-Status")
    @Expose
    private String transferStatus;
    @SerializedName("Result-Message")
    @Expose
    private String resultMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
