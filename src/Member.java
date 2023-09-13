import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Member extends Person {
    private String memId;
    private double totalSpend;
    private double storePointRate;
    private int storePoint;

    //constructor
    public Member() {
        super();
    }

    public Member(String memId, double totalSpend, double storePointRate, int storePoint, String name, String gender, LocalDate dateOfBirth, String phoneNumber, String address, String icNum) {
        super(name, gender, dateOfBirth, phoneNumber, address, icNum);
        this.memId = memId;
        this.totalSpend = totalSpend;
        this.storePointRate = storePointRate;
        this.storePoint = storePoint;
    }

    //getter
    public double getStorePointRate() {
        return storePointRate;
    }

    public double getTotalSpend() {
        return totalSpend;
    }

    public int getStorePoint() {
        return storePoint;
    }

    public String getMemId() {
        return memId;
    }

    //setter
    public void setMemId(String memId) {
        this.memId = memId;
    }

    public void setStorePoint(int storePoint) {
        this.storePoint = storePoint;
    }

    public void setStorePointRate(double storePointRate) {
        this.storePointRate = storePointRate;
    }

    public void setTotalSpend(double totalSpend) {
        this.totalSpend = totalSpend;
    }

    public ArrayList<String> displayAllDatabase() throws SQLException, ClassNotFoundException {
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

        //get the staffId from database
        ResultSet rs = st.executeQuery("SELECT memId, name, gender, dateOfBirth, phoneNumber, address, m.icNum, totalSpend, storePointRate, storePoint FROM person p, member m WHERE p.icNum = m.icNum");

        ArrayList<String> displayAll = new ArrayList<String>();

        //store the data into array
        while (rs.next()) {
            displayAll.add(String.format("%-5s||%-20s||%-6s||%-10s||%-12s||%-90s||%-14s||%20.2f||%10.2f||%12d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getDouble(9), rs.getInt(10)));
        }

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        return displayAll;
    }

    public ArrayList<Member> getMemberDatabase() throws ClassNotFoundException, SQLException {
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
        ResultSet rs = st.executeQuery("SELECT memId, totalSpend, storePointRate, storePoint, name, gender, dateOfBirth, phoneNumber, address, m.icNum FROM person p, member m WHERE p.icNum = m.icNum");

        ArrayList<Member> member = new ArrayList<>();

        //store the data into array
        while (rs.next()) {
            Member memberDatabase = new Member(rs.getString(1), rs.getDouble(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDate(7).toLocalDate(), rs.getString(8), rs.getString(9), rs.getString(10));
            member.add(memberDatabase);
        }

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        return member;
    }

    public boolean validateEditOrDeleteMemberId(String memId) {
        //let the first character become uppercase so user can enter 's' or 'S'
        String s = Character.toUpperCase(memId.charAt(0)) + memId.substring(1);
        boolean returnedType = false;

        ArrayList<String> memIdDatabase = new ArrayList<>();
        ArrayList<Member> memberDatabase;
        try {
            memberDatabase = getMemberDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Member sd : memberDatabase) {
            memIdDatabase.add(sd.getMemId());
        }

        if (s.charAt(0) != '0') {
            for (String validate : memIdDatabase) {
                if (validate.equals(s)) {
                    returnedType = true;
                    break;
                }
            }
        } else {
            returnedType = true;
        }

        if (!returnedType) {
            System.out.println("\nThis Staff Id does not exist!!");
        }

        return returnedType;
    }

    public boolean validateToAddMemberId(String newMemId) {
        newMemId = Character.toUpperCase(newMemId.charAt(0)) + newMemId.substring(1);

        ArrayList<String> memIdDatabase = new ArrayList<>();
        ArrayList<Member> memberDatabase;
        try {
            memberDatabase = getMemberDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Member sd : memberDatabase) {
            memIdDatabase.add(sd.getMemId());
        }

        for (String s : memIdDatabase) {
            if (!s.equals(newMemId)) {
                if (newMemId.charAt(0) != 'M') {
                    System.out.println("Invalid staffId please re-enter with the format \"M001\"");
                    return false;
                } else if (Integer.parseInt(newMemId.substring(1)) <= 0 || Integer.parseInt(newMemId.substring(1)) > 999) {
                    System.out.println("Invalid staffId please re-enter with the format \"M001\"");
                    return false;
                }
            } else {
                System.out.println("This staffId already existed!");
                return false;
            }
        }
        return true;
    }

    public void updateEditedStaff(String keyIcNum, Member editedMember) throws SQLException, ClassNotFoundException {
        String driverClassName
                = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query_person
                = "UPDATE person SET name = ?, gender = ?, dateOfBirth = ?, phoneNumber = ?, address = ?, icNum = ? WHERE icNum = ?";
        String query_member
                = "UPDATE member SET memId = ?, icNum = ? WHERE icNum = ?";
        String dateString = editedMember.getDateOfBirth().toString();

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(
                url, username, password);

        // Obtain a statement
        Statement st = con.createStatement();

        // Create a PreparedStatement to safely insert data
        PreparedStatement ps_person;
        PreparedStatement ps_member;

        ps_person = con.prepareStatement(query_person);
        ps_person.setString(1, editedMember.getName());
        ps_person.setString(2, editedMember.getGender());
        ps_person.setString(3, dateString);
        ps_person.setString(4, editedMember.getPhoneNumber());
        ps_person.setString(5, editedMember.getAddress());
        ps_person.setString(6, editedMember.getIcNum());
        ps_person.setString(7, keyIcNum);


        ps_member = con.prepareStatement(query_member);
        ps_member.setString(1, editedMember.getMemId());
        ps_member.setString(2, editedMember.getIcNum());
        ps_member.setString(3, keyIcNum);

        // Execute the query
        ps_person.executeUpdate();
        ps_member.executeUpdate();
        System.out.println("Staff edited successfully!");

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        ps_person.close();
        st.close();
        ps_member.close();
    }

    public void addMemberToDatabase(ArrayList<Member> newMember) throws ClassNotFoundException, SQLException {
        String driverClassName
                = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query_person
                = "INSERT INTO person (name, gender, dateOfBirth, phoneNumber, address, icNum) VALUES (?, ?, ?, ?, ?, ?)";
        String query_member
                = "INSERT INTO member (memId, totalSpend, storePointRate, storePoint, icNum) VALUES (?, ?, ?, ?, ?)";

        ArrayList<String> birthDate = new ArrayList<String>();
        for (Member sd : newMember) {
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
        PreparedStatement ps_member = null;

        for (int i = 0; i < newMember.size(); i++) {
            ps_person = con.prepareStatement(query_person);
            ps_person.setString(1, newMember.get(i).getName());
            ps_person.setString(2, newMember.get(i).getGender());
            ps_person.setString(3, birthDate.get(i));
            ps_person.setString(4, newMember.get(i).getPhoneNumber());
            ps_person.setString(5, newMember.get(i).getAddress());
            ps_person.setString(6, newMember.get(i).getIcNum());


            ps_member = con.prepareStatement(query_member);
            ps_member.setString(1, newMember.get(i).getMemId());
            ps_member.setDouble(2, newMember.get(i).getTotalSpend());
            ps_member.setDouble(3, newMember.get(i).getStorePointRate());
            ps_member.setInt(3, newMember.get(i).getStorePoint());
            ps_member.setString(5, newMember.get(i).getIcNum());
        }

        // Execute the query
        assert ps_person != null;
        ps_person.executeUpdate();
        int count = ps_member.executeUpdate();
        System.out.println(count + " member added");

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        ps_person.close();
        st.close();
        ps_member.close();
    }

    public void deleteSelectedMember (Member deleteMember) throws ClassNotFoundException, SQLException {
        String driverClassName
                = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query_member
                = "DELETE FROM member WHERE icNum = ?";
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

        ps_person = con.prepareStatement(query_member);
        ps_person.setString(1, deleteMember.getIcNum());
        ps_staff = con.prepareStatement(query_person);
        ps_staff.setString(1, deleteMember.getIcNum());

        // Execute the query
        ps_person.executeUpdate();
        ps_staff.executeUpdate();
        System.out.println("Member deleted successfully!");

        // Closing the connection as per the
        // requirement with connection is completed
        con.close();
        ps_person.close();
        ps_staff.close();
        st.close();
    }
}