package com.bjit.dw.enovia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Error {
    @SerializedName("Error")
    @JsonProperty("Error")
    private HashMap<String, String> error = null;

    public HashMap<String, String> getError() {
        return error;
    }

    public void setError(HashMap<String, String> error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Error{" + error + "}";
    }
}
