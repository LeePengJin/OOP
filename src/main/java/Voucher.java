import java.time.LocalDate;
import java.sql.*;

public class Voucher {
    //data member
    private String voucherCode;
    private LocalDate voucherStartDate;
    private LocalDate voucherExpiredDate;
    private String voucherDesc;
    private double voucherMinSpend;
    private double voucherRate;
    private String usedStatus;

    //constructor
    public Voucher(){

    }

    public Voucher(String voucherCode, LocalDate voucherStartDate, LocalDate voucherExpiredDate, String voucherDesc, double voucherMinSpend, double voucherRate, String usedStatus){
        this.voucherCode = voucherCode;
        this.voucherStartDate = voucherStartDate;
        this.voucherExpiredDate = voucherExpiredDate;
        this.voucherDesc = voucherDesc;
        this.voucherMinSpend = voucherMinSpend;
        this.voucherRate = voucherRate;
        this.usedStatus = usedStatus;
    }

    //getter
    public String getVoucherCode() {
        return voucherCode;
    }
    public LocalDate getVoucherStartDate() {
        return voucherStartDate;
    }
    public LocalDate getVoucherExpiredDate() {
        return voucherExpiredDate;
    }
    public String getVoucherDesc() {
        return voucherDesc;
    }
    public double getVoucherMinSpend() {
        return voucherMinSpend;
    }
    public double getVoucherRate() {
        return voucherRate;
    }
    public String getUsedStatus() {
        return usedStatus;
    }

    //setter
    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
    public void setVoucherStartDate(LocalDate voucherStartDate) {
        this.voucherStartDate = voucherStartDate;
    }
    public void setVoucherExpiredDate(LocalDate voucherExpiredDate) {
        this.voucherExpiredDate = voucherExpiredDate;
    }
    public void setVoucherDesc(String voucherDesc) {
        this.voucherDesc = voucherDesc;
    }
    public void setVoucherMinSpend(double voucherMinSpend) {
        this.voucherMinSpend = voucherMinSpend;
    }
    public void setVoucherRate(double voucherRate) {
        this.voucherRate = voucherRate;
    }
    public void setUsedStatus(String usedStatus) {
        this.usedStatus = usedStatus;
    }

    //methods
    //============================================= Voucher Validation =================================================
    public boolean voucherValidation(Voucher vc, double amount, LocalDate paymentDate) throws SQLException, ClassNotFoundException {
        if (paymentDate.isAfter(vc.voucherExpiredDate) || paymentDate.isBefore(vc.voucherStartDate)){  //check whether the voucher is valid at the payment date
            System.out.println("This voucher only applicable from " + vc.voucherStartDate + " to " + vc.voucherExpiredDate);
            return false;
        }
        else if (amount < vc.voucherMinSpend) {  //  check whether the amount has exceeded or equal to the voucher minimum spend
            System.out.println("RM" + (vc.voucherMinSpend - amount) + " is needed to reach the minimum spend of RM" + vc.voucherMinSpend);
            return false;
        }
        else if (vc.usedStatus.equalsIgnoreCase("used")){  //check whether the voucher has been used
            System.out.println("Voucher can only be used once! Please try another voucher.");
            return false;
        }
        else {
            return true;
        }
    }

    //============================================ Get Database Gift Card ==============================================
    public static Voucher getDatabaseVoucher(String inputVoucherCode) throws SQLException, ClassNotFoundException {
        boolean valid = false;
        int result = 0;

        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query = "";

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(url, username, password);

        // Obtain a statement
        PreparedStatement statement;
        statement = con.prepareStatement("SELECT * FROM VOUCHER WHERE VOUCHERCODE = ?");  //retrieve all the information of the voucher from database
        statement.setString(1, inputVoucherCode);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            if (inputVoucherCode.equals(rs.getString(1))) {
                //store the voucher information into the voucher object
                return new Voucher(rs.getString(1), LocalDate.parse(rs.getString(2)), LocalDate.parse(rs.getString(3)), rs.getString(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7));
            }
        }

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();

        return new Voucher();
    }

    public void editDatabaseVoucher(Voucher vc) throws SQLException, ClassNotFoundException {
        boolean valid = false;
        int result = 0;

        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query = "";

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(url, username, password);

        // Obtain a statement
        PreparedStatement statement;
        statement = con.prepareStatement("UPDATE VOUCHER SET usedStatus = ? WHERE voucherCode = ?");  //update the usedStatus of the voucher to "used"
        statement.setString(1, "used");
        statement.setString(2, vc.voucherCode);
        statement.executeUpdate();

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();


    }
}