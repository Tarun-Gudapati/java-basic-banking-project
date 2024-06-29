import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        // init scanner
        Scanner sc = new Scanner(System.in);

        // init bank
        Bank theBank = new Bank("Bank of Drausin");

        // add a user , which also creates a savings account
        User aUser = theBank.addUser("john", "Doe", "1234");
        Account newAccount = new Account("checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            curUser = ATM.mainMenuPrompt(theBank, sc);
            ATM.printUserMenu(curUser, sc);
        }

    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user Id: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("incorrect user id/pin combination please try again");
            }
        } while (authUser == null);
        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {
        theUser.printAccountsSummary();
        int choice;
        do {
            System.out.printf("welcome %s,what would you like to do? \n", theUser.getFirstNAme());
            System.out.println("1) show the account transaction history");
            System.out.println("2)Withdrawl");
            System.out.println("3)deposit");
            System.out.println("4)transfer");
            System.out.println("5)quit");
            System.out.println();
            System.out.println("Enter a choice");
            choice = sc.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("enter a valid choice");
            }
        } while (choice < 1 || choice > 5);
        switch (choice) {
            case 1:
                ATM.showTransactionHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawlFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;   


        }
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

    public static void showTransactionHistory(User theUser, Scanner sc) {

        int theAcct;
        do {
            System.out.printf("enter the number(1-%d) of the account\n" + "whose transactions you want to see: ",
                    theUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account.please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());
        theUser.printAcctTransHistory(theAcct);

    }

    public static void transferFunds(User theUser, Scanner sc) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        // account to tranfer from
        do {
            System.out.printf("enter the number (1-%d) of the account \n" + "to transfer from", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account please try again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        // account to transfer to
        do {
            System.out.printf("enter the number (1-%d) of the account \n" + "to transfer to", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account please try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        // the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer(max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");

            } else if (amount > acctBal) {
                System.out.println("insufficient balance amount");
            }

        } while (amount < 0 || amount > acctBal);
        theUser.addAcctTransaction(fromAcct, -1 * amount,
                String.format("Transfer to acc %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to acc %s", theUser.getAcctUUID(fromAcct)));

    }

    public static void withdrawlFunds(User theUser, Scanner sc) {

        int fromAcct;

        double amount;
        double acctBal;
        String memo;
        // account to tranfer from
        do {
            System.out.printf("enter the number (1-%d) of the account \n" + "to withdraw from", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account please try again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        do {
            System.out.printf("Enter the amount to withdraw(max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");

            } else if (amount > acctBal) {
                System.out.println("insufficient balance amount");
            }

        } while (amount < 0 || amount > acctBal);
        sc.nextLine();
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        theUser.addAcctTransaction(fromAcct, -1 * amount, memo);

    }

    public static void depositFunds(User theUser, Scanner sc) {

        int toAcct;

        double amount;
        double acctBal;
        String memo;
        // account to tranfer from
        do {
            System.out.printf("enter the number (1-%d) of the account \n" + "to deposit in : ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account please try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);
        do {
            System.out.printf("Enter the amount to transfer(max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");

            }

        } while (amount < 0);
        sc.nextLine();
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        theUser.addAcctTransaction(toAcct, amount, memo);

    }
}
