public class Payment {

    private String paymentID;
    private double paymentAmount;
    private String paymentDate;
    private String paymentMethod;
    private String paymentStatus;

    public Payment(){
        this("", 0, "", "", "");
    }

    public Payment(String paymentID, double paymentAmount, String paymentDate, String paymentMethod, String paymentStatus){
        this.paymentID = paymentID;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }
    //get n set
    public String getPaymentID() {
        return paymentID;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double calPaymentAmount(double totalAmount){
        return totalAmount;
    }
}
