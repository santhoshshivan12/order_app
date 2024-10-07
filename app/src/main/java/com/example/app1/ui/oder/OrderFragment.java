package com.example.app1.ui.oder;


import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.app1.R;
import com.example.app1.ui.transcation.TransactionFragment;
import com.example.app1.ui.transcation.transaction;
import com.example.app1.ui.transcation.transactionManager;


import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.zip.Inflater;

public class OrderFragment extends Fragment implements OrderAdapter.OnItemRemovedListener {

    private ImageView closeButton;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<CartItem> orderList; // List of orders
    private TextView subtotalText, taxText, totalText,checkno,customer,table;
    private Button confirmAndPayButton, toGoButton;
    private static int transactionCounter = 1;

    SharedPreferences sp;


    private static final double TAX_RATE = 0.0825; // Tax rate (8.25%)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oder, container, false);
        table=view.findViewById(R.id.table_no);
        checkno=view.findViewById(R.id.check_no);
        customer=view.findViewById(R.id.customer);
        recyclerView = view.findViewById(R.id.cart_recycler_view);
        subtotalText = view.findViewById(R.id.subtotal);
        taxText = view.findViewById(R.id.tax);
        totalText = view.findViewById(R.id.total);
        confirmAndPayButton = view.findViewById(R.id.confirm_button);
        toGoButton = view.findViewById(R.id.togo_button);
        closeButton=view.findViewById(R.id.close_button);
        sp= getActivity().getSharedPreferences("user",MODE_PRIVATE);

        // Initialize order list and adapter
        orderList = OrderManager.getInstance().getOrders();
        Log.d("OrderFragment", "Order list size: " + orderList.size()); // Get orders from OrderManager
        orderAdapter = new OrderAdapter(orderList, this);
        Log.d("OrderFragment", "Adapter created");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderAdapter);

        // Setup the Confirm and Pay button
        confirmAndPayButton.setOnClickListener(v ->  {
            // Implement order confirmation logic here
            double subtotal = 0;
            for (CartItem order : orderList) {
                subtotal += order.getPrice() * order.getCount();
            }
            double tax = subtotal * TAX_RATE;
            double total = subtotal + tax;

            // Get the customer information
            String customerName = customer.getText().toString(); // Customer name or "Guest"
            String orderNo = checkno.getText().toString(); // Check No
            String status = "Paid"; // Assuming status is paid after confirmation

            // Generate a sequential transaction number starting from 0001
            String transactionNumber = String.format("%04d", transactionCounter);

            // Increment the transaction counter for the next transaction
            transactionCounter++;

            // Format the current time in HH:mm
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String currentTime = timeFormat.format(new Date());

            // Create a new Transaction object
            transaction newTransaction = new transaction(
                    String.format("$%.2f", total),  // Amount
                    currentTime,                     // Date (current date)
                    sp.getString("user","").equals("user") ? "Guest" : customerName,  // Username or Guest
                    sp.getString("user","").equals("user") ? "-----": orderNo,                       // Check No
                    transactionNumber // Transaction No and time

            );

            ArrayList<transaction> transactionList=new ArrayList<>();
            transactionManager.getInstance().addTransaction(newTransaction);


//            TransactionFragment tf=new TransactionFragment(newTransaction);



            Log.d("OrderFragment", "Transaction created");

            Navigation.findNavController(view).navigate(R.id.action_nav_oder_to_nav_transaction);
            OrderManager.getInstance().clearOrders();
            customer.setText("----");
            checkno.setText("----");
            table.setText("----");
            orderList.clear();





        });

        // Setup the TO GO button
        toGoButton.setOnClickListener(v -> showToGoPopup());

        // Setup the Close button
        closeButton.setOnClickListener(v -> {
            OrderManager.getInstance().clearOrders();
            customer.setText("----");
            checkno.setText("----");

            table.setText("----");
            Navigation.findNavController(view).navigate(R.id.action_nav_oder_to_nav_home);



        });

        // Update the total
        updateTotal();

        return view;
    }

        private void resetOrderAndClose() {
            // Clear the order list
            OrderManager.getInstance().clearOrders();
            customer.setText("----");
            checkno.setText("----");

            table.setText("----");
            // Navigate back to the main menu or previous screen
            requireActivity().onBackPressed(); // Simulate pressing the back button
        }




    // Update the total, tax, and subtotal displays
    private void updateTotal() {
        double subtotal = 0;
        for (CartItem order : orderList) {
            subtotal += order.getPrice() * order.getCount();
        }
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;


        subtotalText.setText(String.format("$%.2f", subtotal));
        taxText.setText(String.format("$%.2f", tax));
        totalText.setText(String.format("Total: $%.2f", total));

    }



    // Show the TO GO popup
    private void showToGoPopup() {
        // Logic to display the popup for TO GO functionality
        if(sp.getString("user","").equals("user")){

            Toast.makeText(getContext(), "Please login as manager", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_togo, null);
            builder.setView(dialogView);

            EditText firstNameEditText = dialogView.findViewById(R.id.first_name);
            EditText lastNameEditText = dialogView.findViewById(R.id.last_name);
            EditText mobileNumberEditText = dialogView.findViewById(R.id.mobile_number);
            EditText emailEditText = dialogView.findViewById(R.id.email);
            Button submitButton = dialogView.findViewById(R.id.submit_button);

            AlertDialog dialog = builder.create();

            submitButton.setOnClickListener(v -> {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String mobileNumber = mobileNumberEditText.getText().toString();
                String email = emailEditText.getText().toString();

                // Validate inputs before submission
                if (firstName.isEmpty() || lastName.isEmpty() || mobileNumber.isEmpty() || email.isEmpty()) {
                    // Show error message
                    return;
                }else{
                    customer.setText(firstName+" "+lastName);
                    checkno.setText(" "+OrderManager.getInstance().getOrders().size());
                    Random rn = new Random();
                    int answer = rn.nextInt(10) + 1;
                    table.setText(" "+answer);
                }

                // Attach customer info to the order
                attachCustomerToOrder(firstName, lastName, mobileNumber, email);

                dialog.dismiss(); // Close the dialog after submission
            });

            dialog.show();
        }

        // Method to attach customer information to the order

        }

    @Override
    public void onItemRemoved(CartItem item) {
        updateTotal();
    }

    private void attachCustomerToOrder(String firstName, String lastName, String mobile, String email) {
        // Assuming you have a way to attach customer info to the current order
        CustomerInfo customerInfo = new CustomerInfo(firstName, lastName, mobile, email);



    }



    // Add the new transaction to the TransactionAdapter

}






