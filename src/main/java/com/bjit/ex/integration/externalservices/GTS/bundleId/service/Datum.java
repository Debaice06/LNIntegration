
package com.bjit.ex.integration.externalservices.GTS.bundleId.service;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("bundle_id")
    @Expose
    private Integer bundleId;
    @SerializedName("bundle_state")
    @Expose
    private String bundleState;
    @SerializedName("data_list")
    @Expose
    private List<DataList> dataList = null;

    public Integer getBundleId() {
        return bundleId;
    }

    public void setBundleId(Integer bundleId) {
        this.bundleId = bundleId;
    }

    public String getBundleState() {
        return bundleState;
    }

    public void setBundleState(String bundleState) {
        this.bundleState = bundleState;
    }

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }

}
