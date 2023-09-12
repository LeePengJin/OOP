public class Refund {

    private String refundID;
    private String refundDate;
    private String refundStatus;

    public Refund(){
        this("", "", "");
    }

    public Refund(String refundID, String refundDate, String refundStatus){
        this.refundID = refundID;
        this.refundDate = refundDate;
        this.refundStatus = refundStatus;
    }

    public String getRefundID() {
        return refundID;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundID(String refundID) {
        this.refundID = refundID;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

}
