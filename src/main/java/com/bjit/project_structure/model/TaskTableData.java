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
public class TaskTableData {
    @SerializedName("TableData")
    private TaskBean tableData;

    public TaskBean getTableData() {
        return tableData;
    }

    public void setTableData(TaskBean tableData) {
        this.tableData = tableData;
    }
}
