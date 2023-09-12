import java.sql.*;
public class Toy {
    //data members
    private String toyId;
    private String toyName;
    private String ageSuitable;
    private String manufracturer;
    private double toyPrice;
    private Category categoryId;

    public Toy(){
        this("", "", 0, "", "", null);
    }

    public Toy(String toyId, String toyName, double toyPrice) {
        this.toyId = toyId;
        this.toyName = toyName;
        this.toyPrice = toyPrice;
    }

    public Toy(String toyId, String toyName, double toyPrice, String ageSuitable, String manufracturer, Category categoryId){

        this.toyName = toyName;
        this.toyId = toyId;
        this.toyPrice = toyPrice;
        this.ageSuitable = ageSuitable;
        this.manufracturer = manufracturer;
        this.categoryId = categoryId;
    }

    public String getToyId() {
        return toyId;
    }

    public void setToyId(String toyId) {
        this.toyId = toyId;
    }

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {

        this.toyName = toyName;
    }

    public String getAgeSuitable() {
        return ageSuitable;
    }

    public void setAgeSuitable(String ageSuitable) {
        this.ageSuitable = ageSuitable;
    }

    public String getManufacturer() {
        return manufracturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufracturer = manufacturer;
    }

    public double getToyPrice() {
        return toyPrice;
    }

    public void setToyPrice(double toyPrice) {
        this.toyPrice = toyPrice;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public static Toy[] getData(String categoryId, Category cat) throws SQLException, ClassNotFoundException {

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
        PreparedStatement getToy;
        getToy = con.prepareStatement("SELECT * FROM toy WHERE categoryId = ?");
        getToy.setString(1, categoryId);
        ResultSet gc = getToy.executeQuery();

        Toy[] toyArr=new Toy[99];

        System.out.println(" ");

        int i = 0;
        while (gc.next()) {

            toyArr[i] = new Toy(gc.getString("toyID"), gc.getString("toyName"), gc.getDouble("toyPrice"), gc.getString("manufracturar"), gc.getString("ageSuitable"), new Category(gc.getString("categoryID"), cat.getCategoryName(), cat.getCategoryDesc()));
            i++;
        }
        return toyArr;
    }

    public static int displayToy(Toy[] toysArr){

        //show all the Toys with its details
        System.out.printf("%100s\n", "====================================================================================================");
        System.out.printf("%-3s %25s %40s   %-12s   %7s\n","No.", "      Toy Name", "Manufacturer", "Age Suitable", "Price");
        System.out.printf("%100s\n", "====================================================================================================");
        int i = 0;
        do {
            System.out.printf("%02d.   %-50s    %-12s  %-10s      RM%6.2f\n",i+1, toysArr[i].getToyName(), toysArr[i].getAgeSuitable(), toysArr[i].getManufacturer(), toysArr[i].getToyPrice());
            i++;
        } while (toysArr[i] != null);
        System.out.printf("%100s\n", "====================================================================================================");
        return i;
    }

    public static String getToyID(String catID, int toySelected){
        String letter="", d1="", d2="", digit="" ;
        //design the ToyID with the selection that choose by user
        //so that user can only type in the number(1, 2, 3,...) not type in the toyID
        //it will decrease the probability of user enter wrong input
        switch (catID){//letter part of the ID
            case "RY01":
                letter = "TL";
                break;
            case "RY02":
                letter = "TC";
                break;
            case "RY03":
                letter = "TG";
                break;
            case "RY04":
                letter = "TD";
                break;
            case "RY05":
                letter = "TP";
                break;
            default:
                break;
        }
        if(toySelected<10){  //Digit part of the ID
            d1 = "0";
            d2 = Integer.toString(toySelected);
            digit = d1+d2;
        }else {
            digit = Integer.toString(toySelected);
        }
        //combine the letter and digit to become a complete ID
        return letter + digit;

    }

    public static Toy getToyInfo(String fullToyID, Category cat, Toy toy) throws ClassNotFoundException, SQLException {

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
        PreparedStatement getToyInfo;
        getToyInfo = con.prepareStatement("SELECT * FROM toy WHERE  toyId = ?");
        getToyInfo.setString(1, fullToyID);
        ResultSet toyInfo = getToyInfo.executeQuery();
        Toy toySelected = new Toy();

        //int i = 0;
        while (toyInfo.next()) {
            toySelected = new Toy(toyInfo.getString("toyID"), toyInfo.getString("toyName"), toyInfo.getDouble("toyPrice"), toyInfo.getString("manufracturar"),
                    toyInfo.getString("ageSuitable"), new Category(toyInfo.getString("categoryID"), cat.getCategoryName(), cat.getCategoryDesc()));
            //i++;
        }

        return toySelected;
    }

    public static void displayOrder(Toy[] toysSelected, double[] newToysPrice,int[] quantity, int totalToy) {

        System.out.println("\n\n\n____________________________________________________________________________________________________________________________");
        System.out.printf("%60s\n", "Order Details");
        System.out.println("____________________________________________________________________________________________________________________________");
        System.out.printf("%-10s %15s %-35s %-15s %-20s %-11s %-13s\n", "Toy ID", " ", "Toy Name", "Normal Price", "Price Promotion", "Quantity", "Total Price");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");

        for(int i=0;i<totalToy;i++){
            System.out.printf(" %-9s %-50s   RM%7.2f       RM%7.2f  %13d         RM%7.2f\n", toysSelected[i].getToyId(), toysSelected[i].getToyName(), toysSelected[i].getToyPrice(), newToysPrice[i], quantity[i], newToysPrice[i] * quantity[i]);
            //show the order details
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        }
    }



    public static void passToPayment(Toy[] toysSelected, double[] newToyPrice, int[] toyQuantity, int index){
        System.out.println("\n           Payment\n");
        for(int i = 0; i < index; i++) {
            System.out.printf("%-10s %s %.2f %5d\n",toysSelected[i].getToyId(), "   " , newToyPrice[i], toyQuantity[i]);
        }
    }
}
