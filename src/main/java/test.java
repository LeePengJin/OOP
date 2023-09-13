import java.time.LocalDate;
import java.sql.*;
import java.util.Scanner;

public class test {
    public static void main(String[] args)throws SQLException, ClassNotFoundException{
        Scanner sc = new Scanner(System.in);
        LocalDate paymentDate = LocalDate.now();
        double paymentAmount = 250;
        boolean complete = false, voucherValidity, valid = false, voucherUsed = false, giftCardUsed = false;
        String choice, paymentChoice, inputVoucherCode;
        String input, inputExpirationDate, inputCvv, confirm = "", inputReload;

        do {
            choice = paymentOrVoucher(); //ask user want to make payment or apply voucher
            switch (Integer.parseInt(choice)) {
                case 1 -> { //payment menu
                    do {
                        paymentChoice = paymentMenu(giftCardUsed, voucherUsed, paymentAmount);
                        switch (Integer.parseInt(paymentChoice)){
                            case 1 ->{ //credit card
                                do {
                                    input = "";
                                    System.out.print("Please enter the credit card number (B to back) : ");
                                    input = sc.next();
                                    sc.nextLine();
                                    if (input.equalsIgnoreCase("B")){
                                        continue;
                                    }
                                    else {
                                        System.out.print("Please enter the expiration date (MM/YY) : ");
                                        inputExpirationDate = sc.next();
                                        sc.nextLine();

                                        System.out.print("Please enter the CVV : ");
                                        inputCvv = sc.next();
                                        sc.nextLine();

                                        CreditCard cc = new CreditCard(input, inputExpirationDate, inputCvv);

                                        if ((valid = cc.creditCardNumValidation(cc))){ //check credit card number format
                                            if((valid = cc.checkExpirationDate(cc))){ //do validation of the credit card expiration date
                                                if ((valid = cc.checkCvv(cc))){ //check the format of cvv
                                                    if (paymentConfirmation()) {
                                                        System.out.println("Transaction completed.\n");
                                                        complete = true;
                                                        break;
                                                        //cc.addDatabaseCreditCard(cc);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } while(!input.equalsIgnoreCase("B"));
                                break;
                            }
                            case 2 ->{ //debit card
                                do {
                                    input = "";
                                    System.out.print("Please enter the debit card number (B to back) : "); //prompt debit card number
                                    input = sc.next();
                                    sc.nextLine();
                                    if (input.equalsIgnoreCase("B")){ //if input is b, back to the previous page
                                        continue;
                                    }
                                    else {
                                        System.out.print("Please enter the expiration date (MM/YY) : "); //prompt expiration date
                                        inputExpirationDate = sc.next();
                                        sc.nextLine();

                                        System.out.print("Please enter the CVV : "); //prompt cvv
                                        inputCvv = sc.next();
                                        sc.nextLine();

                                        DebitCard dc = new DebitCard(input, inputExpirationDate, inputCvv); //create a debit card object for validation and storage usage later

                                        //debit card validation
                                        if ((valid = dc.debitCardNumValidation(dc))){ //check credit card number format
                                            if((valid = dc.checkExpirationDate(dc))){ //do validation of the credit card expiration date
                                                if ((valid = dc.checkCvv(dc))){ //check the format of cvv
                                                    if (paymentConfirmation()){ //if confirmed, prompt transaction complete msg and store info in database
                                                        System.out.println("Transaction completed.\n");
                                                        complete = true;
                                                        break;
                                                        //dc.addDatabaseDebitCard(dc);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } while(!input.equalsIgnoreCase("B"));
                                break;
                            }
                            case 3 ->{ //eWallet
                                do {
                                    input = "";
                                    System.out.print("Please enter the phone number (B to back) : "); //prompt phone number
                                    input = sc.next();
                                    sc.nextLine();
                                    if (input.equalsIgnoreCase("B")){ //b to back to the previous page
                                        break;
                                    }
                                    else {
                                        Ewallet ew = new Ewallet(input); //create object
                                        if ((complete = ew.checkPhoneNo(ew))){ //perform validation in the class
                                            if (paymentConfirmation()){ //if confirmed, prompt transaction complete msg and store info in database
                                                System.out.println("Transaction completed.\n");
                                                complete = true;
                                                //ew.addDatabaseEwallet(ew);
                                                break;
                                            }
                                        }
                                    }
                                } while(!input.equalsIgnoreCase("B"));
                            }
                            case 4 ->{ //gift card
                                do {
                                    input = "";
                                    System.out.print("Please enter the gift card code (B - back) : "); //prompt gift card code
                                    input = sc.next();
                                    sc.nextLine();
                                    if (input.equalsIgnoreCase("B")){ //b to back to the previous page
                                        continue;
                                    }
                                    else {
                                        if (input.matches("G\\d{4}")){
                                            GiftCard gc = GiftCard.getDatabaseGiftCard(input); //check the gift card existence
                                            if (gc.getGiftCardCode() != null){ //if exist
                                                if (gc.giftCardBalanceValidation(gc.getBalance())){ //check the gift card balance
                                                    //if the balance is not equal to 0
                                                    if (paymentConfirmation()){
                                                        paymentAmount = gc.calAmount(gc, paymentAmount); //calculate the payment after pay by gift card
                                                        giftCardUsed = true;
                                                        gc.editDatabaseGiftCard(gc); //update the gift card balance
                                                        if (paymentAmount != 0){ //if there are payment amount remaindering, direct customer to payment method menu
                                                            System.out.println("Customer need to pay RM" + paymentAmount + " more to complete the transaction\n"); //display the remaindering payment amount
                                                        }
                                                        else { //if the payment amount is 0, display transaction completed message
                                                            System.out.println("Transaction completed.\n");
                                                            complete = true;
                                                        }
                                                        break;
                                                    }
                                                }
                                            }
                                            else {
                                                System.out.println("Gift card not exist! Please try again with another gift card.\n");
                                            }
                                        }
                                        else {
                                            System.out.println("Invalid gift card code format! Please try again.\n");
                                        }
                                    }

                                } while(!input.equalsIgnoreCase("B"));
                            }
                            case 5 ->{ //back
                                break;
                            }
                            default ->{
                                System.out.println("Please enter only (1 - 5).\n");
                            }
                        }
                        if (complete){
                            break;
                        }
                    } while(Integer.parseInt(paymentChoice) != 5);
                }
                case 2 -> { //apply voucher
                    if (!voucherUsed && !giftCardUsed){
                        do {
                            System.out.print("Please enter the voucher code (B to back) : ");  //prompt voucher code
                            inputVoucherCode = sc.next();
                            sc.nextLine();
                            voucherValidity = false;
                            if (inputVoucherCode.equalsIgnoreCase("B")){  //if user input b, back to the previous page
                                continue;
                            }
                            else if (inputVoucherCode.matches("V\\d{3}")){ //check voucher code format
                                Voucher vc = Voucher.getDatabaseVoucher(inputVoucherCode); //store the voucher information in the vc object
                                if (vc.getVoucherCode() != null){ //if the voucher is existed
                                    voucherValidity = vc.voucherValidation(vc, paymentAmount, paymentDate); //do voucher validation, if invalid error message will be prompted
                                    if (voucherValidity){ //if the voucher is valid
                                        boolean confirmation = voucherConfirmation(vc); //ask for confirmation
                                        if (confirmation){ //if user confirm, display apply successfully message and count the remaindering payment amount
                                            System.out.println("Voucher applied successfully.\n");
                                            paymentAmount -= (paymentAmount * vc.getVoucherRate());  //return the amount after discount
                                            System.out.println(paymentAmount);
                                            vc.editDatabaseVoucher(vc);
                                            voucherUsed = true;
                                        }
                                    }
                                }
                                else {
                                    System.out.println("Voucher not exists. Please try again.\n");
                                }
                            }
                            else {
                                System.out.println("Invalid voucher code format! Please try again.\n");
                            }
                            if (voucherUsed){
                                break;
                            }
                        } while(!inputVoucherCode.equalsIgnoreCase("B"));
                        break;
                    }
                    else if (giftCardUsed) {
                        System.out.println("Voucher cannot be apply after portion of amount has been pay by using gift card\n");
                    }
                    else {
                        System.out.println("Only one voucher can apply to a payment.\n");
                    }

                }
                case 3 -> {
                    do {
                        System.out.print("Please enter the gift card code to reload (B to exit) : ");
                        input = sc.next();
                        sc.nextLine();
                        if (input.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            if (input.matches("G\\d{4}")) {
                                GiftCard gc = GiftCard.getDatabaseGiftCard(input);
                                if (gc.getGiftCardCode() != null) { //if exist
                                    do {
                                        System.out.print("Please enter the amount of reload (0 to back) : ");
                                        inputReload = sc.next();
                                        sc.nextLine();
                                        try {
                                            Double.parseDouble(inputReload);
                                            if (!inputReload.equalsIgnoreCase("0")) {
                                                boolean reloadValid = gc.reloadGiftCardValidation(gc, inputReload);
                                                if (reloadValid) {
                                                    do {
                                                        System.out.print("Confirm to reload RM" + inputReload + " to gift card " + gc.getGiftCardCode() + "? (Y = yes, N = no) : ");
                                                        confirm = sc.next();
                                                        sc.nextLine();
                                                        switch (confirm.toUpperCase()) {
                                                            case "Y" -> {
                                                                gc = gc.reloadGiftCard(gc, Double.parseDouble(inputReload));
                                                                gc.editDatabaseGiftCard(gc);
                                                                System.out.println("Reload completed.\n");
                                                                break;
                                                            }
                                                            case "N" -> {
                                                                break;
                                                            }
                                                            default -> {
                                                                System.out.println("Please enter 'Y' or 'N' only.");
                                                            }
                                                        }
                                                    } while (!confirm.equalsIgnoreCase("Y") && !confirm.equalsIgnoreCase("N"));

                                                }
                                            }
                                            else if (input.equalsIgnoreCase("0")) {
                                                continue;
                                            }

                                            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")){
                                                break;
                                            }
                                        } catch (NumberFormatException e){
                                            System.out.println("Please enter number only.\n");
                                        }
                                    } while (!inputReload.equalsIgnoreCase("0"));
                                } else {
                                    System.out.println("Gift card not exist! Please try again with another gift card.\n");
                                }
                            } else {
                                System.out.println("Invalid gift card code format! Please try again\n");
                            }
                        }
                    } while (!input.equalsIgnoreCase("B"));
                    break;
                }
                default ->{
                    System.out.println("Please enter only (1 - 2).\n");
                }
            }
        } while(!complete);



    }

    public static String paymentOrVoucher(){
        Scanner sc = new Scanner(System.in);
        String choice;

        System.out.println("Make payment or apply voucher");
        System.out.println("1. Make payment");
        System.out.println("2. Apply voucher");
        System.out.println("3. reload");
        System.out.print("Enter your choice here --> ");
        choice = sc.next();
        sc.nextLine();

        while(!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("2") && !choice.equalsIgnoreCase("3")){
            System.out.println("Make payment or apply voucher");
            System.out.println("1. Make payment");
            System.out.println("2. Apply voucher");
            System.out.println("3. reload");
            System.out.println("Please enter only '1' or '2' to select.");
            System.out.print("Enter your choice here --> ");
            choice = sc.next();
            sc.nextLine();
        }

        return choice;
    }

    public static String paymentMenu(boolean giftCardUsed, boolean voucherUsed, Double paymentAmount){
        Scanner sc = new Scanner(System.in);
        String choice;

        if (!giftCardUsed && !voucherUsed){ //if both gift card and voucher are not used
            System.out.println("Total amount need to pay : RM" + paymentAmount);
            System.out.println("Select one of the payment method below");
            System.out.println("1. Credit card");
            System.out.println("2. Debit card");
            System.out.println("3. Ewallet");
            System.out.println("4. Gift card");
            System.out.println("5. Back");
            System.out.print("Enter your choice here --> ");
            choice = sc.next();
            sc.nextLine();

            while (!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("2") && !choice.equalsIgnoreCase("3") && !choice.equalsIgnoreCase("4") && !choice.equalsIgnoreCase("5")){
                System.out.println("Select one of the payment method below");
                System.out.println("1. Credit card");
                System.out.println("2. Debit card");
                System.out.println("3. Ewallet");
                System.out.println("4. Gift card");
                System.out.println("5. Back");
                System.out.println("Please enter only '1', '2', '3', '4' or '5' to select.");
                System.out.print("Enter your choice here --> ");
                choice = sc.next();
                sc.nextLine();
            }
        }
        else if (voucherUsed && !giftCardUsed) { //if voucher is applied and gift card is not used
            System.out.println("Select one of the payment method below");
            System.out.println("1. Credit card");
            System.out.println("2. Debit card");
            System.out.println("3. Ewallet");
            System.out.println("4. Gift card");
            System.out.print("Enter your choice here --> ");
            choice = sc.next();
            sc.nextLine();

            while (!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("2") && !choice.equalsIgnoreCase("3") && !choice.equalsIgnoreCase("4")){
                System.out.println("Select one of the payment method below");
                System.out.println("1. Credit card");
                System.out.println("2. Debit card");
                System.out.println("3. Ewallet");
                System.out.println("4. Gift card");
                System.out.println("Please enter only '1', '2', '3' or '4' to select.");
                System.out.print("Enter your choice here --> ");
                choice = sc.next();
                sc.nextLine();
            }
        }
        else if (giftCardUsed && !voucherUsed) { //if gift card used but voucher not applied
            System.out.println("Select one of the payment method below");
            System.out.println("1. Credit card");
            System.out.println("2. Debit card");
            System.out.println("3. Ewallet");
            System.out.println("4. Back");
            System.out.print("Enter your choice here --> ");
            choice = sc.next();
            sc.nextLine();

            while (!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("2") && !choice.equalsIgnoreCase("3") && !choice.equalsIgnoreCase("4" )){
                System.out.println("Select one of the payment method below");
                System.out.println("1. Credit card");
                System.out.println("2. Debit card");
                System.out.println("3. Ewallet");
                System.out.println("4. Back");
                System.out.println("Please enter only '1', '2', '3' or '4' to select.");
                System.out.print("Enter your choice here --> ");
                choice = sc.next();
                sc.nextLine();
            }

            if (choice.equalsIgnoreCase("4")){
                return "5";
            }
        }
        else { //if gift card and voucher is used
            System.out.println("Select one of the payment method below");
            System.out.println("1. Credit card");
            System.out.println("2. Debit card");
            System.out.println("3. Ewallet");
            System.out.print("Enter your choice here --> ");
            choice = sc.next();
            sc.nextLine();

            while (!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("2") && !choice.equalsIgnoreCase("3")){
                System.out.println("Select one of the payment method below");
                System.out.println("1. Credit card");
                System.out.println("2. Debit card");
                System.out.println("3. Ewallet");
                System.out.println("Please enter only '1', '2' or '3' to select.");
                System.out.print("Enter your choice here --> ");
                choice = sc.next();
                sc.nextLine();
            }
        }

        return choice;
    }

    public static boolean voucherConfirmation(Voucher vc){
        Scanner sc = new Scanner(System.in);
        String confirm;

        System.out.println("Voucher Code          : " + vc.getVoucherCode());
        System.out.println("Voucher Description   : " + vc.getVoucherDesc());
        System.out.println("Voucher Minimum Spend : " + vc.getVoucherMinSpend());
        System.out.println("Voucher Discount Rate : " + vc.getVoucherRate());
        System.out.println("Voucher Expired Date  : " + vc.getVoucherExpiredDate());
        System.out.print("Confirm to apply this voucher? (Y = yes, N = no) : ");
        confirm = sc.next();
        sc.nextLine();

        while(!confirm.equalsIgnoreCase("Y") && !confirm.equalsIgnoreCase("N")){
            System.out.println("Please enter 'Y' or 'N' only.");
            System.out.print("Confirm to apply this voucher? (Y = yes, N = no) : ");
            confirm = sc.next();
            sc.nextLine();
        }

        if (confirm.equalsIgnoreCase("Y")) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean paymentConfirmation(){
        Scanner sc = new Scanner(System.in);
        String confirm;

        System.out.print("Confirm to complete this transaction? (Y = yes, N = no) : ");
        confirm = sc.next();
        sc.nextLine();

        while(!confirm.equalsIgnoreCase("Y") && !confirm.equalsIgnoreCase("N")){
            System.out.println("Please enter 'Y' or 'N' only.");
            System.out.print("Confirm to complete this transaction? (Y = yes, N = no) : ");
            confirm = sc.next();
            sc.nextLine();
        }

        if (confirm.equalsIgnoreCase("Y")) {
            return true;
        }
        else {
            return false;
        }
    }
}

/*do {
                        System.out.print("Please enter the gift card code to reload (B to exit) : ");
                        input = sc.next();
                        sc.nextLine();
                        if (input.equalsIgnoreCase("B")) {
                            break;
                        } else {
                            if (input.matches("G\\d{4}")) {
                                GiftCard gc = GiftCard.getDatabaseGiftCard(input);
                                if (gc.getGiftCardCode() != null) { //if exist
                                    do {
                                        System.out.print("Please enter the amount of reload (0 to back) : ");
                                        inputReload = sc.next();
                                        sc.nextLine();
                                        try {
                                            Double.parseDouble(inputReload);
                                            if (!inputReload.equalsIgnoreCase("0")) {
                                                boolean reloadValid = gc.reloadGiftCardValidation(gc, inputReload);
                                                if (reloadValid) {
                                                    do {
                                                        System.out.print("Confirm to reload RM" + inputReload + " to gift card " + gc.getGiftCardCode() + "? (Y = yes, N = no) : ");
                                                        confirm = sc.next();
                                                        sc.nextLine();
                                                        switch (confirm.toUpperCase()) {
                                                            case "Y" -> {
                                                                gc = gc.reloadGiftCard(gc, Double.parseDouble(inputReload));
                                                                gc.editDatabaseGiftCard(gc);
                                                                System.out.println("Reload completed.\n");
                                                            }
                                                            case "N" -> {
                                                            }
                                                            default -> {
                                                                System.out.print("Please enter 'Y' or 'N' only.\n");
                                                                confirm = sc.next();
                                                                sc.nextLine();
                                                            }
                                                        }

                                                    } while (!confirm.equalsIgnoreCase("Y") && !confirm.equalsIgnoreCase("N"));

                                                }
                                            }
                                            else if (input.equalsIgnoreCase("0")) {
                                                continue;
                                            }

                                            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")){
                                                break;
                                            }
                                        } catch (NumberFormatException e){
                                            System.out.println("Please enter number only.\n");
                                        }
                                    } while (!inputReload.equalsIgnoreCase("0"));
                                } else {
                                    System.out.println("Gift card not exist! Please try again with another gift card.\n");
                                }
                            } else {
                                System.out.println("Invalid gift card code format! Please try again\n");
                            }
                        }
                    } while (!input.equalsIgnoreCase("B"));
                    break;*/
