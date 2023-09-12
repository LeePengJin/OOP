public class EWallet {

    private String phoneNo;

    public EWallet(){
        this("");
    }

    public EWallet(String phoneNo){
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
