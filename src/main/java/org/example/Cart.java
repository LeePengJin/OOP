package org.example;

public class Cart {
    //data members
    private String cartId;
    private int toyQuantity;
    private Toy toys;

    //constructor
    public Cart(){
        this("", 0);
    }

    public Cart(String cartId, int toyQuantity){
        this.cartId = cartId;
        this.toyQuantity = toyQuantity;
    }

    //getter
    public int getToyQuantity() {
        return toyQuantity;
    }

    public String getCartId() {
        return cartId;
    }

    //setter
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public void setToyQuantity(int toyQuantity) {
        this.toyQuantity = toyQuantity;
    }

    //method
}
