package com.bjit.ex.integration.model.webservice;

import java.util.Map;

public class Item {
    private String id;
    private TNR tnr;
    private Map<String, String> attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TNR getTnr() {
        return tnr;
    }

    public void setTnr(TNR tnr) {
        this.tnr = tnr;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", tnr=" + tnr + ", attributes=" + attributes + '}';
    }
}
