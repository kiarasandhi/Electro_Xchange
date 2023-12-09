package com.example.electroxchange;

public class Transaction {
    private int transactionId;
    private String transactionDate;
    private double totalAmount;

    public Transaction(int transactionId, String transactionDate, double totalAmount) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.totalAmount = totalAmount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
