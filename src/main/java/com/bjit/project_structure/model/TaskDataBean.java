/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.project_structure.model;

import com.bjit.project_structure.utilities.NullOrEmptyChecker;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author BJIT
 */
public class TaskDataBean {
    @SerializedName("TableData")
    @JsonProperty("TableData")
    @Expose
    private List<TaskBean> tableData = null;

    public List<TaskBean> getTableData() {
        return tableData;
    }

    public void setTableData(List<TaskBean> tableData) {
        this.tableData = tableData;
    }
    
//    public void mergeTableData(List<TaskBean> newTableData){
    public void mergeTaskData(TaskDataBean taskDataBean){
        List<TaskBean> newTableData = taskDataBean.getTableData();
        
        if(NullOrEmptyChecker.isNullOrEmpty(newTableData)){
            return;
        }
        
        if(NullOrEmptyChecker.isNullOrEmpty(tableData)){
            setTableData(newTableData);
        }
        else{
            this.getTableData().addAll(newTableData);
        }
    }
}
