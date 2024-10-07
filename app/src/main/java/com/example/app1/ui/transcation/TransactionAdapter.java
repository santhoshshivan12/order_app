package com.example.app1.ui.transcation;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app1.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<transaction> transactionList;


    // Constructor
    public TransactionAdapter(List<transaction> transactionList) {
        this.transactionList = transactionList;
        Log.d("TransactionAdapter", "Transaction list size: " + transactionList.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView transactionIcon;
        private TextView transactionAmount;
        private TextView transactionDate;
        private TextView transactionUser;
        private TextView transactionOrderNo;
        private TextView transactionDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            transactionIcon = itemView.findViewById(R.id.transactionIcon);
            transactionAmount = itemView.findViewById(R.id.transactionAmount);
            transactionDate = itemView.findViewById(R.id.transactionDate);
            transactionUser = itemView.findViewById(R.id.transactionuser);
            transactionOrderNo = itemView.findViewById(R.id.transactioncheckno);
            transactionDetails = itemView.findViewById(R.id.transactionorderno);
        }
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        transaction transaction = transactionList.get(position);
        Log.d("TransactionAdapter", "Binding transaction at position: " + position);

        holder.transactionAmount.setText(transaction.getAmount());
        holder.transactionDate.setText(transaction.getDate());
        Log.d("TransactionAdapter", "Transaction date: " + transaction.getDate());
        holder.transactionOrderNo.setText(transaction.getOrderNo());

        holder.transactionUser.setText(transaction.getUser());
        holder.transactionDetails.setText(transaction.getTransactionDetails());

        // Set the icon based on the transaction type (dummy for now)
//        holder.transactionIcon.setImageResource(R.drawable.ic_transaction); // Use a default icon
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

}
