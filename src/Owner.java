import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Owner object is a User that has special privileges - he holds a list of stores which he owns.
 * In these stores, the owner can add, remove, or edit catalogs and catalog items
 */
public class Owner extends User{

    List<Store> storesOwned;

    public Owner(String userId, String firstName, String lastName, String address, String email, String password){
        super(userId, firstName, lastName, address, email, password);
        getStoresOwned();
    }

    private void getStoresOwned() {
        storesOwned = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("storeOwners.txt"));) {
            String line = reader.readLine();
            while (line != null) {
                if(line.matches(userId + " \\|.*")){
                    String[] storeParameters =  line.split(" \\| ");
                    if(storeParameters[0].matches(userId)){
                        storesOwned.add(new Store(storeParameters[1]));
                    }
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            log.writeToLogFile("Failed to get stores owned by owner " + userId + ". Error: " + e.getMessage());
        }
    }

    public void addCatalog(String catalogName, String storeName){
        boolean catalogExists = false;
        Store store = getStoreOwnedByName(storeName);
        if(store == null){
            String errorMessage = "Failed to add catalog " + catalogName + " to store " + storeName
                    + ". Error: Owner " + userId + " does not own this store.";
            log.writeToLogFile(errorMessage);
            System.err.println(errorMessage);
        }
        else{
            for(Catalog catalog : store.getCatalogs()){
                if(catalog.getName().equals(catalogName)){
                    catalogExists = true;
                }
            }
            if(!catalogExists) {
                try{
                    store.addCatalog(catalogName);
                    store.getCatalogs().add(new Catalog(catalogName));
                }
                catch(Exception e){
                    log.writeToLogFile("Failed to add catalog " + catalogName + " to store " + storeName
                            + ". Error: " + e.getMessage());
                }

            }
            else{
                String errorMessage = "Catalog " + catalogName + " already exists in store " + storeName;
                log.writeToLogFile(errorMessage);
                System.err.println(errorMessage);
            }
        }

    }

    public void addItemToCatalog(String storeName, String catalogName, String itemName, double itemPrice){
        Store store = getStoreOwnedByName(storeName);
        Catalog catalog = store.getCatalogByName(catalogName);
        boolean itemExists = false;
        for(Item item : catalog.getItems()){
            if(item.getName().equals(itemName)){
                itemExists = true;
            }
        }
        Item newItem = new Item(itemName, itemPrice);
        if(!itemExists){
            try{
                store.addItem(catalogName, newItem);
                catalog.getItems().add(newItem);
                log.writeToLogFile("Item " + itemName + " successfully added to store " + storeName
                        + " in catalog " + catalogName);
            }
            catch(Exception e){
                String errorMessage ="Failed to add " + itemName + " to store " + storeName
                        + " Error: " + e.getMessage();
                log.writeToLogFile(errorMessage);
                System.err.println(errorMessage);
            }

        }
        else {
            String errorMessage = "Item " + itemName + " already exists in store " + storeName
                    + " in catalog " + catalogName;
            log.writeToLogFile(errorMessage);
            System.err.println(errorMessage);
        }
    }

    public void removeItemFromCatalog(String storeName, String catalogName, String itemName){
        Store store = getStoreOwnedByName(storeName);
        Catalog catalog = store.getCatalogByName(catalogName);
        Item item = catalog.getItemByName(itemName);
    }

    public Store getStoreOwnedByName(String storeName){
        for(Store ownedStore : storesOwned){
            if(ownedStore.getName().equals(storeName)){
                return ownedStore;
            }
        }
        return null;
    }

    public void registerUser(){
        registerUser("Owner");
        // Add some specific authorization only allowed to store owners in the mall
    }

    public void refundUser(double amount, String userId){
        log.writeToLogFile("User " + userId + " has been refunded by store owner " +
                           firstName + " " + lastName + " for an amount of " + amount + " ILS.");
    }

    public void openNewStore(String storeName){
        try{
            Mall.addStore(storeName, userId);
            storesOwned.add(new Store(storeName));
            log.writeToLogFile("Owner " + firstName + " " + lastName +
                                " has opened a new store called " + storeName);
        }
        catch(Exception e){
            String errorMessage = "Failed to add store for owner. Error message: " + e.getMessage();
            log.writeToLogFile(errorMessage);
            System.err.println(errorMessage);
        }

    }

}
