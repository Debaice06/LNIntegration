/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.project_structure.model;

import java.util.HashMap;

/**
 *
 * @author BJIT
 */
public class CreateObjectBean{
    private Boolean isAutoName;
    private TNR tnr;
    private HashMap<String, String> attributes;
    private String templateBusinessObjectId;
    private String folderId;
    private Boolean attributeGlobalRead;
    private String cs; //Collaboration Space

    public Boolean getIsAutoName() {
        return isAutoName;
    }

    public void setIsAutoName(Boolean isAutoName) {
        this.isAutoName = isAutoName;
    }
    
    public TNR getTnr() {
        return tnr;
    }

    public void setTnr(TNR tnr) {
        this.tnr = tnr;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getTemplateBusinessObjectId() {
        return templateBusinessObjectId;
    }

    public void setTemplateBusinessObjectId(String templateBusinessObjectId) {
        this.templateBusinessObjectId = templateBusinessObjectId;
    }
    
    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public Boolean getAttributeGlobalRead() {
        return attributeGlobalRead;
    }

    public void setAttributeGlobalRead(Boolean attributeGlobalRead) {
        this.attributeGlobalRead = attributeGlobalRead;
    }
    
    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }
}
