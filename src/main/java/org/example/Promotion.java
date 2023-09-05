package org.example;

public class Promotion {
    //data members
    private String promotionCode;
    private String promotionStartDate;
    private String promotionEndDate;
    private String promotionDescription;
    private boolean promotionIsActive;
    private double promotionDiscountRate;

    //constructor
    public Promotion(){
        this("", "", "", "", false, 0);
    }

    public Promotion(String promotionCode, String promotionStartDate, String promotionEndDate, String promotionDescription, boolean promotionIsActive, double promotionDiscountRate){
        this.promotionCode = promotionCode;
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;
        this.promotionDescription = promotionDescription;
        this.promotionIsActive = promotionIsActive;
        this.promotionDiscountRate = promotionDiscountRate;
    }

    //getter
    public String getPromotionCode() {
        return promotionCode;
    }

    public String getPromotionDescription() {
        return promotionDescription;
    }

    public String getPromotionEndDate() {
        return promotionEndDate;
    }

    public String getPromotionStartDate() {
        return promotionStartDate;
    }

    public double getPromotionDiscountRate() {
        return promotionDiscountRate;
    }

    //setter
    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    public void setPromotionEndDate(String promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }

    public void setPromotionIsActive(boolean promotionIsActive) {
        this.promotionIsActive = promotionIsActive;
    }

    public void setPromotionStartDate(String promotionStartDate) {
        this.promotionStartDate = promotionStartDate;
    }

    public void setPromotionDiscountRate(double promotionDiscountRate) {
        this.promotionDiscountRate = promotionDiscountRate;
    }

    //method
}
