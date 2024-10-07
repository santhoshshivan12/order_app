package com.example.app1.ui.home.product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app1.R;
import com.example.app1.ui.oder.CartItem;
import com.example.app1.ui.oder.OrderManager;


public class additemFragment extends Fragment {
    // Declare views
    ImageView itemImage;
    TextView quantityText;
    TextView plusButton;
    TextView minusButton;
    Button addItemButton;
    TextView totalprice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_additem, container, false);

        // Initialize views after inflating the layout
        itemImage = view.findViewById(R.id.item_image);
        quantityText = view.findViewById(R.id.quantity_text);
        plusButton = view.findViewById(R.id.plus_button);
        minusButton = view.findViewById(R.id.minus_button);
        addItemButton = view.findViewById(R.id.add_item_button);
        totalprice = view.findViewById(R.id.total_price);


        Bundle bundle = getArguments();
        if (bundle != null) {
            String productName = bundle.getString("productName");
            double productPrice = bundle.getDouble("productPrice", 0.0);
            int productImageId = bundle.getInt("productImageId", -1);

            // Set initial values
            quantityText.setText("1"); // Default quantity is 1
            totalprice.setText("$" + String.format("%.2f", productPrice)); // Display the price
            itemImage.setImageResource(productImageId); // Set the image for the product





        // Set initial values
        quantityText.setText("1"); // Default quantity is 1
        totalprice.setText("$" + String.format("%.2f", productPrice)); // Display the price
        itemImage.setImageResource(productImageId); // Set the image for the product

        // Implement minus button click listener
        minusButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(quantityText.getText().toString());
            if (quantity > 1) {
                quantity--;
                quantityText.setText(String.valueOf(quantity));
                double totalPrice = quantity * productPrice; // Update total price based on quantity
                totalprice.setText("$" + String.format("%.2f", totalPrice));
            }
        });

        // Implement plus button click listener
        plusButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(quantityText.getText().toString());
            quantity++;
            quantityText.setText(String.valueOf(quantity));
            double totalPrice = quantity * productPrice; // Update total price based on quantity
            totalprice.setText("$" + String.format("%.2f", totalPrice));
        });

        // Implement add item button click listener
        addItemButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(quantityText.getText().toString());
            CartItem cartItem = new CartItem(productImageId,productName, productPrice, quantity);
            OrderManager.getInstance().addOrder(cartItem); // Add item to the order manager
            Log.d("AddItem", "Item added to cart: " + productName + " Quantity: " + quantity);
            // Close the activity and return to the previous one
            Navigation.findNavController(v).navigate(R.id.action_additemFragment_to_nav_oder);


        });

    }
        return  view;
}}