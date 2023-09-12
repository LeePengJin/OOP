import java.sql.*;
public class Category {
    //data members
    private String categoryId;
    private String categoryName;
    private String categoryDesc;

    public Category(){
        this("","","");
    }
    public Category(String categoryId, String categoryName, String categoryDesc){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public static Category getData() throws SQLException, ClassNotFoundException {

        //connect to database
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop";
        String username = "root";
        String password = "";
        String query = "";

        // Load driver class
        Class.forName(driverClassName);

        // Obtain a connection
        Connection con = DriverManager.getConnection(url, username, password);

        //execute query
        PreparedStatement getCategory;
        getCategory = con.prepareStatement("SELECT * FROM category");
        ResultSet getCat = getCategory.executeQuery();

        System.out.println(" ");

        while (getCat.next()) {
            return new Category(getCat.getString("categoryID"), getCat.getString("categoryName"), getCat.getString("categoryDesc"));
        }

        return null;
    }

}


