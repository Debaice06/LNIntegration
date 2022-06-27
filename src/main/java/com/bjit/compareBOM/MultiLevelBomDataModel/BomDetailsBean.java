package com.bjit.compareBOM.MultiLevelBomDataModel;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BomDetailsBean {

    @SerializedName("name")
    private String name;
    @SerializedName("revision")
    private String revision;
    @SerializedName("drawingNumber")
    private String drawingNumber;
    @SerializedName("inventoryUnit")
    private String inventoryUnit;
    @SerializedName("group")
    private String group;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("signalCode")
    private String signalCode;
    @SerializedName("selectionCode")
    private String selectionCode;
    @SerializedName("productType")
    private String productType;
    @SerializedName("note")
    private String note;
    @SerializedName("level")
    private String level;
    @SerializedName("BOMLines")
    private List<BomLineBean> bomLines;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getDrawingNumber() {
        return drawingNumber;
    }

    public void setDrawingNumber(String drawingNumber) {
        this.drawingNumber = drawingNumber;
    }

    public String getInventoryUnit() {
        return inventoryUnit;
    }

    public void setInventoryUnit(String inventoryUnit) {
        this.inventoryUnit = inventoryUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSignalCode() {
        return signalCode;
    }

    public void setSignalCode(String signalCode) {
        this.signalCode = signalCode;
    }

    public String getSelectionCode() {
        return selectionCode;
    }

    public void setSelectionCode(String selectionCode) {
        this.selectionCode = selectionCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<BomLineBean> getBomLines() {
        if (bomLines == null) {
            bomLines = new ArrayList<BomLineBean>();
        }
        return this.bomLines;
    }

    public void setBomLines(List<BomLineBean> bomLines) {
        this.bomLines = bomLines;
    }

}
