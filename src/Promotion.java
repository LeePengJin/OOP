import java.sql.*;
import java.time.LocalDate;
public class Promotion {

    //data members
    private String promotionCode;
    private LocalDate promotionStartDate;
    private LocalDate promotionEndDate;
    private String promotionDesc;
    private double promotionDiscount;
    private Toy toy;

    public Promotion(){
        this("",null,null,"",0, null);
    }

    public Promotion(String promotionCode, LocalDate promotionStartDate, LocalDate promotionEndDate, String promotionDesc, double promotionDiscount, Toy toy){
        this.promotionCode = promotionCode;
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;
        this.promotionDesc = promotionDesc;
        this.promotionDiscount = promotionDiscount;
        this.toy = toy;
    }


    //getter & setter
    public String getPromotionCode() {
        return promotionCode;
    }

    public LocalDate getPromotionStartDate() {
        return promotionStartDate;
    }

    public LocalDate getPromotionEndDate() {
        return promotionEndDate;
    }

    public String getPromotionDesc() {
        return promotionDesc;
    }

    public double getPromotionDiscount() {
        return promotionDiscount;
    }

    public Toy getToy() {
        return toy;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public void setPromotionStartDate(LocalDate promotionStartDate) {
        this.promotionStartDate = promotionStartDate;
    }

    public void setPromotionEndDate(LocalDate promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }

    public void setPromotionDesc(String promotionDesc) {
        this.promotionDesc = promotionDesc;
    }

    public void setPromotionDiscount(double promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public void setToy(Toy toy) {
        this.toy = toy;
    }


    //methods
    public static Promotion[] getData(Toy toy, Category cat) throws SQLException, ClassNotFoundException {

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
        PreparedStatement getPromotion;
        getPromotion = con.prepareStatement("SELECT * FROM promotion");
        ResultSet getPromo = getPromotion.executeQuery();

        System.out.println(" ");

        Promotion[] promoArr = new Promotion[10];
        int i = 0;
        while (getPromo.next()) {
            promoArr[i] = new Promotion(getPromo.getString("promotionCode"), LocalDate.parse(getPromo.getString("promotionStartDate")),
                    LocalDate.parse(getPromo.getString("promotionEndDate")), getPromo.getString("promotionDesc"), getPromo.getDouble("promotionDiscount"),
                    new Toy(getPromo.getString("toyId"), toy.getToyName(), toy.getToyPrice(), toy.getManufacturer(), toy.getAgeSuitable(),
                            new Category(cat.getCategoryId(), cat.getCategoryName(), cat.getCategoryDesc())));
            i++;
        }
        return promoArr;
    }

    public static double[] checkPromotion(Promotion[] promoArr, Toy[] toysSelected, int totalToy) {

        LocalDate now = LocalDate.now();
        double[] newToysPrice = new double[99];

        for (int i = 0; i < totalToy; i++) {
            for (int x = 0; x < promoArr.length; x++) {
                if (toysSelected[i].getToyId().equals(promoArr[x].toy.getToyId())) {//compare the toyID that user selected and toyID in database
                    //if toyID have same means have promotion
                    //check whether the promotion is Active or not (valid - current date is in the promotion period)
                    if ((now.isBefore(promoArr[x].getPromotionEndDate()) || now.isEqual(promoArr[x].getPromotionEndDate()))
                            && (now.isAfter(promoArr[x].getPromotionStartDate()) || now.isEqual(promoArr[x].getPromotionStartDate()))) {
                        //if valid, calculate the price after promotion
                        System.out.printf("The Promotion(%s) of '%s' is Active\n", promoArr[x].getPromotionCode(), toysSelected[i].getToyName());
                        newToysPrice[i] = calcPromotion(promoArr[x].getPromotionDiscount(), toysSelected[i].getToyPrice());
                        break;
                    } else {
                        //if not valid, print the NOT Active msg
                        System.out.printf("The Promotion(%s) of '%s' is NOT Active\n", promoArr[x].getPromotionCode(), toysSelected[i].getToyName());
                    }
                } else {
                    //if no same toyID between user selection and database
                    //keep the normal price
                    newToysPrice[i] = toysSelected[i].getToyPrice();
                }
            }
        }
        return newToysPrice;
    }

    public static double calcPromotion(double promoRate, double price) {
        double toyPrice;//calculate the price with the promotion discount rate
        toyPrice = price - (price * promoRate);
        return toyPrice;
    }

}
