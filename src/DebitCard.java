public class DebitCard {
    private String debitCardNo;

    public DebitCard(){
        this("");
    }

    public DebitCard(String debitCardNo){
        this.debitCardNo = debitCardNo;
    }
    public String getDebitCardNo() {
        return debitCardNo;
    }

    public void setDebitCardNo(String debitCardNo) {
        this.debitCardNo = debitCardNo;
    }

}
