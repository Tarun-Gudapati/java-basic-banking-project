import java.util.Date;

public class Transaction {
    // the amount of transaction happend
    private double amount;

    // the time and date of this transaction
    private Date timestamp;
    // a memo of this transaction
    private String memo;

    // the account in which the transaction is performed
    private Account inAccount;

    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";

    }

    public Transaction(double amount, String memo, Account inAccount) {
        this(amount, inAccount);
        this.memo = memo;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
        }
    }

}
