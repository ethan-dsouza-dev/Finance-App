package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

// Represents a Transaction summary (a list of transactions) and the methods that can be done on the list.
public class TransactionSummary implements Writable {
    private ArrayList<Transaction> transactions;

    /**
     * @EFFECTS: creates a new TransactionSummary object with an empty list of transactions.
     */
    public TransactionSummary() {
        transactions = new ArrayList<>();
    }

    /**
     * @MODIFIES: this
     * @EFFECTS: adds a new transaction to the end of transactionSummary.
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        EventLog.getInstance().logEvent(new Event("Added Transaction: " + transaction.getDate() + "; "
                + transaction.getDetails() + "; " + transaction.getAmount()));
    }

    /**
     * @EFFECTS: returns a list of transactions in transactionSummary
     */
    public ArrayList<Transaction> getTransactionSummary() {
        return transactions;
    }

    /**
     * @REQUIRES: index to be in range
     * @MODIFIES: this
     * @EFFECTS: removes the transaction at given index
     */
    public void removeTransaction(int index) {
        transactions.remove(index);
        EventLog.getInstance().logEvent(new Event("Removed Transaction at index: " + index));
    }

    /**
     * @MODIFIES: this
     * @EFFECTS: removes transactions with a given detail
     */
    public void removeTransactionWithDetails(String detail) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getDetails().equals(detail)) {
                transactions.remove(i);
            }
        }
        EventLog.getInstance().logEvent(new Event("Removed Transaction with Detail: " + detail));
    }

    /**
     * @REQUIRES: an element to exist at given index
     * @EFFECTS: returns transaction at given index
     */
    public Transaction getTransactionAtIndex(int index) {
        return transactions.get(index);
    }

    /**
     * @REQUIRES: atleast one element to be in the list.
     * @EFFECTS: returns the transaction with the greatest amount.
     */
    public Transaction findGreatestTransaction() {
        Transaction maxAmountTransaction = null;
        int maxAmount = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > maxAmount) {
                maxAmountTransaction = transaction;
                maxAmount = (int) transaction.getAmount();
            }
        }
        return maxAmountTransaction;
    }

    /**
     * @REQUIRES: transactionSummary should not be empty
     * @EFFECTS: returns the largest expense amount for the month.
     */
    public Transaction findGreatestTransactionForMonth(LocalDate now) {
        Transaction maxAmountTransaction = null;
        int maxAmount = 0;
        int numberOfElements = transactions.size();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();

            long diff = ChronoUnit.DAYS.between(transactionDate, now);

            if ((transaction.getAmount() > maxAmount) && (diff <= 30)) {
                maxAmountTransaction = transaction;
                maxAmount = (int) transaction.getAmount();
            }
        }
        return maxAmountTransaction;
    }

    public int getNumberTransactions() {
        return transactions.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: returns transactions in this transactionSummary as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
