import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Person {
    protected String name;
    protected String gender;
    protected LocalDate dateOfBirth;
    protected String phoneNumber;
    protected String address;
    protected String icNum;

    //constructor
    public Person() {}

    public Person(String name, String gender, LocalDate dateOfBirth, String phoneNumber, String address, String icNum) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.icNum = icNum;
    }

    //getter
    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }

    public String getIcNum() {
        return icNum;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIcNum(String icNum) {
        this.icNum = icNum;
    }

    //database
    public abstract ArrayList<String> displayAllDatabase()
            throws SQLException, ClassNotFoundException;

    //methods
    public boolean validateNewIcNum (String icNumber, LocalDate inputDate) {
        // Define the regular expression pattern for IC numbers with birthdates
        String pattern = "\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])-\\d{2}-\\d{4}";

        // Compile the pattern
        Pattern regexPattern = Pattern.compile(pattern);

        // Match the input IC number against the pattern
        Matcher matcher = regexPattern.matcher(icNumber);

        if (matcher.matches()) {
            // Parse the birthdate from the IC number
            String year = icNumber.substring(0, 2);
            String month = icNumber.substring(2, 4);
            String day = icNumber.substring(4, 6);

            String dateString = inputDate.format(DateTimeFormatter.ofPattern("yyMMdd"));

            // Create a LocalDate object from the parsed values
            String icBirthDate = year + month + day;

            // Check if the IC birthdate matches the input date
            if (!icBirthDate.equals(dateString)) {
                System.out.println("Invalid Input! Please follow the date you inserted just now!");
            }
            return icBirthDate.equals(dateString);
        } else {
            // IC number does not match the expected format
            System.out.println("Invalid Input! Please follow the format xxxxxx-xx-xxxx where 'x' represents number.");
            return false;
        }
    }

    public boolean validateNewGender(String newStaffGender) {
        if (newStaffGender.length() == 1) {
            char character = Character.toUpperCase(newStaffGender.charAt(0));

            // Check if the character is valid (either 'M' or 'F')
            if (character == 'm' || character == 'M' || character == 'f' || character == 'F') {
                return true;
            } else {
                System.out.println("\ninvalid Input! Please Enter 'M' or 'F'!");
                return false;
            }
        } else {
            System.out.println("\ninvalid Input! Please Enter 'M' or 'F'!");
            return false;
        }
    }

    public boolean validateNewPhoneNumber(String phoneNumber) {
        // Define the regular expression pattern
        String pattern = "\\d{3}-\\d{8}|\\d{3}-\\d{7}";

        // Compile the pattern
        Pattern regex = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher matcher = regex.matcher(phoneNumber);

        // Check if the input matches the pattern
        if (matcher.matches()) {
            return true;
        } else {
            System.out.println("\nInvalid Format! Please follow the format xxx-xxxxxxx where x represent number!");
            return false;
        }
    }

    // Method to check if the age is valid (at least minAge years old)
    public boolean isAgeValid(LocalDate birthDate, int minAge) {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears() >= minAge;
    }

    public LocalDate validateBirthDate (String dateInput) {
        try {
            // Parse the user input into a LocalDate object
            LocalDate newStaffBirthDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Check if the age is valid (more than 18 years old
            if (!isAgeValid(newStaffBirthDate, 18)) {
                System.out.println("The person must be at least 18 years old.");
                return null;
            }
            return newStaffBirthDate;
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter a date in the format yyyy-MM-dd.");
            return null;
        }
    }
}