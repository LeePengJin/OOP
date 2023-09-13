import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StaffDriver {
    static Staff staff = new Staff();
    public static void main (String[] args) {
        staffLogin();
    }

    public static void staffLogin() {
        Scanner staff_input = new Scanner(System.in);
        boolean returnedValue = true;
        String staffId;
        String password;
        do {
            System.out.println("STAFF LOGIN");
            System.out.println("===========");

            System.out.print("Enter StaffID : ");
            staffId = staff_input.next();
            staff.setStaffId(staffId);

            System.out.print("Enter Password : ");
            password = staff_input.next();

            returnedValue = staff.validateToLogin(staffId, password);

        } while (!returnedValue);
        staffMenu(staff.getStaffId());
    }

    public static void displayStaff() {
        Scanner displayStaff_input = new Scanner(System.in);
        System.out.println("===========================================================================================================================================================================================================================");
        System.out.printf("||%-5s||%-30s||%-20s||%-6s||%-14s||%-12s||%-100s||%-14s||\n", "Id", "Name", "Position", "Gender", "Birth Date", "Phone Number", "Address", "IC Number");
        System.out.println("===========================================================================================================================================================================================================================");
        ArrayList<String> displayStaff;
        try {
            displayStaff = staff.displayAllDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        for (String displayArray : displayStaff) {
            System.out.println("||" + displayArray + "||");
        }
        System.out.println("===========================================================================================================================================================================================================================");

        int staffChoice = 1;
        do {
            System.out.println("\n1. Edit Staff");
            System.out.println("2. Add Staff");
            System.out.println("3. Delete Staff");
            System.out.println("0. Back To Menu");
            System.out.print("Select Your Next Step: ");
            staffChoice = displayStaff_input.nextInt();

            if (staffChoice >= 0 && staffChoice <= 3) {
                switch (staffChoice) {
                    case 1:
                        try {
                            editStaff();
                        } catch (SQLException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        addStaff();
                        break;
                    case 3:
                        deleteStaff();
                        break;
                    case 0:
                        staffMenu(staff.getStaffId());
                        break;
                }
            } else {
                System.out.println("\nInvalid Input! Please follow the menu given!!");
            }
        }while (staffChoice != 0);
    }

    public static void staffMenu(String staffId_menu) {
        Scanner staffMenu_input = new Scanner(System.in);

        int staffChoice = 1;
        int validationChoice = 0;
        int validationSwitch = 0;
        do {
            validationChoice = 0;
            validationSwitch = 0;
            System.out.println("\nStaff Menu");
            System.out.println("===========================");
            System.out.println("1. View Member");
            System.out.println("2. Make Order");
            System.out.println("3. View Payment Record");
            System.out.println("4. Change Account Password");
            System.out.println("5. Reload GiftCard");
            System.out.println("6. Refund");

            ArrayList<String> staffPositionDatabase = new ArrayList<>();
            ArrayList<String> staffIdDatabase = new ArrayList<>();
            ArrayList<Staff> staffDatabase;
            try {
                staffDatabase = staff.getStaffDatabase();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            for (Staff sd : staffDatabase) {
                staffIdDatabase.add(sd.getStaffId());
            }

            for (Staff sd : staffDatabase) {
                staffPositionDatabase.add(sd.getPosition());
            }

            for (int i = 0; i < staffPositionDatabase.size(); i++) {
                if (staffIdDatabase.get(i).equals(staffId_menu)) {
                    if (staffPositionDatabase.get(i).equals("Admin")) {
                        System.out.println("7. View Staff");
                        System.out.println("8. View Toys");
                        validationChoice = 1;
                    }
                }
            }

            System.out.println("0. Log Out");
            System.out.print("Enter Your Choice: ");
            staffChoice = staffMenu_input.nextInt();

            if (validationChoice == 0) {
                if (staffChoice < 0 || staffChoice > 6) {
                    System.out.println("Invalid Input! Please follow the menu given!");
                    validationSwitch = 1;
                }
            }
            else {
                if (staffChoice < 0 || staffChoice > 8) {
                    System.out.println("Invalid Input! Please follow the menu given!");
                    validationSwitch = 1;
                }
            }

            if (validationSwitch == 0) {
                switch (staffChoice) {
                    case 1:
                        MemberDriver.memberMain(staff);
                        break;
                    case 2:
                        //orderList();
                        break;
                    case 3:
                        //Payment record
                        break;
                    case 4:
                        changePassword();
                        break;
                    case 5:
                        //reload
                        break;
                    case 6:
                        //refund
                        break;
                    case 7:
                        displayStaff();
                        break;
                    case 8:
                        //toy
                        break;
                    case 0:
                        staffLogin();
                        break;
                }
            }
        } while (staffChoice != 0);
    }

    public static void addStaff() {
        Scanner addStaff_input = new Scanner(System.in);
        ArrayList<String> displayAddStaffArray = new ArrayList<>();
        ArrayList<Staff> staffArray = new ArrayList<>();
        Staff newStaff = new Staff();

        System.out.print("\nHow many staff you want to add in(0 for exit): ");
        int staffAddIn = addStaff_input.nextInt();
        addStaff_input.nextLine();

        if (staffAddIn != 0) {
            for (int i = 0; i < staffAddIn; i++) {
                System.out.println("\nNo: " + (i + 1));
                do {
                    System.out.print("\n\tStaff Id(Format: S001): ");
                    newStaff.setStaffId(addStaff_input.nextLine());
                } while (!staff.validateToAddStaffId(newStaff.getStaffId()));
                newStaff.setStaffId(Character.toUpperCase(newStaff.getStaffId().charAt(0)) + newStaff.getStaffId().substring(1));

                System.out.print("\n\tStaff Name: ");
                newStaff.setName(addStaff_input.nextLine());

                do {
                    System.out.print("\n\tStaff Phone Number(xxx-xxxxxxxx): ");
                    newStaff.setPhoneNumber(addStaff_input.nextLine());
                } while (!staff.validateNewPhoneNumber(newStaff.getPhoneNumber()));

                do {
                    System.out.print("\n\tStaff Gender(M/F): ");
                    newStaff.setGender(addStaff_input.nextLine());
                } while (!staff.validateNewGender(newStaff.getGender()));
                newStaff.setGender(newStaff.getGender().toUpperCase());

                String dateInput;
                do {
                    System.out.print("\n\tStaff Birth Date(yyyy-MM-dd): ");
                    dateInput = addStaff_input.nextLine();
                    newStaff.setDateOfBirth(staff.validateBirthDate(dateInput));
                } while (newStaff.getDateOfBirth() == null);

                System.out.print("\n\tStaff Address: ");
                newStaff.setAddress(addStaff_input.nextLine());

                do {
                    System.out.print("\n\tStaff IC Number(xxxxxx-xx-xxxx): ");
                    newStaff.setIcNum(addStaff_input.nextLine());
                } while (!staff.validateNewIcNum(newStaff.getIcNum(), newStaff.getDateOfBirth()));

                int validatePosition = 0;
                do {
                    System.out.println("\n\tStaff Position: ");
                    System.out.println("\t\t1. Cashier");
                    System.out.println("\t\t2. Admin");
                    System.out.print("\t\tEnter Your Choice: ");
                    int staffPositionChoice = addStaff_input.nextInt();
                    addStaff_input.nextLine();

                    switch (staffPositionChoice) {
                        case 1:
                            newStaff.setPosition("Cashier");
                            validatePosition = 1;
                            break;
                        case 2:
                            newStaff.setPosition("Admin");
                            validatePosition = 1;
                            break;
                        default:
                            System.out.println("\nInvalid Input! Please follow the menu given!");
                            break;
                    }
                } while (validatePosition == 0);

                do {
                    System.out.print("\n\tStaff Password: ");
                    newStaff.setPassword(addStaff_input.nextLine());
                } while (!staff.validatePassword(newStaff.getPassword()));

                displayAddStaffArray.add(String.format("||%-5s||%-30s||%-12s||%-6s||%-14s||%-70s||%-14s||%-10s||%-30s||", newStaff.getStaffId(), newStaff.getName(), newStaff.getPhoneNumber(), newStaff.getGender(), newStaff.getDateOfBirth(), newStaff.getAddress(), newStaff.getIcNum(), newStaff.getPosition(), newStaff.getPassword()));
                staffArray.add(newStaff);
            }

            System.out.println("\n===================================================================================================================================================================================================================");
            System.out.printf("||%-5s||%-30s||%-12s||%-6s||%-14s||%-70s||%-14s||%-10s||%-30s||", "Id", "Name", "Phone Number", "Gender", "Birth Date", "Address", "IC Number", "Position", "Password");
            System.out.println("\n===================================================================================================================================================================================================================");

            for (String s : displayAddStaffArray) {
                System.out.println(s);
            }

            System.out.println("===================================================================================================================================================================================================================");

            System.out.print("\n\nConfirm to add in?(1 = yes, other = no) : ");
            int confirmation = addStaff_input.nextInt();

            if (confirmation == 1) {
                try {
                    staff.addStaffToDatabase(staffArray);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            displayStaff();
        }
    }

    public static void editStaff () throws SQLException, ClassNotFoundException {
        Scanner editStaff_input = new Scanner(System.in);

        String editStaffId;
        do {
            System.out.print("Enter Staff Id to edit(Format: S001, 0 for exit): ");
        } while (!staff.validateEditOrDeleteStaffId(editStaffId = editStaff_input.nextLine()));

        //let the first character become uppercase so user can enter 's' or 'S'
        editStaffId = Character.toUpperCase(editStaffId.charAt(0)) + editStaffId.substring(1);

        Staff currentData = getStaff(editStaffId);
        Staff editedStaff = new Staff();
        Staff newStaff = new Staff(currentData.getStaffId(), currentData.getPosition(), currentData.getIcNum(), currentData.getStaffName(), currentData.getGender(), currentData.getDateOfBirth(), currentData.getPhoneNumber(), currentData.getAddress());
        int changeMenu = 0;
        int editChoice = 1;

        do {
            if (editStaffId.charAt(0) != '0') {
                System.out.println("\nEditing Staff " + editStaffId + ": ");
                System.out.println("\t1. Staff Id");
                System.out.println("\t2. Staff Name");
                System.out.println("\t3. Staff Position");
                System.out.println("\t4. Staff Address");
                System.out.println("\t5. Staff IC Number");
                System.out.println("\t6. Staff Phone Number");
                System.out.println("\t7. Staff Gender");
                System.out.println("\t8. Staff Birth Date");
                if (changeMenu == 0) {
                    System.out.println("\t0. Back");
                } else {
                    System.out.println("\t0. Back and Save");
                }

                System.out.print("Enter Your Choice: ");
                editChoice = editStaff_input.nextInt();
                editStaff_input.nextLine();

                if (editChoice >= 0 && editChoice <= 8) {
                    switch (editChoice) {
                        case 1:
                            System.out.println("Current Staff Id: " + currentData.getStaffId());
                            do {
                                System.out.print("Enter New Staff Id(Format: S001): ");
                                newStaff.setStaffId(editStaff_input.nextLine());
                            } while (!staff.validateToAddStaffId(newStaff.getStaffId()));
                            newStaff.setStaffId(Character.toUpperCase(newStaff.getStaffId().charAt(0)) + newStaff.getStaffId().substring(1));
                            changeMenu = 1;
                            break;
                        case 2:
                            System.out.println("Current Name:  " + currentData.getName());
                            System.out.print("Enter New Staff Name: ");
                            newStaff.setName(editStaff_input.nextLine());
                            changeMenu = 1;
                            break;
                        case 3:
                            System.out.println("Current Position: " + currentData.getPosition());
                            int validatePosition = 0;
                            do {
                                System.out.println("Staff Position: ");
                                System.out.println("\t1. Cashier");
                                System.out.println("\t2. Admin");
                                System.out.print("\tEnter Your Choice: ");
                                int staffPositionChoice = editStaff_input.nextInt();

                                switch (staffPositionChoice) {
                                    case 1:
                                        newStaff.setPosition("Cashier");
                                        validatePosition = 1;
                                        break;
                                    case 2:
                                        newStaff.setPosition("Admin");
                                        validatePosition = 1;
                                        break;
                                    default:
                                        System.out.println("\nInvalid Input! Please follow the menu given!");
                                        break;
                                }
                            } while (validatePosition == 0);
                            changeMenu = 1;
                            break;
                        case 4:
                            System.out.println("Current Address: " + currentData.getAddress());
                            System.out.print("Enter New Address: ");
                            newStaff.setAddress(editStaff_input.nextLine());
                            changeMenu = 1;
                            break;
                        case 5:
                            System.out.println("Current IC Number: " + currentData.getIcNum());
                            do {
                                System.out.print("Enter New IC Number(xxxxxx-xx-xxxx)): ");
                                newStaff.setIcNum(editStaff_input.nextLine());
                            } while (!staff.validateNewIcNum(newStaff.getIcNum(), newStaff.getDateOfBirth()));
                            changeMenu = 1;
                            break;
                        case 6:
                            System.out.println("Current Phone Number: " + currentData.getPhoneNumber());
                            do {
                                System.out.print("Enter New Phone Number(xxx-xxxxxxxx): ");
                                newStaff.setPhoneNumber(editStaff_input.nextLine());
                            } while (!staff.validateNewPhoneNumber(newStaff.getPhoneNumber()));
                            changeMenu = 1;
                            break;
                        case 7:
                            System.out.println("Current Gender: " + currentData.getGender());
                            do {
                                System.out.print("Enter New Gender('M'/'F'): ");
                                newStaff.setGender(editStaff_input.nextLine());
                            } while (!staff.validateNewGender(newStaff.getGender()));
                            newStaff.setGender(newStaff.getGender().toUpperCase());
                            changeMenu = 1;
                            break;
                        case 8:
                            String dateInput;
                            System.out.println("Current Birth Date: " + currentData.getDateOfBirth());
                            do {
                                System.out.print("Enter New Birth Date(yyyy-MM-dd): ");
                                dateInput = editStaff_input.nextLine();
                                newStaff.setDateOfBirth(staff.validateBirthDate(dateInput));
                            } while (newStaff.getDateOfBirth() == null);
                            changeMenu = 1;
                            break;
                        case 0:
                            if (changeMenu == 0) {
                                displayStaff();
                            } else {
                                editedStaff = new Staff(newStaff.getStaffId(), newStaff.getPosition(), newStaff.getIcNum(), newStaff.getName(), newStaff.getGender(), newStaff.getDateOfBirth(), newStaff.getPhoneNumber(), newStaff.getAddress());
                                System.out.println("\n=========================================================================================================================================================================");
                                System.out.printf("||%-20s||%-71s||%-70s||", "", "Before Edit", "After Edited");
                                System.out.println("\n=========================================================================================================================================================================");
                                System.out.printf("||%-20s||%-71s||%-70s||", "Staff Id", currentData.getStaffId(), newStaff.getStaffId());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Staff Name", currentData.getName(), newStaff.getName());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Staff Position", currentData.getPosition(), newStaff.getPosition());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Staff Address", currentData.getAddress(), newStaff.getAddress());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Staff IC Number", currentData.getIcNum(), newStaff.getIcNum());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Staff Phone Number", currentData.getPhoneNumber(), newStaff.getPhoneNumber());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Staff Gender", currentData.getGender(), newStaff.getGender());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Staff Birth Date", currentData.getDateOfBirth(), newStaff.getDateOfBirth());

                                System.out.println("\n=========================================================================================================================================================================");

                                System.out.print("\nConfirm Change?(1 = Yes, other = No): ");
                                int confirmChange = editStaff_input.nextInt();

                                if (confirmChange == 1) {
                                    staff.updateEditedStaff(currentData.getIcNum(), editedStaff);
                                }
                            }
                            break;
                    }
                } else {
                    System.out.println("Invalid input! Please follow the menu given!");
                }
            }
        }while (editChoice != 0);
    }

    private static Staff getStaff(String editStaffId) {
        Staff currentData = null;
        ArrayList<Staff> staffDatabase;
        try {
            staffDatabase = staff.getStaffDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Staff sd : staffDatabase) {
            if (sd.getStaffId().equals(editStaffId)) {
                currentData = new Staff(sd.getStaffId(), sd.getPosition(), sd.getPassword(), sd.getIcNum(), sd.getName(), sd.getGender(), sd.getDateOfBirth(), sd.getPhoneNumber(), sd.getAddress());
            }
        }
        return currentData;
    }

    public static void deleteStaff () {
        Scanner deleteStaff_input = new Scanner(System.in);
        String deleteId;
        int deleteLoop = 1;

        do {
            deleteLoop = 1;
            do {
                System.out.print("Enter Staff Id(Format: S001, 0 = Exit): ");
            } while (!staff.validateEditOrDeleteStaffId(deleteId = deleteStaff_input.nextLine()));
            deleteId = Character.toUpperCase(deleteId.charAt(0)) + deleteId.substring(1);

            Staff deleteStaff = new Staff();
            ArrayList<Staff> staffDatabase;
            try {
                staffDatabase = staff.getStaffDatabase();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            for (Staff sd : staffDatabase) {
                if (sd.getStaffId().equals(deleteId)) {
                    deleteStaff = sd;
                }
            }

            if (deleteId.charAt(0) != '0' && deleteId.equals(staff.getStaffId())) {
                System.out.println("===========================================================================================================================================================================================================================");
                System.out.printf("||%-5s||%-30s||%-20s||%-6s||%-14s||%-12s||%-100s||%-14s||\n", "Id", "Name", "Position", "Gender", "Birth Date", "Phone Number", "Address", "IC Number");
                System.out.println("===========================================================================================================================================================================================================================");
                System.out.printf("||%-5s||%-30s||%-20s||%-6s||%-14s||%-12s||%-100s||%-14s||\n", deleteStaff.getStaffId(), deleteStaff.getName(), deleteStaff.getPosition(), deleteStaff.getGender(), deleteStaff.getDateOfBirth(), deleteStaff.getPhoneNumber(), deleteStaff.getAddress(), deleteStaff.getIcNum());
                System.out.println("===========================================================================================================================================================================================================================");

                System.out.print("\nConfirm to delete?(1 = Yes, other = No): ");
                int confirmDelete = deleteStaff_input.nextInt();

                if (confirmDelete == 1) {
                    try {
                        staff.deleteSelectedStaff(deleteStaff);
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (!deleteId.equals(staff.getStaffId())) {
                System.out.println("You can't delete yourself!");
                deleteLoop = 0;
            }
        }while (deleteLoop == 0);
    }

    public static void changePassword() {
        Scanner changePassword_input = new Scanner(System.in);
        ArrayList<Staff> staffDatabase;
        try {
            staffDatabase = staff.getStaffDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String newPassword;
        for (Staff sd : staffDatabase) {
            if (sd.getStaffId().equals(staff.getStaffId())) {
                System.out.println("Current Password: " + sd.getPassword());
                do {
                    System.out.print("New Password: ");
                } while (!staff.validatePassword(newPassword = changePassword_input.nextLine()));

                sd.setPassword(newPassword);
                try {
                    staff.updateChangedPassword(sd);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }
}
