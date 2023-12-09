package com.example.electroxchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    private Context mContext;
    private ArrayList<Transaction> mTransactionList;

    public TransactionAdapter(Context context, ArrayList<Transaction> transactionList) {
        super(context, 0, transactionList);
        mContext = context;
        mTransactionList = transactionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.transaction_item_layout, parent, false);
        }

        Transaction currentTransaction = mTransactionList.get(position);

        TextView transactionId = listItem.findViewById(R.id.transactionId);
        transactionId.setText(String.valueOf(currentTransaction.getTransactionId()));

        TextView transactionDate = listItem.findViewById(R.id.transactionDate);
        transactionDate.setText(currentTransaction.getTransactionDate());

        TextView transactionAmount = listItem.findViewById(R.id.transactionAmount);
        transactionAmount.setText(String.valueOf(currentTransaction.getTotalAmount()));

        return listItem;
    }
}
