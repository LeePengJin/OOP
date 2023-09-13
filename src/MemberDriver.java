import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberDriver {
    static Member member = new Member();
    public static void memberMain(Staff staff) {
        ArrayList<String> displayMember;
        try {
            displayMember = member.displayAllDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n=============================================================================================================================================================================================================================");
        System.out.printf("||%-5s||%-20s||%-6s||%-10s||%-12s||%-90s||%-14s||%20s||%10s||%12s||", "Id", "Name", "Gender", "Birth Date", "Phone Number", "Address", "IC Number", "Total Spend(RM)", "SP Rate", "Store Point");
        System.out.println("\n=============================================================================================================================================================================================================================");

        for (String displayArray : displayMember) {
            System.out.println("||" + displayArray + "||");
        }
        System.out.println("\n=============================================================================================================================================================================================================================");

        Scanner memberDriver_input = new Scanner(System.in);
        int memberChoice = 1;

        do {
            System.out.println("\n1. Edit Member");
            System.out.println("2. Add Member");
            System.out.println("3. Delete Member");
            System.out.println("0. Back");
            System.out.print("Enter Your Next Step: ");
            memberChoice = memberDriver_input.nextInt();

            if (memberChoice >= 0 && memberChoice <= 3) {
                switch (memberChoice) {
                    case 1:
                        editMember(staff);
                        break;
                    case 2:
                        addMember(staff);
                        break;
                    case 3:
                        deleteMember(staff);
                        break;
                    case 0:
                        StaffDriver.staffMenu(staff.getStaffId());
                        break;
                }
            }
            else {
                System.out.println("\nInvalid Input! Please follow the menu given!!");
            }
        }while (memberChoice != 0);
    }

    public static void editMember (Staff staff) {
        Scanner editMember_input = new Scanner(System.in);

        String editMemId;
        do {
            System.out.print("Enter Member Id(Format: M001, 0 = Exit): ");
        } while (member.validateEditOrDeleteMemberId(editMemId = editMember_input.nextLine()));

        editMemId = Character.toUpperCase(editMemId.charAt(0)) + editMemId.substring(1);

        Member currentData = getMember(editMemId);
        Member editedMember = new Member();
        Member newMember = new Member(currentData.getMemId(), currentData.getTotalSpend(), currentData.getStorePointRate(), currentData.getStorePoint(), currentData.getName(), currentData.getGender(), currentData.getDateOfBirth(), currentData.getPhoneNumber(), currentData.getAddress(), currentData.getIcNum());
        int changeMenu = 0;
        int editChoice = 1;

        do {
            if (editMemId.charAt(0) != '0') {
                System.out.println("\nEditing Member " + editedMember + ": ");
                System.out.println("\t1. Member Id");
                System.out.println("\t2. Member Name");
                System.out.println("\t3. Member Birth Date");
                System.out.println("\t4. Member Address");
                System.out.println("\t5. Member IC Number");
                System.out.println("\t6. Member Phone Number");
                System.out.println("\t7. Member Gender");
                if (changeMenu == 0) {
                    System.out.println("\t0. Back");
                } else {
                    System.out.println("\t0. Back and Save");
                }

                System.out.print("Enter Your Choice: ");
                editChoice = editMember_input.nextInt();
                editMember_input.nextLine();

                if (editChoice >= 0 && editChoice <= 7) {
                    switch (editChoice) {
                        case 1:
                            System.out.println("Current Member Id: " + currentData.getMemId());
                            do {
                                System.out.print("Enter New Member Id(Format: M001): ");
                                newMember.setMemId(editMember_input.nextLine());
                            } while (!member.validateToAddMemberId(newMember.getMemId()));
                            newMember.setMemId(Character.toUpperCase(newMember.getMemId().charAt(0)) + newMember.getMemId().substring(1));
                            changeMenu = 1;
                            break;
                        case 2:
                            System.out.println("Current Name:  " + currentData.getName());
                            System.out.print("Enter New Member Name: ");
                            newMember.setName(editMember_input.nextLine());
                            changeMenu = 1;
                            break;
                        case 3:
                            String dateInput;
                            System.out.println("Current Birth Date: " + currentData.getDateOfBirth());
                            do {
                                System.out.print("Enter New Birth Date(yyyy-MM-dd): ");
                                dateInput = editMember_input.nextLine();
                                newMember.setDateOfBirth(member.validateBirthDate(dateInput));
                            } while (newMember.getDateOfBirth() == null);
                            changeMenu = 1;
                            break;
                        case 4:
                            System.out.println("Current Address: " + currentData.getAddress());
                            System.out.print("Enter New Address: ");
                            newMember.setAddress(editMember_input.nextLine());
                            changeMenu = 1;
                            break;
                        case  5:
                            System.out.println("Current IC Number: " + currentData.getIcNum());
                            do {
                                System.out.print("Enter New IC Number(xxxxxx-xx-xxxx)): ");
                                newMember.setIcNum(editMember_input.nextLine());
                            } while (!member.validateNewIcNum(newMember.getIcNum(), newMember.getDateOfBirth()));
                            changeMenu = 1;
                            break;
                        case 6:
                            System.out.println("Current Phone Number: " + currentData.getPhoneNumber());
                            do {
                                System.out.print("Enter New Phone Number(xxx-xxxxxxxx): ");
                                newMember.setPhoneNumber(editMember_input.nextLine());
                            } while (!newMember.validateNewPhoneNumber(newMember.getPhoneNumber()));
                            changeMenu = 1;
                            break;
                        case 7:
                            System.out.println("Current Gender: " + currentData.getGender());
                            do {
                                System.out.print("Enter New Gender('M'/'F'): ");
                                newMember.setGender(editMember_input.nextLine());
                            } while (!member.validateNewGender(newMember.getGender()));
                            newMember.setGender(newMember.getGender().toUpperCase());
                            changeMenu = 1;
                            break;
                        case 0:
                            if (changeMenu == 0) {
                                memberMain(staff);
                            } else {
                                editedMember = new Member(newMember.getMemId(), newMember.getTotalSpend(), newMember.getStorePointRate(), newMember.getStorePoint(), newMember.getName(), newMember.getGender(), newMember.getDateOfBirth(), newMember.getPhoneNumber(), newMember.getAddress(), newMember.getIcNum());
                                System.out.println("\n=========================================================================================================================================================================");
                                System.out.printf("||%-20s||%-71s||%-70s||", "", "Before Edit", "After Edited");
                                System.out.println("\n=========================================================================================================================================================================");
                                System.out.printf("||%-20s||%-71s||%-70s||", "Member Id", currentData.getMemId(), newMember.getMemId());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Member Name", currentData.getName(), newMember.getName());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Member Birth Date", currentData.getDateOfBirth(), newMember.getDateOfBirth());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Member Address", currentData.getAddress(), newMember.getAddress());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Member IC Number", currentData.getIcNum(), newMember.getIcNum());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Member Phone Number", currentData.getPhoneNumber(), newMember.getPhoneNumber());
                                System.out.printf("\n||%-20s||%-71s||%-70s||", "Member Gender", currentData.getGender(), newMember.getGender());
                                System.out.println("\n=========================================================================================================================================================================");

                                System.out.print("\nConfirm Change?(1 = Yes, other = No): ");
                                int confirmChange = editMember_input.nextInt();

                                if (confirmChange == 1) {
                                    try {
                                        member.updateEditedStaff(currentData.getIcNum(), editedMember);
                                    } catch (SQLException | ClassNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                            break;
                    }
                }
                else {
                    System.out.println("Invalid input! Please follow the menu given!");
                }
            }
        }while (editChoice != 0);
    }

    private static Member getMember(String editMemId) {
        Member currentData = new Member();
        ArrayList<Member> memberDatabase;
        try {
            memberDatabase = member.getMemberDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Member sd : memberDatabase) {
            if (sd.getMemId().equals(editMemId)) {
                currentData = new Member(sd.getMemId(), sd.getTotalSpend(), sd.getStorePointRate(), sd.getStorePoint(), sd.getName(), sd.getGender(), sd.getDateOfBirth(), sd.getPhoneNumber(), sd.getAddress(), sd.getIcNum());
            }
        }
        return currentData;
    }

    public static void addMember (Staff staff) {
        Scanner addMember_input = new Scanner(System.in);
        ArrayList<String> displayAddMemberArray = new ArrayList<>();
        ArrayList<Member> memberArray = new ArrayList<>();
        Member newMember = new Member();

        System.out.print("\nHow many staff you want to add in(0 for exit): ");
        int memberAddIn = addMember_input.nextInt();
        addMember_input.nextLine();

        if (memberAddIn != 0) {
            for (int i = 0; i < memberAddIn; i++) {
                System.out.println("\nNo: " + (i + 1));
                do {
                    System.out.print("\n\tMember Id(Format: M001): ");
                    newMember.setMemId(addMember_input.nextLine());
                } while (!staff.validateToAddStaffId(newMember.getMemId()));
                newMember.setMemId(Character.toUpperCase(newMember.getMemId().charAt(0)) + newMember.getMemId().substring(1));

                System.out.print("\n\tMember Name: ");
                newMember.setName(addMember_input.nextLine());

                do {
                    System.out.print("\n\tStaff Phone Number(xxx-xxxxxxxx): ");
                    newMember.setPhoneNumber(addMember_input.nextLine());
                } while (!newMember.validateNewPhoneNumber(newMember.getPhoneNumber()));

                do {
                    System.out.print("\n\tStaff Gender(M/F): ");
                    newMember.setGender(addMember_input.nextLine());
                } while (!staff.validateNewGender(newMember.getGender()));
                newMember.setGender(newMember.getGender().toUpperCase());

                String dateInput;
                do {
                    System.out.print("\n\tStaff Birth Date(yyyy-MM-dd): ");
                    dateInput = addMember_input.nextLine();
                    newMember.setDateOfBirth(staff.validateBirthDate(dateInput));
                } while (newMember.getDateOfBirth() == null);

                System.out.print("\n\tStaff Address: ");
                newMember.setAddress(addMember_input.nextLine());

                do {
                    System.out.print("\n\tStaff IC Number(xxxxxx-xx-xxxx): ");
                    newMember.setIcNum(addMember_input.nextLine());
                } while (!staff.validateNewIcNum(newMember.getIcNum(), newMember.getDateOfBirth()));

                //set default value
                newMember.setTotalSpend(0);
                newMember.setStorePoint(0);
                newMember.setStorePointRate(0.1);

                displayAddMemberArray.add(String.format("||%-5s||%-30s||%-12s||%-6s||%-14s||%-100s||%-14s||", newMember.getMemId(), newMember.getName(), newMember.getPhoneNumber(), newMember.getGender(), newMember.getDateOfBirth(), newMember.getAddress(), newMember.getIcNum()));
                memberArray.add(newMember);
            }

            System.out.println("\n=========================================================================================================================================================================================================");
            System.out.printf("||%-5s||%-30s||%-12s||%-6s||%-14s||%-100s||%-14s||", "Id", "Name", "Phone Number", "Gender", "Birth Date", "Address", "IC Number");
            System.out.println("\n=========================================================================================================================================================================================================");

            for (String s : displayAddMemberArray) {
                System.out.println(s);
            }

            System.out.println("=========================================================================================================================================================================================================");

            System.out.print("\n\nConfirm to add in?(1 = yes, other = no) : ");
            int confirmation = addMember_input.nextInt();

            if (confirmation == 1) {
                try {
                    member.addMemberToDatabase(memberArray);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            memberMain(staff);
        }
    }

    public static void deleteMember(Staff staff) {
        Scanner deleteMember_input = new Scanner(System.in);
        String deleteId;

        do {
            System.out.print("Enter Member Id(Format: M001, 0 = Exit): ");
        } while (!member.validateEditOrDeleteMemberId(deleteId = deleteMember_input.nextLine()));
        deleteId = Character.toUpperCase(deleteId.charAt(0)) + deleteId.substring(1);

        Member deleteMember = new Member();
        ArrayList<Member> memberDatabase;
        try {
            memberDatabase = member.getMemberDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Member sd : memberDatabase) {
            if (sd.getMemId().equals(deleteId)) {
                deleteMember = sd;
            }
        }

        if (deleteId.charAt(0) != '0' && deleteId.equals(staff.getStaffId())) {
            System.out.println("\n=============================================================================================================================================================================================================================");
            System.out.printf("||%-5s||%-20s||%-6s||%-10s||%-12s||%-90s||%-14s||%20s||%10s||%12s||", "Id", "Name", "Gender", "Birth Date", "Phone Number", "Address", "IC Number", "Total Spend(RM)", "SP Rate", "Store Point");
            System.out.println("\n=============================================================================================================================================================================================================================");
            System.out.printf("||%-5s||%-20s||%-6s||%-10s||%-12s||%-90s||%-14s||%20.2f||%10.2f||%12d||\n", deleteMember.getMemId(), deleteMember.getName(), deleteMember.getGender(), deleteMember.getDateOfBirth(), deleteMember.getPhoneNumber(), deleteMember.getAddress(), deleteMember.getIcNum(), deleteMember.getTotalSpend(), deleteMember.getStorePointRate(), deleteMember.getStorePoint());
            System.out.println("===========================================================================================================================================================================================================================");

            System.out.print("\nConfirm to delete?(1 = Yes, other = No): ");
            int confirmDelete = deleteMember_input.nextInt();

            if (confirmDelete == 1) {
                try {
                    member.deleteSelectedMember(deleteMember);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}