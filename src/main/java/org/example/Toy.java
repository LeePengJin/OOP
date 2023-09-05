package org.example;

public class Toy {
    //data members
    private String toyName;
    private String toyId;
    private double weight;
    private String manufacturer;
    private Promotion toyPromotion;

    //constructor
    public Toy(){
        this("", "", 0, "");
    }

    public Toy(String toyName, String toyId, double weight, String manufacturer){
        this.toyName = toyName;
        this.toyId = toyId;
        this.weight = weight;
        this.manufacturer = manufacturer;
    }

    //getter
    public String getToyName() {
        return toyName;
    }

    public String getToyId() {
        return toyId;
    }

    public double getWeight() {
        return weight;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    //setter
    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public void setToyId(String toyId) {
        this.toyId = toyId;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    //method
    public void displayToy(){

    }
}
