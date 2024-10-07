package com.example.app1.ui.transcation;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class transactionManager {



        private static transactionManager instance;
        private List<transaction> transactions; // List to hold transactions
        private int transactionCount; // Counter for transaction numbers

        private transactionManager() {
            transactions = new ArrayList<>();
            transactionCount = 1; // Start transaction count from 1
        }

        public static synchronized transactionManager getInstance() {
            if (instance == null) {
                instance = new transactionManager();
            }
            return instance;
        }

        public List<transaction> getTransactions() {
            Log.d("transactionManager", "Getting transactions");

            Log.d("transactionManager", "Transactions: " + transactions.size());
            return transactions; // Return the list of transactions
        }

        public void addTransaction(transaction transaction) {
            transactions.add(transaction);
            Log.d("transactionManager", "Transaction added: " + transactions.size());// Add a new transaction to the list
            transactionCount++; // Increment transaction count
        }

        public int getTransactionCount() {
            return transactionCount; // Return the current transaction count
        }

        public void clearTransactions() {
            transactions.clear(); // Clear the list of transactions
            transactionCount = 1; // Reset transaction count
        }
    }


