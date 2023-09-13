import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Staff extends Person{
    private String staffId;
    private String position;
    private String password;

    //constructor
    public Staff () {
        super();
    }

    public Staff (String staffId, String position, String password, String icNum, String name, String gender, LocalDate dateOfBirth, String phoneNumber, String address) {
        super(name, gender, dateOfBirth, phoneNumber, address, icNum);
        this.staffId = staffId;
        this.position = position;
        this.password = password;
    }

    public Staff (String staffId, String position, String icNum, String name, String gender, LocalDate dateOfBirth, String phoneNumber, String address) {
        super(name, gender, dateOfBirth, phoneNumber, address, icNum);
        this.staffId = staffId;
        this.position = position;
    }

    //getter
    public String getStaffId() {
        return staffId;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition() {
        return position;
    }

    //setter
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    //database
    public ArrayList<Staff> getStaffDatabase()
            throws SQLException, ClassNotFoundException
    {
        String driverClassName
                = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(
                url, username, password);

        // Obtain a statement
        Statement st = con.createStatement();

        //get the data of staff table from database
        ResultSet rs = st.executeQuery("SELECT staffId, position, staffPassword, s.icNum, name, gender, dateOfBirth, phoneNumber, address FROM person p, staff s WHERE p.icNum = s.icNum");

        ArrayList<Staff> staff = new ArrayList<>();

        //store the data into array
        while (rs.next()){
            Staff staffDatabase = new Staff(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7).toLocalDate(), rs.getString(8), rs.getString(9));
            staff.add(staffDatabase);
        }

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        return staff;
    }

    public ArrayList<String> displayAllDatabase () throws ClassNotFoundException, SQLException {
        ArrayList<String> displayAll = new ArrayList<String>();

        ArrayList<Staff> staffDatabase;
        try {
            staffDatabase = getStaffDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //store the data into array with the format display
        for (Staff sd : staffDatabase) {
            displayAll.add(String.format("%-5s||%-30s||%-20s||%-6s||%-14s||%-12s||%-100s||%-14s", sd.getStaffId(), sd.getName(), sd.getPosition(), sd.getGender(), sd.getDateOfBirth(), sd.getPhoneNumber(), sd.getAddress(), sd.getIcNum()));
        }

        return displayAll;
    }

    public String getStaffName () throws ClassNotFoundException, SQLException {
        String driverClassName
                = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(
                url, username, password);

        //get the staff name from database by getting the staffId from user
        PreparedStatement statement;
        statement = con.prepareStatement("SELECT person.name FROM person, staff WHERE staff.icNum = person.icNum AND staff.staffId = ?");
        statement.setString(1, this.staffId);
        ResultSet rs = statement.executeQuery();

        String staffName = "Error!";

        while (rs.next()) {
            staffName = rs.getString(1);
        }
        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        return staffName;
    }
    //methods
    public boolean validateToLogin (String staffId, String password) {
        ArrayList<String> staffIdDatabase = new ArrayList<>();
        ArrayList<String> passwordDatabase = new ArrayList<>();
        ArrayList<Staff> staffDatabase;
        try {
            staffDatabase = getStaffDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Staff sd : staffDatabase) {
            staffIdDatabase.add(sd.getStaffId());
        }

        for (Staff sd : staffDatabase) {
            passwordDatabase.add(sd.getPassword());
        }

        boolean returnType = true;


        for (int i = 0; i < staffIdDatabase.size(); i++) {
            if (!staffId.equals(staffIdDatabase.get(i))) {
                returnType = false;
            } else {
                if (password.equals(passwordDatabase.get(i))) {
                    try {
                        System.out.println("\nWelcome back " + getStaffName());
                        setName(getStaffName());
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                    returnType = true;
                    break;
                } else {
                    returnType = false;
                }
            }
        }
        if (!returnType) {
            System.out.println("Invalid password or staffId, kindly re-enter!");
        }
        return returnType;
    }
    public boolean validateToAddStaffId (String newStaffId) {
        newStaffId = Character.toUpperCase(newStaffId.charAt(0)) + newStaffId.substring(1);

        ArrayList<String> staffIdDatabase = new ArrayList<>();
        ArrayList<Staff> staffDatabase;
        try {
            staffDatabase = getStaffDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Staff sd : staffDatabase) {
            staffIdDatabase.add(sd.getStaffId());
        }

        for (String s : staffIdDatabase) {
            if (!s.equals(newStaffId)) {
                if (newStaffId.charAt(0) != 'S') {
                    System.out.println("Invalid staffId please re-enter with the format \"S001\"");
                    return false;
                } else if (Integer.parseInt(newStaffId.substring(1)) <= 0 || Integer.parseInt(newStaffId.substring(1)) > 999) {
                    System.out.println("Invalid staffId please re-enter with the format \"S001\"");
                    return false;
                }
            }
            else {
                System.out.println("This staffId already existed!");
                return false;
            }
        }
        return true;
    }

    public void addStaffToDatabase (ArrayList<Staff> newStaff) throws ClassNotFoundException, SQLException {
        {
            String driverClassName
                    = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/oop";
            String username = "root";
            String password = "";
            String query_person
                    = "INSERT INTO person (name, gender, dateOfBirth, phoneNumber, address, icNum) VALUES (?, ?, ?, ?, ?, ?)";
            String query_staff
                    = "INSERT INTO staff (staffId, position, staffPassword, icNum) VALUES (?, ?, ?, ?)";

            ArrayList<String> birthDate = new ArrayList<String>();
            for (Staff sd: newStaff) {
                String dateString = sd.getDateOfBirth().toString();
                birthDate.add(dateString);
            }

            // Load driver class
            Class.forName(driverClassName);

            // Obtain a connection
            Connection con = DriverManager.getConnection(
                    url, username, password);

            // Obtain a statement
            Statement st = con.createStatement();

            // Create a PreparedStatement to safely insert data
            PreparedStatement ps_person = null;
            PreparedStatement ps_staff = null;

            for (int i = 0; i < newStaff.size(); i++) {
                ps_person = con.prepareStatement(query_person);
                ps_person.setString(1, newStaff.get(i).getName());
                ps_person.setString(2, newStaff.get(i).getGender());
                ps_person.setString(3, birthDate.get(i));
                ps_person.setString(4, newStaff.get(i).getPhoneNumber());
                ps_person.setString(5, newStaff.get(i).getAddress());
                ps_person.setString(6, newStaff.get(i).getIcNum());


                ps_staff = con.prepareStatement(query_staff);
                ps_staff.setString(1, newStaff.get(i).getStaffId());
                ps_staff.setString(2, newStaff.get(i).getPosition());
                ps_staff.setString(3, newStaff.get(i).getPassword());
                ps_staff.setString(4, newStaff.get(i).getIcNum());
            }

            // Execute the query
            assert ps_person != null;
            ps_person.executeUpdate();
            int count = ps_staff.executeUpdate();
            System.out.println(count + " staff added");

            // Closing the connection as per the
            // requirement with connection is completed
            con.close();
            ps_person.close();
            st.close();
            ps_staff.close();
        }
    }

    public boolean validateEditOrDeleteStaffId(String staffId) {
        //let the first character become uppercase so user can enter 's' or 'S'
        String s = Character.toUpperCase(staffId.charAt(0)) + staffId.substring(1);
        boolean returnedType = false;

        ArrayList<String> staffIdDatabase = new ArrayList<>();
        ArrayList<Staff> staffDatabase;
        try {
            staffDatabase = getStaffDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Staff sd : staffDatabase) {
            staffIdDatabase.add(sd.getStaffId());
        }

        if (s.charAt(0) != '0') {
            for (String validate : staffIdDatabase) {
                if (validate.equals(s)) {
                    returnedType = true;
                    break;
                }
            }
        }
        else {
            returnedType = true;
        }

        if (!returnedType) {
            System.out.println("\nThis Staff Id does not exist!!");
        }

        return returnedType;
    }

    public void updateEditedStaff (String keyIcNum, Staff editedStaff) throws SQLException, ClassNotFoundException {
        String driverClassName
                = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query_person
                = "UPDATE person SET name = ?, gender = ?, dateOfBirth = ?, phoneNumber = ?, address = ?, icNum = ? WHERE icNum = ?";
        String query_staff
                = "UPDATE staff SET staffId = ?, position = ?, icNum = ? WHERE icNum = ?";
        String dateString = editedStaff.getDateOfBirth().toString();

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(
                url, username, password);

        // Obtain a statement
        Statement st = con.createStatement();

        // Create a PreparedStatement to safely insert data
        PreparedStatement ps_person;
        PreparedStatement ps_staff;

        ps_person = con.prepareStatement(query_person);
        ps_person.setString(1, editedStaff.getName());
        ps_person.setString(2, editedStaff.getGender());
        ps_person.setString(3, dateString);
        ps_person.setString(4, editedStaff.getPhoneNumber());
        ps_person.setString(5, editedStaff.getAddress());
        ps_person.setString(6, editedStaff.getIcNum());
        ps_person.setString(7, keyIcNum);


        ps_staff = con.prepareStatement(query_staff);
        ps_staff.setString(1, editedStaff.getStaffId());
        ps_staff.setString(2, editedStaff.getPosition());
        ps_staff.setString(3, editedStaff.getIcNum());
        ps_staff.setString(4, keyIcNum);

        // Execute the query
        ps_person.executeUpdate();
        ps_staff.executeUpdate();
        System.out.println("Staff edited successfully!");

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        ps_person.close();
        st.close();
        ps_staff.close();
    }

    public void deleteSelectedStaff (Staff deleteStaff) throws ClassNotFoundException, SQLException {
        String driverClassName
                = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query_staff
                = "DELETE FROM staff WHERE icNum = ?";
        String query_person
                = "DELETE FROM person WHERE icNum = ?";

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(
                url, username, password);

        // Obtain a statement
        Statement st = con.createStatement();

        // Create a PreparedStatement to safely insert data
        PreparedStatement ps_person;
        PreparedStatement ps_staff;

        ps_person = con.prepareStatement(query_staff);
        ps_person.setString(1, deleteStaff.getIcNum());
        ps_staff = con.prepareStatement(query_person);
        ps_staff.setString(1, deleteStaff.getIcNum());

        // Execute the query
        ps_person.executeUpdate();
        ps_staff.executeUpdate();
        System.out.println("Staff deleted successfully!");

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        ps_person.close();
        ps_staff.close();
        st.close();
    }

    public boolean validatePassword(String password) {
        // Define the regular expression pattern
        String pattern = "^(?=.*\\d).{8,}$";

        // Compile the pattern
        Pattern regexPattern = Pattern.compile(pattern);

        // Match the input password against the pattern
        Matcher matcher = regexPattern.matcher(password);
        if (!matcher.matches()) {
            System.out.println("At least 8 characters and at least included 1 number!!");
        }

        // Return true if it matches, otherwise false
        return matcher.matches();
    }

    public void updateChangedPassword (Staff newPassword) throws ClassNotFoundException, SQLException {
        String driverClassName
                = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query
                = "UPDATE staff SET password = ? WHERE icNum = ?";

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(
                url, username, password);

        // Obtain a statement
        Statement st = con.createStatement();

        // Create a PreparedStatement to safely insert data
        PreparedStatement ps;

        ps = con.prepareStatement(query);
        ps.setString(1, newPassword.getPassword());
        ps.setString(2, newPassword.getIcNum());

        // Execute the query
        ps.executeUpdate();
        System.out.println("Password changed successfully!");

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        st.close();
        ps.close();
    }
}