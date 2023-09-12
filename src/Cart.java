public class Cart {
    //data members
    private String cartId;
    private int toyQuantity;

    public Cart(){
        this("", 0);
    }
    public Cart(String cartId, int toyQuantity){
        this.cartId = cartId;
        this.toyQuantity = toyQuantity;
    }

    public String getCartId() {
        return cartId;
    }

    public int getToyQuantity() {
        return toyQuantity;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public void setToyQuantity(int toyQuantity) {
        this.toyQuantity = toyQuantity;
    }
}
