package com.bjit.dw.enovia.ex.dw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class DWResponseBean {
    @SerializedName("status")
    @JsonProperty("status")
    private String status;
    @SerializedName("data")
    @JsonProperty("data")
    private List<HashMap<String, String>> data;
    @SerializedName("systemErrors")
    @JsonProperty("systemErrors")
    private List<String> systemErrors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HashMap<String, String>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, String>> data) {
        this.data = data;
    }

    public List<String> getSystemErrors() {
        return systemErrors;
    }

    public void setSystemErrors(List<String> systemErrors) {
        this.systemErrors = systemErrors;
    }
}
