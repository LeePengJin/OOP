public class Customer extends Person{

    //data member
    private String custID;
    protected Person person;
    private String creditCardNum;
    private String bankAcc;
    private double totalSpend;
    private int storePoint;
    private double storePointRate;

    public Customer(){
        this("", null, "", "", 0, 0, 0);
    }

    public Customer(String custID, Person person, String creditCardNum, String bankAcc, double totalSpend, int storePoint, double storePointRate){
        this.custID = custID;
        this.person = person;
        this.creditCardNum = creditCardNum;
        this.bankAcc = bankAcc;
        this.totalSpend = totalSpend;
        this.storePoint = storePoint;
        this.storePointRate = storePointRate;

    }
    //get n set
    public String getCustID() {
        return custID;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public double getTotalSpend() {
        return totalSpend;
    }

    public int getStorePoint() {
        return storePoint;
    }

    public double getStorePointRate() {
        return storePointRate;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public void setTotalSpend(double totalSpend) {
        this.totalSpend = totalSpend;
    }

    public void setStorePoint(int storePoint) {
        this.storePoint = storePoint;
    }

    public void setStorePointRate(double storePointRate) {
        this.storePointRate = storePointRate;
    }

    //constructor
    public double calSP(double totalSpend, double storePointRate){
        return totalSpend * storePointRate;
    }


}
