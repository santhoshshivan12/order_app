package com.example.app1.ui.home.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.app1.ui.home.category.category;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app1.R;

import java.util.ArrayList;
import java.util.List;

public class product_adapter extends RecyclerView.Adapter<product_adapter.ProductViewHolder> {
    private List<product> originalProductList = new ArrayList<>(); // All products
    private List<product> filteredProductList = new ArrayList<>(); // Filtered products


    public product_adapter(List<product> productList) {
        this.originalProductList = new ArrayList<>(productList); // Make a copy of the original list
        this.filteredProductList = new ArrayList<>(productList); // Initially, both lists are the same
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product, parent, false);
        return new ProductViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        product product = filteredProductList.get(position);
        holder.bind(product);
        Log.d("product_adapter", "Binding product: " + product.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When category item is clicked, pass the category to the listener


                NavController navController = Navigation.findNavController(v);
                Bundle bundle = new Bundle();
                bundle.putString("productName", product.getName());
                bundle.putDouble("productPrice", product.getPrice());
                bundle.putInt("productImageId", product.getImageId());
                navController.navigate(R.id.additemFragment, bundle);



            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredProductList.size();
    }

    public void filterByProductName(String query) {
        query = query.toLowerCase(); // Make search case-insensitive
        filteredProductList.clear();

        if (query.isEmpty()) {
            // If query is empty, show all products
            filteredProductList.addAll(originalProductList);
        } else {
            // Filter products by name
            for (product product : originalProductList) {
                if (product.getName().toLowerCase().contains(query)) {
                    filteredProductList.add(product);
                }
            }
        }

        // Notify the adapter that the data set has changed
        notifyDataSetChanged();
    }

    // Method to filter the products by category
    public void filterProducts(category category) {
        Log.d("product_adapter", "Filtering by category: " + category.getText());
        Log.d("product_adapter", "Original Product List Size: " + originalProductList.size());
        filteredProductList.clear();

        Log.d("product_adapter", "Original Product List Size: " + originalProductList.size());
        for (product product : originalProductList) {
            Log.d("product_adapter", "Processing product: " + product.getCategory());
            if (product.getCategory().equals(category.getText())) {
                Log.d("product_adapter", "Adding product to filtered list: " + product.getCategory());
                filteredProductList.add(product);

            }
        }

        Log.d("product_adapter", "Filtered Product List Size: " + filteredProductList.size());
        notifyDataSetChanged(); // Notify adapter to refresh the UI
    }

    // Method to reset the list to show all products
    public void resetProductList() {
        filteredProductList.clear();
        filteredProductList.addAll(originalProductList); // Reset to original products
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
        }

        public void bind(product product) {
            productImage.setImageResource(product.getImageId());
            productName.setText(product.getName());
            productPrice.setText("$" + product.getPrice());
        }
    }
}

