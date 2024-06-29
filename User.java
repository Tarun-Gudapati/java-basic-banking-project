import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    // first name of the user
    private String firstname;
    // last name of the user
    private String lastname;
    // the id of user
    private String uuid;
    // md5 hash of the users pin number
    private byte pinhash[];
    // list of all accounts of this user
    private ArrayList<Account> accounts;

    public User(String firstname, String lastname, String pin, Bank theBank)

    {
        this.firstname = firstname;
        this.lastname = lastname;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinhash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // getting new unique universal id for the bank user
        this.uuid = theBank.getNewUserUUID();
        // create empty list of accounts
        this.accounts = new ArrayList<Account>();
        // print log message
        System.out.printf("New user %s,%s with ID %s created.\n", lastname, firstname, this.uuid);

    }

    /*
     * adding an account for user
     * anAcct being the account to be added
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinhash);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstNAme() {
        return this.firstname;
    }

    public void printAccountsSummary() {
        System.out.printf("\n\n%s's  account summary\n ", this.firstname);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("  %d) %s\n", a + 1, this.accounts.get(a).getSummaryLine());
        }
        System.err.println();
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx)

    {
        this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}