import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DebitCard {
    //data members
    private String debitCardNo;
    private String debitCardExpirationDate;
    private String cvv;

    //constructor
    public DebitCard(){

    }

    public DebitCard(String debitCardNo, String debitCardExpirationDate, String cvv){
        this.debitCardNo = debitCardNo;
        this.debitCardExpirationDate = debitCardExpirationDate;
        this.cvv = cvv;
    }

    //getter
    public String getDebitCardNo() {
        return debitCardNo;
    }
    public String getDebitCardExpirationDate() {
        return debitCardExpirationDate;
    }
    public String getCvv() {
        return cvv;
    }

    //setter
    public void setDebitCardNo(String debitCardNo) {
        this.debitCardNo = debitCardNo;
    }
    public void setDebitCardExpirationDate(String debitCardExpirationDate) {
        this.debitCardExpirationDate = debitCardExpirationDate;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    //methods
    //=============================================== Debit Card Validation ===========================================
    public boolean debitCardNumValidation(DebitCard dc){

        if (!dc.debitCardNo.matches("\\d{12}")){
            System.out.println("Please enter only digit for debit card number.\n");
            return false;
        }
        else {
            return true;
        }
    }

    //============================================== Date Validation ===================================================
    public boolean checkExpirationDate(DebitCard dc){
        boolean valid = false;
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //set the date formatter with (yyyy-MM-dd) format

        if (!dc.debitCardExpirationDate.matches("\\d{2}/\\d{2}")){
            System.out.println("Invalid expiration date format!! Please enter again in this format (eg. 01/24).");
            return valid;
        }

        String expirationDate = "20" + dc.debitCardExpirationDate.substring(3,5) + "-" + dc.debitCardExpirationDate.substring(0,2) + "-31"; //combine the string to (yyyy-MM-dd) format

        LocalDate expiry = LocalDate.parse(expirationDate, originalFormatter); //change the date format to (MM/yy)

        LocalDate currentDate = LocalDate.now();  //declare current date

        if (expiry.isBefore(currentDate)){  //check whether the credit card is expired
            System.out.println("This debit card is expired!! Please try another one.\n");
            return valid;
        }
        else {
            return true;
        }
    }

    //================================================ Check CVV =======================================================
    public boolean checkCvv(DebitCard dc){
        if (!dc.cvv.matches("\\d{3}")){
            System.out.println("Invalid CVV! Please try again.\n");
            return false;
        }
        else {
            return true;
        }
    }

    //=========================================== Add Record To Database ===============================================
    public void addDatabaseDebitCard(DebitCard dc) throws SQLException, ClassNotFoundException {

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
        statement = con.prepareStatement("INSERT INTO DEBITCARD (debitCardNo, debitCardExpirationDate, cvv) VALUES (?, ?, ?)");  //update the usedStatus of the voucher to "used"
        statement.setString(1, dc.debitCardNo);
        statement.setString(2, dc.debitCardExpirationDate);
        statement.setString(3, dc.cvv);

        statement.executeUpdate();

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
    }
}
