package ui;

import model.Transaction;
import model.TransactionSummary;

import java.util.Scanner;

public class FinanceApp {

    private Scanner scanner;
    private TransactionSummary transactionSummary;

    /**
     * @EFFECTS: creates a new instance of a FinanceApp
     */
    public FinanceApp() {
        transactionSummary = new TransactionSummary();
        scanner = new Scanner(System.in);
        runMenu();
    }

    /**
     * @MODIFIES: this
     * @EFFECTS: processes choice selected by user
     */
    private void runMenu() {
        boolean quit = false;
        String choice;

        while (!quit) {
            displayMenu();
            choice = scanner.nextLine();
            choice = choice.toUpperCase();

            if (choice.equals("Q")) {
                quit = true;
            } else {
                doChoice(choice);
            }
        }

        System.out.println("\nClosing Transaction Summary");
    }

    /**
     * @EFFECTS: displays the menu
     */
    public void displayMenu() {
        System.out.println("--------------------------");
        System.out.println("Choose an option:\n");
        System.out.println("\tA - Add a Transaction");
        System.out.println("\tB - View Transaction Summary");
        System.out.println("\tC - Remove Transaction");
        System.out.println("\tD - Find largest expense");

        System.out.println("\tQ - Quit");

        System.out.println("--------------------------");

    }

    /**
     * @MODIFIES: this
     * @EFFECTS: processes user input, performs the task selected by user
     */
    public void doChoice(String choice) {
        if (choice.equals("A")) {
            addTransaction();
        } else if (choice.equals("B")) {
            displayTransactionSummary();
            pauseMenu();
        } else if (choice.equals("C")) {
            removeTransaction();
            pauseMenu();
        } else if (choice.equals("D")) {
            displayGreatestExpense();
            pauseMenu();
        } else {
            System.out.println("Selection not valid...");
            pauseMenu();
        }
    }

    /**
     * @EFFECTS: waits for user-input
     */
    private void pauseMenu() {
        System.out.println("Press any key to go to menu.");
        scanner.nextLine();
    }


    /**
     * @MODIFIES: this
     * @EFFECTS: adds a transaction to the transaction summary
     */
    public void addTransaction() {
        transactionSummary.addTransaction(collectTransactionData());
        System.out.println("Added Transaction");
    }

    /**
     * @EFFECTS: collects data from the user about the transaction and creates a
     *           new Transaction object with those details.
     */
    public Transaction collectTransactionData() {

        System.out.println("Enter Date: ");
        String transactionDate = scanner.nextLine();

        System.out.println("\nEnter Details: ");
        String transactionDetail = scanner.nextLine();

        System.out.println("\nEnter Amount: ");
        double transactionAmount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("\nEnter Category: ");
        String transactionCategory = scanner.nextLine();

        Transaction newTransaction = new Transaction(transactionDate, transactionDetail, transactionAmount,
                transactionCategory);

        return newTransaction;

    }

    /**
     * @EFFECTS: displays all trasactions in the transaction summary
     */
    public void displayTransactionSummary() {
        System.out.println("----------------------------------\n");
        System.out.println("Date, Details, Amount, Category\n");

        for (Transaction transaction : transactionSummary.getTransactionSummary()) {
            displayTransaction(transaction);
        }

        System.out.println("----------------------------------\n");

    }

    /**
     * @EFFECTS: displays parameters of a Transaction object to the console.
     */
    private void displayTransaction(Transaction transaction) {
        System.out.print(transaction.getDate() + "\t");
        System.out.print(transaction.getDetails() + "\t");
        System.out.print(transaction.getAmount() + "\t");
        System.out.print(transaction.getCategory() + "\n");
    }

    /**
     * @REQUIRES: index must not be out of range.
     * @MODIFIES: this
     * @EFFECTS: asks the user which for the index at which the transaction should be deleted.
     */
    public void removeTransaction() {
        displayTransactionSummary();
        System.out.println("Enter the Index of the transaction to be removed: ");
        int index = scanner.nextInt();
        transactionSummary.removeTransaction(index);
        System.out.println("Removed Transaction");
    }

    /**
     * @EFFECTS: prints to the console the expense with the highest amount in transactionSummary.
     */
    public void displayGreatestExpense() {
        Transaction highestExpense = null;
        System.out.println("Your greatest expense is shown below: ");
        highestExpense = transactionSummary.findGreatestTransaction();
        System.out.println("Date, Details, Amount, Category\n");
        displayTransaction(highestExpense);
    }


}