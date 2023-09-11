import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class GiftCard {
    //data members
    private String giftCardCode;
    private double balance;

    //constructor
    public GiftCard(){

    }

    public GiftCard(String giftCardCode, double balance){
        this.giftCardCode = giftCardCode;
        this.balance = balance;
    }

    //getter
    public String getGiftCardCode() {
        return giftCardCode;
    }
    public double getBalance() {
        return balance;
    }

    //setter
    public void setGiftCardCode(String giftCardCode) {
        this.giftCardCode = giftCardCode;
    }
    public void setBalance(double giftCardValue) {
        this.balance = balance;
    }

    //methods
    //============================================== Prompt Payment Detail =============================================
    public double promptPaymentDetail(GiftCard gc, double amount) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        boolean validGiftCard = false;

        do {
            System.out.print("Please enter the gift card code (B to exit) : ");
            String inputGiftCardCode = sc.next();
            if (inputGiftCardCode.equalsIgnoreCase("B"))
                break;

            if (!inputGiftCardCode.matches("G\\d{4}")) {
                System.out.println("Invalid gift card code format! Please try again.");
            }
            else if(!gc.getDatabaseGiftCard(gc, inputGiftCardCode)) {
                System.out.println("Gift card not found! Please try again.");
            }
            else {
                validGiftCard = giftCardValidation(gc);
            }

        } while(!validGiftCard);

        if (validGiftCard){
            amount = calAmount(gc, amount);
            System.out.println("Transaction completed.");
            gc.editDatabaseGiftCard(gc, amount);
            return amount;
        }
        else {
            return amount;
        }

        //retrieve the gift card value from the database based on the gift card code
        //sql code
    }

    //============================================ Get Database Gift Card ==============================================
    public boolean getDatabaseGiftCard(GiftCard gc, String inputGiftCardCode) throws SQLException, ClassNotFoundException {
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
        statement = con.prepareStatement("SELECT * FROM GIFTCARD WHERE GIFTCARDCODE = ?");  //retrieve all the information of the voucher from database
        statement.setString(1, inputGiftCardCode);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            if (inputGiftCardCode.equals(rs.getString(1))) {
                result = 1;
                //store the voucher information into the voucher object
                gc.giftCardCode = rs.getString(1);
                gc.balance = rs.getDouble(2);
            }
        }

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();

        if (result >= 1) {
            return valid = true;
        } else {
            return valid;
        }
    }

    //=============================================== Gift Card Validation =============================================
    public boolean giftCardValidation(GiftCard gc) {
        if (balance == 0) {
            System.out.println("Insufficient gift card balance! Please try another gift card or another payment method.");
            return false;
        }
        else {
            return true;
        }
    }

    //============================================= Edit Database Gift Card ============================================
    public void editDatabaseGiftCard(GiftCard gc, double amount) throws SQLException, ClassNotFoundException {
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
        statement = con.prepareStatement("UPDATE GIFTCARD SET balance = ? WHERE giftCardCode = ?");  //update the usedStatus of the voucher to "used"
        statement.setDouble(1, gc.balance);
        statement.setString(2, gc.giftCardCode);
        statement.executeUpdate();

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
    }

    //=================================================== calAmount ====================================================
    public double calAmount(GiftCard gc, double amount){
        double finalAmount;
        if (gc.balance > amount){
            gc.balance -= amount;
            finalAmount = 0;

        }
        else if(gc.balance < amount){
            finalAmount = amount - gc.balance;
            gc.balance = 0;
        }
        else {
            finalAmount = amount - gc.balance;
            gc.balance -= finalAmount;
        }

        return finalAmount;
    }
}
