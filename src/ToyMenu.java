import java.sql.SQLException;
import java.util.Scanner;

/*public class ToyMenu {

    public static void main(String[]args) throws SQLException, ClassNotFoundException {


    }
    public static String displayToyMenu() throws SQLException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        int selection;
        String category = "";
        //show the category of Toy
        System.out.println("\n\t\tWelcome to Toy4U");
        System.out.println(" ");
        System.out.println("    1. LEGO ");
        System.out.println("    2. CARS ");
        System.out.println("    3. GUNS ");
        System.out.println("    4. DOLLS ");
        System.out.println("    5. GAME & PUZZLES ");

        do{
            System.out.print("\nSelect a category you want: "); //ask user input
            String stdin = input.nextLine();
            //validate the input of user whether fulfill the requirement
            try{
                selection = Integer.valueOf(stdin);
            }catch (NumberFormatException e){
                System.out.println("Wrong input. Please Enter Again."); //show error msg if wrong input
                selection = -1;
            }
            //define the value follow the user input
            switch (selection) {
                case 1:
                    category = "RY01";
                    break;
                case 2:
                    category = "RY02";
                    break;
                case 3:
                    category = "RY03";
                    break;
                case 4:
                    category = "RY04";
                    break;
                case 5:
                    category = "RY05";
                    break;
                case -1:
                    break;
                default:
                    System.out.println("Wrong input. Please Enter Again.");
                    break;
            }
        }while(selection != 1 && selection != 2 && selection != 3 && selection != 4 && selection != 5);

        return category;
    }

    public static int selectToy(int totalToy) {
        Scanner input = new Scanner(System.in);
        int toySelected;

        do {
            System.out.print("\nSelect a toy: ");
            String stdin = input.nextLine();
            //validate the user input
            try {
                toySelected = Integer.valueOf(stdin);
            } catch (NumberFormatException e) {
                toySelected = -1;
            }//prompt error msg if wrong input
            if (toySelected < 1 || toySelected > totalToy) {
                System.out.println("Toy Not Found. Please Enter Again!");
            }
        } while (toySelected < 1 || toySelected > totalToy);

        return toySelected;
    }
    public static int enterQuantity(){
        Scanner input = new Scanner(System.in);
        int quantity;

        do {
            System.out.print("Enter a quantity: "); //ask quantity of the toy that choose by user
            String stdin = input.nextLine();
            //validation of user input
            try {
                quantity = Integer.valueOf(stdin);
            }catch (NumberFormatException e){
                quantity = -1;
            }
            //prompt error msg if wrong input
            if(quantity < 1){
                System.out.println("Wrong quantity input. Please Enter at least 1 quantity!\n");
            }
        }while (quantity < 1);

        return quantity;
    }

    public static int addOrder() throws SQLException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        //ask whether the user want to purchase
        System.out.print("\nPurchase another toys? (1-Yes, Enter-No): ");
        String stdin = input.nextLine();
        int orderMore;
        //validation of user input
        try{
            orderMore = Integer.valueOf(stdin);
        }catch(NumberFormatException e){
            orderMore = -1;
        }

        if(orderMore == 1){
            //if yes
            //go back to display all the toy category
            return 1;
        }else{
            //if no
            //pass the price after promotion and quantity to payment
            return -1;
            //Toy.passToPayment(newToysPrice,toyQuantity);
        }
    }

}*/
