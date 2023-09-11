import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CreditCard {
    //data member
    private String creditCardNo;
    private String creditCardExpirationDate;
    private String cvv;
    Scanner sc = new Scanner(System.in);

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
    //============================================== Prompt Payment Detail =============================================
    public void promptPaymentDetail(CreditCard cc){
        boolean valid = false;

        do {
            System.out.print("Please enter the credit card number (B to exit) : ");     //prompt credit card number
            cc.creditCardNo = sc.next();
            if (cc.creditCardNo.equalsIgnoreCase("B"))  //if user input "B", back to the previous page
                break;

            System.out.print("Please enter the expiration date (MM/YY) : ");      //prompt expiration date in string format
            cc.creditCardExpirationDate = sc.next();

            System.out.print("Please enter the CVV : ");      //prompt CVV
            cc.cvv = sc.next();

            valid = creditCardValidation(cc);       //call function to check the validity of credit card

            if (valid){  //if credit card is valid, print transaction complete message and store record in database
                System.out.println("Transaction completed");
                //sql code
            }

        }while(!valid);     //if the credit card is invalid, ask user input again
    }

    //============================================= Credit Card Validation =============================================
    public boolean creditCardValidation(CreditCard cc){
        boolean dateAndCvvValid;
        if (cc.creditCardNo.length() != 12 ){  //check whether the length of the credit card number is correct
            System.out.println("Invalid credit card number entered! Please enter again.\n");
            return false;
        }
        else {
            if (!cc.creditCardNo.matches("\\d{12}")){
                System.out.println("Please enter only digit for credit card number.\n");
                return false;
            }
        }

        dateAndCvvValid = checkExpirationDate(cc); //after check credit card number, call function to check the validity of expiration date

        return dateAndCvvValid;
    }

    //============================================== Date Validation ===================================================
    public boolean checkExpirationDate(CreditCard cc){
        boolean dateValid = false;
        boolean cvvValid = false;
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //set the date formatter with (yyyy-MM-dd) format

        if (!cc.creditCardExpirationDate.matches("\\d{2}/\\d{2}")){
            System.out.println("Invalid expiration date format!! Please enter again in this format (eg. 01/24).");
            return dateValid;
        }

        String expirationDate = "20" + cc.creditCardExpirationDate.substring(3,5) + "-" + cc.creditCardExpirationDate.substring(0,2) + "-31"; //combine the string to (yyyy-MM-dd) format

        LocalDate expiry = LocalDate.parse(expirationDate, originalFormatter); //change the date format to (MM/yy)

        LocalDate currentDate = LocalDate.now();  //declare current date

        if (expiry.isBefore(currentDate)){  //check whether the credit card is expired
            System.out.println("This credit card is expired!! Please try another one.\n");
            return dateValid;
        }
        else {
            cvvValid = checkCvv(cc);
            return cvvValid;
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
}


/*public boolean checkExpirationDate(String creditCardExpirationDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        LocalDate currentDate = LocalDate.now();

        if (!creditCardExpirationDate.matches("\\d{2}/\\d{2}")){
            System.out.println("Invalid expiration date format!! Please enter again in this format (eg. 01/24).");
            return false;
        }

        try {
            LocalDate expirationDate = LocalDate.parse(creditCardExpirationDate, formatter);
            String formattedCurrentDate = currentDate.format(formatter); //convert the current date to String with the format (MM/YY)
            LocalDate parsedDate = LocalDate.parse(formattedCurrentDate, formatter); //convert the String back to LocalDate

            if (expirationDate.isBefore(parsedDate)){//check whether the credit card is expired
                System.out.println("The credit card had expired! Please try with another credit card.");
                return false;
            }
            else {
                return true;
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return false;
        }


    }*/