import java.sql.*;
public class Payment {
    //data members
    private String paymentID;
    private double paymentAmount;
    private String paymentDate;
    private static String paymentMethod;

    private static double cashAmount;
    private static double memberPoints = 0.0;

    private static final double serviceTax = 0.05;
    private static final double sstTax = 0.06;
    private static final double memberPointRate = 0.01;
    private static int paymentCounter = 1;


    //constructor
    public Payment(){
        this("",0.0,"","",0.0);
    }
    public Payment(String paymentID, double paymentAmount, String paymentDate, String paymentMethod, double cashAmount){
        this.paymentID = "P" + String.format("%03d", paymentCounter);
        paymentCounter++;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.cashAmount = cashAmount;
    }

    //getter
    public String getPaymentID() {
        return paymentID;
    }
    public double getPaymentAmount() {
        return paymentAmount;
    }
    public String getPaymentDate() {
        return paymentDate;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public double getCashAmount() {
        return cashAmount;
    }

    //setter
    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public void displayTotalPayment(){
        double amount;
    }

    public static double calcMemberDiscount(double memberPoints){
        return memberPoints;
    }
    public static double calcServiceTax(double totalPayment){
        return totalPayment * serviceTax;
    }

    public static double calcSSTTax(double totalPayment){
        return totalPayment * sstTax;
    }

    public static double calcMemberPoint(double totalPayment){
        return totalPayment * memberPointRate;
    }

    public static void applyMemberDiscount(){
        if (pointsToUse > memberPoints){
            System.out.println("No enough member points");
            return;
        }
        double memberDiscount = calcMemberDiscount(pointsToUse);
        memberPoints -= pointsToUse;

        System.out.println("Applied member points discount: RM" + memberDiscount);
    }


    public static void generateReceipt(String staffName, String paymentID, String paymentDate, double paymentAmount, boolean isCashPayment, String voucherCode, double voucherDiscount, double giftCardDiscount, double memberDiscount){

            double totalServiceTax = calcServiceTax(paymentAmount);
            double totalSSTTax = calcSSTTax(paymentAmount);

            double totalPayment = paymentAmount + totalServiceTax + totalSSTTax;

            if(memberPoints > 0){
                applyMemberDiscount();
            }

            double change = 0.0;
            if(isCashPayment){
                change = cashAmount - totalPayment;
                if(change < 0 ) {
                    System.out.println("Insufficient cash amount. Please provide additional payment.");
                }
            }

            if(totalPayment > 0){
                System.out.println("Remaining amount to be paid: RM" + totalPayment);
                //choose payment method then get additional payment
                System.out.println("Additional Payment Method: " + additionalPaymentMethod);
            }

            System.out.println("Payment Receipt");
            System.out.println("----------------");
            System.out.println("Staff Name: " + staffName);
            System.out.println("Payment ID: " + paymentID);
            System.out.println("Payment Method: " + paymentMethod);
            if(paymentMethod == debitCard){
                System.out.println("Card Number: " + cardNumber);
            }else if(paymentMethod == creditCard){
                System.out.println("Card Number: " + cardNumber);
            }else if(paymentMethod == eWallet){
                System.out.println("Telephone number: " + telNo);
            }
            System.out.println("Payment Date: " + paymentDate);


        Toy[] toysSelected = new Toy[0];
        double[] newToyPrice = new double[0];
        int[] toyQuantity = new int[0];
        int index = 0;
        Toy.passToPayment(toysSelected, newToyPrice,toyQuantity,index);

        if (!voucherCode.isEmpty() && voucherDiscount > 0) {
            System.out.println("Voucher Code: " + voucherCode);
            System.out.println("Voucher Discount: RM" + (paymentAmount * voucherDiscount));
        }
        if (giftCardDiscount > 0) {
            System.out.println("Gift Card Discount: RM" + giftCardDiscount);
        }
        if (memberDiscount > 0){
            System.out.println("Member Points Discount: RM" + memberDiscount);
        }
            System.out.println("Payment Subtotal: RM" + paymentAmount);
            System.out.println("Service Tax (5%): RM" + serviceTax);
            System.out.println("SST Tax (6%): RM" + sstTax);
            System.out.println("Payment Total: RM" + totalPayment);
            if (isCashPayment) {
                System.out.println("Cash Amount: RM" + cashAmount);
                System.out.println("Change: RM" + change);
            }
            System.out.println("----------------");
            System.out.println("Thank you for your payment!");

    }
    public static void storePaymentInfo(String staffName, String paymentID, String paymentMethod, String paymentDate, double paymentAmount, String voucherCode, String cartID) throws SQLException, ClassNotFoundException {
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO payment (payment_id, payment_method, payment_date, payment_amount, voucher_code, cart_id) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, paymentID);
                statement.setString(2, paymentMethod);
                statement.setString(3, paymentDate);
                statement.setDouble(4, paymentAmount);
                statement.setString(5, voucherCode);
                statement.setString(6, cartID);

                // Execute the SQL INSERT statement
                statement.executeUpdate();
            }
        }

    }
}
