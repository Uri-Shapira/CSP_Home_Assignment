import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Store {

    private String name;
    private List<Owner> storeOwners;
    // list of users not authorized to purchase in the store
    private Set<User> blackList;
    private List<Catalog> catalogs;
    // defines discounts in percent (value) made for users making purchases
    // above or equal to some price (key)
    private Map<Double, Double> priceDiscountParameters;
    private final Logger log = Logger.getLogger();

    public Store(String name){
        this.name = name;
        storeOwners = new ArrayList<>();
        blackList = new HashSet<>();
        populateStoreCatalogs();
        addItemsToCatalogs();
        priceDiscountParameters = new HashMap<>();
    }

    public Store(String name, Owner storeOwner){
        this.name = name;
        storeOwners = new ArrayList<>();
        storeOwners.add(storeOwner);
        blackList = new HashSet<>();
        populateStoreCatalogs();
        addItemsToCatalogs();
        priceDiscountParameters = new HashMap<>();
    }

    public Map<Double, Double> getPriceDiscountParameters() {
        return priceDiscountParameters;
    }

    public void setPriceDiscountParameters(Map<Double, Double> priceDiscountParameters) {
        this.priceDiscountParameters = priceDiscountParameters;
    }

    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    private void populateStoreCatalogs(){
        catalogs = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("catalogs.txt"));) {
            String line = reader.readLine();
            while (line != null) {
                if(line.matches(name + " \\|.*")){
                    String[] storeParameters =  line.split(" \\| ");
                    catalogs.add(new Catalog(storeParameters[1]));
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            log.writeToLogFile("Failed to get catalogs of store " + name + ". Error: " + e.getMessage());
        }
    }

    public List<Owner> getStoreOwners() {
        return storeOwners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addItem(String catalogName, Item newItem) throws Exception{
        String entry = catalogName + " | " + newItem.getName() + " | " +
                       newItem.getPrice() + " | " + newItem.getDiscount() + "\n";
        File itemsDB = new File("items.txt");
        if(itemsDB.createNewFile()) {
            Files.write(Paths.get("items.txt"), (entry).getBytes(), StandardOpenOption.WRITE);
        }
        else{
            Files.write(Paths.get("items.txt"), (entry).getBytes(), StandardOpenOption.APPEND);
        }
    }

    public void addCatalog(String catalogName) throws Exception{
        String entry = name + " | " + catalogName + "\n";
        File catalogsDB = new File("catalogs.txt");
        if(catalogsDB.createNewFile()) {
            Files.write(Paths.get("catalogs.txt"), (entry).getBytes(), StandardOpenOption.WRITE);
        }
        else{
            Files.write(Paths.get("catalogs.txt"), (entry).getBytes(), StandardOpenOption.APPEND);
        }
    }

    public Catalog getCatalogByName(String catalogName){
        for(Catalog catalog : catalogs){
            if(catalog.getName().equals(catalogName)){
                return catalog;
            }
        }
        return null;
    }

    public double discountOnPrice(double price){
        double discount = 0;
        if(priceDiscountParameters.containsKey(price)){
            discount = priceDiscountParameters.get(price);
        }
        return discount;
    }
    
    public void addItemsToCatalogs(){
        try(BufferedReader reader = new BufferedReader(new FileReader("items.txt"));) {
            String line = reader.readLine();
            while (line != null) {
                String[] items =  line.split(" \\| ");
                for(Catalog catalog : catalogs) {
                    if(items[0].matches(catalog.getName() + "\\s*")){
                        String itemName = items[1];
                        double itemPrice = Double.parseDouble(items[2]);
                        double itemQuantity = Double.parseDouble(items[3]);
                        Item newItem = new Item(itemName, itemPrice);
                        catalog.getItems().add(newItem);
                    }
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            log.writeToLogFile("Failed to get addItemsToCatalogs of store " + name + ". Error: " + e.getMessage());
        }
    }

    public void processUserOrder(String customerId, ShoppingCart userShoppingCart){
        for(ShoppingCartEntity shoppingCartItem : userShoppingCart.getShoppingCartItems()){
            Catalog catalog = shoppingCartItem.getCatalog();
            Item item = shoppingCartItem.getItem();
            // Send request to MailingService to prepare items for shipment to user
            // Update DB with pending payment amount from the user
        }
        System.out.println("Store " + name + " is preparing shipment for user " + customerId +
                           " priced " + userShoppingCart.getTotalPrice());
        // Send email to customer that the order has been transferred to shipment
    }

}
