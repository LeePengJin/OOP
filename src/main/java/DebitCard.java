import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
    public void promptPaymentDetail(DebitCard dc){
        boolean valid = false;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("Please enter the debit card number (B to exit) : ");     //prompt credit card number
            dc.debitCardNo = sc.next();
            if (dc.debitCardNo.equalsIgnoreCase("B"))  //if user input "B", back to the previous page
                break;

            System.out.print("Please enter the expiration date (MM/YY) : ");      //prompt expiration date in string format
            dc.debitCardExpirationDate = sc.next();

            System.out.print("Please enter the CVV : ");      //prompt CVV
            dc.cvv = sc.next();

            valid = debitCardValidation(dc);       //call function to check the validity of credit card

            if (valid){  //if credit card is valid, print transaction complete message and store record in database
                System.out.println("Transaction completed");
                //sql code
            }

        }while(!valid);     //if the credit card is invalid, ask user input again
    }

    //=============================================== Debit Card Validation ===========================================
    public boolean debitCardValidation(DebitCard dc){
        boolean dateAndCvvValid;
        if (dc.debitCardNo.length() != 12 ){  //check whether the length of the credit card number is correct
            System.out.println("Invalid debit card number entered! Please enter again.\n");
            return false;
        }
        else {
            if (!dc.debitCardNo.matches("\\d{12}")){
                System.out.println("Please enter only digit for debit card number.\n");
                return false;
            }
        }

        dateAndCvvValid = checkExpirationDate(dc); //after check credit card number, call function to check the validity of expiration date

        return dateAndCvvValid;
    }

    //============================================== Date Validation ===================================================
    public boolean checkExpirationDate(DebitCard dc){
        boolean dateValid = false;
        boolean cvvValid = false;
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //set the date formatter with (yyyy-MM-dd) format

        if (!dc.debitCardExpirationDate.matches("\\d{2}/\\d{2}")){
            System.out.println("Invalid expiration date format!! Please enter again in this format (eg. 01/24).");
            return dateValid;
        }

        String expirationDate = "20" + dc.debitCardExpirationDate.substring(3,5) + "-" + dc.debitCardExpirationDate.substring(0,2) + "-31"; //combine the string to (yyyy-MM-dd) format

        LocalDate expiry = LocalDate.parse(expirationDate, originalFormatter); //change the date format to (MM/yy)

        LocalDate currentDate = LocalDate.now();  //declare current date

        if (expiry.isBefore(currentDate)){  //check whether the credit card is expired
            System.out.println("This debit card is expired!! Please try another one.\n");
            return dateValid;
        }
        else {
            cvvValid = checkCvv(dc);
            return cvvValid;
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
}
