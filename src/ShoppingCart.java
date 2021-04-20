import java.util.ArrayList;
import java.util.List;

/**
 * Object holding a list of all items a user has decided to purchase
 */
public class ShoppingCart {

    private Store store;
    private List<ShoppingCartEntity> shoppingCartItems;
    private double totalPrice;

    public ShoppingCart(Store store){
        this.store = store;
        shoppingCartItems = new ArrayList<>();
        totalPrice = 0;
    }
    public List<ShoppingCartEntity> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(List<ShoppingCartEntity> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
