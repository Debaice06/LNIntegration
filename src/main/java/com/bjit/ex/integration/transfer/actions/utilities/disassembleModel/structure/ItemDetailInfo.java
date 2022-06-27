/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.actions.utilities.disassembleModel.structure;

import java.util.ArrayList;

/**
 *
 * @author Sajjad
 */
public class ItemDetailInfo {
    private String disAssemblyItem;
    private String sortingPosition;
    private String disAssembleQuantity;
    ArrayList<Line> lines;

    public String getDisAssemblyItem() {
        return disAssemblyItem;
    }

    public void setDisAssemblyItem(String disAssemblyItem) {
        this.disAssemblyItem = disAssemblyItem;
    }

    public String getSortingPosition() {
        return sortingPosition;
    }

    public void setSortingPosition(String sortingPosition) {
        this.sortingPosition = sortingPosition;
    }

    public String getDisAssembleQuantity() {
        return disAssembleQuantity;
    }

    public void setDisAssembleQuantity(String disAssembleQuantity) {
        this.disAssembleQuantity = disAssembleQuantity;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }
    
}
