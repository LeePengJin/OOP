public class GiftCard {

    private String giftCardCode;
    private double giftCardValue;

    public GiftCard(){
        this("", 0);
    }

    public GiftCard(String giftCardCode, double giftCardValue){
        this.giftCardCode = giftCardCode;
        this.giftCardValue = giftCardValue;
    }
    public String getGiftCardCode() {
        return giftCardCode;
    }

    public double getGiftCardValue() {
        return giftCardValue;
    }

    public void setGiftCardCode(String giftCardCode) {
        this.giftCardCode = giftCardCode;
    }

    public void setGiftCardValue(double giftCardValue) {
        this.giftCardValue = giftCardValue;
    }
}
