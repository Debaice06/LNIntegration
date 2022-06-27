
package com.bjit.ex.integration.externalservices.GTS.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("translation_id")
    @Expose
    private String translationId;
    @SerializedName("bundle_id")
    @Expose
    private Integer bundleId;

    public String getTranslationId() {
        return translationId;
    }

    public void setTranslationId(String translationId) {
        this.translationId = translationId;
    }

    public Integer getBundleId() {
        return bundleId;
    }

    public void setBundleId(Integer bundleId) {
        this.bundleId = bundleId;
    }

}
