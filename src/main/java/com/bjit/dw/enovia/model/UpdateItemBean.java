package com.bjit.dw.enovia.model;

import java.util.HashMap;
import java.util.Map;

public class UpdateItemBean extends ItemInfo {

    public UpdateItemBean() {
        this.attributes = new HashMap<>();
    }

    private Map<String, String> attributes;

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
