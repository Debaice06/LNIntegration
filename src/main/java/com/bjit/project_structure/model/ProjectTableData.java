/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.project_structure.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author BJIT
 */
public class ProjectTableData {
    @SerializedName("TableData")
    @Expose
    private ActivitiesBean tableData;

    public ActivitiesBean getTableData() {
        return tableData;
    }

    public void setTableData(ActivitiesBean tableData) {
        this.tableData = tableData;
    }
}
