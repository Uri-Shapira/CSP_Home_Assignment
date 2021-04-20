import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

/**
 * Customer object is an object of type User that can also make purchases.
 * The user is saved in users.txt, has bankdetails, a payment method, shopping history, and a shopping cart
 * including the items he chose to purchase
 */
public class Customer extends User{

    private PaymentMethod defaultPaymentMethod;
    private BankDetails bankDetails;
    private Map<Item,Integer> shoppingHistory;
    private ShoppingCart shoppingCart;

    public Customer(String userId, String firstName, String lastName, String address, String email, String password){
            super(userId, firstName, lastName, address, email, password);
            getPaymentMethod();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Map<Item, Integer> getShoppingHistory() {
        return shoppingHistory;
    }

    private void getPaymentMethod(){
        try(BufferedReader reader = new BufferedReader(new FileReader("paymentMethods.txt"));) {
            String line = reader.readLine();
            while (line != null) {
                if(line.matches(userId + " \\|.*")){
                    String[] paymentParameters =  line.split(" \\| ");
                    int cardNumber = Integer.parseInt(paymentParameters[1]);
                    int cvv = Integer.parseInt(paymentParameters[2]);
                    int expirationDay = Integer.parseInt(paymentParameters[3]);
                    int expirationMonth = Integer.parseInt(paymentParameters[4]);
                    int expirationYear = Integer.parseInt(paymentParameters[5]);
                    defaultPaymentMethod = new PaymentMethod(cardNumber,cvv,expirationDay,expirationMonth,expirationYear);
                    break;
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            log.writeToLogFile("Failed to get payment method of user " + userId + ". Error: " + e.getMessage());
        }
    }

    public void setShoppingHistory(Map<Item, Integer> shoppingHistory) {
        this.shoppingHistory = shoppingHistory;
    }

    public void addItemsToShoppingCart(String storeName, String catalogName, String itemName, int quantity) {
        Store store = Mall.getStoreByName(storeName);
        if(shoppingCart == null || !shoppingCart.getStore().getName().equals(storeName)){
            shoppingCart = new ShoppingCart(store);
        }
        Catalog catalog = null;
        Item item = null;
        boolean isValidInput = true;
        if(store == null){
            isValidInput = false;
            String errorMessage ="Bad request. Store " + storeName + " does not exist";
            log.writeToLogFile(errorMessage);
            System.out.println(errorMessage);
        }
        else{
            catalog = store.getCatalogByName(catalogName);
            if(catalog == null){
                isValidInput = false;
                String errorMessage ="Bad request. Catalog " + catalogName + " does not exist in store " + storeName;
                log.writeToLogFile(errorMessage);
                System.err.println(errorMessage);
            }
            else{
                item = catalog.getItemByName(itemName);
                if(item == null){
                    isValidInput = false;
                    String errorMessage ="Bad request. Item " + itemName + " does not exist in catalog " + catalogName;
                    log.writeToLogFile(errorMessage);
                    System.err.println(errorMessage);
                }
            }
        }
        if(isValidInput){
            log.writeToLogFile("Customer " + getFirstName() + " " + getLastName() + " is adding item " + item.getName()
                    + " to his shopping cart");
            double currentPrice = shoppingCart.getTotalPrice();
            shoppingCart.getShoppingCartItems().add(new ShoppingCartEntity(catalog, item, quantity));
            double itemsPrice = item.getPrice() * quantity;
            itemsPrice -= itemsPrice * catalog.getItemDiscount(item);
            itemsPrice *= 1 - store.discountOnPrice(itemsPrice);
            shoppingCart.setTotalPrice(currentPrice + itemsPrice);
            log.writeToLogFile("Item " + item.getName() + " added successfully");

        }
    }

    public void registerUser(){
        registerUser("Customer");
    }

    public void registerBankDetails(){

    }

    public void registerPaymentMethod(int cardNumber, int cvv, int expirationDay, int expirationMonth, int expirationYear){
        try{
            File paymentMethodDB = new File("paymentMethods.txt");
            String entry = userId + " | " + cardNumber + " | " + cvv + " | " + expirationDay + " | " + expirationMonth
                    + " | " + expirationYear + "\n";
            if(paymentMethodDB.createNewFile()) {
                Files.write(Paths.get("paymentMethods.txt"), (entry).getBytes(), StandardOpenOption.WRITE);
            }
            else{
                Files.write(Paths.get("paymentMethods.txt"), (entry).getBytes(), StandardOpenOption.APPEND);
            }
            defaultPaymentMethod = new PaymentMethod(cardNumber, cvv, expirationDay, expirationMonth,expirationYear);
        }
        catch (Exception e){
            log.writeToLogFile("Failed to register payment method for user " + userId + ". Error: " + e.getMessage());
        }

    }

    public void getRefund(double amount){
        // send request to bank API using user's bank details
    }

    public void executePurchase(){
        log.writeToLogFile("Customer " + userId + " is executing a purchase request.");
        if(defaultPaymentMethod == null || !defaultPaymentMethod.isValid()){
            String errorMessage = "Failed to execute purchase. Customer " + userId + " has no valid payment method.";
            log.writeToLogFile(errorMessage);
            System.err.println(errorMessage);
        }
        else if(shoppingCart.getShoppingCartItems() == null || shoppingCart.getShoppingCartItems().size() == 0){
            String errorMessage = "Failed to execute purchase. Shopping cart is empty.";
            log.writeToLogFile(errorMessage);
            System.err.println(errorMessage);
        }
        else {
            // makePayment();
            Store store = shoppingCart.getStore();
            log.writeToLogFile("Customer " + userId + " has completed his order.");
            store.processUserOrder(userId, shoppingCart);
            // Empty shopping cart after purchase
            shoppingCart = new ShoppingCart(store);
        }
    }

}
