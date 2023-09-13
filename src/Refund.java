import java.util.Date;

public class Refund {
    // Data members
    private String refundID;
    private String paymentID; // Assuming this identifies the order associated with the refund
    private double refundAmount;
    private Date refundDate;

    // Constructors
    public Refund() {
        this("", "", 0.0, new Date());
    }

    public Refund(String refundID, String paymentID, double refundAmount, Date refundDate) {
        this.refundID = refundID;
        this.paymentID = paymentID;
        this.refundAmount = refundAmount;
        this.refundDate = refundDate;
    }

    // Getters
    public String getRefundID() {
        return refundID;
    }
    public String getPaymentID() {
        return paymentID;
    }
    public double getRefundAmount() {
        return refundAmount;
    }
    public Date getRefundDate() {
        return refundDate;
    }

    // Setters
    public void setRefundID(String refundID) {
        this.refundID = refundID;
    }
    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }
    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }
    public void recordRefundDetails() {
        try {
            String driverClassName = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/oop";
            String username = "root";
            String password = "";

            // Load driver class
            Class.forName(driverClassName);

            // Obtain a connection
            Connection con = DriverManager.getConnection(url, username, password);

            // Prepare SQL statement to insert refund details into a refunds table
            String insertQuery = "INSERT INTO refunds (refundID, paymentID, refundAmount, refundDate) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(insertQuery);

            // Set the parameter values for the SQL statement
            statement.setString(1, refundID);
            statement.setString(2, paymentID);
            statement.setDouble(3, refundAmount);
            statement.setDate(4, new java.sql.Date(refundDate.getTime()));

            // Execute the SQL statement to insert refund details
            statement.executeUpdate();

            // Close the connections
            statement.close();
            con.close();

            System.out.println("Refund details recorded successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while recording refund details.");
        }
    }

    private static boolean isPaymentRecordedInRefunds(String paymentID) {
        boolean isRecorded = false;

        try {
            String driverClassName = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/oop";
            String username = "root";
            String password = "";

            // Load driver class
            Class.forName(driverClassName);

            // Obtain a connection
            Connection con = DriverManager.getConnection(url, username, password);

            // Prepare SQL statement to check if paymentID exists in refunds table
            String selectQuery = "SELECT COUNT(*) AS count FROM Refund WHERE paymentID = ?";
            PreparedStatement statement = con.prepareStatement(selectQuery);

            // Set the parameter values for the SQL statement
            statement.setString(1, paymentID);

            // Execute the SQL statement and get the result
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0) {
                    isRecorded = true;
                }
            }

            // Close the connections
            resultSet.close();
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while checking if payment is recorded.");
        }

        return isRecorded;
    }
    public void displayRefundItem(){
        Toy[] toysSelected = new Toy[0];
        double[] newToyPrice = new double[0];
        int[] toyQuantity = new int[0];
        int index = 0;
        Toy.passToPayment(toysSelected, newToyPrice,toyQuantity,index);
    }
}
