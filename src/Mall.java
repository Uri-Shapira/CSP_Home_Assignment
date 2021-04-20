import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Mall object represents the general system which encapsulates the Stores, Customers, and Owners.
 * Although this was not in the scope of the assignment, I decided to add it to have some logical
 * Application starting point, which records stores, users, etc.
 */
public class Mall {

    /**
     * Add a store to the stores DB
     * @param storeName
     * @param ownerId
     * @throws Exception
     */
    public static void addStore(String storeName, String ownerId) throws Exception{
        if(!Mall.storeAlreadyExists(storeName)){
            File storesDB = new File("stores.txt");
            if(storesDB.createNewFile()) {
                Files.write(Paths.get("stores.txt"), (storeName+"\n").getBytes(), StandardOpenOption.WRITE);
            }
            else{
                Files.write(Paths.get("stores.txt"), (storeName+"\n").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            }
            registerOwnerToStore(storeName, ownerId);
        }
        else{
            throw new Exception("Unable to create store " + storeName + " for owner " + ownerId +
                                ". The store already exists in the mall.");
        }
    }

    /**
     * Checks in the stores database if store name is already taken
     * @param storeName
     * @return
     */
    private static boolean storeAlreadyExists(String storeName){
        try(BufferedReader reader = new BufferedReader(new FileReader("stores.txt"));) {
            String line = reader.readLine();
            while (line != null) {
                if(line.matches(storeName + "\\s*")){
                    return true;
                }
                line = reader.readLine();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    public static void registerUserInMall(String userId, String firstName, String lastName,
                                          String address, String email, String password)  throws Exception{
        File userDB = new File("users.txt");
        String entry = userId + " | " + firstName + " | " + lastName + " | " + address + " | " + email
                       + " | " + password + "\n";
        if(userDB.createNewFile()) {
            Files.write(Paths.get("users.txt"), (entry).getBytes(), StandardOpenOption.WRITE);
        }
        else{
            Files.write(Paths.get("users.txt"), (entry).getBytes(), StandardOpenOption.APPEND);
        }
    }

    /**
     * Add entry to storeOwners DB
     * The entry holds a foreign key to users and a foreign key to stores (storeName is unique)
     * @param storeName
     * @param ownerId
     * @throws Exception
     */
    private static void registerOwnerToStore(String storeName, String ownerId) throws Exception{
        File storeOwnersDB = new File("storeOwners.txt");
        String entry = ownerId + " | " + storeName + "\n";
        if(storeOwnersDB.createNewFile()) {
            Files.write(Paths.get("storeOwners.txt"), (entry).getBytes(), StandardOpenOption.WRITE);
        }
        else{
            Files.write(Paths.get("storeOwners.txt"), (entry).getBytes(), StandardOpenOption.APPEND);
        }
    }

    public static Customer getCustomerById(String userId){
        String[] userDetails = getUserDetailsById(userId);
        if(userDetails == null){
            return null;
        }
        return new Customer(userDetails[0],userDetails[1],userDetails[2],userDetails[3],userDetails[4], userDetails[5]);
    }

    public static Owner getOwnerById(String userId){
        String[] userDetails = getUserDetailsById(userId);
        if(userDetails == null){
            return null;
        }
        Owner owner = new Owner(userDetails[0],userDetails[1],userDetails[2],userDetails[3],userDetails[4], userDetails[5]);
        // Fetch stores owned from DB and add to owner
        return owner;
    }

    /**
     * Get user (customer or owner) details from DB by the user ID
     * @param userId
     * @return
     */
    private static String[] getUserDetailsById(String userId){
        try(BufferedReader reader = new BufferedReader(new FileReader("users.txt"));) {
            String line = reader.readLine();
            while (line != null) {
                if(line.matches(userId + " \\|.*")){
                    return line.split(" \\| ");
                }
                line = reader.readLine();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static Store getStoreByName(String storeName){
        try(BufferedReader reader = new BufferedReader(new FileReader("stores.txt"));) {
            String line = reader.readLine();
            while (line != null) {
                if(line.matches(storeName + "\\s*")){
                    String[] storeParameters =  line.split(" \\| ");
                    return new Store(storeParameters[0]);
                }
                line = reader.readLine();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }



}
