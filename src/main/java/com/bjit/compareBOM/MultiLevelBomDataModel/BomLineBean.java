package com.bjit.compareBOM.MultiLevelBomDataModel;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class BomLineBean {

    @SerializedName("type")
    private String type;

    @SerializedName("name")
    private String name;

    @SerializedName("revision")
    private String revision;

    @SerializedName("position")
    private String position;

    @SerializedName("qty")
    private String qty;

    @SerializedName("signalCode")
    private String signalCode;

    @SerializedName("itemType")
    private String itemType;

    @SerializedName("drawingNumber")
    private String drawingNumber;

    public BomLineBean() {
        this.bomLines = new ArrayList<>();
    }

    public BomLineBean(String position) {
        this.bomLines = new ArrayList<>();
        this.position = position;
        this.type = "dummy_type";
        this.name = "dummy_obj";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSignalCode() {
        return signalCode;
    }

    public void setSignalCode(String signalCode) {
        this.signalCode = signalCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getDrawingNumber() {
        return drawingNumber;
    }

    public void setDrawingNumber(String drawingNumber) {
        this.drawingNumber = drawingNumber;
    }

    @SerializedName("BOMLines")
    private List<BomLineBean> bomLines;

    public List<BomLineBean> getBomLines() {
        return bomLines;
    }

    public void setBomLines(List<BomLineBean> bomLines) {
        this.bomLines = bomLines;
    }

}
