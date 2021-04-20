public abstract class User {

    protected String userId;
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String email;
    protected String password;
    protected Logger log = Logger.getLogger();

    public User(String userId, String firstName, String lastName, String address, String email, String password){
        this.address = address;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void registerUser(String userType){
        try{
            Mall.registerUserInMall(userId, firstName, lastName, address, email, password);
            log.writeToLogFile(userType + " " + firstName + " " + lastName + " has been successfuly registered");
        }
        catch(Exception e){
            log.writeToLogFile("Failed to register user " + userId + " in the mall. Error: " + e.getMessage());
        }
    }
}
