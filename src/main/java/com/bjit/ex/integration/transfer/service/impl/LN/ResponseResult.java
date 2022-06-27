/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service.impl.LN;

import com.bjit.ex.integration.transfer.actions.utilities.SelectionCodeType;

/**
 *
 * @author Sajjad
 */
public class ResponseResult {
    private String id;
    private String item;
    private String revision;
    private String resultText;
    private boolean successful;
    private SelectionCodeType selectionCodeType;

    
     public ResponseResult() {

    }
    public ResponseResult(String item, String resultText, boolean successful) {
        this.item = item;
        this.resultText = resultText;
        this.successful = successful;
    }

    public ResponseResult(String item, String revision, String resultText, boolean successful) {
        this.item = item;
        this.revision = revision;
        this.resultText = resultText;
        this.successful = successful;
    }

    public ResponseResult(String item, String revision, SelectionCodeType selectionCodeType) {
        this.item = item;
        this.revision = revision;
        this.selectionCodeType = selectionCodeType;
    }

    public SelectionCodeType getSelectionCodeType() {
        return selectionCodeType;
    }

    public void setSelectionCodeType(SelectionCodeType selectionCodeType) {
        this.selectionCodeType = selectionCodeType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getRevision() {
        return this.revision == null? "" : this.revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }
}
