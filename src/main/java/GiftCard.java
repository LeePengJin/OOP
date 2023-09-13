import java.sql.*;

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

    public String getGiftCardCode() {
        return giftCardCode;
    }
    public double getBalance() {
        return balance;
    }

    public void setGiftCardCode(String giftCardCode) {
        this.giftCardCode = giftCardCode;
    }
    public void setBalance(double giftCardValue) {
        this.balance = balance;
    }

    //getter
    //setter
    //methods
    //=============================================== Gift Card Validation =============================================
    public boolean giftCardBalanceValidation(Double giftCardBalance) {
        if (giftCardBalance == 0) {
            System.out.println("Insufficient gift card balance! Please try another gift card or another payment method.");
            return false;
        }
        else {
            return true;
        }
    }

    //=================================================== calAmount ====================================================
    public double calAmount(GiftCard gc, double amount){
        double finalAmount;
        if (gc.balance > amount){  //if the gift card balance is greater than the total amount
            gc.balance -= amount;  //minus the gift card balance by the amount
            finalAmount = 0;       //the total amount will be 0

        }
        else if(gc.balance < amount){  //if the amount is greater than the gift card balance
            finalAmount = amount - gc.balance;  //calculate the remaindering amount by minus the amount by gift card balance
            gc.balance = 0;  //the gift card balance will be 0 as all the balance has been used
        }
        else {  //if the total amount is same with the gift card balance
            finalAmount = amount - gc.balance;  //minus the total amount by the gift card value to get the final total amount
            gc.balance = 0;  //the gift card balance equals to 0 as all the balance has been used to make payment
        }

        return finalAmount;  //return the amount after calculation
    }

    //=========================================== Reload Gift Card Balance =============================================
    /*public void reloadGiftCard(GiftCard gc) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        boolean validGiftCard = false;

        do {
            System.out.print("Please enter the gift card code to reload (B to exit) : ");
            String inputGiftCardCode = sc.next();
            if (inputGiftCardCode.equalsIgnoreCase("B")) {
                break;
            }
            else if (!inputGiftCardCode.matches("G\\d{4}")) {
                System.out.println("Invalid gift card code format! Please try again.");
            }
            else if (GiftCard.getDatabaseGiftCard(inputGiftCardCode)) {
                validGiftCard = true;
            }
            else {
                System.out.println("Gift card not found! Please try again.");
            }

        } while(!validGiftCard);

        if (validGiftCard) {
            boolean validReload = false;
            do {
                System.out.print("Please enter the amount of reload (0 to back) : ");
                String inputReload = sc.next();
                try {
                    Double.parseDouble(inputReload);
                    if (Double.parseDouble(inputReload) < 0 ){
                        System.out.println("The amount of reload cannot be or less than 0. Please try again.");
                    }
                    else if (Double.parseDouble(inputReload) == 0) {
                        break;
                    } else {
                        String confirm;
                        do {
                            System.out.println("Confirm to reload? (Y = yes, N = no) : ");
                            confirm = sc.next();
                            switch (confirm.toUpperCase()){
                                case "Y":
                                    gc.balance += Double.parseDouble(inputReload);
                                    editDatabaseGiftCard(gc);
                                    validReload = true;
                                    break;
                                case "N":
                                    validReload = false;
                                    break;
                                default:
                                    System.out.println("Please enter 'Y' or 'N' only. ");
                                    confirm = sc.next();
                                    if (confirm.equalsIgnoreCase("Y")){
                                        validReload = true;
                                    }
                                    else {
                                        validReload = false;
                                    }
                            }
                        } while (!confirm.equalsIgnoreCase("Y") && !confirm.equalsIgnoreCase("N"));
                    }
                } catch (NumberFormatException e){
                    System.out.println("Please enter number only.");
                }
            } while(!validReload);
        }
    }*/

    //============================================ Get Database Gift Card ==============================================
    public static GiftCard getDatabaseGiftCard(String inputGiftCardCode) throws SQLException, ClassNotFoundException {
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
                //store the voucher information into the voucher object
                return new GiftCard(rs.getString(1), rs.getDouble(2));
            }
        }

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();

        return new GiftCard();
    }

    //============================================= Edit Database Gift Card ============================================
    public void editDatabaseGiftCard(GiftCard gc) throws SQLException, ClassNotFoundException {
        boolean valid = false;

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
}
