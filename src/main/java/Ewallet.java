import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ewallet {
    //data member
    private String phoneNo;

    //constructor
    public Ewallet(){

    }

    public Ewallet(String phoneNo){
        this.phoneNo = phoneNo;
    }

    //getter
    public String getPhoneNo() {
        return phoneNo;
    }

    //setter
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    //method
    //=============================================== Check PhoneNo ====================================================
    public boolean checkPhoneNo(Ewallet ew){
        if (ew.phoneNo.matches("\\d{3}-\\d{7}|\\d{3}-\\d{8}")){  //check phone number format
            return true;
        }
        else {
            System.out.println("Invalid phone number! Please enter digit only with this format (01X-XXXXXXX or 01X-XXXXXXXX).");  //prompt error message
            return false;
        }
    }

    //=========================================== Add Record To Database ===============================================
    public void addDatabaseEwallet(Ewallet ew) throws SQLException, ClassNotFoundException {

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
        statement = con.prepareStatement("INSERT INTO EWALLET (phoneNo) VALUES (?)");  //update the usedStatus of the voucher to "used"
        statement.setString(1, ew.phoneNo);

        statement.executeUpdate();

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
    }
}
