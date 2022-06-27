package com.bjit.dw.enovia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class WeightData {
    @SerializedName("TableData")
    @JsonProperty("TableData")
    private List<HashMap<String, String>> data = null;

    @SerializedName("Error")
    @JsonProperty("Error")
    private String error = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<HashMap<String, String>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, String>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TableData{" + "data=" + data + "}";
    }
}
