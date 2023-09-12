
import java.util.Scanner;
class StaffLogin {

    Scanner input = new Scanner(System.in);
    String un, pass;

    public  void Login(){
        System.out.println("Enter Username: ");
        un = input.nextLine();
        System.out.println("Enter Password: ");
        pass = input.nextLine();

    }

    public void validate(){
        if(un.equals("admin")){
            if(pass.equals("admin")){
                System.out.println("Successfully login");
            }
            else{
                System.out.println("Wrong password, please try again!");

            }
        }
        else{
            System.out.println("Wrong Username, please try again!");

        }

    }

}
