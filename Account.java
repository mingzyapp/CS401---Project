public class Account {
// vars
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private long phone;
    private String role;

    //Constructor
    public Account() {
    }

    public Account(String username, String password, String firstName, String lastName, String email, String phone, String role) {
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(Long.parseLong(phone));
        setRole(role);
    }

    //setters & getters

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getRole()
    {
        return role;
    }

    public long getPhone()
    {
        return phone;
    }

    public void setUsername(String newUsername) {
        username= newUsername;

    }

    public void setPassword(String newPassword) {
        password = newPassword;

    }

    public void setFirstName(String newFirstName) {
        firstName= newFirstName;

    }

    public void setLastName(String newLastName) {
        lastName = newLastName;

    }

    public void setEmail(String newEmail) {
        email = newEmail;

    }


    public void setRole(String newRole) {
        role = newRole;

    }

    public void setPhone(long newPhone) {
        phone = newPhone;

    }

// additional methods
    public String toString() {

        return username+"/"+password+"/"+firstName+"/"+lastName+"/"+email+"/"+role+"/"+phone;
    }

    //login

    public void login() {

    }

    public void chat(){

    }

    public void block(){

    }

    public void createGroup(){

    }

    public void searchContact(){

    }

    public void logout () {

    }

    public void getNotification() {

    }

}
