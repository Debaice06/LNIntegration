/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service.impl.LN;

/**
 *
 * @author Sajjad
 *
 */
public class RelationshipAttribute {
    private String childName;
    private String childRev;
    private String extraInfo;
    //private String length;    
    private String netQuantity;
    //private String numberOfUnits;
    private String physialPart;
    private String pseudoRow;
    private int position;
    private String SuppliedBySubcontractor;

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildRev() {
        return childRev;
    }

    public void setChildRev(String childRev) {
        this.childRev = childRev;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

//    public String getLength() {
//        return length;
//    }
//
//    public void setLength(String length) {
//        this.length = length;
//    }

    public String getNetQuantity() {
        return netQuantity;
    }

    public void setNetQuantity(String netQuantity) {
        this.netQuantity = netQuantity;
    }

//    public String getNumberOfUnits() {
//        return numberOfUnits;
//    }
//
//    public void setNumberOfUnits(String numberOfUnits) {
//        this.numberOfUnits = numberOfUnits;
//    }

    public String getPhysialPart() {
        return physialPart;
    }

    public void setPhysialPart(String physialPart) {
        this.physialPart = physialPart;
    }

    public String getPseudoRow() {
        return pseudoRow;
    }

    public void setPseudoRow(String pseudoRow) {
        this.pseudoRow = pseudoRow;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getSuppliedBySubcontractor() {
        return SuppliedBySubcontractor;
    }

    public void setSuppliedBySubcontractor(String SuppliedBySubcontractor) {
        this.SuppliedBySubcontractor = SuppliedBySubcontractor;
    }


    
}
