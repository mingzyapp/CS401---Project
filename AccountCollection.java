import java.io.*;
import java.util.Arrays;
import java.util.Comparator;


public class AccountCollection {

    // Data fields

    /**
     * The current number of DVDs in the array
     */
    private int numAcc;

    /**
     * The array to contain the DVDs
     */
    private Account[] accArray;

    /**
     * The name of the data file that contains dvd data
     */
    private String sourceName;

    /**
     * Boolean flag to indicate whether the DVD collection was
     * modified since it was last saved.
     */
    private boolean modified;

    /**
     * Constructs an empty directory as an array
     * with an initial capacity of 7. When we try to
     * insert into a full array, we will double the size of
     * the array first.
     */
    public AccountCollection() {

        numAcc = 0;
        accArray = new Account[7];
    }

    public String toString() {
        // Return a string containing all the DVDs in the
        // order they are stored in the array along with
        // the values for numdvds and the length of the array.
        // See homework instructions for proper format.

        String accString = "";
        accString += ("numAcc =" + numAcc + "\n");
        accString += ("accArray.length =" + accArray.length + "\n");
        for (int i = 0; i < numAcc; i++) {
            accString += ("dvdArray[" + i + "] =" + accArray[i].toString() + "\n");
        }
        return accString;
    }

    public Account[] getAccArray() {
        return accArray;
    }

    public void addOrModifyAcc(String username, String password, String firstName, String lastName, String email, String phone, String role) {
        // NOTE: Be careful. Running time is a string here
        // since the user might enter non-digits when prompted.
        // If the array is full and a new DVD needs to be added,
        // double the size of the array first.
        if (!validRole(role)) {
            return;
        }
        long phoneNum = validatePhoneNum(phone);
        if (phoneNum <= 0) {
            return;
        }
        modified = true;



        //Search for existing element
        int index = findAcc(username);
        if (index >= 0) {
            accArray[index].setUsername(username);
            accArray[index].setPassword(password);
            accArray[index].setFirstName(firstName);
            System.out.println("Found duplicate at index " + index);
        } else {
            if (numAcc < accArray.length) {
                accArray[numAcc] = new Account(username, password, firstName, lastName, email, phone, role);
                System.out.println("[" + numAcc + "]" + accArray[numAcc].toString());

                numAcc++;
                Arrays.sort(accArray, 0, numAcc, new AccTitleSort());
            } else {
                System.out.println("Need to increase array size first");
                doubleAccArraySize();
                System.out.println(toString());
                addOrModifyAcc(username, password, firstName, lastName, email, phone, role);
                System.out.println(toString());
            }
        }
    }

    public void removeAcc(String username) {
        int index = findAcc(username);
        if (index >= 0) {
            modified = true;
            Account[] newArray = new Account[accArray.length - 1];
            System.arraycopy(accArray, 0, newArray, 0, index);
            System.arraycopy(accArray, index + 1, newArray, index, accArray.length - index - 1);
            accArray = newArray;
            numAcc--;
        } else {
            System.out.println("Account not found");
        }

    }


    public String getAccByName(String firstName) {

        String accList = "";
        for (int i = 0; i < numAcc; i++) {
            if (accArray[i].getFirstName().compareTo(firstName) == 0) {
                accList += accArray[i].toString() + "\n";
            }
        }
        return accList;

    }


    public void loadData(String filename) {
        sourceName = filename;
        modified = false;
        try {
            FileReader fin = new FileReader("accdata.txt");
            BufferedReader bis = new BufferedReader(fin);
            String line;
            while ((line = bis.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 7) {
                    System.out.println("Error: Invalid Account entry in file \"" + line + "\"");
                    return;
                }
                addOrModifyAcc(values[0], values[1], values[2], values[3], values[4], values[5], values[6]);

            }
        } catch (IOException e) {
            System.out.println("Error: File not found: " + filename);
        }

    }

    public void save() {

        try {
            if (!modified) {
                System.out.println("No changes made.");
                return;
            }

            Writer f2 = new FileWriter("accdata.txt", false);
            if (numAcc > 0) {
                for (int i = 0; i < numAcc; i++) {
                    f2.write(accArray[i].getUsername() + "," + accArray[i].getPassword() + "," + accArray[i].getFirstName()+","+accArray[i].getLastName() + "," + accArray[i].getEmail() + "," + accArray[i].getPhone() + "," + accArray[i].getRole());
                    f2.write(System.lineSeparator());
                    //[SUPERMAN/R/127min]

                }
            }
            f2.close();
            System.out.println("Save Successful.");
            modified = false;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("Error saving file: " + e);
        }

    }

    // Additional private helper methods go here:

    private void doubleAccArraySize() {
        Account[] newArray = new Account[accArray.length * 2];
        System.arraycopy(accArray, 0, newArray, 0, numAcc);
        accArray = newArray;
    }

    private boolean validRole(String role) {
        if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("User")) {
            return true;
        } else {
            System.out.println("Invalid Role");
            return false;
        }
    }

    private long validatePhoneNum(String phone) {
        long phoneNum = 0;

        try {
            phoneNum = Long.parseLong(phone);

        } catch (NumberFormatException e) {
            System.out.println("Invalid Phone Number");
        }
        if (String.valueOf(phone).length()!= 10) {
            System.out.println("Invalid Phone Number Length.");
            return -1;
        }
        return phoneNum;
    }

    public int getTotalAcc() {

        int total = 0;
        for (int i = 0; i < numAcc; i++) {
            total += numAcc;
        }
        return total;// STUB: Remove this line.

    }

    public boolean checkAcc(String username, String password) {
        int index = findAcc(username);
        if (index >= 0) {
            if (accArray[index].getUsername().equals(username)&&accArray[index].getPassword().equals(password)) {

                return true;
                    }

                }

            return false;
        }




        private int findAcc (String username){
            if (numAcc > 0) {
                for (int i = 0; i < numAcc; i++) {
                    if (accArray[i].getUsername().equals(username)) {
                        return i;
                    }

                }
            }
            return -1;
        }

        private int findPassword (String password){
            if (numAcc > 0) {
                for (int i = 0; i < numAcc; i++) {
                    if (accArray[i].getPassword().equals(password)) {
                        return i;
                    }

                }
            }
            return -1;
        }


    }

    class AccTitleSort implements Comparator<Account> {
        public int compare(Account a, Account b) {
            return a.getUsername().compareTo(b.getUsername());
        }
    }


