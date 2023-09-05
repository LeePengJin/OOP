import java.sql.*;

public class database {

    public static void main(String[] args)
            throws SQLException, ClassNotFoundException
    {
        String driverClassName
                = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query
                = ""; //code here

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(
                url, username, password);

        // Obtain a statement
        Statement st = con.createStatement();

        // Execute the query //store into database
        //  int count = st.executeUpdate(query);
        //        System.out.println(
        //                "number of rows affected by this query= "
        //                        + count);

        //to print out the data(getString(1) mean column 1)
        /*        ResultSet rs = st.executeQuery("SELECT FROM person;");
        while (rs.next()) {
            System.out.println(rs.getString(1)); //print results
        }
        */

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
    }
} // class