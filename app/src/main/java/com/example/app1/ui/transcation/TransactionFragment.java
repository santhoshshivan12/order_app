package com.example.app1.ui.transcation;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app1.R;

import java.util.List;


public class TransactionFragment extends Fragment {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<transaction> transactionList;
    private transactionManager transactionManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_transaction2, container, false);
        recyclerView = view.findViewById(R.id.transaction_item_layout);
        TextView emptyStateMessage = view.findViewById(R.id.empty_state_message);

        // Get the list of transactions
        List<transaction> transactions = transactionManager.getInstance().getTransactions();
        Log.d("TransactionFragment", "Transactions size: " + transactions.size());

        // Set up the RecyclerView and adapter
        adapter = new TransactionAdapter(transactions);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);

        // Check if there are no transactions and update UI accordingly
        if (transactions.isEmpty()) {
            recyclerView.setVisibility(View.GONE); // Hide RecyclerView
            emptyStateMessage.setVisibility(View.VISIBLE); // Show empty message
        } else {
            recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
            emptyStateMessage.setVisibility(View.GONE); // Hide empty message
        }
        return view;
    }
}