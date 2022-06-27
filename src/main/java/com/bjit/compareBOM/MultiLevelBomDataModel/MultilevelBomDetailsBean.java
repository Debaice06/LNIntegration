package com.bjit.compareBOM.MultiLevelBomDataModel;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultilevelBomDetailsBean {
    
    @Expose
    @SerializedName("source")
    private String source;
    
    @SerializedName("structure")
    @Expose
    private BomLineBean structure;

    public MultilevelBomDetailsBean() {
        this.source = "LN";
    }

    public BomLineBean getStructure() {
        return this.structure;
    }

    public void setStructure(BomLineBean bomDetails) {
        this.structure = bomDetails;
    }
    
    public String getSource() {
        return source;
    }
}
