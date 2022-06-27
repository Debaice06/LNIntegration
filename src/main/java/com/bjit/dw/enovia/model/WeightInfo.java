package com.bjit.dw.enovia.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeightInfo {
    @SerializedName("ITEM")
    @Expose
    private String item;
    @SerializedName("WEIGHT")
    @Expose
    private String weight;
    @SerializedName("WEIGHTUNIT")
    @Expose
    private String weightUnit;
    @SerializedName("LOGISTICCOMPANY")
    @Expose
    private String logsticCompany;
    @SerializedName("PRODUCTTYPE")
    @Expose
    private String productType;
    @SerializedName("Modified_Date")
    @Expose
    private String modifiedDate;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getLogsticCompany() {
        return logsticCompany;
    }

    public void setLogsticCompany(String logsticCompany) {
        this.logsticCompany = logsticCompany;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "WeightInfo{" + "item=" + item + ", weight=" + weight + ", weightUnit=" + weightUnit + ", logsticCompany=" + logsticCompany + ", productType=" + productType + ", modifiedDate=" + modifiedDate + '}';
    }
}
