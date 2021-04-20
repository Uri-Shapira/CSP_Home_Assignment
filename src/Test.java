public class Test {
    public static void main(String[] args) {

        // General tests to add some data to "DB" files
        String userId = "1";
        String firstName = "Uri";
        String lastName = "Shapira";
        String address = "Tel Aviv";
        String email = "uri@uri.com";
        String password = "21313";
        Owner uriOwner = new Owner(userId, firstName, lastName, address, email, password);
        uriOwner.registerUser();

        // Opening 2 new stores
        uriOwner.openNewStore("Uri Carpets");
        uriOwner.openNewStore("Uri Furniture");

        // Try to open another store with the same name
        // EXPECTED OUTPUT - should see a failure as the store name is already taken
        uriOwner.openNewStore("Uri Carpets");

        // Adding catalogs to different stores
        uriOwner.addCatalog("2021 Carpet Catalog","Uri Carpets");
        uriOwner.addCatalog( "Summer Furniture Catalog","Uri Furniture");

        // Try to add catalog to a store that the owner does not own
        // EXPECTED OUTPUT - should see a failure as the store is not owned by this owner
        uriOwner.openNewStore("Uri Carpets");

        // Adding items to catalogs
        uriOwner.addItemToCatalog("Uri Carpets", "2021 Carpet Catalog", "Red Carpet", 250.99);
        uriOwner.addItemToCatalog("Uri Carpets", "2021 Carpet Catalog", "Green Carpet", 255.99);
        uriOwner.addItemToCatalog("Uri Carpets", "2021 Carpet Catalog", "Huge Carpet", 355);
        uriOwner.addItemToCatalog("Uri Furniture","Summer Furniture Catalog", "Couch", 3000);
        uriOwner.addItemToCatalog("Uri Furniture","Summer Furniture Catalog", "Chair", 650);
        uriOwner.addItemToCatalog("Uri Furniture","Summer Furniture Catalog", "Table", 1500);

        // Try to add a duplicate item to the same store catalog
        // EXPECTED OUTPUT - should see a failure as the item already exists
        uriOwner.addItemToCatalog("Uri Carpets", "2021 Carpet Catalog", "Red Carpet", 250.99);

        // Adding a customer
        userId = "2";
        firstName = "Yosi";
        lastName = "Abuksis";
        address = "Tel Aviv";
        email = "Yosi@uri.com";
        password = "12341231";
        Customer customerYosi = new Customer(userId, firstName, lastName, address, email, password);
        customerYosi.registerUser();

        // Attempting to add 2 lamps for Yosi
        // EXPECTED OUTPUT - should see a failure as the store catalog has no lamp
        customerYosi.addItemsToShoppingCart("Uri Furniture", "Summer Furniture Catalog", "lamp", 2);

        // Adding an existing item to Yosi's shopping cart
        customerYosi.addItemsToShoppingCart("Uri Furniture", "Summer Furniture Catalog", "Chair", 1);

        // Attempting to checkout and buy for Yosi
        // EXPECTED OUTPUT - should see a failure as Yosi has no registered payment method
        customerYosi.executePurchase();

        // adding a payment method to Yosi
        int cardNumber = 12345678;
        int cvv = 1231;
        int expirationDay = 21;
        int expirationMonth = 4;
        int expirationYear = 2026;
        customerYosi.registerPaymentMethod(cardNumber, cvv, expirationDay, expirationMonth, expirationYear);

        // Attempting to checkout and buy again
        customerYosi.executePurchase();
    }

}
