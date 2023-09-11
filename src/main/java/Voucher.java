import java.time.LocalDate;
import java.sql.*;
import java.time.ZoneId;
import java.util.Scanner;

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

    public Voucher(String voucherCode, String voucherDesc, double voucherMinSpend, double voucherRate, String usedStatus){
        this.voucherCode = voucherCode;
        this.voucherDesc = voucherDesc;
        this.voucherMinSpend = voucherMinSpend;
        this.voucherRate = voucherRate;
        this.usedStatus = usedStatus;
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
    //=============================================== Apply Voucher ====================================================
    public double applyVoucher(Voucher vc, double amount, LocalDate paymentDate){  //receive object vc, amount and payment date as parameter
        Scanner sc = new Scanner(System.in);
        boolean validVoucher = false;

        do {
            System.out.print("Please enter the voucher code (B to exit) : ");  //prompt voucher code
            String inputVoucherCode = sc.next();
            if (inputVoucherCode.equalsIgnoreCase("B")){  //if user input b, back to the previous page
                break;
            }

            //retrieve from database and check whether it is existed
            try {
                if (!inputVoucherCode.matches("V\\d{3}")){
                    System.out.println("Invalid voucher code format! Please try again.");
                }
                else if(!getDatabaseVoucher(vc, inputVoucherCode)) {  //if not exists, prompt error message
                    System.out.println("Voucher not found! Please try again.\n");
                }
                else {
                    validVoucher = voucherValidation(vc, amount, paymentDate);  //if exists, do validation for voucher
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        } while(!validVoucher);  //if invalid voucher, prompt user voucher code again

        if (validVoucher){  //if the voucher is valid, display voucher applied successfully message
            System.out.println("Voucher applied successfully.");
            return amount -= (amount * vc.voucherRate);  //return the amount after discount
        }
        else {
            return amount;  //if the voucher is invalid, return the original amount
        }
    }

    //============================================= Voucher Validation =================================================
    public boolean voucherValidation(Voucher vc, double amount, LocalDate paymentDate) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
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
            System.out.print("Confirm to use this voucher? (Y = yes, N = no) : ");  //ask user to confirm apply the voucher
            String confirm = sc.next();
            if (!confirm.equalsIgnoreCase("y")){  //if no, voucher will not be applied and the amount will remain the same
                return false;
            }
            else {
                vc.editDatabaseVoucher(vc);  //if yes, edit the usedStatus of the voucher as "used" in the database
                return true;
            }
        }
    }

    //============================================ Get Database Gift Card ==============================================
    public boolean getDatabaseVoucher(Voucher vc, String inputVoucherCode) throws SQLException, ClassNotFoundException {
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
                result = 1;
                //store the voucher information into the voucher object
                vc.voucherCode = rs.getString(1);
                vc.voucherStartDate = LocalDate.parse(rs.getString(2));
                vc.voucherExpiredDate = LocalDate.parse(rs.getString(3));
                vc.voucherDesc = rs.getString(4);
                vc.voucherMinSpend = rs.getDouble(5);
                vc.voucherRate = rs.getDouble(6);
                vc.usedStatus = rs.getString(7);
            }
        }

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();

        if (result >= 1){
            return valid = true;
        }
        else {
            return valid;
        }
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

        System.out.println(vc.voucherCode);

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