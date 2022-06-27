package com.bjit.ex.integration.model.webservice;

import java.io.Serializable;

public class TNR implements Serializable, Cloneable, Comparable<TNR> {

    private String type;
    private String name;
    private String revision;

    public TNR() {

    }

    public TNR(String type, String name, String revision) {
        this.type = type;
        this.name = name;
        this.revision = revision;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Override
    public String toString() {
        return "Type : '" + this.getType() + "' Name : '" + this.getName() + "' Revision : '" + this.getRevision() + "'";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(TNR tnr) {
        return this.getType().equalsIgnoreCase(tnr.getType()) && this.getName().equalsIgnoreCase(tnr.getName()) && this.getRevision().equalsIgnoreCase(tnr.getRevision()) ? 0 : 1;
    }
}