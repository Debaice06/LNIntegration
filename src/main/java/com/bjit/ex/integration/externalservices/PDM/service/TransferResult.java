
package com.bjit.ex.integration.externalservices.PDM.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferResult {

    @SerializedName("Transfer-Results")
    @Expose
    private TransferResults transferResults;
    @SerializedName("Error Message")
    @Expose
    private String errorMessage;

    public TransferResults getTransferResults() {
        return transferResults;
    }

    public void setTransferResults(TransferResults transferResults) {
        this.transferResults = transferResults;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
