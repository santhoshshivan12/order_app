package com.example.app1.ui.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app1.ui.oder.OrderManager;
import com.example.app1.R;
import com.example.app1.ui.home.category.category;
import com.example.app1.ui.home.category.category_adapter;
import com.example.app1.ui.home.product.product;
import com.example.app1.ui.home.product.product_adapter;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements category_adapter.OnCategoryClickListener {


    private product_adapter adapter;
    OrderManager cart;// Adapter for products
    // All products
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true); // Enable the options menu for the fragment

        TextView itemsAdded = rootView.findViewById(R.id.added);
        // Setup RecyclerViews (category and Horizontal)
        setupcategoryRecyclerView(rootView);
        setupproductRecyclerView(rootView);

        cart = OrderManager.getInstance(); // Example initialization
        if (cart != null) {


            Log.d("HomeFragment", "OrderManager is not null");

            if(!(cart.getOrders().isEmpty())){
                itemsAdded.setVisibility(View.VISIBLE);
                Log.d("HomeFragment", "OrderManager is not empty");
                Log.d("HomeFragment", "OrderManager size: " + cart.getOrders().size());
            } else {
                itemsAdded.setVisibility(View.GONE);

                // Handle the null case, maybe throw an exception or log an error
            }
        }else{
            Log.e("HomeFragment", "OrderManager is null");
        }

        itemsAdded.setOnClickListener(v -> {
            // Navigate to oderfragment
            Navigation.findNavController(rootView).navigate(R.id.action_nav_home_to_nav_oder);

        });




        // Load products
        handleBackPressed();
        return rootView;




    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);



        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) ((MenuItem) searchItem).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filterByProductName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterByProductName(newText);
                return false;
            }
        });
    }

    // Set up category RecyclerView (Categories)
    private void setupcategoryRecyclerView(View rootView) {
        RecyclerView categoryRecyclerView = rootView.findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);

        List<category> categories = getCategoryList(); // Your categories list
        category_adapter categoryAdapter = new category_adapter(categories, this);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    // Set up horizontal RecyclerView (products)
    private void setupproductRecyclerView(View rootView) {
        RecyclerView productRecyclerView = rootView.findViewById(R.id.product_recycler_view);
        List<product> products = getAllproducts();;
        Log.d("entryFragment", "Product List Size: " + products.size());
       adapter = new product_adapter(products); // Initialize with all products
        productRecyclerView.setAdapter(adapter);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    // Handle category click (Filtering)
    @Override
    public void onCategoryClick(category selectedCategory) {
        if (adapter != null) {
            Log.d("entryFragment", "Category clicked: " + selectedCategory.getText());
            adapter.filterProducts(selectedCategory); // Filter products by category
        } else {

            Log.e("entryFragment", "Adapter is null");
        }
    }

    private void handleBackPressed() {

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Reset the adapter to the original product list on back press
                adapter.resetProductList();

                Log.d("ProductFragment", "Back pressed: Resetting product list");
                // If there are no more custom behaviors to handle, call remove() to let the system handle the back press
                remove();  // Optional: Call remove() to disable this callback and let the system handle future back presses.
            }
        });
    }

    // Load all products into the list
    private List<product> getAllproducts() {
        // Example products and categories, add your data here
        List<product> allproducts=new ArrayList<>();
        category burgerCategory = new category("Burger", R.drawable.burger);
        allproducts.add(new product("Double Bacon Burger", 0.99, burgerCategory, R.drawable.d_b));
        allproducts.add(new product("McD Combo Burger", 9.99, burgerCategory, R.drawable.c_b));
        allproducts.add(new product("Burger Mac", 2.99, burgerCategory, R.drawable.b));
        allproducts.add(new product("Big Mac", 3.99, burgerCategory, R.drawable.b));

        category sandwichCategory = new category("Sandwich", R.drawable.sandwich);
        allproducts.add(new product("Club Sandwich", 5.99, sandwichCategory,R.drawable.n_sw));
        allproducts.add(new product("chessy sandwich", 4.99, sandwichCategory,R.drawable.c_sw));
        allproducts.add(new product("Mc", 3.99, sandwichCategory,R.drawable.n_sw));

        category dessertscategory = new category("Desserts", R.drawable.shake);
        allproducts.add(new product("Brownie", 1.99, dessertscategory,R.drawable.b_d));
        allproducts.add(new product("Ice Cream", 2.99, dessertscategory,R.drawable.b_d));

        category shakeCategory = new category("Shakes", R.drawable.shake);
        allproducts.add(new product("Chocolate Shake", 3.99, shakeCategory,R.drawable.c_s));
        allproducts.add(new product("Strawberry Shake", 4.99, shakeCategory,R.drawable.r_s));
        allproducts.add(new product("Mango Shake", 5.99, shakeCategory,R.drawable.b_s));
        allproducts.add(new product("Vanilla Shake", 2.99, shakeCategory,R.drawable.r_s));

        category chipsCategory = new category("Chips", R.drawable.chips);
        allproducts.add(new product("Potato Chips", 1.99, chipsCategory,R.drawable.ff));


        return allproducts;
    }

    // Sample categories list
    private List<category> getCategoryList() {
        List<category> data = new ArrayList<>();
        data.add(new category("Burger",R.drawable.burger));
        data.add(new category("Sandwich",R.drawable.sandwich));
        data.add(new category("Shakes",R.drawable.shake));
        data.add(new category("Desserts",R.drawable.dessert));
        data.add(new category("Chips",R.drawable.chips));
        data.add(new category("Cocktail",R.drawable.cocktail));
        data.add(new category("Beer",R.drawable.beer));
        return data;
    }


}