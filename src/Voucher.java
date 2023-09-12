public class Voucher {

    //data member
    private String voucherCode;
    private String voucherExpiredDate;
    private String voucherTitle;
    private double voucherRate;

    public Voucher(){
        this("", "", "", 0);
    }

    public Voucher(String voucherCode, String voucherExpiredDate, String voucherTitle, double voucherRate){
        this.voucherCode = voucherCode;
        this.voucherExpiredDate = voucherExpiredDate;
        this.voucherTitle = voucherTitle;
        this.voucherRate = voucherRate;

    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public String getVoucherExpiredDate() {
        return voucherExpiredDate;
    }

    public String getVoucherTitle() {
        return voucherTitle;
    }

    public double getVoucherRate() {
        return voucherRate;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public void setVoucherExpiredDate(String voucherExpiredDate) {
        this.voucherExpiredDate = voucherExpiredDate;
    }

    public void setVoucherTitle(String voucherTitle) {
        this.voucherTitle = voucherTitle;
    }

    public void setVoucherRate(double voucherRate) {
        this.voucherRate = voucherRate;
    }

}
