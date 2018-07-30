package com.example.pratishparija.pos.models;

public class PriceRule {
    private String priceRuleId;
    private String priceValue;
    private String validFrom;
    private String validThrough;
    private String createdOn;
    private String createdBy;
    private String lastModifiedDate;
    private String lastModifiedBy;

    public PriceRule() {
    }

    public String getPriceRuleId() {
        return priceRuleId;
    }

    public void setPriceRuleId(String priceRuleId) {
        this.priceRuleId = priceRuleId;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidThrough() {
        return validThrough;
    }

    public void setValidThrough(String validThrough) {
        this.validThrough = validThrough;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
