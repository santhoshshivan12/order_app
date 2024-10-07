package com.example.app1.ui.oder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app1.R;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnItemRemovedListener listener;

    public OrderAdapter(List<CartItem> cartItems, OnItemRemovedListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item);



        holder.incrementButton.setOnClickListener(v -> {
            item.incrementCount();
            notifyItemChanged(position);
            updateTotals();
        });

        holder.decrementButton.setOnClickListener(v -> {
            item.decrementCount();
            if (item.isCountZero()) {
                cartItems.remove(position);
                notifyItemRemoved(position);
                listener.onItemRemoved(item);
            } else {
                notifyItemChanged(position);
            }
            updateTotals();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void updateTotals() {
        listener.onItemRemoved(null); // Trigger a total update
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemCount,incrementButton, decrementButton;
        ImageView itemImage;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemCount = itemView.findViewById(R.id.item_quantity);
            incrementButton = itemView.findViewById(R.id.plus_button);
            decrementButton = itemView.findViewById(R.id.minus_button);
        }

        public void bind(CartItem item) {
            itemName.setText(item.getName());
            itemPrice.setText(String.format("$%.2f", item.getPrice()));
            itemCount.setText(String.format("Count: %d", item.getCount()));
            itemImage.setImageResource(item.getImageResourceId());

        }
    }

    public interface OnItemRemovedListener {
        void onItemRemoved(CartItem item);
    }
}
