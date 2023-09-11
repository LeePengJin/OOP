import java.util.Scanner;

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
    //============================================== Prompt Payment Detail =============================================
    public void promptPaymentDetail(Ewallet ew){
        Scanner sc = new Scanner(System.in);
        boolean phoneNoValid = false;

        do {
            System.out.print("Please enter the phone number (B to exit) : ");  //prompt user's phone number
            ew.phoneNo = sc.next();
            if (ew.phoneNo.equalsIgnoreCase("B")){  //if user input "B", back to the previous page
                break;
            }

            phoneNoValid = checkPhoneNo(ew);  //call function to check the validity of the phone number

            if (phoneNoValid){  //if the phone number is valid, print transaction complete message and store record in database
                System.out.println("Transaction complete.");
                //sql code
            }

        } while(!phoneNoValid);
    }

    //=============================================== Check PhoneNo ====================================================
    public boolean checkPhoneNo(Ewallet ew){
        if (ew.phoneNo.matches("\\d{3}-\\d{7}|\\d{3}-\\d{8}")){  //check phone number format
            return true;
        }
        else {
            System.out.println("Invalid phone number! Please enter digit only.");  //prompt error message
            return false;
        }
    }
}
