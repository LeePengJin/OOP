import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreditCard {
    //data member
    private String creditCardNo;
    private String creditCardExpirationDate;
    private String cvv;

    //constructor
    public CreditCard(){

    }
    public CreditCard(String creditCardNo, String creditCardExpirationDate, String cvv){
        this.creditCardNo = creditCardNo;
        this.creditCardExpirationDate = creditCardExpirationDate;
        this.cvv = cvv;
    }

    //getter
    public String getCreditCardExpirationDate() {
        return creditCardExpirationDate;
    }
    public String getCreditCardNo() {
        return creditCardNo;
    }
    public String getCvv() {
        return cvv;
    }

    //setter
    public void setCreditCardExpirationDate(String creditCardExpirationDate) {
        this.creditCardExpirationDate = creditCardExpirationDate;
    }
    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    //method
    //============================================= Credit Card Validation =============================================
    public boolean creditCardNumValidation(CreditCard cc){

        if (!cc.creditCardNo.matches("\\d{12}")){  //use regular expression to check the format
            System.out.println("Please enter 12 digits for credit card number.\n");
            return false;
        }
        else {
            return true;
        }
    }

    //============================================== Date Validation ===================================================
    public boolean checkExpirationDate(CreditCard cc){
        boolean valid = false;
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //set the date formatter with (yyyy-MM-dd) format

        if (!cc.creditCardExpirationDate.matches("\\d{2}/\\d{2}")){
            System.out.println("Invalid expiration date format!! Please enter again in this format (eg. 01/24).");
            return valid;
        }

        String expirationDate = "20" + cc.creditCardExpirationDate.substring(3,5) + "-" + cc.creditCardExpirationDate.substring(0,2) + "-31"; //combine the string to (yyyy-MM-dd) format

        LocalDate expiry = LocalDate.parse(expirationDate, originalFormatter); //change the date format to (MM/yy)

        LocalDate currentDate = LocalDate.now();  //declare current date

        if (expiry.isBefore(currentDate)){  //check whether the credit card is expired
            System.out.println("This credit card is expired!! Please try another one.\n");
            return valid;
        }
        else {
            return true;
        }
    }

    //================================================ Check CVV =======================================================
    public boolean checkCvv(CreditCard cc){
        if (!cc.cvv.matches("\\d{3}")){
            System.out.println("Invalid CVV! Please try again.\n");
            return false;
        }
        else {
            return true;
        }
    }

    //=========================================== Add Record To Database ===============================================
    public void addDatabaseCreditCard(CreditCard cc) throws SQLException, ClassNotFoundException {

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
        statement = con.prepareStatement("INSERT INTO CREDITCARD (creditCardNo, creditCardExpirationDate, cvv) VALUES (?, ?, ?)");  //update the usedStatus of the voucher to "used"
        statement.setString(1, cc.creditCardNo);
        statement.setString(2, cc.creditCardExpirationDate);
        statement.setString(3, cc.cvv);

        statement.executeUpdate();

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
    }
}