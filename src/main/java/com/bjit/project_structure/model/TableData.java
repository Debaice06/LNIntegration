/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.project_structure.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author BJIT
 */
public class TableData {

    @SerializedName("Project")
    private String project;

    @SerializedName("Activity")
    private String activity;

    @SerializedName("ActivityDescription")
    private String activityDescription;

    @SerializedName("ActivityStatus")
    private String activityStatus;

    @SerializedName("ActivityType")
    private String activityType;

    @SerializedName("ParentActivity")
    private String parentActivity;

    @SerializedName("AcutalEndDate")
    private String acutalEndDate;

    @SerializedName("NetQuantity")
    private String netQuantity;

    @SerializedName("Unit")
    private String unit;

    @SerializedName("ProductType")
    private String productType;

    @SerializedName("ProductTypeDesc")
    private String productTypeDesc;

    @SerializedName("Pos")
    private String pos;

    @SerializedName("Selectable")
    private String selectable;

    @SerializedName("ContractDeliverableNo")
    private String contractDeliverableNo;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(String parentActivity) {
        this.parentActivity = parentActivity;
    }

    public String getAcutalEndDate() {
        return acutalEndDate;
    }

    public void setAcutalEndDate(String acutalEndDate) {
        this.acutalEndDate = acutalEndDate;
    }

    public String getNetQuantity() {
        return netQuantity;
    }

    public void setNetQuantity(String netQuantity) {
        this.netQuantity = netQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductTypeDesc() {
        return productTypeDesc;
    }

    public void setProductTypeDesc(String productTypeDesc) {
        this.productTypeDesc = productTypeDesc;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getSelectable() {
        return selectable;
    }

    public void setSelectable(String selectable) {
        this.selectable = selectable;
    }

    public String getContractDeliverableNo() {
        return contractDeliverableNo;
    }

    public void setContractDeliverableNo(String contractDeliverableNo) {
        this.contractDeliverableNo = contractDeliverableNo;
    }
}
